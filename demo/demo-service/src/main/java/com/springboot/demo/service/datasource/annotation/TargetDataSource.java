package com.springboot.demo.service.datasource.annotation;

import com.springboot.demo.service.datasource.type.EnumDataSourceType;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {

    EnumDataSourceType value() default EnumDataSourceType.ORACLE;

}
