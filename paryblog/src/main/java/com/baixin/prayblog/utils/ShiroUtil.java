package com.baixin.prayblog.utils;


import com.baixin.prayblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author zhangdada
 */
public class ShiroUtil {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
