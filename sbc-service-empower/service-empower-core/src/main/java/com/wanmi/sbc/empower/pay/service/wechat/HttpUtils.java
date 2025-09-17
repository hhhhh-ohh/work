/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.wanmi.sbc.empower.pay.service.wechat;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * WEB请求工具类
 *
 * @author daiyitian
 * @version 1.0
 * @since 2017年5月15日下午2:49:32
 */
@Slf4j
public class HttpUtils {

    private static final String USERAGENT = "User-Agent";

    public static final int CONNECTIMEOUT = 20000;

    public static final int READTIMEOUT = 20000;

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like " + "Gecko) Chrome/31.0.1650.63 Safari/537.36";

    private HttpUtils() {
    }

    /**
     * Send a get request
     *
     * @param url
     * @return response
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        return get(url, Maps.newHashMap());
    }

    /**
     * Send a get request
     *
     * @param url     Url as string
     * @param headers Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    public static String get(String url, Map<String, String> headers) throws IOException {
        return fetch("GET", url, null, headers);
    }

    /**
     * Send a post request
     *
     * @param url  Url as string
     * @param body Request body as string
     * @return response Response as string
     * @throws IOException
     */
    public static String post(String url, String body) throws IOException {
        return post(url, body, null);
    }

    /**
     * Send a post request
     *
     * @param url     Url as string
     * @param body    Request body as string
     * @param headers Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    public static String post(String url, String body, Map<String, String> headers) throws IOException {
        Map<String, String> headers1 = headers;
        // set content type
        if (headers1 == null) {
            headers1 = new HashMap<>();
        }
        if(!headers1.containsKey("Content-Type")) {
            headers1.put("Content-Type", CONTENT_TYPE_JSON);
        }
        headers1.put(USERAGENT, USER_AGENT);
        return fetch("POST", url, body, headers1);
    }

    /**
     * Send a request
     *
     * @param method  HTTP method, for example "GET" or "POST"
     * @param url     Url as string
     * @param body    Request body as string
     * @param headers Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    private static String fetch(String method, String url, String body, Map<String, String> headers) throws IOException {
        if(!(url.startsWith("http://") || url.startsWith("https://"))){
            return null;
        }
        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(CONNECTIMEOUT);
            conn.setReadTimeout(READTIMEOUT);
            conn.setRequestMethod(method);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (body != null) {
                conn.setDoOutput(true);
                os = conn.getOutputStream();
                os.write(body.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();
            }

            is = conn.getInputStream();
            String response = streamToString(is);
            is.close();

            if (conn.getResponseCode() == 301) {
                String location = conn.getHeaderField("Location");
                return fetch(method, location, body, headers);
            }
            return response;
        } catch (IOException e) {
            throw e;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("关闭close异常", e);
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("关闭close异常", e);
                }
            }

            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Read an input stream into a string
     *
     * @param in
     * @return string
     * @throws IOException
     */
    public static String streamToString(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

}
