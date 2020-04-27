package cn.bearever.mosaicserver.topic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 话题管理类
 */
@Service
public class TopicService {
    private static volatile TopicService instance;
    private List<TopicDao> mTopicList = new ArrayList<>();
    private Random mRandom;
    private long mLastUpdateTime = 0;
    private long MIN_UPDATE_TIME = 1000 * 60 * 10;
    private int EACH_COUNT = 8;

    @Autowired
    private TopicRepository topicRepository;

    public static TopicService getInstance() {
        if (instance == null) {
            synchronized (TopicService.class) {
                if (instance == null) {
                    instance = new TopicService();
                }
            }
        }

        return instance;
    }


    /**
     * 获取话题信息
     *
     * @param uid
     * @return
     */
    public List<TopicDao> getTopics(String uid) {
        checkStore();
        if (mRandom == null) {
            mRandom = new Random();
        }
        int index = 0;
        List<TopicDao> list = new ArrayList<>();
        while (list.size() < EACH_COUNT) {
            index = (int) (mRandom.nextDouble() * mTopicList.size());
            TopicDao dao = mTopicList.get(index);
            if (list.contains(dao)) {
                continue;
            }
            list.add(dao);
        }
        return list;
    }

    private synchronized void checkStore() {
        if (System.currentTimeMillis() - mLastUpdateTime >= MIN_UPDATE_TIME) {
            //需要刷新缓存
            mTopicList = topicRepository.getList();
        }
        mLastUpdateTime = System.currentTimeMillis();
    }


}
