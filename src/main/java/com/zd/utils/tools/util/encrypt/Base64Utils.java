package com.zd.utils.tools.util.encrypt;

import java.util.Base64;

/**
 * BASE64加解密工具类
 */
public class Base64Utils {

    /**
     * BASE64字符串解码为二进制数据
     */
    public static byte[] decode(String value){
        if (value != null && !"".equals(value.trim())) {
            return Base64.getDecoder().decode(value.getBytes());
        }
        return "".getBytes();
    }

    /**
     * 二进制数据编码为BASE64字符串
     */
    public static String encode(byte[] bytes){
        if (bytes != null && bytes.length != 0) {
            return new String(Base64.getEncoder().encode(bytes));
        }
        return "";
    }
}
