package com.dzw.service;

import com.dzw.entity.UserMood;
import com.dzw.entity.UserMoodWord;

import java.util.List;
import java.util.Map;

public interface UserMoodService {

    public UserMood selectOneUserMoodByUserId(int userId);
    public List<UserMood> selectUserMoodByUserId(int userId);
    public List<UserMood> selectAllUserMood();

   //发布心情类型
   public UserMood publishMoodType(Integer type);
}
