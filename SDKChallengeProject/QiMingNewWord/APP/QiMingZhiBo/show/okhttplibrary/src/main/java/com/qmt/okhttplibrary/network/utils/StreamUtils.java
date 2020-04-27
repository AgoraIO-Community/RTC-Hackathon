package com.qmt.okhttplibrary.network.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 读取本地中的json
 * 注意json需要放在res/raw目录中
 * 主要用于操作测试接口
 *
 * @author wlk
 * @date 2017/10/11
 */

public class StreamUtils {

    /**
     * @param context contest
     * @param id R.id.tests.json
     * @return 文件内字符串
     */
    public static String get(Context context, int id) {
        InputStream stream = context.getResources().openRawResource(id);
        return read(stream);
    }

    private static String read(InputStream stream) {
        return read(stream, "utf-8");
    }

    private static String read(InputStream is, String encode) {
        if (is != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
                StringBuilder sb = new StringBuilder();
                String line ;
                while ((line = reader.readLine()) != null) {
                    sb.append(line );
                    sb.append("\n");
                }
                is.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
