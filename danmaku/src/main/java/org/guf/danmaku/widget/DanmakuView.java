package org.guf.danmaku.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import org.guf.danmaku.I_Danmaku;
import org.guf.danmaku.bean.BaseDanmaku;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public class DanmakuView extends View implements I_Danmaku {
    public DanmakuView(Context context) {
        super(context);
        init(context);
    }


    public DanmakuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DanmakuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DanmakuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private Paint mPaint;

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(20);
    }

    private BaseDanmaku danmaku;

    @Override
    public void addDanmaku(BaseDanmaku danmaku) {
        this.danmaku = danmaku;
        invalidate();
    }

    @Override
    public void addDanmakuBottom(BaseDanmaku danmaku) {

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

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void release() {

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(danmaku.text.toString(), 100, 100, mPaint);

    }
}
