package com.baixin.prayblog.config;


import com.baixin.prayblog.entity.SnowflakeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbaixin
 */
@Data
@ConfigurationProperties(prefix = "com.baixin.prayblog")
public class TestProperties {

    private SnowflakeProperties snowflake;

}

