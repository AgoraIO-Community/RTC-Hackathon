package com.qmt.okhttplibrary.network.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.qmt.okhttplibrary.R;

/**
 *
 *
 * @author wlk
 * @date 2017/2/28
 */
public class MyUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp单位
     * @return px（像素）单位
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 弹出吐司
     * @param mContext 上下文
     * @param str s
     */
    public static void toastLong(Context mContext, String str){
        if (!TextUtils.isEmpty(str)) {
            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, mContext.getString(R.string.err), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 弹出吐司
     * @param mContext 上下文
     * @param str s
     */
    public static void toastShort(Context mContext, String str){
        if (!TextUtils.isEmpty(str)) {
            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, mContext.getString(R.string.err), Toast.LENGTH_SHORT).show();
        }
    }
}
