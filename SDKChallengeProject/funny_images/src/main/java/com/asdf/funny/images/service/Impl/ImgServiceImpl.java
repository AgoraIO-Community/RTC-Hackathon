package com.asdf.funny.images.service.Impl;

import com.asdf.funny.images.dao.ImgDao;
import com.asdf.funny.images.service.ImgService;
import com.asdf.funny.images.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ImgServiceImpl implements ImgService {
    @Autowired
    private ImgDao imgDao;
    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0,0);

    @Override
    public List<String> findAllMp4Mongo() {
        return imgDao.findAllMp4Mongo();
    }

    @Override
    public void insertVideo(Long id, String title, String fileName, String nowStr) {
        imgDao.insertVideo(id,title,fileName,nowStr);
    }

    @Override
    public List<Map<String, Object>> newvideoImg(int startNum, int pageSize) {
        return imgDao.newvideoImg(startNum,pageSize);
    }

    @Override
    public Integer giftotalNum() {
        return imgDao.giftotalNum();
    }

    @Override
    public List<String> compressjpeg() {
        return imgDao.compressjpeg();
    }

    @Override
    public List<String> getAllGif() {
        return imgDao.getAllGif();
    }

    @Override
    public List<String> get400jpgImgMongodbId() {
        return imgDao.get400jpgImgMongodbId();
    }

    @Override
    public List<String> get499ImgMongodbId() {
        return imgDao.get499ImgMongodbId();
    }

    @Override
    public void inserImg(String mongoId,String fileTyle,String fileName) {
        Long imgId=snowflakeIdWorker.nextId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String nowDate = formatter.format(currentDate);
        imgDao.inserImg(imgId,nowDate,mongoId,fileTyle,fileName);
    }

    @Override
    public void inserImgYasuo(String mongoId,String fileTyle,String fileName) {
        Long imgId=snowflakeIdWorker.nextId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String nowDate = formatter.format(currentDate);
        imgDao.inserImgYasuo(imgId,nowDate,mongoId,fileTyle,fileName);
    }

    @Override
    public void verifyFail(long imgId) {
        imgDao.verifyFail(imgId);
    }

    @Override
    public void verifyPass(long imgId) {
        imgDao.verifyPass(imgId);
    }

    @Override
    public Integer totalNum() {
        return imgDao.totalNum();
    }

    @Override
    public Integer totalNumVerify() {
        return imgDao.totalNumVerify();
    }



    @Override
    public List<Map<String, Object>> newImgVerify(int startNum,int pageSize) {
        return imgDao.newImgVerify(startNum,pageSize);
    }
    @Override
    public List<Map<String, Object>> newImg(int startNum,int pageSize) {
        return imgDao.newImg(startNum,pageSize);
    }
    @Override
    public List<Map<String, Object>> randImg(int startNum,int pageSize) {
        return imgDao.randImg(startNum,pageSize);
    }
    @Override
    public List<Map<String, Object>> newGifImg(int startNum, int pageSize) {
        return imgDao.newGifImg(startNum,pageSize);
    }

    @Override
    public Integer videototalNum() {
        return imgDao.videototalNum();
    }
}
