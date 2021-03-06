package com.xtoolapp.file.filedemo.ext

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.AdapterView
import androidx.annotation.ColorInt

/**
 * 简略打印
 */
val TAG = "wangyu"

fun logd(msg: String) {
    Log.d(TAG, msg)
}

fun logi(msg: String) {
    Log.i(TAG, msg)
}

fun logw(msg: String) {
    Log.w(TAG, msg)
}

//color-状态栏背景颜色
fun Activity.whiteTextTheme(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE//恢复状态栏白色字体
    }
}

//color-状态栏背景颜色
fun Activity.darkTextTheme(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//恢复状态栏黑色字体
    }
}

//深度回收view
fun View.gc() {
    val view = this
    if (view.background != null) {
        view.background.callback = null
    }
    if (view is ViewGroup && view !is AdapterView<*>) {
        if (view is WebView) {
            view.removeAllViews()
            view.destroy()
            return
        }
        for (i in 0 until view.childCount) {
            view.getChildAt(i).gc()
        }
        view.removeAllViews()
    }
}
