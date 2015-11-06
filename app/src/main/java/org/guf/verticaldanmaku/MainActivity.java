package org.guf.verticaldanmaku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.guf.danmaku.bean.BaseDanmaku;
import org.guf.danmaku.bean.Danmaku;
import org.guf.danmaku.core.BaseCacheStuffer;
import org.guf.danmaku.widget.DanmakuView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DanmakuView danmakuView;
    int i = 0;
    private Handler handler = new Handler();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        danmakuView = (DanmakuView) findViewById(R.id.danmakuView);
        danmakuView.setCacheStuffer(stuffer);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (handler == null) return;
//                Danmaku danmaku = new Danmaku();
//                danmaku.text = "text" + (i++);
//                danmaku.duration = 10 * 1000;
//                danmaku.textColor = Color.WHITE;
//                danmaku.fillColor = Color.rgb(176, 73, 255);
//                danmaku.padding = 3;
//                danmaku.margin = 4;
//                danmakuView.addDanmaku(danmaku);
//
//                handler.postDelayed(this, random.nextInt(10) * 800);
//            }
//        }, 0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Danmaku danmaku = new Danmaku();
//                danmaku.text = "游客进来了" + (i++);
//                danmaku.padding = 3;
//                danmaku.textColor = Color.WHITE;
//                danmaku.margin = 4;
//                danmaku.fillColor = Color.rgb(255, 102, 2);
//                danmaku.gravity = Gravity.BOTTOM;
//                danmaku.duration = 10 * 1000;
//                danmakuView.addDanmaku(danmaku);
//
                danmakuView.cleanBottom();
                handler.postDelayed(this, random.nextInt(20) * 500);
            }
        }, 1000);
        danmakuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Danmaku danmaku = new Danmaku();
                danmaku.text = "游客进来了" + (i++);
                danmaku.padding = 3;
                danmaku.textColor = Color.WHITE;
                danmaku.margin = 4;
                danmaku.fillColor = Color.rgb(255, 102, 2);
                danmaku.gravity = Gravity.BOTTOM;
                danmaku.duration = 10 * 1000;
                danmakuView.addDanmaku(danmaku);
            }
        });
    }

    public BaseCacheStuffer stuffer = new BaseCacheStuffer() {

        @Override
        public View createBottomDanmaku(Context context, BaseDanmaku danmaku) {
            TextView textView = new TextView(context);
            textView.setText(danmaku.text.toString());
            textView.setTextColor(danmaku.textColor);
            setBackground(textView, danmaku);
            return textView;
        }

        @Override
        public View createDanmaku(Context context, BaseDanmaku danmaku) {
            TextView textView = new TextView(context);
            textView.setText(danmaku.text.toString());
            textView.setTextColor(danmaku.textColor);
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
//            int strokeWidth = 5; // 3dp 边框宽度
            int roundRadius = 15; // 8dp 圆角半径
//            int strokeColor = Color.parseColor("#2E3135");//边框颜色
            int fillColor = danmaku.fillColor;//内部填充颜色
            GradientDrawable gd = new GradientDrawable();//创建drawable
            gd.setColor(fillColor);
            gd.setCornerRadius(roundRadius);
//            gd.setStroke(strokeWidth, strokeColor);
//            view.setBackgroundColor(Color.parseColor("#F0000000"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(gd);
            else
                view.setBackgroundDrawable(gd);
        }
    };

    @Override
    protected void onDestroy() {
        danmakuView.release();
        handler.removeCallbacks(null);
        handler = null;
        super.onDestroy();
    }
}
