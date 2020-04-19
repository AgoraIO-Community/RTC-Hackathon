package cn.bearever.mingbase.app.util;

import android.content.Context;
import android.widget.Toast;

import cn.bearever.mingbase.app.BaseApplication;

/**
 * toast 工具类
 *
 * @author luoming
 * @date 2020/4/12
 */
public class ToastUtil {

    /**
     * 显示toast
     *
     * @param text
     */
    public static void show(String text) {
        Toast.makeText(BaseApplication.getApplication(), text, Toast.LENGTH_SHORT).show();
    }
}
