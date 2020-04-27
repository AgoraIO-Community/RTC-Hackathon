package com.asdf.funny.images.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AttachmentsManager {
    Map<String,Object> fileUploadTMongoDBAndReids(MultipartFile fileName);
    Map<String,Object> fileUploadTMongoDBAndReidsByte(byte[] fileName,long fileSize,String fileType,String OriginalFilename);

    /**
     * 保存到mongoDB和mysql
     * 成功后返回mongodbFileId
     */
//    String fileUploadToMongoDBAndMysql(MultipartFile fileName) throws Exception;
//    Map<String,Object> fileUpload(MultipartFile fileName);

    /**
     * 需要保存字段如下：
     *      业务模块：busiId
     *    附件分类Id：categoryId
     *  附件分类编码：busiAlias
     *
     *       分类Id：categoryName
     *分类编码(模块)：categoryCode
     *     创建人Id：categoryCode --必填
     */
//    void saveAttaInfo(Attachments attachments);
    byte[] getFileByMongoDBId(String id)throws Exception;
    Map<String, Object> downloadFile(String mongoId)throws Exception;
//    Map<String, Object> downloadFile(String mongoId)throws Exception;
    void deleteMondoDB(Object mongoId)throws Exception;
//    void saveAttachments(Object obj);
//    void deleteImgByImgUrl(String imgUrl) throws Exception;
}
