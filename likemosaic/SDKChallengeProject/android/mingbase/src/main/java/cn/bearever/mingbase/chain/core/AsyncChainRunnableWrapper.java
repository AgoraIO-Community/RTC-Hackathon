package cn.bearever.mingbase.chain.core;

/**
 * AsyncChainRunnable 的包装类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/2
 **/

public class AsyncChainRunnableWrapper {
    /**
     * 异步操作
     */
    public AsyncChainRunnable runnable;
    /**
     * 执行的线程
     */
    public int thread = ORIGINAL;
    /**
     * 延迟执行的时间，毫秒
     */
    public long delay = 0;

    /**
     * 原始线程
     */
    public final static int ORIGINAL = 0;
    /**
     * 工作线程
     */
    public final static int WORK = 1;
    /**
     * 主线程
     */
    public final static int MAIN = 2;

    public AsyncChainRunnableWrapper(AsyncChainRunnable runnable, int thread) {
        this.runnable = runnable;
        this.thread = thread;
    }

    public AsyncChainRunnableWrapper(AsyncChainRunnable runnable, int thread, long delay) {
        this.runnable = runnable;
        this.thread = thread;
        this.delay = delay;
    }
}
