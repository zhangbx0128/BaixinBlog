package com.baixin.prayblog.utils;


import com.baixin.prayblog.common.constant.FileConstants;
import com.baixin.prayblog.common.enums.FileUploadTypeEnum;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.UUID;

/**
 * @author zhangdada
 * @since 2022-7-28
 */
@Slf4j
public class FileUtils {
    static SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");



    public static String upload(MultipartFile uplodFile, HttpServletRequest request){
        //文件名称
        String oldName=uplodFile.getOriginalFilename();
        //文件后缀
        String fileSuf=oldName.substring(oldName.lastIndexOf("."),oldName.length()).toLowerCase();
        //判断文件类型并选择合适目录
        String pathSuf=null;
        if (FileConstants.document.contains(fileSuf)) {
            pathSuf= FileUploadTypeEnum.DOCUMENT.getName();
        }
        if (pathSuf==null&&FileConstants.picture.contains(fileSuf)) {
            pathSuf= FileUploadTypeEnum.PICTURE.getName();
        }
        if (pathSuf==null&&FileConstants.video.contains(fileSuf)) {
            pathSuf= FileUploadTypeEnum.VIDEO.getName();
        }
        if (pathSuf==null&&FileConstants.audio.contains(fileSuf)) {
            pathSuf= FileUploadTypeEnum.AUDIO.getName();
        }
        if (pathSuf==null&&FileConstants.compress.contains(fileSuf)) {
            pathSuf= FileUploadTypeEnum.COMPRESS.getName();
        }
        if (pathSuf==null) {
            return "该文件类型暂时不支持";
        }
        //创建新名称
        String newFileName= UUID.randomUUID().toString()+fileSuf;
        //创建新文件目录(tomcat)临时目录下的 document或picture……/yyyy/MM/dd/  可以自定义目录配置nginx进行回显
        //String realPath=request.getSession().getServletContext().getRealPath(pathSuf)+sdf.format(new Date());
        //先存储到磁盘
        String realPath ="/Users/zhangdada/Java/mxjk/sp-web/src/main/resources/static"+pathSuf;
        //如果文件目录不存在 新创建
        File folder=new File(realPath);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        try {
            //复制文件到目标文件和名字
            uplodFile.transferTo(new File(folder,newFileName));
            //返回可访问地址
            String resStr = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/show"+pathSuf + newFileName;
            log.info(resStr);
            return resStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


}
