package com.qmt.okhttplibrary.network.bean;

/**
 * 公司自己的返回数据实体结构
 *
 * @author wlk
 * @date 2017/10/11
 */

public class ResponseDataBase  {


    private int status;
    private String message;
    private int totalNum;

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
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
