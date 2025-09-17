package com.wanmi.sbc.pay;

import com.wanmi.sbc.order.api.provider.paycallback.PayAndRefundCallBackProvider;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName PayCallBackService
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/7/9 19:46
 **/
@Service
public class PayAndRefundCallBackTaskService {

    @Autowired
    private PayAndRefundCallBackProvider payCallBackProvider;

    @Async("payCallBack")
    public void payCallBack(TradePayOnlineCallBackRequest request){
        payCallBackProvider.payCallBack(request);
    }

    @Async("refundCallBack")
    public void refundCallBack(TradeRefundOnlineCallBackRequest request){
        payCallBackProvider.refundCallBack(request);
    }
}
