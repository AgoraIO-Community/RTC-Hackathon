package cn.bearever.mingbase.app.permission;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * 权限库的入口类，构造一个{@link AsyncPermissionWith}来管理请求。
 * 使用示例
 * <pre>
 * {@code
 *  AsyncPermission.with(activity)
 *                 .request(Manifest.permission.CAMERA)
 *                 .onAllGranted(new OnAsyncPermissionGrantedListener() {
 *                     @Override
 *                     public void onResult(List<Permission> permissions) {
 *                     //所有的权限都获取成功
 *                     }
 *                 }).onDenied(new OnAsyncPermissionDeniedListener() {
 *             @Override
 *             public void onResult(List<Permission> permissions) {
 *             //有权限获取失败
 *             }
 *         });
 * }
 * </pre>
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class AsyncPermission {
    /**
     * 请求指定的权限
     *
     * @param activity 当前的activity
     * @return 权限请求构造类
     */
    public static AsyncPermissionWith with(FragmentActivity activity) {
        return new AsyncPermissionWith(activity);
    }

    /**
     * 请求指定的权限
     *
     * @param fragment 当前的fragemnt
     * @return 权限请求构造类
     */
    public static AsyncPermissionWith with(Fragment fragment) {
        return new AsyncPermissionWith(fragment);
    }
}
