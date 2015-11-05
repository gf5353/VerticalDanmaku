package org.guf.danmaku.core;

import android.content.Context;
import android.view.View;

import org.guf.danmaku.bean.BaseDanmaku;

/**
 * Created by Guf on 2015/11/1 0001.
 */
public interface BaseCacheStuffer {

    View createBottomDanmaku(Context context, BaseDanmaku danmaku);

    View createDanmaku(Context context, BaseDanmaku danmaku);

    void updateBottomDanmaku(View view, BaseDanmaku danmaku);

    void setBackground(View view, BaseDanmaku danmaku);
}
