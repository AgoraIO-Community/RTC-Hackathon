package cn.bearever.mingbase.app.permission.requester;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import cn.bearever.mingbase.app.permission.AsyncPermissionRequest;
import cn.bearever.mingbase.app.permission.checker.AsyncPermissionChecker;
import cn.bearever.mingbase.app.permission.checker.AsyncPermissionCheckerFactory;
import cn.bearever.mingbase.app.permission.info.Permission;
import cn.bearever.mingbase.app.permission.info.PermissionEnum;
import cn.bearever.mingbase.app.permission.util.Go2SettingActivityUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

/**
 * 版本号大于23的权限请求实现
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class AsyncPermissionRequesterM extends AsyncPermissionRequester {
    private final Object lock = new Object();
    private AsyncPermissionChecker mChecker;
    private List<AsyncPermissionRequest> mRequestList = new ArrayList<>();
    private AsyncPermissionRequest mRequestTemp;
    private List<Permission> mPermissionListTemp = new ArrayList<>();
    private final int REQUEST_PERMISSION_CODE = 29;
    private final int REQUEST_SETTING_CODE = 31;

    public AsyncPermissionRequesterM() {
        mChecker = AsyncPermissionCheckerFactory.getChecker();
    }

    /**
     * 请求权限
     *
     * @param request 权限请求的对象
     */
    @TargetApi(23)
    @Override
    public void request(AsyncPermissionRequest request) {
        synchronized (lock) {
            //使用列表缓存请求对象，一个请求一个请求的执行
            mRequestList.add(request);
            if (mRequestList.size() == 1) {
                startRequestPermissions();
            }
        }
    }

    /**
     * 开始执行权限请求
     */
    private void startRequestPermissions() {
        if (mRequestList.size() > 0) {
            mRequestTemp = mRequestList.remove(0);
            requestPermissions(mRequestTemp.getPermissions(), REQUEST_PERMISSION_CODE);
        }
    }

    /**
     * 权限请求的返回接口，对每一个权限进行判断：
     * 是否设置为了不再显示授权弹窗
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            mPermissionListTemp.clear();
            for (String pm : permissions) {
                Permission permission = new Permission(pm, PermissionEnum.of(pm).getMessage(),
                        mChecker.hasPermission(getContext(), mRequestTemp.isHasTest(), pm));
                mPermissionListTemp.add(permission);
            }
            //最后的挣扎，自定义弹窗告诉用户去设置页面打开权限
            checkHasPermission();
        }
    }

    /**
     * 如果有权限没有授权的话，就弹窗提示用去设置页面打开权限
     */
    private void checkHasPermission() {
        boolean grand = true;
        List<Permission> deniedList = new ArrayList<>();
        for (Permission permission : mPermissionListTemp) {
            if (!permission.isGrand()) {
                deniedList.add(permission);
                grand = false;
            }
        }
        if (!grand) {
            for (Permission permission : deniedList) {
                if (!shouldShowRequestPermissionRationale(permission.getName())) {
                    showSettingDialog(deniedList);
                    return;
                }
            }
        }
        //权限请求结束
        requestPermissionsFinish();
        //开启下一个请求
        startRequestPermissions();
    }

    private AlertDialog mDialog;

    /**
     * 显示去设置页面的弹窗
     *
     * @param deniedList
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void showSettingDialog(List<Permission> deniedList) {
        String readPermissions = getReadPermissions(deniedList);
        mDialog = new AlertDialog.Builder(getContext())
                .setTitle("权限获取失败")
                .setMessage("缺少" + readPermissions + "，您可以在设置页面开启权限")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //尝试被拒绝
                        mDialog.dismiss();
                        //权限请求结束
                        requestPermissionsFinish();
                        //开启下一个请求
                        startRequestPermissions();
                    }
                })
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Go2SettingActivityUtil.gotoPermissionSetting(AsyncPermissionRequesterM.this, REQUEST_SETTING_CODE);
                    }
                })
                .create();
        mDialog.show();
    }

    /**
     * 将权限代码转化为用户理解的权限信息
     *
     * @param deniedList
     * @return
     */
    private String getReadPermissions(List<Permission> deniedList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deniedList.size(); i++) {
            Permission permission = deniedList.get(i);
            sb.append(permission.getMessage());
            if (i < deniedList.size() - 1) {
                sb.append("、");
            }
        }
        return sb.toString();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTING_CODE) {
            //检查权限
            List<Permission> newPermissionList = new ArrayList<>();
            for (Permission permission : mPermissionListTemp) {
                permission.setGrand(mChecker.hasPermission(getContext(), mRequestTemp.isHasTest(), permission.getName()));
                newPermissionList.add(permission);
            }
            mPermissionListTemp.clear();
            mPermissionListTemp.addAll(newPermissionList);
            //行动结束
            requestPermissionsFinish();
            //开启下一个请求
            startRequestPermissions();
        }
    }

    /**
     * 一个权限请求行为结束
     */
    private void requestPermissionsFinish() {
        //结果回调
        boolean grand = true;
        List<Permission> grantedList = new ArrayList<>();
        List<Permission> deniedList = new ArrayList<>();
        for (Permission permission : mPermissionListTemp) {
            if (!permission.isGrand()) {
                grand = false;
                deniedList.add(permission);
            } else {
                grantedList.add(permission);
            }
        }
        if (grand && mRequestTemp.getAllGrantedListener() != null) {
            mRequestTemp.getAllGrantedListener().onResult(grantedList);
        } else if (grantedList.size() > 0 && mRequestTemp.getGrantedListener() != null) {
            mRequestTemp.getGrantedListener().onResult(grantedList);
        }
        if (deniedList.size() > 0 && mRequestTemp.getDeniedListener() != null) {
            mRequestTemp.getDeniedListener().onResult(deniedList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRequestList.clear();
        mRequestTemp = null;
    }
}
