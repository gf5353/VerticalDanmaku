package org.guf.verticaldanmaku;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import org.guf.danmaku.bean.Danmaku;
import org.guf.danmaku.widget.DanmakuLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DanmakuLayout danmakuView;
    int i = 0;
    private Handler handler = new Handler();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        danmakuView = (DanmakuLayout) findViewById(R.id.danmakuView);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (handler == null) return;
                Danmaku danmaku = new Danmaku();
                danmaku.text = "text" + (i++);
                danmaku.duration = 10 * 1000;
                danmaku.padding = 2;
                danmaku.margin = 4;
                danmakuView.addDanmaku(danmaku);

                handler.postDelayed(this, random.nextInt(10) * 800);
            }
        }, 0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Danmaku danmaku = new Danmaku();
                danmaku.text = "游客进来了" + (i++);
                danmaku.padding = 2;
                danmaku.margin = 4;
                danmaku.gravity = Gravity.BOTTOM;
                danmaku.duration = 10 * 1000;
                danmakuView.addDanmaku(danmaku);
                handler.postDelayed(this, random.nextInt(10) * 500);
            }
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        danmakuView.release();
        handler.removeCallbacks(null);
        handler = null;
        super.onDestroy();
    }
}
