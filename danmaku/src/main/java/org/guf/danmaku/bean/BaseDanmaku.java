package org.guf.danmaku.bean;

import android.view.Gravity;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public class BaseDanmaku {
    /**
     * 文本
     */
    public CharSequence text;

    /**
     * 显示时间(毫秒)
     */
    public long time;

    /**
     * 文本颜色
     */
    public int textColor;
    /**
     * 字体大小
     */
    public float textSize = -1;

    /**
     * 文字方向
     */
    public int gravity = Gravity.TOP;

    public int position = 0;
}
