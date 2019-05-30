package com.xtoolapp.file.filedemo.rebeal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

import com.xtoolapp.file.filedemo.R;

/**
 * Created by WangYu on 2018/5/30.
 */
public class RevealActivity extends AppCompatActivity {


    private int centerX;
    private int centerY;
    private float startRadius;
    private float endRadius;
    private View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        mRootView = findViewById(R.id.ll_root);
        Intent intent = getIntent();
        centerX = intent.getIntExtra("centerX", 0);
        centerY = intent.getIntExtra("centerY", 0);
        startRadius = intent.getIntExtra("startRadius", 0);
        endRadius = (float) intent.getDoubleExtra("endRadius", 0);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(getWindow().getDecorView(), centerX, centerY, startRadius, endRadius);
                circularReveal.setDuration(500).start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(getWindow().getDecorView(), centerX, centerY, endRadius, startRadius);
        circularReveal
                .setDuration(500)
                .addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        overridePendingTransition(0, 0);
                        finish();
//                        overridePendingTransition(R.anim.anim_hold, 0);
                    }
                });
        circularReveal.start();
    }
}
