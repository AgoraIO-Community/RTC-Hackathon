package cn.bearever.likemosaic.call

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import cn.bearever.likemosaic.Constant
import cn.bearever.likemosaic.R
import cn.bearever.likemosaic.bean.MatchResultBean
import cn.bearever.likemosaic.bean.SelectTopicBean
import cn.bearever.likemosaic.bean.TopicBean
import cn.bearever.likemosaic.home.MosaicVideoSink
import cn.bearever.mingbase.app.mvp.BaseActivity
import cn.bearever.mingbase.app.util.DipPxUtil
import cn.bearever.mingbase.app.util.ToastUtil
import cn.bearever.mingbase.app.view.OnDoubleTouchListener
import com.jaeger.library.StatusBarUtil
import io.agora.rtc.RtcEngine
import io.agora.rtc.mediaio.IVideoSink
import kotlinx.android.synthetic.main.activity_video_chat_view.*

/**
 * 视频聊天页面
 *
 * @author bear
 */
class VideoCallActivity : BaseActivity<VideoCallPresenter?>(), VideoCallContact.View, View.OnClickListener {

    companion object {
        private val TAG = VideoCallActivity::class.java.simpleName
        private val MAX_SELECT_TOPIC_COUNT = 3
        private val MAX_TOPIC_COUNT = 9
    }

