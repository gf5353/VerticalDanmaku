package org.guf.danmaku.core;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import org.guf.danmaku.bean.BaseDanmaku;

/**
 * Created by Guf on 2015/11/1 0001.
 */
public class DefCacheStuffer implements BaseCacheStuffer {

    @Override
    public View createBottomDanmaku(Context context, BaseDanmaku danmaku) {
        TextView textView = new TextView(context);
        textView.setText(danmaku.text.toString());
        textView.setTextColor(Color.RED);
        setBackground(textView, danmaku);
        return textView;
    }

    @Override
    public View createDanmaku(Context context, BaseDanmaku danmaku) {
        TextView textView = new TextView(context);
        textView.setText(danmaku.text.toString());
        textView.setTextColor(Color.WHITE);
        setBackground(textView, danmaku);
        return textView;
    }

    @Override
    public void updateBottomDanmaku(View view, BaseDanmaku danmaku) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(danmaku.text.toString());
        }

    }

    @Override
    public void setBackground(View view, BaseDanmaku danmaku) {
        view.setBackgroundColor(Color.parseColor("#F0000000"));
    }

}
