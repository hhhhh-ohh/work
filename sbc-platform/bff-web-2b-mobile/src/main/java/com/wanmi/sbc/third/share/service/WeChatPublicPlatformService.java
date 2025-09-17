package com.wanmi.sbc.third.share.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SpecialSymbols;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatshareset.WechatShareSetQueryProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.QueryWechatConfigByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.wechatshareset.WechatShareSetInfoRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.WechatConfigResponse;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetResponse;
import com.wanmi.sbc.empower.api.response.wechatshareset.WechatShareSetInfoResponse;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.empower.bean.vo.WechatConfigVO;
import com.wanmi.sbc.third.share.request.WeChatSignRequest;
import com.wanmi.sbc.third.share.response.WeChatTicketResponse;
import com.wanmi.sbc.third.wechat.WechatSetService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhanggaolei
 * @className WeChatPublicPlatformService
 * @description
 * @date 2022/5/23 16:56
 */
@Slf4j
@Service
public class WeChatPublicPlatformService {

    @Autowired private RestTemplate restTemplate;

    @Autowired private RedisUtil redisService;

    @Autowired private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired private WechatShareSetQueryProvider wechatShareSetQueryProvider;

    @Autowired private WechatLoginSetQueryProvider wechatLoginSetQueryProvider;

    @Autowired private MiniProgramSetQueryProvider miniProgramSetQueryProvider;

    @Autowired private WechatSetService wechatSetService;

    private static String TICKET_KEY =
            CacheKeyConstant.WE_CHAT + SpecialSymbols.COLON.toValue() + "TICKET";

    /**
     * 通过缓存获取
     *
     * @param strTerminalType
     * @return
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'wechatTicket:'+#strTerminalType")
    public WeChatSignRequest getTicketKey(String strTerminalType) {
        String appId = null;
        String appSecret = null;

        TerminalType terminalType = null;
        if (StringUtils.isNotBlank(strTerminalType)) {
            terminalType = TerminalType.valueOf(strTerminalType);
        }
        // 小程序设置
        if (TerminalType.MINI.equals(terminalType)) {
            BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse =
                    miniProgramSetQueryProvider.getByType(
                            MiniProgramSetByTypeRequest.builder()
                                    .type(Constants.ZERO)
                                    .build());
            if (StringUtils.equals(
                    CommonErrorCodeEnum.K000000.getCode(),
                    miniProgramSetByTypeResponseBaseResponse.getCode())) {
                MiniProgramSetVO miniProgramSetVO =
                        miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
                if (Constants.no.equals(miniProgramSetVO.getStatus())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                appId = miniProgramSetVO.getAppId();
                appSecret = miniProgramSetVO.getAppSecret();
            }
        } // H5
        else if (TerminalType.H5.equals(terminalType)) {
            WechatLoginSetResponse setResponse = wechatLoginSetQueryProvider.getInfo().getContext();
            if (setResponse != null
                    && DefaultFlag.YES.equals(setResponse.getMobileServerStatus())) {
                WechatConfigResponse wechatConfigResponse =
                        paySettingQueryProvider
                                .getWechatConfigByStoreId(new QueryWechatConfigByStoreIdRequest(Constants.BOSS_DEFAULT_STORE_ID)).getContext();
                if (Objects.nonNull(wechatConfigResponse)
                        && CollectionUtils.isNotEmpty(wechatConfigResponse.getConfigVOList())) {
                    WechatConfigVO wechatConfigVO =
                            wechatConfigResponse.getConfigVOList().stream()
                                    .filter(
                                            v ->
                                                    Objects.equals(
                                                            NumberUtils.INTEGER_ZERO,
                                                            v.getSceneType()))
                                    .findFirst()
                                    .orElse(null);
                    if (Objects.nonNull(wechatConfigVO)) {
                        appId = wechatConfigVO.getAppId();
                        appSecret = wechatConfigVO.getSecret();
                    }
                }
            }
        } else {
            WechatShareSetInfoResponse infoResponse =
                    wechatShareSetQueryProvider
                            .getInfo(WechatShareSetInfoRequest.builder().build())
                            .getContext();
            appId = infoResponse.getShareAppId();
            appSecret = infoResponse.getShareAppSecret();
        }

        if (appId == null) {
            return null;
        }

        String ticket = this.getTicket(wechatSetService.getToken(appId, appSecret));

        return WeChatSignRequest.builder().ticket(ticket).appId(appId).build();
    }

    private String getTicket(String token) {
        String ticket = redisService.getString(TICKET_KEY);
        if (!StringUtils.isBlank(ticket)) {
            return ticket;
        }
        Map<String, String> map = new HashMap<>();
        map.put("access_token", token);
        WeChatTicketResponse response =
                restTemplate.getForObject(
                        "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={access_token}&type=jsapi",
                        WeChatTicketResponse.class,
                        map);
        if (response == null || response.getErrcode() != 0) {
            log.error("获得微信ticket异常：请求参数：{}", map);
            throw new RuntimeException("获得微信ticket异常");
        }
        ticket = response.getTicket();
        redisService.setString(TICKET_KEY, ticket, response.getExpires_in() - 200L);
        return ticket;
    }
}
