package com.wanmi.sbc.third.login.util;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: sbc-micro-service
 * @description:
 * @create: 2020-05-08 10:56
 **/
@Slf4j
public class WeChatClient {
    /**
     * 微信api接口网址
     */
    private final static String API_URL = "https://api.weixin.qq.com";

//    private final static List<Long> WXErrCodeList = Arrays.asList(40013L, 41002L);

    /**
     * 微信小程序appId
     */
    private String appId;
    /**
     * 微信小程序密钥
     */
    private String appSecret;

    public WeChatClient(){}

    public WeChatClient(String appId,String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public WeChatSession authorize(final String code) {
        final String path = "/sns/jscode2session";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("appid", appId);
        parameters.put("secret", appSecret);
        log.info("微信小程序信息："+"appId: {}  \nappSecret：{}", appId, appSecret);
        parameters.put("js_code", code);
        final String response = HttpUtils.get(API_URL + path, String.class, parameters);
        log.info("authorize response = "+response);
        final WeChatSession session = JSON.parseObject(response, WeChatSession.class);
        if (null != session.getErrCode()) {
            log.error("微信授权失败, session={}", session);
            //throw new BusinessException("微信授权失败，请重新授权");
            if (Long.valueOf(40013L).equals(session.getErrCode())
                    || Long.valueOf(41002L).equals(session.getErrCode())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
        }
        return session;
    }
}