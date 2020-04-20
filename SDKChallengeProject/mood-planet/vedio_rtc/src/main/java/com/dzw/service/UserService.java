package com.dzw.service;

import com.dzw.entity.Role;
import com.dzw.entity.User;
import com.dzw.entity.UserInfo;

import java.util.Map;


public interface UserService {

    public User findUserByName(String username);

    public Boolean saveUser(String username, String password);

    public Role findRole(User user);

    public Boolean saveUserInfo(UserInfo userInfo);

    public Boolean updateUserInfo(UserInfo userInfo);

    public Map<String,Object> getUserInfo(Integer userId);

}
