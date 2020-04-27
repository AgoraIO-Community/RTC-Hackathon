package com.qmt.okhttplibrary.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.qmt.okhttplibrary.network.bean.ResponseData;
import com.qmt.okhttplibrary.network.bean.ResultCallback;
import com.qmt.okhttplibrary.network.utils.Base64Encoder;
import com.qmt.okhttplibrary.network.utils.JsonUtils;
import com.qmt.okhttplibrary.network.utils.LogUtils;
import com.qmt.okhttplibrary.network.utils.LoginDialog;
import com.qmt.okhttplibrary.network.utils.MyUtils;
import com.qmt.okhttplibrary.network.utils.StringUtils;
import com.qmt.okhttplibrary.network.utils.ToastUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



/**
 * OkHttp第一次封装
 *
 * @author wlk
 * @date 2017/10/11
 */
public class HttpNetWork {

    private static final MediaType BODY_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "HttpNetWork";
    private static final String HEADER_CRYPT = "coact_oauth_crypt";
    private String error_msg = "";

    private static HttpNetWork mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private HttpNetWork() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)//两分钟（单位：秒）
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public synchronized static HttpNetWork getmInstance() {
        if (mInstance == null) {
            mInstance = new HttpNetWork();
        }
        return mInstance;
    }

    private void getRequest(Context mContext, String url, final ResultCallback callback) {

        // 填入 Customer ID 和 Customer Certificate，计算 plainCredentials。
        String plainCredentials = "d8d1a85d0c2a40f09073cf920482c052:3188164cd94d46cc9d33b1d79aa63cd2";
        // 填入 plainCredentials（使用 Base64 算法编码的 Credential），计算 base64Credentials，即你要的 Authorization 字段。
        String base64Credentials = Base64Encoder.encode(plainCredentials.getBytes());

        final Request request = new Request.Builder()
                .url(url)
                .header("Authorization","Basic "+base64Credentials)
                .header("Content-Type", "application/json")
                .build();
        deliveryResult(mContext, callback, request);
    }
    private void deleteRequest(Context mContext, String url, final ResultCallback callback, Object param) {
            final Request request = buildDeleteRequest(url, param);
            deliveryResult(mContext, callback, request);
        }
    private void postRequest(Context mContext, String url, final ResultCallback callback, Object param) {
        Request request = buildPostRequest(mContext,url, param);
        deliveryResult(mContext, callback, request);
    }

    public void deliveryResult(final Context mContext, final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG+"--------返回参数 IOException------\n",e.toString());
                sendFailCallback(mContext, callback, e);

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    error_msg = "";
                    LogUtils.e(TAG+"--------返回参数-------\n",str);
                    ResponseData responseData = JsonUtils.deserialize(str,ResponseData.class);
                    sendSuccessCallBack(mContext, callback, JsonUtils.deserialize(str, callback.mType));
                } catch (final Exception e) {
                    LogUtils.e(TAG, "convert json failure", e);
                    sendFailCallback(mContext, callback, e);
                }
            }
        });
    }


    private void sendFailCallback(final Context mContext, final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (LoginDialog.dialogIsShowIng()){
                    LoginDialog.dismissDialog();
                }
                if (callback != null) {
                    callback.onFailure(e);
                    LogUtils.e(TAG+"----------请求失败--------",e.toString());
                    if(!StringUtils.isEmpty(error_msg)){
                        ToastUtils.showShort(mContext,error_msg);
                    }else {
                        ToastUtils.showShort(mContext,"请求失败！");
                    }

                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void sendSuccessCallBack(final Context mContext, final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {

                if (LoginDialog.dialogIsShowIng()){
                    LoginDialog.dismissDialog();
                }

                if (callback != null) {
                    try {
                        ResponseData<Object> data = (ResponseData<Object>) obj;
                        if (data.getCode() == 0 || callback.isShowFail) {
                            callback.onSuccess(obj);
                        } else {
                            MyUtils.toastLong(mContext, data.getErrorMsg());
                        }
                    }catch (Exception e){
                        try {
                            callback.onSuccess(obj);
                        }catch (Exception e1){
                            MyUtils.toastLong(mContext,"请求失败");
                            callback.onFailure(e1);
                        }

                    }

                }
            }
        });
    }
    private Request buildPostRequest(Context mContext, String url, Object param) {
        RequestBody requestBody;
        if(param != null ){
            LogUtils.e(TAG+"----------请求参数--------"+"\n",param.toString());
            requestBody = RequestBody.create(JSON_TYPE, JsonUtils.serialize(param));
        }else {
            requestBody = RequestBody.create(JSON_TYPE, "");
        }
        // 填入 Customer ID 和 Customer Certificate，计算 plainCredentials。
        String plainCredentials = "d8d1a85d0c2a40f09073cf920482c052:3188164cd94d46cc9d33b1d79aa63cd2";
        // 填入 plainCredentials（使用 Base64 算法编码的 Credential），计算 base64Credentials，即你要的 Authorization 字段。
        String base64Credentials = Base64Encoder.encode(plainCredentials.getBytes());
        return new Request.Builder().url(url).header("Authorization","Basic "+base64Credentials)
                .addHeader("Content-Type", "application/json").post(requestBody).build();

    }
    private Request buildDeleteRequest(String url, Object param) {
        RequestBody requestBody;
        LogUtils.e(TAG+"----------请求参数--------"+"\n",JsonUtils.serialize(param));
        requestBody = RequestBody.create(JSON_TYPE, JsonUtils.serialize(param));
        if(url.contains("user/logout")){
            return new Request.Builder().url(url).header("Content-Type", "application/x-www-form-urlencoded").post(requestBody).build();
        }
        return new Request.Builder().url(url).header("Content-Type", "application/json").delete(requestBody).build();

    }
    /**********************对外接口************************/
    /**
     * get请求
     * @param mContext 上下文
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(Context mContext, String url, ResultCallback callback) {
        if(StringUtils.isEmpty(url)){
            return;
        }
        getmInstance().getRequest(mContext,url, callback);
    }
    /**
     * get请求
     * @param mContext 上下文
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(Context mContext, String url, ResultCallback callback, Map<String, Object> param) {
        StringBuilder sUrl = new StringBuilder(url);
        if(!url.contains("?")){
            sUrl.append("?");
        }
        for (String key : param.keySet()) {
            sUrl.append(key).append("=").append(param.get(key)).append("&");
        }
        sUrl = sUrl.delete(sUrl.lastIndexOf("&"),sUrl.length());
        getmInstance().getRequest(mContext, sUrl.toString(), callback);
    }

    /**
     * get请求
     * @param mContext 上下文
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(Context mContext, String url, boolean isShowDlg, String msg, boolean canClose, ResultCallback callback, Map<String, Object> param) {
        if (isShowDlg) {
            LoginDialog.showDialog(mContext,msg,canClose);
        }
        StringBuilder sUrl = new StringBuilder(url);
        if (param != null && param.size() > 0){
            sUrl.append("?");
            for (String key : param.keySet()) {
                sUrl.append(key).append("=").append(param.get(key)).append("&");
            }
            sUrl = sUrl.delete(sUrl.lastIndexOf("&"),sUrl.length());
        }
        getmInstance().getRequest(mContext, sUrl.toString(), callback);
    }
    /**
     * get请求
     * @param mContext 上下文
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(Context mContext, String url, boolean isShowDlg, String msg, boolean canClose, ResultCallback callback) {
        if (isShowDlg) {
            LoginDialog.showDialog(mContext,msg,canClose);
        }
        getmInstance().getRequest(mContext, url, callback);
    }

    /**
     * Post的JSON请求
     * @param mContext 上下文
     * @param url 接口地址
     * @param isShowDlg 是否显示加载框
     * @param msg 消息
     * @param canClose 是否可以关闭弹窗
     * @param callback 回调
     * @param param 请求数据 grant_type
     */

    public static void post(Context mContext, String url, boolean isShowDlg, String msg, boolean canClose, final ResultCallback callback, Object param) {
        if (isShowDlg) {
            if (!LoginDialog.dialogIsShowIng()){
                LoginDialog.showDialog(mContext,msg,canClose);
            }
        }
        StringBuilder sUrl = new StringBuilder(url);
        if(StringUtils.isEmpty(url)){
            return;
        }
        getmInstance().postRequest(mContext, sUrl.toString(), callback, param);
    }

    /**
     * Post的JSON请求
     * @param mContext 上下文
     * @param url 接口地址
     * @param isShowDlg 是否显示加载框
     * @param msg 消息
     * @param canClose 是否可以关闭弹窗
     * @param callback 回调
     * @param param 请求数据 grant_type
     */

    public static void delete(Context mContext, String url, boolean isShowDlg, String msg, boolean canClose, final ResultCallback callback, Object param) {
        if (isShowDlg) {
            if (!LoginDialog.dialogIsShowIng()){
                LoginDialog.showDialog(mContext,msg,canClose);
            }
        }
        StringBuilder sUrl = new StringBuilder(url);
        if(StringUtils.isEmpty(url)){
            return;
        }
        getmInstance().deleteRequest(mContext, sUrl.toString(), callback, param);
    }

    public static void resultCallback(Context mContext, String url, ResultCallback callback, Map<String, Object> param) {
        StringBuilder sUrl = new StringBuilder(url);
        for (String key : param.keySet()) {
            sUrl.append(key).append("=").append(param.get(key)).append("&");
        }
        sUrl = sUrl.delete(sUrl.lastIndexOf("&"),sUrl.length());
        getmInstance().getRequest(mContext, sUrl.toString(), callback);
    }


}
