/**
 * Project Name:sccl-common-attachment-mongodb
 * File Name:MongoUtil.java
 * Package Name:cn.sccl.common.attachment.mongodb
 * Date:2018年1月3日上午9:21:38
 * Copyright (c) 2018, 493008064@qq.com All Rights Reserved.
 */

package com.asdf.funny.images.util.mongodb;

import com.asdf.funny.images.config.MongodbConfiguration;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ClassName:MongoUtil <br/>
 * Date: 2018年1月3日 上午9:21:38 <br/>
 *
 */
public class MongoUtil {

    private static MongoClient mongoClient;
/*    private static String BINARY_PROCESS_DATA = "data";// 存入Mongodb二进制流数据
    private static String FILE_BUSIID = "busiId";//文件后缀
    private static String FILE_DATABASE = "database";//存入mongodb的库
    private static String FILE_CREATE_TIME = "create_time";//文件上传时间
    private static String FILE_BY = "by";//文件类型
    private static String FILE_TITLE = "title";//原始文件名字
    private static Long FILE_SIZE = 16777216L;// 16M文件大小*/


    static {
        MongodbConfiguration getBean = (MongodbConfiguration) SpringBeanNewUtil.getBean("mongodbConfiguration");
        mongoClient = getBean.MongoClientConnectionFactory();
    }

    // ------------------------------------共用方法---------------------------------------------------

    /**
     * 获取DB实例 - 指定DB
     *
     * @param dbName
     * @return
     */
    public static MongoDatabase getDB(String dbName) {

        if (dbName != null && !"".equals(dbName)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            return database;
        }
        return null;
    }

    /**
     * 获取collection对象 - 指定Collection
     *
     * @param collName
     * @return
     */
    public static MongoCollection<Document> getCollection(String dbName, String collName) {
        if (null == collName || "".equals(collName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = database.getCollection(collName);
        return collection;
    }

    /**
     * 查询DB下的所有表名
     */
    public static List<String> getAllCollections(String dbName) {
        MongoIterable<String> colls = getDB(dbName).listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : colls) {
            _list.add(s);
        }
        return _list;
    }

    public static List<String> getRows(String database, String coll) {

        List<Document> rows = findAll(database, coll);

        List<String> _list = new ArrayList<String>();
        for (Document s : rows) {
            _list.add(s.toString());
        }
        return _list;
    }

    /**
     * 获取所有数据库名称列表
     *
     * @return
     */
    public static MongoIterable<String> getAllDBNames() {
        MongoIterable<String> s = mongoClient.listDatabaseNames();
        return s;
    }

    /**
     * 查找对象 - 根据主键_id
     *
     *
     * @param id
     * @return
     */
    public static Document findById(MongoCollection<Document> coll, String id) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Document myDoc = coll.find(Filters.eq("_id", _idobj)).first();
        return myDoc;
    }

    /**
     * 查找对象 - 根据主键_id
     *
     * @param id
     * @return
     */
    public static byte[] downloadBinary(String database, String busiAlias, String id) {
        MongoCollection<Document> coll = MongoUtil.getCollection(database, busiAlias);
        Document doc = findById(coll, id);
        Object object = doc.get(StaticValue.BINARY_PROCESS_DATA);
        if (object != null) {
            Binary binary = (Binary) object;
            return binary.getData();
            // 拿到字节数组
            // byte[] filebyte = binary.getData();
            // byte2File(filebyte, outfilePath, outfileName);
            // System.out.println(aaa);
        }
        return null;

    }

    /**
     * 下载文件文件
     *
     * @return
     */
    public static byte[] download(String databaseName, String busiAlias, String mongodbFileId, Long fileSize)
            throws IOException {

        if (fileSize < StaticValue.FILE_SIZE) {
            // 二进制方式下载
            return downloadBinary(databaseName, busiAlias, mongodbFileId);
        } else {
            // GridFs方式下载
            return downloadGridFSBucketFile(databaseName, busiAlias, mongodbFileId);
        }
    }

