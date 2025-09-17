package com.wanmi.sbc.empower.pay.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;

/**
 * 对接第三方支付通用接口
 * @author zhanghao
 */
public interface PayBaseService {

    /****
     * 获得支付单详情
     * @param request
     * @return
     */
    BaseResponse getPayOrderDetail(PayOrderDetailRequest request);

    /***
     * 关闭支付单
     * @param request
     * @return
     */
    BaseResponse payCloseOrder(PayCloseOrderRequest request);

    /***
     * 支付退款
     * @param request
     * @return
     */
    BaseResponse payRefund(PayRefundBaseRequest request);


    BaseResponse pay(BasePayRequest basePayRequest);
}
