package com.asdf.funny.images.controller;


import com.asdf.funny.images.service.AttachmentsManager;
import com.asdf.funny.images.service.ImgService;
import com.asdf.funny.images.service.QRCodeService;
import com.asdf.funny.images.util.GifUtils;
import com.asdf.funny.images.util.MybatisBaseQuery;
import com.asdf.funny.images.util.SnowflakeIdWorker;
import com.tinify.Tinify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/uploadVedio")
public class UploadVedioController {
    @Value("${uploadVideoFilePar}")
    private String uploadVideoFilePar;
    @Autowired
    private ImgService imgService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @GetMapping("/generatePage")
    public void generatePage(@RequestParam("title")String title,@RequestParam("fileName")String fileName) throws IOException {
        //创建日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr=dateFormat.format(new Date());

        //创建模版加载器
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/uploadVedio/");  //模板文件的所在目录
        resolver.setSuffix(".html");       //模板文件后缀
        //创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        //将加载器放入模板引擎
        templateEngine.setTemplateResolver(resolver);
        //创建文件夹
        String numberFile=snowflakeIdWorker.nextId()+"";
        //存库
        imgService.insertVideo(Long.parseLong(numberFile),title,fileName,nowStr);
        String filePar =uploadVideoFilePar+numberFile+"/";// 文件夹路径
        File myPath = new File( filePar );
        if ( !myPath.exists()){//若此目录不存在，则创建之
            myPath.mkdir();
            System.out.println("创建文件夹路径为："+ filePar);
        }
        //创建字符输出流并且自定义输出文件的位置和文件名
        FileWriter writer = new FileWriter(filePar+"single-video.html");
        //创建Context对象(存放Model)
        Context context = new Context();
        //放入数据
        context.setVariable("title",title);
        context.setVariable("fileName",fileName);
        context.setVariable("nowStr",nowStr);
        //创建静态文件,"text"是模板html名字
        templateEngine.process("single-video",context,writer);
    }
}
