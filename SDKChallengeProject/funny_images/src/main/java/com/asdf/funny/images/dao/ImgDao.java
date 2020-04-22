package com.asdf.funny.images.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ImgDao {
    void inserImg(@Param("imgId") Long imgId,@Param("nowDate") String nowDate, @Param("mongoId") String mongoId,@Param("fileTyle")String fileTyle,@Param("fileName")String fileName);
    void inserImgYasuo(@Param("imgId") Long imgId,@Param("nowDate") String nowDate, @Param("mongoId") String mongoId,@Param("fileTyle")String fileTyle,@Param("fileName")String fileName);

    List<Map<String,Object>> newImg(@Param("startNum") int startNum,@Param("pageSize") int pageSize);
    List<Map<String,Object>> randImg(@Param("startNum") int startNum,@Param("pageSize") int pageSize);

    List<Map<String,Object>> newImgVerify(@Param("startNum") int startNum,@Param("pageSize") int pageSize);

    List<Map<String,Object>> newGifImg(@Param("startNum") int startNum,@Param("pageSize") int pageSize);
    List<Map<String,Object>> newvideoImg(@Param("startNum") int startNum,@Param("pageSize") int pageSize);

    Integer totalNum();
    Integer totalNumVerify();
    List<String> get499ImgMongodbId();
    List<String> get400jpgImgMongodbId();
    List<String> getAllGif();
    List<String> compressjpeg();
    void verifyFail(@Param("imgId") long imgId);
    void verifyPass(@Param("imgId") long imgId);
    Integer giftotalNum();
    Integer videototalNum();

    List<String> findAllMp4Mongo();

    void insertVideo(@Param("id") Long id,@Param("title") String title,
                     @Param("fileName") String fileName,@Param("nowStr") String nowStr);

}
