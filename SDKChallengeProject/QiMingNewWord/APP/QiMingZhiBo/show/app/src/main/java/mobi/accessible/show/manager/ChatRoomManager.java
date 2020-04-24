package mobi.accessible.show.manager;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;

import mobi.accessible.show.ChatRoomApplication;
import mobi.accessible.show.model.AttributeKey;
import mobi.accessible.show.model.ChannelData;
import mobi.accessible.show.model.Constant;
import mobi.accessible.show.model.Member;
import mobi.accessible.show.model.Message;
import mobi.accessible.show.util.DialogUtils;
import mobi.accessible.show.util.MemberUtil;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmMessage;

public final class ChatRoomManager extends SeatManager implements MessageManager {

    private final String TAG = ChatRoomManager.class.getSimpleName();

    private static ChatRoomManager instance;

    private RtcManager mRtcManager;
    private RtmManager mRtmManager;
    private ChatRoomEventListener mListener;

    private ChannelData mChannelData = new ChannelData();

    @Override
    public ChannelData getChannelData() {
        return mChannelData;
    }

    @Override
    MessageManager getMessageManager() {
        return this;
    }

    @Override
    public RtcManager getRtcManager() {
        return mRtcManager;
    }

    @Override
    RtmManager getRtmManager() {
        return mRtmManager;
    }

    @Override
    void onSeatUpdated(int position) {
        if (mListener != null) {
            mListener.onSeatUpdated(position);
        }
    }

    private ChatRoomManager(Context context) {
        mRtcManager = RtcManager.instance(context);
        mRtcManager.setListener(mRtcListener);
        mRtmManager = RtmManager.instance(context);
        mRtmManager.setListener(mRtmListener);
    }

    public static ChatRoomManager instance(Context context) {
        if (instance == null) {
            synchronized (ChatRoomManager.class) {
                if (instance == null)
                    instance = new ChatRoomManager(context);
            }
        }
        return instance;
    }

    public void setListener(ChatRoomEventListener listener) {
        mListener = listener;
    }

