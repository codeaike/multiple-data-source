package com.springboot.demo.service.datasource.aspect;

import com.springboot.demo.service.datasource.DataSourceContextHolder;
import com.springboot.demo.service.datasource.annotation.TargetDataSource;
import com.springboot.demo.service.datasource.type.EnumDataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
// 默认优先级 最后执行 可调整Order
public class DynamicDataSourceAspect {


    @Before("@annotation(targetDataSource)")
    public void before(JoinPoint point, TargetDataSource targetDataSource) {
        try {
            TargetDataSource annotationOfClass = point.getTarget().getClass().getAnnotation(TargetDataSource.class);
            String methodName = point.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
            Method method = point.getTarget().getClass().getMethod(methodName, parameterTypes);
            TargetDataSource methodAnnotation = method.getAnnotation(TargetDataSource.class);
            methodAnnotation = methodAnnotation == null ? annotationOfClass : methodAnnotation;
            EnumDataSourceType dataSourceType = methodAnnotation != null && methodAnnotation.value() != null ? methodAnnotation.value() : EnumDataSourceType.ORACLE;
            DataSourceContextHolder.setDataSource(dataSourceType.name());
        } catch (NoSuchMethodException e) {
            log.warn("Aspect targetDataSource inspect exception.", e);
        }
    }

    @After("@annotation(targetDataSource)")
    public void after(JoinPoint point, TargetDataSource targetDataSource) {
        DataSourceContextHolder.clearDataSource();
    }
}
