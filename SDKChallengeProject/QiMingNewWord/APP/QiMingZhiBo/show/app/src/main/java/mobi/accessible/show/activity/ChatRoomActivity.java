package mobi.accessible.show.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.qmt.okhttplibrary.network.HttpNetWork;
import com.qmt.okhttplibrary.network.bean.ResultCallback;
import com.qmt.okhttplibrary.utils.FileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import mobi.accessible.show.ChatRoomApplication;
import mobi.accessible.show.R;
import mobi.accessible.show.adapter.MessageListAdapter;
import mobi.accessible.show.adapter.SeatGridAdapter;
import mobi.accessible.show.manager.ChatRoomEventListener;
import mobi.accessible.show.manager.ChatRoomManager;
import mobi.accessible.show.model.ChannelData;
import mobi.accessible.show.model.Constant;
import mobi.accessible.show.model.Member;
import mobi.accessible.show.model.Message;
import mobi.accessible.show.model.ResourceInfoBean;
import mobi.accessible.show.model.ResourceResultBean;
import mobi.accessible.show.model.Seat;
import mobi.accessible.show.util.AlertUtil;
import mobi.accessible.show.util.DialogUtils;
import mobi.accessible.show.util.MemberUtil;
import mobi.accessible.show.widget.GiftPopView;
import mobi.accessible.show.widget.MemberListDialog;
import mobi.accessible.show.widget.VoiceChangerDialog;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class ChatRoomActivity extends AppCompatActivity implements ChatRoomEventListener, SeatGridAdapter.OnItemClickListener {

    public static final String BUNDLE_KEY_CHANNEL_ID = "channelId";
    public static final String BUNDLE_KEY_BACKGROUND_RES = "backgroundRes";
    private final int PERMISSION_REQ_ID = 22;

    private ChatRoomManager mManager;

    public ChatRoomManager getManager() {
        return mManager;
    }

    private MemberListDialog mMemberDialog = new MemberListDialog();
    private VoiceChangerDialog mChangerDialog = new VoiceChangerDialog();
    private SeatGridAdapter mSeatAdapter;
    private MessageListAdapter mMessageAdapter;
    private String mChannelId;
    private boolean mMuteRemote;
    private boolean isDestroyed;

    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_num)
    TextView btn_num;
    @BindView(R.id.rv_seat_grid)
    RecyclerView rv_seat_grid;
    @BindView(R.id.rv_message_list)
    RecyclerView rv_message_list;
    @BindView(R.id.cb_mixing)
    CheckBox cb_mixing;
    @BindView(R.id.btn_mic)
    ImageButton btn_mic;
    @BindView(R.id.gift)
    GiftPopView gift;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);

        initView();
        initManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            destroy();
        }
    }

    @Override
    protected void onDestroy() {
        destroy();
        super.onDestroy();
    }

    private void destroy() {
        if (isDestroyed) return;

        mManager.leaveChannel();

        isDestroyed = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mManager.joinChannel(mChannelId);
            else
                AlertUtil.showAlertDialog(this, "No permission", "finish", (dialog, which) -> finish());
        }
    }

    /**
     * 获取云端录制资源
     */
    private void initData() {
        uid = String.valueOf(Math.abs(UUID.randomUUID().hashCode()));
        MemberUtil.getUserId();
        Map<String,Object> parems = new HashMap<>();
        Map<String,Integer> map = new HashMap<>();
        map.put("resourceExpiredHour",24);
        parems.put("cname","QiMingZhiBo");
        parems.put("uid",  uid);
        parems.put("clientRequest", map);
        //下面 XXXXX   去https://console.agora.io/project/An_PVGcIvnT  申请APPID更换
        HttpNetWork.post(this, ChatRoomApplication.BaseUrl
                        + "/apps/XXXXX/cloud_recording/acquire"  
                ,true,"",true
                , new ResultCallback<ResourceInfoBean>() {
                    @Override
                    public void onSuccess(ResourceInfoBean response) {
                        if(response !=null && !TextUtils.isEmpty(response.getResourceId())){
                            startCloudRecord(response.getResourceId());
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                },parems);
    }

    /**
     *停止云端录制
     */
    private void stopCloudRecord(){
        if(resourceIdBean == null){
            finish();
            return;
        }
        String url = "/apps​/"+getString(R.string.app_id)
                +"​/cloud_recording​/resourceid​/"+resourceIdBean.getResourceId()
                +"​/sid​/"+resourceIdBean.getSid()+"​/mode​/mix​/stop";
        Map<String,Object> parems = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        parems.put("cname","QiMingZhiBo");
        parems.put("uid",  uid);
        parems.put("clientRequest", map);
        HttpNetWork.post(this, ChatRoomApplication.BaseUrl+url
                ,true,"",true
                        , new ResultCallback<ResourceResultBean>() {
                        @Override
                        public void onSuccess(ResourceResultBean response) {
                            if(response !=null&& response.getServerResponse() != null){
                                Toast.makeText(ChatRoomActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            finish();
                        }
                },parems);

    }
    private ResourceInfoBean resourceIdBean;
    private String uid;

    /**
     * 开始云端录制
     * @param resourceId
     */
    private void startCloudRecord(String resourceId) {
        String url = "/apps/"+getString(R.string.app_id)+"/cloud_recording/resourceid/"+resourceId+"/mode/mix/start";
        MemberUtil.getUserId();
        Map<String,Object> parems = new HashMap<>();
        Map<String,Object> setMap = initSet();
        parems.put("cname","QiMingZhiBo");
        parems.put("uid",  uid);
        parems.put("clientRequest", setMap);
        HttpNetWork.post(this, ChatRoomApplication.BaseUrl+url
                ,true,"",true
                , new ResultCallback<ResourceInfoBean>() {
                    @Override
                    public void onSuccess(ResourceInfoBean response) {
                        if(response !=null){
                            resourceIdBean = response;
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                },parems);
    }

    /**
     * 云端录制设置参数
     * @return
     */
    private Map<String, Object> initSet() {
        Map<String,Object> setMap = new HashMap<>();
        Map<String,Object> recordingConfig = new HashMap<>();
        recordingConfig.put("channelType",1);//直播模式
        recordingConfig.put("streamTypes",0);//仅录制音频
        recordingConfig.put("maxIdleTime",60);//直播场景下，如果频道内有观众但无主播，一旦无主播的状态超过 maxIdleTime，录制程序会自动退出。
//        recordingConfig.put("decryptionMode",0);//解密方案
        Map<String,Object> storageConfig = new HashMap<>();
        storageConfig.put("vendor",3);//第三方云存储供应商  腾讯云
        storageConfig.put("region",6);//AP_Shenzhen_FSI
        storageConfig.put("bucket","qimingzhibo-1257465110");//第三方云存储的 bucket。
        storageConfig.put("accessKey","AKIDw99sOfDowtZzJMMC5i49gxy53kBLw3bt");//第三方云存储的 access key。
        storageConfig.put("secretKey","BOPBj64td7NUtCKaaT7aueHSgx7Ofqwn");//第三方云存储的 secret key。
        //由多个字符串组成的数组，指定录制文件在第三方云存储中的存储位置。
        // 举个例子，fileNamePrefix = ["directory1","directory2"]，
        // 将在录制文件名前加上前缀 "directory1/directory2/"，即 directory1/directory2/xxx.m3u8
        storageConfig.put("fileNamePrefix",new String[]{"QmFm","qmzb"});
        setMap.put("recordingConfig",recordingConfig);
        setMap.put("storageConfig",storageConfig);
        return setMap;
    }

    private void initView() {
        Intent intent = getIntent();
        container.setBackgroundResource(intent.getIntExtra(BUNDLE_KEY_BACKGROUND_RES, 0));
        mChannelId = intent.getStringExtra(BUNDLE_KEY_CHANNEL_ID);
        mChannelId = "QiMingZhiBo";
        tv_title.setText("启明直播");
        initSeatRecyclerView();
        initMessageRecyclerView();
    }

    private void initSeatRecyclerView() {
        rv_seat_grid.setHasFixedSize(true);

        RecyclerView.ItemAnimator animator = rv_seat_grid.getItemAnimator();
        if (animator instanceof SimpleItemAnimator)
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);

        mSeatAdapter = new SeatGridAdapter(this);
        mSeatAdapter.setOnItemClickListener(this);
        rv_seat_grid.setAdapter(mSeatAdapter);

        rv_seat_grid.setLayoutManager(new GridLayoutManager(this, 5));

        int spacing = getResources().getDimensionPixelSize(R.dimen.item_seat_spacing);
        rv_seat_grid.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });
    }

    private void initMessageRecyclerView() {
        mMessageAdapter = new MessageListAdapter(this);
        rv_message_list.setAdapter(mMessageAdapter);

        rv_message_list.setLayoutManager(new LinearLayoutManager(this));

        int spacing = getResources().getDimensionPixelSize(R.dimen.item_message_spacing);
        rv_message_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(spacing, 0, spacing, spacing);
            }
        });
    }

    private void initManager() {
        mManager = ChatRoomManager.instance(this);
        mManager.setListener(this);
        if (checkPermission())
            mManager.joinChannel(mChannelId);
        mMemberDialog.setManager(mManager);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_ID);
            return false;
        }
        return true;
    }

    public void givingGift(View view) {
        mManager.givingGift();
    }

    @OnCheckedChanged({R.id.cb_mixing})
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.cb_mixing:
                if (isChecked) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/*"); //选择音频
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 1);
                } else {
                    mManager.getRtcManager().stopAudioMixing();
                }
                break;
        }
    }

    @OnClick({R.id.btn_exit, R.id.btn_num, R.id.btn_changer, R.id.btn_mic, R.id.btn_speaker})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                DialogUtils.showExitDialog(this, new DialogUtils.OnPositivieClickListener() {
                    @Override
                    public void onCLick(boolean isExit) {
                        isDestroyed = !isExit;
                        if(isExit && mManager.getChannelData().isAnchorMyself()){
                            stopCloudRecord();
                        }else {
                            finish();
                        }

                    }
                });
                break;
            case R.id.btn_num:
                mMemberDialog.show(getSupportFragmentManager(), null);
                break;
            case R.id.btn_changer:
                mChangerDialog.show(getSupportFragmentManager(), null);
                break;
            case R.id.btn_mic:
                String myUserId = String.valueOf(Constant.sUserId);
                mManager.muteMic(myUserId, !mManager.getChannelData().isUserMuted(myUserId));
                break;
            case R.id.btn_speaker:
                mMuteRemote = !mMuteRemote;
                view.setContentDescription(mMuteRemote ? "开启声音" : "关闭声音");
                ((ImageButton) view).setImageResource(mMuteRemote ? R.mipmap.ic_speaker_off : R.mipmap.ic_speaker_on);
                mManager.getRtcManager().muteAllRemoteAudioStreams(mMuteRemote);
                break;
        }
    }

    @OnEditorAction({R.id.et_input})
    public void onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            String message = v.getText().toString();
            if (TextUtils.isEmpty(message)) return;
            mManager.sendMessage(message);
            v.setText("");
        }
    }

    @Override
    public void onItemClick(View view, int position, Seat seat) {
        ChannelData channelData = mManager.getChannelData();
        boolean isAnchor = channelData.isAnchorMyself();
        if (seat != null) {
            if (seat.isClosed()) {
                if (isAnchor)
                    showSeatPop(view, new int[]{R.id.open_seat}, null, position);
                return;
            } else {
                String userId = seat.getUserId();
                if (channelData.isUserOnline(userId)) {
                    if (isAnchor) {
                        boolean muted = channelData.isUserMuted(userId);
                        showSeatPop(view, new int[]{
                                R.id.to_audience,
                                muted ? R.id.turn_on_mic : R.id.turn_off_mic,
                                R.id.close_seat
                        }, userId, position);
                    } else {
                        if (Constant.isMyself(userId))
                            showSeatPop(view, new int[]{R.id.to_audience}, userId, position);
                    }
                    return;
                }
            }
        }
        showSeatPop(
                view,
                isAnchor ? new int[]{R.id.to_broadcast, R.id.close_seat} : new int[]{R.id.to_broadcast},
                String.valueOf(Constant.sUserId),
                position
        );
    }

    private void showSeatPop(View view, int[] ids, String userId, int position) {
        AlertUtil.showPop(this, view, ids, (index, menu) -> {
            switch (menu.getId()) {
                case R.id.to_broadcast:
                    mManager.toBroadcaster(String.valueOf(Constant.sUserId), position);
                    break;
                case R.id.to_audience:
                    mManager.toAudience(userId, null);
                    break;
                case R.id.turn_off_mic:
                    mManager.muteMic(userId, true);
                    break;
                case R.id.turn_on_mic:
                    mManager.muteMic(userId, false);
                    break;
                case R.id.close_seat:
                    mManager.closeSeat(position);
                    break;
                case R.id.open_seat:
                    mManager.openSeat(position);
                    break;
            }
            return true;
        }, null);
    }

    @Override
    public void onSeatUpdated(int position) {
        runOnUiThread(() -> mSeatAdapter.notifyItemChanged(position));
    }

    @Override
    public void onUserGivingGift(String userId) {
        runOnUiThread(() -> gift.show(userId));
    }

    @Override
    public void onMessageAdded(int position) {
        runOnUiThread(() -> {
            mMessageAdapter.notifyItemInserted(position);
            rv_message_list.scrollToPosition(position);
        });
    }

    @Override
    public void onMemberListUpdated(String userId) {
        runOnUiThread(() -> {
            btn_num.setText(String.valueOf(mManager.getChannelData().getMemberList().size()));
            btn_num.setContentDescription("房间人数： "+mManager.getChannelData().getMemberList().size()+"人");
            mSeatAdapter.notifyItemChanged(userId, false);
            mMemberDialog.notifyDataSetChanged();
        });
    }

    /**
     * 是否强制下线
     * @param isGameOVer
     */
    @Override
    public void gameOver(boolean isGameOVer) {
        runOnUiThread(() -> {
            Toast.makeText(ChatRoomActivity.this,isGameOVer?"您已被主播强制下线，请两分钟之后再进入直播间":"亲，主播已下线，请稍候再来。",Toast.LENGTH_SHORT).show();
            isDestroyed = false;
            ChatRoomActivity.this.finish();
        });
    }

    @Override
    public void onUserStatusChanged(String userId, Boolean muted) {
        runOnUiThread(() -> {
            if (Constant.isMyself(userId)) {
                if (muted != null && muted) {
                    btn_mic.setImageResource(R.mipmap.ic_mic_off);
                    btn_mic.setContentDescription("开启发言");
                } else {
                    btn_mic.setImageResource(R.mipmap.ic_mic_on);
                    btn_mic.setContentDescription("关闭发言");
                }
            }
            mSeatAdapter.notifyItemChanged(userId, false);
            mMemberDialog.notifyItemChangedByUserId(userId);
        });
    }

    @Override
    public void onAudioMixingStateChanged(boolean isPlaying) {
        runOnUiThread(() -> cb_mixing.setChecked(isPlaying));
    }

    @Override
    public void onAudioVolumeIndication(String userId, int volume) {
        runOnUiThread(() -> mSeatAdapter.notifyItemChanged(userId, true));
    }

    @Override
    public void onApplyForBroadcaster(String userId) {
        runOnUiThread(()-> {
            Member member =  mManager.getChannelData().getMember(userId);
            DialogUtils.showApplyForDialog(this,member != null ? member.getName() : userId, new DialogUtils.OnPositivieClickListener() {

                @Override
                public void onCLick(boolean consent) {
                    if (consent) {
                        mManager.sendOrder(userId, Message.ORDER_TYPE_BROADCASTER, String.valueOf(mManager.getChannelData().firstIndexOfEmptySeat()), null);
                    }
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mManager.getChannelData().isAnchorMyself()){
            initData();
        }
    }

    @Override
    public void onBackPressed() {
        DialogUtils.showExitDialog(this, new DialogUtils.OnPositivieClickListener() {
            @Override
            public void onCLick(boolean isExit) {
                isDestroyed = !isExit;
                if(isExit && mManager.getChannelData().isAnchorMyself()){
                    stopCloudRecord();
                }else {
                    finish();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String path;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = FileUtils.getPath(this, uri);
            } else {//4.4以下下系统调用方法
                path = FileUtils.getRealPathFromURI(this,uri);
            }
            mManager.getRtcManager().startAudioMixing(path);
        }
    }
}
