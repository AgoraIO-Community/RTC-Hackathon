package com.qmt.okhttplibrary.network.downLoadFile;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by sunch on 2018/3/22.
 */

public class ProgressResponseBody extends ResponseBody {
    public static final int UPDATE = 0x01;
    public static final String TAG = ProgressResponseBody.class.getName();
    private ResponseBody responseBody;
    private ProgressListener mListener;
    private BufferedSource bufferedSource;
    private Handler myHandler;
    public ProgressResponseBody(ResponseBody body, ProgressListener listener) {
        responseBody = body;
        mListener = listener;
        if (myHandler==null){
            myHandler = new MyHandler();
        }
    }

    /**
     * 将进度放到主线程中显示
     */
    class MyHandler extends Handler {

        public MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE:
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    //接口返回
                    if (mListener!=null) {
                        mListener.onProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                    }
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {

        if (bufferedSource==null){
            bufferedSource = Okio.buffer(mySource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source mySource(Source source) {

        return  new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead +=bytesRead!=-1?bytesRead:0;
                //发送消息到主线程，ProgressModel为自定义实体类
                Message msg = Message.obtain();
                msg.what = UPDATE;
                if(bytesRead!=-1){
                    msg.obj =  new ProgressModel(totalBytesRead,contentLength(),totalBytesRead==contentLength());
                    myHandler.sendMessage(msg);
                }
                return bytesRead;
            }
        };
    }
}