    public void joinChannel(String channelId) {
        mRtmManager.login(Constant.sUserId, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Member member = new Member(String.valueOf(Constant.sUserId), Constant.sName, Constant.headimgurl, Constant.sAvatarIndex);
                mRtmManager.setLocalUserAttributes(AttributeKey.KEY_USER_INFO, member.toJsonString());

                mRtcManager.joinChannel(channelId, Constant.sUserId);

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {

            }
        });
    }

    public void leaveChannel() {
        mRtcManager.leaveChannel();
        mRtmManager.leaveChannel();
        mChannelData.release();
    }

    private void checkAndBeAnchor() {
        String myUserId = String.valueOf(Constant.sUserId);

        if (mChannelData.isAnchorMyself()) {
            int index = mChannelData.indexOfSeatArray(myUserId);
            if (index == -1) {
                index = mChannelData.firstIndexOfEmptySeat();
            }
            toBroadcaster(myUserId, index);
        } else {
            if (mChannelData.hasAnchor() && mChannelData.isUserOnline(mChannelData.getAnchorId())) return;
            mRtmManager.addOrUpdateChannelAttributes(AttributeKey.KEY_ANCHOR_ID, myUserId, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    toBroadcaster(myUserId, mChannelData.firstIndexOfEmptySeat());
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {

                }
            });
        }
    }

    public void givingGift() {
        Message message = new Message(Message.MESSAGE_TYPE_GIFT, null, Constant.sUserId);
        mRtmManager.sendMessage(message.toJsonString(), new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (mListener != null)
                    mListener.onUserGivingGift(message.getSendId());
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {

            }
        });
    }

    @Override
    public void sendOrder(String userId, String orderType, String content, ResultCallback<Void> callback) {
        if (!mChannelData.isAnchorMyself()) return;
        Message message = new Message(orderType, content, Constant.sUserId);
        mRtmManager.sendMessageToPeer(userId, message.toJsonString(), callback);
    }

    @Override
    public void sendMessage(String text) {
        Message message = new Message(text, Constant.sUserId);
        mRtmManager.sendMessage(message.toJsonString(), new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                addMessage(message);
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {

            }
        });
    }

    @Override
    public void processMessage(RtmMessage rtmMessage) {
        Message message = Message.fromJsonString(rtmMessage.getText());
        switch (message.getMessageType()) {
            case Message.MESSAGE_TYPE_TEXT:
            case Message.MESSAGE_TYPE_IMAGE:
                addMessage(message);
                break;
            case Message.MESSAGE_TYPE_GIFT:
                if (mListener != null)
                    mListener.onUserGivingGift(message.getSendId());
                break;
            case Message.MESSAGE_TYPE_ORDER:
                String myUserId = String.valueOf(Constant.sUserId);
                switch (message.getOrderType()) {
                    case Message.ORDER_TYPE_AUDIENCE:
                        toAudience(myUserId, null);
                        break;
                    case Message.ORDER_TYPE_BROADCASTER:
                        toBroadcaster(myUserId, Integer.valueOf(message.getContent()));
                        break;
                    case Message.ORDER_TYPE_GAME_OVER:
                        MemberUtil.setGameOverTime();
                        isGameOver(true);
                        break;
                    case Message.ORDER_TYPE_JOIN_BROADCASTER:
                        if(mListener != null){
                            mListener.onApplyForBroadcaster(message.getContent());
                        }
                        break;
                    case Message.ORDER_TYPE_MUTE:
                        muteMic(myUserId, Boolean.valueOf(message.getContent()));
                        break;
                }
                break;
        }
    }

    @Override
    public void addMessage(Message message) {
        int position = mChannelData.addMessage(message);
        if (mListener != null)
            mListener.onMessageAdded(position);
    }

    private RtcManager.RtcEventListener mRtcListener = new RtcManager.RtcEventListener() {
        @Override
        public void onJoinChannelSuccess(String channelId) {
            mRtmManager.joinChannel(channelId, null);
        }

        @Override
        public void onUserOnlineStateChanged(int uid, boolean isOnline) {
            if (isOnline) {
                mChannelData.addOrUpdateUserStatus(uid, false);

                if (mListener != null)
                    mListener.onUserStatusChanged(String.valueOf(uid), false);
            } else {
                mChannelData.removeUserStatus(uid);

                if (mListener != null)
                    mListener.onUserStatusChanged(String.valueOf(uid), null);
            }
        }

        @Override
        public void onUserMuteAudio(int uid, boolean muted) {
            mChannelData.addOrUpdateUserStatus(uid, muted);

            if (mListener != null)
                mListener.onUserStatusChanged(String.valueOf(uid), muted);
        }

        @Override
        public void onAudioMixingStateChanged(boolean isPlaying) {
            if (mListener != null)
                mListener.onAudioMixingStateChanged(isPlaying);
        }

        @Override
        public void onAudioVolumeIndication(int uid, int volume) {
            if (mListener != null)
                mListener.onAudioVolumeIndication(String.valueOf(uid), volume);
        }
    };

    private RtmManager.RtmEventListener mRtmListener = new RtmManager.RtmEventListener() {
        @Override
        public void onChannelAttributesLoaded() {
            checkAndBeAnchor();
        }

        @Override
        public void onChannelAttributesUpdated(Map<String, String> attributes) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case AttributeKey.KEY_ANCHOR_ID:
                        String userId = entry.getValue();
                        if (mChannelData.setAnchorId(userId))
                            Log.i(TAG, String.format("onChannelAttributesUpdated %s %s", key, userId));
                        break;
                    default:
                        int index = AttributeKey.indexOfSeatKey(key);
                        if (index >= 0) {
                            String value = entry.getValue();
                            if (updateSeatArray(index, value)) {
                                Log.i(TAG, String.format("onChannelAttributesUpdated %s %s", key, value));

                                if (mListener != null)
                                    mListener.onSeatUpdated(index);
                            }
                        }
                        break;
                }
            }
        }

        @Override
        public void onInitMembers(List<RtmChannelMember> members) {
            for (RtmChannelMember member : members) {
                mChannelData.addOrUpdateMember(new Member(member.getUserId()));
            }

            if (mListener != null) {
                mListener.onMemberListUpdated(null);
            }
        }

        @Override
        public void onMemberJoined(String userId, Map<String, String> attributes) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                if (AttributeKey.KEY_USER_INFO.equals(entry.getKey())) {
                    Member member = Member.fromJsonString(entry.getValue());
                    mChannelData.addOrUpdateMember(member);

                    if (mListener != null)
                        mListener.onMemberListUpdated(userId);
                    break;
                }
            }
        }

        @Override
        public void onMemberLeft(String userId) {
            mChannelData.removeMember(userId);

            if (mListener != null)
                mListener.onMemberListUpdated(userId);
        }

        @Override
        public void onMessageReceived(RtmMessage message) {
            processMessage(message);
        }
    };
    /**
     * 判断是否强制下线
     * @param isGameOVer
     */
    private void isGameOver(boolean isGameOVer) {
        if (mListener != null)
            if(isGameOVer || !mChannelData.hasAnchor() || !mChannelData.isUserOnline(mChannelData.getAnchorId())){
                mListener.gameOver(isGameOVer);
            }

    }

}
