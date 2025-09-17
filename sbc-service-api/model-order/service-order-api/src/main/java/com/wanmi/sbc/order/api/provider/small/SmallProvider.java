package com.wanmi.sbc.order.api.provider.small;


import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.small.ReturnSmallOrderRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>调用small平台Provider</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@FeignClient(value = "${application.order.name}", contextId = "SmallProvider")
public interface SmallProvider {

    /**
     * 测试samll下单
     *
     * @author lq
     * @return 测试samll下单 {@link }
     */

    @PostMapping("/order/${application.order.version}/small/test-create-small-order")
    BaseResponse testCreateSmallOrder(@RequestBody @Valid TradeGetByIdRequest tradeGetByIdRequest);


    /**
     * mq消息推送订单到h5 创单
     *
     * @author lfx
     * @return
     */

    @PostMapping("/order/${application.order.version}/small/create-h5-order")
    BaseResponse createH5OrderByTid(@RequestParam("tid") String  tid);

    /**
     * mq消息推送退款订单到h5 修改订单状态
     *
     * @author lfx
     * @return
     */

    @PostMapping("/order/${application.order.version}/small/refund-h5-order-by-refund-id")
    BaseResponse refundH5OrderByRefundId(@RequestParam("refundId") String  refundId);
    /**
     * small订单退款
     *
     * @author lq
     * @return small订单退款 {@link }
     */

    @PostMapping("/order/${application.order.version}/small/return-small-order")
    BaseResponse returnSmallOrder(@RequestBody @Valid ReturnSmallOrderRequest request);

    @PostMapping("/order/${application.order.version}/small/get-full-gift-num")
    BaseResponse getFullGiftNum(@RequestParam("phone") String phone);
    @PostMapping("/order/${application.order.version}/small/check-full-gift")
    BaseResponse checkFullGift(@RequestParam("phone") String phone);


    @PostMapping("/order/${application.order.version}/small/checkOrderIsAfterSale")
    BaseResponse<Boolean> checkOrderIsAfterSale(@RequestParam("orderSn") String orderSn);
}
