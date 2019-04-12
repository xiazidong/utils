package com.zd.utils.tools.util.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

import static com.zd.utils.constant.HTTPMethod.DEL_REQUEST;
import static com.zd.utils.constant.HTTPMethod.GET_REQUEST;

/**
 * 使用此 utils需要在启动类上注入设置
 * <p>
 * 启动的时候要注意，由于我们在controller中注入了 RestTemplate，所以启动的时候需要实例化该类的一个实例
 *
 * @author Zidong
 * @Autowired private RestTemplateBuilder builder;
 * <p>
 * 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
 * @Bean public RestTemplate restTemplate() {
 * return builder.build();
 * }
 * @date 2019/4/10 2:40 PM
 */
@Component
public class RestTemplateUtil {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 如果是 get、del请求，将会把参数拼接在 url后面，否则会被以表单提交的方式放入到方法体中
     *
     * @param method 请求方式
     * @param url    请求路径
     * @param body   方法体内容 封装参数，千万不要替换为 Map与HashMap，否则参数无法传递
     * @param header 请求头 如果不需要传递自定义 request header,请传递 null
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> doFormRequest(HttpMethod method, String url, final Map<String, String> body, final Map<String, String> header) {
        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if (header != null && header.size() > 0) {
            setHeader(header, headers);
        }
        // 如果是 get请求，将参数拼接到 url后面
        if (method.matches(GET_REQUEST) || method.matches(DEL_REQUEST)) {
            if (null != body && !body.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                Set<Map.Entry<String, String>> entries = body.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    sb.append("?");
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue());
                    sb.append("&");
                }
                // del last &
                sb.deleteCharAt(sb.length() - 1);
                url = url + sb.toString();
            }
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        if (body != null && body.size() > 0) {
            Set<Map.Entry<String, String>> entries = body.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                params.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        // do request
        return restTemplate.exchange(url, method, requestEntity, String.class);
    }

    /**
     * json提交
     * 接口需要使用 @RequestBody绑定参数
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> doJsonRequest(String url, final Map<String, String> params, final Map<String, String> header) {
        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (header != null && header.size() > 0) {
            setHeader(header, headers);
        }
        //  data to json string
        String jsonStr = JSONObject.toJSONString(params);
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonStr, headers);
        //  do request
        return restTemplate.postForEntity(url, requestEntity, String.class);

    }

    /**
     * 设置请求头
     */
    private void setHeader(Map<String, String> header, HttpHeaders headers) {
        Set<Map.Entry<String, String>> entries = header.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            headers.add(entry.getKey(), entry.getValue());
        }
    }
}
