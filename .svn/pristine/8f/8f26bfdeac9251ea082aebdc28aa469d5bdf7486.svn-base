package com.wanmi.sbc.empower.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @date: 2020/2/25 17:06
 * @author: xufan
 * @Description: HTTP工具类
 */
@Slf4j
public class HttpUtils {

    private final static int POOL_MAX_TOTAL = 640;

    private final static int POOL_DEFAULT_MAX_PER_ROUTE = 320;

    private final static int CONNECT_TIMEOUT = 60000;

    private final static int CONNECTION_REQUEST_TIMEOUT = 60000;

    private final static int SOCKET_TIMEOUT = 60000;

    private final static int RETRY_COUNT = 3;

    /***
     * 京东接口采用池化管理
     */
    private static PoolingHttpClientConnectionManager poolConnManager = null;

    /***
     * 线程安全，所有的线程都可以使用它一起发送http请求
     */
    private static CloseableHttpClient httpClient;

    static {
        try {
            log.info("初始化HttpClient开始");
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 http 和 https （京东接口使用https）
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory
                    .getSocketFactory()).register("https", sslsf).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 同时最多连接数
            poolConnManager.setMaxTotal(POOL_MAX_TOTAL);
            // 设置最大路由
            poolConnManager.setDefaultMaxPerRoute(POOL_DEFAULT_MAX_PER_ROUTE);
            // 初始化httpClient
            httpClient = getConnection();

            log.info("初始化HttpClient结束");
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            log.error("初始化HttpClient异常", e);
        }
    }

    /***
     * 发送一个POST请求
     * @param url
     * @param obj
     * @return
     */
    public static String postData(String url, Object obj) {
        log.info("vop--POST请求url={},请求参数={}", url, JSONObject.toJSONString(obj));
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> nameValuePairs = parseParams(obj);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            log.info("HttpUtils postData==================== {}", JSONObject.toJSONString(httpPost));
            response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity());
            log.info("vop--POST请求url={},返回参数={}", url, result);
            if (code == HttpStatus.SC_OK) {
                return result;
            } else {
                log.error("请求{}返回错误码:{},请求参数:{},{}", url, code, obj, result);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
            }
        } catch (IOException e) {
            log.error("收集服务配置http请求异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("HttpUtils postData 关闭流异常!", e);
            }
        }
    }

    /***
     * 发送一个GET请求
     * @param url
     * @param obj
     * @return
     */
    public static String getData(String url, Object obj) {
        log.info("GET请求url={},请求参数={}", url, JSONObject.toJSONString(obj));
        StringBuffer sb = new StringBuffer();
        Field[] fields = obj.getClass().getDeclaredFields();

        try{
            for (int i = 0; i < fields.length; i++) {
                ReflectionUtils.makeAccessible(fields[i]);
                if(i > 0){
                    sb.append("&");
                }
                if(Objects.nonNull(fields[i].get(obj))){
                    sb.append(fields[i].getName());
                    sb.append("=");
                    sb.append(fields[i].get(obj));
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        url += sb.toString();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity());
            log.info("Get请求url={},返回参数={}", url, result);
            if (code == HttpStatus.SC_OK) {
                return result;
            } else {
                log.error("请求{}返回错误码:{},请求参数:{},{}", url, code, obj, result);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
            }
        } catch (IOException e) {
            log.error("收集服务配置http请求异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("HttpUtils getData 关闭流异常!", e);
            }
        }
    }

    /***
     * 获得一个连接
     * @return
     */
    private static CloseableHttpClient getConnection() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        return HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(poolConnManager)
                .setDefaultRequestConfig(config)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, false)).build();
    }

    /***
     * 将JSON格式参数转为NameValuePair集合
     * @param obj   请求参数
     * @return
     */
    private static List<NameValuePair> parseParams(Object obj) {
        if (Objects.isNull(obj)) {
            return new ArrayList<>();
        }
        return JSONObject.parseObject(JSON.toJSONString(obj)).entrySet().stream()
                .map((e) -> new BasicNameValuePair(e.getKey(), toStr(e.getValue())))
                .collect(Collectors.toList());
    }

    /***
     * 将一个对象转为String
     * @param obj 对象
     * @return
     */
    private static String toStr(Object obj){
        return Objects.isNull(obj) ? null : obj.toString();
    }


    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}
