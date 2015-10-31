package org.guf.danmaku;

import org.guf.danmaku.bean.BaseDanmaku;

/**
 * Created by Guf on 2015/10/31 0031.
 */
public interface I_Danmaku {

    void addDanmaku(BaseDanmaku danmaku);

    void addDanmaku(BaseDanmaku danmaku, int index);
}
