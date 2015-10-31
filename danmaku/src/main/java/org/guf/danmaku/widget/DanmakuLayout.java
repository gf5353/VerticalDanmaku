package org.guf.danmaku.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.guf.danmaku.I_Danmaku;
import org.guf.danmaku.bean.BaseDanmaku;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public class DanmakuLayout extends LinearLayout implements I_Danmaku {
    private String TAG = "DanmakuLayout";
    private int heightUsed;//子控件占用的所有高度
    private int height;//父控件的高度

    public DanmakuLayout(Context context) {
        super(context);
        init(context);
    }


    public DanmakuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DanmakuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DanmakuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.BOTTOM);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d(TAG, "onMeasure() called with: " + "widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
    }

    @Override
    public void addDanmaku(final BaseDanmaku danmaku) {
        drawView(danmaku);
    }

    @Override
    public void addDanmakuBottom(BaseDanmaku danmaku) {
        int childCount = getChildCount();
        Log.d(TAG, "childCount:" + childCount);
        if (childCount >= 1) {
            this.removeViewAt(childCount - 1);
        }
        drawView(danmaku);
    }

    public void drawView(BaseDanmaku danmaku) {
        TextView textView = new TextView(getContext());
        textView.setText(danmaku.text.toString());
        this.addView(textView);
    }

    @Override
    public void seekTo(Long ms) {

    }

    @Override
    public void start() {

    }

    @Override
    public void start(long postion) {

    }

    @Override
    public void stop() {
        this.removeAllViews();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void release() {
        stop();
    }

    @Override
    public void toggle() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        this.height = getHeight();
        this.heightUsed = heightUsed;
//        Log.d(TAG, "height:" + child.getMeasuredHeight() + "height：" + height + "heightUsed:" + heightUsed);
        if (height <= this.heightUsed) {
            removeViewAt(0);
        }
    }
}
