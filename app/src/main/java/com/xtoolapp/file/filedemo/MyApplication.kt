package com.xtoolapp.file.filedemo

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompatApplication

/**
 * Created by WangYu on 2018/8/3.
 */
class MyApplication : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @JvmStatic
        lateinit var context: Context
    }
}
