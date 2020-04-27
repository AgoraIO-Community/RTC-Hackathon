package com.asdf.funny.images.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static String sessionId="";
    private static String randomStr="";
    @RequestMapping("setCookie")
    @ResponseBody
    public void setCookie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session= request.getSession();
        Cookie cookie= new Cookie("a",session.getId());
        sessionId=session.getId();
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
    }



    @RequestMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
      String clientCookie=request.getRequestedSessionId();
      String randomStra=ServletRequestUtils.getStringParameter(request,"randomStr");
      if(clientCookie==null || !clientCookie.equals(sessionId)){
          return "登陆失败";
      }else {
          randomStr=randomStra;
          return "redirect:/home/goVerify";
      }
    }

    //验证是否扫码登录
    @RequestMapping("verifyLogin")
    @ResponseBody
    public boolean verifyLogin(@RequestParam("broRandom")String broRandom) throws Exception{
        if(broRandom.equals(randomStr)){
            return true;
        }else {
            return false;
        }
    }




}
