package cn.bearever.mingbase.chain.core;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycle;
import cn.bearever.mingbase.chain.lifecycler.base.AsyncChainLifeCycleListener;
import cn.bearever.mingbase.chain.lifecycler.iml.ApplicationLifeCycle;
import cn.bearever.mingbase.chain.lifecycler.iml.FragmentLifeCycle;
import cn.bearever.mingbase.chain.lifecycler.iml.SupportFragmentLifeCycle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * 异步链式的流程控制类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/23
 **/
@TargetApi(11)
public class AsyncChainManager implements AsyncChainLifeCycleListener {
    private static final String TAG = "AsyncChainManager";
    private static AsyncChainManager instance;
    /**
     * 使用fragment的生命周期列表
     */
    private Map<String, AsyncChainLifeCycle> mFragmentLifeCycleMap = new HashMap<>();
    /**
     * 使用application的生命周期
     */
    private static AsyncChainLifeCycle mApplicationLifeCycle;
    /**
     * Fragment的TAG
     */
    private static final String FRAGMENT_TAG = "AsyncChainManagerFragmentOK";

    /**
     * 以下代码参考自{@link android.os.AsyncTask}
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncChainManager #" + mCount.getAndIncrement());
        }
    };

    /**
     * 线程池
     */
    private static final ScheduledExecutorService THREAD_POOL_EXECUTOR;

    static {
        THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(
                MAXIMUM_POOL_SIZE, THREAD_FACTORY);
    }

    /**
     * 用来切换主线程的handler
     */
    private static InternalHandler MAIN_HANDLER;
    private static int MSG_RUN_MAIN = 256;
    private static int MSG_ERROR_MAIN = 258;

    synchronized static AsyncChainManager getInstance() {
        if (instance == null) {
            synchronized (AsyncChainManager.class) {
                if (instance == null) {
                    instance = new AsyncChainManager();
                }
            }
        }
        return instance;
    }

    private AsyncChainManager() {
        mApplicationLifeCycle = new ApplicationLifeCycle();
    }

    /**
     * 获取生命周期实现
     *
     * @param context context
     * @return
     */
    AsyncChainLifeCycle getLifeCycle(Context context) {
        if (context == null) {
            return mApplicationLifeCycle;
        } else if (context instanceof AppCompatActivity) {
            return getLifeCycle((AppCompatActivity) context);
        } else if (context instanceof Application) {
            return mApplicationLifeCycle;
        }
        return mApplicationLifeCycle;
    }

    /**
     * 获取生命周期实现
     *
     * @return
     */
    AsyncChainLifeCycle getLifeCycle(AppCompatActivity activity) {
        if (activity == null) {
            return mApplicationLifeCycle;
        } else if (activity.isFinishing()) {
            return mApplicationLifeCycle;
        } else if (activity instanceof FragmentActivity) {
            return getSupportFragmentLifeCycle(activity, ((FragmentActivity) activity).getSupportFragmentManager(), null);
        } else {
            return getFragmentLifeCycle(activity, activity.getSupportFragmentManager(), null);
        }
    }


    /**
     * 获取生命周期实现
     *
     * @param fragment
     * @return
     */
    AsyncChainLifeCycle getLifeCycle(Fragment fragment) {
        if (fragment == null) {
            return mApplicationLifeCycle;
        } else {
            return getLifeCycle(fragment.getActivity());
        }
    }

    /**
     * 获取生命周期实现
     *
     * @return
     */
    AsyncChainLifeCycle getLifeCycle(View view) {
        AppCompatActivity activity = findActivity(view.getContext());
        if (activity == null) {
            return getLifeCycle(view.getContext().getApplicationContext());
        }
        return getLifeCycle(activity);
    }

    private AppCompatActivity findActivity(@NonNull Context context) {
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }

