package cn.bearever.mingbase.app.permission.checker;


import android.content.Context;

/**
 * 检查权限的接口
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public interface AsyncPermissionChecker {
    /**
     * 判断应用是否拥有某个权限
     *
     * @param context     务必使用ApplicationContext
     * @param hasTest     是否使用测试类
     * @param permissions 权限数组
     * @return
     */
    boolean hasPermission(Context context, boolean hasTest, String... permissions);
}
