package cn.bearever.mingbase.app.permission.listener;


import cn.bearever.mingbase.app.permission.info.Permission;

import java.util.List;

/**
 * 权限请求的结果回调接口
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public interface OnAsyncPermissionGrantedListener {
    /**
     * 请求权限的结果
     *
     * @param permissions 授权成功的权限列表
     */
    void onResult(List<Permission> permissions);
}
