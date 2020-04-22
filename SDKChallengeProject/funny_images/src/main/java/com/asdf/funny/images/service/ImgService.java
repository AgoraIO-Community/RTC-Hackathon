package com.asdf.funny.images.service;


import com.asdf.funny.images.dao.UserMapper;
import com.asdf.funny.images.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
public interface ImgService {
    void inserImg(String mongoId,String fileTyle,String fileName);
    void inserImgYasuo(String mongoId,String fileTyle,String fileName);


    List<Map<String,Object>> newImgVerify(int startNum,int pageSize);
    List<Map<String,Object>> newImg(int startNum,int pageSize);
    List<Map<String,Object>> randImg(int startNum,int pageSize);

    List<Map<String,Object>> newGifImg(int startNum,int pageSize);
    List<Map<String,Object>> newvideoImg(int startNum,int pageSize);

    Integer totalNumVerify();
    Integer totalNum();
    Integer giftotalNum();
    Integer videototalNum();

    void verifyPass(long imgId);
    void verifyFail(long imgId);


    List<String> get499ImgMongodbId();
    List<String> get400jpgImgMongodbId();
    List<String> getAllGif();
    List<String> compressjpeg();

    List<String> findAllMp4Mongo();

    void insertVideo(Long id,String title,String fileName,String nowStr);

}
