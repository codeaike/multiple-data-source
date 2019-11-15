package com.springboot.demo.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Locale;

@Slf4j
@Component
public class I18nService {
    @Resource
    private MessageSource messageSource;

    /**
     * 获取国际化资源
     * @param key 国际化资源的key
     * @return 国际化内容
     */
    public String getMessage(String key) {
        String defaultValue = "";
        if (StringUtils.isEmpty(key)) {
            return defaultValue;
        }
        Locale locale = LocaleContextHolder.getLocale();
        if (locale == null) {
            locale = Locale.SIMPLIFIED_CHINESE;
        }
        try {
            return messageSource.getMessage(key, null, locale);
        } catch (Exception ex) {
            log.warn("No message value for key: {}, locale: {}", key, locale, ex);
        }
        return defaultValue;
    }

}

