package com.asdf.funny.images.util.mongodb;

/**
 * Created by ljf on 2018/7/10.
 */
public class RichText<T> {
    /**状态码*/
    private String code;
    /**说明*/
    private String msg;
    /**数据*/
    private T data;

    private String[] src;
    private String title;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public String[] getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSrc(String[] src) {
        this.src = src;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RichText{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", src='" + src + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
