package com.zd.utils.tools.util;

public class MyBatisUtil {

    /**
     * 对内容前后加 %
     * @return
     */
    public static String splicingLike(String value){
        return "%" + value + "%";
    }

    /**
     * 对内容前加 %
     * @param value
     * @return
     */
    public static String prefixLike(String value){
        return "%" + value;
    }

    /**
     * 对内容后加 %
     * @param value
     * @return
     */
    public static String suffixLike(String value){
        return value + "%";
    }
}
