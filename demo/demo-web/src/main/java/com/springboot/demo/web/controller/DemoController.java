package com.springboot.demo.web.controller;

import com.springboot.demo.web.model.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

    @GetMapping("/query")
    public ResultInfo query() {
        log.info("DemoController query.");
        return new ResultInfo();
    }

    @PostMapping("/clear")
    public ResultInfo clear() {
        log.info("DemoController clear.");
        return new ResultInfo();
    }

}