    /**
     * downloadGridFSBucketFile:(GridFs方式下载). <br/>
     *
     * @author yangl
     * @param database
     * @param collection
     * @param objectId
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static byte[] downloadGridFSBucketFile(String database, String collection, String objectId)
            throws IOException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
//		Date now = new Date();
//		String datae = dateFormat.format(now);
//		System.out.println(datae + "，mongodb下载文件开始！::::::");
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDatabase, collection);
        InputStream inputStream = gridFSBucket.openDownloadStream(new ObjectId(objectId));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
//		Date now1 = new Date();
//		 datae = dateFormat.format(now1);
//		System.out.println(datae + "，mongodb下载文件开始2！::::::");
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
        }
//		Date now2 = new Date();
//		 datae = dateFormat.format(now2);
//		System.out.println(datae + "，mongodb下载文件开始3！::::::");
        return output.toByteArray();
        // return inputStream;
    }

    /**
     * 查找对象 - 根据主键_id
     *
     * @param id
     * @return
     */
//    public static byte[] findByIdtoString(String database, String busiAlias, String id) {
    public static Map<String,Object> findByIdtoString(String database, String busiAlias, String id) {
        Map<String,Object>  map = new HashMap<String,Object>();//构造返回值...
        MongoCollection<Document> coll = MongoUtil.getCollection(database, busiAlias);
        Document doc = findById(coll, id);
        if(doc!=null){
            map.put(StaticValue.FILE_BUSIID, doc.get(StaticValue.FILE_BUSIID));
            map.put(StaticValue.FILE_DATABASE, doc.get(StaticValue.FILE_DATABASE));
            map.put(StaticValue.FILE_CREATE_TIME, doc.get(StaticValue.FILE_CREATE_TIME));
            map.put(StaticValue.FILE_BY, doc.get(StaticValue.FILE_BY));
            map.put(StaticValue.FILE_TITLE, doc.get(StaticValue.FILE_TITLE));
            Binary binary = (Binary)doc.get(StaticValue.BINARY_PROCESS_DATA);
            if (binary != null) {
                // 拿到字节数组
                byte[] filebyte = binary.getData();
                map.put(StaticValue.BINARY_PROCESS_DATA,filebyte);
                return map;
            }
        }
        return map;
    }

