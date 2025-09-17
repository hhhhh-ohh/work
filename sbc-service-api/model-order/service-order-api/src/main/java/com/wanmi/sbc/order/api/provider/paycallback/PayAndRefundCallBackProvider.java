package com.wanmi.sbc.order.api.provider.paycallback;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.CreditCallBackRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.order.name}", contextId = "PayCallBackProvider")
public interface PayAndRefundCallBackProvider {

    /**
     * 订单支付回调处理
     *
     * @param tradePayOnlineCallBackRequest 订单信息 {@link TradePayOnlineCallBackRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/pay-call-back")
    BaseResponse payCallBack(@RequestBody @Valid TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest);

    /**
     * 订单支付回调处理
     *
     * @param tradeRefundOnlineCallBackRequest 订单信息 {@link TradePayOnlineCallBackRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/refund-call-back")
    BaseResponse refundCallBack(@RequestBody @Valid TradeRefundOnlineCallBackRequest tradeRefundOnlineCallBackRequest);

    /**
     * 授信支付回调处理
     *
     * @param creditCallBackRequest 订单信息 {@link CreditCallBackRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/credit-call-back")
    BaseResponse creditCallBack(@RequestBody @Valid CreditCallBackRequest creditCallBackRequest);
}
