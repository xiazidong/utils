package com.zd.utils.tools.util.encrypt;

import java.util.Base64;

/**
 * BASE64加解密工具类
 */
public class Base64Utils {

    /**
     * BASE64字符串解码为二进制数据
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64){
        return Base64.getDecoder().decode(base64.getBytes());
    }

    /**
     * 二进制数据编码为BASE64字符串
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes){
        return new String(Base64.getEncoder().encode(bytes));
    }
}
