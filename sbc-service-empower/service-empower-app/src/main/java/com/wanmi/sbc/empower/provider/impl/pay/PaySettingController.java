package com.wanmi.sbc.empower.provider.impl.pay;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingProvider;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemSaveRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.*;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.service.PayDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * <p>支付操作接口实现</p>
 * Created by of628-wenzhi on 2018-08-18-下午4:41.
 */
@RestController
@Validated
@Slf4j
public class PaySettingController implements PaySettingProvider {

    @Autowired
    private PayDataService payDataService;

    @Override
    public BaseResponse addGateway(@RequestBody @Valid GatewayAddRequest gatewayAddRequest) {
        PayGateway gateway = PayGateway.builder()
                .createTime(LocalDateTime.now())
                .isOpen(IsOpen.YES)
                .name(gatewayAddRequest.getGatewayEnum())
                .type(gatewayAddRequest.getType())
                .build();
        payDataService.saveGateway(gateway);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyGateway(@RequestBody @Valid GatewayModifyRequest gatewayModifyRequest) {
        PayGateway gateway = PayGateway.builder()
                .isOpen(gatewayModifyRequest.getIsOpen())
                .name(gatewayModifyRequest.getGatewayEnum())
                .id(gatewayModifyRequest.getId())
                .type(gatewayModifyRequest.getType())
                .build();
        payDataService.modifyGateway(gateway);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse saveChannelItem(@RequestBody @Valid ChannelItemSaveRequest channelItemSaveRequest) {
        PayChannelItem payChannelItem = KsBeanUtil.copyPropertiesThird(channelItemSaveRequest, PayChannelItem.class);
        if (payChannelItem.getId() == null || payChannelItem.getCreateTime() == null) {
            payChannelItem.setCreateTime(LocalDateTime.now());
            payChannelItem.setIsOpen(channelItemSaveRequest.getIsOpen());
        }
        payChannelItem.setGateway(PayGateway.builder().id(channelItemSaveRequest.getGatewayId()).build());
        payDataService.saveItem(payChannelItem);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse saveGatewayConfig(@RequestBody @Valid GatewayConfigSaveRequest gatewayConfigSaveRequest) {
        PayGatewayConfig config = KsBeanUtil.copyPropertiesThird(gatewayConfigSaveRequest, PayGatewayConfig.class);
        if (config.getId() == null || config.getCreateTime() == null) {
            config.setCreateTime(LocalDateTime.now());
        }
        config.setPayGateway(PayGateway.builder().id(gatewayConfigSaveRequest.getGatewayId()).build());
        payDataService.saveConfig(config);
        return BaseResponse.SUCCESSFUL();
    }



    @Override
    public BaseResponse savePayGateway(@RequestBody @Validated PayGatewaySaveRequest payGatewaySaveRequest) {
        payDataService.savePayGateway(payGatewaySaveRequest);
        // 判断是否授信支付，如果是直接返回
        if(PayGatewayEnum.CREDIT.toValue().equals(payGatewaySaveRequest.getName())){
            payDataService.saveCreditName(payGatewaySaveRequest.getAlias());
        }
        if (PayGatewayEnum.LAKALA.toValue().equals(payGatewaySaveRequest.getName())) {
            payDataService.setCache(payGatewaySaveRequest.getId());
        }
        if (PayGatewayEnum.LAKALACASHIER.toValue().equals(payGatewaySaveRequest.getName())) {
            payDataService.setLklCasherCache(payGatewaySaveRequest.getId());
        }
        return BaseResponse.SUCCESSFUL();
    }



    @Override
    public BaseResponse savePayGatewayByTerminalType(@RequestBody @Valid PayGatewaySaveByTerminalTypeRequest request) {
        payDataService.savePayGatewayByTerminalType(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateWechatConfig(@RequestBody @Valid WechatConfigSaveRequest request) {
        payDataService.updatePayWechatConfig(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse saveWechatConfig(@Valid WechatConfigSaveRequest request) {
        payDataService.savePayWechatConfig(request);
        return BaseResponse.SUCCESSFUL();
    }


}
