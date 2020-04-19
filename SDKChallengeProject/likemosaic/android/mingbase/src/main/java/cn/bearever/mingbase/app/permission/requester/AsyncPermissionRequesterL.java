package cn.bearever.mingbase.app.permission.requester;


import android.os.Handler;

import cn.bearever.mingbase.app.permission.AsyncPermissionRequest;
import cn.bearever.mingbase.app.permission.checker.AsyncPermissionCheckerFactory;
import cn.bearever.mingbase.app.permission.info.Permission;
import cn.bearever.mingbase.app.permission.info.PermissionEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本号低于23的权限请求
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class AsyncPermissionRequesterL extends AsyncPermissionRequester {
    private H mHandler;

    public AsyncPermissionRequesterL() {
        mHandler = new H();
    }

    @Override
    public void request(final AsyncPermissionRequest request) {
        //延迟执行，让用户添加的监听器能够存进request对象里面
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Permission> grantedList = new ArrayList<>();
                List<Permission> deniedList = new ArrayList<>();
                boolean allGranted = true;
                for (String permission : request.getPermissions()) {
                    if (AsyncPermissionCheckerFactory.getChecker().hasPermission(getContext(), request.isHasTest(), permission)) {
                        grantedList.add(new Permission(permission, PermissionEnum.of(permission).getMessage(), true));
                    } else {
                        deniedList.add(new Permission(permission, PermissionEnum.of(permission).getMessage(), false));
                        allGranted = false;
                    }
                }
                if (allGranted && request.getAllGrantedListener() != null) {
                    request.getAllGrantedListener().onResult(grantedList);
                } else if (request.getGrantedListener() != null) {
                    request.getGrantedListener().onResult(grantedList);
                }
                if (deniedList.size() > 0 && request.getDeniedListener() != null) {
                    request.getDeniedListener().onResult(deniedList);
                }
            }
        }, 200);
    }

    private static class H extends Handler {

    }
}
