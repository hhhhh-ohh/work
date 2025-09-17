package com.wanmi.sbc.third;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatloginset.WechatLoginSetQueryProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetListRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.api.response.wechatloginset.WechatLoginSetResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.setting.api.response.wechat.WechatSetResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private MiniProgramSetQueryProvider miniProgramSetQueryProvider;

    /**
     * 获取所有微信配置
     * @return
     */
    public WechatSetResponse get(List<TerminalType> terminalTypes){
        if(CollectionUtils.isEmpty(terminalTypes)){
            return null;
        }
        WechatSetResponse response = new WechatSetResponse();

        //获取PC的配置和开关  H5/APP的开关
        if(terminalTypes.contains(TerminalType.PC) || terminalTypes.contains(TerminalType.H5) || terminalTypes.contains(TerminalType.APP)) {
            WechatLoginSetResponse setResponse = wechatLoginSetQueryProvider.getInfo().getContext();
            KsBeanUtil.copyPropertiesThird(setResponse, response);
        }

        //填充H5/app的appId、密钥
        if(terminalTypes.contains(TerminalType.H5) || terminalTypes.contains(TerminalType.APP)) {
            PayGatewayConfigResponse payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(new
                    GatewayConfigByGatewayRequest(PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
            response.setMobileAppId(payGatewayConfig.getAppId());
            response.setMobileAppSecret(payGatewayConfig.getSecret());
            response.setAppAppId(payGatewayConfig.getOpenPlatformAppId());
            response.setAppAppSecret(payGatewayConfig.getOpenPlatformSecret());
        }

        //填充小程序的appId、密钥、开关
        if(terminalTypes.contains(TerminalType.MINI)) {
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
     * 获取微信设置
     * @param terminalType 类型
     * @return 设置信息
     */
    public MiniProgramSetVO getBySet(TerminalType terminalType) {
        MiniProgramSetListRequest typeRequest = new MiniProgramSetListRequest();
        typeRequest.setDelFlag(DeleteFlag.NO);
        typeRequest.setStatus(Constants.yes);
        if(TerminalType.MINI == terminalType) {
            typeRequest.setType(Constants.ZERO);
        }
        return miniProgramSetQueryProvider.list(typeRequest).getContext().getMiniProgramSetVOList().stream().findFirst().orElse(new MiniProgramSetVO());
    }
}
