package mobi.accessible.show.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.accessible.show.R;
import mobi.accessible.show.util.AlertUtil;
import io.agora.rtm.ChannelAttributeOptions;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmAttribute;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelAttribute;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;

public final class RtmManager {

    public interface RtmEventListener {
        void onChannelAttributesLoaded();

        void onChannelAttributesUpdated(Map<String, String> attributes);

        void onInitMembers(List<RtmChannelMember> members);

        void onMemberJoined(String userId, Map<String, String> attributes);

        void onMemberLeft(String userId);

        void onMessageReceived(RtmMessage message);
    }

    private final String TAG = RtmManager.class.getSimpleName();

    private static RtmManager instance;

    private Context mContext;
    private RtmEventListener mListener;
    private RtmClient mRtmClient;
    private RtmChannel mRtmChannel;
    private boolean mIsLogin;

    private RtmManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static RtmManager instance(Context context) {
        if (instance == null) {
            synchronized (RtmManager.class) {
                if (instance == null)
                    instance = new RtmManager(context);
            }
        }
        return instance;
    }

    public void setListener(RtmEventListener listener) {
        mListener = listener;
    }

    public void init() {
        if (mRtmClient == null) {
            try {
                mRtmClient = RtmClient.createInstance(mContext, mContext.getString(R.string.app_id), mClientListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void login(int userId, ResultCallback<Void> callback) {
        if (mRtmClient != null) {
            if (mIsLogin) {
                if (callback != null)
                    callback.onSuccess(null);
                return;
            }
            mRtmClient.login(mContext.getString(R.string.rtm_token), String.valueOf(userId), new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "rtm login success");
                    mIsLogin = true;

                    if (callback != null)
                        callback.onSuccess(aVoid);
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    Log.e(TAG, String.format("rtm join %s", errorInfo.getErrorDescription()));
                    mIsLogin = false;

                    if (callback != null)
                        callback.onFailure(errorInfo);
                }
            });
        }
    }

    void joinChannel(String channelId, ResultCallback<Void> callback) {
        if (mRtmClient != null) {
            leaveChannel();

            Log.w(TAG, String.format("joinChannel %s", channelId));

            try {
                RtmChannel rtmChannel = mRtmClient.createChannel(channelId, mChannelListener);
                if (rtmChannel == null) return;
                rtmChannel.join(new ResultCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "rtm join success");
                        mRtmChannel = rtmChannel;

                        getChannelAttributes(channelId);
                        getMembers();

                        if (callback != null)
                            callback.onSuccess(aVoid);
                    }

                    @Override
                    public void onFailure(ErrorInfo errorInfo) {
                        Log.e(TAG, String.format("rtm join %s", errorInfo.getErrorDescription()));
                        AlertUtil.showToast("RTM login failed, see the log to get more info");

                        mRtmChannel = rtmChannel;

                        if (callback != null)
                            callback.onFailure(errorInfo);
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getChannelAttributes(String channelId) {
        if (mRtmClient != null) {
            mRtmClient.getChannelAttributes(channelId, new ResultCallback<List<RtmChannelAttribute>>() {
                @Override
                public void onSuccess(List<RtmChannelAttribute> attributeList) {
                    processChannelAttributes(attributeList);

                    if (mListener != null)
                        mListener.onChannelAttributesLoaded();
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    Log.e(TAG, String.format("getChannelAttributes %s", errorInfo.getErrorDescription()));
                }
            });
        }
    }

    private void processChannelAttributes(List<RtmChannelAttribute> attributeList) {
        if (attributeList != null) {
            Map<String, String> attributes = new HashMap<>();
            for (RtmChannelAttribute attribute : attributeList) {
                attributes.put(attribute.getKey(), attribute.getValue());
            }

            if (mListener != null)
                mListener.onChannelAttributesUpdated(attributes);
        }
    }

    private void getMembers() {
        if (mRtmChannel != null) {
            mRtmChannel.getMembers(new ResultCallback<List<RtmChannelMember>>() {
                @Override
                public void onSuccess(List<RtmChannelMember> rtmChannelMembers) {
                    if (mListener != null)
                        mListener.onInitMembers(rtmChannelMembers);

                    for (RtmChannelMember member : rtmChannelMembers) {
                        getUserAttributes(member.getUserId());
                    }
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {

                }
            });
        }
    }

    private void getUserAttributes(String userId) {
        if (mRtmClient != null) {
            mRtmClient.getUserAttributes(userId, new ResultCallback<List<RtmAttribute>>() {
                @Override
                public void onSuccess(List<RtmAttribute> rtmAttributes) {
                    Log.d(TAG, String.format("getUserAttributes %s", rtmAttributes.toString()));

                    processUserAttributes(userId, rtmAttributes);
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    Log.e(TAG, String.format("getUserAttributes %s", errorInfo.getErrorDescription()));
                }
            });
        }
    }

    private void processUserAttributes(String userId, List<RtmAttribute> attributeList) {
        if (attributeList != null) {
            Map<String, String> attributes = new HashMap<>();
            for (RtmAttribute attribute : attributeList) {
                attributes.put(attribute.getKey(), attribute.getValue());
            }

            if (mListener != null)
                mListener.onMemberJoined(userId, attributes);
        }
    }

    void setLocalUserAttributes(String key, String value) {
        if (mRtmClient != null) {
            RtmAttribute attribute = new RtmAttribute(key, value);
            mRtmClient.setLocalUserAttributes(Collections.singletonList(attribute), null);
        }
    }

    void addOrUpdateChannelAttributes(String key, String value, ResultCallback<Void> callback) {
        if (mRtmClient != null) {
            if (mRtmChannel == null) {
                AlertUtil.showToast("RTM not login, see the log to get more info");
                return;
            }

            RtmChannelAttribute attribute = new RtmChannelAttribute(key, value);
            mRtmClient.addOrUpdateChannelAttributes(mRtmChannel.getId(), Collections.singletonList(attribute), options(), new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, String.format("addOrUpdateChannelAttributes %s %s", key, value));

                    if (callback != null)
                        callback.onSuccess(aVoid);
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    Log.e(TAG, String.format("addOrUpdateChannelAttributes %s %s %s", key, value, errorInfo.getErrorDescription()));

                    if (callback != null)
                        callback.onFailure(errorInfo);
                }
            });
        }
    }

    private ChannelAttributeOptions options() {
        return new ChannelAttributeOptions(true);
    }

    void deleteChannelAttributesByKey(String key) {
        if (mRtmClient != null) {
            if (mRtmChannel == null) {
                AlertUtil.showToast("RTM not login, see the log to get more info");
                return;
            }

            mRtmClient.deleteChannelAttributesByKeys(mRtmChannel.getId(), Collections.singletonList(key), options(), null);
        }
    }

    void sendMessage(String content, ResultCallback<Void> callback) {
        if (mRtmClient != null) {
            RtmMessage message = mRtmClient.createMessage(content);
            if (mRtmChannel != null) {
                mRtmChannel.sendMessage(message, new ResultCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, String.format("sendMessage %s", content));

                        if (callback != null)
                            callback.onSuccess(aVoid);
                    }

                    @Override
                    public void onFailure(ErrorInfo errorInfo) {
                        Log.e(TAG, String.format("sendMessage %s", errorInfo.getErrorDescription()));

                        if (callback != null)
                            callback.onFailure(errorInfo);
                    }
                });
            }
        }
    }

    void sendMessageToPeer(String userId, String content, ResultCallback<Void> callback) {
        if (TextUtils.isEmpty(userId)) return;

        if (mRtmClient != null) {
            RtmMessage message = mRtmClient.createMessage(content);
            mRtmClient.sendMessageToPeer(userId, message, null, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, String.format("sendMessageToPeer %s %s", userId, content));

                    if (callback != null)
                        callback.onSuccess(aVoid);
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    Log.e(TAG, String.format("sendMessageToPeer %s", errorInfo.getErrorDescription()));

                    if (callback != null)
                        callback.onFailure(errorInfo);
                }
            });
        }
    }

    void leaveChannel() {
        if (mRtmChannel != null) {
            Log.w(TAG, String.format("leaveChannel %s", mRtmChannel.getId()));

            mRtmChannel.leave(null);
            mRtmChannel.release();
            mRtmChannel = null;
        }
    }

    private RtmClientListener mClientListener = new RtmClientListener() {
        @Override
        public void onConnectionStateChanged(int i, int i1) {
        }

        @Override
        public void onMessageReceived(RtmMessage rtmMessage, String s) {
            Log.i(TAG, String.format("onPeerMessageReceived %s %s", rtmMessage.getText(), s));

            if (mListener != null)
                mListener.onMessageReceived(rtmMessage);
        }

        @Override
        public void onTokenExpired() {
        }

        @Override
        public void onPeersOnlineStatusChanged(Map<String, Integer> map) {
        }
    };

    private RtmChannelListener mChannelListener = new RtmChannelListener() {
        @Override
        public void onMemberCountUpdated(int i) {
        }

        @Override
        public void onAttributesUpdated(List<RtmChannelAttribute> list) {
            Log.i(TAG, "onAttributesUpdated");

            processChannelAttributes(list);
        }

        @Override
        public void onMessageReceived(RtmMessage rtmMessage, RtmChannelMember rtmChannelMember) {
            Log.i(TAG, String.format("onChannelMessageReceived %s %s", rtmMessage.getText(), rtmChannelMember.getUserId()));

            if (mListener != null)
                mListener.onMessageReceived(rtmMessage);
        }

        @Override
        public void onMemberJoined(RtmChannelMember rtmChannelMember) {
            String userId = rtmChannelMember.getUserId();
            Log.i(TAG, String.format("onMemberJoined %s", userId));

            getUserAttributes(userId);
        }

        @Override
        public void onMemberLeft(RtmChannelMember rtmChannelMember) {
            String userId = rtmChannelMember.getUserId();
            Log.i(TAG, String.format("onMemberLeft %s", userId));

            if (mListener != null)
                mListener.onMemberLeft(userId);
        }
    };

}
