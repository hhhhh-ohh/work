package com.wanmi.sbc.empower.wechat;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.empower.api.request.wechatwaybill.TraceWaybillRequest;
import com.wanmi.sbc.empower.miniprogramset.model.root.MiniProgramSet;
import com.wanmi.sbc.empower.miniprogramset.service.MiniProgramSetService;
import com.wanmi.sbc.empower.wechat.constant.WechatApiConstant;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 调用微信api接口方法
 */
@Component
@Slf4j
public class WechatApiUtil {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private RedissonClient redissonClient;

    @Value("${wechat.access-token.cache:true}")
    private boolean accessTokenCache;

    @Autowired
    private MiniProgramSetService miniProgramSetService;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    private final static String ACCESS_TOKEN_KEY = "get_access_token_";

    private final static String ACCESS_TOKEN_LOCK = "get_access_token_lock_";

    private final static String STABLE_TOKEN_KEY = "get_stable_token_";

    private final static String STABLE_TOKEN_LOCK = "get_stable_token_lock_";

    private final  static  Long ACCESS_TOKEN_OUT_TIME = 3600L;

    /**
     * 获取access_token参数接口
     *
     * @return
     */
    public String getAccessToken(String type) {
        String redisKey = ACCESS_TOKEN_KEY + type;
        // 1.是否先从缓存中获取
        if (accessTokenCache) {
            return getAccessTokenPlus(type);
        }
        //并设置缓存失效时间为3600秒，access_token失效时间为7200秒
        String accessToken = getToken(type);
        if (StringUtils.isNotEmpty(accessToken)) {
            redisService.setString(redisKey, accessToken, ACCESS_TOKEN_OUT_TIME);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "获取accessToken失败");
        }
        log.info("小程序type:{}===AccessToken:{}", type, accessToken);
        return accessToken;
    }

    /**
    *
     * @description   通过缓存获取
     * @author  wur
     * @date: 2022/5/9 16:00
     * @param type
     * @return
     **/
    public String getAccessTokenPlus(String type) {
        String redisKey = ACCESS_TOKEN_KEY + type;
        // 1.是否先从缓存中获取
        String accessToken = redisService.getString(redisKey);
        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }
        //并设置缓存失效时间为3600秒，access_token失效时间为7200秒
        accessToken = getToken(type);
        if (StringUtils.isNotEmpty(accessToken)) {
            redisService.setString(redisKey, accessToken, ACCESS_TOKEN_OUT_TIME);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "获取accessToken失败");
        }
        log.info("小程序type:{}===AccessToken:{}", type, accessToken);
        return accessToken;
    }

    /**
     * @description    AccessToken过期重新获取
     * @author  wur
     * @date: 2022/5/9 11:22
     * @param type
     * @return
     **/
    public String getAccessTokenCleanRedis(String type) {
        String redisKey = ACCESS_TOKEN_KEY + type;
        redisService.delete(redisKey);
        //并设置缓存失效时间为3600秒，access_token失效时间为7200秒
        String accessToken = getToken(type);
        if (StringUtils.isNotEmpty(accessToken)) {
            redisService.setString(redisKey, accessToken, ACCESS_TOKEN_OUT_TIME);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "获取accessToken失败");
        }
        log.info("小程序type:{}===AccessToken:{}", type, accessToken);
        return accessToken;
    }

    /**
     * @description
     * @author  wur
     * @date: 2022/5/9 10:12
     * @param type
     * @return
     **/
    public String getToken(String type) {
        String accessToken = null;
        //redis锁，防止并发
        String lockKey = ACCESS_TOKEN_LOCK + type;
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try{
            // 1.通过接口获取
            String appId;
            String appSecret;
            MiniProgramSet miniProgramSet = miniProgramSetService.getOneByType(Constants.ZERO);
            //如果禁用,直接return null
            if (Constants.no.equals(miniProgramSet.getStatus())) {
                return null;
            }
            appId = miniProgramSet.getAppId();
            appSecret = miniProgramSet.getAppSecret();
            String url = String.format(WechatApiConstant.ACCESS_TOKEN_URL, WechatApiConstant.GRANT_TYPE, appId, appSecret);
            String accessTokenResStr = doGet(url);
            log.info("获取access_token接口url:{}，返回结果：{}",url,accessTokenResStr);
            if (StringUtils.isNotEmpty(accessTokenResStr)) {
                JSONObject tokenResJson = JSONObject.parseObject(accessTokenResStr);
                accessToken = tokenResJson.getString("access_token");
            }
            return accessToken;
        } catch (Exception e){
            log.error("获取access_token参数接口异常：", e);
            return accessToken;
        } finally {
            rLock.unlock();
        }
    }


    /**
     *
     * @description   通过缓存获取
     * @author  wur
     * @date: 2022/5/9 16:00
     * @param type
     * @return
     **/
    public String getStableTokenPlus(String type) {
        String redisKey = STABLE_TOKEN_KEY + type;
        // 1.是否先从缓存中获取
        String accessToken = redisService.getString(redisKey);
        if (StringUtils.isNotEmpty(accessToken)) {
            log.info("小程序type:{}===缓存StableToken:{}", type, accessToken);
            return accessToken;
        }
        //并设置缓存失效时间为3600秒，access_token失效时间为7200秒
        accessToken = getStableToken(type);
        if (StringUtils.isNotEmpty(accessToken)) {
            redisService.setString(redisKey, accessToken, ACCESS_TOKEN_OUT_TIME);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "获取accessToken失败");
        }
        log.info("小程序type:{}===StableToken:{}", type, accessToken);
        return accessToken;
    }


    /**
     * @description 稳定版token过期重新获取
     * @author  dyt
     * @date: 2025/5/9 11:22
     * @param type
     * @return
     **/
    public String getStableTokenCleanRedis(String type) {
        String redisKey = STABLE_TOKEN_KEY + type;
        redisService.delete(redisKey);
        //并设置缓存失效时间为3600秒，access_token失效时间为7200秒
        String accessToken = getStableToken(type);
        if (StringUtils.isNotEmpty(accessToken)) {
            redisService.setString(redisKey, accessToken, ACCESS_TOKEN_OUT_TIME);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "获取stableToken失败");
        }
        log.info("小程序type:{}===StableToken:{}", type, accessToken);
        return accessToken;
    }

    /**
     * @description 获取稳定版token
     * @author  dyt
     * @date: 2025/5/9 10:12
     * @param type
     * @return
     **/
    public String getStableToken(String type) {
        String accessToken = null;
        //redis锁，防止并发
        String lockKey = STABLE_TOKEN_LOCK + type;
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try{
            // 1.通过接口获取
            MiniProgramSet miniProgramSet = miniProgramSetService.getOneByType(Constants.ZERO);
            //如果禁用,直接return null
            if (Constants.no.equals(miniProgramSet.getStatus())) {
                return null;
            }
            String appId = miniProgramSet.getAppId();
            String appSecret = miniProgramSet.getAppSecret();
            JSONObject params = new JSONObject();
            params.put("grant_type", "client_credential");
            params.put("appid", appId);
            params.put("secret", appSecret);
            params.put("force_refresh", true);
            String paramStr = params.toJSONString();
            JSONObject res = doPost(WechatApiConstant.STABLE_TOKEN_URL, params.toJSONString());
            log.info("获取access_token接口url:{}，返回结果：{}",paramStr, res.toJSONString());
            if (res == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "获取accessToken失败");
            }
            accessToken = res.getString("access_token");
            return accessToken;
        } catch (Exception e){
            log.error("获取access_token参数接口异常：", e);
            return accessToken;
        } finally {
            rLock.unlock();
        }
    }



    /**
     * 通过该接口生成的小程序码，永久有效，数量暂无限制
     *
     * @param request 入参
     * @return
     */
    public String getWxaCodeUnlimit(MiniProgramQrCodeRequest request, String accessTokenType,String code) {
        String imagesUrl = this.getQrImageCache(request,code);
        if(StringUtils.isNotBlank(imagesUrl)){
            return imagesUrl;
        }
        byte[] qrCodeJson = this.getWxaCodeBytesUnlimit(request, accessTokenType);
        //上传小程序码到oss上,并返回地址给调用方

        if(Objects.nonNull(qrCodeJson)){
            String image= yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                    .resourceName("小程序码.jpg")
                    .content(qrCodeJson)
                    .resourceType(ResourceType.IMAGE)
                    .build()).getContext().getResourceUrl();
            this.updateQrImageCache(request, image, code);
            return image;
        }

        return "";
    }

    /**
     * 通过该接口生成的小程序码，永久有效，数量暂无限制
     *
     * @param request 入参
     * @return
     */
    public byte[] getWxaCodeBytesUnlimit(MiniProgramQrCodeRequest request, String accessTokenType) {
        String accessToken = getAccessToken(accessTokenType);
        String params = JSONObject.toJSONString(request);

        if(accessToken == null){
            return null;
        }
        // 1.获取微信小程序码字节数组
        String url = String.format(WechatApiConstant.GET_WX_A_CODE_UNLIMIT, accessToken);
        byte[] qrCodeJson = doPostSpecial(url, params);
        // 2.若获取小程序码错误,将返回json格式的对象(而不是文件流)
        String resStr = new String(qrCodeJson, StandardCharsets.UTF_8);
        if (resStr.contains("errcode")) {
            //报token问题
            if (JSONObject.parseObject(resStr).getString("errcode").equals(Constants.STR_40001)) {
                //删除redis缓存重新生成
                qrCodeJson = doPostSpecial(String.format(WechatApiConstant.GET_WX_A_CODE_UNLIMIT, getAccessTokenCleanRedis(accessTokenType)),
                        params);

            } else {
                log.error("生成小程序码失败，参数：{},exception：{}",JSONObject.toJSONString(request),resStr);
                return null;
            }
        }
        return qrCodeJson;
    }

    /**
     * get请求，参数拼接在地址上
     *
     * @param url 请求地址加参数
     * @return 字符串类型的返回值
     */
    public String doGet(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(get);
            if (response != null && response.getStatusLine().getStatusCode() == Constants.NUM_200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("get请求，参数拼接在地址上异常", e);
            }
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url     请求地址
     * @param jsonStr json格式的参数
     * @return JSONObject返回对象
     */
    public JSONObject doPost(String url, String jsonStr) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionTimeToLive(2, TimeUnit.SECONDS).build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
            HttpResponse res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("post请求异常", e);
            }
        }
        return response;
    }

    /**
     * post请求
     *
     * @param url     请求地址
     * @param jsonStr json格式的参数
     * @return JSONObject返回对象
     */
    public JSONObject doPost(String url, String jsonStr, String charset) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            post.addHeader("Content-type", "application/json; charset="+charset);
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(jsonStr, Charset.forName(charset)));
            HttpResponse res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity(), charset);
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("post请求异常", e);
            }
        }
        return response;
    }

    /**
     * post请求
     * 为了小程序码,单独写的方法,因为小程序码接口返回的是文件流
     *
     * @param url     请求地址
     * @param jsonStr json格式的参数
     * @return 字节数组
     */
    private byte[] doPostSpecial(String url, String jsonStr) {
        log.info("http post request:{}",jsonStr);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        byte[] out = null;
        try {
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
            HttpResponse res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                if (entity.isStreaming()) {
                    InputStream input = entity.getContent();
                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = input.read(buff)) != -1) {
                        byteOut.write(buff, 0, len);
                    }
                    out = byteOut.toByteArray();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("doPostSpecial异常", e);
            }
        }
        return out;
    }
    /**
     * 获取二维码图片（缓存）
     * @param request
     * @return
     */
    private String getQrImageCache(MiniProgramQrCodeRequest request,String code){
        String key = Objects.toString(request.getPage()).concat(Objects.toString(code));
        return redisService.hget(RedisKeyConstant.QR_CODE_CACHE, key);
    }

    /**
     * 更新二维码图片（缓存）
     * @param request
     * @param imageUrl
     */
    private void updateQrImageCache(MiniProgramQrCodeRequest request, String imageUrl,String code){
        String key = Objects.toString(request.getPage()).concat(Objects.toString(code));
        boolean isNew = true;
        if(redisService.hasKey(RedisKeyConstant.QR_CODE_CACHE)){
            isNew = false;
        }
        redisService.hset(RedisKeyConstant.QR_CODE_CACHE, key, imageUrl);
        if(isNew) {
            //新增的设定5500秒失效，跟着token失效时间一样
            redisService.expireBySeconds(RedisKeyConstant.QR_CODE_CACHE, 6000L);
        }
    }


    /**
     * 查询运单物流轨迹信息（trace_waybill）
     *
     * @param accessToken 请求token
     * @param traceWaybillRequest     微信物流传运单接口请求
     * @return 返回获取到的 waybill_token 字符串
     */
    public String traceWaybill(String accessToken, TraceWaybillRequest traceWaybillRequest) {
        String url = "https://api.weixin.qq.com/cgi-bin/express/delivery/open_msg/trace_waybill?access_token=" + accessToken;

        JSONObject response = doPost(url, JSONObject.toJSONString(traceWaybillRequest));
        if (response != null && response.containsKey("errcode")) {
            int errcode = response.getIntValue("errcode");
            if (errcode == 0) {
                return response.getString("waybill_token");
            } else {
                log.error("查询运单轨迹失败，错误码: {}, 错误信息: {}", errcode, response.getString("errmsg"));
                return "";
            }
        }
        return null;
    }

    /**
     * 查询运单 status 接口
     *
     * @param accessToken 请求token
     * @param openId 用户 OpenID
     * @param waybillToken 查询id
     * @return 返回运单的物流订单状态（如 4 表示已签收）
     */
    public Integer queryTraceStatus(String accessToken, String openId, String waybillToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/express/delivery/open_msg/query_trace?access_token=" + accessToken;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("openid", openId);
        requestBody.put("waybill_token", waybillToken);

        JSONObject response = doPost(url, com.alibaba.fastjson2.JSONObject.toJSONString(requestBody));
        if (response != null && response.containsKey("errcode")) {
            int errcode = response.getIntValue("errcode");
            if (errcode == 0) {
                JSONObject waybillInfo = response.getJSONObject("waybill_info");
                return waybillInfo.getInteger("status");
            } else {
                log.error("查询运单状态失败，错误码: {}, 错误信息: {}", errcode, response.getString("errmsg"));
                return -1;
            }
        }
        return null;
    }

}
