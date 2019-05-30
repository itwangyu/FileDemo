package com.xtoolapp.file.filedemo.ext

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * Created by banana on 2019/5/30.
 */

fun AppCompatActivity.start(clazz: Class<out Activity>) {
    startActivity(Intent(this, clazz))
}

fun <T : ViewModel> FragmentActivity.getViewModel(clazz: Class<T>): T {
    return ViewModelProviders.of(this).get(clazz)
}

fun <T : ViewModel> Fragment.getViewModel(clazz: Class<T>): T {
    return activity?.run { ViewModelProviders.of(this).get(clazz) }
            ?: ViewModelProviders.of(this).get(clazz)
}