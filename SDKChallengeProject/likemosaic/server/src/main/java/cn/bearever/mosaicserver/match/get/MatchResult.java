package cn.bearever.mosaicserver.match.get;


import cn.bearever.mosaicserver.BaseResult;
import cn.bearever.mosaicserver.topic.TopicDao;

import java.util.List;

/**
 * 匹配数据的返回值bean
 */
public class MatchResult extends BaseResult {
    /**
     * 实时消息的token
     */
    private String rtmToken = "";
    /**
     * 视频聊天的token
     */
    private String rtcToken = "";
    /**
     * 房间号
     */
    private String channel = "";
    /**
     * 聊天对方的uid
     */
    private String remoteUid = "";

    /**
     * 获取信息
     */
    private List<TopicDao> list;

    public MatchResult() {
    }

    public String getRtmToken() {
        return rtmToken;
    }

    public void setRtmToken(String rtmToken) {
        this.rtmToken = rtmToken;
    }

    public String getRtcToken() {
        return rtcToken;
    }

    public void setRtcToken(String rtcToken) {
        this.rtcToken = rtcToken;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRemoteUid() {
        return remoteUid;
    }

    public void setRemoteUid(String remoteUid) {
        this.remoteUid = remoteUid;
    }

    public List<TopicDao> getList() {
        return list;
    }

    public void setList(List<TopicDao> list) {
        this.list = list;
    }
}
