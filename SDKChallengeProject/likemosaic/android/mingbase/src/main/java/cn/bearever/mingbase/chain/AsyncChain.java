package cn.bearever.mingbase.chain;


import cn.bearever.mingbase.chain.core.AsyncChainLink;
import cn.bearever.mingbase.chain.core.AsyncChainRunnable;

/**
 * 一部链式调用的入口类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/22
 **/
public class AsyncChain {

    /**
     * 开启一个异步链式链，在原始线程执行
     *
     * @param runnable 需要异步执行的操作
     * @return 一个异步链式的链
     */
    public static AsyncChainLink with(AsyncChainRunnable runnable) {
        AsyncChainLink link = new AsyncChainLink();
        return link.with(runnable);
    }

    /**
     * 开启一个异步链式链，在工作线程执行
     *
     * @param runnable 需要异步执行的操作
     * @return 一个异步链式的链
     */
    public static AsyncChainLink withWork(AsyncChainRunnable runnable) {
        AsyncChainLink link = new AsyncChainLink();
        return link.withWork(runnable);
    }

    /**
     * 开启一个异步链式链，在主线程执行，用于更新UI
     *
     * @param runnable 需要异步执行的操作
     * @return 一个异步链式的链
     */
    public static AsyncChainLink withMain(AsyncChainRunnable runnable) {
        AsyncChainLink link = new AsyncChainLink();
        return link.withMain(runnable);
    }

    /**
     * 延迟一定时长执行操作，仅支持在工作线程做延时操作
     *
     * @param ms 延迟的时间单位毫秒
     * @return 一个异步链式的链
     */
    public static AsyncChainLink delay(long ms) {
        AsyncChainLink link = new AsyncChainLink();
        return link.delay(ms);
    }
}
