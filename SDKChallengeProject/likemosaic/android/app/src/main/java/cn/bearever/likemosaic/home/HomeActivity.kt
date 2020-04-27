package cn.bearever.likemosaic.home

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.animation.addListener
import cn.bearever.likemosaic.Constant
import cn.bearever.likemosaic.R
import cn.bearever.likemosaic.call.VideoCallActivity
import cn.bearever.likemosaic.bean.MatchResultBean
import cn.bearever.likemosaic.call.VideoCallContact
import cn.bearever.likemosaic.call.VideoCallPresenter
import cn.bearever.mingbase.app.mvp.BaseActivity
import cn.bearever.mingbase.app.permission.AsyncPermission
import cn.bearever.mingbase.app.util.DipPxUtil
import cn.bearever.mingbase.app.util.ScreenUtil
import cn.bearever.mingbase.app.util.ToastUtil
import com.jaeger.library.StatusBarUtil
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.IRtcEngineEventHandlerEx
import io.agora.rtc.RtcEngine
import io.agora.rtc.mediaio.IVideoSink
import io.agora.rtc.video.VideoEncoderConfiguration
import io.agora.rtm.RtmCallEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_video_chat_view.*
import java.lang.Exception


/**
 *  主页面
 *
 * @author luoming
 * @date 2020/4/11
 */
class HomeActivity : BaseActivity<HomePresenter>(), HomeContact.View {
    private lateinit var animationStart: ValueAnimator
    private lateinit var animationStop: ValueAnimator
    private var mOtherPositionStart = 0F
    private var mMePositionStart = 0F
    private var mLoadingPositionStart = 0F
    private var mBtnPositionStart = 0F

    private var mOtherPositionEnd = 0F
    private var mMePositionEnd = 0F
    private var mLoadingPositionEnd = 0F
    private var mBtnPositionEnd = 0F

    private var mRtcEngine: RtcEngine? = null


    companion object {
        private val TAG = "HomeActivity"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        mPresenter = HomePresenter(this, this)
        Log.d(TAG, "initPresenter")
        initRtcEngine()
    }

    override fun initView() {
        StatusBarUtil.setTransparent(this)
        btn_match.setOnClickListener {
            it.isEnabled = false
            it.isSelected = !it.isSelected
            mStartCallVideo = false
            if (it.isSelected) {
                requestPermission()
            } else {
                mPresenter?.stopMatch()
                stopMatch()
            }
        }
        relativeLayout.post {
            setupInitLayout()
        }
    }

    private fun setupInitLayout() {
        setupAnimationPosition()
        fl_remote_container.y = mOtherPositionStart
        iv_loading.y = mLoadingPositionStart
        fl_mine_root.y = mMePositionStart
        btn_match.y = mBtnPositionStart
        mMePositionStart = fl_mine_root.y
        btn_match.isSelected = false
    }

    private fun setupAnimationPosition() {
        if (mOtherPositionStart == 0F) {
            fl_remote_container.measure(0, 0)
            fl_mine_root.measure(0, 0)
            iv_loading.measure(0, 0)
            mOtherPositionStart = (-fl_remote_container.measuredHeight).toFloat()
            mMePositionStart = ScreenUtil.getHeight(this) / 3F
            mLoadingPositionStart = (-DipPxUtil.dip2px(14F)).toFloat()
            mBtnPositionStart = ScreenUtil.getHeight(this) * 0.7F

            mOtherPositionEnd = ScreenUtil.getHeight(this) * 0.45F - fl_remote_container.measuredHeight
            mMePositionEnd = ScreenUtil.getHeight(this) * 0.55F
            mLoadingPositionEnd = ScreenUtil.getHeight(this) / 2F - iv_loading.measuredHeight
            mBtnPositionEnd = mMePositionEnd + fl_mine_root.measuredHeight +
                    (ScreenUtil.getHeight(this) - mMePositionEnd - fl_mine_root.measuredHeight) / 2 -
                    iv_loading.measuredHeight
        }
    }

