package me.pl09kk.demoapp

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var recyclerView :RecyclerView ?= null
    private val list = arrayListOf<Bitmap>()

    private val data = arrayListOf<String>()
    val test = TestThread() ;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        DecodeFrame.decode(this , decodeCallback)
        startService(Intent(this , WorkService::class.java))
        val toast = Toast.makeText(this, "Toast" , Toast.LENGTH_LONG)
        reflectTNHandler(toast)
        toast.show()
        runOnUiThread {  }
//        test.start()
//        start() ;
        findViewById<Button>(R.id.btn).setOnClickListener {
//            test.bind()
            bind() ;
        }

        PackageUtils.getClasses(javaClass.`package`.name)
//        Thread.sleep(5000) ;
//        val locker = Locked()
//        locker.lock()
//        locker.doSomeThing()
        EdLog.e("MainActivity" , "create in master !!!")
        test() ;
    }


    @Throws(InterruptedException::class)
    fun bind() {
        synchronized (DataHolder::class.java){
            for (item in test.holder.data) {
                Thread.sleep(1)
//                val new = item.toString()
            }
        }

//        synchronized(data){
//        }
    }

    internal class DataHolder {
        val data = ArrayList<String>()
    }

    private val holder = DataHolder()
    private var count = 0

    fun start() {
        Thread(Runnable {
            while (true) {
                if (holder.data.size > 1000) {
                    holder.data.removeAt(holder.data.size - 1)
                }
                holder.data.add("1233")
                Log.e("Thread", "data is = " + count++)
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
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