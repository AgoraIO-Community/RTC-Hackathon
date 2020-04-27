package com.qmt.okhttplibrary;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by czp on 12/03/2019.
 */

public class PhoneUtils {


    public static String phoneModel() {
        return Build.MODEL;
    }

    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            String name = context.getPackageName();
            verCode = context.getPackageManager().getPackageInfo(name, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verCode;
    }

    public static String getVersionName(Context context) {
        String name = context.getPackageName();
        try {
            return context.getPackageManager().getPackageInfo(name, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getSdkInt(Context context) {
        return Build.VERSION.SDK_INT;

    }
}
