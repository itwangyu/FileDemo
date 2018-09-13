package com.xtoolapp.file.filedemo.alive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xtoolapp.file.filedemo.utils.FileUtils;

/**
 * Created by WangYu on 2018/8/14.
 */
public class AliveBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("wangyu", intent.getAction());

        FileUtils.writeLog(intent.getAction());
    }

}
