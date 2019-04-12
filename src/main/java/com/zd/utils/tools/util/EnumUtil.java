package com.zd.utils.tools.util;

import com.zd.utils.constant.BaseEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Zidong
 * @date 2019/3/15 下午2:23
 */
public class EnumUtil {
    public static <T> T getEnumByValue(Class<T> cls, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        if (!BaseEnum.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("The Enum must be implement " + BaseEnum.class.getName());
        }
        Method method = cls.getMethod("values");
        BaseEnum[] enums = (BaseEnum[]) method.invoke(null, null);
        BaseEnum result = null;
        for (BaseEnum baseEnum : enums) {
            if (baseEnum.getValue().equals(value)) {
                result = baseEnum;
                break;
            }
        }
        if (result == null) {
            throw new NoSuchFieldException("No such enum by " + value);
        }
        return (T) result;
    }
}
