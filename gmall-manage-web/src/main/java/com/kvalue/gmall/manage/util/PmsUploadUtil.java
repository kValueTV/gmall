package com.kvalue.gmall.manage.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class PmsUploadUtil {
    public static String uploadImg(MultipartFile multipartFile){
        String imgUrl = "http://192.168.42.135";
        //获取配置文件路径
        String tracker = PmsUploadUtil.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient=new TrackerClient();
        //获取trackerServer实例
        TrackerServer trackerServer= null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //通过tracker获取一个storage连接客户端
        StorageClient storageClient=new StorageClient(trackerServer,null);

        try {
            //获取文件名：a.jpg
            String originalFilename = multipartFile.getOriginalFilename();
            //获取后缀名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //获取文件路径"d://a.jpg"
            byte[] bytes = multipartFile.getBytes();
            String[] uploadInfos = storageClient.upload_file(bytes, extName, null);
            for (String uploadInfo :uploadInfos){
                imgUrl += "/" + uploadInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgUrl;
    }
}
