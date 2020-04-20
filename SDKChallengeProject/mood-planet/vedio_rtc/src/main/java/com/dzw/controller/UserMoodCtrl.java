package com.dzw.controller;

import com.dzw.entity.UserMoodWord;
import com.dzw.service.MoodWordService;
import com.dzw.service.UserMoodService;
import com.dzw.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class UserMoodCtrl {

    @Autowired
    private MoodWordService moodWordService;

    @GetMapping("moodWord")
    public Object getMoodWord(@RequestParam("context") String context,String img) {
        Map<String,Object> map = moodWordService.publishMoodWord(context,img);
        return map;
    }


    @GetMapping("personalMood")
    public Object getpersonalMood() {
        List<UserMoodWord> userMoodWords= moodWordService.selectMoodWordByUserId(UserUtil.getLoginUser().getId());
        return userMoodWords;
    }

    @GetMapping("xingqiuMood")
    public Object getxingqiuMood() {
        List<UserMoodWord> userMoodWords= moodWordService.selectAllMoodWord();
        return userMoodWords;
    }


}