    /**
     * 通过ID删除 （二进制入GridFs两种方式）
     *
     * @param coll
     * @param id
     * @param fileSize
     * @param busiAlias
     * @return
     */
    public static int deleteById(MongoCollection<Document> coll, String id, Long fileSize, String databaseName,
                                 String busiAlias) {
        int count = 0;
        ObjectId _id = null;
        try {
            _id = new ObjectId(id);
        } catch (Exception e) {
            return 0;
        }
        if (fileSize < StaticValue.FILE_SIZE) {
            Bson filter = Filters.eq("_id", _id);
            DeleteResult deleteResult = coll.deleteOne(filter);
            count = (int) deleteResult.getDeletedCount();
            System.out.println("delete mogodb by id (" + id + "): conut:" + count);

        } else {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
            GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDatabase, busiAlias);
            gridFSBucket.delete(_id);
            System.out.println("delete gridFSBucket mogodb by id (" + id + ")");

        }
        return count;
    }

    /**
     * saveMongodbEsheetFile:电子表单保存或更新. <br/>
     *
     * @author yangl
     * @param data
     * @param filename
     * @param database
     * @param busiAlias
     * @param busiId
     * @param mongodbFileId
     * @return
     * @since JDK 1.8
     */
    public static String saveMongodbEsheetFile(String data, String filename, String database, String busiAlias,
                                               String busiId, String mongodbFileId,String fileBy) {

        ObjectId _idobj = saveFinary(data, filename, database, busiAlias, busiId, mongodbFileId,fileBy);
        return _idobj.toString();
    }

    private static ObjectId saveFinary(String file, String fileName, String database, String busiAlias, String busiId,
                                       String mongodbFileId, String fileBy) {

        MongoCollection<Document> coll = MongoUtil.getCollection(database, busiAlias);
        if (StringUtils.isNotBlank(mongodbFileId)) {
            // 删除附件
            deleteById(coll, mongodbFileId, Long.valueOf(file.length()), database, busiAlias);
            System.out.println("文档删除成功" + mongodbFileId);
        }
        ObjectId _idobj = null;
        // 得到Mongodb系统自动生成的ObjectId
        _idobj = ObjectId.get();
        HashMap<String, Object> map = setDocumentMap(fileName, _idobj, database, busiAlias, busiId, busiAlias,fileBy);
        map.put(StaticValue.BINARY_PROCESS_DATA, file);
        Document document = new Document(map);
        coll.insertOne(document);
//        System.out.println("文档插入成功");
        return _idobj;
    }

    /**
     * saveMongodbFile:(这里用一句话描述这个方法的作用). <br/>
     * 数据据 ：年 表 col按模块划分
     *
     * @author yangl
     * @param file
     * @param fileSize
     * @param fileName
     * @param database
     * @return
     * @throws Exception
     * @since JDK 1.6
     */
    public static String saveMongodbFile(MultipartFile file, long fileSize, String fileName, String database,
                                         String collection, String busiId, String fileBy) throws Exception {
        byte[] byteArray = file.getBytes();

        ObjectId _idobj = null;
        if (fileSize < StaticValue.FILE_SIZE) {
            // 小于16M的直接存二进制字节
            _idobj = saveFinary(byteArray, fileName, database, collection, busiId,fileBy);
        } else {
            // 采用FS客户端
            InputStream input = new ByteArrayInputStream(byteArray);
            _idobj = saveGridFsBucket(input, fileName, database, collection, busiId,fileBy);
        }
        return _idobj.toString();
    }

    public static String saveMongodbFileByte(byte[] file, long fileSize, String fileName, String database,
                                         String collection, String busiId, String fileBy) throws Exception {
        byte[] byteArray = file;

        ObjectId _idobj = null;
        if (fileSize < StaticValue.FILE_SIZE) {
            // 小于16M的直接存二进制字节
            _idobj = saveFinary(byteArray, fileName, database, collection, busiId,fileBy);
        } else {
            // 采用FS客户端
            InputStream input = new ByteArrayInputStream(byteArray);
            _idobj = saveGridFsBucket(input, fileName, database, collection, busiId,fileBy);
        }
        return _idobj.toString();
    }




    /**
     * saveGridFsBucket:(通过gridFSBucket来保存大于16M的文件). <br/>
     * @author yangl
     * @param fileName
     * @param database
     *            数据库名
     * @param busiId
     * @return ObjectId Mongodb主键Id
     * @throws IOException
     * @since JDK 1.8
     */
    public static ObjectId saveGridFsBucket(InputStream in, String fileName, String database, String collection,
                                            String busiId, String fileBy) throws IOException {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDatabase, collection);
        HashMap<String, Object> map = setDocumentMap(fileName, null, database, collection, busiId, collection,fileBy);

        // chunk缓存大小设置1M 1024*1204=1231896
        GridFSUploadOptions gridFSUploadOptions = new GridFSUploadOptions().chunkSizeBytes(1231896)
                .metadata(new Document(map));
        ObjectId _idobj = null;
        try {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
            String datae = dateFormat.format(now);
            System.out.println(datae + "，mongodb正试保存文件开始！::::::");
            _idobj = gridFSBucket.uploadFromStream(fileName, in, gridFSUploadOptions);

        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
        String datae = dateFormat.format(now);
        System.out.println(datae + "，mongodb文件保存成功！::::::");
        return _idobj;
    }

    /**
     * saveFinary:(通过二进制流的方式保存附件). <br/>
     *
     * @author yangl
     * @param file
     * @param fileName
     * @param database
     * @throws Exception
     * @since JDK 1.6
     */
    private static ObjectId saveFinary(byte[] file, String fileName, String database, String busiAlias, String busiId, String fileBy)
            throws Exception {
        MongoCollection<Document> coll = MongoUtil.getCollection(database, busiAlias);
        ObjectId _idobj = null;
        // 得到Mongodb系统自动生成的ObjectId
        _idobj = ObjectId.get();
        HashMap<String, Object> map = setDocumentMap(fileName, _idobj, database, busiAlias, busiId, busiAlias,fileBy);
        map.put(StaticValue.BINARY_PROCESS_DATA, file);
        Document document = new Document(map);
        coll.insertOne(document);
//        System.out.println("文档插入成功");
        return _idobj;
    }

    private static HashMap<String, Object> setDocumentMap(String fileName, ObjectId _idobj, String database,
                                                          String busiAlias, String busiId, String coll, String fileBy ) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
        String datae = dateFormat.format(now);
        map.put("create_time", datae);
        map.put("title", fileName);
        if (_idobj != null) {
            map.put("_id", _idobj);
        }
        map.put("by", fileBy);
        map.put("database", database);
        map.put("busiAlias", busiAlias);
        map.put("busiId", busiId);
        return map;
    }

    // 从mongodb删除附件
    public static void deletMongodbBy(String databaseName, String collection, String mongodbFileId, Long fileSize) {
        MongoCollection<Document> coll = MongoUtil.getCollection(databaseName, collection);
        deleteById(coll, mongodbFileId, fileSize, databaseName, collection);
    }

    public static String saveMongodbFile(File file, long fileSize, String fileName, String database, String collection,
                                         String busiId,String fileBy) throws Exception {

        byte[] byteArray = File2byte(file);

        ObjectId _idobj = null;
        if (fileSize < StaticValue.FILE_SIZE) {
            // 小于16M的直接存二进制字节
            _idobj = saveFinary(byteArray, fileName, database, collection, busiId,fileBy);
        } else {
            // 采用FS客户端
            InputStream input = new ByteArrayInputStream(byteArray);
            _idobj = saveGridFsBucket(input, fileName, database, collection, busiId,fileBy);
        }
        return _idobj.toString();
    }

    // File和byte[]转换
    public static byte[] File2byte(File file) {
        byte[] buffer = null;
        try {
            // File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * databaseNames:查询数据，返回LIST对象数据名称
     *
     * @author yangl
     * @return
     * @since JDK 1.8
     */
    public static List<String> databaseNames() {
        MongoIterable<String> databaseNames = getAllDBNames();
        List<String> _list = new ArrayList<String>();
        for (String s : databaseNames) {
            System.out.println(s);
            _list.add(s);
        }
        return _list;

    }

    /**
     * 查找集合内所有Document
     *
     * @param collName
     * @return
     */
    public static List<Document> findAll(String dbName, String collName) {
        MongoCollection<Document> coll = getDB(dbName).getCollection(collName);
        List<Document> result = new ArrayList<Document>();
        // FindIterable<Document> findIterable = coll.find();
        MongoCursor<Document> mongoCursor = coll.find().skip(0).limit(20).iterator();
        // MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            result.add(mongoCursor.next());
        }
        return result;
    }

    /**
     * 删除一个数据库
     */
    public void dropDB(String dbName) {
        getDB(dbName).drop();
    }

    /** 统计数 */
    public int getCount(MongoCollection<Document> coll) {
        int count = (int) coll.count();
        return count;
    }

    /** 条件查询 */
    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).iterator();
    }

    /** 分页查询 */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }

    /**
     *
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Bson filter = Filters.eq("_id", _idobj);
        // coll.replaceOne(filter, newdoc); // 完全替代
        coll.updateOne(filter, new Document("$set", newdoc));
        return newdoc;
    }

    public void dropCollection(String dbName, String collName) {
        getDB(dbName).getCollection(collName).drop();
    }

    /**
     * 关闭Mongodb
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

}
