package com.springboot.demo.service.dao.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private String urid;

    private String userid;

    private String name;

    private Date gmtCreated = new Date();

    private Date gmtModified = new Date();

    private String memory;
}