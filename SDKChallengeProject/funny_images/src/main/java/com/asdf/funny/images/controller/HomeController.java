package com.asdf.funny.images.controller;

import com.asdf.funny.images.util.MybatisBaseQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {
    private static String sessionId="";
    @RequestMapping("gohome")
    public ModelAndView GetUser(){
        return new ModelAndView("index");
    }
    @RequestMapping("goGifIndex")
    public ModelAndView goGifIndex(){
        return new ModelAndView("gifIndex");
    }
    @RequestMapping("goUploadVideo")
    public ModelAndView goUploadVideo(){
        return new ModelAndView("uploadVideo");
    }
    @RequestMapping("goVideoIndex")
    public ModelAndView goVideoIndex(){
        return new ModelAndView("video");
    }
    @RequestMapping("goUpload")
    public ModelAndView goUpload(){
        return new ModelAndView("upload");
    }
    @RequestMapping("goVerify")
    public ModelAndView goVerify(){
        return new ModelAndView("verify");
    }
    @RequestMapping("goRandom")
    public ModelAndView goRandom(){
        return new ModelAndView("rand");
    }


    @RequestMapping("goVerifyLogin")
    public ModelAndView goVerifyLogin(){
        return new ModelAndView("verifyLogin");
    }



}
