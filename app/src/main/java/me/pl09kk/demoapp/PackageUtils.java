package me.pl09kk.demoapp;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PackageUtils implements  Cloneable{

    public PackageUtils clone(){
        try {
           return (PackageUtils) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } ;
        return null ;
    }

    private static final String TAG = "PackageUtils" ;
    public static Class[] getClasses(String packageName) {
        Log.e(TAG  , "get Classes " + packageName) ;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) return null;
        String realPath = packageName.replace('.', '/');
        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(realPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resources == null) return null;
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL res = resources.nextElement();
            dirs.add(new File(res.getFile()));
        }
        Log.e(TAG  , "dirs is  " + dirs) ;
        ArrayList<Class> classes = new ArrayList<>();
        for (File file : dirs) {
            classes.addAll(findClasses(file , packageName)) ;
        }
        Log.e(TAG  , "classes is  " + classes) ;
        return classes.toArray(new Class[classes.size()]) ;
    }

    public static List<Class> findClasses(File dir, String packageName) {

        List<Class> classes = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }
}
