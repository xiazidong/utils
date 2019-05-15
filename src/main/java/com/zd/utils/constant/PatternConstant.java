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
}
