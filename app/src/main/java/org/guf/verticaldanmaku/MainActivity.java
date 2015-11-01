package org.guf.verticaldanmaku;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import org.guf.danmaku.bean.Danmaku;
import org.guf.danmaku.widget.DanmakuLayout;
import org.guf.danmaku.widget.DanmakuView;

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
                danmaku.padding=2;
                danmaku.margin = 4;
                danmakuView.addDanmaku(danmaku);

                handler.postDelayed(this, random.nextInt(10) * 100);
            }
        }, 0);

        danmakuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Danmaku danmaku = new Danmaku();
                danmaku.text = "小明进来了";
                danmaku.padding=2;
                danmaku.margin = 4;
                danmaku.gravity = Gravity.BOTTOM;
                danmaku.duration = 10 * 1000;
                danmakuView.addDanmaku(danmaku);
            }
        });
    }

    @Override
    protected void onDestroy() {
        danmakuView.release();
        handler.removeCallbacks(null);
        handler = null;
        super.onDestroy();
    }
}