    private fun requestPermission() {
        AsyncPermission.with(this).requestNoTest(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onAllGranted {
                    //request match
                    startMatch()
                }
                .onDenied { permissions ->
                    ToastUtil.show("缺少" + permissions[0].message)
                    btn_match.isEnabled = true
                }
    }

    override fun startMatch() {
        btn_match.isSelected = true
        if (!this::animationStart.isInitialized) {
            animationStart = ObjectAnimator.ofFloat(0F, 100F)
            animationStart.duration = 500
            animationStart.addListener(onEnd = {
                val drawable = iv_loading.drawable as AnimationDrawable
                drawable.start()
                mPresenter.requestMatch()
                btn_match.isEnabled = true
                btn_match.text = "停止匹配"
            })

            animationStart.addUpdateListener {
                val percent = it.animatedValue as Float / 100F
                //对方的画面
                val y1 = mOtherPositionStart + (mOtherPositionEnd - mOtherPositionStart) * percent
                fl_remote_container.y = y1
                //我的画面
                val y2 = mMePositionStart + (mMePositionEnd - mMePositionStart) * percent
                fl_mine_root.y = y2
                //匹配按钮
                val y3 = mBtnPositionStart + (mBtnPositionEnd - mBtnPositionStart) * percent
                btn_match.y = y3
                //loading图标
                val y4 = mLoadingPositionStart + (mLoadingPositionEnd - mLoadingPositionStart) * percent
                iv_loading.y = y4
            }
        }
        animationStart.cancel()
        animationStart.start()
    }

    override fun stopMatch() {
        btn_match.isSelected = false
        if (!this::animationStop.isInitialized) {
            animationStop = ObjectAnimator.ofFloat(100F, 0F)
            animationStop.duration = 500
            animationStop.addListener(onEnd = {
                btn_match.isEnabled = true
                val drawable = iv_loading.drawable as AnimationDrawable
                drawable.stop()
                btn_match.setText(R.string.start_match)
            })

            animationStop.addUpdateListener {
                val percent = it.animatedValue as Float / 100F
                //计算按钮的位置
                //对方的画面
                val y1 = mOtherPositionStart + (mOtherPositionEnd - mOtherPositionStart) * percent
                fl_remote_container.y = y1
                //我的画面
                val y2 = mMePositionStart + (mMePositionEnd - mMePositionStart) * percent
                fl_mine_root.y = y2
                //匹配按钮
                val y3 = mBtnPositionStart + (mBtnPositionEnd - mBtnPositionStart) * percent
                btn_match.y = y3
                //loading图标
                val y4 = mLoadingPositionStart + (mLoadingPositionEnd - mLoadingPositionStart) * percent
                iv_loading.y = y4
            }
        }
        animationStop.cancel()
        animationStop.start()
    }

    override fun matchFailed(msg: String) {
        ToastUtil.show(msg)
        btn_match.isEnabled = false
        stopMatch()
    }

    override fun matchSucceed(matchResultBean: MatchResultBean) {
        goVideoChat(matchResultBean)
        btn_match.isEnabled = true
        btn_match.setText(R.string.start_match)
    }

    private fun setupLocalVideo() {
        var localView: MosaicVideoSink? = null
        for (i in 0 until fl_mine_container.childCount) {
            val child = fl_mine_container.getChildAt(i)
            if (child is MosaicVideoSink) {
                localView = child
                break
            }
        }
        if (localView == null) {
            localView = MosaicVideoSink(this, true)
            localView.tag = "mine"
            fl_mine_container.addView(localView)
        }
        mRtcEngine?.setLocalVideoRenderer(localView)
        if (AsyncPermission.with(this).checkNoTest(Manifest.permission.CAMERA)) {
            fl_mine_container.post {
                mRtcEngine?.startPreview()
            }
        }
    }

    private var mStartCallVideo = false
    private fun goVideoChat(matchResultBean: MatchResultBean) {
        btn_match.isSelected = false
        mStartCallVideo = true

        intent = Intent()
        intent.setClass(this, VideoCallActivity::class.java)
        intent.putExtra(Constant.KEY_MATCH_BEAN, matchResultBean)
        startActivity(intent)
    }

    private val mRtcEventHandler = object : IRtcEngineEventHandler() {

    }

    private fun initRtcEngine() {
        if (mRtcEngine == null) {
            try {
                mRtcEngine = RtcEngine.create(this, getString(R.string.agora_app_id), mRtcEventHandler)
                mRtcEngine?.enableVideo()
                mRtcEngine?.setVideoEncoderConfiguration(VideoEncoderConfiguration(
                        VideoEncoderConfiguration.VD_120x120,
                        VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                        VideoEncoderConfiguration.STANDARD_BITRATE,
                        VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        initRtcEngine()
        setupLocalVideo()
    }

    private fun removeLocalVideo() {
        mRtcEngine?.stopPreview()
        fl_mine_container.removeAllViews()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
        if (this::animationStart.isInitialized) {
            animationStart.cancel()
        }
        if (this::animationStop.isInitialized) {
            animationStop.cancel()
        }
        if (!mStartCallVideo) {
            removeLocalVideo()
        }

        setupInitLayout()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        RtcEngine.destroy()
        mRtcEngine?.removeHandler(mRtcEventHandler)
        mRtcEngine = null
    }

}