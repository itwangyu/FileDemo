package com.xtoolapp.file.filedemo.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xtoolapp.file.filedemo.R;

/**
 * Created by WangYu on 2018/5/31.
 */
public class TestView extends View {
    private Context mContext;
    private Paint mPaint;
    private int mWidth, mHeight;
    private PorterDuffXfermode xfermode;
    private Bitmap dst;
    private Bitmap src;
    private Canvas scrCanvas;
    private int progress;
    private Paint p;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        progress=mHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        makeDst(mWidth, mHeight);
        makeSrc(mWidth, mHeight);
    }

    Bitmap makeDst(int w, int h) {
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_zodiac_small_11);
        dst = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(this.dst);
        drawable.setBounds(0,0,w,h);

//        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
//        p.setColor(0xFFFFCC44);
//        c.drawBitmap(bm,0,0,mPaint);
        drawable.draw(c);
        return this.dst;
    }

    Bitmap makeSrc(int w, int h) {
        if (src == null) {
            src = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            scrCanvas = new Canvas(this.src);
            p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setColor(Color.GREEN);
        }
        Log.i("wangyu",progress+"" );
        scrCanvas.drawRect(0, progress, w, h, p);
        return this.src;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.saveLayer(0, 0, mWidth, mHeight, null);
        canvas.drawBitmap(dst, 0, 0, mPaint);
        mPaint.setXfermode(xfermode);
        makeSrc(mWidth, mHeight);
        canvas.drawBitmap(src, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restore();
        if (progress>0) {
            progress--;
            postInvalidate();
        }
    }
}
