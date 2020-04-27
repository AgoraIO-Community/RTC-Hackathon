package com.pzj.visualcall.server.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
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

//首页服务界面
public class HomeServerFragment extends Fragment {

    @BindView(R.id.bt_start_server)
    AppCompatButton bt_start_server;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_server, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    //开始服务
    @OnClick(R.id.bt_start_server)
    public void startServer(){
        startActivity(new Intent("com.pzj.visualcall.server.video"));
    }

}
