package com.baixin.prayblog.common.enums;

/**
 * @author zhangbaixin
 * @since 2022-7-28
 */

public enum FileUploadTypeEnum {
    //文档
    DOCUMENT ("/document/"),
    //图片
    PICTURE ("/picture/"),
    //视屏
    VIDEO ("/video/"),
    //音频
    AUDIO ("/audio/"),
    //压缩
    COMPRESS ("/compress/");
    private String name;

    FileUploadTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
