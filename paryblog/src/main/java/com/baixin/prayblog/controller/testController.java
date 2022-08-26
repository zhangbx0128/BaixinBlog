package com.baixin.prayblog.controller;

import com.baixin.prayblog.entity.TbUserAuth;
import com.baixin.prayblog.service.ITbUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangdada
 */
@RestController
@RequestMapping("/test")
public class TestController {
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
    @GetMapping("/test")
    public int test(){

        return 1;}

}
