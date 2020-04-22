package com.asdf.funny.images.service;


import com.asdf.funny.images.dao.UserMapper;
import com.asdf.funny.images.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User Sel(int id){
        return userMapper.Sel(id);
    }
}
