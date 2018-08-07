package com.xtoolapp.file.filedemo.database;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xtoolapp.file.filedemo.R;

/**
 * Created by WangYu on 2018/8/7.
 */
public class DataBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        findViewById(R.id.bt_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfo appInfo = new AppInfo();
                appInfo.appName = "魔法相机";
                appInfo.packageName = "com.wangyu.camera";
                AppInfoDataBase.getInstance().insertApp(appInfo);
            }
        });
        findViewById(R.id.bt_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoDataBase.getInstance().getAllAppInfo();
            }
        });
        findViewById(R.id.bt_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoDataBase.getInstance().updateInfo();
            }
        });
        findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoDataBase.getInstance().deleteInfo();
            }
        });
    }
}
