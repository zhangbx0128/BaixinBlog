package com.baixin.prayblog.controller;

import com.baixin.prayblog.entity.TbUserAuth;
import com.baixin.prayblog.service.ITbUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author zhangdada
 */
public class testController {
    @Autowired
    private ITbUserAuthService iTbUserAuthService;
    /**

     * @return 测试操作日志
     */

    @PostMapping("/12")
    public List<TbUserAuth> test12(){
        iTbUserAuthService.list();
        return iTbUserAuthService.list();
    }
}
