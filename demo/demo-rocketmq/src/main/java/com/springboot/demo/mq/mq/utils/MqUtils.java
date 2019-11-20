package com.springboot.demo.mq.mq.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

@Slf4j
public final class MqUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private MqUtils() {
    }

    // 字符串转byte[]
    public static byte[] convertStrToByteArr(String str) {
        try {
            return str == null ? new byte[0] : str.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("Convert string to byte array fail, str is {}", str, e);
        }
        return new byte[0];
    }

    // byte[]转字符串
    public static String convertByteArrToStr(byte[] bytes) {
        return bytes == null ? "" : convertByteArrNotNull(bytes);
    }

    private static String convertByteArrNotNull(byte[] bytes) {
        try {
            return new String(bytes, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("Convert byte array to string fail.", e);
        }
        return "";
    }

}
