package cn.bearever.mingbase.chain.core;


/**
 * 错误信息类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/2
 **/
public class AsyncChainError {
    /**
     * 错误描述
     */
    private String message;
    /**
     * 原始的错误信息{@link Exception}
     */
    private Exception exception;
    /**
     * 异步行为的控制类
     */
    private AsyncChainTask asyncChainTask;
    /**
     * 异步操作所在的完整异步链
     */
    private AsyncChainLink asyncChainLink;

    public AsyncChainError(String message) {
        this.message = message;
    }

    public AsyncChainError(String message, Exception e) {
        this.message = message;
        this.exception = e;
    }

    public AsyncChainError(String message, Exception e, AsyncChainTask task) {
        this.message = message;
        this.exception = e;
        this.asyncChainTask = task;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    AsyncChainTask getAsyncChainTask() {
        return asyncChainTask;
    }

    void setAsyncChainTask(AsyncChainTask asyncChainTask) {
        this.asyncChainTask = asyncChainTask;
    }

    protected AsyncChainLink getAsyncChainLink() {
        return asyncChainLink;
    }

    protected void setAsyncChainLink(AsyncChainLink asyncChainLink) {
        this.asyncChainLink = asyncChainLink;
    }

    @Override
    public String toString() {
        return "AsyncChainTask:" + asyncChainTask + "message:" + message + "\nexception:" + exception;
    }
}
