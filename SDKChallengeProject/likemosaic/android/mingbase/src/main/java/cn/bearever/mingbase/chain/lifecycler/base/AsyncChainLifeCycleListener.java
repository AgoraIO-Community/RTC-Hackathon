package cn.bearever.mingbase.chain.lifecycler.base;


import android.app.Activity;

import androidx.fragment.app.Fragment;

/**
 * 生命周期状态的基类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/24
 **/
public interface AsyncChainLifeCycleListener {

    /**
     * 当 {@link Fragment#onDestroy()}} 或者 {@link
     * Activity#onDestroy()}  的时候执行，表示依附的对象销毁了
     *
     * @param lifeCycle 被销毁的生命周期类
     */
    void onDestroy(AsyncChainLifeCycle lifeCycle);
}