    /**
     * 获取一个{@link cn.bearever.mingbase.chain.lifecycler.iml.SupportFragmentLifeCycle}
     *
     * @param activity        宿主activity
     * @param fragmentManager 用于管理Fragment的{@link FragmentManager}
     * @param parentFragment  宿主fragment
     * @return 返回 SupportFragmentLifeCycle
     */
    private AsyncChainLifeCycle getSupportFragmentLifeCycle(
            AppCompatActivity activity,
            FragmentManager fragmentManager,
            Fragment parentFragment) {
        String key = parentFragment == null ? activity.toString() : parentFragment.toString();
        AsyncChainLifeCycle lifeCycle = mFragmentLifeCycleMap.get(key);
        if (lifeCycle instanceof SupportFragmentLifeCycle) {
            return lifeCycle;
        }

        SupportFragmentLifeCycle fragmentLifeCycle = (SupportFragmentLifeCycle) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragmentLifeCycle == null) {
            fragmentLifeCycle = new SupportFragmentLifeCycle();
            mFragmentLifeCycleMap.put(key, fragmentLifeCycle);
            fragmentLifeCycle.addLifeCycleListener(this);
            fragmentManager.beginTransaction().add(fragmentLifeCycle, FRAGMENT_TAG).commitAllowingStateLoss();
        }

