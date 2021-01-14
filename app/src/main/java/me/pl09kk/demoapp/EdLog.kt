package me.pl09kk.demoapp

import android.util.Log

object EdLog {

    fun d(tag : String , msg : Any ){
        Log.d( tag, msg.toString())
    }


    fun e(tag : String , msg : Any ){
        Log.e( tag, msg.toString())
    }
}