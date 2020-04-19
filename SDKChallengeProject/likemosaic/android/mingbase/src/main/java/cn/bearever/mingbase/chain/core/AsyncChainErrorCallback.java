package cn.bearever.mingbase.chain.core;

/**
 * 异步操作执行错误的回调接口，用于对错误进行处理，一旦报错，异步链就停止运行了，不会继续下去。
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/5
 **/
public abstract class AsyncChainErrorCallback {
    /**
     * 错误回调执行的线程{@link AsyncChainRunnableWrapper#ORIGINAL}/{@link AsyncChainRunnableWrapper#MAIN}/{@link AsyncChainRunnableWrapper#WORK}
     */
    private int thread;

    int getThread() {
        return thread;
    }

    /**
     * 设置执行的线程
     *
     * @param thread {@link AsyncChainRunnableWrapper#ORIGINAL}/{@link AsyncChainRunnableWrapper#MAIN}/{@link AsyncChainRunnableWrapper#WORK}
     */
    void setThread(int thread) {
        this.thread = thread;
    }

    /**
     * 异步操作执行出现了错误
     *
     * @param error 错误的信息
     */
    public abstract void error(AsyncChainError error) throws Exception;
}
