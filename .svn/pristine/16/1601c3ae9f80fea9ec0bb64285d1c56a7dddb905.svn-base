package com.wanmi.sbc.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.StringTokenizer;

/**
 * Http相关帮助类
 *
 * @author lihe 2013-7-4 下午5:30:05
 * @see
 */
@Slf4j
public final class HttpUtil {

    private static final String UNKNOWN = "unknown";

    public static final String LOCAL_ADDRESS = "127.0.0.1";

    /**
     * 获取当前HTTP请求对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前HTTP请求对象
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取当前Scheme（含端口号）
     * 如http://127.0.0.1:80
     *
     * @return
     */
    public static String getBasePath() {
        HttpServletRequest request = getRequest();
        return MessageFormat.format("{0}://{1}:{2}", request.getScheme(), request.getServerName(), String.valueOf(request.getServerPort()));
    }

    /**
     * 获取当前项目名
     *
     * @return
     */
    public static String getProjectName() {
        HttpServletRequest request = getRequest();
        return request.getContextPath();
    }

    /**
     * 获取当前项目路径
     *
     * @return
     */
    public static String getProjectRealPath() {
        HttpServletRequest request = getRequest();
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取客户端ip
     *
     * @return
     */
    public static String getIpAddr() {

        return getIpAddr(getRequest());
    }
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(',') != -1) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
    /**
     * 获取真实的远端IP
     *
     * @param request HttpServletRequest
     * @return ip
     */
    public static String getRealRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");

        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("REMOTE-HOST");
        }

        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (StringUtils.isNotEmpty(ip)) {
            StringTokenizer st = new StringTokenizer(ip, ",");

            // 多级反向代理时，取第一个IP
            if (st.countTokens() > 1) {
                ip = st.nextToken();
            }
        } else {
            // 未获取到反向代理IP，返回RemoteAddr
            ip = request.getRemoteAddr();
        }

        return ip;
    }



    /**
     * 获取客户端设备信息
     *
     * @return
     */
    public static String getUserAgent() {
        HttpServletRequest request = getRequest();
        return request.getHeader("User-Agent");
    }
}
