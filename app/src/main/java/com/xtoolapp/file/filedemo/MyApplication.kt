package com.xtoolapp.file.filedemo

import android.app.Application
import android.content.Context

/**
 * Created by WangYu on 2018/8/3.
 */
class MyApplication : Application() {
    override fun attachBaseContext(var1: Context) {
        super.attachBaseContext(var1)
//        SplitCompat.install(this)
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @JvmStatic
        lateinit var context: Context
    }
}
