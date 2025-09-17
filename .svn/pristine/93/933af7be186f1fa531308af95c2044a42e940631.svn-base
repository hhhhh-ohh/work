package com.wanmi.sbc.empower.provider.impl.pay.weixin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.pay.weixin.WxPayProvider;
import com.wanmi.sbc.empower.api.request.pay.gateway.PayGatewayUploadPayCertificateRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformCompanyResponse;
import com.wanmi.sbc.empower.pay.service.wechat.WechatPayServiceImpl;
import com.wanmi.sbc.empower.pay.service.wechat.WechatTransferServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信支付接口
 */
@RestController
@Slf4j
public class WxPayController implements WxPayProvider {

    @Autowired
    private WechatPayServiceImpl wechatPayServiceImpl;

    @Autowired
    private WechatTransferServiceImpl wechatTransferService;

    /**
     * 微信支付--微信企业付款到零钱
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<WxPayCompanyPaymentRsponse> wxPayCompanyPayment(@RequestBody WxPayCompanyPaymentInfoRequest request) {
        WxPayCompanyPaymentRsponse wxPayCompanyPaymentRsponse = wechatPayServiceImpl.wxPayCompanyPayment(request);
        return BaseResponse.success(wxPayCompanyPaymentRsponse);
    }

    /**
     * 微信支付--商家转账到零钱
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<WxPaySupplierTransferRsponse> wxPaySupplierTransfer(@RequestBody WxPayCompanyPaymentInfoRequest request) {
        WxPaySupplierTransferRsponse wxPaySupplierTransferRsponse = wechatTransferService.wxPaySupplierTransfer(request);
        return BaseResponse.success(wxPaySupplierTransferRsponse);
    }

    @Override
    public BaseResponse<WxPayBossTransferRsponse> wxPayBossTransfer(WxPayCompanyPaymentInfoRequest request) {
        WxPayBossTransferRsponse wxPayBossTransferRsponse = wechatTransferService.wxPayBossTransfer(request);
        return BaseResponse.success(wxPayBossTransferRsponse);
    }

    @Override
    public BaseResponse<WxRefundOrderDetailReponse> getWxRefundOrderDetail(@RequestBody WxRefundOrderDetailRequest request) {
        WxRefundOrderDetailReponse reponse = wechatPayServiceImpl.wxRefundQueryOrder(request);
        return BaseResponse.success(reponse);
    }

    @Override
    public BaseResponse uploadPayCertificate(@RequestBody PayGatewayUploadPayCertificateRequest request) {
        wechatPayServiceImpl.uploadPayCertificate(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<WxPaySupplierTransferQueryResponse> wxPaySupplierTransferQuery(@RequestBody WxPaySupplierTransferQueryRequest queryRequest) {
        WxPaySupplierTransferQueryResponse response =  wechatTransferService.wxPaySupplierTransferQuery(queryRequest);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<WxPaySupplierTransferQueryDetailResponse> wxPaySupplierTransferDetailQuery(@RequestBody WxPaySupplierTransferQueryRequest transferQueryRequest) {
        WxPaySupplierTransferQueryDetailResponse response =  wechatTransferService.wxPaySupplierTransferDetailQuery(transferQueryRequest);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse wxPayUploadShippingInfo(WxPayUploadShippingInfoRequest request) {
        return BaseResponse.success(wechatPayServiceImpl.wxPayUploadShippingInfo(request));
    }

    @Override
    public BaseResponse<Integer> wxPayUploadShippingGetOrder(WxPayUploadShippingInfoGetOrderRequest request) {
        Integer status = wechatPayServiceImpl.wxPayUploadShippingGetOrder(request);
        return BaseResponse.success(status);
    }

    @Override
    public BaseResponse<PlatformCompanyResponse> wxPayGetDeliveryList() {
        PlatformCompanyResponse response = wechatPayServiceImpl.wxPayGetDeliveryList();
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse setMsgJumpPath(@RequestBody WxPayMsgJumpPathSetRequest request) {
        wechatPayServiceImpl.setMsgJumpPath(request);
        return BaseResponse.SUCCESSFUL();
    }
}
