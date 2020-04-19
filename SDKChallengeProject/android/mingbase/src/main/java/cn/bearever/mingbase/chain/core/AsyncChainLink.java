package cn.bearever.mingbase.chain.core;

import android.content.Context;
import android.view.View;

import cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycle;
import cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycleListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * 一部链式调用的入口类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/22
 **/
public class AsyncChainLink implements AsyncChainLifeCycleListener {
    private static final String TAG = "AsyncChainLink";
    /**
     * 是否已经开始了
     */
    private boolean isStart = false;
    /**
     * 异步操作的列表
     */
    private List<AsyncChainRunnableWrapper> mRunnableList = new ArrayList<>();
    /**
     * 异步操作错误回调接口
     */
    private AsyncChainErrorCallback mErrorCallback;


    public AsyncChainLink() {

    }

    /**
     * 在原始线程执行一个异步操作
     *
     * @param runnable
     */
    public AsyncChainLink with(AsyncChainRunnable runnable) {
        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
                runnable, AsyncChainRunnableWrapper.ORIGINAL);
        mRunnableList.add(wrapper);
        return this;
    }

    /**
     * 在工作线程，添加一个异步执行的操作
     */
    public AsyncChainLink withWork(AsyncChainRunnable runnable) {
        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
                runnable, AsyncChainRunnableWrapper.WORK);
        mRunnableList.add(wrapper);
        return this;
    }

    /**
     * 在主线程ui(UI线程)，添加一个异步执行的操作
     */
    public AsyncChainLink withMain(AsyncChainRunnable runnable) {
        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
                runnable, AsyncChainRunnableWrapper.MAIN);
        mRunnableList.add(wrapper);
        return this;
    }

    /**
     * 延迟一定时长执行操作，仅支持在工作线程做延时操作
     *
     * @param ms 延迟的时间单位毫秒
     */
    public AsyncChainLink delay(long ms) {
        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
                new AsyncChainRunnable.DefaultAsyncChainRunnable(), AsyncChainRunnableWrapper.WORK, ms);
        mRunnableList.add(wrapper);
        return this;
    }

    /**
     * 在报错的线程处理错误
     *
     * @param errorCallback 执行错误的时候的回调
     */
    public AsyncChainLinkGo error(@NonNull AsyncChainErrorCallback errorCallback) {
        mErrorCallback = errorCallback;
        mErrorCallback.setThread(AsyncChainRunnableWrapper.ORIGINAL);
        return new AsyncChainLinkGo(this);
    }

    /**
     * 在工作线程处理错误
     *
     * @param errorCallback 执行错误的时候的回调
     */
    public AsyncChainLinkGo errorWork(@NonNull AsyncChainErrorCallback errorCallback) {
        mErrorCallback = errorCallback;
        mErrorCallback.setThread(AsyncChainRunnableWrapper.WORK);
        return new AsyncChainLinkGo(this);
    }

    /**
     * 在主线程处理错误
     *
     * @param errorCallback 执行错误的时候的回调
     */
    public AsyncChainLinkGo errorMain(@NonNull AsyncChainErrorCallback errorCallback) {
        mErrorCallback = errorCallback;
        mErrorCallback.setThread(AsyncChainRunnableWrapper.MAIN);
        return new AsyncChainLinkGo(this);
    }

    /**
     * 开始执行
     * 1.获取一个{@link cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycle}实例
     * 2.{@link  cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycle}实例添加当前{@link AsyncChainLink}
     * 3.{@link AsyncChainManager}添加{@link cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycle}实例
     */
    public void go(AppCompatActivity activity) {
        AsyncChainManager.getInstance().getLifeCycle(activity).addLifeCycleListener(this);
        start(null);
    }

    /**
     * 开始执行
     *
     * @param fragment 绑定生命周期的fragment
     */
    public void go(Fragment fragment) {
        AsyncChainManager.getInstance().getLifeCycle(fragment).addLifeCycleListener(this);
        start(null);
    }

    /**
     * 开始执行
     *
     * @param view 绑定生命周期的view
     */
    public void go(View view) {
        AsyncChainManager.getInstance().getLifeCycle(view).addLifeCycleListener(this);
        start(null);
    }

    /**
     * 开始执行
     *
     * @param context 绑定生命周期的context
     */
    public void go(Context context) {
        AsyncChainManager.getInstance().getLifeCycle(context).addLifeCycleListener(this);
        start(null);
    }

    /**
     * 开始执行，生命周期和App一样
     */
    public void go() {
        AsyncChainManager.getInstance().getLifeCycle((AppCompatActivity) null).addLifeCycleListener(this);
        start(null);
    }

    /**
     * 获取异步操作列表
     *
     * @return {@link List<AsyncChainRunnableWrapper>}的列表
     */
    protected List<AsyncChainRunnableWrapper> getRunnableList() {
        return mRunnableList;
    }

    /**
     * 获取异步操作错误时的回调接口
     *
     * @return
     */
    protected AsyncChainErrorCallback getErrorCallback() {
        return mErrorCallback;
    }

    /**
     * 执行下一个操作
     *
     * @param task
     * @param result
     */
    protected void next(AsyncChainTask task, Object result) {
        if (mRunnableList.size() == 0) {
            return;
        }
        AsyncChainRunnableWrapper wrapper = mRunnableList.get(0);
        if (wrapper.runnable.runId.equals(task.getRunId())) {
            isStart = false;
            mRunnableList.remove(wrapper);
            start(result);
        }
    }

    /**
     * 开始一个异步操作
     */
    private void start(Object result) {
        if (isStart) {
            return;
        }
        isStart = true;

        if (mRunnableList.size() > 0) {
            AsyncChainManager.getInstance().run(mRunnableList.get(0), result);
        }
    }

    /**
     * 这个是生命周期的回调方法，请不要主动执行这个方法，避免引起问题
     *
     * @param lifeCycle 被销毁的生命周期类
     */
    @Override
    public void onDestroy(AsyncChainLifeCycle lifeCycle) {
        this.mRunnableList.clear();
    }

}
