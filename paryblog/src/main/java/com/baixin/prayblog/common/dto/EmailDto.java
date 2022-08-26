package com.baixin.prayblog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangbaixin
 * @since 2022-7-29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    /**
     * 发送邮箱列表
     */
    private List<String> tos;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

}
