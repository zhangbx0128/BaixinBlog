package com.baixin.prayblog.shiro;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author zhangdada
 */
@Data
public class AccountProfile implements Serializable {

    private String id;

    private String account;

    private String avatar;

    private String email;



}
