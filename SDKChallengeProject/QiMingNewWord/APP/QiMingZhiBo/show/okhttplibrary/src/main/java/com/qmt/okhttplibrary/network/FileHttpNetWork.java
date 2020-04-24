package com.qmt.okhttplibrary.network;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by sunch on 2018/3/22.
 */

public class FileHttpNetWork {

    private static final int MSG_SUCCESS = 0;
    private static final int MSG_SUCCESS_DOWNLOAD = 1;
    private Context context;
    private OkHttpClient client;
    private Response response;

    public FileHttpNetWork(Context context) {
        this.context = context;
    }

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SUCCESS :
                    Toast.makeText(context, "上传成功", Toast.LENGTH_LONG).show();
                    break;
                case MSG_SUCCESS_DOWNLOAD :
                    Toast.makeText(context,"下载成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void downloadFile(final String name, final String url) throws IOException, InterruptedException {
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "downloadfile 开始");
                int cacheSize = 10 * 1024 * 1024;
                File sdcache = new File(String.valueOf(Environment.getExternalStorageDirectory()) + "/com.coactsoft.demo/fileLoad");
                Cache cache = new Cache(sdcache.getAbsoluteFile(), cacheSize);
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.cache(cache);
                client = builder.build();
                //传入文件的名字
                RequestBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                try {
                    response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        Log.d(TAG, "下载失败");
                        throw new IOException("Unexpected code " + response);
                    } else {
                        Log.d(TAG, "返回数据成功，开始写入");
                        InputStream is;
                        is = response.body().byteStream();
                        FileOutputStream fos;
                        String pa = Environment.getExternalStorageDirectory().toString();
                        File ff = new File(pa + "/com.coactsoft.demo/fileLoad",name);
                        if (!ff.exists()) {
                            ff.createNewFile();
                        }
                        fos = new FileOutputStream(ff);
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        while ((len = is.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);

                        }
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                        Log.d(TAG, "结束");
                        handler.sendEmptyMessage(MSG_SUCCESS_DOWNLOAD);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }
}
