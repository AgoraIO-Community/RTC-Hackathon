package cn.bearever.mosaicserver.match;

import java.util.*;

public class MatchManager {
    private static volatile MatchManager instance;
    private final Object LOCK_UID;
    private final Object LOCK_CHANNEL;
    private final int MAX_ALIVE_TIME = 6000 * 25;
    /**
     * 申请匹配的用户列表
     */
    private Map<String, Long> mPostUidMap;
    /**
     * 已经匹配的记录
     */
    private Map<String, MatchData> mChannelMap;
    private static final String PRE_KEY = "luoming";
    private String mTime;
    private long count = 0;
    private Base64.Encoder mBase64;


    public static MatchManager getInstance() {
        if (instance == null) {
            synchronized (MatchManager.class) {
                if (instance == null) {
                    instance = new MatchManager();
                }
            }
        }
        return instance;
    }

    private MatchManager() {
        LOCK_UID = new Object();
        LOCK_CHANNEL = new Object();
        mPostUidMap = new HashMap<>();
        mChannelMap = new HashMap<>();
    }

    /**
     * 加入匹配队列
     *
     * @param uid
     */
    public void add(String uid) {
        synchronized (LOCK_UID) {
            mPostUidMap.put(uid, System.currentTimeMillis());
            setupChannel();
        }
    }

    /**
     * 为等待中的用户分配频道
     */
    private void setupChannel() {
        if (mBase64 == null) {
            mBase64 = Base64.getMimeEncoder();
        }
        Set<Map.Entry<String, Long>> entries = mPostUidMap.entrySet();

        Iterator<Map.Entry<String, Long>> iterator = entries.iterator();
        while (entries.size() >= 2) {

            Map.Entry<String, Long> entry1 = iterator.next();
            Map.Entry<String, Long> entry2 = iterator.next();
            if (System.currentTimeMillis() - entry1.getValue() >= MAX_ALIVE_TIME) {
                entries.remove(entry1);
                entry1 = null;
            }

            if (System.currentTimeMillis() - entry2.getValue() >= MAX_ALIVE_TIME) {
                entries.remove(entry2);
                entry2 = null;
            }

            if (entry1 == null || entry2 == null) {
                continue;
            }


            count++;
            mTime = PRE_KEY + count;
            String channel = mBase64.encodeToString(mTime.getBytes());

            synchronized (LOCK_CHANNEL) {
                MatchData matchDataA = new MatchData(entry2.getKey(), channel, entry2.getValue());
                MatchData matchDataB = new MatchData(entry1.getKey(), channel, entry1.getValue());
                mChannelMap.put(entry1.getKey(), matchDataA);
                mChannelMap.put(entry2.getKey(), matchDataB);
            }
            entries.remove(entry1);
            entries.remove(entry2);
        }
    }

    public void remove(String uid) {
        synchronized (LOCK_UID) {
            mPostUidMap.remove(uid);
        }
    }

    /**
     * 获取用户加入的频道
     *
     * @param uid 用户id
     * @return 频道号
     */
    public MatchData getChannel(String uid) {
        synchronized (LOCK_CHANNEL) {
            MatchData matchData = mChannelMap.remove(uid);
            if (matchData == null) {
                return null;
            }
            if (System.currentTimeMillis() - matchData.time >= MAX_ALIVE_TIME) {
                return null;
            }
            return matchData;
        }
    }

    public static class MatchData {
        public String uid;
        public String channel;
        public long time;

        public MatchData(String uid, String channel, long time) {
            this.uid = uid;
            this.channel = channel;
            this.time = time;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof MatchData) {
                return this.uid.equals(((MatchData) obj).uid);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.uid);
        }
    }

    public interface MatchCallback {
        void onMatch(String uid, String channel);
    }
}
