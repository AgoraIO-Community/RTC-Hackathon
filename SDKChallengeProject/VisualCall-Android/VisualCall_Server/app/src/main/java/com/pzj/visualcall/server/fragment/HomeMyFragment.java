package com.pzj.visualcall.server.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pzj.visualcall.server.R;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.wildma.pictureselector.PictureSelector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeMyFragment extends Fragment {

    @BindView(R.id.iv_head)
    QMUIRadiusImageView iv_head;//头像

   private final int SELECT_TX_CODE=1;

    //Glide加载配置类
    private RequestOptions glideRequestOptions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_my, container, false);

        ButterKnife.bind(this, rootView);

        glideRequestOptions = new RequestOptions()
                //.circleCrop()//圆形图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存策略 不缓存
                .skipMemoryCache(true);//跳过内存缓存
                //.placeholder(R.drawable.img_touxiang)//默认图片
                //.error(R.drawable.image_load_err);//网络加载出错图片

        return rootView;
    }

    /**
     * 选择头像
     */
    @OnClick(R.id.iv_head)
    public void onHeadClick() {
        PictureSelector
                .create(this, SELECT_TX_CODE)
                .selectPicture(true, 200, 200, 1, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            switch (requestCode) {
                case SELECT_TX_CODE:
                    //选择头像结果回调
                    String  avatar_url = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    //File imageFile=new File(avatar_url);//选择的头像文件

                    //加载图片
                    /*如果使用 Glide 加载图片，则需要禁止 Glide 从缓存中加载，因为裁剪后保存的图片地址是相同的*/
                    Glide.with(this).load(avatar_url).apply(glideRequestOptions).into(iv_head);

                    break;
                default:
                    break;
            }
        }
    }
}
