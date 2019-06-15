package com.zd.utils.constant;

/**
 * @author Zidong
 * @date 2019/5/8 6:25 PM
 */
public class PatternConstant {

    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static final String CHINESE = "[\u0391-\uFFE5]+$";

    public static final String DOUBLE_FLOAT = "^[-\\+]?\\d+\\.\\d+$";

    public static final String COMPLATE_INTEGER = "^[-\\+]?[\\d]+$";

    public final static String IP_PATTERN_CONSTANT = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    /**
     * 匹配正整数
     */
    public final static String POSITIVE_INTEGER = "^[1-9]\\d*$";

    /**
     * 匹配负整数
     */
    public final static String NEGATIVE_INTEGER = "^-[1-9]\\d*$";

    /**
     * 匹配整数
     */
    public final static String INTEGER = "^-?[1-9]\\d*$";

    /**
     * 匹配非负整数（正整数 + 0）
     */
    public final static String NON_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";

    /**
     * 匹配非正整数（负整数 + 0）
     */
    public final static String NON_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";

    /**
     * 匹配正浮点数
     */
    public final static String FLOATING_POINT_NUMBER = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    /**
     * 匹配负浮点数
     */
    public final static String FLOATING_POINT_SCORE = "^-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)$";

    /**
     * 匹配浮点数
     */
    public final static String FLOAT = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";

    /**
     * 匹配非负浮点数（正浮点数 + 0）
     */
    public final static String NON_NEGATIVE_FLOATING_POINT_NUMBER = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";

    /**
     * 匹配非正浮点数（负浮点数 + 0）
     */
    public final static String NON_POSITIVE_FLOATING_POINT_NUMBER = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";

}
