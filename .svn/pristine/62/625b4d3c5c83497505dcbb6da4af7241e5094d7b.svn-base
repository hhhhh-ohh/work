package com.wanmi.sbc.empower.pay.service.wechat;

import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Map;
import java.util.Objects;

/**
 * @author wur
 * @className WechatPayHttpUtil
 * @date 2022/11/28 11:07
 **/
public class WechatPayHttpUtil {

    /***
     * 请求器的配置
     */
    private static RequestConfig requestConfig;

    /***
     * 连接超时时间,默认10秒
     */
    private static int socketTimeout = 10000;

    /***
     * 传输超时时间,默认30秒
     */
    private static int connectTimeout = 30000;

    /***
     * HTTP请求器
     */
    private static CloseableHttpClient httpClient;

    /**
     * @description   静态变量赋值
     * @author  wur
     * @date: 2022/9/13 18:22
     * @param httpClient
     * @return
     **/
    private static void setHttpClient(CloseableHttpClient httpClient) {
        WechatPayHttpUtil.httpClient = httpClient;
    }

    /**
     * 初始化httpclient
     **/
    private static void initHttpClient(PayGatewayConfig payGatewayConfig) throws Exception{
        String merchantId = payGatewayConfig.getAccount();

        //私钥
        String privateKey = payGatewayConfig.getPrivateKey();

        //apiV3Key
        String apiV3Key = payGatewayConfig.getApiV3Key();

        //证书序列号
        String merchantSerialNumber = payGatewayConfig.getMerchantSerialNumber();

        // 加载商户私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));

        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(merchantId, new WechatPay2Credentials(merchantId,
                new PrivateKeySigner(merchantSerialNumber, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));

        // 从证书管理器中获取verifier
        Verifier verifier = certificatesManager.getVerifier(merchantId);

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(merchantId, merchantSerialNumber, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier));

        // 初始化httpClient
        setHttpClient(builder.build());
    }

    /**
     * 通过Https往API GET json数据
     *
     * @param url    API地址
     * @return
     */
    public static String doGetData(String url, PayGatewayConfig payGatewayConfig) throws Exception {
        WechatPayHttpUtil.initHttpClient(payGatewayConfig);
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpGet.addHeader("Accept", "application/json");

        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpGet.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = null;
            if (Objects.nonNull(response)) {
                entity = response.getEntity();
            }
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpGet.abort();
        }
        return result;
    }



    /**
     * 通过Https往API post json数据
     *
     * @param url    API地址
     * @param jsonString 要提交的jsonString数据对象
     * @return
     */
    public static String doPostData(String url, String jsonString, PayGatewayConfig payGatewayConfig) throws Exception {
        WechatPayHttpUtil.initHttpClient(payGatewayConfig);
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.addHeader("Accept", "application/json");

        // 得指明使用UTF-8编码
        StringEntity postEntity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(postEntity);

        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = null;
            if (Objects.nonNull(response)) {
                entity = response.getEntity();
            }
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpPost.abort();
        }
        return result;
    }


    public static String doGetData(String url, PayGatewayConfig payGatewayConfig, Map<String, String> headerMap) throws Exception {
        WechatPayHttpUtil.initHttpClient(payGatewayConfig);
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        if (Objects.nonNull(headerMap) && !headerMap.isEmpty()) {
            headerMap.forEach((k, v) -> {
                httpGet.addHeader(k, v);
            });
        }
        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpGet.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = null;
            if (Objects.nonNull(response)) {
                entity = response.getEntity();
            }
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpGet.abort();
        }
        return result;
    }
}