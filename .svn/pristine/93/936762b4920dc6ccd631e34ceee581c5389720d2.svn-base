package com.wanmi.sbc.order.provider.impl.paycallback;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.paycallback.PayAndRefundCallBackProvider;
import com.wanmi.sbc.order.api.request.trade.CreditCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.paycallback.PayAndRefundCallBackBaseService;
import com.wanmi.sbc.order.paycallback.PayAndRefundCallBackServiceFactory;
import com.wanmi.sbc.order.paycallback.PayCallBackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Validated
public class PayAndRefundCallBackController implements PayAndRefundCallBackProvider {

    @Autowired
    private PayAndRefundCallBackServiceFactory payAndRefundCallBackServiceFactory;

    @Autowired
    private PayCallBackUtil payCallBackUtil;

    @Override
    public BaseResponse payCallBack(@RequestBody @Valid TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        PayAndRefundCallBackBaseService payAndRefundCallBackBaseService =
                payAndRefundCallBackServiceFactory.create(tradePayOnlineCallBackRequest.getPayCallBackServiceType());
        return payAndRefundCallBackBaseService.payCallBack(tradePayOnlineCallBackRequest);
    }

    @Override
    public BaseResponse refundCallBack(@RequestBody @Valid TradeRefundOnlineCallBackRequest tradeRefundOnlineCallBackRequest) {
        PayAndRefundCallBackBaseService payAndRefundCallBackBaseService =
                payAndRefundCallBackServiceFactory.create(tradeRefundOnlineCallBackRequest.getPayCallBackServiceType());
        return payAndRefundCallBackBaseService.refundCallBack(tradeRefundOnlineCallBackRequest);
    }

    @Override
    public BaseResponse creditCallBack(@RequestBody @Valid CreditCallBackRequest creditCallBackRequest) {
        payCallBackUtil.creditRepayCallbackOnline(creditCallBackRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
