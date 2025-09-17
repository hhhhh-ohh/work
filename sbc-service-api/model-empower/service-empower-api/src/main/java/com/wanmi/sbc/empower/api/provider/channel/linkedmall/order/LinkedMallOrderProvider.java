package com.wanmi.sbc.empower.api.provider.channel.linkedmall.order;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallLogisticsQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallOrderListQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallRenderOrderRequest;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallLogisticsQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallOrderListQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallRenderOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: yhy
 * \* Date: 2020-8-10
 * \* Time: 17:33
 */
@FeignClient(value = "${application.empower.name}",contextId = "LinkedMallOrderProvider")
public interface LinkedMallOrderProvider {


    /**
     * 渲染订单：是否可售、不可售原因、配送方式、邮费
     * @param linkedMallRenderOrderRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/linkedmall/order/init-render-order")
    BaseResponse<LinkedMallRenderOrderResponse> renderOrder(@RequestBody @Valid LinkedMallRenderOrderRequest linkedMallRenderOrderRequest);

    /**
     * linkedMall 根据筛选条件查询订单详情
     * @param linkedMallOrderListQueryRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/linkedmall/order/query-order-detail")
    BaseResponse<LinkedMallOrderListQueryResponse> queryOrderDetail(@RequestBody @Valid LinkedMallOrderListQueryRequest linkedMallOrderListQueryRequest);

    /**
     * linkedMall 订单物流查询
     * @param sbcLogisticsQueryRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/linkedmall/order/get-order-logistics")
    BaseResponse<LinkedMallLogisticsQueryResponse> getOrderLogistics(@RequestBody @Valid LinkedMallLogisticsQueryRequest sbcLogisticsQueryRequest);


}
