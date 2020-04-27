package mobi.accessible.show.model;

import com.google.gson.Gson;

public class Message {

    public static final int MESSAGE_TYPE_TEXT = 0;
    public static final int MESSAGE_TYPE_IMAGE = 1;
    public static final int MESSAGE_TYPE_GIFT = 2;
    public static final int MESSAGE_TYPE_ORDER = 3;

    public static final String ORDER_TYPE_AUDIENCE = "toAudience";
    public static final String ORDER_TYPE_BROADCASTER = "toBroadcaster";
    public static final String ORDER_TYPE_JOIN_BROADCASTER = "joinToBroadcaster";
    public static final String ORDER_TYPE_GAME_OVER = "isGameOver";
    public static final String ORDER_TYPE_MUTE = "mute";

    private int messageType = MESSAGE_TYPE_TEXT;
    private String orderType;
    private String content;
    private String sendId;

    public Message(String content, int sendId) {
        this.content = content;
        this.sendId = String.valueOf(sendId);
    }

    public Message(int messageType, String content, int sendId) {
        this(content, sendId);
        this.messageType = messageType;
    }

    public Message(String orderType, String content, int sendId) {
        this(content, sendId);
        this.messageType = MESSAGE_TYPE_ORDER;
        this.orderType = orderType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String toJsonString() {
        return new Gson().toJson(this);
    }

    public static Message fromJsonString(String str) {
        return new Gson().fromJson(str, Message.class);
    }

}
