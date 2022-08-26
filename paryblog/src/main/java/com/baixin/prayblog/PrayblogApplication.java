package com.baixin.prayblog;

import org.apache.shiro.spring.config.web.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangdada
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ShiroAnnotationProcessorAutoConfiguration.class})
public class PrayblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrayblogApplication.class, args);
    }

}
