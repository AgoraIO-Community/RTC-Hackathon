//package com.asdf.funny.images;
//
//import com.asdf.funny.images.service.AttachmentsManager;
//import com.asdf.funny.images.service.ImgService;
//import com.asdf.funny.images.util.GifUtils;
//import com.tinify.Source;
//import com.tinify.Tinify;
//import org.apache.commons.io.IOUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ImagesApplicationTests {
//
//    @Autowired
//    private AttachmentsManager attachmentsManager;
//    @Autowired
//    private ImgService imgService;
//    public static String baseImgUrl = "/admin/accessorybag/attachments/images?mongoId=";
//
////    @Test
////    public void contextLoads()throws Exception {
////        //所有图片
////        File file = new File("G:\\手机视频\\魅族手机");
////        File [] files = file.listFiles();
////        for (int i = 0; i < files.length; i++)
////        {
////            File file1 = files[i];
////            FileInputStream input = new FileInputStream(file1);
////            String fileName=file1.getName();
////            String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
////            MultipartFile multipartFile =new MockMultipartFile("file", file1.getName(), "text/plain", IOUtils.toByteArray(input));
////            Map<String, Object> map = attachmentsManager.fileUploadTMongoDBAndReids(multipartFile);
////            Map<String, Object> newMap = new HashMap<>();
////            if ("true".equals(map.get("success"))) {
////                //添加成功
////                //存到数据库
////                imgService.inserImg((String)map.get("msg"),fileTyle);
////                String img = baseImgUrl + map.get("msg");
////                newMap.put("img", img);
////                newMap.put("mongoId", map.get("msg"));
////                newMap.put("fileName", map.get("fileName"));
////            }
////        }
////
////
////    }
//
//    @Test
//    //上传gif
//    public void uploadcompressimg()throws Exception {
//        //所有图片
//        File file = new File("F:\\已上传到有趣图片网站上的图片视频\\MP4");
//        File [] files = file.listFiles();
//        for (int i = 0; i < files.length; i++)
//        {
//            File file1 = files[i];
//            FileInputStream input = new FileInputStream(file1);
//            String fileName=file1.getName();
//            String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
//            String fileRealName=fileName.substring(0,fileName.lastIndexOf("."));
//            MultipartFile multipartFile =new MockMultipartFile("file", file1.getName(), "text/plain", IOUtils.toByteArray(input));
//            Map<String, Object> map = attachmentsManager.fileUploadTMongoDBAndReids(multipartFile);
//            Map<String, Object> newMap = new HashMap<>();
//            if ("true".equals(map.get("success"))) {
//                //添加成功
//                //存到数据库
//                imgService.inserImg((String)map.get("msg"),fileTyle,fileRealName);
//                String img = baseImgUrl + map.get("msg");
//                newMap.put("img", img);
//                newMap.put("mongoId", map.get("msg"));
//                newMap.put("fileName", map.get("fileName"));
//            }
//        }
//
//
//    }
//
//    @Test
//    //上传png
//    public void uploadcompressimgpng()throws Exception {
//        //所有图片
//        File file = new File("F:\\已上传到有趣图片网站上的图片视频\\已压缩的图片\\jpg");
//        File [] files = file.listFiles();
//        for (int i = 0; i < files.length; i++)
//        {
//            File file1 = files[i];
//            FileInputStream input = new FileInputStream(file1);
//            String fileName=file1.getName();
//            String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
//            String fileRealName=fileName.substring(0,fileName.lastIndexOf("."));
//            MultipartFile multipartFile =new MockMultipartFile("file", file1.getName(), "text/plain", IOUtils.toByteArray(input));
//            Map<String, Object> map = attachmentsManager.fileUploadTMongoDBAndReids(multipartFile);
//            Map<String, Object> newMap = new HashMap<>();
//            if ("true".equals(map.get("success"))) {
//                //添加成功
//                //存到数据库
//                imgService.inserImg((String)map.get("msg"),fileTyle,fileRealName);
//                String img = baseImgUrl + map.get("msg");
//                newMap.put("img", img);
//                newMap.put("mongoId", map.get("msg"));
//                newMap.put("fileName", map.get("fileName"));
//            }
//        }
//
//
//    }
//    @Test
//    public void s()throws Exception {
//        String a=codeString("G:\\md5数据\\crackstation.txt.gz");
//        String pathname = "G:\\md5数据\\crackstation.txt.gz"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
//        String fileContent = "";
//        File f = new File(pathname);
//        if (f.isFile() && f.exists()) {
//            InputStreamReader read = new InputStreamReader(new FileInputStream(f), "GBK");
//            BufferedReader reader = new BufferedReader(read);
//            String line;
//            while ((line = reader.readLine()) != null) {
//                fileContent += line;
//            }
//            read.close();
//        }
//
//    }
//    /**
//     * 获得文件编码
//     * @param fileName
//     * @return
//     * @throws Exception
//     */
//    public static String codeString(String fileName) throws Exception {
//        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
//        int p = (bin.read() << 8) + bin.read();
//        bin.close();
//        String code = null;
//
//        switch (p) {
//            case 0xefbb:
//                code = "UTF-8";
//                break;
//            case 0xfffe:
//                code = "Unicode";
//                break;
//            case 0xfeff:
//                code = "UTF-16BE";
//                break;
//            default:
//                code = "GBK";
//        }
//
//        return code;
//    }
//
//
//    @Test
//    public void compressImg() {
//        try {
//            Tinify.setKey("a80NwM2NcFgZsnh8t8wtMcYAxFTSeoNj");
////            //获取前19个图片的mongodbId(png)
////            List<String> list=imgService.get499ImgMongodbId();
////            int i=1;
////            for(String mongodbid:list){
////                byte[] bytes = attachmentsManager.getFileByMongoDBId(mongodbid);
////                Source source =Tinify.fromBuffer(bytes);
////                source.toFile("F:\\已上传到有趣图片网站上的图片视频\\已压缩的图片\\png\\"+mongodbid+".png");
////                System.out.println("成功："+i+"张");
////                i++;
////            }
//
//            //获取前400个图片的mongodbId(jpg)
//            List<String> list=imgService.get400jpgImgMongodbId();
//            int i=1;
//            for(String mongodbid:list){
//                System.out.print("mongodbId:"+mongodbid+",");
//                byte[] bytes = attachmentsManager.getFileByMongoDBId(mongodbid);
//                Source source =Tinify.fromBuffer(bytes);
//                source.toFile("F:\\已上传到有趣图片网站上的图片视频\\已压缩的图片\\jpg\\"+mongodbid+".jpg");
//                System.out.println("它成功，第"+i+"张");
//                i++;
//            }
//
//
//
//
////            Tinify.setProxy("http://user:pass@192.168.0.1:8080");
//            //直接提供图片
////            Source source = Tinify.fromFile("C:\\Users\\Administrator\\Desktop\\u=2556197056,2105630514&fm=27&gp=0.jpg");
////            source.toFile("C:\\Users\\Administrator\\Desktop\\optimized.jpg");
////            //返回byte[]
////            byte[] sourceData = Files.readAllBytes(Paths.get("unoptimized.jpg"));
////            byte[] resultData = Tinify.fromBuffer(sourceData).toBuffer();
////            //传图片url路径
////            Source sourceUrl = Tinify.fromUrl("https://tinypng.com/images/panda-happy.png");
////            sourceUrl.toFile("optimized.jpg");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    //压缩jpeg
//    @Test
//    public void compressjpeg() {
//        try {
//            Tinify.setKey("a80NwM2NcFgZsnh8t8wtMcYAxFTSeoNj");
////            //获取前19个图片的mongodbId(png)
////            List<String> list=imgService.get499ImgMongodbId();
////            int i=1;
////            for(String mongodbid:list){
////                byte[] bytes = attachmentsManager.getFileByMongoDBId(mongodbid);
////                Source source =Tinify.fromBuffer(bytes);
////                source.toFile("F:\\已上传到有趣图片网站上的图片视频\\已压缩的图片\\png\\"+mongodbid+".png");
////                System.out.println("成功："+i+"张");
////                i++;
////            }
//
//            //获取前400个图片的mongodbId(jpg)
//            List<String> list=imgService.compressjpeg();
//            int i=1;
//            for(String mongodbid:list){
//                System.out.print("mongodbId:"+mongodbid+",");
//                byte[] bytes = attachmentsManager.getFileByMongoDBId(mongodbid);
//                Source source =Tinify.fromBuffer(bytes);
//                source.toFile("F:\\已上传到有趣图片网站上的图片视频\\已压缩的图片\\jpeg\\"+mongodbid+".jpeg");
//                System.out.println("它成功，第"+i+"张");
//                i++;
//            }
//
//
//
//
////            Tinify.setProxy("http://user:pass@192.168.0.1:8080");
//            //直接提供图片
////            Source source = Tinify.fromFile("C:\\Users\\Administrator\\Desktop\\u=2556197056,2105630514&fm=27&gp=0.jpg");
////            source.toFile("C:\\Users\\Administrator\\Desktop\\optimized.jpg");
////            //返回byte[]
////            byte[] sourceData = Files.readAllBytes(Paths.get("unoptimized.jpg"));
////            byte[] resultData = Tinify.fromBuffer(sourceData).toBuffer();
////            //传图片url路径
////            Source sourceUrl = Tinify.fromUrl("https://tinypng.com/images/panda-happy.png");
////            sourceUrl.toFile("optimized.jpg");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    //从mongodb中读出gif
//    @Test
//    public void compressgif() {
//        try {
//
//            List<String> listGif=imgService.getAllGif();
//            for(String mongodbid:listGif){
//                System.out.print("mongodbId:"+mongodbid+",");
//                byte[] bytes = attachmentsManager.getFileByMongoDBId(mongodbid);
//                getFileFromBytes(bytes,"F:\\已上传到有趣图片网站上的图片视频\\从mongodb中传过来的gif\\"+mongodbid+".gif");
//                System.out.println("它成功");
//            }
//
//
////            File file = new File("F:\\已上传到有趣图片网站上的图片视频\\gif");
////            File [] files = file.listFiles();
////            for (int i = 0; i < files.length; i++)
////            {
////                File file1 = files[i];
////            }
////            String src = "C:\\Users\\Administrator\\Desktop\\images.gif";
////            String out = "C:\\Users\\Administrator\\Desktop\\a.gif";
////            int width = 320;
////            GifUtils.resizeByWidth(src, out, width);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    //将gif压缩并存储成文件
//    @Test
//    public void realcompressgif() {
//        try {
//
//            File file = new File("F:\\已上传到有趣图片网站上的图片视频\\从mongodb中传过来的gif");
//            File [] files = file.listFiles();
//            for (int i = 0; i < files.length; i++)
//            {
//                File file1 = files[i];
//                String src = file1.getAbsolutePath();
//                String out = "F:\\已上传到有趣图片网站上的图片视频\\已压缩的图片\\gif\\"+file1.getName()+"";
//                int width = 320;
//                System.out.println("开始压缩"+file1.getName());
//                GifUtils.resizeByWidth(src, out, width);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//    /**
//     * 把字节数组保存为一个文件
//     *
//     * @param b
//     * @param outputFile
//     * @return
//     */
//    public static File getFileFromBytes(byte[] b, String outputFile) {
//        File ret = null;
//        BufferedOutputStream stream = null;
//        try {
//            ret = new File(outputFile);
//            FileOutputStream fstream = new FileOutputStream(ret);
//            stream = new BufferedOutputStream(fstream);
//            stream.write(b);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return ret;
//    }
//
//    //删除mongodb下所有MP4
//    @Test
//    public void deleteMongoMp4() {
//        try {
//            List<String> list=imgService.findAllMp4Mongo();
//            for(String mongoDbId:list){
//                attachmentsManager.deleteMondoDB(mongoDbId);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//}
