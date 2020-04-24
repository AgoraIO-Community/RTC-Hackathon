package mobi.accessible.show.model;

import android.text.TextUtils;

import mobi.accessible.show.util.MemberUtil;

public class Constant {

    public static final int sUserId = MemberUtil.getUserId();

    public static boolean isMyself(String userId) {
        return TextUtils.equals(userId, String.valueOf(Constant.sUserId));
    }

    public static final String sName = MemberUtil.getName();
    public static final int sAvatarIndex = MemberUtil.getAvatarIndex();
    public static final String headimgurl = MemberUtil.getHeadimgurl();

}
