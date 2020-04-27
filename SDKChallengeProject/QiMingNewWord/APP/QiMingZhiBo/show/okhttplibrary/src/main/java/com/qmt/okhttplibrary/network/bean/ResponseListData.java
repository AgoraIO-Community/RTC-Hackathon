package com.qmt.okhttplibrary.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author wlk
 * @date 2017/10/11
 */

public class ResponseListData<T> implements Serializable {


    private int status;
    private String message;
    private int totalElements;
    private List<T> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalNum() {
        return totalElements;
    }

    public void setTotalNum(int totalNum) {
        this.totalElements = totalNum;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
