package com.dzw.service;

import java.util.Map;

public interface ChatService {
    //用户匹配列表
    public Map<String,Object> getMatchList();

    //匹配好友
    public String matchUser(Integer otherId);

    //查询是否已匹配
    public Boolean isMatch(Integer otherId);

    //用户发送信息
    public Boolean sentMessage(String context,Integer userId,Integer otherId);

    //获取某用户聊天信息
    public Map<String,Object> getChatRecord(Integer otherId);
}
