package cn.bearever.likemosaic.bean;

import java.io.Serializable;

/**
 * 发送的消息格式
 *
 * @author luoming
 * @date 2020/4/18
 */
public class MessageBean<D extends Serializable> implements Serializable {
    public String key;
    public D data;
    public String text;
    /**
     * 当前频道信息
     */
    public String channel;

    public MessageBean(String channel) {
        this.channel = channel;
    }

    public static final String KEY_SELECT_TOPIC = "KEY_SELECT_TOPIC";

    public static final String KEY_REFRESH_TOPIC = "KEY_REFRESH_TOPIC";

    public static final String KEY_REMOTE_LIKE_CHANGE = "KEY_REMOTE_LIKE_CHANGE";

    public static final String KEY_QUIT_ROOM = "KEY_QUIT_ROOM";
}
