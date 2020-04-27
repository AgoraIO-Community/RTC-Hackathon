package com.pzj.visualcall.server.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;


/**
 * 输入法帮助类
 */

public class KeyboardHelper {
    private Activity activity;
    private OnKeyboardStatusChangeListener onKeyboardStatusChangeListener;
    private int windowBottom = -1;
    private int keyboardHeight = 0;

    public KeyboardHelper(Activity activity) {
        this.activity = activity;
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onCreate() {
        View content = activity.findViewById(android.R.id.content);
        // content.addOnLayoutChangeListener(listener); 这个方法有时会出现一些问题
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void onDestroy() {
        View content = activity.findViewById(android.R.id.content);
        content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            Log.d("KeyboardHelper", "onGlobalLayout: " + rect + ", " + windowBottom);
            int newBottom = rect.bottom;
            if (windowBottom != -1 && windowBottom != newBottom) {
                if (newBottom < windowBottom) {
                    // keyboard pop
                    keyboardHeight = windowBottom - newBottom;
                    if (onKeyboardStatusChangeListener != null) {
                        onKeyboardStatusChangeListener.onKeyboardPop(keyboardHeight);
                    }
                } else {
                    // keyboard close
                    if (onKeyboardStatusChangeListener != null) {
                        onKeyboardStatusChangeListener.onKeyboardClose(keyboardHeight);
                    }
                }
            }
            windowBottom = newBottom;
        }
    };

    public void setOnKeyboardStatusChangeListener(
            OnKeyboardStatusChangeListener onKeyboardStatusChangeListener) {
        this.onKeyboardStatusChangeListener = onKeyboardStatusChangeListener;
    }

    public interface OnKeyboardStatusChangeListener {

        void onKeyboardPop(int keyboardHeight);

        void onKeyboardClose(int keyboardHeight);
    }
}
