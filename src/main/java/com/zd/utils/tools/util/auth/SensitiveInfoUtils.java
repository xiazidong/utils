package com.zd.utils.tools.util.auth;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

/**
 * 手机号脱敏
 *
 * @author michen
 */
public class SensitiveInfoUtils {
    /**
     * [电话号码 不区分手机和固定电话] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String commonPhone(final String phoneNum) {
        String padStr = "*";
        if (StringUtils.isBlank(phoneNum)) {
            return phoneNum;
        }
        int length = StringUtils.length(phoneNum);
        String rightReserved = StringUtils.right(phoneNum, 4);
        if (length < 7) {
            return StringUtils.leftPad(rightReserved, length, padStr);
        }
        String leftToAppend = StringUtils.left(phoneNum, 3);
        String paddingToLeft = StringUtils.leftPad(rightReserved, length, padStr);
        String headThreeCropped = StringUtils.removeStart(paddingToLeft, Strings.repeat(padStr, 3));
        return leftToAppend.concat(headThreeCropped);
    }
}
