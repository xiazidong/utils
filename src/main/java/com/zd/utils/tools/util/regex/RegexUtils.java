package com.zd.utils.tools.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zd.utils.constant.PatternConstant.*;

/**
 * 正则表达式工具类，验证数据是否符合规范
 */
public class RegexUtils {

    /**
     * 判断字符串是否符合正则表达式
     */
    public static boolean find(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断输入的字符串是否符合Email格式.
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL);
        return pattern.matcher(email).matches();
    }

    /**
     * 判断输入的字符串是否为纯汉字
     */
    public static boolean isChinese(String value) {
        Pattern pattern = Pattern.compile(CHINESE);
        return pattern.matcher(value).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     */
    public static boolean isDouble(String value) {
        Pattern pattern = Pattern.compile(DOUBLE_FLOAT);
        return pattern.matcher(value).matches();
    }

    /**
     * 判断是否为整数
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile(COMPLATE_INTEGER);
        return pattern.matcher(value).matches();
    }

    /**
     * 判断是否为IP地址
     */
    public static boolean isIp(String value) {
        Pattern pattern = Pattern.compile(IP_PATTERN_CONSTANT);
        return pattern.matcher(value).matches();
    }
}
