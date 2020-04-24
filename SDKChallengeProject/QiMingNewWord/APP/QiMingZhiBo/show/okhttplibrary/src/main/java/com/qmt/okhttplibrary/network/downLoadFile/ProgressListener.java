package com.qmt.okhttplibrary.network.downLoadFile;

/**
 * Created by sunch on 2018/3/22.
 */

public interface ProgressListener {
    //已完成的 总的文件长度 是否完成
    void onProgress(long currentBytes, long contentLength, boolean done);
}
