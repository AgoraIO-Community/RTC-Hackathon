package com.dzw.util;

import com.dzw.entity.User;
import org.apache.shiro.SecurityUtils;

public class UserUtil {

    public static User getLoginUser(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    public static Integer getOtherId(String message){
        String sub = message.substring(0,message.indexOf(','));
        return Integer.valueOf(sub);
    }
}
