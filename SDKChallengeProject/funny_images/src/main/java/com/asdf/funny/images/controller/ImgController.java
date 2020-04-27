package com.asdf.funny.images.controller;


import com.alibaba.fastjson.JSONObject;
import com.asdf.funny.images.service.AttachmentsManager;
import com.asdf.funny.images.service.ImgService;
import com.asdf.funny.images.service.QRCodeService;
import com.asdf.funny.images.service.UserService;
import com.asdf.funny.images.util.FileUtil;
import com.asdf.funny.images.util.GifUtils;
import com.asdf.funny.images.util.MybatisBaseQuery;
import com.tinify.Source;
import com.tinify.Tinify;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.KeyUtil;

import java.io.*;
import java.text.DecimalFormat;
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
@RequestMapping("/img")
public class ImgController {

    @Value("${uploadVideoFilePar}")
    private String uploadVideoFilePar;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private ImgService imgService;
    @Autowired
    private AttachmentsManager attachmentsManager;
    public static String baseImgUrl = "/admin/accessorybag/attachments/images?mongoId=";

    @RequestMapping("newImg")
    @ResponseBody
    public MybatisBaseQuery newImg(@RequestParam(value = "currentPage")Integer currentPage,@RequestParam(value = "pageSize")Integer pageSize){
        Integer totalNum=imgService.totalNum();
        MybatisBaseQuery mybatisBaseQuery=new MybatisBaseQuery();
        mybatisBaseQuery.setBaseQueryInfo(currentPage,pageSize,totalNum);
        List<Map<String,Object>> maps=imgService.newImg(mybatisBaseQuery.getStartNum(),mybatisBaseQuery.getPageSize());
        mybatisBaseQuery.setList(maps);
        return mybatisBaseQuery;
    }

    @RequestMapping("randImg")
    @ResponseBody
    public MybatisBaseQuery randImg(@RequestParam(value = "currentPage")Integer currentPage,@RequestParam(value = "pageSize")Integer pageSize){
        Integer totalNum=imgService.totalNum();
        MybatisBaseQuery mybatisBaseQuery=new MybatisBaseQuery();
        mybatisBaseQuery.setBaseQueryInfo(currentPage,pageSize,totalNum);
        List<Map<String,Object>> maps=imgService.randImg(mybatisBaseQuery.getStartNum(),mybatisBaseQuery.getPageSize());
        mybatisBaseQuery.setList(maps);
        return mybatisBaseQuery;
    }

    @RequestMapping("verify")
    @ResponseBody
    public MybatisBaseQuery verify(@RequestParam(value = "currentPage")Integer currentPage,@RequestParam(value = "pageSize")Integer pageSize){
        Integer totalNum=imgService.totalNumVerify();
        MybatisBaseQuery mybatisBaseQuery=new MybatisBaseQuery();
        mybatisBaseQuery.setBaseQueryInfo(currentPage,pageSize,totalNum);
        List<Map<String,Object>> maps=imgService.newImgVerify(mybatisBaseQuery.getStartNum(),mybatisBaseQuery.getPageSize());
        for(Map<String,Object> map:maps){
            map.put("id",map.get("id").toString());
        }
        mybatisBaseQuery.setList(maps);
        return mybatisBaseQuery;
    }

    @RequestMapping("verifyPass")
    @ResponseBody
    public Map<String, Object> verifyPass(@RequestParam(value = "imgId")long imgId){
        Map<String, Object> newMap = new HashMap<>();
        //审核通过
        imgService.verifyPass(imgId);
        newMap.put("message","success");
        return newMap;
    }

    @RequestMapping("verifyFail")
    @ResponseBody
    public Map<String, Object> verifyFail(@RequestParam(value = "imgId")long imgId){
        Map<String, Object> newMap = new HashMap<>();
        //审核通过
        imgService.verifyFail(imgId);
        newMap.put("message","success");
        return newMap;
    }



    @RequestMapping("gifImg")
    @ResponseBody
    public MybatisBaseQuery gifImg(@RequestParam(value = "currentPage")Integer currentPage,@RequestParam(value = "pageSize")Integer pageSize){
        Integer totalNum=imgService.giftotalNum();
        MybatisBaseQuery mybatisBaseQuery=new MybatisBaseQuery();
        mybatisBaseQuery.setBaseQueryInfo(currentPage,pageSize,totalNum);
        List<Map<String,Object>> maps=imgService.newGifImg(mybatisBaseQuery.getStartNum(),mybatisBaseQuery.getPageSize());
        mybatisBaseQuery.setList(maps);
        return mybatisBaseQuery;
    }

    @RequestMapping("video")
    @ResponseBody
    public MybatisBaseQuery video(@RequestParam(value = "currentPage")Integer currentPage,@RequestParam(value = "pageSize")Integer pageSize){
        Integer totalNum=imgService.videototalNum();
        MybatisBaseQuery mybatisBaseQuery=new MybatisBaseQuery();
        mybatisBaseQuery.setBaseQueryInfo(currentPage,pageSize,totalNum);
        List<Map<String,Object>> maps=imgService.newvideoImg(mybatisBaseQuery.getStartNum(),mybatisBaseQuery.getPageSize());
        mybatisBaseQuery.setList(maps);
        return mybatisBaseQuery;
    }

