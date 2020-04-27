package cn.bearever.mingbase.app.permission.checker;

import android.os.Build;

/**
 * 用来获取权限检查器的工厂
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/14
 **/
public class AsyncPermissionCheckerFactory {
    private static AsyncPermissionChecker mChecker;

    /**
     * 获取权限检查器
     *
     * @return
     */
    public static AsyncPermissionChecker getChecker() {
        if (mChecker == null) {
            synchronized (AsyncPermissionCheckerFactory.class) {
                if (mChecker == null) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        mChecker = new AsyncPermissionCheckerL();
                    } else {
                        mChecker = new AsyncPermissionCheckerM();
                    }
                }
            }
        }
        return mChecker;
    }
}
