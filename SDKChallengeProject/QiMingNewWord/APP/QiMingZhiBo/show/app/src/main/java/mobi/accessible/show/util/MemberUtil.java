package mobi.accessible.show.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.TextUtils;

import androidx.annotation.ArrayRes;

import java.util.Random;
import java.util.UUID;

import mobi.accessible.show.ChatRoomApplication;
import mobi.accessible.show.R;

public class MemberUtil {

    private static SharedPreferences sp() {
        return ChatRoomApplication.instance.getSharedPreferences("member", Context.MODE_PRIVATE);
    }

    public static int getUserId() {
        int userId = sp().getInt("userId", 0);
        if (userId == 0) {
            userId = Math.abs(UUID.randomUUID().hashCode());
            sp().edit().putInt("userId", userId).apply();
        }
        return userId;
    }

    public static String getName() {
        String name = sp().getString("name", "");
//        if (TextUtils.isEmpty(name)) {
//            name = String.format("%s %s", randomName(R.array.random_surnames), randomName(R.array.random_names));
//            sp().edit().putString("name", name).apply();
//        }
        return name;
    }
   public static void setName(String name) {
       sp().edit().putString("name", name).apply();
    }

    private static String randomName(@ArrayRes int resId) {
        String[] names = ChatRoomApplication.instance.getResources().getStringArray(resId);
        return names[new Random().nextInt(names.length - 1)];
    }

    public static String getHeadimgurl() {
        return "";
    }
    public static int getAvatarIndex() {
        int avatarIndex = sp().getInt("avatarIndex", 0);
        if (avatarIndex == 0) {
            TypedArray images = ChatRoomApplication.instance.getResources().obtainTypedArray(R.array.random_avatar_images);
            int length = images.length();
            images.recycle();
            avatarIndex = new Random().nextInt(length - 1);
            sp().edit().putInt("avatarIndex", avatarIndex).apply();
        }
        return avatarIndex;
    }

    public static long getGameOverTime() {
        return sp().getLong("gameOverTime", 0l);
    }
    public static void setGameOverTime() {
        sp().edit().putLong("gameOverTime", System.currentTimeMillis()).apply();
    }
    public static int getAvatarResId(int index) {
        TypedArray images = ChatRoomApplication.instance.getResources().obtainTypedArray(R.array.random_avatar_images);
        int resId = images.getResourceId(index, R.mipmap.ic_unkown);
        images.recycle();
        return resId;
    }

}
