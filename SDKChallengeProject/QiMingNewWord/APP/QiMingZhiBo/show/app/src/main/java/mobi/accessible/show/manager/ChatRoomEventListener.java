package mobi.accessible.show.manager;

public interface ChatRoomEventListener {

    void onSeatUpdated(int position);

    void onUserGivingGift(String userId);

    void onMessageAdded(int position);

    void onMemberListUpdated(String userId);

    void onUserStatusChanged(String userId, Boolean muted);

    void onAudioMixingStateChanged(boolean isPlaying);

    void gameOver(boolean isOnline);
    void onAudioVolumeIndication(String userId, int volume);
    void onApplyForBroadcaster(String userId);
}
