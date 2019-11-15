package com.springboot.demo.common.utils;

import com.google.gson.Gson;
import com.springboot.demo.common.constants.Constants;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MD5Utils {

    private static final String ALGORITHM_NAME = "md5";

    private static final String SECRET_KEY = "dfasuiyhkuhjk2t5290wouojjeerweeqwqdfd";

    private static final int RADIX = 16;

    private static final int LEN = 32;

    private static final String ADD_STR = "0";

    /**
     * 转换成对应的MD5信息
     * @param paramMap
     * @return
     */
    public static String stringToMD5(Map<String,String> paramMap) {
        String covertString = covertParamMapToString(paramMap);
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance(ALGORITHM_NAME).digest(
                    covertString.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such MD5 Algorithm.");
        }
        String md5code = new BigInteger(1, secretBytes).toString(RADIX);
        for (int i = 0; i < LEN - md5code.length(); i++) {
            md5code = ADD_STR + md5code;
        }
        return md5code;
    }

    /**
     * 转换成对应的string信息
     * @param paramMap
     * @return
     */
    private static String covertParamMapToString(Map<String,String> paramMap) {
        Set<String> sets = paramMap.keySet();
        List<String> valueList = new ArrayList<>();
        for (String key : sets) {
            if (key.equals(Constants.SIGN_KEY)) {
                continue;
            }
            String value = paramMap.get(key);
            valueList.add(value);
        }
        Collections.sort(valueList);
        String jsonString = new Gson().toJson(valueList);
        jsonString = jsonString + SECRET_KEY;
        return jsonString;
    }

}
