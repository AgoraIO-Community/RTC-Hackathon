package com.pzj.visualcall.server;

import android.app.Application;

public class MyApplication extends Application {

    //通道名称
    public static final String CHANNEL_NAME_1 ="CHANNEL_NAME_1";
    public static final String CHANNEL_NAME_2 ="CHANNEL_NAME_2";
    public static final String CHANNEL_NAME_3 ="CHANNEL_NAME_3";
    public static final String CHANNEL_NAME_4 ="CHANNEL_NAME_4";

    //客户端uid
    public static final int CLIENT_UID_1 =1;
    public static final int CLIENT_UID_2 =2;
    public static final int CLIENT_UID_3 =3;
    public static final int CLIENT_UID_4 =4;

    //屏幕共享uid
    public static final int CLIENT_UID_5 =5;

    //坐席端uid
    public static final int CALL_UID =10;



    @Override
    public void onCreate() {
        super.onCreate();
    }
}
