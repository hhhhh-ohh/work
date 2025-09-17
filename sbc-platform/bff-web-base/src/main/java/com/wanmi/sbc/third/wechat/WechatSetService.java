package com.wanmi.sbc.third.wechat;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetQueryProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetListRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.QueryWechatConfigByStoreIdRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.WechatConfigResponse;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetResponse;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.setting.api.response.wechat.WechatSetResponse;
import com.wanmi.sbc.third.wechat.response.WeChatTokenResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 商品缓存服务
 * Created by daiyitian on 2017/4/11.
 */
@Slf4j
@Service
public class WechatSetService {


    @Autowired
    private WechatLoginSetQueryProvider wechatLoginSetQueryProvider;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private MiniProgramSetQueryProvider miniProgramSetQueryProvider;

    private static String TOKEN_KEY = CacheKeyConstant.WE_CHAT + SpecialSymbols.COLON.toValue() + "TOKEN";


    /**
     * 获取状态
     * @param terminalType 终端类型
     * @return 状态
     */
    public DefaultFlag getStatus(TerminalType terminalType){
        if(TerminalType.APP.equals(terminalType)){
            return this.get(TerminalType.APP).getAppServerStatus();
        }else if(TerminalType.MINI.equals(terminalType)){
            return this.get(TerminalType.MINI).getMiniProgramServerStatus();
        }else if(TerminalType.PC.equals(terminalType)){
            return this.get(TerminalType.PC).getPcServerStatus();
        }
        return this.get(TerminalType.H5).getMobileServerStatus();
    }

    /**
     * 获取微信设置配置
     * @param terminalType 终端类型
     * @return
     */
    public WechatSetResponse get(TerminalType terminalType){
        return get(Collections.singletonList(terminalType));
    }

    /**
     * 获取微信设置配置
     * @return
     */
    public WechatSetResponse get(List<TerminalType> terminalTypes) {
        if (CollectionUtils.isEmpty(terminalTypes)) {
            return null;
        }
        WechatSetResponse response = new WechatSetResponse();

        //获取PC的配置和开关  H5/APP的开关
        if (terminalTypes.contains(TerminalType.PC) || terminalTypes.contains(TerminalType.H5) || terminalTypes.contains(TerminalType.APP)) {
            WechatLoginSetResponse setResponse = wechatLoginSetQueryProvider.getInfo().getContext();
            KsBeanUtil.copyPropertiesThird(setResponse, response);
        }

        //填充H5/app的appId、密钥
        if (terminalTypes.contains(TerminalType.H5) || terminalTypes.contains(TerminalType.APP)) {
            WechatConfigResponse payGatewayConfig = paySettingQueryProvider.getWechatConfigByStoreId(QueryWechatConfigByStoreIdRequest.builder().storeId(Constants.BOSS_DEFAULT_STORE_ID).build()).getContext();
            if (Objects.nonNull(payGatewayConfig) && CollectionUtils.isNotEmpty(payGatewayConfig.getConfigVOList())) {
                payGatewayConfig.getConfigVOList().stream().forEach(wechatConfigVO -> {
                    if (wechatConfigVO.getSceneType() == 0) {
                        response.setMobileAppId(wechatConfigVO.getAppId());
                        response.setMobileAppSecret(wechatConfigVO.getSecret());
                    } else if (wechatConfigVO.getSceneType() == 2) {
                        response.setAppAppId(wechatConfigVO.getAppId());
                        response.setAppAppSecret(wechatConfigVO.getSecret());
                    }
                });
            }
        }

        //填充小程序的appId、密钥、开关
        if (terminalTypes.contains(TerminalType.MINI)) {
            BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
                    .type(Constants.ZERO)
                    .build());
            if (StringUtils.equals(CommonErrorCodeEnum.K000000.getCode(),miniProgramSetByTypeResponseBaseResponse.getCode())) {
                MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
                response.setMiniProgramAppAppId(miniProgramSetVO.getAppId());
                response.setMiniProgramAppSecret(miniProgramSetVO.getAppSecret());
                response.setMiniProgramServerStatus(DefaultFlag.fromValue(miniProgramSetVO.getStatus()));
            }
        }
        return response;
    }




    /**
     * 获取token，另外也起到校验作用
     * @param appId appId
     * @param appSecret 密钥
     * @return
     */
    public String getToken(String appId, String appSecret) {
        String token = redisService.getString(TOKEN_KEY.concat(appId));
        if (StringUtils.isNotBlank(token)) {
            return token;
        }
        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        WeChatTokenResponse response = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}", WeChatTokenResponse.class, map);
        if (response == null || response.getErrcode() != null) {
            log.error("获得微信token异常：请求参数：{},response:{}", map, response);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        token = response.getAccess_token();
        redisService.setString(TOKEN_KEY.concat(appId), token, response.getExpires_in() - 200L);
        return token;
    }
}
