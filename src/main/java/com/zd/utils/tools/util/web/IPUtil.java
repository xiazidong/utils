package com.zd.utils.tools.util.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.zd.utils.constant.CharConstant.COMMA;
import static com.zd.utils.constant.NetworkConstant.LOCALHOST_IP;
import static com.zd.utils.tools.util.regex.RegexUtils.isIp;

/**
 * @author Zidong
 * @date 2019/5/8 7:17 PM
 */
public class IPUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPUtil.class);

    private static final String[] IP_HEADERS = new String[]{"X-Forwarded-For", "REMOTE_ADDR", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

    private static final String UNKNOWN_IP = "unknown";

    /**
     * 适用于 scs或者nginx 前端为cdn等可信任的代理时
     *
     * @param request httpRequest
     * @return ip
     */
    private static String getHttpClientIp(HttpServletRequest request) {
        return getIp(request, true);
    }

    /**
     * 适用于客户端直接访问 scs或者nginx，即前面不存在cdn等反向代理
     *
     * @return ip
     */
    private static String getTcpClientIp(HttpServletRequest request) {
        return getIp(request, false);
    }

    private static String getIp(HttpServletRequest request, boolean userCdn) {
        String ipAddress = request.getHeader("HTTP_CIP");
        if (verifyIp(ipAddress)) {
            LOGGER.warn("return from HTTP_CIP " + ipAddress);
            return ipAddress;
        }
        ipAddress = request.getHeader(IP_HEADERS[0]);
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            // "***.***.***.***".length() = 15
            String[] ipAddressArray = ipAddress.split(COMMA);
            if (ipAddressArray.length > 0) {
                if (userCdn) {
                    ipAddress = ipAddressArray[0];
                } else {
                    ipAddress = ipAddressArray[ipAddressArray.length - 1].trim();
                }
            }
        }
        if (verifyIp(ipAddress)) {
            return ipAddress;
        }
        ipAddress = request.getHeader(IP_HEADERS[1]);
        if (verifyIp(ipAddress)) {
            return ipAddress;
        }
        ipAddress = request.getHeader(IP_HEADERS[2]);
        if (verifyIp(ipAddress)) {
            return ipAddress;
        }
        ipAddress = request.getRemoteAddr();
        if (LOCALHOST_IP.equals(ipAddress)) {
            //根据网卡取本机配置的IP
            InetAddress inet;
            try {
                inet = InetAddress.getLocalHost();
                ipAddress = inet.getHostAddress();
            } catch (UnknownHostException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return ipAddress;
    }

    private static boolean verifyIp(String ip) {
        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            return false;
        }
        return isIp(ip);
    }

    public static long ipToLong(String ip) {
        long[] array = new long[4];
        int position1 = ip.indexOf(".");
        int position2 = ip.indexOf(".", position1 + 1);
        int position3 = ip.indexOf(".", position2 + 1);
        array[0] = Long.parseLong(ip.substring(0, position1));
        array[1] = Long.parseLong(ip.substring(position1 + 1, position2));
        array[2] = Long.parseLong(ip.substring(position2 + 1, position3));
        array[3] = Long.parseLong(ip.substring(position3 + 1));
        return (array[0] << 24) + (array[1] << 16) + (array[2] << 8) + array[3];
    }

    public static String ipToString(long ip) {
        return "" + String.valueOf((ip >>> 24)) +
                "." +
                String.valueOf((ip & 0x00FFFFFF) >>> 16) +
                "." +
                String.valueOf((ip & 0x0000FFFF) >>> 8) +
                "." +
                String.valueOf((ip & 0x000000FF));
    }

}
