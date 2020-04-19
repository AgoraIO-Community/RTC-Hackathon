package cn.bearever.mingbase.chain.core;

import java.util.UUID;

/**
 * 异步操作的接口
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/22
 **/
public abstract class AsyncChainRunnable<LAST_RESULT, NEXT_RESULT> {
    private static final String TAG = "AsyncChainRunnable";
    protected String runId = UUID.randomUUID().toString();
    /**
     * 异步行为的控制器
     */
    private AsyncChainTask mAsyncChainTask;

    public AsyncChainRunnable() {
        mAsyncChainTask = new AsyncChainTask(runId);
    }

    /**
     * 执行异步操作的接口
     *
     * @param task 当前这个异步操作行为控制类
     * @throws Exception 异步操作执行异常
     */
    public abstract void run(AsyncChainTask<LAST_RESULT, NEXT_RESULT> task) throws Exception;

    /**
     * 获取异步行为控制器
     *
     * @return {@link AsyncChainTask}
     */
    AsyncChainTask getAsyncChainTask() {
        return mAsyncChainTask;
    }

    void setAsyncChainTask(AsyncChainTask mAsyncChainTask) {
        this.mAsyncChainTask = mAsyncChainTask;
    }

    public static class DefaultAsyncChainRunnable extends AsyncChainRunnable {
        @Override
        public void run(AsyncChainTask task) throws Exception {
            task.onNext(task.getLastResult());
        }
    }
}
