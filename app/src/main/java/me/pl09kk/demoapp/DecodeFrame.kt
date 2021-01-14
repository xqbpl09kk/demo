package me.pl09kk.demoapp

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlin.concurrent.thread


interface Callback{
    fun callback(bitmap : Bitmap)
}

object DecodeFrame  {

    var num = 0L

    fun decode(context : Context, runnable: Callback){


        thread {
            val start = System.currentTimeMillis()
            val url: Uri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.basketball)
            val videoRetriever = MediaMetadataRetriever()
            videoRetriever.setDataSource(context , url)
            while(num < 70){
                num ++
                val bitmap = videoRetriever.getFrameAtTime(num*1000 * 1000 )
                bitmap?.let {runnable.callback(bitmap) }
            }
            Log.e("DecodeFrame" , "Default decode cost ${System.currentTimeMillis() - start}")
            videoRetriever.release()
        }
        thread {
            val start = System.currentTimeMillis()
            val url: Uri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.basketball)
            val videoRetriever = MediaMetadataRetriever()
            videoRetriever.setDataSource(context , url)
            while(num < 70){
                num ++
                val bitmap = videoRetriever.getFrameAtTime(num*1000 * 1000 )
                bitmap?.let {runnable.callback(bitmap) }
            }
            Log.e("DecodeFrame" , "Default decode cost ${System.currentTimeMillis() - start}")
            videoRetriever.release()
        }

        thread {
            val start = System.currentTimeMillis()
            val url: Uri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.basketball)
            val videoRetriever = MediaMetadataRetriever()
            videoRetriever.setDataSource(context , url)
            while(num < 70){
                num ++
                val bitmap = videoRetriever.getFrameAtTime(num*1000 * 1000 )
                bitmap?.let {runnable.callback(bitmap) }
            }
            Log.e("DecodeFrame" , "Default decode cost ${System.currentTimeMillis() - start}")
            videoRetriever.release()
        }




//        thread {
//            val start = System.currentTimeMillis()
//            var num = 0L
//            val videoRetriever = MediaMetadataRetriever()
//            val url: Uri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.basketball)
//            videoRetriever.setDataSource(context , url)
//            while(num < 100){
//                num ++
//                val bitmap = videoRetriever.getFrameAtTime(num , MediaMetadataRetriever.OPTION_CLOSEST)
//            }
//            Log.e("DecodeFrame" , "OPTION_CLOSEST decode cost ${System.currentTimeMillis() - start}")
//        }
    }
}