package com.myturntableview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.turntableview.TurntableView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mBtStart;
    private TurntableView mTurntable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTurntable = findViewById(R.id.turntable);
        mBtStart = findViewById(R.id.bt_start);

        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTurntable.startRotate();
            }
        });

    }
}
