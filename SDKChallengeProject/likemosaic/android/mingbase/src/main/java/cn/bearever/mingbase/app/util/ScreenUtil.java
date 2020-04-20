package cn.bearever.mingbase.app.util;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * @author luoming
 * @date 2020/4/20
 */
public class ScreenUtil {

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.y;
    }
}
