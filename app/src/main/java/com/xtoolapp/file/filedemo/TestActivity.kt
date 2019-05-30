package com.xtoolapp.file.filedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * Created by banana on 2019/5/30.
 */
class TestActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun <T : ViewModel> createViewModel(clazz: Class<T>): T {
        return ViewModelProviders.of(this).get(clazz)
    }
}
