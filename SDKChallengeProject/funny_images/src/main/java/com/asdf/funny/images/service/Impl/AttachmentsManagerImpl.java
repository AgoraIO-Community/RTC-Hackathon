package com.asdf.funny.images.service.Impl;

import com.asdf.funny.images.dao.AttachmentsDAO;
import com.asdf.funny.images.model.Attachments;
import com.asdf.funny.images.service.AttachmentsManager;
import com.asdf.funny.images.util.RedisUtil;
import com.asdf.funny.images.util.mongodb.AttachmentsMongoDB;
import com.asdf.funny.images.util.mongodb.StaticValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentsManagerImpl implements AttachmentsManager {

	@Autowired
	private AttachmentsMongoDB attachmentsMongoDB;
	@Autowired
	private AttachmentsDAO attachmentsDAO;

	@Autowired
	private RedisUtil redisUtil;


//
//	@Value("${shiro.timeOut}")
//	private Integer timeOut;

	@Override
	public Map<String, Object> fileUploadTMongoDBAndReidsByte(byte[] file,long fileSize,String fileType,String OriginalFilename) {
		Date date = new Date();
		// 调用保存mongodb方法.
		Map<String,String>  map = attachmentsMongoDB.uploadAttachmentsByte(file, fileSize, fileType, OriginalFilename);
		Map<String,Object>  resultMap = new HashMap<String,Object>();//返回Map
		String mongodbFileId = map.get("mongodbFileId");
		if (mongodbFileId!=null && !"".equals(mongodbFileId)){
			//如果存在，说明保存成功》》》保存基础信息
			//获得该数据的id
//			Attachments attachments = getAttachments(map, file,request);
			try{
				System.out.println("mongodbFileId:"+mongodbFileId);
//				// 以此刻自动生成的主键作为redis的key,保存在数据库中.
//				if("shortTime".equals(type)){
//					//直接插入数据库
//					redisUtil.set(mongodbFileId,attachments,timeOut);
//				}else{
//					attachmentsDAO.insert(attachments);
//				}
				//插入成功返回id
				resultMap.put("success","true");
				resultMap.put("msg",mongodbFileId);
			}catch (Exception e){
				resultMap.put("success","false");
				resultMap.put("msg","插入失败："+e.getMessage());
				e.printStackTrace();
			}
		}else{
			resultMap.put("success","false");
			resultMap.put("msg","mongodb异常");
		}
		return resultMap;
	}

	/**
	 * 保存到mongoDB并放入redis
	 * @param file
	 * @return
	 */
	@Override
	public Map<String, Object> fileUploadTMongoDBAndReids(MultipartFile file) {
		Date date = new Date();
		// 调用保存mongodb方法.
		Map<String,String>  map = attachmentsMongoDB.uploadAttachments(file);
		Map<String,Object>  resultMap = new HashMap<String,Object>();//返回Map
		String mongodbFileId = map.get("mongodbFileId");
		if (mongodbFileId!=null && !"".equals(mongodbFileId)){
			//如果存在，说明保存成功》》》保存基础信息
			//获得该数据的id
//			Attachments attachments = getAttachments(map, file,request);
			try{
				System.out.println("mongodbFileId:"+mongodbFileId);
//				// 以此刻自动生成的主键作为redis的key,保存在数据库中.
//				if("shortTime".equals(type)){
//					//直接插入数据库
//					redisUtil.set(mongodbFileId,attachments,timeOut);
//				}else{
//					attachmentsDAO.insert(attachments);
//				}
				//插入成功返回id
				resultMap.put("success","true");
				resultMap.put("msg",mongodbFileId);
			}catch (Exception e){
				resultMap.put("success","false");
				resultMap.put("msg","插入失败："+e.getMessage());
				e.printStackTrace();
			}
		}else{
			resultMap.put("success","false");
			resultMap.put("msg","mongodb异常");
		}
		return resultMap;
	}

//	/**
//	 * 保存到mongoDB和mysql
//	 * 成功后返回mongodbFileId
//	 * @param file
//	 * @return
//	 */
//	@Override
//	public String fileUploadToMongoDBAndMysql(MultipartFile file) throws Exception {
//		// 保存到mongodb
//		Map<String,String>  map = attachmentsMongoDB.uploadAttachments(file);
//		Map<String,Object>  resultMap = new HashMap<String,Object>();//返回Map
//		String mongodbFileId = map.get("mongodbFileId");
//		if (StringUtils.isNotBlank(mongodbFileId)){
//			//如果存在，说明保存成功》》》保存基础信息
//			//获得该数据的id
//			Attachments attachments = getAttachments(map, file);
//			//保存到mysql
//			attachmentsDAO.insert(attachments);
//			//插入成功返回id
//			resultMap.put("status",200);
//		}else{
//			throw new Exception("图片上传失败:mongodb异常");
//		}
//		return mongodbFileId;
//	}
//	private Attachments getAttachments(Map<String,String> map,MultipartFile file,HttpServletRequest request){
//		Attachments attachments = new Attachments();//创建Att对象
//		UserInfo userInfo = deviceInfoManagerImpl.getCurrentUser(request);
//		Date date = new Date();
//		long id = getId();//创建id
//
//		attachments.setId(id);
//		attachments.setMongodbFileId(map.get("mongodbFileId"));//保存在mongoDB数据库到的主键ID
//		attachments.setCollection(map.get("collection"));//保存在mongodb的集合名
//		attachments.setFileName(file.getOriginalFilename());//保存文件名字
//		attachments.setCreateTime(date);
//		attachments.setFileSize(file.getSize());
//		attachments.setDeleteFlag("1");//。是否删除
//		attachments.setDatabaseName(map.get("database"));//保存在mongodb的数据库
//		attachments.setFileType(file.getContentType());//文件类型
//		attachments.setUpdateTime(date);
//		attachments.setSaveType("mongodb");//保存类型
//		attachments.setYear(Integer.parseInt(DateFormatUtil.getYeah(date)));
////		attachments.setCategoryId(devUser.getId());//
////		attachments.setCategoryName(devUser.getDeveloperName());//
//		attachments.setCreatorId(userInfo.getId());//设置创建者ID
//		attachments.setCreatorName(userInfo.getAccount());//设置创建者名字
//		return attachments;
//	}

//
//	@Override
//	public void saveAttaInfo(Attachments attachments) {
//		attachmentsDAO.update(attachments);
//	}
//
	@Override
	public byte[] getFileByMongoDBId(String id)throws Exception {
		Map<String, Object> map = attachmentsMongoDB.attachmentsReading(id);
		return (byte[])map.get(StaticValue.BINARY_PROCESS_DATA);
	}

	@Override
	public Map<String, Object> downloadFile(String mongoId) throws Exception {
		Map<String, Object> map = attachmentsMongoDB.attachmentsReading(mongoId);
		return map;
	}

//	@Override
//	public Map<String, Object> downloadFile(String mongoId)throws Exception {
//		Map<String, Object> map = attachmentsMongoDB.attachmentsReading(mongoId);
//		return map;
//	}
    @Override
    public void deleteMondoDB(Object obj)throws Exception {
		//删除mongodb的
		int i = 0;
		if(obj instanceof List){
			List<String> mongoListId = (List<String>) obj;
			if (mongoListId.size()>0) {
				mongoListId.forEach(k->{
					attachmentsMongoDB.deleteFile(k);
				});
				//删除mysql中
//				i = attachmentsDAO.deleteByMongoListId(mongoListId);
			}
		}else{
			String mongoId = (String) obj;
			attachmentsMongoDB.deleteFile(mongoId);
			//删除mysql中
//			i = attachmentsDAO.deleteByMongoId(mongoId);
		}

		System.out.println("delete mysql_attachments by mongoId (" + obj + "): conut:" + i);
	}

//	/**
//	 * 用于附件的第二次保存，持久性保存
//	 * @param obj
//	 */
//	@Override
//	public void saveAttachments(Object obj){
//		if (obj instanceof List){
//			System.out.println("删除list");
//			List<String> list = (List) obj;
//			if(list!=null&&list.size()>0){
//				list.forEach(k->{
//					insertInfo(k);
//				});
//			}
//		}else{
//			System.out.println("删除字符串");
//			String str = (String) obj;
//			insertInfo(str);
//		}
//	}

//	private void insertInfo(String mongoId){
//		Attachments attachments = (Attachments)redisUtil.get(mongoId);
//		if(attachments!=null){
//			attachmentsDAO.insert(attachments);
//			redisUtil.del(mongoId);
//		}
//	}
	/**
	 * 根据图片url删除图片
	 * 包括删除了mongoDB和mysql
	 * @param imgUrl
	 * @throws Exception
	 */
//	@Override
//	public void deleteImgByImgUrl(String imgUrl) throws Exception {
//		String mongoId = StringUtils.getMongoId(imgUrl);// 获取原图片的mongoId
//		if(StringUtils.isNotBlank(mongoId)){
//			deleteMondoDB(mongoId);
//		}
//	}
}
