package com.dzw.controller;

import com.dzw.entity.User;
import com.dzw.entity.UserInfo;
import com.dzw.service.UserService;
import com.dzw.util.UserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public Object login() {
        return "Here is Login page";
    }

    @GetMapping("unauthc")
    public Object unauthc() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    @GetMapping("doLogin")
    public Object doLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            return "password error!";
        } catch (UnknownAccountException uae) {
            return "username error!";
        }

        User user = userService.findUserByName(username);
        subject.getSession().setAttribute("user", user);

        Cookie userCoo = new Cookie("username",user.getUsername());
        Cookie idCoo = new Cookie("userid",user.getId().toString());
        response.addCookie(userCoo);
        response.addCookie(idCoo);

        return "SUCCESS";
    }

    @PostMapping("doRegister")
    public Object register(@RequestParam String username, @RequestParam String password,@RequestParam(required = false) String role) {
        if (userService.saveUser(username, password) == true) {
            return "SUCCESS";
        }
        return "fail";
    }

    @PostMapping("/api/setUserInfo")
    public Boolean setUserInfo( UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        } else if (userInfo.getId() == null||userInfo.getId()==0) {
            userInfo.setId(null);
            return userService.saveUserInfo(userInfo);
        }else {
            return userService.updateUserInfo(userInfo);
        }
    }

    @GetMapping("/api/getUserInfo")
    public Map<String, Object> getUserInfo() {
        User user = UserUtil.getLoginUser();
        if (user == null || user.getId()==null) {
            return null;
        } else  {
            return userService.getUserInfo(user.getId());
        }
    }

    @PostMapping("/upload")
    public Map<String,Object> setUserImage(HttpServletRequest req,  MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //原始名称
        String originalFilename = file.getOriginalFilename();
        //上传图片
        if(file!=null && originalFilename!=null && originalFilename.length()>0){
            //存储图片的物理路径
            String pic_path = "E:\\photos\\";
            //新的图片名称
            String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //新图片
            File newFile = new File(pic_path+newFileName);
            //将内存中的数据写入磁盘
            file.transferTo(newFile);
            map.put("message", "success");
            map.put("imgsrc",newFileName );
            return map;
        }
        map.put("message", "fail");
        return map;
    }
}