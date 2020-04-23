package com.pzj.visualcall.server;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.ss.ScreenSharingClient;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

/**
 * 音视频通话界面
 *
 * 流程
 * 1.初始化RtcEngine
 * 2.设置本地视图
 * 3.加入频道
 * 4.设置远端视图
 * 5.离开频道
 * 6.销毁RtcEngine
 */
public class VideoActivity extends AppCompatActivity {


    private static String TAG="VideoActivity";

    // App 运行时确认麦克风和摄像头设备的使用权限。
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    private RtcEngine mRtcEngine;
    //标记是否正在拨打
    private boolean mCalling;
    //标记本地是否静音
    private boolean mMuted;

    @BindView(R.id.local_video_view_container)
    FrameLayout mLocalContainer;
    @BindView(R.id.remote_video_view_container)
    RelativeLayout mRemoteContainer;

    @BindView(R.id.iv_call)
    ImageView iv_call;
    @BindView(R.id.iv_mute)
    ImageView iv_mute;
    @BindView(R.id.iv_switch_camera)
    ImageView iv_switch_camera;

    @BindView(R.id.bt_screen)
    Button bt_screen;

    private SurfaceView mLocalView;
    private SurfaceView mRemoteView;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_chat_view);

        //初始化 View绑定框架
        ButterKnife.bind(this);

        //沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        //设置状态栏白色字体和图标
        QMUIStatusBarHelper.setStatusBarDarkMode(this);

        //权限请求
        requestPermission();
    }

    //初始化音视频通话引擎
    private void initializeEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
            Log.d(TAG, "initializeEngine: 初始化音视频通话引擎成功");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    //设置音视频通话配置
    private void setupVideoConfig() {
        //配置前结束通话
        mRtcEngine.enableVideo();
        //https://docs.agora.io/cn/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1video_1_1_video_encoder_configuration.html
        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(
                //分辨率
                VideoEncoderConfiguration.VD_640x360,
                //帧率（每秒15帧）
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                //码率模式（标准）
                VideoEncoderConfiguration.STANDARD_BITRATE,
                //视频方向
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT)
        );
    }

    //设置本地端视图
    private void setupLocalVideo() {
        mLocalView = RtcEngine.CreateRendererView(getBaseContext());
        mLocalView.setZOrderMediaOverlay(true);
        mLocalContainer.addView(mLocalView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(mLocalView, VideoCanvas.RENDER_MODE_HIDDEN, 0));



    }

    //开启本地摄像头预览
    private void startPreviewLocalVideo(){
        mRtcEngine.enableLocalAudio(true);
        mRtcEngine.enableLocalVideo(true);
        mRtcEngine.startPreview();
    }



    //设置远程端视图
    private void setupRemoteVideo(int uid) {
        int count = mRemoteContainer.getChildCount();
        View view = null;
        for (int i = 0; i < count; i++) {
            View v = mRemoteContainer.getChildAt(i);
            if (v.getTag() instanceof Integer &&((int) v.getTag()) == uid ) {
                view = v;
            }
        }

        if (view != null) {
            mRemoteContainer.removeView(view);
        }

        mRemoteView = RtcEngine.CreateRendererView(getBaseContext());
        mRemoteContainer.addView(mRemoteView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        mRemoteView.setTag(uid);


    }


    //加入频道
    private void joinChannel() {

        String accessToken = getString(R.string.agora_access_token);
        if (accessToken.equals("")) {
            accessToken = null; // default, no token
        }

        mRtcEngine.joinChannel(accessToken, MyApplication.CHANNEL_NAME_1, "Extra Optional Data", MyApplication.CALL_UID); // 未指定 uid，SDK 会自动分配一个。
    }

    //离开频道
    private void leaveChannel() {
        if (mRtcEngine!=null){
            mRtcEngine.leaveChannel();
        }
    }



    //音视频通话流程回调
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // 注册 onJoinChannelSuccess 回调。
        // 本地用户成功加入频道时，会触发该回调。
        public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
            Log.d(TAG, "onJoinChannelSuccess: 本地用户成功加入频道成功");
            Log.i("agora","Join channel success, uid: " + (uid & 0xFFFFFFFFL));



        }


        // 注册 onFirstRemoteVideoDecoded 回调。
        // SDK 接收到第一帧远端视频并成功解码时，会触发该回调。
        // 可以在该回调中调用 setupRemoteVideo 方法设置远端视图。
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "onFirstRemoteVideoDecoded: 接收到第一帧远端视频并成功解码");
                    Log.i("agora","onFirstRemoteVideoDecoded, uid: " + (uid & 0xFFFFFFFFL));
                    //设置远端视图
                    //setupRemoteVideo(uid);
                }
            });
        }

