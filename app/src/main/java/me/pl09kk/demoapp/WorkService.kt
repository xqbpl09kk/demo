package me.pl09kk.demoapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class WorkService : Service() , Runnable {


    companion object{
        private const val WORK_SCHEDULE = 1000  * 2L
        private const val TAG = "WORK_THREAD"

    }

    private val workThread = Thread(this)

    private var runningFlag = false

    private var workCount = 0

    override fun onBind(intent: Intent?): IBinder? {
        EdLog.d(TAG  , "onBind")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        EdLog.d(TAG  , "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startWork()
        EdLog.d(TAG  , "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        EdLog.d(TAG  , "onDestroy")
        stopWork()
    }

    private fun startWork(){
        runningFlag = true
        workThread.start()
    }

    private fun stopWork (){
        runningFlag = false
        workThread.interrupt()
    }

    override fun run() {
        while (runningFlag){
            scheduledWork()
            if(!runningFlag) break
            try {
                Thread.sleep(WORK_SCHEDULE)
            }catch (interrupt : InterruptedException){
                interrupt.printStackTrace()
                break
            }
        }
    }

    private fun scheduledWork(){
        EdLog.d(TAG  , "doing scheduled work !!${workCount++}")
    }


}