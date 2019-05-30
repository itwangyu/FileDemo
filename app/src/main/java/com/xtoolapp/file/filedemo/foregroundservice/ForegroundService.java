package com.xtoolapp.file.filedemo.foregroundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.util.Log;

/**
 * Created by WangYu on 2018/9/10.
 */
public class ForegroundService extends Service {

    public static void start(Context context) {
        if (context==null) {
            return;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("wangyu", "on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("wangyu", "on start command");
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
                notification.setContentTitle("cute!!!")
                .setContentText("test!!!")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true);
        startForeground(1,notification.build());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("wangyu", "running");
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("wangyu", "on destroy");
        super.onDestroy();
    }
}
