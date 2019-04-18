package com.zd.utils.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * create by wzc on 2018/5/11
 */

/**
 * 基础公共工具集。
 */
public class NullCheckUtil {

    /**
     * 判断集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断Map是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断数组是否为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断List是否为空
     * @param list
     * @return
     */
    public static boolean isEmpty(List<Object> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 检查对象是否为空。
     *
     * @param obj 被检查对象
     * @return 对象为空返回true，字符串和BigDecimal为""也返回true，否则返回false
     */
    public static Boolean isNull(Object obj) {
        boolean bool = true;
        String typeName = null;
        // 对象本身为空
        if (obj == null) {
            bool = true;
        } else {
            // 再根据对象的真实类型进行检查
            typeName = obj.getClass().getName();
            switch (typeName) {
                case "java.lang.String":
                    bool = "".equals(((String) obj).trim()) || "null".equalsIgnoreCase(((String) obj).trim());
                    break;
                case "java.lang.Integer":
                    bool = "".equals(obj.toString().trim());
                    break;
                case "java.math.BigDecimal":
                    bool = "".equals(obj.toString().trim());
                    break;
                case "java.util.List":
                    bool = ((List) obj).size() == 0;
                    break;
                case "java.util.ArrayList":
                    bool = ((List) obj).size() == 0;
                    break;
                case "java.util.LinkedList":
                    bool = ((List) obj).size() == 0;
                    break;
                case "java.util.HashMap":
                    bool = ((Map) obj).size() == 0;
                    break;
                default:
                    bool = false;
                    break;
            }
        }
        return bool;
    }

    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);

                System.out.print(f.getName() + ":");
                System.out.println(f.get(object));

                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
