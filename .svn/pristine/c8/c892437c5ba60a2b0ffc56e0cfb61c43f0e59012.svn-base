package com.wanmi.sbc.empower.api.provider.pay.weixin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.gateway.PayGatewayUploadPayCertificateRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformCompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 微信支付接口
 */
@FeignClient(value = "${application.empower.name}", contextId = "WxPayProvider")
public interface WxPayProvider {


    /**
     * 微信支付--微信企业付款到零钱
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wx-pay-company-payment")
    BaseResponse<WxPayCompanyPaymentRsponse> wxPayCompanyPayment(@RequestBody WxPayCompanyPaymentInfoRequest request);

    /**
     * 微信支付--微信转账到零钱
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wx-pay-supplier-transfer")
    BaseResponse<WxPaySupplierTransferRsponse> wxPaySupplierTransfer(@RequestBody WxPayCompanyPaymentInfoRequest request);


    /**
     * 微信支付--微信转账到零钱
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wx-pay-boss-transfer")
    BaseResponse<WxPayBossTransferRsponse> wxPayBossTransfer(@RequestBody WxPayCompanyPaymentInfoRequest request);


    /**
     * 获取微信退款详情
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/get-wx-refund-order-detail")
    BaseResponse<WxRefundOrderDetailReponse> getWxRefundOrderDetail(@RequestBody WxRefundOrderDetailRequest request);

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author lvzhenwei
     * @Description 上传微信支付证书
     * @Date 14:22 2019/5/7
     * @Param [request]
     **/
    @PostMapping("/empower/${application.empower.version}/upload-pay-certificate")
    BaseResponse uploadPayCertificate(@RequestBody PayGatewayUploadPayCertificateRequest request);

    /**
     * 微信支付--查询转账状态
     * @param queryRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wx-pay-supplier-transfer-query")
    BaseResponse<WxPaySupplierTransferQueryResponse> wxPaySupplierTransferQuery(@RequestBody WxPaySupplierTransferQueryRequest queryRequest);

    /**
     * 微信支付--查询转账明细
     * @param transferQueryRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wx-pay-supplier-transfer-detail-query")
    BaseResponse<WxPaySupplierTransferQueryDetailResponse> wxPaySupplierTransferDetailQuery(@RequestBody WxPaySupplierTransferQueryRequest transferQueryRequest);

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author lvzhenwei
     * @Description 微信支付--发货信息录入接口
     * @Date 14:22 2019/5/7
     * @Param [request]
     **/
    @PostMapping("/empower/${application.empower.version}/wx-pay-upload-shipping-info")
    BaseResponse wxPayUploadShippingInfo(@RequestBody WxPayUploadShippingInfoRequest request);

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author lvzhenwei
     * @Description 微信支付--发货信息录入查询订单接口
     * @Date 14:22 2019/5/7
     * @Param [request]
     **/
    @PostMapping("/empower/${application.empower.version}/wx-pay-upload-shipping-get-order")
    BaseResponse<Integer> wxPayUploadShippingGetOrder(@RequestBody WxPayUploadShippingInfoGetOrderRequest request);

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author dyt
     * @Description 微信支付--所有运力id的列表接口
     * @Date 14:22 2019/5/7
     * @Param [request]
     **/
    @PostMapping("/empower/${application.empower.version}/wx-pay-get-delivery-list")
    BaseResponse<PlatformCompanyResponse> wxPayGetDeliveryList();

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author lvzhenwei
     * @Description 微信支付--消息跳转路径设置
     * @Date 14:22 2019/5/7
     * @Param [request]
     **/
    @PostMapping("/empower/${application.empower.version}/wx-pay-set-msg-jump-path")
    BaseResponse setMsgJumpPath(@RequestBody WxPayMsgJumpPathSetRequest request);
}
