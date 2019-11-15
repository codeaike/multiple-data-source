package com.springboot.demo.web.config;

import com.springboot.demo.web.interceptor.I18nInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 使用Cookie进行国际化资源的转换 与 I18nConfig选择其中之一即可
 */
@Configuration
public class I18nConfigII implements WebMvcConfigurer {
    @Resource
    private I18nInterceptor i18nInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(i18nInterceptor);
    }
}

