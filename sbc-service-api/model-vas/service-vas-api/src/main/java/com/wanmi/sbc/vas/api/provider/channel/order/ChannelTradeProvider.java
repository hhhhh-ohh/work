package com.wanmi.sbc.vas.api.provider.channel.order;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderCompensateRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderFreightRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderSplitRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderVerifyRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderCompensateResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderFreightResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderSplitResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderVerifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 订单服务接口
 * @author daiyitian
 * @date 2021/5/21 20:53
 */
@FeignClient(value = "${application.vas.name}", contextId = "ChannelTradeProvider")
public interface ChannelTradeProvider {

    /**
     * 订单商品状态验证
     *
     * @param request 订单信息 {@link ChannelOrderVerifyRequest}
     * @return 订单列表 {@link ChannelOrderVerifyResponse}
     */
    @PostMapping("/channel/${application.vas.version}/order/order-verify")
    BaseResponse<ChannelOrderVerifyResponse> verifyOrder(
            @RequestBody @Valid ChannelOrderVerifyRequest request);

    /**
     * 订单拆分
     *
     * @param request 供应商订单信息 {@link ChannelOrderVerifyRequest}
     * @return 拆分后的订单列表 {@link ChannelOrderSplitResponse}
     */
    @PostMapping("/channel/${application.vas.version}/order/order-split")
    BaseResponse<ChannelOrderSplitResponse> splitOrder(
            @RequestBody @Valid ChannelOrderSplitRequest request);

    /**
     * 订单状态补偿
     *
     * @param request 第三方订单信息 {@link ChannelOrderCompensateRequest}
     * @return 补偿结果 {@link ChannelOrderCompensateResponse}
     */
    @PostMapping("/channel/${application.vas.version}/order/order-compensate")
    BaseResponse<ChannelOrderCompensateResponse> compensateOrder(
            @RequestBody @Valid ChannelOrderCompensateRequest request);

    /**
     * 订单状态补偿
     *
     * @param request 第三方订单信息 {@link ChannelOrderFreightRequest}
     * @return 补偿结果 {@link ChannelOrderFreightResponse}
     */
    @PostMapping("/channel/${application.vas.version}/order/query-freight")
    BaseResponse<ChannelOrderFreightResponse> queryFreight(
            @RequestBody @Valid ChannelOrderFreightRequest request);
}
