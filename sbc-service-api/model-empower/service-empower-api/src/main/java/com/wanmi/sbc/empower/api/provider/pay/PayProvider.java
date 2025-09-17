package com.wanmi.sbc.empower.api.provider.pay;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.*;
import com.wanmi.sbc.empower.api.request.pay.PayRequest;
import com.wanmi.sbc.empower.api.request.pay.RefundRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaPayVerifySignRequest;
import com.wanmi.sbc.empower.api.response.pay.RefundResponse;
import com.wanmi.sbc.empower.api.response.pay.WxPayV3CertificatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>支付服务Provider</p>
 * @author zhanghao
 * @date 2019-12-03 15:36:05
 */
@FeignClient(value = "${application.empower.name}",contextId = "PayProvider")
public interface PayProvider {


    /**
     * 查询支付单详情
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/get-pay-order-detail")
    BaseResponse getPayOrderDetail(@RequestBody PayOrderDetailRequest request);


    /**
     * 关闭支付单
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/pay-close-order")
    BaseResponse payCloseOrder(@RequestBody PayCloseOrderRequest request);


    /**
     * 退款
     * @param refundInfoRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/pay-refund")
    BaseResponse payRefund(@RequestBody PayRefundBaseRequest refundInfoRequest);


    /**
     * 支付
     * @param basePayRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/pay")
    BaseResponse pay(@RequestBody BasePayRequest basePayRequest);


    /**
     * 退款请求，当退款操作请求成功后，返回的退款对象
     *
     * @param refundRequest 支付请求对象 {@link PayRequest}
     * @return 退款对象
     */
    @PostMapping("/empower/${application.empower.version}/refund")
    BaseResponse<RefundResponse> commonRefund(@RequestBody @Valid RefundRequest refundRequest);

    /**
    *
     * @description   获取微信支付-V3 微信平台证书，方便支付回调和退款回调验签使用
     * @author  wur
     * @date: 2022/12/1 15:55
     * @param refundRequest
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/pay/wx_v3_certificates")
    BaseResponse<WxPayV3CertificatesResponse> getWxPayV3Certificates(@RequestBody @Valid WxPayV3CertificatesRequest refundRequest);

}
