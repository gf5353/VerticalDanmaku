package org.guf.danmaku.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.guf.danmaku.I_Danmaku;
import org.guf.danmaku.bean.BaseDanmaku;
import org.guf.danmaku.core.DrawHandler;
import org.guf.danmaku.core.IDanmakuViewController;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public class DanmakuLayout extends LinearLayout implements I_Danmaku, IDanmakuViewController {
    private String TAG = "DanmakuLayout";
    private int heightUsed;//子控件占用的所有高度
    private int height;//父控件的高度
    private DrawHandler handler;

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
        handler = new DrawHandler(context, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d(TAG, "onMeasure() called with: " + "widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
    }

    int lastGravity = Gravity.TOP;

    @Override
    public void addDanmaku(BaseDanmaku danmaku) {
        int childCount = getChildCount();
        Log.d(TAG, "childCount:" + childCount);
        if (lastGravity == Gravity.BOTTOM && childCount > 0)
            this.removeViewAt(childCount - 1);
        lastGravity = danmaku.gravity;

        if (handler != null) handler.addItem(danmaku);
    }

    public void drawView(BaseDanmaku danmaku) {
        TextView view = new TextView(getContext());
        view.setText(danmaku.text.toString());
        this.addView(view);
        Animator set = getAnimator(view);
        set.addListener(new AnimEndListener(view));
        set.start();
    }

    private Animator getAnimator(View target) {
        AnimatorSet set = getEnterAnimtor(target);
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimtor(View target) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 1f, 0.1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(3 * 1000);//500
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha);
        enter.setTarget(target);
        return enter;
    }

    @Override
    public void drawDanmaku(BaseDanmaku danmaku) {
        drawView(danmaku);
    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
//            Log.v(TAG, "removeView后子view数:" + getChildCount());
        }
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
