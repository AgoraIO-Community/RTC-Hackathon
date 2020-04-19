package cn.bearever.mingbase.app.permission;


import cn.bearever.mingbase.app.permission.listener.OnAsyncPermissionDeniedListener;
import cn.bearever.mingbase.app.permission.listener.OnAsyncPermissionGrantedListener;
import cn.bearever.mingbase.app.permission.requester.AsyncPermissionRequester;
import cn.bearever.mingbase.app.permission.requester.AsyncPermissionRequesterFactory;

/**
 * 权限请求构造类，用于实现链式请求。
 * 权限库请用 {@link AsyncPermission}
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class AsyncPermissionRequest {
    private boolean hasTest = true;
    private AsyncPermissionRequester mRequester;
    private String[] mPermissions;
    private OnAsyncPermissionGrantedListener mOnAsyncPermissionGrantedListener;
    private OnAsyncPermissionGrantedListener mOnAllAsyncPermissionGrantedListener;
    private OnAsyncPermissionDeniedListener mOnAsyncPermissionDeniedListener;

    AsyncPermissionRequest(AsyncPermissionWith with, boolean hasTest) {
        this.hasTest = hasTest;
        if (with.mActivity != null) {
            mRequester = AsyncPermissionRequesterFactory.getInstance().get(with.mActivity);
        } else {
            mRequester = AsyncPermissionRequesterFactory.getInstance().get(with.mFragment);
        }
    }

    /**
     * 设置所有权限请求成功的结果回调监听接口
     *
     * @param listener 包含请求成功的权限列表监听接口
     */
    public AsyncPermissionRequest onAllGranted(OnAsyncPermissionGrantedListener listener) {
        this.mOnAllAsyncPermissionGrantedListener = listener;
        return this;
    }

    /**
     * 设置部分权限权限请求成功的结果回调监听接口
     *
     * @param listener 包含请求成功的权限列表监听接口
     */
    public AsyncPermissionRequest onGranted(OnAsyncPermissionGrantedListener listener) {
        this.mOnAsyncPermissionGrantedListener = listener;
        return this;
    }

    /**
     * 设置权限请求失败的结果回调监听接口
     *
     * @param listener 包含请求失败的权限列表的监听接口
     */
    public AsyncPermissionRequest onDenied(OnAsyncPermissionDeniedListener listener) {
        this.mOnAsyncPermissionDeniedListener = listener;
        return this;
    }

    /**
     * 请求权限
     *
     * @param permissions 需要请求的权限数组
     */
    AsyncPermissionRequest request(String... permissions) {
        mPermissions = permissions;
        mRequester.request(this);
        return this;
    }

    /**
     * 获取请求了哪些权限的数组
     *
     * @return
     * @hide
     */
    public String[] getPermissions() {
        return mPermissions;
    }

    /**
     * 获取所有权限请求成功结果回调对象
     *
     * @return
     * @hide
     */
    public OnAsyncPermissionGrantedListener getAllGrantedListener() {
        return mOnAllAsyncPermissionGrantedListener;
    }

    /**
     * 获取请求成功结果回调对象
     *
     * @return
     * @hide
     */
    public OnAsyncPermissionGrantedListener getGrantedListener() {
        return mOnAsyncPermissionGrantedListener;
    }

    /**
     * 获取请求失败结果回调对象
     *
     * @return
     * @hide
     */
    public OnAsyncPermissionDeniedListener getDeniedListener() {
        return mOnAsyncPermissionDeniedListener;
    }

    /**
     * 是否使用测试类
     *
     * @return
     */
    public boolean isHasTest() {
        return hasTest;
    }
}
