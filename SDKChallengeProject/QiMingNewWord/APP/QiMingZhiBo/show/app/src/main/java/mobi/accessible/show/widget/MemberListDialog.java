package mobi.accessible.show.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.accessible.show.R;
import mobi.accessible.show.adapter.MemberListAdapter;
import mobi.accessible.show.manager.ChatRoomManager;
import mobi.accessible.show.model.ChannelData;
import mobi.accessible.show.model.Message;
import mobi.accessible.show.util.DialogUtils;

public class MemberListDialog extends DialogFragment implements MemberListAdapter.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_member_list)
    RecyclerView rv_member_list;

    private Context mContext;
    private ChatRoomManager mManager;
    private MemberListAdapter mAdapter;

    public MemberListDialog() {

    }

    public void setManager(ChatRoomManager mManager) {
        this.mManager = mManager;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_member_list);

        ButterKnife.bind(this, dialog);

        refreshTitle();
        initRecyclerView();
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null)
                window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void initRecyclerView() {
        rv_member_list.setHasFixedSize(true);

        RecyclerView.ItemAnimator animator = rv_member_list.getItemAnimator();
        if (animator instanceof SimpleItemAnimator)
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);

        mAdapter = new MemberListAdapter(mContext);
        mAdapter.setOnItemClickListener(this);
        rv_member_list.setAdapter(mAdapter);

        rv_member_list.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }
        });

        rv_member_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            private Drawable mDivider = getResources().getDrawable(R.drawable.inset_divider);
            private final Rect mBounds = new Rect();
            private int spacing = getResources().getDimensionPixelSize(R.dimen.item_member_spacing);

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                drawVertical(c, parent);
            }

            private void drawVertical(Canvas canvas, RecyclerView parent) {
                canvas.save();
                int left;
                int right;
                if (parent.getClipToPadding()) {
                    left = parent.getPaddingLeft();
                    right = parent.getWidth() - parent.getPaddingRight();
                    canvas.clipRect(left, parent.getPaddingTop(), right,
                            parent.getHeight() - parent.getPaddingBottom());
                } else {
                    left = 0;
                    right = parent.getWidth();
                }

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    if (i == 0) {
                        int top = mBounds.top - Math.round(child.getTranslationY());
                        int bottom = top + mDivider.getIntrinsicHeight();
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(canvas);
                    }
                    if (i == childCount - 1) return;
                    int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                    int top = bottom - mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                canvas.restore();
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(spacing, spacing / 2, spacing, spacing / 2);
            }
        });
    }

    private void refreshTitle() {
        int num = ChatRoomManager.instance(mContext).getChannelData().getMemberList().size();
        tv_title.setText(mContext.getString(R.string.channel_member_list, num));
    }

    public void notifyDataSetChanged() {
        if (mContext != null) {
            refreshTitle();
            mAdapter.notifyDataSetChanged();
        }
    }

    public void notifyItemChangedByUserId(String userId) {
        if (mContext != null) {
            mAdapter.notifyItemChangedByUserId(userId);
        }
    }

    @Override
    public void onItemClick(View view, int position, String userId) {
        ChatRoomManager manager = ChatRoomManager.instance(mContext);
        ChannelData channelData = manager.getChannelData();
        switch (view.getId()) {
            case R.id.btn_role:
                if (channelData.isUserOnline(userId))
                    manager.toAudience(userId, null);
                else
                    manager.toBroadcaster(userId, channelData.firstIndexOfEmptySeat());
                break;
            case R.id.btn_mute:
                manager.muteMic(userId, !channelData.isUserMuted(userId));
                break;
            case R.id.btn_game_over:
                DialogUtils.showGameOverDialog(mContext, channelData.getMember(userId).getName(), new DialogUtils.OnPositivieClickListener() {
                    @Override
                    public void onCLick(boolean consent) {
                        if(consent){
                            mManager.sendOrder(userId, Message.ORDER_TYPE_GAME_OVER, String.valueOf(true), null);
                        }
                    }
                });
                break;
        }
    }

    @OnClick({R.id.btn_close})
    public void onClick(View view) {
        dismiss();
    }

}
