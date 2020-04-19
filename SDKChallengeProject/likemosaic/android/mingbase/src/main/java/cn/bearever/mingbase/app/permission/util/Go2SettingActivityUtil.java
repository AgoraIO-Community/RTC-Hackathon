package cn.bearever.mingbase.app.permission.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import cn.bearever.mingbase.BuildConfig;

/**
 * 前往权限设置页面的工具类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/31
 **/
public class Go2SettingActivityUtil {
    /**
     * 前往设置页面
     *
     * @param fragment
     */
    public static void gotoPermissionSetting(Fragment fragment, int code) {
        //手机厂商
        String brand = Build.BRAND;
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission(fragment, code);
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(fragment, code);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission(fragment, code);
        } else {
            fragment.startActivityForResult(getAppDetailSettingIntent(fragment.getContext()), code);
        }
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Fragment fragment, int code) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", fragment.getContext().getPackageName());
            fragment.startActivityForResult(localIntent, code);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", fragment.getContext().getPackageName());
                fragment.startActivityForResult(localIntent, code);
            } catch (Exception e1) { // 否则跳转到应用详情
                fragment.startActivityForResult(getAppDetailSettingIntent(fragment.getContext()), code);
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Fragment fragment, int code) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            fragment.startActivityForResult(intent, code);
        } catch (Exception e) {
            e.printStackTrace();
            fragment.startActivityForResult(getAppDetailSettingIntent(fragment.getContext()), code);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Fragment fragment, int code) {
        try {
            Intent intent = new Intent();
            //华为权限管理
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            fragment.startActivityForResult(intent, code);
        } catch (Exception e) {
            e.printStackTrace();
            fragment.startActivityForResult(getAppDetailSettingIntent(fragment.getContext()), code);
        }
    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return localIntent;
    }
}
