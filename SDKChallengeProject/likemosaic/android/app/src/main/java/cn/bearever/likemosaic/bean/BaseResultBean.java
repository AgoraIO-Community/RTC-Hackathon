package cn.bearever.likemosaic.bean;

import java.io.Serializable;

/**
 * @author luoming
 * @date 2020/4/12
 */
public class BaseResultBean implements Serializable {
    public int code;
    public String msg;

    /**
     * 成功
     */
    public static final int CODE_SUCCEED = 200;
    /**
     * 失败
     */
    public static final int CODE_FAILED = 500;

}

