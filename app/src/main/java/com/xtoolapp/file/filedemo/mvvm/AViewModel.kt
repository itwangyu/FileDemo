package com.xtoolapp.file.filedemo.mvvm

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * viewmodel是根据ViewModelsProviders.of()传入的对象而单例
 * 如果传入相同的对象，虽然返回的provider是新的，但是modelstore是activity或者fragment里的同一个，
 * modelstore里面有个hashmap，用来存储viewmodel 的对象。同一个modelstore 就能保证单例了
 * Created by banana on 2019/5/30.
 */
class AViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * livedata 当fragment或者activity的的状态最少是started才会立马接到通知(onStop执行之后不会再收到)
     * 当状态恢复为active也就是start时候  会收到最后一次  也就是当前的值的一次通知
     */
    val stringData = MutableLiveData<String>()
    val intData = MutableLiveData<Int>()

    init {
        stringData.observeForever {
            Toast.makeText(getApplication(), it, Toast.LENGTH_LONG).show()
            Log.i("wangyu", "change:$it")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("wangyu", "on clear")
    }
}