package mobi.accessible.show.model;

import android.text.TextUtils;

import java.util.Locale;

public class AttributeKey {
    public static final String KEY_ANCHOR_ID = "anchorId";
    public static final String[] KEY_SEAT_ARRAY = initSeatKeys();
    public static final String KEY_USER_INFO = "userInfo";

    private static String[] initSeatKeys() {
        String[] strings = new String[ChannelData.MAX_SEAT_NUM];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = String.format(Locale.getDefault(), "seat%d", i);
        }
        return strings;
    }

    public static int indexOfSeatKey(String key) {
        for (int i = 0; i < KEY_SEAT_ARRAY.length; i++) {
            if (TextUtils.equals(key, KEY_SEAT_ARRAY[i])) return i;
        }
        return -1;
    }
}
