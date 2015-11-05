package org.guf.danmaku.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.guf.danmaku.I_Danmaku;
import org.guf.danmaku.bean.BaseDanmaku;
import org.guf.danmaku.core.BaseCacheStuffer;
import org.guf.danmaku.core.DrawHandler;
import org.guf.danmaku.core.IDanmakuViewController;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public class DanmakuView extends LinearLayout implements I_Danmaku, IDanmakuViewController {
    private String TAG = "DanmakuLayout";
    private int heightUsed;//子控件占用的所有高度
    private int height;//父控件的高度
    private DrawHandler handler;
    private BaseCacheStuffer stuffer;

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

    private void init(Context context) {
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.BOTTOM);
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

//        if (lastGravity == Gravity.BOTTOM && childCount > 0)
//            this.removeViewAt(childCount - 1);

        if (handler != null) handler.addItem(danmaku);
    }

    @Override
    public void setCacheStuffer(BaseCacheStuffer stuffer) {
        this.stuffer = stuffer;
    }


    private Animator getAnimator(View target, long duration) {
        AnimatorSet set = getEnterAnimtor(target);
        set.setDuration(duration);
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimtor(View target) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 1f, 0.1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha);
        enter.setTarget(target);
        return enter;
    }

    @Override
    public void drawDanmaku(BaseDanmaku danmaku) {
        int childCount = getChildCount();
        View view = null;
        if (lastGravity == Gravity.BOTTOM && danmaku.gravity == Gravity.BOTTOM) {
            view = getChildAt(childCount - 1);
            if (view != null && stuffer != null) {
                stuffer.updateBottomDanmaku(view, danmaku);
            }
            return;
        } else if (childCount > 1 && lastGravity == Gravity.BOTTOM && danmaku.gravity == Gravity.TOP) {//防止底部进入房间的item滚动到上方
            this.removeViewAt(childCount - 1);
        }
        if (view == null && stuffer != null) {
            if (danmaku.gravity == Gravity.TOP) {
                view = stuffer.createDanmaku(getContext(), danmaku);
            } else if (danmaku.gravity == Gravity.BOTTOM) {
                view = stuffer.createBottomDanmaku(getContext(), danmaku);
            }
            this.addView(view);
            view.setPadding(danmaku.padding, danmaku.padding, danmaku.padding, danmaku.padding);
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            if (params != null) {
                params.setMargins(danmaku.margin, danmaku.margin, danmaku.margin, danmaku.margin);
                view.setLayoutParams(params);
            }
        }
        lastGravity = danmaku.gravity;
        if (lastGravity != Gravity.BOTTOM) {
            Animator set = getAnimator(view, danmaku.duration);
            set.addListener(new AnimEndListener(view));
            set.start();
        } else if (danmaku.gravity == Gravity.BOTTOM && getChildCount() == 0) {
            Animator set = getAnimator(view, danmaku.duration);
            set.addListener(new AnimEndListener(view));
            set.start();
        }
    }

    @Override
    public void removeDanmaku(BaseDanmaku danmaku) {
        View view = this.getChildAt(0);
        if (view == null || danmaku == null) return;

        Log.d("11111111111111111--", danmaku.text + "");

        Toast.makeText(getContext(), danmaku.text.toString(), Toast.LENGTH_LONG).show();
//        Animator set = getAnimator(view, danmaku.duration);
//        set.addListener(new AnimEndListener(view));
//        set.start();
    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if (target == null) return;
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
        }
    }


    @Override
    public void stop() {
        this.removeAllViews();
    }


    @Override
    public void release() {
        stop();
    }


    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        this.height = getHeight();
        this.heightUsed = heightUsed;
//        Log.d(TAG, "height:" + child.getMeasuredHeight() + "height：" + height + "heightUsed:" + heightUsed);
        if (height * 0.9 <= this.heightUsed) {//控制占总高度的90%
            removeViewAt(0);
        }
    }
}
