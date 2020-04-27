package cn.bearever.mingbase.app.view;

import android.view.View;

/**
 * 双击的回调函数
 *
 * @author luoming
 * @date 2020/4/18
 */
public abstract class OnDoubleClickListener implements View.OnClickListener {
    private static final long DOUBLE_TIME = 1000;
    private static long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            onDoubleClick(v);
        }
        lastClickTime = currentTimeMillis;
    }

    public abstract void onDoubleClick(View v);

}
