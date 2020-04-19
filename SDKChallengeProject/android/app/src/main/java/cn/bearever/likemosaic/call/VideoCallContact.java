package cn.bearever.likemosaic.call;

import android.nfc.Tag;

import java.util.List;

import androidx.annotation.NonNull;
import cn.bearever.likemosaic.bean.MessageBean;
import cn.bearever.likemosaic.bean.SelectTopicBean;
import cn.bearever.likemosaic.bean.TopicBean;
import cn.bearever.likemosaic.bean.TopicListResultBean;
import cn.bearever.mingbase.BaseCallback;
import cn.bearever.mingbase.app.mvp.IBaseModel;
import cn.bearever.mingbase.app.mvp.IBasePresenter;
import cn.bearever.mingbase.app.mvp.IBaseView;
import io.agora.rtc.mediaio.IVideoSink;

/**
 * @author luoming
 * @date 2020/4/16
 */
public class VideoCallContact {
    public interface View extends IBaseView {
        /**
         * 刷新话题区显示
         *
         * @param topicList
         */
        void refreshTags(@NonNull List<TopicBean> topicList);

        /**
         * 执行刷新话题的动画
         *
         * @param request 动画执行完毕是否请求刷新话题区
         */
        void startRefreshAnimation(boolean request);

        /**
         * 收到了对方选择/取消选择话题的消息，需要对ui进行更新
         */
        void receiveSelectTag(SelectTopicBean selectTopicBean);

        /**
         * 更新好感度显示，需要更新马赛克级别和好感度进度条
         *
         * @param likeCount
         */
        void refreshLike(int likeCount);

        /**
         * 对方退出聊天
         */
        void onUserLeft();

        /**
         * 对方加入聊天
         *
         * @param uid
         */
        void onUserJoin(int uid);

        /**
         * 我对对方的好感度降低为0
         */
        void localLikeEmpty();

        /**
         * 显示退出房间按钮
         */
        void showQuitBtn();

        /**
         * 显示提醒文字
         */
        void showNote(String note);
    }

    public interface Model extends IBaseModel {
        /**
         * 登录
         *
         * @param rtmToken
         */
        void loginRtm(String rtmToken, String channel, String remoteUid);

        /**
         * 退出登录
         */
        void logoutRtm();

        /**
         * 发送消息
         *
         * @param message
         */
        void sendMessage(MessageBean message);

        /**
         * 发送消息
         *
         * @param message
         */
        void sendMessage(MessageBean message, String uid);

        /**
         * 获取话题列表
         *
         * @param callback
         */
        void getTopics(BaseCallback<TopicListResultBean> callback);

        /**
         * 注册消息状态变化的接口
         *
         * @param listener
         */
        void registerMessage(OnMessageChangeListener listener);
    }

    public interface OnMessageChangeListener {
        /**
         * 接收到消息
         *
         * @param message
         */
        void onReceive(MessageBean message);
    }

    public interface Presenter extends IBasePresenter {

        /**
         * 设置本地视频渲染器
         *
         * @param sink
         */
        void setLocalVideoRenderer(IVideoSink sink);

        /**
         * 设置远程视频渲染器
         *
         * @param uid
         * @param sink
         */
        void setRemoteVideoRenderer(int uid, IVideoSink sink);

        /**
         * 加入房间
         *
         * @param channel
         * @param rtcToken
         * @param rtmToken
         * @param remoteUid
         */
        void joinRoom(String channel, String rtcToken, String rtmToken, String remoteUid);

        /**
         * 离开房间
         */
        void quitRoom();

        /**
         * 静音
         *
         * @param mute 是否静音
         */
        void muteAudio(boolean mute);

        /**
         * 选中/取消选中 话题
         *
         * @param topicBean
         * @param isSelect
         */
        void selectTopic(TopicBean topicBean, boolean isSelect);

        /**
         * 增加好感度
         */
        void addLike();

        /**
         * 刷新话题区
         */
        void refreshTopics();
    }
}
