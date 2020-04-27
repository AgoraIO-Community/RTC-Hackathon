package mobi.accessible.show.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.accessible.show.R;

public class VoiceChangerGridAdapter extends RecyclerView.Adapter<VoiceChangerGridAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private VoiceChangerGridAdapter.OnItemClickListener mListener;
    private int mSelectedIndex;

    private String[] mKeys;
    private int[] mValues;

    public VoiceChangerGridAdapter(Context context, @ArrayRes int keysId, @ArrayRes int valuesId) {
        mInflater = LayoutInflater.from(context);
        mKeys = context.getResources().getStringArray(keysId);
        mValues = context.getResources().getIntArray(valuesId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_changer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = mKeys[position];

        holder.tv_changer.setText(key);
        holder.tv_changer.setBackgroundResource(mSelectedIndex == position ? R.drawable.bg_changer_item_selected : R.drawable.bg_changer_item_normal);

        holder.tv_changer.setOnClickListener((view) -> {
            setSelectedIndex(position);
            if (mListener != null)
                mListener.onItemClick(position, mValues[position]);
        });
    }

    @Override
    public int getItemCount() {
        if (mKeys == null) return 0;
        return mKeys.length;
    }

    public void setSelectedIndex(int index) {
        notifyItemChanged(mSelectedIndex);
        mSelectedIndex = index;
        notifyItemChanged(index);
    }

    public void setOnItemClickListener(VoiceChangerGridAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_changer)
        TextView tv_changer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, int value);
    }

}
