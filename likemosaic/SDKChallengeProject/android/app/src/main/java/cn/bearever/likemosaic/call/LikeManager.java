package cn.bearever.likemosaic.call;

/**
 * 好感度管理器
 *
 * @author luoming
 * @date 2020/4/18
 */
public class LikeManager {
    private static LikeManager instance;
    //我对对方的好感度
    private int mLikeCountMe2Other = 60;
    //对方对我的好感度
    private int mLikeCountOther2Me = 60;
    private final int MOSAIC_LEVEL_1 = 8;
    private final int MOSAIC_LEVEL_2 = 16;
    private final int MOSAIC_LEVEL_3 = 32;
    private final int MOSAIC_LEVEL_NO = 0;

    public static LikeManager getInstance() {
        if (instance == null) {
            synchronized (LikeManager.class) {
                if (instance == null) {
                    instance = new LikeManager();
                }
            }
        }
        return instance;
    }

    /**
     * 设置对方对我的好感度
     *
     * @param likeCount
     */
    public void setLikeCountOther2Me(int likeCount) {
        mLikeCountOther2Me = likeCount;
    }

    /**
     * 设置我对对方的好感度
     *
     * @param likeCount
     */
    public void setLikeCountMe2Other(int likeCount) {
        mLikeCountMe2Other = likeCount;
    }

    public int getRemoteMosaicLevel() {
        return getMosaicLevel(mLikeCountOther2Me);
    }

    public int getLocalMosaicLevel() {
        return getMosaicLevel(mLikeCountMe2Other);
    }


    public void reset() {
        mLikeCountOther2Me = 60;
        mLikeCountMe2Other = 60;
    }

    private int getMosaicLevel(int likeCount) {
        if (likeCount >= 300) {
            return MOSAIC_LEVEL_NO;
        } else if (likeCount >= 200) {
            return MOSAIC_LEVEL_1;
        } else if (likeCount >= 100) {
            return MOSAIC_LEVEL_2;
        } else {
            return MOSAIC_LEVEL_3;
        }
    }
}
