package com.dzw.service.impl;

import com.dzw.dao.MessageMapper;
import com.dzw.dao.UserInfoMapper;
import com.dzw.dao.UserMapper;
import com.dzw.dao.UserMatchMapper;
import com.dzw.entity.*;
import com.dzw.pojo.UserAndInfo;
import com.dzw.service.ChatService;
import com.dzw.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private UserMatchMapper userMatchMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Map<String, Object> getMatchList() {
        Map<String, Object> map = new HashMap<String, Object>();
        int userid = UserUtil.getLoginUser().getId();
        UserMatchExample example = new UserMatchExample();
        example.or().andUser1IdEqualTo(userid);
        example.or().andUser2IdEqualTo(userid);
        List<UserMatch> matchlist = userMatchMapper.selectByExample(example);
        if (matchlist == null) {
            map.put("message", "无匹配好友");
        }
        List<UserAndInfo> users = new ArrayList<UserAndInfo>();
        for (UserMatch item : matchlist) {
            UserAndInfo userAndInfo = new UserAndInfo();
            User user = null;
            if (item.getUser1Id() == userid) {
                 user = userMapper.selectByPrimaryKey(item.getUser2Id());
            }else {
                user = userMapper.selectByPrimaryKey(item.getUser1Id());
            }
            user.setPassword(null);
            userAndInfo.setUser(user);
            UserInfoExample example1 = new UserInfoExample();
            UserInfoExample.Criteria criteria = example1.createCriteria().andUserIdEqualTo(user.getId());
            List<UserInfo> userinfolist = userInfoMapper.selectByExample(example1);
            if (userinfolist != null && userinfolist.size()>0) {
                userAndInfo.setUserInfo(userinfolist.get(0));
            }
            users.add(userAndInfo);
        }
        map.put("matchlist", users);
        map.put("message", "success");
        return map;
    }

    @Override
    public String matchUser(Integer ortherId) {
        if(isMatch(ortherId)){
            return "matched";
        }
        int userid = UserUtil.getLoginUser().getId();
        Date date = new Date();
        UserMatch userMatch = new UserMatch();
        userMatch.setUser1Id(userid);
        userMatch.setUser2Id(ortherId);
        userMatch.setCreatetime(date);
        userMatch.setUpdatetime(date);
        if (userMatchMapper.insert(userMatch) > 0) {
            return "success";
        }
        return "fail";
    }

    @Override
    public Boolean isMatch(Integer ortherId) {
        int userid = UserUtil.getLoginUser().getId();
        UserMatchExample example = new UserMatchExample();
        example.or().andUser1IdEqualTo(userid).andUser2IdEqualTo(ortherId);
        example.or().andUser1IdEqualTo(ortherId).andUser2IdEqualTo(userid);
        List<UserMatch> matchlist = userMatchMapper.selectByExample(example);
        if (matchlist == null|| matchlist.size()<=0) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean sentMessage(String context,Integer userId, Integer otherId) {
        Message message = new Message();
        message.setContent(context);
        message.setSenderUserId(userId);
        message.setReceiverUserId(otherId);
        Date date = new Date();
        message.setCreatetime(date);
        message.setUpdatetime(date);
        if(messageMapper.insert(message) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Map<String,Object> getChatRecord(Integer otherId) {
        Map<String, Object> map = new HashMap<String, Object>();
        int userid = UserUtil.getLoginUser().getId();
        MessageExample example = new MessageExample();
        example.or().andSenderUserIdEqualTo(userid).andReceiverUserIdEqualTo(otherId);
        example.or().andSenderUserIdEqualTo(otherId).andReceiverUserIdEqualTo(userid);
        example.setOrderByClause("createtime desc LIMIT  0,4");
        List<Message> messageList = messageMapper.selectByExample(example);
        map.put("message", "success");
        map.put("record", messageList);
        return map;
    }
}
