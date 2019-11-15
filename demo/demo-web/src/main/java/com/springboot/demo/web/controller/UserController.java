package com.springboot.demo.web.controller;

import com.springboot.demo.service.dao.domain.User;
import com.springboot.demo.web.model.ResultInfo;
import com.springboot.demo.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/user/query")
    public List<User> queryUsers(@PathParam("pageNum") int pageNum, @PathParam("pageSize") int pageSize) {
        log.info("Query users.");
        return userService.selectAll(pageNum, pageSize);
    }

    @GetMapping("/user/queryUserById/{userId}")
    public User queryUserById(@PathVariable String userId) {
        log.info("Query user by Id.");
        return userService.queryUserById(userId);
    }

    @PostMapping("/user/add")
    public ResultInfo addUser(@RequestBody User user) {
        log.info("Add user.");
        userService.addUser(user);
        return new ResultInfo();
    }
}
