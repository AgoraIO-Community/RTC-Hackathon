package cn.bearever.mingbase.chain.core;

/**
 * 异步操作Runnable的行为控制类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/2
 **/
public class AsyncChainTask<LAST_RESULT, NEXT_RESULT> {
    private String runId;
    private LAST_RESULT lastResult;
    private NEXT_RESULT nextResult;

    public AsyncChainTask(String runId) {
        this.runId = runId;
    }

    /**
     * 某一个操作成功结束了，执行下一步
     *
     * @param newResult 当前异步行为的结果，传递给下一个步骤使用，可以传递null
     */
    public void onNext(NEXT_RESULT newResult) {
        AsyncChainManager.getInstance().onNext(this, newResult);
    }

    /**
     * 某一个操作失败了
     *
     * @param error 错误信息
     */
    public void onError(AsyncChainError error) {
        error.setAsyncChainTask(this);
        AsyncChainManager.getInstance().onError(error);
    }

    /**
     * 异步操作结束了，整个异步链停止，即使还有后续的异步操作，也不会执行了
     */
    public void onComplete() {
        AsyncChainManager.getInstance().onComplete(this);
    }

    /**
     * 获取上一个一步行为的结果
     *
     * @return 来自于上一个步骤的{@link #onNext(lastResult)}
     */
    public LAST_RESULT getLastResult() {
        return lastResult;
    }

    void setLastResult(LAST_RESULT lastResult) {
        this.lastResult = lastResult;
    }

    void setNextResult(NEXT_RESULT nextResult) {
        this.nextResult = nextResult;
    }

    NEXT_RESULT getNextResult() {
        return nextResult;
    }

    String getRunId() {
        return runId;
    }
}
