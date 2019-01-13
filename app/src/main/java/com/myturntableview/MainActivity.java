package com.myturntableview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.turntableview.LoggerUtil;
import com.turntableview.TurntableView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TurntableView mTurntable;
    private ImageView mIvGo;
    private Button mBtChangeColor;
    private Button mBtChangeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTurntable = (TurntableView) findViewById(R.id.turntable);
        mIvGo = (ImageView) findViewById(R.id.iv_node);
        mBtChangeColor = (Button) findViewById(R.id.bt_changecolor);
        mBtChangeData = (Button) findViewById(R.id.bt_changedata);

        mBtChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置item的颜色
                changeColors();
            }
        });

        mBtChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改转盘数据
                changeDatas();
            }
        });

        //开始抽奖
        mIvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTurntable.startRotate();
            }
        });

    }

    private void changeColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#ff8584"));
        colors.add(getResources().getColor(R.color.colorAccent));
        colors.add(Color.parseColor("#000000"));
        mTurntable.setBackColor(colors);
    }

    private void changeDatas() {
        int num = 6;
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            names.add("第" + i);
            bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        }
        mTurntable.setDatas(num, names, bitmaps);
    }
}
