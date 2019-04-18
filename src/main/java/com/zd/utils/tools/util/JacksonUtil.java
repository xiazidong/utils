package com.zd.utils.tools.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sf.pg.entity.R;

import java.io.IOException;
import java.util.List;


public class JacksonUtil {

    private static ObjectMapper om = null;

    static {
        om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * java bean 转成json字符串
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析对象的json
     *
     * @param json 对象字符串
     * @param bean bean
     * @param <T>  泛型
     * @return bean
     */
    public static <T> T parseBean(String json, Class<T> bean) {
        try {
            return om.readValue(json, bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析 List的 JSON
     *
     * @param json 集合字符串
     * @param bean
     * @param <T>  List的bean
     * @return
     */
    public static <T> List<T> parseList(String json, Class<T> bean) {
        List<T> list = null;
        TypeFactory typeFactory = om.getTypeFactory();
        try {
            list = om.readValue(json, typeFactory.constructCollectionType(List.class, bean));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取json节点数据
     *
     * @param key
     * @param json
     * @return key的value值
     */
    public static String getNodeString(String key, String json) {
        String result = null;
        try {
            JsonNode jsonNode = om.readTree(json);
            result = String.valueOf(jsonNode.get(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 列表数据返回
     * @param result
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> R<List<T>> apiJsonToListBean(String result, Class<T> bean) {
        String code = getNodeString("code", result);
        String message = getNodeString("message", result);
        boolean success = Boolean.parseBoolean(getNodeString("success", result));
        List<T> data = parseList(getNodeString("data", result), bean);
        return new R(success, message, code, data);
    }

    /**
     * 实体返回
     * 例如 json形式的 R<Object> 转换为对象
     * ex: R<MyShipperDetailResponse> r = JsonParse.apiJsonToBean(post.getBody(), MyShipperDetailResponse.class);
     * 存在一个问题，如果 原始的 R对象中的 message存在 ""，再次还原的时候会变为 """"，需要去掉多余的 ""
     *  if (r.getCode() != null) {
     *      String replace = r.getCode().replace("\"", "");
     *      r.setCode(replace);
     *  }
     *  if (r.getMessage() != null) {
     *      String replace = r.getMessage().replace("\"", "");
     *      r.setMessage(replace);
     *  }
     * @param result
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> R<T> apiJsonToBean(String result, Class<T> bean) {
        String code = getNodeString("code", result);
        String message = getNodeString("message", result);
        boolean success = Boolean.parseBoolean(getNodeString("success", result));
        T data = parseBean(getNodeString("data", result), bean);
        return new R<>(success, message, code, data);
    }

}
