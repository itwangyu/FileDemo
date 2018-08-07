package com.xtoolapp.file.filedemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by WangYu on 2018/8/3.
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}
