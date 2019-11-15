package com.springboot.demo.web;

import com.springboot.demo.common.constants.Constants;
import com.springboot.demo.common.utils.MD5Utils;
import com.springboot.demo.web.model.ResultInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TestController {

    private static final String URL_PREFIX = "http://localhost:10095/demo";

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testQuery() {
        ResultInfo resultInfo = restTemplate.getForObject(URL_PREFIX + "/query?origin=1&sign=f8a7e51875f63413479d561248398264", ResultInfo.class);
        Assert.isTrue(resultInfo.getCode() == 0, "Query Failed");
    }

    @Test
    public void testClear() {
        Map<String, String> request = new HashMap<>();
        request.put(Constants.SIGN_ORIGIN_KEY, "1");
        request.put(Constants.SIGN_KEY, MD5Utils.stringToMD5(request));
        ResultInfo resultInfo = restTemplate.postForObject(URL_PREFIX + "/clear", request, ResultInfo.class);
        Assert.isTrue(resultInfo.getCode() == 0, "Query Failed");
    }
}
