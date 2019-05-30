package com.xtoolapp.file.filedemo.camera;

import android.hardware.Camera;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by WangYu on 2018/6/7.
 */
public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int cameras = Camera.getNumberOfCameras();
        Log.i("wangyu", cameras+"");
    }

    public static Camera openCamera(int cameraId) {
        try{
            return Camera.open(cameraId);
        }catch(Exception e) {
            return null;
        }
    }
}
