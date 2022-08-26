package com.baixin.prayblog.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {
    //匹配url 中的资源映射

    @Value("${accessFile.resourceHandler}")
    private String resourceHandler;
    //上传文件保存的本地目录

    @Value("${accessFile.location}")
    private String location;

    /**
     * 配置静态资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //匹配到resourceHandler,将URL映射至location,也就是本地文件夹
        registry.addResourceHandler(resourceHandler).addResourceLocations("file:///" + location);
    }
}
