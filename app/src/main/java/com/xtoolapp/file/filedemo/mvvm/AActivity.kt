package com.xtoolapp.file.filedemo.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.xtoolapp.file.filedemo.R
import com.xtoolapp.file.filedemo.ext.getViewModel

/**
 * 体验mvvm
 */
class AActivity : AppCompatActivity() {

    val mViewMoel:AViewModel by lazy { getViewModel(AViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mv)
        mViewMoel.stringData.postValue("这是来自AActivity的改变")
        mViewMoel.intData.observe(this, Observer {  })
    }
}

