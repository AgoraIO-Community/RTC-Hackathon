package cn.bearever.likemosaic.call

import android.content.Context
import android.text.TextUtils
import android.util.Log
import cn.bearever.likemosaic.R
import cn.bearever.likemosaic.UidUtil
import cn.bearever.likemosaic.bean.MessageBean
import cn.bearever.likemosaic.bean.SelectTopicBean
import cn.bearever.likemosaic.bean.TopicBean
import cn.bearever.likemosaic.bean.TopicListResultBean
import cn.bearever.mingbase.BaseCallback
import cn.bearever.mingbase.app.mvp.BasePresenterIml
import cn.bearever.mingbase.app.util.ToastUtil
import io.agora.rtc.Constants.CONNECTION_CHANGED_INVALID_TOKEN
import io.agora.rtc.IRtcEngineEventHandlerEx
import io.agora.rtc.RtcEngine
import io.agora.rtc.mediaio.IVideoSink
import io.agora.rtc.video.VideoEncoderConfiguration
import io.agora.rtm.jni.CONNECTION_STATE
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author luoming
 * @date 2020/4/16
 */
class VideoCallPresenter(view: VideoCallContact.View?, context: Context?) :
        BasePresenterIml<VideoCallContact.View?, VideoCallContact.Model?>(view, context), VideoCallContact.Presenter {

    companion object {
        private const val TAG = "VideoCallPresenter"
    }

    private var mRtcEngine: RtcEngine? = null
    private var mChannel = ""
    //我对对方的好感度
    private var mLikeCountMe2Other = 50
    //对方对我的好感度
    private var mLikeCountOther2Me = 50
    private var mTimerCount = 0
    private val LOCK_LIKE_COUNT = Any()
    private lateinit var mTimer: Timer

    init {
        initEngineAndJoinChannel()
        initLikeTimer()
    }

    private fun initLikeTimer() {
        //每一秒钟将mLikeCount-1
        mTimer = Timer()
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                synchronized(LOCK_LIKE_COUNT) {
                    mLikeCountMe2Other--
                    mLikeCountOther2Me--
                }

                view?.refreshLike(mLikeCountOther2Me)

                if (mLikeCountMe2Other <= 0) {
                    //好感度为0，聊天结束
                    view?.localLikeEmpty()
                    mTimer.cancel()
                    return
                }
                if (mLikeCountOther2Me <= 0) {
                    view?.onUserLeft()
                    mTimer.cancel()
                }

                mTimerCount++
                if (mTimerCount == 5) {
                    view?.showQuitBtn()
                }

                if (mLikeCountOther2Me == 10) {
                    view?.showNote("对方对你的好感度降至冰点了！")
                }

                LikeManager.getInstance().setLikeCountMe2Other(mLikeCountMe2Other)
                LikeManager.getInstance().setLikeCountOther2Me(mLikeCountOther2Me)
                sendLike()
            }
        }, 1000, 1000)
    }

    private fun initEngineAndJoinChannel() {
        initializeEngine()
        setupVideoConfig()
    }


    private fun initializeEngine() {
        try {
            mRtcEngine = RtcEngine.create(context, context.getString(R.string.agora_app_id), object : IRtcEngineEventHandlerEx() {

                // 注册 onJoinChannelSuccess 回调。
                // 本地用户成功加入频道时，会触发该回调。
                override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                    super.onJoinChannelSuccess(channel, uid, elapsed)
                    Log.d(TAG, "onJoinChannelSuccess:" + uid)
                }

                // SDK 接收到第一帧远端视频并成功解码时，会触发该回调。
                // 可以在该回调中调用 setupRemoteVideo 方法设置远端视图。
                override fun onFirstRemoteVideoDecoded(uid: Int, width: Int, height: Int, elapsed: Int) {
                    view?.onUserJoin(uid)
                }

                override fun onConnectionStateChanged(state: Int, reason: Int) {
                    super.onConnectionStateChanged(state, reason)
                    Log.e(TAG, "错误了：" + reason)
                    if (reason == CONNECTION_CHANGED_INVALID_TOKEN) {
                        Log.e(TAG, "生成的token无效")
                    }
                }

            })
        } catch (e: Exception) {
            Log.e(TAG, Log.getStackTraceString(e))
            throw RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e))
        }
    }

    private fun setupVideoConfig() {
        mRtcEngine?.enableVideo()
        mRtcEngine?.setVideoEncoderConfiguration(VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x480,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT))
    }

    override fun initModel() {
        mModel = VideoCallModel(context)
        mModel?.registerMessage { message ->
            //接收到对方发送的消息
            if (message?.channel != mChannel) {
                return@registerMessage
            }

            when (message.key) {
                MessageBean.KEY_SELECT_TOPIC -> {
                    view?.receiveSelectTag(message.data as SelectTopicBean?)
                    view?.showNote(message.text)
                }

                MessageBean.KEY_REFRESH_TOPIC -> {
                    view?.refreshTags(message.data as ArrayList<TopicBean>)
                    view?.startRefreshAnimation(false)
                }

                MessageBean.KEY_REMOTE_LIKE_CHANGE -> {
                    val likeCount = message.data as Int
                    if (likeCount - mLikeCountOther2Me > 3) {
                        if (mLikeCountOther2Me < 100 && likeCount > 100) {
                            view?.showNote("对方对你的好感度增加了！")
                        } else if (mLikeCountOther2Me < 200 && likeCount > 200) {
                            view?.showNote("对方对你的好感度增加了！")
                        } else if (mLikeCountOther2Me < 300 && likeCount > 300) {
                            view?.showNote("对方对你的好感度报表啦！")
                        }
                    }
                    mLikeCountOther2Me = likeCount
                    view?.refreshLike(mLikeCountOther2Me)
                }

                MessageBean.KEY_QUIT_ROOM -> {
                    view?.onUserLeft()
                }
            }
//            if (!TextUtils.isEmpty(message.text)) {
//                ToastUtil.show(message.text)
//            }
        }
    }

    override fun setLocalVideoRenderer(sink: IVideoSink) {
        mRtcEngine?.setLocalVideoRenderer(sink)
        mRtcEngine?.startPreview()
    }

    override fun setRemoteVideoRenderer(uid: Int, sink: IVideoSink) {
        mRtcEngine?.setRemoteVideoRenderer(uid, sink)
    }

    override fun joinRoom(channel: String?, rtcToken: String?, rtmToken: String?, remoteUid: String?) {
        mChannel = channel ?: ""
        mModel?.loginRtm(rtmToken, channel, remoteUid)
        mRtcEngine?.joinChannelWithUserAccount(rtcToken, channel, UidUtil.getUid(context))
    }

    override fun quitRoom() {
        //发送离开频道的消息
        sendQuitRoomMessage()
        //
        mRtcEngine?.leaveChannel()
        mModel?.logoutRtm()
        mTimer.cancel()
    }

    private fun sendQuitRoomMessage() {
        val message = MessageBean<String>(mChannel)
        message.key = MessageBean.KEY_QUIT_ROOM
        mModel?.sendMessage(message)
    }

    override fun muteAudio(mute: Boolean) {
        mRtcEngine?.muteLocalAudioStream(mute)
    }

    override fun selectTopic(topicBean: TopicBean, isSelect: Boolean) {
        val message = MessageBean<SelectTopicBean>(mChannel)
        message.key = MessageBean.KEY_SELECT_TOPIC
        val selectTopicBean = SelectTopicBean()
        selectTopicBean.id = topicBean.id
        selectTopicBean.selected = isSelect
        message.data = selectTopicBean
        if (isSelect) {
            message.text = "对方选择了【" + topicBean.text + "】话题"
        } else {
            message.text = "对方取消了【" + topicBean.text + "】话题"
        }
        mModel?.sendMessage(message)
    }

    override fun addLike() {
        synchronized(LOCK_LIKE_COUNT) {
            mLikeCountMe2Other+=2
        }
    }

    private fun sendLike() {
        val message = MessageBean<Int>(mChannel)
        message.key = MessageBean.KEY_REMOTE_LIKE_CHANGE
        message.data = mLikeCountMe2Other
        mModel?.sendMessage(message)
    }

    override fun refreshTopics() {
        mModel?.getTopics(object : BaseCallback<TopicListResultBean>() {
            override fun suc(data: TopicListResultBean) {
                view?.refreshTags(data.list)
                val message = MessageBean<ArrayList<TopicBean>>(mChannel)
                message.key = MessageBean.KEY_REFRESH_TOPIC
                message.data = data.list
                message.text = "对方刷新了话题区"
                mModel?.sendMessage(message)
            }
        })
    }
}