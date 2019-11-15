package com.springboot.demo.service.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {

//    @ConfigurationProperties(prefix = "spring.datasource.druid.oracle")
//    @Bean
//    public DataSource druid(){
//        return new DruidDataSource();
//    }
}
