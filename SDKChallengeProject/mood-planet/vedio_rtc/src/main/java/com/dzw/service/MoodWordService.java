package com.dzw.service;

import com.dzw.entity.UserMoodWord;

import java.util.List;
import java.util.Map;

public interface MoodWordService {
    public UserMoodWord selectOneMoodWordByUserId(int userId);
    public List<UserMoodWord> selectMoodWordByUserId(int userId);
    public List<UserMoodWord> selectAllMoodWord();
    public UserMoodWord selectOneMoodWordByMoodId(int moodId);

    //发布心情
    public Map<String, Object> publishMoodWord(String context,String img);
}
