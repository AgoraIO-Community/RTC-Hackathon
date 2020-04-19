package cn.bearever.likemosaic.bean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author luoming
 * @date 2020/4/12
 */
public class MatchResultBean extends BaseResultBean {
    /**
     * 实时消息的token
     */
    public String rtmToken;
    /**
     * 视频聊天的token
     */
    public String rtcToken;
    /**
     * 匹配对方的uid，用于进行点对点通信
     */
    public String remoteUid;
    public String channel;
    public ArrayList<TopicBean> list;

    @NonNull
    @Override
    public String toString() {
        return "rtmToken:" + rtmToken + ",rtcToken:" + rtcToken + ",channel:" + channel + ",remoteUid:" + remoteUid + ",topic:" + list;
    }
}
