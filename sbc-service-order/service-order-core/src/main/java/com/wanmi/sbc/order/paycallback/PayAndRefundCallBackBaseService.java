package com.wanmi.sbc.order.paycallback;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;


public interface PayAndRefundCallBackBaseService {

    /****
     * 支付回调
     * @param tradePayOnlineCallBackRequest
     * @return
     */
    BaseResponse payCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest);

    /****
     * 退款回调
     * @param tradeRefundOnlineCallBackRequest
     * @return
     */
    BaseResponse refundCallBack(TradeRefundOnlineCallBackRequest tradeRefundOnlineCallBackRequest);

}
