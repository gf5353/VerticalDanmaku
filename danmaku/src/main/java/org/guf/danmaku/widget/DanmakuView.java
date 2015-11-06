package org.guf.danmaku.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

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
    public void cleanBottom() {
        int child = getChildCount();
        if (child == 1) {
            View view = getChildAt(child - 1);
            if (view != null) {
                BaseDanmaku danmaku = (BaseDanmaku) view.getTag();
                if (danmaku != null && danmaku.gravity == Gravity.BOTTOM) {
                    removeView(view);
                }
            }
        }
    }

    @Override
    public void setCacheStuffer(BaseCacheStuffer stuffer) {
        this.stuffer = stuffer;
    }


    @Override
    public void drawDanmaku(BaseDanmaku danmaku) {
        int childCount = getChildCount();
        View view = null;
        if (lastGravity == Gravity.BOTTOM && danmaku.gravity == Gravity.BOTTOM) {
            view = getChildAt(childCount - 1);
            if (view != null && stuffer != null) {
                stuffer.updateBottomDanmaku(view, danmaku);
                return;
            }
        } else if (childCount > 1 && lastGravity == Gravity.BOTTOM && danmaku.gravity == Gravity.TOP) {//防止底部进入房间的item滚动到上方
            this.removeViewAt(childCount - 1);
        }
        if (view == null && stuffer != null) {
            if (danmaku.gravity == Gravity.TOP) {
                view = stuffer.createDanmaku(getContext(), danmaku);
            } else if (danmaku.gravity == Gravity.BOTTOM) {
                view = stuffer.createBottomDanmaku(getContext(), danmaku);
            }
            view.setTag(danmaku);
            this.addView(view);
            view.setPadding(danmaku.padding, danmaku.padding, danmaku.padding, danmaku.padding);
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            if (params != null) {
                params.setMargins(danmaku.margin, danmaku.margin, danmaku.margin, danmaku.margin);
                view.setLayoutParams(params);
            }
        }
        if (danmaku.gravity != Gravity.BOTTOM) {
            Animation alphaAnimation = new AlphaAnimation(1f, 0f);
            alphaAnimation.setDuration(danmaku.duration);//设置动画时间
            alphaAnimation.setAnimationListener(new AnimEndListener(view));
            view.setAnimation(alphaAnimation);
            view.startAnimation(alphaAnimation);
        }
        lastGravity = danmaku.gravity;
    }


    private class AnimEndListener implements Animation.AnimationListener {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (target == null) return;
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

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
