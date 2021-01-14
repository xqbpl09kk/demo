package me.pl09kk.demoapp

import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.view.WindowManager
import android.widget.Toast
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


private const val TAG = "ToastCompat"


fun reflectTNHandler(toast: Toast) {
    try {
        val tNField: Field = toast::class.java.getDeclaredField("mTN") ?: return
        tNField.isAccessible = true
        val mTN = tNField[toast] ?: return
        val handlerField = mTN.javaClass.getDeclaredField("mHandler") ?: return
        EdLog.d(TAG , "TN class is ${mTN.javaClass}")
        handlerField.isAccessible = true
        handlerField[mTN] = ProxyTNHandler(mTN)
        EdLog.d(TAG , "replace TN success !! ")
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}


private class ProxyTNHandler internal constructor(private val tnObject: Any) : Handler(Looper.getMainLooper()) {

    private var handleShowMethod: Method? = null
    private var handleHideMethod: Method? = null

    init {
        try {
            handleShowMethod = tnObject.javaClass.getDeclaredMethod("handleShow", IBinder::class.java)
            handleShowMethod?.isAccessible = true
            EdLog.d(TAG ,"handleShow method is $handleShowMethod")
            handleHideMethod = tnObject.javaClass.getDeclaredMethod("handleHide")
            handleHideMethod?.isAccessible = true
            EdLog.d(TAG , "handleHide method is $handleHideMethod")
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
    }

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            0 -> { //SHOW
                val token: IBinder = msg.obj as IBinder
                try {
                    handleShowMethod?.invoke(tnObject, token)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                    EdLog.e(TAG , "show toast error.")
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                    EdLog.e(TAG , "show toast error.")
                } catch (e: WindowManager.BadTokenException) {
                    //显示Toast时添加BadTokenException异常捕获
                    e.printStackTrace()
                    EdLog.e(TAG , "show toast error.")
                }
            }
            1 -> { //HIDE
                EdLog.d(TAG , "handleMessage(): hide")
                try {
                    handleHideMethod?.invoke(tnObject)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
            2 -> {//CANCEL
                EdLog.d(TAG, "handleMessage(): cancel")
                try {
                    handleHideMethod?.invoke(tnObject)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
        super.handleMessage(msg)
    }


}
