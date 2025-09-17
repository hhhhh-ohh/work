package com.wanmi.sbc.order.api.provider.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.growthvalue.OrderGrowthValueTempQueryRequest;
import com.wanmi.sbc.order.api.request.mqconsumer.OrderMqConsumerRequest;
import com.wanmi.sbc.order.api.response.growthvalue.OrderGrowthValueTempPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className OrderMqConsumerProvider
 * @description mq消费接口
 * @date 2021/8/13 5:24 下午
 **/
@FeignClient(value = "${application.order.name}", contextId = "OrderMqConsumerProvider")
public interface OrderMqConsumerProvider {

    /**
     * @description 业务员交接
     * @author  lvzhenwei
     * @date 2021/8/17 9:44 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/mq/consumer/modify-employee-data")
    BaseResponse modifyEmployeeData(@RequestBody @Valid OrderMqConsumerRequest request);

    /**
     * @description 取消订单
     * @author  lvzhenwei
     * @date 2021/8/17 9:43 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/mq/consumer/cancel-order")
    BaseResponse cancelOrder(@RequestBody @Valid OrderMqConsumerRequest request);

    /**
     * @description 开团提醒
     * @author  lvzhenwei
     * @date 2021/8/17 11:38 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/mq/consumer/open-groupon")
    BaseResponse openGroupon(@RequestBody @Valid OrderMqConsumerRequest request);

    /**
     * @description 拼团订单-支付成功，订单异常，自动退款
     * @author  lvzhenwei
     * @date 2021/8/17 2:20 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/mq/consumer/groupon-order-pay-success-auto-refund")
    BaseResponse grouponOrderPaySuccessAutoRefund(@RequestBody @Valid OrderMqConsumerRequest request);

    /**
     * @description  参团人数延迟消息处理
     * @author  lvzhenwei
     * @date 2021/8/17 2:40 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/mq/consumer/groupon-num-limit")
    BaseResponse grouponNumLimit(@RequestBody @Valid OrderMqConsumerRequest request);

    /**
     * @description 异常订单发送限售信息 ——（取消订单，超时未支付，退货，退款，审批未通过）
     * @author  lvzhenwei
     * @date 2021/8/17 2:49 下午
     * @param request 
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/order/${application.order.version}/mq/consumer/reduce-restricted-purchase-num")
    BaseResponse reduceRestrictedPurchaseNum(@RequestBody @Valid OrderMqConsumerRequest request);



    @PostMapping("/order/${application.order.version}/mq/consumer/deal-order-points-increase")
    BaseResponse dealOrderPointsIncrease(@RequestBody @Valid OrderMqConsumerRequest request);

}
