package org.guf.danmaku;

import org.guf.danmaku.bean.BaseDanmaku;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public interface I_Danmaku {

    void addDanmaku(BaseDanmaku danmaku);

    void addDanmakuBottom(BaseDanmaku danmaku);


    // ------------- 播放控制 -------------------

    public void seekTo(Long ms);

    public void start();

    public void start(long postion);

    public void stop();

    public void pause();

    public void resume();

    public void release();

    public void toggle();

    public void show();

    public void hide();
}
