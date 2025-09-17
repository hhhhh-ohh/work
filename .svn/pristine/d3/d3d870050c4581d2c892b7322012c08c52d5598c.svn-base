package com.wanmi.sbc.empower.api.provider.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderConfirmReceivedRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderCreateRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderPayRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderQuerySkuListRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderCreateResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderQuerySkuListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author daiyitian
 * @description 渠道订单服务Provider
 * @date 2021/5/10 15:58
 */
@FeignClient(value = "${application.empower.name}", contextId = "ChannelOrderProvider")
public interface ChannelOrderProvider {

    /**
     * 订单创建
     *
     * @param request 订单信息 {@link ChannelOrderCreateRequest}
     * @return 第三方订单号 {@link ChannelOrderCreateResponse}
     */
    @PostMapping("/channel/${application.empower.version}/order/create")
    BaseResponse<ChannelOrderCreateResponse> create(
            @RequestBody @Valid ChannelOrderCreateRequest request);

    /**
     * 订单支付
     *
     * @param request 第三方订单号 {@link ChannelOrderPayRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/channel/${application.empower.version}/order/pay")
    BaseResponse pay(@RequestBody @Valid ChannelOrderPayRequest request);

    /**
     * 确认收货
     *
     * @param request 第三方订单号 {@link ChannelOrderConfirmReceivedRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/channel/${application.empower.version}/order/confirm-received")
    BaseResponse confirmReceived(@RequestBody @Valid ChannelOrderConfirmReceivedRequest request);

    /**
     * 查询订单SKU列表
     *
     * @param request
     * @return
     */
    @PostMapping("/channel/${application.empower.version}/order/batch-query-order-sku-list")
    BaseResponse<ChannelOrderQuerySkuListResponse> batchQueryOrderSkuList(
            @RequestBody @Valid ChannelOrderQuerySkuListRequest request);
}
