package me.pl09kk.demoapp

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private var recyclerView :RecyclerView ?= null
    private val list = arrayListOf<Bitmap>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        DecodeFrame.decode(this , callback )
        val toast = Toast.makeText(this, "Toast" , Toast.LENGTH_LONG)
        reflectTNHandler(toast)
        toast.show()
        Thread.sleep(5000) ;
        val locker = Locked()
        locker.lock()
        locker.doSomeThing()
    }




    private val callback  =  object : Callback {
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
                    : RecyclerView.ViewHolder {
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
            this.layoutParams = RecyclerView.LayoutParams(200 , 200).also { it.leftMargin = 20 }
            this.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun initItemHolder(itemHolder : RecyclerView.ViewHolder)  {
        itemHolder.itemView.setOnClickListener {
            Toast.makeText(this@MainActivity , "position : ${itemHolder.adapterPosition}" , Toast.LENGTH_LONG).show()
        }
    }
}