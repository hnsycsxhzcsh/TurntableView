package com.turntableview;

/**
 * Created by HARRY on 2019/1/13.
 */

public interface ITurntableListener {
    void onStart();

    void onEnd(int position, String name);
}
