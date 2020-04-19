package cn.bearever.mingbase;

import cn.bearever.mingbase.app.util.ToastUtil;
import cn.bearever.mingbase.chain.AsyncChain;
import cn.bearever.mingbase.chain.core.AsyncChainRunnable;
import cn.bearever.mingbase.chain.core.AsyncChainTask;

/**
 * 通用异步返回接口
 *
 * @author luoming
 * @date 2020/4/12
 */
public abstract class BaseCallback<T> {
    /**
     * 成功
     *
     * @param data
     */
    public abstract void suc(T data);

    /**
     * 失败
     *
     * @param msg  失败说明
     * @param code 失败码
     */
    public void fail(final String msg, int code) {
        AsyncChain.withMain(new AsyncChainRunnable() {
            @Override
            public void run(AsyncChainTask task) throws Exception {
                ToastUtil.show(msg);
                task.onComplete();
            }
        }).go();
    }
}
