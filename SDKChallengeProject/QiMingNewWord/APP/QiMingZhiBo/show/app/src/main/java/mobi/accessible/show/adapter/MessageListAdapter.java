package mobi.accessible.show.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.accessible.show.R;
import mobi.accessible.show.model.ChannelData;
import mobi.accessible.show.model.Member;
import mobi.accessible.show.model.Message;
import mobi.accessible.show.manager.ChatRoomManager;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    private ChannelData mChannelData;
    private Context mContext;

    public MessageListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mChannelData = ChatRoomManager.instance(context).getChannelData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mChannelData == null) return 0;
        return mChannelData.getMessageList().size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = mChannelData.getMessageList().get(position);
        String userId = message.getSendId();

        if(TextUtils.isEmpty(mChannelData.getMember(userId).getHeadimgurl())){
            holder.iv_avatar.setImageResource(mChannelData.getMemberAvatar(userId));
        }else {
            Glide.with(mContext).load(mChannelData.getMember(userId).getHeadimgurl()).into(holder.iv_avatar);
        }
        switch (message.getMessageType()) {
            case Message.MESSAGE_TYPE_TEXT:
                holder.tv_message.setVisibility(View.VISIBLE);
                Member member = mChannelData.getMember(userId);
                holder.tv_message.setText(String.format("%s：%s", member != null ? member.getName() : userId, message.getContent()));
                break;
            case Message.MESSAGE_TYPE_IMAGE:
                // TODO
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView iv_avatar;
        @BindView(R.id.tv_message)
        TextView tv_message;
        @BindView(R.id.iv_image)
        ImageView iv_image;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
