package com.asdf.funny.images.controller;


import com.asdf.funny.images.service.AttachmentsManager;
import com.asdf.funny.images.util.mongodb.JsonResultController;
import com.asdf.funny.images.util.mongodb.ResultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "admin/accessorybag/attachments")
public class AttachmentsController extends JsonResultController {
    public static String baseImgUrl = "/admin/accessorybag/attachments/images?mongoId=";
    /*	*/
    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     *//*
	@RequestMapping(value = "upLoadFile")
	@ResponseBody
	public Map<String, Object> upLoadFile(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws IOException {
		Map<String, Object> map = attachmentsManager.fileUpload(file,"");
		Map<String, Object> newMap = new HashMap<>();
		if ("true".equals(map.get("success"))){
			//添加成功
			String img = "/admin/accessorybag/attachments/images?mongoId=" + map.get("msg");
			newMap.put("img",img);
			newMap.put("mongoId",map.get("msg"));
			return newMap;
		}
		return null;
	}*/

    @Autowired
    private AttachmentsManager attachmentsManager;

//    // 处理附件上传...此方法用于测试...
//    @RequestMapping(value = "fileUpload")
//    @ResponseBody
//    public Map<String, Object> fileUpload(MultipartFile fileName)
//            throws ServletRequestBindingException, IOException {
//        return attachmentsManager.fileUploadTMongoDBAndReids(fileName, "");
//    }

    // 用于附件显示...
    @RequestMapping(value = "images")
    public void fileUpload3(@RequestParam(value="mongoId") String mongoId, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            byte[] bytes = attachmentsManager.getFileByMongoDBId(mongoId);
            outputStream = response.getOutputStream();
            // 之所以要这样,主要是为了测试...测试完了好最后确定用那种
            synchronized (this) {
                if (outputStream != null) {
                    outputStream.write(bytes);
                } else {
                    System.out.println("outputStream为空... ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("附件中暂无 " + mongoId + " 该附件信息...");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件
     * mid：mongodbID
     * dataPName 文件名
     */
    @RequestMapping(value = "downloadFile")
    @ResponseBody
    public ResultValue downloadFile(String mid, String dataPName, HttpServletResponse response) throws IOException {
        try{
            //根据产品ID获取到产品名字.
            Map<String, Object> map = attachmentsManager.downloadFile(mid);
            byte[] bytes = (byte[])map.get("data");//获取到要返回的二进制数据
            String fileName =null;
            fileName = URLEncoder.encode(dataPName,"UTF-8");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName+"."+map.get("busiId"));
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            e.printStackTrace();
            return jsonResult(500, "下载失败", "");
        }
        return jsonResult(500, "成功", "");
    }

//    /**
//     * 文件删除
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "deleteUpload")
//    @ResponseBody
//    public ResultValue deleteUpload(String id) {
//        System.out.println("删除附件：" + id);
//        try {
//            attachmentsManager.deleteMondoDB(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return jsonResult(MsgConstants.ERROR_500, MsgConstants.ERROR_500_DEI_FILE, "");
//        }
//        return jsonResult(MsgConstants.STATUS_CODE_SUCCESS, MsgConstants.INFO_200_DELFILE, "");
//    }
//
//    /**
//     * 下载文件
//     * mid：mongodbID
//     * pid：产品ID
//     */
//    @RequestMapping(value = "downloadFile")
//    @ResponseBody
//    public ResultValue downloadFile(String mid, String dataPName, HttpServletResponse response) throws IOException {
//        System.out.println("下载文件mongodbID：" + mid + ",产品名字:" + dataPName);
//        try {
//            //根据产品ID获取到产品名字.
//            Map<String, Object> map = attachmentsManager.downloadFile(mid);
//            byte[] bytes = (byte[]) map.get(StaticValue.BINARY_PROCESS_DATA);//获取到要返回的二进制数据
//            String fileName = URLEncoder.encode(dataPName + "测试报告", "UTF-8");
//            response.setContentType("application/x-msdownload");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "." + map.get(StaticValue.FILE_BUSIID));
//            response.getOutputStream().write(bytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return jsonResult(MsgConstants.ERROR_500, MsgConstants.ERROR_500_DOWNLOAD_FILE, "");
//        }
//        return jsonResult(MsgConstants.STATUS_CODE_SUCCESS, MsgConstants.INFO_200_DELFILE, "");
//    }

//    @Autowired
//    private GridFsTemplate gridFsTemplate;
//    /**
//     * 下载
//     *
//     * @param fileId   文件id
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/downloadFile")
//    public void downloadFile(@RequestParam(name = "file_id") String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Query query = Query.query(Criteria.where("_id").is(fileId));
//        // 查询单个文件
//        GridFSDBFile gfsfile = gridFsTemplate.findOne(query);
//        if (gfsfile == null) {
//            return;
//        }
//        String fileName = gfsfile.getFilename().replace(",", "");
//        //处理中文文件名乱码
//        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
//                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
//                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
//            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//        } else {
//            //非IE浏览器的处理：
//            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
//        }
//        // 通知浏览器进行文件下载
//        response.setContentType(gfsfile.getContentType());
//        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
//        gfsfile.writeTo(response.getOutputStream());
//    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
//    //@CcsiotAuthorization
    @RequestMapping(value = "upLoadFileShort")
    @ResponseBody
    public Map<String, Object> upLoadFileShort(MultipartFile file, String type, HttpServletRequest request) throws IOException {
        Map<String, Object> map = attachmentsManager.fileUploadTMongoDBAndReids(file);
        Map<String, Object> newMap = new HashMap<>();
        if ("true".equals(map.get("success"))) {
            //添加成功
            String img = baseImgUrl + map.get("msg");
            newMap.put("img", img);
            newMap.put("mongoId", map.get("msg"));
            newMap.put("fileName",map.get("fileName"));
            return newMap;
        }
        return null;
    }

}
