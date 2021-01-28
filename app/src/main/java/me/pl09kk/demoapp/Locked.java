package me.pl09kk.demoapp;

import android.util.Log;

public class Locked {

    private final Object a = new Object();
    private final Object b =new Object();

    public void lock(){
        PackageUtils utils = new PackageUtils() ;
        utils.clone() ;
        new Thread(() -> {
            synchronized (a){
                Log.e("Log111" , "locked a from 1 ")  ;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    Log.e("Log111" , "locked b from 1 ")  ;
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (b){
                Log.e("Log111" , "locked b from 2 ")  ;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    Log.e("Log111" , "locked a from 2 ")  ;
                }
            }
        }).start();

    }


    public synchronized void doSomeThing(){
        Log.e("Locker" ,  a.toString()) ;
    }


}
