package cn.bearever.likemosaic;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.UUID;

import cn.bearever.mingbase.app.store.SpManager;

/**
 * 获取UID
 *
 * @author luoming
 * @date 2020/4/12
 */
public class UidUtil {
    /**
     * 获取UID
     *
     * @return
     */
    public static String getUid(Context context) {
        String uid = SpManager.getInstance().getString(Constant.KEY_UID);
        if (TextUtils.isEmpty(uid)) {
            uid = UUID.randomUUID().toString();
            SpManager.getInstance().putString(Constant.KEY_UID, uid);
        }
        return uid;
    }
}
