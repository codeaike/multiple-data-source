package com.springboot.demo.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultInfo {
    /**
     * 返回码 code=0表示成功
     */
    private int code = 0;

    /**
     * 错误描述
     */
    private String msg = "";

    /**
     * 返回的对象
     */
    private Object object;
}
