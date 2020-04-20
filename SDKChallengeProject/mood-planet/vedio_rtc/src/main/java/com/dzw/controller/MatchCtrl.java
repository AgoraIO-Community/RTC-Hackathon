package com.dzw.controller;

import com.dzw.entity.UserInfo;
import com.dzw.service.ChatService;
import com.dzw.service.UserService;
import com.dzw.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class MatchCtrl {

    private final static Logger logger = Logger.getLogger(MatchCtrl.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @GetMapping("matchlist")
    public Object getmatchlist() {
        Map<String, Object> map = chatService.getMatchList();
        return map;
    }

    @GetMapping("matchother")
    public Object getmatchlist(@RequestParam("otherid") Integer otherid) {
        String response = chatService.matchUser(otherid);
        return response;
    }

    @GetMapping("messagelist")
    public Object getmessagelist(@RequestParam("otherid") Integer otherid) {
        Map<String,Object> map = chatService.getChatRecord(otherid);
        return map;
    }

    @GetMapping("userimg")
    public Map<String,Object> getUserImg(Integer otherId){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userId = UserUtil.getLoginUser().getId();
        Map<String, Object> usermap = userId!=null?userService.getUserInfo(userId):null;
        if(usermap!=null&&usermap.get("message").equals("success")&&usermap.get("userinfo")!=null){
            String userImg = ((UserInfo)usermap.get("userinfo")).getImg();
            map.put("userImg", userImg);
        }
        Map<String, Object> othermap = otherId!=null?userService.getUserInfo(otherId):null;
        if(othermap!=null&&othermap.get("message").equals("success")&&othermap.get("userinfo")!=null){
            String otherImg = ((UserInfo)othermap.get("userinfo")).getImg();
            map.put("otherImg", otherImg);
        }
        map.put("message", "success");
        return map;
    }
}
