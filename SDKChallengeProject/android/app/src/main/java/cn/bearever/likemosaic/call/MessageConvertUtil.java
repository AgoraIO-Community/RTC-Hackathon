package cn.bearever.likemosaic.call;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.bearever.likemosaic.bean.MessageBean;
import cn.bearever.likemosaic.bean.SelectTopicBean;
import cn.bearever.likemosaic.bean.TopicBean;

/**
 * @author luoming
 * @date 2020/4/18
 */
public class MessageConvertUtil {
    public static Gson mGson;

    public static MessageBean convert(String message) {
        if (mGson == null) {
            mGson = new Gson();
        }
        try {
            JSONObject jsonObject = new JSONObject(message);
            String key = jsonObject.getString("key");

            switch (key) {
                case MessageBean.KEY_SELECT_TOPIC: {
                    Type type = new TypeToken<MessageBean<SelectTopicBean>>() {
                    }.getType();
                    return mGson.<MessageBean<SelectTopicBean>>fromJson(message, type);
                }

                case MessageBean.KEY_REFRESH_TOPIC: {
                    Type type = new TypeToken<MessageBean<ArrayList<TopicBean>>>() {
                    }.getType();
                    return mGson.fromJson(message, type);
                }

                case MessageBean.KEY_REMOTE_LIKE_CHANGE: {
                    Type type = new TypeToken<MessageBean<Integer>>() {
                    }.getType();
                    return mGson.fromJson(message, type);
                }

                case MessageBean.KEY_QUIT_ROOM: {
                    Type type = new TypeToken<MessageBean<String>>() {
                    }.getType();
                    return mGson.fromJson(message, type);
                }

                default: {
                    return new MessageBean("");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new MessageBean("");
    }
}
