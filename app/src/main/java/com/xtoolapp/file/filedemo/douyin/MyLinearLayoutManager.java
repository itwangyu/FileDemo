package com.xtoolapp.file.filedemo.douyin;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by WangYu on 2018/6/5.
 */
public class MyLinearLayoutManager extends LinearLayoutManager {

    private PagerSnapHelper snapHelper;

    public MyLinearLayoutManager(Context context) {
        super(context);
        snapHelper = new PagerSnapHelper();

    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        snapHelper.attachToRecyclerView(view);
        view.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {

            }
        });
    }

    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                Log.i("wangyu", "idle:"+getChildCount());
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                Log.i("wangyu", "dragging:"+getChildCount());
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                Log.i("wangyu", "settling:"+getChildCount());
                break;
        }
    }
}
