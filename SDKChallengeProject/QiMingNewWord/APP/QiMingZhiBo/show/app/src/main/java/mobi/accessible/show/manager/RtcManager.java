package mobi.accessible.show.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

import mobi.accessible.show.R;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public final class RtcManager {

    public interface RtcEventListener {
        void onJoinChannelSuccess(String channelId);

        void onUserOnlineStateChanged(int uid, boolean isOnline);

        void onUserMuteAudio(int uid, boolean muted);

        void onAudioMixingStateChanged(boolean isPlaying);

        void onAudioVolumeIndication(int uid, int volume);
    }

    private final String TAG = RtcManager.class.getSimpleName();

    private static RtcManager instance;

    private Context mContext;
    private RtcEventListener mListener;
    private RtcEngine mRtcEngine;
    private int mUserId;

    private RtcManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static RtcManager instance(Context context) {
        if (instance == null) {
            synchronized (RtcManager.class) {
                if (instance == null)
                    instance = new RtcManager(context);
            }
        }
        return instance;
    }

    public void setListener(RtcEventListener listener) {
        mListener = listener;
    }

    public void init() {
        if (mRtcEngine == null) {
            try {
                mRtcEngine = RtcEngine.create(mContext, mContext.getString(R.string.app_id), mEventHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mRtcEngine != null) {
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mRtcEngine.setAudioProfile(Constants.AUDIO_PROFILE_MUSIC_HIGH_QUALITY, Constants.AUDIO_SCENARIO_CHATROOM_ENTERTAINMENT);
            mRtcEngine.enableAudioVolumeIndication(500, 3, false);
            mRtcEngine.adjustAudioMixingPlayoutVolume(200);
        }
    }

    void joinChannel(String channelId, int userId) {
        if (mRtcEngine != null)
            mRtcEngine.joinChannel(mContext.getString(R.string.token), channelId, null, userId);
    }

    void setClientRole(int role) {
        if (mRtcEngine != null)
            mRtcEngine.setClientRole(role);
    }

    public void muteAllRemoteAudioStreams(boolean muted) {
        if (mRtcEngine != null)
            mRtcEngine.muteAllRemoteAudioStreams(muted);
    }

    void muteLocalAudioStream(boolean muted) {
        if (mRtcEngine != null)
            mRtcEngine.muteLocalAudioStream(muted);
        if (mListener != null)
            mListener.onUserMuteAudio(mUserId, muted);
    }

    public void startAudioMixing(String filePath) {
        if (mRtcEngine != null) {
            mRtcEngine.startAudioMixing(TextUtils.isEmpty(filePath)?"/assets/mixing.mp3":filePath, false, false, 1);
            mRtcEngine.adjustAudioMixingVolume(65);
        }
    }

    public void stopAudioMixing() {
        if (mRtcEngine != null)
            mRtcEngine.stopAudioMixing();
    }

    public void setVoiceChanger(int type) {
        if (mRtcEngine != null)
            mRtcEngine.setParameters(String.format(Locale.getDefault(), "{\"che.audio.morph.voice_changer\": %d}", type));
    }

    public void setReverbPreset(int type) {
        if (mRtcEngine != null)
            mRtcEngine.setParameters(String.format(Locale.getDefault(), "{\"che.audio.morph.reverb_preset\": %d}", type));
    }

    void leaveChannel() {
        if (mRtcEngine != null) {
            mRtcEngine.leaveChannel();
            setClientRole(Constants.CLIENT_ROLE_AUDIENCE);
        }
    }

    private IRtcEngineEventHandler mEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            super.onJoinChannelSuccess(channel, uid, elapsed);
            Log.i(TAG, String.format("onJoinChannelSuccess %s %d", channel, uid));

            mUserId = uid;
            if (mListener != null)
                mListener.onJoinChannelSuccess(channel);
        }

        @Override
        public void onClientRoleChanged(int oldRole, int newRole) {
            super.onClientRoleChanged(oldRole, newRole);
            Log.i(TAG, String.format("onClientRoleChanged %d %d", oldRole, newRole));

            if (mListener != null) {
                if (newRole == Constants.CLIENT_ROLE_BROADCASTER)
                    mListener.onUserOnlineStateChanged(mUserId, true);
                else if (newRole == Constants.CLIENT_ROLE_AUDIENCE)
                    mListener.onUserOnlineStateChanged(mUserId, false);
            }
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            Log.i(TAG, String.format("onUserJoined %d", uid));

            if (mListener != null)
                mListener.onUserOnlineStateChanged(uid, true);
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            super.onUserOffline(uid, reason);
            Log.i(TAG, String.format("onUserOffline %d", uid));

            if (mListener != null)
                mListener.onUserOnlineStateChanged(uid, false);
        }

        @Override
        public void onUserMuteAudio(int uid, boolean muted) {
            super.onUserMuteAudio(uid, muted);
            Log.i(TAG, String.format("onUserMuteAudio %d %b", uid, muted));

            if (mListener != null)
                mListener.onUserMuteAudio(uid, muted);
        }

        @Override
        public void onAudioVolumeIndication(AudioVolumeInfo[] speakers, int totalVolume) {
            super.onAudioVolumeIndication(speakers, totalVolume);

            for (AudioVolumeInfo info : speakers) {
                if (info.volume > 0) {
                    int uid = info.uid == 0 ? mUserId : info.uid;
                    if (mListener != null)
                        mListener.onAudioVolumeIndication(uid, info.volume);
                }
            }
        }

        @Override
        public void onAudioMixingStateChanged(int state, int errorCode) {
            super.onAudioMixingStateChanged(state, errorCode);

            if (mListener != null)
                mListener.onAudioMixingStateChanged(state == Constants.MEDIA_ENGINE_AUDIO_EVENT_MIXING_PLAY);
        }
    };

}
