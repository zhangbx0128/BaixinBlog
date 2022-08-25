package com.baixin.prayblog;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.*;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {
  public static void main(String[] args) {
    /**
     * mybaits-plus 生成代码到指定模块路径下
     *
     * @param args
     */
    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();
    // set freemarker 模板引擎
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());

    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    // gc.setOutputDir(projectPath + "/src/main/java");
    gc.setAuthor("baixin");
    gc.setDateType(DateType.ONLY_DATE);
    gc.setOpen(false);
    gc.setBaseResultMap(true);
    gc.setSwagger2(true); //实体属性 Swagger2 注解
    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl(
        "jdbc:mysql://localhost:3306/blog?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
    // dsc.setSchemaName("public");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("Zbx1314520!");
    mpg.setDataSource(dsc);

    /** 包配置 简单来说 就是写绝对路径 */
    PackageConfig pc = new PackageConfig();
    //  pc.setModuleName("code");
    pc.setParent("com.baixin.prayblog");
    // 指定生成文件导入的包
    pc.setEntity("entity");
    pc.setService("service");
    pc.setServiceImpl("service.impl");
    pc.setController("controller");
    pc.setMapper("mapper");
    pc.setXml(null);
    // 指定生成文件的绝对路径
    Map<String, String> pathInfo = new HashMap<>();


///Users/zhangdada/BaixinBlog/ParyBlog/src/main/java/com/baixin/prayblog
    String entityPath =
        projectPath.concat("/ParyBlog/src/main/java/com/baixin/prayblog/entity");
    String mapper_path =
        projectPath.concat("/ParyBlog/src/main/java/com/baixin/prayblog/mapper");
    String xml_path = projectPath.concat("/ParyBlog/src/main/resources/mapper");
    String service_path =
        projectPath.concat("/ParyBlog/src/main/java/com/baixin/prayblog/service");
    String service_impl_path =
        projectPath.concat("/ParyBlog/src/main/java/com/baixin/prayblog/service/impl");
    String controller_path =
        projectPath.concat("/ParyBlog/src/main/java/com/baixin/prayblog/controller");

    pathInfo.put("entity_path", entityPath);
    pathInfo.put("mapper_path", mapper_path);
    pathInfo.put("xml_path", xml_path);
    pathInfo.put("service_path", service_path);
    pathInfo.put("service_impl_path", service_impl_path);
    pathInfo.put("controller_path", controller_path);
    pc.setPathInfo(pathInfo);
    mpg.setPackageInfo(pc);

    // 自定义配置
    // 自定义配置
    InjectionConfig cfg =
        new InjectionConfig() {
          @Override
          public void initMap() {
            // to do nothing
          }
        };
    mpg.setCfg(cfg);
    // 配置模板
    TemplateConfig templateConfig = new TemplateConfig();
    mpg.setTemplate(templateConfig);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setEntityLombokModel(true);
    strategy.setRestControllerStyle(true);
    strategy.setControllerMappingHyphenStyle(true); // 驼峰转
    strategy.setInclude("tb_user_auth"); // 表名
    strategy.setControllerMappingHyphenStyle(true);
    mpg.setStrategy(strategy);
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    mpg.execute();
  }
}
