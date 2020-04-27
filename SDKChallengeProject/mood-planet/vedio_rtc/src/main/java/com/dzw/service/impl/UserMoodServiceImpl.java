package com.dzw.service.impl;

import com.dzw.dao.UserMoodMapper;
import com.dzw.entity.UserMood;
import com.dzw.entity.UserMoodExample;
import com.dzw.entity.UserMoodWord;
import com.dzw.service.UserMoodService;
import com.dzw.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserMoodServiceImpl implements UserMoodService {

    @Autowired
    private UserMoodMapper userMoodMapper;

    @Override
    public UserMood selectOneUserMoodByUserId(int userId) {
        List<UserMood> userMoodlist = selectUserMoodByUserId(userId);
        if (userMoodlist == null) {
            return null;
        }
        return userMoodlist.get(0);
    }

    @Override
    public List<UserMood> selectUserMoodByUserId(int userId) {
        UserMoodExample example = new UserMoodExample();
        UserMoodExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<UserMood> userMoodlist = userMoodMapper.selectByExample(example);
        return userMoodlist;
    }

    @Override
    public List<UserMood> selectAllUserMood() {
        List<UserMood> userMoodlist = userMoodMapper.selectByExample(null);
        return userMoodlist;
    }

    @Override
    public UserMood publishMoodType(Integer type) {
        Date date = new Date();
        UserMood userMood = new UserMood();

        userMood.setUserId(UserUtil.getLoginUser().getId());
        userMood.setMoodType(0);
        userMood.setCreatetime(date);
        userMood.setUpdatetime(date);

        if (userMoodMapper.insert(userMood) > 0) {
            return userMood;
        }

        return null;
    }
}
