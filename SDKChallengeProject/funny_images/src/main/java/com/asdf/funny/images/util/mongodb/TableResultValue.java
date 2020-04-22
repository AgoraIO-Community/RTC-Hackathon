package com.asdf.funny.images.util.mongodb;

/**
 * @author: zyh
 * @date: 2018/7/6
 */
public class TableResultValue<T> {
    /**状态码*/
    private Integer code;
    /**说明*/
    private String msg;
    /**总条数*/
    private Integer count;
    /**数据*/
    private T data;

    private Long userId;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
