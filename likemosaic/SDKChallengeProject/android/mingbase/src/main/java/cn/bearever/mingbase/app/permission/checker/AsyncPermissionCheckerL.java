package cn.bearever.mingbase.app.permission.checker;

import android.content.Context;

import cn.bearever.mingbase.app.permission.checker.test.MainPermissionTest;


/**
 * sdk版本小于23时的权限检查
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class AsyncPermissionCheckerL implements AsyncPermissionChecker {
    AsyncPermissionCheckerL() {
    }

    @Override
    public boolean hasPermission(Context context, boolean hasTest, String... permissions) {
        //使用test
        for (String permission : permissions) {
            if (hasTest && !test(context, permission)) {
                return false;
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
}
