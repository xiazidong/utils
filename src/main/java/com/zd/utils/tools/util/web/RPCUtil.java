package com.zd.utils.tools.util.web;

import com.zd.utils.tools.util.io.IOUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.zd.utils.constant.HTTPMethod.GET_REQUEST;

/**
 * @author Zidong
 * @date 2019/5/8 7:14 PM
 */
@Slf4j
public class RPCUtil {
    /**
     * 调用接口，获取图片资源
     * @param response httpRes
     */
    public static void getImage(URL url, HttpServletResponse response){
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(GET_REQUEST);
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            byte[] data = IOUtil.readInputStream(inStream);
            //设置返回的文件类型
            response.setContentType("image/jpg");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
        } catch (Exception e) {
            log.error("获取图片资源发生异常，url:{}，异常原因:{}",url,e.getMessage());
        }
    }
}
