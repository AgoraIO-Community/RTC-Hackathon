package mobi.accessible.show.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import mobi.accessible.show.R;
import mobi.accessible.show.model.ChannelData;
import mobi.accessible.show.model.Seat;
import mobi.accessible.show.manager.ChatRoomManager;
import mobi.accessible.show.widget.SpreadView;

public class SeatGridAdapter extends RecyclerView.Adapter<SeatGridAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    private ChannelData mChannelData;

    public SeatGridAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mChannelData = ChatRoomManager.instance(context).getChannelData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_seat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mChannelData == null) return 0;
        return mChannelData.getSeatArray().length;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.size() > 0)
            holder.view_anim.startAnimation();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Seat seat = mChannelData.getSeatArray()[position];
        StringBuffer des = new StringBuffer("座位"+(position+1));
        des.append(",");
        if (seat != null) {
            if (seat.isClosed()) {
                des.append("禁座");
                holder.iv_seat.setImageResource(R.mipmap.ic_ban);
                holder.iv_mute.setVisibility(View.GONE);
            } else {
                String userId = seat.getUserId();
                if (mChannelData.isUserOnline(userId)) {
                    holder.iv_seat.setImageResource(mChannelData.getMemberAvatar(userId));
                    holder.iv_mute.setVisibility(mChannelData.isUserMuted(userId) ? View.VISIBLE : View.GONE);
                    des.append(mChannelData.getMember(userId).getName());
                    des.append(mChannelData.isUserMuted(userId) ? "禁止发言":"");
                } else {
                    holder.iv_seat.setImageResource(R.mipmap.ic_join);
                    holder.iv_mute.setVisibility(View.GONE);
                }
            }
        } else {
            holder.iv_seat.setImageResource(R.mipmap.ic_join);
            holder.iv_mute.setVisibility(View.GONE);
        }
        holder.iv_seat.setContentDescription(des);
        holder.iv_seat.setOnClickListener(view -> {
            if (mListener != null)
                mListener.onItemClick(holder.view, position, seat);
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void notifyItemChanged(String userId, boolean animated) {
        int index = mChannelData.indexOfSeatArray(userId);
        if (index >= 0) {
            if (animated)
                notifyItemChanged(index, true);
            else
                notifyItemChanged(index);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.view_anim)
        SpreadView view_anim;
        @BindView(R.id.iv_seat)
        CircleImageView iv_seat;
        @BindView(R.id.iv_mute)
        ImageView iv_mute;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Seat seat);
    }

}