//        远端用户/主播调用 joinChannel 方法加入频道
//        远端用户加入频道后调用 setClientRole 将用户角色改变为主播
//        远端用户/主播网络中断后重新加入频道
//        远端用户通过调用 addInjectStreamUrl 方法成功输入在线媒体流
        @Override
        public void onUserJoined(final int uid, int elapsed) {
            Log.i(TAG, "onUserJoined: " + (uid&0xFFFFFFL));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupRemoteVideo(uid);
                    }
                });
        }



        @Override
        // 注册 onUserOffline 回调。
        // 远端用户离开频道或掉线时，会触发该回调。
        public void onUserOffline(final int uid, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "onUserOffline: 远端用户离开频道或掉线");
                    Log.i("agora","User offline, uid: " + (uid & 0xFFFFFFFFL));
                    //设置用户离开或掉线视图
                    //onRemoteUserLeft();
                    try {
                        int count = mRemoteContainer.getChildCount();
                        View view = null;
                        for (int i = 0; i < count; i++) {
                            View v = mRemoteContainer.getChildAt(i);
                            if (v.getTag() instanceof Integer &&((int) v.getTag()) == uid ) {
                                view = v;
                            }
                        }

                        if (view != null) {
                            mRemoteContainer.removeView(view);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

    };


    //权限请求
    public void requestPermission() {
        XXPermissions.with(this)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
                .permission(REQUESTED_PERMISSIONS)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            Log.d(TAG, "hasPermission: 获取权限成功");
                            //初始化RtcEngine
                            initializeEngine();
                            //设置音视频通话配置
                            setupVideoConfig();
                            //设置本地端视图
                            setupLocalVideo();
                            //开启本地摄像头预览
                            startPreviewLocalVideo();
                            //加入频道
                            joinChannel();


                        }else {
                            Log.d(TAG, "hasPermission: 获取权限成功，部分权限未正常授予");
                        }
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if(quick) {
                            Log.d(TAG, "noPermission: 申请授权被拒绝，请手动授予权限");
                            //如果是被拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(VideoActivity.this);
                        }else {
                            Log.d(TAG, "noPermission: 获取权限失败");
                        }
                    }
                });
    }


    private boolean isRemote=false;

    //本地远程端视图位置互换
    @OnClick(R.id.remote_video_view_container)
    public void onSwitchLocalView() {

        if (isRemote){
            try {
                mLocalContainer.removeView(mLocalView);
                mRemoteContainer.addView(mLocalView);
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                mRemoteContainer.removeView(mRemoteView);
                mLocalContainer.addView(mRemoteView);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            try {
                mLocalContainer.removeView(mRemoteView);
                mRemoteContainer.addView(mRemoteView);
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                mRemoteContainer.removeView(mLocalView);
                mLocalContainer.addView(mLocalView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        isRemote=!isRemote;

    }

    //前后摄像头转换
    @OnClick(R.id.iv_switch_camera)
    public void onSwitchCameraClicked() {
        mRtcEngine.switchCamera();
    }

    //本地音频开关
    @OnClick(R.id.iv_mute)
    public void onLocalAudioMuteClicked() {
        mMuted = !mMuted;
        mRtcEngine.muteLocalAudioStream(mMuted);
        int res = mMuted ? R.drawable.btn_mute : R.drawable.btn_unmute;
        iv_mute.setImageResource(res);
    }

    //挂电话
    @OnClick(R.id.iv_call)
    public void onCallClicked() {
        try {
            leaveChannel();
            stopScreenSharing();
        }catch (Exception e){
            e.printStackTrace();
        }

        this.finish();
    }

    //办理成功
    @OnClick(R.id.bt_success)
    public void onSuccess() {
        try {
            leaveChannel();
            stopScreenSharing();
        }catch (Exception e){
            e.printStackTrace();
        }

        this.finish();
    }

    //办理失败
    @OnClick(R.id.bt_fail)
    public void onFail() {
        try {
            leaveChannel();
            stopScreenSharing();
        }catch (Exception e){
            e.printStackTrace();
        }

        this.finish();
    }



    //屏幕共享
    @OnClick(R.id.bt_screen)
    public void onScreenSharing(){

        mRtcEngine.leaveChannel();
        screenSharing(MyApplication.CALL_UID);

        try {
            int count = mRemoteContainer.getChildCount();
            View view = null;
            for (int i = 0; i < count; i++) {
                View v = mRemoteContainer.getChildAt(i);
                if (v.getTag() instanceof Integer &&((int) v.getTag()) == MyApplication.CLIENT_UID_1 ) {
                    view = v;
                }
            }

            if (view != null) {
                mRemoteContainer.removeView(view);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //停止屏幕共享
    @OnClick(R.id.bt_stop_screen)
    public void onStopScreenSharing(){
        stopScreenSharing();
        //加入频道
        joinChannel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            leaveChannel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ScreenSharingClient mSSClient;
    //屏幕共享
    public void screenSharing(int uid) {
        mSSClient= ScreenSharingClient.getInstance();
        ScreenSharingClient.IStateListener mListener = new ScreenSharingClient.IStateListener() {
            @Override
            public void onError(int error) {
                Log.e(TAG, "Screen share service error happened: " + error);
            }

            @Override
            public void onTokenWillExpire() {
                Log.d(TAG, "Screen share service token will expire");
                mSSClient.renewToken(null); // Replace the token with your valid token
            }
        };
        mSSClient.setListener(mListener);
        VideoEncoderConfiguration mVEC = new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT);
        mSSClient.start(this, getResources().getString(R.string.agora_app_id), null,
                MyApplication.CHANNEL_NAME_1, uid, mVEC);
    }

    //停止屏幕共享
    public void stopScreenSharing(){
        try {
            mSSClient.stop(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
