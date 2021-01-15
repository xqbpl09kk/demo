package me.pl09kk.demoapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var recyclerView :RecyclerView ?= null
    private val list = arrayListOf<Bitmap>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        DecodeFrame.decode(this , decodeCallback)
        startService(Intent(this , WorkService::class.java))
        val toast = Toast.makeText(this, "Toast" , Toast.LENGTH_LONG)
        reflectTNHandler(toast)
        toast.show()
        Thread.sleep(5000) ;
        val locker = Locked()
        locker.lock()
        locker.doSomeThing()
        EdLog.e("MainActivity" , "create in branch b1")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this , WorkService::class.java))
    }

    private val decodeCallback  =  object : Callback {
        override fun callback(bitmap:Bitmap) {
            runOnUiThread {
                list.add(bitmap)
                recyclerView?.adapter
                        ?.notifyItemInserted(list.size -1)
                        ?: initAdapter()
            }
        }
    }

    private fun initAdapter(){
        recyclerView?.layoutManager = LinearLayoutManager(this@MainActivity
                , LinearLayoutManager.HORIZONTAL , false)
        recyclerView?.adapter = object :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                    : RecyclerView.ViewHolder {123
                return object : RecyclerView.ViewHolder(createItemView()) {
                    init { initItemHolder(this) }
                }
            }

            override fun getItemCount(): Int {
                return list.size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as ImageView).setImageBitmap(list[position])
            }

        }
    }

    private fun createItemView() : View{
        return ImageView(this@MainActivity).apply {
            layoutParams = RecyclerView.LayoutParams(200 , 200).apply { leftMargin = 20 }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun initItemHolder(itemHolder : RecyclerView.ViewHolder)  {
        itemHolder.itemView.setOnClickListener {
            Toast.makeText(this@MainActivity , "position : ${itemHolder.adapterPosition}" , Toast.LENGTH_LONG).show()
            stopService(Intent(this , WorkService::class.java))
        }
    }
}