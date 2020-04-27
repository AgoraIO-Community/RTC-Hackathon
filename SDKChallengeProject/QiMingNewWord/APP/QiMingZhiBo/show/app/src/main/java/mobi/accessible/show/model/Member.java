package mobi.accessible.show.model;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

public class Member {

    private String userId;
    private String name;
    private int avatarIndex;
    private String headimgurl;

    public Member(String userId) {
        this.userId = userId;
    }

    public Member(String userId, String name,String headimgurl, int avatarIndex) {
        this(userId);
        this.name = name;
        this.headimgurl = headimgurl;
        this.avatarIndex = avatarIndex;
    }
    public String getUserId() {
        return userId;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatarIndex() {
        return avatarIndex;
    }

    public void setAvatarIndex(int avatarIndex) {
        this.avatarIndex = avatarIndex;
    }

    public void update(Member member) {
        this.name = member.name;
        this.avatarIndex = member.avatarIndex;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Member)
            return TextUtils.equals(userId, ((Member) obj).userId);
        return super.equals(obj);
    }

    public String toJsonString() {
        return new Gson().toJson(this);
    }

    public static Member fromJsonString(String str) {
        return new Gson().fromJson(str, Member.class);
    }

}
