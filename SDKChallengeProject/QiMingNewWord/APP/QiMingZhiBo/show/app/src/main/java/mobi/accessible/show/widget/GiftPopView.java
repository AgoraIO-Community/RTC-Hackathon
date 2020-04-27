package mobi.accessible.show.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.accessible.show.R;
import mobi.accessible.show.manager.ChatRoomManager;
import mobi.accessible.show.model.ChannelData;
import mobi.accessible.show.model.Member;

public class GiftPopView extends FrameLayout {

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;

    Context mContext;
    public GiftPopView(Context context) {
        super(context);
        init(context);
    }

    public GiftPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_gift, this);
        ButterKnife.bind(this);
    }

    public void show(String userId) {
        ChannelData channelData = ChatRoomManager.instance(getContext()).getChannelData();
        Member member = channelData.getMember(userId);
        if (member != null)
            tv_name.setText(member.getName());
        else
            tv_name.setText(userId);
//        iv_avatar.setImageResource(channelData.getMemberAvatar(userId));
        if(TextUtils.isEmpty(member.getHeadimgurl())){
            iv_avatar.setImageResource(channelData.getMemberAvatar(userId));
        }else {
            Glide.with(mContext).load(member.getHeadimgurl()).into(iv_avatar);
        }
        setVisibility(View.VISIBLE);
        animation();
        postDelayed(() -> setVisibility(View.GONE), 2500);
    }

    private void animation() {
        AnimationSet animationSet = new AnimationSet(true);
        measure(0, 0);
        animationSet.addAnimation(new TranslateAnimation(0, 0, getMeasuredHeight(), 0));
        animationSet.addAnimation(new AlphaAnimation(0, 1));
        animationSet.setDuration(1000);
        startAnimation(animationSet);
    }

}
