package com.zero.game.component.activity

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.zero.game.R
import com.zero.game.component.view.PlayView
import com.zero.game.utils.frame.VideoFrameHandler
import com.zero.game.repository.model.FaceState
import com.zero.game.repository.model.Role
import com.zero.game.repository.model.Room
import com.zero.game.repository.model.RoomLeave
import com.zero.game.repository.network.RetrofitFactory
import com.zero.game.utils.AppConfig
import com.zero.game.utils.file.FileUtil
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import kotlinx.android.synthetic.main.activity_call.*
import kotlinx.coroutines.runBlocking


class VideoCallActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context, room: Room, role: Role) {
            val intent = Intent(context, VideoCallActivity::class.java)
            intent.putExtra("room", room)
            intent.putExtra("role", role)
            context.startActivity(intent)
        }
    }


    private lateinit var rtcEngine: RtcEngine
    private var videoFrameHandler = VideoFrameHandler()
    private var streamId: Int = 0
    private var role = Role.RECEIVE_PLAYER
    private lateinit var room: Room
    private var count = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var mLocalView: SurfaceView
    private lateinit var mRemoteView: SurfaceView

    private var guoGetCount = 0

    class RadiusViewOutlineProvider(private val radius: Float) : ViewOutlineProvider() {

        override fun getOutline(view: View?, outline: Outline?) {
            val rect = Rect()
            view?.getGlobalVisibleRect(rect)
            val leftMargin = 0
            val topMargin = 0
            val selfRect = Rect(
                leftMargin, topMargin,
                rect.right - rect.left - leftMargin, rect.bottom - rect.top - topMargin
            );
            outline?.setRoundRect(selfRect, radius);
        }
    }

    private val viewOutlineProvider =
        RadiusViewOutlineProvider(
            16f
        )
    private val viewOutlineProvider2 =
        RadiusViewOutlineProvider(
            AppConfig.dp2px(30).toFloat()
        )


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );

        role = intent.getSerializableExtra("role") as Role
        room = intent.getSerializableExtra("room") as Room
        setContentView(R.layout.activity_call)

        group_play.visibility = View.VISIBLE
        group_result.visibility = View.GONE

        if (role == Role.THROW_PLAYER) {
            iv_game_start.visibility = View.VISIBLE
        } else {
            iv_game_start.visibility = View.GONE
        }
        iv_game_start.isEnabled = false

        rtcEngine = RtcEngine.create(this, AppConfig.appKey,
            object : IRtcEngineEventHandler() {
                override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                    Log.i("ldh", "Join channel success, uid: $uid")
                }

                override fun onFirstRemoteVideoDecoded(
                    uid: Int,
                    width: Int,
                    height: Int,
                    elapsed: Int
                ) {
                    Log.i("ldh", "First remote video decoded, uid: $uid")
                    runOnUiThread { setupRemoteVideo(uid) }
                }

                override fun onUserOffline(uid: Int, reason: Int) {
                    Log.i("ldh", "onUserOffline, uid: $uid")
                }

                override fun onStreamMessage(uid: Int, s: Int, data: ByteArray?) {
                    runOnUiThread {

                        data?.let {
                            val string = String(it)
                            when (string) {
                                "left" -> {
                                    play_view.addPoint(FaceState.LEFT)
                                }
                                "right" -> {
                                    play_view.addPoint(FaceState.RIGHT)
                                }
                                "front" -> {
                                    play_view.addPoint(FaceState.FRONT)
                                }
                                "start" -> {
                                    gameStart()
                                    play_view.startGame()
                                }
                                "end" -> {
                                    if ((guoGetCount > 3)) {
                                        endGame(false)
                                    } else {
                                        endGame(true)
                                    }
                                }
                            }
                            if (string.startsWith("points")) {
                                tv_point.text = string.replace("points", "")
                                try {
                                    guoGetCount = string.replace("points", "").toInt()
                                } catch (e: Exception) {

                                }

                            }
                        }
                    }
                }
            })
        streamId = rtcEngine.createDataStream(true, true)
        setupLocalVideo()
        join()
        videoFrameHandler.register(object : VideoFrameHandler.IOnCallback {
            override fun onBitmap(bitmap: Bitmap) {

            }

            override fun onFaceState(faceState: FaceState) {
                runOnUiThread {
                    play_view.setFaceState(faceState)
                }
            }

            override fun onVFaceState(faceState: FaceState) {
            }
        })

        play_view.playerCallback = object : PlayView.PlayerCallback {
            override fun onGet() {
                guoGetCount++
                tv_point.text = "$guoGetCount"
                send("points${guoGetCount}")
            }

            override fun onPointsFlyAway(state: FaceState) {
                runOnUiThread {
                    send(state)
                }
            }
        }
        //test()
    }

    private fun gameStart() {
        count = 0
        guoGetCount = 0
        tv_point.text = "${guoGetCount}"
        if (role == Role.THROW_PLAYER) {
            iv_game_start.visibility = View.GONE
            send("start")
            throwRunnable()
            play_view.startGame()
        } else {
            play_view.startGame()
        }
    }

    private fun throwRunnable() {
        handler.postDelayed({
            count++
            if (count >= 30) {
                handler.postDelayed({
                    send("end")
                    if (guoGetCount <= 3) {
                        endGame(false)
                    } else {
                        endGame(true)
                    }
                    guoGetCount = 0
                }, 10000)
            } else {
                play_view.sendPoint(play_view.currentState)
                throwRunnable()
            }
        }, 1000)
    }

    override fun onBackPressed() {
        val dialog = showLoading()
        runBlocking {
            try {
                RetrofitFactory.getPlayHeadApi().leave(RoomLeave(room_id = room.id))
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                dialog.dismiss()
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        leaveChannel()
        videoFrameHandler.unRegister()
    }

    private fun setupLocalVideo() { // 启用视频模块。
        rtcEngine.enableVideo()
        // 创建 SurfaceView 对象。
        mLocalView = RtcEngine.CreateRendererView(baseContext)
        mLocalView.setZOrderMediaOverlay(true)
        mLocalView.outlineProvider = viewOutlineProvider
        mLocalView.clipToOutline = true
        local_video.addView(mLocalView)
        // 设置本地视图。
        rtcEngine.setupLocalVideo(VideoCanvas(mLocalView, VideoCanvas.RENDER_MODE_HIDDEN, 0))
    }

    private fun setupRemoteVideo(uid: Int) { // 创建一个 SurfaceView 对象。
        mRemoteView = RtcEngine.CreateRendererView(baseContext)
        mRemoteView.setZOrderMediaOverlay(true)
        mRemoteView.outlineProvider = viewOutlineProvider
        mRemoteView.clipToOutline = true
        remote_video.addView(mRemoteView)
        rtcEngine.setupRemoteVideo(VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid))

        runOnUiThread {
            if (role == Role.THROW_PLAYER) {
                iv_game_start.isEnabled = true
                iv_game_start.setOnClickListener {
                    gameStart()
                }
            }
        }
    }

    private fun join() {
        val id = rtcEngine.joinChannel(room.token, room.channel, "", room.uid);
        Log.i("ldh", "join" + id);
    }

    private fun switchRole() {
        role = if (role == Role.THROW_PLAYER) {
            Role.RECEIVE_PLAYER
        } else {
            Role.THROW_PLAYER
        }
    }

    private fun leaveChannel() {
        rtcEngine.leaveChannel()
        RtcEngine.destroy()
    }

    private fun send(str: String) {
        rtcEngine.sendStreamMessage(streamId, str.toByteArray())
    }

    private fun send(faceState: FaceState) {
        when (faceState) {
            FaceState.LEFT -> {
                send("left")
            }
            FaceState.FRONT -> {
                send("front")
            }
            FaceState.RIGHT -> {
                send("right")
            }
            else -> {

            }
        }
    }

    private fun endGame(win: Boolean) {
        group_play.visibility = View.GONE
        group_result.visibility = View.VISIBLE
        local_video.removeView(mLocalView)
        local_result_view.addView(mLocalView)
        mLocalView.outlineProvider = viewOutlineProvider2
        if (win) {
            if (role == Role.THROW_PLAYER) {
                result_view.setBackgroundResource(R.mipmap.success1)
            } else {
                result_view.setBackgroundResource(R.mipmap.success2)
            }
        } else {
            if (role == Role.THROW_PLAYER) {
                result_view.setBackgroundResource(R.mipmap.fail2)
            } else {
                result_view.setBackgroundResource(R.mipmap.fail1)
            }
        }
        save_btn.setOnClickListener {
            save_btn.isEnabled = false
            videoFrameHandler.capture(object : VideoFrameHandler.IOnCapture {
                override fun onCapture(bitmap: Bitmap) {
                    runOnUiThread {

                        save_btn.visibility = View.GONE
                        local_result_view.visibility = View.GONE
                        val bigBg = FileUtil.getBitmapByView(result_view)!!
                        save_btn.visibility = View.VISIBLE
                        local_result_view.visibility = View.VISIBLE
                        val bitmapCenter = Bitmap.createBitmap(
                            bitmap,
                            0,
                            (bitmap.height - bitmap.width) / 2,
                            bitmap.width,
                            bitmap.width
                        )
                        val w = bitmapCenter.width
                        val h = bitmapCenter.height
                        val w1 = AppConfig.dp2px(60)
                        val h1 = AppConfig.dp2px(60)
                        val sx = w1.toFloat() / w.toFloat()
                        val sy = h1.toFloat() / h.toFloat()
                        val matrix = Matrix()
                        matrix.postScale(sx, sy)
                        val avatarBitmap =
                            Bitmap.createBitmap(bitmapCenter, 0, 0, w, h, matrix, true)
                        val circleBitmap = Bitmap.createBitmap(
                            avatarBitmap.width,
                            avatarBitmap.height,
                            Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(circleBitmap)
                        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                        canvas.drawCircle(
                            avatarBitmap.width.toFloat() / 2,
                            avatarBitmap.height.toFloat() / 2,
                            AppConfig.dp2px(30).toFloat(),
                            paint
                        )
                        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                        canvas.drawBitmap(avatarBitmap, 0f, 0f, paint)

                        canvas.setBitmap(bigBg)
                        val paint2 = Paint(Paint.ANTI_ALIAS_FLAG)
                        canvas.drawBitmap(bigBg, 0f, 0f, paint2)
                        canvas.drawBitmap(
                            circleBitmap,
                            (bigBg.width / 2 - circleBitmap.width / 2 - AppConfig.dp2px(3)).toFloat(),
                            (bigBg.height.toFloat() - circleBitmap.height) / 2,
                            paint2
                        )


                        FileUtil.saveBitmap(bigBg, object : FileUtil.IOnSaveBitmapListener {
                            override fun onSuccess() {
                                save_btn.isEnabled = true
                                toast(R.string.save_success)
                            }

                            override fun onError() {
                                save_btn.isEnabled = true
                            }
                        })
                    }
                }
            })
        }
        btn_back.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        group_play.visibility = View.VISIBLE
        group_result.visibility = View.GONE
        local_result_view.removeView(mLocalView)
        local_video.addView(mLocalView)
        count = 0
        guoGetCount = 0
        tv_point.text = "${guoGetCount}"
        if (role == Role.THROW_PLAYER) {
            iv_game_start.visibility = View.VISIBLE
        }
        mLocalView.outlineProvider = viewOutlineProvider
    }

}