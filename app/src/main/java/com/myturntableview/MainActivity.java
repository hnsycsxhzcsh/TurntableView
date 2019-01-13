package com.myturntableview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.turntableview.LoggerUtil;
import com.turntableview.TurntableView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mBtStart;
    private TurntableView mTurntable;
    private ImageView mIvGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTurntable = (TurntableView) findViewById(R.id.turntable);
        mBtStart = (Button) findViewById(R.id.bt_start);
        mIvGo = (ImageView) findViewById(R.id.iv_node);

        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTurntable.startRotate();
            }
        });

        mIvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                float randomPositionPro = mTurntable.getRandomPositionPro();
//                LoggerUtil.i(MainActivity.this, randomPositionPro);
                LoggerUtil.i(MainActivity.this, mTurntable.getRandom(7));
            }
        });

    }
}
