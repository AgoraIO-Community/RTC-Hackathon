package com.qmt.okhttplibrary.network;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.qmt.okhttplibrary.network.bean.ResponseData;
import com.qmt.okhttplibrary.network.bean.ResultCallback;
import com.qmt.okhttplibrary.network.utils.JsonUtils;
import com.qmt.okhttplibrary.network.utils.LogUtils;
import com.qmt.okhttplibrary.network.utils.LoginDialog;
import com.qmt.okhttplibrary.network.utils.MyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 图片加载工具类
 *
 * @author wlk
 * @date 2017/10/11
 */

public class ImgHttpNetWork {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 显示弹窗
     */
    private final static int SHOW_VIEW = 1;
    /**
     * 关闭弹窗
     */
    private final static int CLOSE_VIEW = 2;
    private static ImgHttpNetWork mInstance;
    private OkHttpClient client;
    private Handler mDelivery;


    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_VIEW:
//                    dlg = new LoginView((Context)msg.obj, "加载中...");
                    LoginDialog.showDialog((Context) msg.obj, "加载中",false);
                    break;
                case CLOSE_VIEW:
                    if (LoginDialog.dialogIsShowIng()){
                        LoginDialog.dismissDialog();
                    }
                    break;
                default:
                    break;

            }
        }
    };

    private ImgHttpNetWork() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        client = builder.build();
        mDelivery = new Handler(Looper.getMainLooper());

    }

    private synchronized static ImgHttpNetWork getmInstance() {
        if (mInstance == null) {
            mInstance = new ImgHttpNetWork();
        }
        return mInstance;
    }

    public static void postImg(Context mContext, List<File> files, String url, ResultCallback callback) {

        Message msg = handler.obtainMessage();
        //发送context上下文
        msg.obj = mContext;
        msg.what = SHOW_VIEW;
        handler.sendMessage(msg);
        getmInstance().uploadImg(mContext, files, url, callback);
    }

    private void uploadImg(Context mContext, List<File> files, String url, ResultCallback callback) {
        Request request = buildPostImgRequest(files, url);
        deliveryImgResult(mContext, callback, request);
    }

    private void deliveryImgResult(final Context mContext, final ResultCallback callback, final Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(mContext, callback, e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                LogUtils.e("", s);
                sendSuccessCallBack(mContext, callback, JsonUtils.deserialize(s, callback.mType));
            }

        });
    }

    private Request buildPostImgRequest(List<File> files, String url) {
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (File file : files) {
            builder.addFormDataPart("img", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }
        builder.addFormDataPart("token", JsonUtils.serialize(UUID.randomUUID().toString()));
        MultipartBody requestBody = builder.build();
        //构建请求
        return new Request.Builder().url(url).header("Content-Type", "application/json").post(requestBody).build();
    }

    private void sendFailCallback(final Context mContext, final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                    handler.sendEmptyMessage(CLOSE_VIEW);
                    Toast.makeText(mContext, "请求失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void sendSuccessCallBack(final Context mContext, final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    handler.sendEmptyMessage(CLOSE_VIEW);
                    ResponseData<Object> data = (ResponseData<Object>) obj;
                    if (data.getCode() == 0) {
                        callback.onSuccess(obj);
                    } else {
                        MyUtils.toastLong(mContext, data.getErrorMsg());
                    }

                }
            }
        });
    }

}
