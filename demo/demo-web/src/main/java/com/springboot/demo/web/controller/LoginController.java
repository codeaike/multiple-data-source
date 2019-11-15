package com.springboot.demo.web.controller;

import com.springboot.demo.web.model.ResultInfo;
import com.springboot.demo.web.service.I18nService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class LoginController {
    private static final String LOGIN_KEY = "login";

    private static final String LOGIN_FAILED_KEY = "login.failed";

    @Resource
    private I18nService i18NService;

    @GetMapping("/login")
    public ResultInfo login() {
        log.info("login.");
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(i18NService.getMessage(LOGIN_KEY));
        return resultInfo;
    }

    @GetMapping("/login/failed")
    public ResultInfo loginFailed() {
        log.info("login failed.");
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(i18NService.getMessage(LOGIN_FAILED_KEY));
        return resultInfo;
    }
}
