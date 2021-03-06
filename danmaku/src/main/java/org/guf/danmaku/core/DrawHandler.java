package org.guf.danmaku.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.guf.danmaku.bean.BaseDanmaku;

import java.lang.ref.WeakReference;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public class DrawHandler extends Handler {
    private WeakReference<Activity> mActivityReference;
    private IDanmakuViewController controller;

    public static final int UPDATE = 2;

    public DrawHandler(Context context, IDanmakuViewController controller) {
        this.controller = controller;
        mActivityReference = new WeakReference<Activity>((Activity) context);
    }


    public void addItem(BaseDanmaku item) {
        Message msg = Message.obtain();
        msg.what = UPDATE;
        msg.obj = item;
        sendMessage(msg);
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Activity activity = mActivityReference.get();
        if (activity == null)
            return;
        switch (msg.what) {
            case UPDATE:
                if (controller != null) {
                    controller.drawDanmaku((BaseDanmaku) msg.obj);
                }
                break;
        }
    }

}