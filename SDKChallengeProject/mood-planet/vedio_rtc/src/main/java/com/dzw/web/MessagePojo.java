package com.dzw.web;

import java.io.Serializable;

public class MessagePojo implements Serializable {
    private static final long serialVersionUID = -6451812593150428310L;

    private String source;// 信息来源
    private String messageType;// 消息类型
    private String msgContent;// 消息内容
    private String target;// 发送目的地
    private String createtime;// 消息保存时间
    private String otherContent;// 其他信息
    private Boolean videoFlag;// 视频标志
    private String channel;// 视频频道

    public MessagePojo() {
    }

    public MessagePojo(String source, String messageType, String msgContent, String target, String infoSourceIP, String createtime, String otherContent,Boolean videoFlag,String channel) {
        this.source = source;
        this.messageType = messageType;
        this.msgContent = msgContent;
        this.target = target;
        this.createtime = createtime;
        this.otherContent = otherContent;
        this.videoFlag = videoFlag;
        this.channel = channel;
    }

    public MessagePojo(String source, String msgContent,String target) {
        this.source = source;
        this.msgContent = msgContent;
        this.target = target;
    }


    public MessagePojo(String source,String target,Boolean videoFlag,String channel) {
        this.source = source;
        this.target = target;
        this.videoFlag = videoFlag;
        this.channel = channel;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSourse() {
        return source;
    }

    public void setSourse(String source) {
        this.source = source;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getVedioFlag() {
        return videoFlag;
    }

    public void setVedioFlag(Boolean videoFlag) {
        this.videoFlag = videoFlag;
    }

    public Boolean getVideoFlag() {
        return videoFlag;
    }

    public void setVideoFlag(Boolean videoFlag) {
        this.videoFlag = videoFlag;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
