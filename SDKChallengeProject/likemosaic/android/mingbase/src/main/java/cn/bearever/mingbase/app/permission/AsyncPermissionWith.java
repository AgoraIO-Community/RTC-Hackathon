package cn.bearever.mingbase.app.permission;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import cn.bearever.mingbase.app.permission.checker.AsyncPermissionCheckerFactory;


/**
 * 权限请求构造类，用于实现链式请求。
 * 权限库请用 {@link AsyncPermission}
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class AsyncPermissionWith {
    FragmentActivity mActivity;
    Fragment mFragment;

    AsyncPermissionWith(@NonNull FragmentActivity activity) {
        this.mActivity = activity;
    }

    AsyncPermissionWith(@NonNull Fragment fragment) {
        this.mFragment = fragment;
    }

    /**
     * 请求权限
     *
     * @param permissions 需要请求的权限数组
     */
    public AsyncPermissionRequest request(String... permissions) {
        AsyncPermissionRequest request = new AsyncPermissionRequest(this, true);
        return request.request(permissions);
    }

    /**
     * 请求权限,不使用测试类检查权限
     *
     * @param permissions 需要请求的权限数组
     */
    public AsyncPermissionRequest requestNoTest(String... permissions) {
        AsyncPermissionRequest request = new AsyncPermissionRequest(this, false);
        return request.request(permissions);
    }

    /**
     * 检查是否拥有某一个权限,只有所有权限都申请到了才会返回true
     *
     * @return 是否拥有某一个权限
     */
    public boolean check(String... permissions) {
        return AsyncPermissionCheckerFactory.getChecker().hasPermission(getContext(), true, permissions);
    }

    /**
     * 检查是否拥有某一个权限,只有所有权限都申请到了才会返回true
     * 不使用测试类检查权限
     *
     * @return 是否拥有某一个权限
     */
    public boolean checkNoTest(String... permissions) {
        return AsyncPermissionCheckerFactory.getChecker().hasPermission(getContext(), false, permissions);
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        } else if (mFragment != null) {
            return mFragment.getContext();
        }
        return null;
    }
}
