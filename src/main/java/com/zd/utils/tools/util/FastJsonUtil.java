package com.zd.utils.tools.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * @author Zidong
 * @date 2019/4/18 8:24 PM
 */
public class FastJsonUtil {
    /**
     * 判断是否为 json格式
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        try {
            JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将JSON字符串转为Java对象
     * ex：Code2SessionResponse response = FastJsonUtil.jsonString2Object(resultJson, Code2SessionResponse.class);
     */
    public static <T> T jsonString2Object(String result, Class<T> clazz) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        return JSONObject.toJavaObject(jsonObject, clazz);
    }

    /**
     * JSON字符串对象解析成java List对象
     *
     */
    public static <T> ArrayList<T> jsonStringList2Object(String resultList, Class<T> clazz) {
        // 返回数据解析成JSONArray对象
        JSONArray jsonArray = JSONArray.parseArray(resultList);
        // JSON对象解析成java List对象
        return (ArrayList<T>) jsonArray.toJavaList(clazz);
    }

}
