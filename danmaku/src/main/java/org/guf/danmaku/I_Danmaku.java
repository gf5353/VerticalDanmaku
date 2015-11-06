package org.guf.danmaku;

import org.guf.danmaku.bean.BaseDanmaku;
import org.guf.danmaku.core.BaseCacheStuffer;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public interface I_Danmaku {

    void addDanmaku(BaseDanmaku danmaku);

    /***
     * 没数据的时候去手动清除最后一条
     */
    void cleanBottom();


    void setCacheStuffer(BaseCacheStuffer stuffer);
    // ------------- 播放控制 -------------------

//    public void seekTo(Long ms);
//
//    public void start();
//
//    public void start(long postion);

    public void stop();

//    public void pause();
//
//    public void resume();

    public void release();

//    public void toggle();
//
//    public void show();
//
//    public void hide();
}
