package cn.bearever.mingbase.app.permission.requester;


import android.annotation.SuppressLint;
import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理{@link AsyncPermissionRequester}的单例类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
@SuppressLint("RestrictedApi")
public class AsyncPermissionRequesterFactory implements GenericLifecycleObserver {
    private static AsyncPermissionRequesterFactory instance;
    /**
     * 保存Fragment和权限请求Fragment映射关系的map
     */
    private Map<LifecycleOwner, AsyncPermissionRequester> mFragmentMap = new HashMap<>();

    /**
     * 标记权限Fragment的标签
     */
    private static final String TAG_FRAGMENT = "AsyncPermissionFragment2019";

    public static AsyncPermissionRequesterFactory getInstance() {
        if (instance == null) {
            synchronized (AsyncPermissionRequesterFactory.class) {
                if (instance == null) {
                    instance = new AsyncPermissionRequesterFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 获取当前页面的AsyncPermissionFragment对象
     *
     * @param fragment 权限请求fragment的宿主
     */
    public AsyncPermissionRequester get(Fragment fragment) {
        fragment.getLifecycle().addObserver(this);
        AsyncPermissionRequester tagFragment = mFragmentMap.get(fragment);
        if (tagFragment == null) {
            tagFragment = createRequester();
            FragmentManager manager = fragment.getChildFragmentManager();
            manager.beginTransaction().add(tagFragment, TAG_FRAGMENT).commitNowAllowingStateLoss();
            mFragmentMap.put(fragment, tagFragment);
        }
        return tagFragment;
    }

    /**
     * 获取当前页面的AsyncPermissionFragment对象
     *
     * @param activity 权限请求activity的宿主
     */
    public AsyncPermissionRequester get(FragmentActivity activity) {
        activity.getLifecycle().addObserver(this);
        AsyncPermissionRequester tagFragment = mFragmentMap.get(activity);
        if (tagFragment == null) {
            tagFragment = createRequester();
            FragmentManager manager = activity.getSupportFragmentManager();
            manager.beginTransaction().add(tagFragment, TAG_FRAGMENT).commitNowAllowingStateLoss();
            mFragmentMap.put(activity, tagFragment);
        }
        return tagFragment;
    }

    /**
     * 更新Android版本生成不同的请求器
     *
     * @return
     */
    private AsyncPermissionRequester createRequester() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new AsyncPermissionRequesterL();
        } else {
            return new AsyncPermissionRequesterM();
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            //移除引用
            mFragmentMap.remove(source);
        }
    }
}