    @RequestMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, @RequestParam(value="location",defaultValue ="0") Integer location) {
        Map<String, Object> newMap = new HashMap<>();
        try {
            String fileName=file.getOriginalFilename();
            String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
            if(fileTyle.equals("jpg") || fileTyle.equals("png") || fileTyle.equals("jpeg")){
                //原图
                String fileRealName=fileName.substring(0,fileName.lastIndexOf("."));
                Map<String, Object> map = attachmentsManager.fileUploadTMongoDBAndReids(file);
                if ("true".equals(map.get("success"))) {
                    //添加成功
                    //存到数据库
                    imgService.inserImg((String)map.get("msg"),fileTyle,"0");
                    String img = baseImgUrl + map.get("msg");
                    newMap.put("img", img);
                    newMap.put("mongoId", map.get("msg"));
                    newMap.put("fileName", map.get("fileName"));
                    newMap.put("state","success");
                }
                //压缩
                Tinify.setKey("a80NwM2NcFgZsnh8t8wtMcYAxFTSeoNj");
                byte[] resultData = Tinify.fromBuffer(file.getBytes()).toBuffer();
                Map<String, Object> mapYasuo = attachmentsManager.fileUploadTMongoDBAndReidsByte(resultData,file.getSize(),fileTyle,fileRealName);
                if ("true".equals(mapYasuo.get("success"))) {
                    //添加成功
                    //存到数据库
                    imgService.inserImgYasuo((String)mapYasuo.get("msg"),fileTyle,(String)map.get("msg"));
                    String img = baseImgUrl + mapYasuo.get("msg");
                    newMap.put("img", img);
                    newMap.put("mongoId", mapYasuo.get("msg"));
                    newMap.put("fileName", mapYasuo.get("fileName"));
                    newMap.put("state","success");
                }
            }else if(fileTyle.equals("gif")){
                //原图
                String fileRealName=fileName.substring(0,fileName.lastIndexOf("."));
                Map<String, Object> map = attachmentsManager.fileUploadTMongoDBAndReids(file);
                if ("true".equals(map.get("success"))) {
                    //添加成功
                    //存到数据库
                    imgService.inserImg((String)map.get("msg"),fileTyle,"0");
                    String img = baseImgUrl + map.get("msg");
                    newMap.put("img", img);
                    newMap.put("mongoId", map.get("msg"));
                    newMap.put("fileName", map.get("fileName"));
                    newMap.put("state","success");
                }
                //压缩
                String filePath=ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/temp/a.gif";

                File f = null;
                InputStream ins = file.getInputStream();
                f=new File(file.getOriginalFilename());
                inputStreamToFile(ins, f);
                GifUtils.resizeByWidthMy(f, filePath);

                InputStream input = new FileInputStream(new File(filePath));
                byte[] byt = new byte[input.available()];
                input.read(byt);

                Map<String, Object> mapYasuo = attachmentsManager.fileUploadTMongoDBAndReidsByte(byt,file.getSize(),fileTyle,fileRealName);
                if ("true".equals(mapYasuo.get("success"))) {
                    //添加成功
                    //存到数据库
                    imgService.inserImgYasuo((String)mapYasuo.get("msg"),fileTyle,(String)map.get("msg"));
                    String img = baseImgUrl + mapYasuo.get("msg");
                    newMap.put("img", img);
                    newMap.put("mongoId", mapYasuo.get("msg"));
                    newMap.put("fileName", mapYasuo.get("fileName"));
                    newMap.put("state","success");
                }
                File del = new File(f.toURI());
                del.delete();

            }else{
                //原图
                String fileRealName=fileName.substring(0,fileName.lastIndexOf("."));
                Map<String, Object> map = attachmentsManager.fileUploadTMongoDBAndReids(file);
                if ("true".equals(map.get("success"))) {
                    //添加成功
                    //存到数据库
                    imgService.inserImg((String)map.get("msg"),fileTyle,"0");
                    String img = baseImgUrl + map.get("msg");
                    newMap.put("img", img);
                    newMap.put("mongoId", map.get("msg"));
                    newMap.put("fileName", map.get("fileName"));
                    newMap.put("state","success");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            newMap.put("state","error");
        }
        return newMap;

    }

    @RequestMapping("uploadBlogVideo")
    @ResponseBody
    public Map<String, Object> uploadBlogVideo(@RequestParam("file") MultipartFile file, @RequestParam(value="location",defaultValue ="0") Integer location) {
        Map<String, Object> newMap = new HashMap<>();
        try {
            String fileName = file.getOriginalFilename();
            String filePath=uploadVideoFilePar+"video"+"/";
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            newMap.put("state","success");
            newMap.put("fileName", fileName);
        }catch (Exception e){
            e.printStackTrace();
            newMap.put("state","error");
        }
        return newMap;

    }

    public static void inputStreamToFile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成二维码
    @RequestMapping("createImg")
    @ResponseBody
    public String createImg(@RequestParam(value = "randomStr")String randomStr) throws IOException {
        String contentUrl="http://img.maptoface.com/login/login?randomStr="+randomStr;
        return qrCodeService.crateQRCode(contentUrl,400,400);
    }



}
