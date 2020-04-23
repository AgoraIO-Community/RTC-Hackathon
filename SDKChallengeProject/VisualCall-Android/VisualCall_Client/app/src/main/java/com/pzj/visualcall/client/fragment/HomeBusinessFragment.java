package com.pzj.visualcall.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.pzj.visualcall.client.MyApplication;
import com.pzj.visualcall.client.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.agora.rtc.ss.ScreenSharingClient;
import io.agora.rtc.video.VideoEncoderConfiguration;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeBusinessFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.iv_spmq)
    ImageView iv_spmq;

    @BindView(R.id.iv_yhkh)
    ImageView iv_yhkh;

    @BindView(R.id.iv_lcbl)
    ImageView iv_lcbl;

    @BindView(R.id.iv_bxbl)
    ImageView iv_bxbl;

    @BindView(R.id.cv_yhkh)
    CardView cv_yhkh;

    @BindView(R.id.cv_lcbl)
    CardView cv_lcbl;

    @BindView(R.id.cv_bxlp)
    CardView cv_bxlp;

    @BindView(R.id.cv_spmq)
    CardView cv_spmq;

    @BindView(R.id.cv_pmgx)
    CardView cv_pmgx;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_business, container, false);

        ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    //初始化
    public void initView() {
        iv_spmq.setOnClickListener(this);
        iv_yhkh.setOnClickListener(this);
        iv_lcbl.setOnClickListener(this);
        iv_bxbl.setOnClickListener(this);

        cv_yhkh.setOnClickListener(this);
        cv_lcbl.setOnClickListener(this);
        cv_bxlp.setOnClickListener(this);
        cv_spmq.setOnClickListener(this);
        cv_pmgx.setOnClickListener(this);
    }


    //显示介绍
    public void onShowTipView(View view, String text) {

        TextView textView = new TextView(getContext());
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(getContext(), 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(getContext(), 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText("" + text);

        QMUIPopups.popup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 250))
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                .view(textView)
                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                .offsetX(QMUIDisplayHelper.dp2px(getContext(), 20))
                .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                .shadow(true)
                .arrow(true)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .dimAmount(0.5f)
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //Toast.makeText(getContext(), "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .show(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_spmq:
                //视频面签介绍
                onShowTipView(v, "以音视频通话的方式，由后台坐席人员（远程面签岗）对客户身份核实、业务事项核对、资料签署、场景见证等进行在线面签操作的全新模式");
                break;
            case R.id.iv_yhkh:
                //银行开户介绍
                onShowTipView(v, "针对银行等金融机构提供基于音视频呼叫、高清视频、双向录制存档、智能排队的远程视频面签，改变传统现场面签的操作形式");
                break;
            case R.id.iv_lcbl:
                //理财办理介绍
                onShowTipView(v, "为金融行业，如银行、证券、互联网金融、汽车金融、保险、信托、消费金额等，提供一站式视频开户解决方案");
                break;
            case R.id.iv_bxbl:
                //保险办理介绍
                onShowTipView(v, "车主可与视频理赔员建立面对面联系，全流程线上化完成，享受一站式极速、极简、极致的车险理赔服务");
                break;
            case R.id.cv_yhkh:
            case R.id.cv_lcbl:
            case R.id.cv_bxlp:
            case R.id.cv_spmq:
                //停止屏幕共享
                stopScreenSharing();
                //跳转到视频页面
                startActivity(new Intent("com.pzj.visualcall.client.video"));
                break;
            case R.id.cv_pmgx:
                //屏幕共享
                screenSharing(MyApplication.CLIENT_UID_1);
                break;

        }
    }


    ScreenSharingClient mSSClient;

    //屏幕共享
    public void screenSharing(int uid) {
        mSSClient = ScreenSharingClient.getInstance();
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
        mSSClient.start(getContext(), getResources().getString(R.string.agora_app_id), null,
                MyApplication.CHANNEL_NAME_1, uid, mVEC);
    }

    //停止屏幕共享
    public void stopScreenSharing() {
        try {
            mSSClient.stop(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
