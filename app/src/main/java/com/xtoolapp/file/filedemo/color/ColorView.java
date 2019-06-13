package com.xtoolapp.file.filedemo.color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.xtoolapp.file.filedemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * index代表数组的角标
 * markIndex代表序号
 * Created by banana on 2019/6/13.
 */
public class ColorView extends View {

    private Bitmap mBitmap;
    int mScaleRate = 3;
    private Stack<Point> mStacks = new Stack<>();
    private int mBorderColor = 0xFF000000;
    private int mBitmapWidth;
    private int mBitmapHeight;
    //序号点map，一个序号对应多个坐标点，也就是一个集合，例如1-> 100,100 200,300两个点组成的集合
    private Map<Integer, List<Mark>> mMarkMap = new HashMap<>();
    //序号点对应的像素map，一个序号对应多个区块，每个区块包含很多像素点
    private Map<Integer, List<List<Integer>>> mBlockPixelMap = new HashMap<>();
    //标记每个像素点的序号
    private int[] mIndexArr;
    private int[] mPixelsArr;
    private Paint mTextPaint;

    public ColorView(Context context) {
        this(context, null);
    }

    public ColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_007).copy(Bitmap.Config.RGB_565, true);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * mScaleRate, mBitmap.getHeight() * mScaleRate, true);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        initData();
    }

    private void initData() {
        long startTime = System.currentTimeMillis();
        if (mBitmap == null) {
            return;
        }
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        //数字点坐标集合
        List<Mark> pointList = new ArrayList<>();
        pointList.add(new Mark(174, 654, Color.RED));
        pointList.add(new Mark(484, 796, Color.RED));
        mMarkMap.put(1, pointList);
        pointList = new ArrayList<>();
        pointList.add(new Mark(723, 703, Color.BLUE));
        pointList.add(new Mark(693, 302, Color.BLUE));
        mMarkMap.put(2, pointList);
        //拿到该bitmap的颜色数组
        mPixelsArr = new int[mBitmapWidth * mBitmapHeight];
        mBitmap.getPixels(mPixelsArr, 0, mBitmapWidth, 0, 0, mBitmapWidth, mBitmapHeight);
        //创建序号数组 用来标记每个像素点的序号
        mIndexArr = new int[mBitmapWidth * mBitmapHeight];
        //遍历数字点坐标集合，计算出每个区域包含的像素点
        Iterator<Map.Entry<Integer, List<Mark>>> iterator = mMarkMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<Mark>> next = iterator.next();
            //序号
            Integer markIndex = next.getKey();
            //当前序号对应的所有区块的list
            List<List<Integer>> list = new ArrayList<>();
            //遍历坐标点
            for (Mark mark : next.getValue()) {
                list.add(getPixelsForBlock(markIndex, new Point(mark.x, mark.y)));
            }
            mBlockPixelMap.put(markIndex, list);
        }
        Log.i("wangyu", "解析完毕:" + (System.currentTimeMillis() - startTime));
    }

    /**
     * 根据给定的坐标点计算出这个坐标点所属区域的所有像素坐标
     * 传入index用于建立序号数组  这样点击的时候马上就能获取到序号  然后根据序号去找对应的区块 再进行着色
     *
     * @param point
     * @return
     */
    private List<Integer> getPixelsForBlock(int markIndex, Point point) {
        List<Integer> list = new ArrayList<>();
        /**
         * 步骤1：将种子点(x, y)入栈；
         */
        mStacks.push(point);
        /**
         * 步骤2：判断栈是否为空，
         * 如果栈为空则结束算法，否则取出栈顶元素作为当前扫描线的种子点(x, y)，
         * y是当前的扫描线；
         */
        while (!mStacks.isEmpty()) {
            /**
             * 步骤3：从种子点(x, y)出发，沿当前扫描线向左、右两个方向填充，
             * 直到边界。分别标记区段的左、右端点坐标为xLeft和xRight；
             */
            Point seed = mStacks.pop();
            int count = fillLineLeft(list, markIndex, seed.x, seed.y);
            int left = seed.x - count + 1;
            count = fillLineRight(list, markIndex, seed.x + 1, seed.y);
            int right = seed.x + count;

            /**
             * 步骤4：
             * 分别检查与当前扫描线相邻的y - 1和y + 1两条扫描线在区间[xLeft, xRight]中的像素，
             * 从xRight开始向xLeft方向搜索，假设扫描的区间为AAABAAC（A为种子点颜色），
             * 那么将B和C前面的A作为种子点压入栈中，然后返回第（2）步；
             */
            //从y-1找种子
            if (seed.y - 1 >= 0)
                findSeedInNewLine(markIndex, seed.y - 1, left, right);
            //从y+1找种子
            if (seed.y + 1 < mBitmapHeight)
                findSeedInNewLine(markIndex, seed.y + 1, left, right);
        }
        return list;
    }

    /**
     * 在新行找种子节点
     */
    private void findSeedInNewLine(int markIndex, int i, int left, int right) {
        /**
         * 获得该行的开始索引
         */
        int begin = i * mBitmapWidth + left;
        /**
         * 获得该行的结束索引
         */
        int end = i * mBitmapWidth + right;

        boolean hasSeed = false;

        int rx = -1, ry = -1;

        ry = i;

        /**
         * 从end到begin，找到种子节点入栈（AAABAAAB，则B前的A为种子节点）
         */
        while (end >= begin) {
            if (mPixelsArr[end] == Color.WHITE && mIndexArr[end] != markIndex) {
                if (!hasSeed) {
                    rx = end % mBitmapWidth;
                    mStacks.push(new Point(rx, ry));
                    hasSeed = true;
                }
            } else {
                hasSeed = false;
            }
            end--;
        }
    }

    /**
     * 往右填色，返回填充的个数
     *
     * @return
     */
    private int fillLineRight(List<Integer> list, int markIndex, int x, int y) {
        int count = 0;

        while (x < mBitmapWidth) {
            //拿到索引
            int index = y * mBitmapWidth + x;
            if (needFillPixel(index)) {
                mIndexArr[index] = markIndex;
                list.add(index);
                count++;
                x++;
            } else {
                break;
            }

        }

        return count;
    }


    /**
     * 往左填色，返回填色的数量值
     *
     * @return
     */
    private int fillLineLeft(List<Integer> list, int indexI, int x, int y) {
        int count = 0;
        while (x >= 0) {
            //计算出索引
            int index = y * mBitmapWidth + x;
            if (needFillPixel(index)) {
                //标记这个像素的序号
                mIndexArr[index] = indexI;
                //将这个像素的index加入list，最后放入序号为key的map里存储
                list.add(index);
                count++;
                x--;
            } else {
                break;
            }
        }
        return count;
    }

    private boolean needFillPixel(int index) {
        int color = mPixelsArr[index];
        return color != mBorderColor;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                L.i("x:" + x + ",y:" + y);
                paintImageColor((int) x, (int) y);
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 图片填色
     *
     * @param x
     * @param y
     */
    private void paintImageColor(int x, int y) {
        //根据点击位置的坐标找到对应的markIndex
        int index = y * mBitmapHeight + x;
        if (index > mIndexArr.length - 1 || index < 0) {
            return;
        }
        int markIndex = mIndexArr[index];
        Log.i("wangyu", "点击的角标：" + markIndex);
        //根据角标去map里找到对应的区域集合
        List<List<Integer>> list = mBlockPixelMap.get(markIndex);
        if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(mMarkMap.get(markIndex))) {
            return;
        }
        int color = mMarkMap.get(markIndex).get(0).color;
        Log.i("wangyu", "开始遍历，size:" + list.size());
        for (List<Integer> blockList : list) {
            if (blockList.contains(index)) {
                for (Integer i : blockList) {
                    mPixelsArr[i] = color;
                }
            }
        }
        mBitmap.setPixels(mPixelsArr, 0, mBitmapWidth, 0, 0, mBitmapWidth, mBitmapHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        Iterator<Map.Entry<Integer, List<Mark>>> iterator = mMarkMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<Mark>> next = iterator.next();
            Integer key = next.getKey();
            for (Mark mark : next.getValue()) {
                mTextPaint.setTextSize(30);
                drawTextCenter(canvas, String.valueOf(key), mTextPaint, mark.x, mark.y);
            }
        }
    }

    /**
     * 将文字以给定的坐标点为中心画在画布上
     */
    private void drawTextCenter(Canvas canvas, String text, Paint paint, float x, float y) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (y - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(text, x, baseLineY, paint);
    }
}
