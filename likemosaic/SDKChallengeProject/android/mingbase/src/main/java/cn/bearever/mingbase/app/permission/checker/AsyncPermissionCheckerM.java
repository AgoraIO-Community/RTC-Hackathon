package cn.bearever.mingbase.app.permission.checker;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;

import cn.bearever.mingbase.app.permission.checker.test.MainPermissionTest;


/**
 * sdk版本大于23时的权限检查
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class AsyncPermissionCheckerM implements AsyncPermissionChecker {
    private static final int MODE_FOREGROUND = 4;

    AsyncPermissionCheckerM() {
    }

    @TargetApi(23)
    @Override
    public boolean hasPermission(Context context, boolean hasTest, String... permissions) {

        //第一次检查，使用常规方式，但是特殊权限和普通权限的检查不一样
        for (String permission : permissions) {
            if (isSpecialPermission(permission)) {
                if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission) && !Settings.canDrawOverlays(context)) {
                    return false;
                } else if (Manifest.permission.WRITE_SETTINGS.equals(permission) && !Settings.System.canWrite(context)) {
                    return false;
                }
            } else if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                //失败,则直接返回结果
                return false;
            }
        }

        //第二次检查，使用安全中心
        AppOpsManager opsManager = null;
        for (String permission : permissions) {
            String ops = AppOpsManager.permissionToOp(permission);
            if (TextUtils.isEmpty(ops)) {
                continue;
            }
            if (opsManager == null) {
                opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            }
            int result = opsManager.checkOpNoThrow(ops, android.os.Process.myUid(), context.getPackageName());
            if (result != AppOpsManager.MODE_ALLOWED && result != MODE_FOREGROUND) {
                return false;
            }
        }

        if (hasTest) {
            //第三次检查，使用test
            for (String permission : permissions) {
                if (!isSpecialPermission(permission) && !test(context, permission)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 运行测试示例
     *
     * @param permission 权限名
     * @return 是否拥有权限
     */
    private boolean test(Context context, String permission) {
        return new MainPermissionTest(context).test(permission);
    }

    /**
     * 是否是特殊权限
     *
     * @param p
     * @return
     */
    private boolean isSpecialPermission(String p) {
        if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(p)) {
            return true;
        } else if (Manifest.permission.WRITE_SETTINGS.equals(p)) {
            return true;
        }
        return false;
    }
}
