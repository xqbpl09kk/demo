package me.pl09kk.demoapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class TestThread {
    static class DataHolder{
        public final ArrayList<String > data = new ArrayList<>() ;
    }

    public final DataHolder holder = new DataHolder();
    private Thread thread  ;
    private int count = 0 ;
    public void start(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized(holder){
                        if(holder.data.size() > 300){
                            holder.data.remove(holder.data.size() -1 ) ;
                        }
                        holder.data.add("1233") ;
                        Log.e("Thread" , "data is = " + count ++) ;
//                    if(count > 300)
//                        break ;
                        try {
                            Thread.sleep(10) ;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
//        thread.start();
    }

    public void bind() throws InterruptedException {
//        try{
        thread.start();
            synchronized (this){
//            holder.wait(1);
                for(String item : holder.data){
                    item.toString() ;
                    Thread.sleep(1);
                }
//                thread.notifyAll();
//            holder.notifyAll();
            }
//        }catch(ConcurrentModificationException cme){
//            cme.printStackTrace();
//        }


//        synchronized(data){
//        }
    }

}