        return fragmentLifeCycle;
    }

    /**
     * 获取一个{@link FragmentLifeCycle}
     *
     * @param activity        宿主activity
     * @param fragmentManager 用于管理Fragment的{@link FragmentManager}
     * @param parentFragment  宿主fragment
     * @return 返回 FragmentLifeCycle
     */
    private AsyncChainLifeCycle getFragmentLifeCycle(
            AppCompatActivity activity,
            FragmentManager fragmentManager,
            Fragment parentFragment) {
        String key = parentFragment == null ? activity.toString() : parentFragment.toString();
        AsyncChainLifeCycle lifeCycle = mFragmentLifeCycleMap.get(key);
        if (lifeCycle instanceof FragmentLifeCycle) {
            return lifeCycle;
        }
        FragmentLifeCycle fragmentLifeCycle = (FragmentLifeCycle) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragmentLifeCycle == null) {
            fragmentLifeCycle = new FragmentLifeCycle();
            mFragmentLifeCycleMap.put(key, fragmentLifeCycle);
            fragmentLifeCycle.addLifeCycleListener(this);
            fragmentManager.beginTransaction().add(fragmentLifeCycle, FRAGMENT_TAG).commitAllowingStateLoss();
        }
        return fragmentLifeCycle;
    }

    /**
     * 执行一个一部操作
     *
     * @param wrapper 需要执行的异步操作
     * @param result  已经完成的异步操作的结果，可以为null
     */
    public synchronized void run(AsyncChainRunnableWrapper wrapper, Object result) {
        if (wrapper == null) {
            //如果wrapper是null，那这个异步链也就不需要了
            return;
        }
        if (wrapper.thread == AsyncChainRunnableWrapper.ORIGINAL) {
            runOriginal(wrapper, result);
        } else if (wrapper.thread == AsyncChainRunnableWrapper.MAIN) {
            runMain(wrapper, result);
        } else if (wrapper.thread == AsyncChainRunnableWrapper.WORK) {
            runWork(wrapper, result);
        }
    }

    /**
     * 在原始线程里执行
     *
     * @param wrapper 异步行为包装类
     * @param result  上一步的结果
     */
    private void runOriginal(AsyncChainRunnableWrapper wrapper, Object result) {
        AsyncChainTask task = wrapper.runnable.getAsyncChainTask();
        task.setLastResult(result);
        try {
            wrapper.runnable.run(task);
        } catch (Exception e) {
            onError(new AsyncChainError(e.getMessage(), e, task));
        }
    }

    /**
     * 获取主线程的handler
     *
     * @return
     */
    private static InternalHandler getMainHandler() {
        synchronized (AsyncChainManager.class) {
            if (MAIN_HANDLER == null) {
                MAIN_HANDLER = new InternalHandler(Looper.getMainLooper());
            }
            return MAIN_HANDLER;
        }
    }


    /**
     * 在主线程执行异步操作
     */
    private void runMain(final AsyncChainRunnableWrapper wrapper, final Object result) {
        if (MAIN_HANDLER == null) {
            MAIN_HANDLER = getMainHandler();
        }
        MAIN_HANDLER.obtainMessage(MSG_RUN_MAIN, new AsyncChainRunnableHandlerObj(wrapper.runnable, result))
                .sendToTarget();
    }

    /**
     * 在工作线程执行异步操作
     */
    private void runWork(final AsyncChainRunnableWrapper wrapper, final Object result) {
        THREAD_POOL_EXECUTOR.schedule(new Runnable() {
            @Override
            public void run() {
                AsyncChainTask task = wrapper.runnable.getAsyncChainTask();
                task.setLastResult(result);
                try {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    wrapper.runnable.run(task);
                } catch (Exception e) {
                    onError(new AsyncChainError(e.getMessage(), e, task));
                }
            }
        }, wrapper.delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取接下来的异步操作
     *
     * @param task   当前执行的异步操作控制器
     * @param result 上一次操作的结果，可能为null
     * @return 当前操作流的下一个异步操作
     **/
    void onNext(AsyncChainTask task, Object result) {
        if (task.getRunId() == null) {
            return;
        }
        AsyncChainLink link = findAsyncChainLink(task);
        if (link != null) {
            link.next(task, result);
        }
    }

    /**
     * 一个异步操作出现了错误，会回调给错误监听接口{@link AsyncChainErrorCallback},
     * 需要让错误回调在指定的线程执行，并且清空错误所在异步链
     *
     * @param error
     */
    void onError(AsyncChainError error) {
        AsyncChainLink link = findAsyncChainLink(error.getAsyncChainTask());
        if (link == null) {
            return;
        }
        //找到错误监听回调
        AsyncChainErrorCallback errorCallback = link.getErrorCallback();
        if (errorCallback == null) {
            return;
        }
        //在指定的线程执行
        if (errorCallback.getThread() == AsyncChainRunnableWrapper.MAIN) {
            errorMain(errorCallback, error);
        } else if (errorCallback.getThread() == AsyncChainRunnableWrapper.ORIGINAL) {
            error(errorCallback, error);
        } else if (errorCallback.getThread() == AsyncChainRunnableWrapper.WORK) {
            errorWork(errorCallback, error);
        }
        //清空错误所在的异步链
        link.getRunnableList().clear();
    }

    /**
     * 在原始线程执行错误回调
     *
     * @param errorCallback
     */
    private void error(AsyncChainErrorCallback errorCallback, AsyncChainError error) {
        try {
            errorCallback.error(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在工作线程执行错误回调
     *
     * @param errorCallback
     */
    private void errorWork(final AsyncChainErrorCallback errorCallback, final AsyncChainError error) {
        THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    errorCallback.error(error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 在主线程执行错误回调
     *
     * @param errorCallback
     */
    private void errorMain(AsyncChainErrorCallback errorCallback, AsyncChainError error) {
        if (MAIN_HANDLER == null) {
            MAIN_HANDLER = getMainHandler();
        }
        MAIN_HANDLER.obtainMessage(MSG_ERROR_MAIN, new AsyncChainErrorHandlerObj(errorCallback, error))
                .sendToTarget();
    }

    /**
     * 表示一个异步链结束了，即使该链里面还有后续的异步操作也不继续了
     * 找到这个异步操作所在的异步链Link，然后移除掉Link个他的Runnable列表
     *
     * @param task
     */
    void onComplete(AsyncChainTask task) {
        AsyncChainLink link = findAsyncChainLink(task);
        if (link != null) {
            link.getRunnableList().clear();
            for (Map.Entry<String, AsyncChainLifeCycle> entry : mFragmentLifeCycleMap.entrySet()) {
                entry.getValue().removeLifeCycleListener(link);
            }
        }
    }

    /**
     * 通过异步行为控制器找到所在的异步链
     *
     * @param task 异步行为控制器
     * @return {@link AsyncChainLink} 异步链 可能为空
     */
    @Nullable
    private AsyncChainLink findAsyncChainLink(AsyncChainTask task) {
        //Fragment的生命周期池
        for (Map.Entry<String, AsyncChainLifeCycle> entry : mFragmentLifeCycleMap.entrySet()) {
            List<AsyncChainLifeCycleListener> lifeCycleListeners = entry.getValue().getLifeCycleListeners();
            for (int i = lifeCycleListeners.size() - 1; i >= 0; i--) {
                AsyncChainLifeCycleListener lifeCycleListener = lifeCycleListeners.get(i);
                if (lifeCycleListener instanceof AsyncChainLink) {
                    AsyncChainLink link = (AsyncChainLink) lifeCycleListener;
                    if (link.getRunnableList().size() == 0) {
                        //说明这个异步链式调用已经结束了,移除引用
                        entry.getValue().removeLifeCycleListener(link);
                    } else {
                        for (AsyncChainRunnableWrapper wrapper : link.getRunnableList()) {
                            if (TextUtils.equals(wrapper.runnable.runId, task.getRunId())) {
                                return link;
                            }
                        }
                    }
                }
            }
        }
        //Application的生命周期池
        for (int i = mApplicationLifeCycle.getLifeCycleListeners().size() - 1; i >= 0; i--) {
            AsyncChainLifeCycleListener lifeCycleListener = mApplicationLifeCycle.getLifeCycleListeners().get(i);
            if (lifeCycleListener instanceof AsyncChainLink) {
                AsyncChainLink link = (AsyncChainLink) lifeCycleListener;
                if (link.getRunnableList().size() == 0) {
                    //说明这个异步链式调用已经结束了,移除引用
                    mApplicationLifeCycle.removeLifeCycleListener(link);
                } else {
                    for (AsyncChainRunnableWrapper wrapper : link.getRunnableList()) {
                        if (TextUtils.equals(wrapper.runnable.runId, task.getRunId())) {
                            return link;
                        }
                    }
                }
            }
        }

        return null;
    }


    /**
     * 这个是生命周期管理类的回调
     *
     * @param lifeCycle 被销毁的生命周期类
     */
    @Override
    public void onDestroy(AsyncChainLifeCycle lifeCycle) {
        Log.d(TAG, "onDestroy: 我死了" + lifeCycle.toString());
        // 移除lifecycle
        for (Map.Entry<String, AsyncChainLifeCycle> entry : mFragmentLifeCycleMap.entrySet()) {
            if (entry.getValue() == lifeCycle) {
                mFragmentLifeCycleMap.remove(entry.getKey());
                return;
            }
        }
    }

    /**
     * 内部的Handler用来进入主线程
     */
    private static class InternalHandler extends Handler {
        InternalHandler(Looper looper) {
            super(looper);
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_RUN_MAIN) {
                AsyncChainRunnableHandlerObj asyncChainHandlerObj = (AsyncChainRunnableHandlerObj) msg.obj;
                AsyncChainRunnable runnable = asyncChainHandlerObj.runnable;
                AsyncChainTask task = runnable.getAsyncChainTask();
                task.setLastResult(asyncChainHandlerObj.result);
                try {
                    asyncChainHandlerObj.runnable.run(task);
                } catch (Exception e) {
                    task.onError(new AsyncChainError(e.getMessage(), e, task));
                }
            } else if (msg.what == MSG_ERROR_MAIN) {
                AsyncChainErrorHandlerObj asyncChainErrorHandlerObj = (AsyncChainErrorHandlerObj) msg.obj;
                if (asyncChainErrorHandlerObj.callback != null) {
                    try {
                        asyncChainErrorHandlerObj.callback.error(asyncChainErrorHandlerObj.error);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 主线程执行异步操作的包装类
     */
    private class AsyncChainRunnableHandlerObj {
        private AsyncChainRunnable runnable;
        private Object result;

        AsyncChainRunnableHandlerObj(AsyncChainRunnable runnable, Object result) {
            this.runnable = runnable;
            this.result = result;
        }
    }

    /**
     * 主线程执行错误处理的包装类
     */
    private class AsyncChainErrorHandlerObj {
        private AsyncChainErrorCallback callback;
        private AsyncChainError error;

        public AsyncChainErrorHandlerObj(AsyncChainErrorCallback callback, AsyncChainError error) {
            this.callback = callback;
            this.error = error;
        }
    }
}
