package com.wanmi.sbc.empower.provider.impl.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelRefundProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundApplyRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundCancelRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundQueryStatusRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundReasonRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundReasonResponse;
import com.wanmi.sbc.empower.channel.base.ChannelOrderBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelRefundBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanwei
 * @className ChannelRefundController
 * @description TODO
 * @date 2021/5/29 15:13
 **/
@RestController
public class ChannelRefundController implements ChannelRefundProvider {

    @Autowired private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse<ChannelRefundReasonResponse> listRefundReason(ChannelRefundReasonRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelRefundBaseService.class, type)
                        .listRefundReason(request));
    }

    @Override
    public BaseResponse applyRefund(ChannelRefundApplyRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelRefundBaseService.class, type)
                        .applyRefund(request));
    }

    @Override
    public BaseResponse cancelRefund(ChannelRefundCancelRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelRefundBaseService.class, type)
                        .cancelRefund(request));
    }

    @Override
    public BaseResponse<ChannelRefundQueryStatusResponse> queryRefundStatus(ChannelRefundQueryStatusRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelRefundBaseService.class, type)
                        .queryRefundStatus(request));
    }
}