
package com.asdf.funny.images.util.mongodb;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ljf on 2018/7/2.
 */
@Component
public class AttachmentsMongoDB {

    @Value("${mongodb.database}")
    private String database;
//    private static String database = "attachmentsDB";
    private String collection = "attachments";

    /**
     * 文件保存
     * @param file
     * @return
     */
    public Map<String,String> uploadAttachmentsByte(byte[] file,long fileSize,String fileType,String OriginalFilename){
        String objctID="";
        System.out.println("保存的数据库为database:"+database+"   文件大小为file.getSize()："+fileSize+" byte");
        Map<String,String> map=new HashMap<>();
        //获取文件类型
        String contentType = fileType;
        //获取文件名
        String originalFilename =OriginalFilename;
        //获取文件后缀
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            objctID= MongoUtil.saveMongodbFileByte(file, fileSize, originalFilename, database, collection, fileSuffix,contentType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        map.put("database",database);
        map.put("collection",collection);
        map.put("mongodbFileId",objctID);
        return map;
    }
    /**
     * 文件保存
     * @param file
     * @return
     */
    public Map<String,String> uploadAttachments(MultipartFile file){
        String objctID="";
        System.out.println("保存的数据库为database:"+database+"   文件大小为file.getSize()："+file.getSize()+" byte");
        Map<String,String> map=new HashMap<>();
        //获取文件类型
        String contentType = file.getContentType();
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            objctID= MongoUtil.saveMongodbFile(file, file.getSize(), originalFilename, database, collection, fileSuffix,contentType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        map.put("database",database);
        map.put("collection",collection);
        map.put("mongodbFileId",objctID);
        return map;
    }
    public void deleteFile(String mongoId){
        MongoUtil.deletMongodbBy(database,collection,mongoId,16777215L);
    }
    /**
     * 通过id读取附件
     * @param id
     * @return
     */
    public Map<String, Object> attachmentsReading(String id)throws Exception{
        Map<String, Object> returnMap = MongoUtil.findByIdtoString(database, collection, id);
        return returnMap;
    }
}

