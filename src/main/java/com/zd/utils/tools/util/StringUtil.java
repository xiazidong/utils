package com.zd.utils.tools.util;

/**
 * @author Zidong
 * @date 2019/4/8 2:46 PM
 */
public class StringUtil {
    /**
     * 去掉字符串前后逗号
     *
     * @param str
     * @return
     */
    public static String isStarOrEndWithComma(String str) {
        if (str != null && !"".equals(str)) {
            boolean isCommaStart = str.startsWith(",");
            boolean isCommaEnd = str.endsWith(",");
            if (isCommaStart) {
                str = str.substring(1);
            }
            if (isCommaEnd) {
                str = str.substring(0, str.length() - 1);
            }
        } else {
            str = "";
        }
        return str;
    }
}
