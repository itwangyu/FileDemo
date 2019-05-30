package com.xtoolapp.file.filedemo.rebeal;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xtoolapp.file.filedemo.R;

/**
 * 练习揭露动画打开新的页面
 */
public class FirstActivity extends AppCompatActivity {

    private RelativeLayout rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final ImageView imageView = findViewById(R.id.iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] locationArr = new int[2];
                imageView.getLocationInWindow(locationArr);
                Intent intent = new Intent(FirstActivity.this, RevealActivity.class);
                intent.putExtra("centerX", imageView.getWidth() / 2 + locationArr[0]);
                intent.putExtra("centerY", imageView.getHeight() / 2 + locationArr[1]);
                intent.putExtra("startRadius", imageView.getWidth() / 2);

                float y = getWindow().getDecorView().getHeight() - (imageView.getHeight() / 2 + locationArr[1]);
                float x = imageView.getWidth() / 2 + locationArr[0];
                double endRadius = Math.sqrt(x * x + y * y);
                intent.putExtra("endRadius", endRadius);
                startActivity(intent);

            }
        });
    }
}
