package com.baixin.prayblog.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangbaixin
 * @since 2022-7-28
 */
public interface FileConstants {
    /**
     * 文件后缀
     *     //文档
     *     //图片
     *     //视屏
     *     //音频
     *     //压缩
     */

    List<String> document= Arrays.asList(".doc",".docx",".xls",".xlsx",".ppt",".pptx",".txt",".pdf");

    List<String> picture=Arrays.asList(".jpg",".png",".JPEG",".bmp",".gif");

    List<String> video=Arrays.asList(".avi",".mp4",".rmvb",".flv",".wmv");

    List<String> audio=Arrays.asList(".mp3",".wav",".amr");

    List<String> compress= Arrays.asList(".zip",".rar",".7z");

}
