package com.dzw.service.impl;


import com.dzw.dao.UserInfoMapper;
import com.dzw.dao.UserMapper;
import com.dzw.entity.*;
import com.dzw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public User findUserByName(String username) {
        if (username == null) {
            return null;
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);
        List<User> userlist = userMapper.selectByExample(example);
        if (userlist == null) {
            return null;
        }
        return userlist.get(0);
    }

    @Override
    public Role findRole(User user) {
//        if (user == null) {
//            return null;
//        }
//        UserRole userRole = userRoleMapper.selectByPrimaryKey(user.getId());
//        Role role = roleMapper.selectByPrimaryKey(userRole.getRid());
//        return role;
        return null;
    }

    @Override
    public Boolean saveUser(String username,String password) {
        if (username==null||password==null) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (userMapper.insert(user) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean saveUserInfo(UserInfo userInfo) {
        if (userInfo == null||userInfo.getUserId()==null) {
            return false;
        }
        if(userInfoMapper.insert(userInfo)>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateUserInfo(UserInfo userInfo) {
        if (userInfo == null||userInfo.getUserId()==null||userInfo.getId()==null) {
            return false;
        }
        if(userInfoMapper.updateByPrimaryKeySelective(userInfo)>0){
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getUserInfo(Integer userId) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<UserInfo> userinfolist = userInfoMapper.selectByExample(example);
        if (userinfolist == null|| userinfolist.size()<1) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userinfo",userinfolist.get(0));
        map.put("message", "success");
        return map;
    }
}