    private var mMuted = false
    var mCallEnd = false
    private var mMatchResultBean: MatchResultBean? = null
    private var mOtherSelectTopicIdList = ArrayList<Int>()
    private var mRemoteView: View? = null
    private var mLocalView: View? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video_chat_view
    }

    override fun initData(saveInstanceState: Bundle?) {
        if (intent != null) {
            val intent = intent
            mMatchResultBean = intent.getSerializableExtra(Constant.KEY_MATCH_BEAN) as MatchResultBean?
        }
        if (saveInstanceState != null) {
            mMatchResultBean = saveInstanceState.getSerializable(Constant.KEY_MATCH_BEAN) as MatchResultBean?
        }

        if (mMatchResultBean == null) {
            endCall()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mMatchResultBean.let {
            outState.putSerializable(Constant.KEY_MATCH_BEAN, it)
        }
        super.onSaveInstanceState(outState)
    }

    override fun initView() {
        StatusBarUtil.setTransparentForImageView(this, btn_mute)
        iv_like.clearAnimation()
        (iv_like.getDrawable() as AnimationDrawable).start()
        if (mMatchResultBean?.list != null) {
            refreshTags(mMatchResultBean?.list!!)
        }
        btn_back.setOnClickListener {
            endCall()
        }
        btn_refresh.setOnClickListener {
            startRefreshAnimation(true)
        }

        remote_video_view_container.setOnTouchListener(object : OnDoubleTouchListener() {
            override fun onDoubleClick(v: View?, x: Float, y: Float) {
                //执行双击屏幕的动画
                startDoubleClickAnimation(v!!, x, y)
                mPresenter?.addLike()
            }
        })
        showNote("快速选择几个你感兴趣的话题吧！也许会发现兴趣重叠的话题哦！")
    }

    private fun startDoubleClickAnimation(v: View, x: Float, y: Float) {
        if (remote_video_view_container.childCount > 5) {
            Log.d(TAG, "点赞动画太多了！")
            return
        }

        val imageView = ImageView(this)
        val p = FrameLayout.LayoutParams(DipPxUtil.dip2px(300F), DipPxUtil.dip2px(300F))
        imageView.layoutParams = p
        imageView.setImageDrawable(resources.getDrawable(R.drawable.drawable_like_click))
        imageView.setTag("likeClickanimation")
        remote_video_view_container.addView(imageView)
        imageView.measure(0, 0)
        imageView.x = x - p.width / 2
        imageView.y = y - p.height

        val animation = (imageView.drawable as AnimationDrawable)
        animation.start()
        remote_video_view_container.postDelayed(object : Runnable {
            override fun run() {
                imageView.clearAnimation()
                remote_video_view_container.removeView(imageView)
            }
        }, 1500)
    }

    override fun startRefreshAnimation(request: Boolean) {
        btn_refresh.isEnabled = false
        val rotateAnimation = RotateAnimation(0F, 180F, btn_refresh.width / 2F, btn_refresh.height / 2.0F)
        rotateAnimation.duration = 300

        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                if (request) {
                    mPresenter?.refreshTopics()
                }
                btn_refresh.isEnabled = true
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        btn_refresh.clearAnimation()
        btn_refresh.startAnimation(rotateAnimation)
    }

    override fun initPresenter() {
        mPresenter = VideoCallPresenter(this, this)
        startCall()
    }

    override fun refreshTags(topicList: List<TopicBean>) {
        val selectList = ArrayList<Int>()
        for (i in (fl_tag.childCount - 1) downTo 0) {
            val child = fl_tag.getChildAt(i)
            if (child.id == R.id.btn_refresh || child.isSelected) {
                continue
            }
            if (child.isSelected) {
                selectList.add(child.id)
            }

            fl_tag.removeView(child)
        }
        for (topic in topicList) {
            if (fl_tag.childCount >= MAX_TOPIC_COUNT) {
                break
            }
            if (selectList.indexOf(topic.id) >= 0) {
                continue
            }
            val view = createTopicView(topic)
            fl_tag.addView(view)
        }
    }

    private fun createTopicView(topic: TopicBean): View {
        val textView = TextView(this)
        textView.text = topic.text
        textView.setPadding(resources.getDimension(R.dimen.tag_padding_left).toInt(),
                resources.getDimension(R.dimen.tag_padding_top).toInt(),
                resources.getDimension(R.dimen.tag_padding_right).toInt(),
                resources.getDimension(R.dimen.tag_padding_bottom).toInt())
        textView.setBackgroundResource(R.drawable.drawable_tag)
        textView.setTextColor(Color.WHITE)
        textView.setTag(topic.id)
        textView.setOnClickListener(this)
        return textView
    }


    override fun onClick(v: View?) {
        val id = v?.tag as Int
        val selected = v.isSelected
        //选中的话需要判断是否超出最大可选数量
        if (!selected) {
            var count = 0
            for (i in 0 until fl_tag.childCount) {
                val child = fl_tag.getChildAt(i)
                if (child.isSelected) {
                    count += 1
                }
            }
            if (count >= MAX_SELECT_TOPIC_COUNT) {
                showNote("最多可以选择" + MAX_SELECT_TOPIC_COUNT + "个话题")
                return
            }
        }

        v.isSelected = !selected
        if (v.isSelected && mOtherSelectTopicIdList.indexOf(id) >= 0) {
            v.setBackgroundResource(R.drawable.drawable_tag_both)
        } else {
            v.setBackgroundResource(R.drawable.drawable_tag)
        }
        val topicBean = TopicBean(id)
        topicBean.text = (v as TextView).text.toString()
        mPresenter?.selectTopic(topicBean, !selected)
    }

    override fun receiveSelectTag(selectTopicBean: SelectTopicBean?) {
        if (mOtherSelectTopicIdList.indexOf(selectTopicBean?.id) < 0) {
            mOtherSelectTopicIdList.add(selectTopicBean?.id ?: 0)
        }
        for (i in (fl_tag.childCount - 1) downTo 0) {
            val child = fl_tag.getChildAt(i)
            if (child.tag is Int && child.tag == selectTopicBean?.id) {
                if (selectTopicBean?.selected == true && child.isSelected) {
                    child.setBackgroundResource(R.drawable.drawable_tag_both)
                } else {
                    child.setBackgroundResource(R.drawable.drawable_tag)
                }
            }
        }
    }

    override fun refreshLike(likeCount: Int) {
        runOnUiThread {
            pb_like.progress = likeCount
            tv_like.text = "" + likeCount + "/300"
        }
    }

    override fun onUserLeft() {
        runOnUiThread {
            ToastUtil.show("对方退出聊天")
            endCall()
        }
        LikeManager.getInstance().reset()
    }

    override fun onUserJoin(uid: Int) {
        runOnUiThread {
            setupRemoteVideo(uid)
            setupLocalVideo()
        }
    }

    override fun localLikeEmpty() {
        runOnUiThread {
            ToastUtil.show("您对对方的好感度降低为0，聊天已结束")
            endCall()
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        val count = remote_video_view_container?.childCount ?: 0
        var view: View? = null
        for (i in 0 until count) {
            val v = remote_video_view_container?.getChildAt(i)
            if (v?.tag is Int && (v.tag as Int) == uid) {
                view = v
                break
            }
        }
        if (view != null) {
            return
        }
        mRemoteView = MosaicVideoSink(this, false)
        remote_video_view_container?.addView(mRemoteView)

        mPresenter?.setRemoteVideoRenderer(uid, mRemoteView as IVideoSink)
        mRemoteView?.setTag(uid)
    }

    private fun removeRemoteVideo() {
        if (mRemoteView != null) {
            remote_video_view_container?.removeView(mRemoteView)
        }
        mRemoteView = null
    }

    private fun setupLocalVideo() {
        removeLocalVideo()
        mLocalView = MosaicVideoSink(this, true)
        local_video_view_container?.addView(mLocalView)
        mPresenter?.setLocalVideoRenderer(mLocalView as IVideoSink)
    }

    private fun joinChannel() {
        mPresenter?.joinRoom(mMatchResultBean?.channel, mMatchResultBean?.rtcToken,
                mMatchResultBean?.rtmToken, mMatchResultBean?.remoteUid)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!mCallEnd) {
            leaveChannel()
        }
        RtcEngine.destroy()
    }

    private var mLastShowNoteTime = 0L
    override fun showNote(note: String?) {
        runOnUiThread {
            tv_note.text = note
            tv_note.visibility = View.VISIBLE
            tv_note.removeCallbacks(null)
            tv_note.postDelayed(object : Runnable {
                override fun run() {
                    if (System.currentTimeMillis() - mLastShowNoteTime > 4000) {
                        tv_note.visibility = View.GONE
                    }
                }
            }, 5000)
            mLastShowNoteTime = System.currentTimeMillis()
        }
    }

    private fun leaveChannel() {
        mPresenter?.quitRoom()
        finish()
    }

    fun onLocalAudioMuteClicked(view: View?) {
        mMuted = !mMuted
        mPresenter?.muteAudio(mMuted)
        btn_mute?.isSelected = mMuted
    }

    private fun startCall() {
        setupLocalVideo()
        joinChannel()
    }

    private fun endCall() {
        removeLocalVideo()
        removeRemoteVideo()
        leaveChannel()
    }

    private fun removeLocalVideo() {
        if (mLocalView != null) {
            local_video_view_container?.removeView(mLocalView)
        }
        mLocalView = null
    }

    override fun showQuitBtn() {
        runOnUiThread {
            btn_back.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        //屏蔽返回操作，仅允许通过点击返回按钮退出房间
//        super.onBackPressed()
    }

}