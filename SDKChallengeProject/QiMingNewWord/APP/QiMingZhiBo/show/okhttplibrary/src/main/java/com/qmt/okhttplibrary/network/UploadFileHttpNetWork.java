package com.qmt.okhttplibrary.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


import com.qmt.okhttplibrary.network.bean.ResultCallback;
import com.qmt.okhttplibrary.network.utils.LogUtils;
import com.qmt.okhttplibrary.network.utils.LoginDialog;
import com.qmt.okhttplibrary.network.utils.StringUtils;

import java.io.File;
import java.io.IOException;
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
 * @author wlk
 * Created by 98283 on 2018/3/24.
 */
public class UploadFileHttpNetWork {

    private static UploadFileHttpNetWork mInstance;
    private static OkHttpClient mOkHttpClient;
    private static Handler mDelivery;

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private UploadFileHttpNetWork() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(2*60, TimeUnit.SECONDS)
                .writeTimeout(2*60, TimeUnit.SECONDS)
                .readTimeout(2*60, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    private synchronized static UploadFileHttpNetWork getmInstance() {
        if (mInstance == null) {
            mInstance = new UploadFileHttpNetWork();
        }
        return mInstance;
    }


    /**
     * 上传文件
     * @param requestUrl 接口地址
     * @param filePath  本地文件地址
     */
    public static <T> void upLoadFile(final Context mContext, String requestUrl, String filePath, final ResultCallback<T> callBack) {

        LoginDialog.showDialog(mContext,"上传中",false);

        getmInstance();
        StringBuilder sUrl = new StringBuilder(requestUrl);
        if(StringUtils.isEmpty(requestUrl)){
            return;
        }
        //创建File
        File file = new File(filePath);
        //创建RequestBody
        RequestBody body1 = new MultipartBody.Builder()
                            .addFormDataPart("token","token")
                            .addFormDataPart("file",file.getName(),
                                    RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                            .build();
        //创建Request
        final Request request = new Request.Builder().url(sUrl.toString()).addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(body1).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "文件上传失败！", Toast.LENGTH_SHORT).show();
                    }
                });
                if (LoginDialog.dialogIsShowIng()){
                    LoginDialog.dismissDialog();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                LogUtils.e("--------返回参数-------\n",str);
                int code = response.code();
                if(code == 200) {
                    try {
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess((T) str);
                            }
                        });
                    }catch (Exception e){
                        callBack.onFailure(e);
                    }
                } else if(code == 400) {
                    if(str != null) {
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "文件上传失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "文件上传失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (LoginDialog.dialogIsShowIng()){
                    LoginDialog.dismissDialog();
                }

            }
        });
    }

}
