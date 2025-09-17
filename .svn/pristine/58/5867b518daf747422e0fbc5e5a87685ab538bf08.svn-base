package com.wanmi.sbc.third.share;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.setting.api.response.wechat.WechatSetResponse;
import com.wanmi.sbc.third.share.request.GetSignRequest;
import com.wanmi.sbc.third.share.request.WeChatSignRequest;
import com.wanmi.sbc.third.share.response.TicketResponse;
import com.wanmi.sbc.third.share.service.WeChatPublicPlatformService;
import com.wanmi.sbc.third.wechat.WechatSetService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/third/share")
@Tag(name = "WeChatPublicPlatformController", description = "微信公众平台Controller")
public class WeChatPublicPlatformController {



    @Autowired
    private WeChatPublicPlatformService weChatPublicPlatformService;



    @Autowired
    private WechatSetService wechatSetService;



    @Operation(summary = "获得微信sign")
    @RequestMapping(value = {"/weChat/getSign"}, method = RequestMethod.POST)
    public BaseResponse<TicketResponse> getSign(@Validated @RequestBody GetSignRequest request) {
//        //获取appId,secret
//        String appId = null;
//        String appSecret = null;
//
//        TerminalType terminalType = null;
//        if(StringUtils.isNotBlank(request.getTerminalType())) {
//            terminalType = TerminalType.valueOf(request.getTerminalType());
//        }
//        //小程序设置
//        if (TerminalType.MINI.equals(terminalType)) {
//            BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
//                    .type(BigDecimal.ROUND_UP)
//                    .build());
//            if (StringUtils.equals(CommonErrorCodeEnum.K000000.getCode(),miniProgramSetByTypeResponseBaseResponse.getCode())) {
//                MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
//                if (Constants.no.equals(miniProgramSetVO.getStatus())) {
//                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
//                }
//                appId = miniProgramSetVO.getAppId();
//                appSecret = miniProgramSetVO.getAppSecret();
//            }
//        } else if (TerminalType.H5.equals(terminalType)) { //H5
//            WechatLoginSetResponse setResponse = wechatLoginSetQueryProvider.getInfo().getContext();
//            if (setResponse != null && DefaultFlag.YES.equals(setResponse.getMobileServerStatus())) {
//                PayGatewayConfigResponse payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(
//                        new GatewayConfigByGatewayRequest(PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
//                appId = payGatewayConfig.getAppId();
//                appSecret = payGatewayConfig.getSecret();
//            }
//        } else {
//            WechatShareSetInfoResponse infoResponse = wechatShareSetQueryProvider.getInfo(WechatShareSetInfoRequest.builder().
//                    operatePerson(commonUtil.getOperatorId()).build()).getContext();
//            appId = infoResponse.getShareAppId();
//            appSecret = infoResponse.getShareAppSecret();
//        }
//
//        if(appId == null) {
//            return BaseResponse.success(new TicketResponse());
//        }
//
//        String ticket = this.getTicket(wechatSetService.getToken(appId,appSecret));
        WeChatSignRequest wechatSignRequest = weChatPublicPlatformService.getTicketKey(request.getTerminalType());
        if(wechatSignRequest==null || wechatSignRequest.getAppId()==null){
            return BaseResponse.success(new TicketResponse());
        }
        TicketResponse response = WeChatSign.sign(wechatSignRequest.getTicket(), request.getUrl());
        response.setAppId(wechatSignRequest.getAppId());
        return BaseResponse.success(response);

    }

    /**
     * 目前提供给APP的装载微信分享SDK，填充appId
     * @param terminalType
     * @return
     */
    @Operation(summary = "获取微信AppId，channel: APP")
    @Parameter(name = "channel", description = "类型终端", required = true)
    @RequestMapping(value = {"/weChat/{terminalType}"}, method = RequestMethod.GET)
    public BaseResponse<WechatSetResponse> getAppIdByTerminalType(@PathVariable String terminalType) {
        WechatSetResponse response = new WechatSetResponse();
        if (TerminalType.APP.name().equalsIgnoreCase(terminalType)) {
            WechatSetResponse setResponse = wechatSetService.get(TerminalType.APP);
            //防止泄露Secret
            response.setAppAppId(setResponse.getAppAppId());
            response.setAppServerStatus(setResponse.getAppServerStatus());
        }
        return BaseResponse.success(response);
    }






}
