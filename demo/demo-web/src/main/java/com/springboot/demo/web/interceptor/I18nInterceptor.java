package com.springboot.demo.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
@Slf4j
public class I18nInterceptor implements HandlerInterceptor {
    private static final String COOKIE_NAME_LANG = "lang";

    private static final String ENGLISH_LANG = "en";

    private static final String ENGLISH_COUNTRY = "US";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 1、获取请求的Cookies
        Cookie[] cookies = request.getCookies();

        // 2、遍历Cookie 查看是否有目标Cookie
        String lang = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME_LANG.equals(cookie.getName())) {
                    lang = cookie.getValue();
                    break;
                }
            }
        }

        // 3、设置到语言环境中
        LocaleContextHolder.setLocale(getLocale(lang));
        return true;
    }

    private Locale getLocale(String lang) {
        // 默认中国大陆-简体中文 lang=zh country=CN
        Locale defaultLocale = Locale.SIMPLIFIED_CHINESE;
        if (StringUtils.isEmpty(lang)) {
            return defaultLocale;
        }
        // 英语采用 en_US
        if (lang.toLowerCase(Locale.ENGLISH).contains(ENGLISH_LANG)) {
            return new Locale(ENGLISH_LANG, ENGLISH_COUNTRY);
        }
        return defaultLocale;
    }
}
