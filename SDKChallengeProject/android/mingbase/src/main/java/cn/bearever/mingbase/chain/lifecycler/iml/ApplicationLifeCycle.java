package cn.bearever.mingbase.chain.lifecycler.iml;


import cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycle;
import cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * app运行期间都存在的生命周期实现
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/24
 **/
public class ApplicationLifeCycle implements AsyncChainLifeCycle {
    private List<AsyncChainLifeCycleListener> mLifeCycleListenerList = new ArrayList<>();

    @Override
    public void addLifeCycleListener(AsyncChainLifeCycleListener listener) {
        mLifeCycleListenerList.remove(listener);
        mLifeCycleListenerList.add(listener);
    }

    @Override
    public List<AsyncChainLifeCycleListener> getLifeCycleListeners() {
        return mLifeCycleListenerList;
    }
    @Override
    public void removeLifeCycleListener(AsyncChainLifeCycleListener listener) {
        mLifeCycleListenerList.remove(listener);
    }
}
