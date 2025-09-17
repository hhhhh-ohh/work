package com.wanmi.sbc.empower.provider.impl.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderConfirmReceivedRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderCreateRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderPayRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderQuerySkuListRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderCreateResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderQuerySkuListResponse;
import com.wanmi.sbc.empower.channel.base.ChannelOrderBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @description 渠道订单服务实现
 * @author daiyitian
 * @date 2021/5/10 17:22
 */
@RestController
@Validated
@Slf4j
public class ChannelOrderController implements ChannelOrderProvider {

    @Autowired private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse<ChannelOrderCreateResponse> create(
            @RequestBody @Valid ChannelOrderCreateRequest request) {
        ThirdPlatformType type = request.getChannelTrade().getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelOrderBaseService.class, type)
                        .create(request));
    }

    @Override
    public BaseResponse pay(@RequestBody @Valid ChannelOrderPayRequest request) {
        log.info("渠道订单开始pay,id="+request.getChannelTrade().getId());
        ThirdPlatformType type = request.getChannelTrade().getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelOrderBaseService.class, type)
                        .pay(request));
    }

    @Override
    public BaseResponse confirmReceived(
            @RequestBody @Valid ChannelOrderConfirmReceivedRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelOrderBaseService.class, type)
                        .confirmReceived(request));
    }

    @Override
    public BaseResponse<ChannelOrderQuerySkuListResponse> batchQueryOrderSkuList(ChannelOrderQuerySkuListRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelOrderBaseService.class, type)
                        .batchQueryOrderSkuList(request));
    }
}
