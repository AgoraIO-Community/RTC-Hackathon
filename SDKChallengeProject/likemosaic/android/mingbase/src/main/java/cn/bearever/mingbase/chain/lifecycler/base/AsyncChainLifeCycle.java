package cn.bearever.mingbase.chain.lifecycler.base;

import java.util.List;

/**
 * 生命周期接口类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/24
 **/
public interface AsyncChainLifeCycle {
    /**
     * 添加生命周期状态监听，生命周期的状态有start、onNext、destory。
     *
     * @param listener 生命周期状态监听接口
     */
    void addLifeCycleListener(AsyncChainLifeCycleListener listener);

    /**
     * 移除生命周期状态监听
     *
     * @param listener 生命周期状态监听接口
     */
    void removeLifeCycleListener(AsyncChainLifeCycleListener listener);

    /**
     * 获取所以的生命周期状态监听接口
     * @return {@link List<AsyncChainLifeCycleListener>}
     */
    List<AsyncChainLifeCycleListener> getLifeCycleListeners();

}
