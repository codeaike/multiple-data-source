package com.springboot.demo.web.config;

import com.springboot.demo.web.filter.HttpServletFilter;
import com.springboot.demo.web.interceptor.SignAuthInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置拦截器与过滤器
 */
@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    private static final String FILTER_PATH = "/*";

    @Resource
    private SignAuthInterceptor signAuthInterceptor;

    @Resource
    private HttpServletFilter httpServletFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(signAuthInterceptor);
    }

    @Bean
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(httpServletFilter);
        registration.addUrlPatterns(FILTER_PATH);
        registration.setOrder(1);
        return registration;
    }
}
