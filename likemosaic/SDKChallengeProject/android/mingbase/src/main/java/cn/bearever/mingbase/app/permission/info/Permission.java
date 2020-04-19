package cn.bearever.mingbase.app.permission.info;


import java.io.Serializable;

/**
 * 权限信息的数据结构
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/8/13
 **/
public class Permission implements Serializable {
    /**
     * 权限名字
     */
    private String name;
    /**
     * 可以看懂的权限名
     */
    private String message;
    /**
     * 是否授权
     */
    private boolean grand;

    public Permission() {
    }

    public Permission(String name, String message, boolean grand) {
        this.name = name;
        this.message = message;
        this.grand = grand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isGrand() {
        return grand;
    }

    public void setGrand(boolean grand) {
        this.grand = grand;
    }
}
