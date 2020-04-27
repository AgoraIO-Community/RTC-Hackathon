package com.qmt.okhttplibrary.network.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * HTTP请求回调
 *
 * @author wlk
 * @date 2017/10/11
 */
public abstract class ResultCallback<T> {
    public Type mType;
    /**
     * 请求失败的时候是否弹出提示和回调成功的方法  默认不回掉
     */
    public boolean isShowFail = false;

    protected ResultCallback() {
        Type superClass = super.getClass().getGenericSuperclass();
        this.mType = ((ParameterizedType) superClass).getActualTypeArguments()[0];

    }

    protected ResultCallback(boolean isShowFail) {
        Type superClass = super.getClass().getGenericSuperclass();
        this.mType = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.isShowFail = isShowFail;
    }


    /**
     * 请求成功回调
     *
     * @param response 返回参数
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败回调
     * @param e Exception
     */
    public abstract void onFailure(Exception e);
}
