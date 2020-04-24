package mobi.accessible.show.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.accessible.show.R;
import mobi.accessible.show.util.MemberUtil;

public class ChannelData {

    public static final int MAX_SEAT_NUM = 10;

    public void release() {
        mAnchorId = null;
        Arrays.fill(mSeatArray, 0, mSeatArray.length, null);
        mUserStatus.clear();
        mMemberList.clear();
        mMessageList.clear();
    }

    // AnchorId
    private String mAnchorId;

    public boolean setAnchorId(String anchorId) {
        if (TextUtils.equals(anchorId, mAnchorId))
            return false;
        mAnchorId = anchorId;
        return true;
    }

    public String getAnchorId() {
        return mAnchorId;
    }
    public boolean hasAnchor() {
        return !TextUtils.isEmpty(mAnchorId);
    }

    public boolean isAnchor(String userId) {
        return TextUtils.equals(userId, mAnchorId);
    }

    public boolean isAnchorMyself() {
        return isAnchor(String.valueOf(Constant.sUserId));
    }

    // SeatArray
    private Seat[] mSeatArray = new Seat[MAX_SEAT_NUM];

    public Seat[] getSeatArray() {
        return mSeatArray;
    }

    public boolean updateSeat(int position, Seat seat) {
        Seat temp = mSeatArray[position];
        if (seat == temp)
            return false;
        if (seat != null && temp != null && TextUtils.equals(seat.getUserId(), temp.getUserId()))
            return false;
        mSeatArray[position] = seat;
        return true;
    }

    public int indexOfSeatArray(String userId) {
        for (int i = 0; i < mSeatArray.length; i++) {
            Seat seat = mSeatArray[i];
            if (seat == null) continue;
            if (TextUtils.equals(userId, seat.getUserId())) return i;
        }
        return -1;
    }

    public int firstIndexOfEmptySeat() {
        for (int i = 0; i < mSeatArray.length; i++) {
            Seat seat = mSeatArray[i];
            if (seat == null) return i;
            if (!isUserOnline(seat.getUserId())) return i;
        }
        return -1;
    }

    // UserStatus
    private Map<String, Boolean> mUserStatus = new HashMap<>();

    public boolean isUserOnline(String userId) {
        Boolean muted = mUserStatus.get(userId);
        return muted != null;
    }

    public boolean isUserMuted(String userId) {
        Boolean muted = mUserStatus.get(userId);
        if (muted != null) return muted;
        return false;
    }

    public void addOrUpdateUserStatus(int uid, boolean muted) {
        mUserStatus.put(String.valueOf(uid), muted);
    }

    public void removeUserStatus(int uid) {
        mUserStatus.remove(String.valueOf(uid));
    }

    // MemberList
    private List<Member> mMemberList = new ArrayList<>();

    public List<Member> getMemberList() {
        return mMemberList;
    }

    public void addOrUpdateMember(Member member) {
        int index = mMemberList.indexOf(member);
        if (index >= 0) {
            mMemberList.get(index).update(member);
        } else {
            mMemberList.add(member);
        }
    }

    public void removeMember(String userId) {
        Member member = new Member(userId);
        mMemberList.remove(member);
    }

    public Member getMember(String userId) {
        for (Member member : mMemberList) {
            if (TextUtils.equals(userId, member.getUserId())) {
                return member;
            }
        }
        return null;
    }

    public int getMemberAvatar(String userId) {
        Member member = getMember(userId);
        if (member == null) {
            return R.mipmap.ic_unkown;
        }
        return MemberUtil.getAvatarResId(member.getAvatarIndex());
    }

    public int indexOfMemberList(String userId) {
        return mMemberList.indexOf(new Member(userId));
    }

    // MessageList
    private List<Message> mMessageList = new ArrayList<>();

    public List<Message> getMessageList() {
        return mMessageList;
    }

    public int addMessage(Message message) {
        mMessageList.add(message);
        return mMessageList.size() - 1;
    }

}
