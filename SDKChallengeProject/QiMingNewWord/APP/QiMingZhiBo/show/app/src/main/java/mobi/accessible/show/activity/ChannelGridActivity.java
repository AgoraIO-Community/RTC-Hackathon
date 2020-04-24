package mobi.accessible.show.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.accessible.show.R;
import mobi.accessible.show.adapter.ChannelGridAdapter;
import mobi.accessible.show.model.Channel;
import mobi.accessible.show.util.ExitDialogUtils;
import mobi.accessible.show.util.MemberUtil;

public class ChannelGridActivity extends AppCompatActivity implements ChannelGridAdapter.OnItemClickListener {

    @BindView(R.id.rv_channel_grid)
    RecyclerView rv_channel_grid;
    @BindView(R.id.btn_set_nikName)
    AppCompatButton btn_set_nikName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_grid);
        ButterKnife.bind(this);

        initRecyclerView();

    }



    private void initRecyclerView() {
        rv_channel_grid.setHasFixedSize(true);

        ChannelGridAdapter adapter = new ChannelGridAdapter(this);
        adapter.setOnItemClickListener(this);
        rv_channel_grid.setAdapter(adapter);

        rv_channel_grid.setLayoutManager(new GridLayoutManager(this, 1)); //先设置一行只有一列

        int spacing = getResources().getDimensionPixelSize(R.dimen.item_channel_spacing);
        rv_channel_grid.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });
    }
    @OnClick({R.id.btn_set_nikName})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_nikName:
                ExitDialogUtils.showEditViewDialog(this, new ExitDialogUtils.OnPositivieClickListener() {
                    @Override
                    public void onCLick(String name) {
                        MemberUtil.setName(name);
                    }
                });
                break;
        }
    }
    @Override
    public void onItemClick(View view, int position, Channel channel) {
        if(TextUtils.isEmpty(MemberUtil.getName())){
            ExitDialogUtils.showEditViewDialog(this, new ExitDialogUtils.OnPositivieClickListener() {
                @Override
                public void onCLick(String name) {
                    MemberUtil.setName(name);
                    joinChatRoom(channel);
                }
            });
        }else {
            joinChatRoom(channel);
        }
    }

    private void joinChatRoom(Channel channel) {
        Intent intent = new Intent(ChannelGridActivity.this, ChatRoomActivity.class);
        intent.putExtra(ChatRoomActivity.BUNDLE_KEY_CHANNEL_ID, "QiMingZhiBo");
        intent.putExtra(ChatRoomActivity.BUNDLE_KEY_BACKGROUND_RES, channel.getBackgroundRes());
        startActivity(intent);
    }
}
