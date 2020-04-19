package cn.bearever.mosaicserver;

/**
 * 返回值的基类
 */
public class BaseResult {
    /**
     * 提示文本
     */
    private String msg = "";
    /**
     * 状态码
     */
    private int code = CODE_SUCCEED;

    /**
     * 成功
     */
    public static final int CODE_SUCCEED = 200;
    /**
     * 失败
     */
    public static final int CODE_FAILED = 500;
    /**
     * 其他
     */
    public static final int CODE_UNKOWN = 1000;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
