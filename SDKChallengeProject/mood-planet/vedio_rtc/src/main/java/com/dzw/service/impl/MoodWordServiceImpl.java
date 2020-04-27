package com.dzw.service.impl;

import com.dzw.dao.UserMoodWordMapper;
import com.dzw.entity.UserMood;
import com.dzw.entity.UserMoodWord;
import com.dzw.entity.UserMoodWordExample;
import com.dzw.service.MoodWordService;
import com.dzw.service.UserMoodService;
import com.dzw.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MoodWordServiceImpl implements MoodWordService {

    @Autowired
    private UserMoodWordMapper userMoodWordMapper;

    @Autowired
    private UserMoodService userMoodService;

    @Override
    public UserMoodWord selectOneMoodWordByUserId(int userId) {
        List<UserMoodWord> userMoodWordlist = selectMoodWordByUserId(userId);
        if (userMoodWordlist == null) {
            return null;
        }
        return userMoodWordlist.get(0);
    }

    @Override
    public List<UserMoodWord> selectMoodWordByUserId(int userId) {
        UserMoodWordExample example = new UserMoodWordExample();
        UserMoodWordExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<UserMoodWord> userMoodWordlist = userMoodWordMapper.selectByExample(example);
        return userMoodWordlist;
    }

    @Override
    public List<UserMoodWord> selectAllMoodWord() {
        List<UserMoodWord> userMoodWordlist = userMoodWordMapper.selectByExample(null);
        return userMoodWordlist;
    }

    @Override
    public UserMoodWord selectOneMoodWordByMoodId(int moodId) {
        UserMoodWordExample example = new UserMoodWordExample();
        UserMoodWordExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(moodId);
        List<UserMoodWord> userMoodWordlist = userMoodWordMapper.selectByExample(example);
        if (userMoodWordlist == null) {
            return null;
        }
        return userMoodWordlist.get(0);
    }

    @Override
    public Map<String, Object> publishMoodWord(String context,String img) {
        Map<String, Object> map = new HashMap<String, Object>();
        int userId = UserUtil.getLoginUser().getId();
        Date date = new Date();
        UserMood userMood = userMoodService.publishMoodType(0);
        UserMoodWord userMoodWord = new UserMoodWord();
        userMoodWord.setContent(context);
        userMoodWord.setImg(img);
        userMoodWord.setUserId(userId);
        userMoodWord.setUserMoodId(userMood.getId());
        userMoodWord.setCreatetime(date);
        userMoodWord.setUpdatetime(date);
        if (userMoodWordMapper.insert(userMoodWord)>0) {
            map.put("mood", userMood);
            map.put("moodword", userMoodWord);
            map.put("message", "success");
            return map;
        }
        map.put("message", "fail");
        return map;
    }
}
