package cn.bearever.likemosaic.call;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import cn.bearever.likemosaic.Constant;
import cn.bearever.likemosaic.MosaiApplication;
import cn.bearever.likemosaic.R;
import cn.bearever.likemosaic.UidUtil;
import cn.bearever.likemosaic.bean.BaseResultBean;
import cn.bearever.likemosaic.bean.MessageBean;
import cn.bearever.likemosaic.bean.TopicListResultBean;
import cn.bearever.mingbase.BaseCallback;
import cn.bearever.mingbase.app.BaseApplication;
import cn.bearever.mingbase.app.store.SpManager;
import cn.bearever.mingbase.chain.AsyncChain;
import cn.bearever.mingbase.chain.core.AsyncChainError;
import cn.bearever.mingbase.chain.core.AsyncChainErrorCallback;
import cn.bearever.mingbase.chain.core.AsyncChainRunnable;
import cn.bearever.mingbase.chain.core.AsyncChainTask;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelAttribute;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static io.agora.rtm.RtmStatusCode.ConnectionState.CONNECTION_STATE_ABORTED;

/**
 * @author luoming
 * @date 2020/4/16
 */
public class VideoCallModel implements VideoCallContact.Model {
    private static final String TAG = "VideoCallModel";
    private Context mContext;
    private RtmClient mRtmClient;
    private VideoCallService mService;
    private Gson mGson;
    private String mRemoteUid;
    private String mChannel;
    private String mRtmToken;
    private VideoCallContact.OnMessageChangeListener mOnMessageChangeListener;


    public VideoCallModel(Context context) {
        mContext = context.getApplicationContext();
        init(mContext);
    }

    @Override
    public void registerMessage(VideoCallContact.OnMessageChangeListener listener) {
        mOnMessageChangeListener = listener;
    }

    @Override
    public void loginRtm(String rtmToken, String channel, String remoteUid) {
        mRemoteUid = remoteUid;
        mChannel = channel;
        mRtmToken = rtmToken;
        mRtmClient.login(rtmToken, UidUtil.getUid(MosaiApplication.getApplication()), new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "login onSuccess: ");
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.e(TAG, "login onFailure: " + errorInfo.getErrorDescription());
            }
        });
    }

    @Override
    public void logoutRtm() {
        if (mRtmClient != null) {
            mRtmClient.logout(null);
        }
    }

    private void init(final Context context) {
        try {
            Retrofit build = new Retrofit.Builder()
                    .baseUrl(Constant.APP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService = build.create(VideoCallService.class);

            mRtmClient = RtmClient.createInstance(context, context.getString(R.string.agora_app_id), new RtmClientListener() {
                @Override
                public void onConnectionStateChanged(int i, int i1) {
                    Log.d(TAG, "onConnectionStateChanged: ");
                    if (i == CONNECTION_STATE_ABORTED) {
                        Log.d(TAG, "onConnectionStateChanged: 被其他的设备顶下去了");
                    }
                }

                @Override
                public void onMessageReceived(RtmMessage rtmMessage, String s) {
//                    Log.d(TAG, "onMessageReceived: " + rtmMessage.getText());
                    if (mOnMessageChangeListener != null) {
                        final MessageBean messageBean = MessageConvertUtil.convert(rtmMessage.getText());
                        AsyncChain.withMain(new AsyncChainRunnable() {
                            @Override
                            public void run(AsyncChainTask task) throws Exception {
                                mOnMessageChangeListener.onReceive(messageBean);
                                task.onComplete();
                            }
                        }).error(new AsyncChainErrorCallback() {
                            @Override
                            public void error(AsyncChainError error) throws Exception {
                                error.getException().printStackTrace();
                            }
                        }).go(context);
                    }
                }

                @Override
                public void onTokenExpired() {
                    Log.d(TAG, "onTokenExpired: ");
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(MessageBean message) {
        sendMessage(message, mRemoteUid);
    }

    @Override
    public void sendMessage(MessageBean message, String uid) {
        if (mRtmClient == null) {
            init(mContext);
        }
        if (mRtmClient == null) {
            return;
        }

        RtmMessage msg = mRtmClient.createMessage();
        if (mGson == null) {
            mGson = new Gson();
        }
        msg.setText(mGson.toJson(message));
        mRtmClient.sendMessageToPeer(uid, msg, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "send message onSuccess: ");
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
//                Log.e(TAG, "send message onFailure: " + errorInfo.getErrorDescription());
            }
        });
    }

    @Override
    public void getTopics(final BaseCallback<TopicListResultBean> callback) {
        Call<TopicListResultBean> call = mService.getTopics();
        call.enqueue(new Callback<TopicListResultBean>() {
            @Override
            public void onResponse(Call<TopicListResultBean> call, Response<TopicListResultBean> response) {
                if (callback != null) {
                    if (response.body().code == BaseResultBean.CODE_SUCCEED) {
                        callback.suc(response.body());
                    } else {
                        callback.fail(response.body().msg, response.body().code);
                    }
                }
            }

            @Override
            public void onFailure(Call<TopicListResultBean> call, Throwable t) {
                if (callback != null) {
                    callback.fail(t.getMessage(), BaseResultBean.CODE_FAILED);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        mRtmClient.release();
        mRtmClient = null;
        mService = null;
        mContext = null;
    }

    public interface VideoCallService {

        @GET("topics")
        Call<TopicListResultBean> getTopics();
    }
}
