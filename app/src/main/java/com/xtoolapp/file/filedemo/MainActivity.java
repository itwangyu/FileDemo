package com.xtoolapp.file.filedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xtoolapp.file.filedemo.bitmapmesh.MeshActivity;
import com.xtoolapp.file.filedemo.database.DataBaseActivity;
import com.xtoolapp.file.filedemo.douyin.VideoTestActivity;
import com.xtoolapp.file.filedemo.fastjson.FastJsonActivity;
import com.xtoolapp.file.filedemo.file.ScanFileActivity;
import com.xtoolapp.file.filedemo.foregroundservice.ForegroundService;
import com.xtoolapp.file.filedemo.rebeal.FirstActivity;
import com.xtoolapp.file.filedemo.wifi.WifiFileActivity;
import com.xtoolapp.file.filedemo.xfermode.XfermodeActivity;
import com.xtoolapp.file.filedemo.xfermode.sampleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        FileUtils.writeLog("这是一个测试文字");

        startService(new Intent(MainActivity.this, ForegroundService.class));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                startService(new Intent(MainActivity.this, ForegroundService.class));
//            }
//        },5000);
    }

    @OnClick({R.id.bt_scan_file, R.id.bt_reveal, R.id.bt_xfermode_demo, R.id.bt_xfermode_test,
    R.id.bt_wifi,R.id.bt_layout_manager,R.id.bt_database,R.id.bt_mesh,
    R.id.bt_fastjson,R.id.bt_ad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_scan_file:
                startActivity(new Intent(this,ScanFileActivity.class));
                break;
            case R.id.bt_reveal:
                startActivity(new Intent(this,FirstActivity.class));
                break;
            case R.id.bt_xfermode_demo:
                startActivity(new Intent(this, sampleActivity.class));
                break;
            case R.id.bt_xfermode_test:
                startActivity(new Intent(this, XfermodeActivity.class));
                break;
            case R.id.bt_wifi:
                startActivity(new Intent(this, WifiFileActivity.class));
                break;
            case R.id.bt_layout_manager:
                startActivity(new Intent(this, VideoTestActivity.class));
                break;
            case R.id.bt_database:
                startActivity(new Intent(this, DataBaseActivity.class));
                break;
            case R.id.bt_mesh:
                startActivity(new Intent(this, MeshActivity.class));
                break;
            case R.id.bt_fastjson:
                startActivity(new Intent(this, FastJsonActivity.class));
                break;
            case R.id.bt_ad:
                break;
        }
    }
}
