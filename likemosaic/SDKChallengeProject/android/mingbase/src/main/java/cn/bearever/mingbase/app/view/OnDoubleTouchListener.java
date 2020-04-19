package cn.bearever.mingbase.app.view;

import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

/**
 * 双击的回调函数
 *
 * @author luoming
 * @date 2020/4/18
 */
public abstract class OnDoubleTouchListener implements View.OnTouchListener {
    private static final long DOUBLE_TIME = 1000;
    private static long lastClickTime = 0;
    private Pair<Float, Float> mPoint;
    private float MAX_DISDANCE = 50;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            long currentTimeMillis = System.currentTimeMillis();
            if (mPoint != null) {
                if (Math.abs(mPoint.first - event.getX()) <= MAX_DISDANCE && Math.abs(mPoint.second - event.getY()) <= MAX_DISDANCE) {
                    if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
                        onDoubleClick(v, event.getX(), event.getY());
                    }
                }
            }
            mPoint = new Pair<>(event.getX(), event.getY());
            lastClickTime = currentTimeMillis;
        }
        return true;
    }

    public abstract void onDoubleClick(View v, float x, float y);

}
