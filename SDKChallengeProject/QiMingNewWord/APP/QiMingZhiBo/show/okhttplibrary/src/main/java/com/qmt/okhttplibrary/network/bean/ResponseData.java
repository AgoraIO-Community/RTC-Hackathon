package com.qmt.okhttplibrary.network.bean;

import java.io.Serializable;

/**
 * 公司自己的返回数据实体结构
 *
 * @author wlk
 * @date 2017/10/11
 */

public class ResponseData<T> implements Serializable {


    private int code;
    private String errorMsg;
    private T content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
