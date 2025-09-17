package com.wanmi.sbc.empower.pay.service.wechat;

//import cn.hutool.core.text.CharSequenceUtil;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.api.request.sellplatform.order.PlatformOrderRequest;
import com.wanmi.sbc.empower.api.response.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformOrderPayParamResponse;
import com.wanmi.sbc.empower.bean.constant.PayServiceConstants;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.miniprogramset.service.MiniProgramSetService;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.model.root.WechatConfig;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.empower.pay.service.PayDataService;
import com.wanmi.sbc.empower.pay.service.wechat.v3.WechatPayV3Constant;
import com.wanmi.sbc.empower.pay.service.wechat.v3.request.*;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformOrderService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayUtil;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.java.core.cipher.RSASigner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wur
 * @className WechatPayHttpUtil
 * @description 微信支付 V3版本
 * @date 2022/11/28 11:07
 */
@Slf4j
@Service(PayServiceConstants.WECHAT_V3_SERVICE)
public class WechatPayV3ServiceImpl implements PayBaseService {

    /** 微信退款成功回调地址 */
    private static final String REFUND_CALLBACK = "/tradeCallback/wxPayV3RefundSuccessCallBack";

    /** 微信支付回调地址 */
    private static final String TRADE_CALLBACK = "/tradeCallback/wxPayV3SuccessCallBack";

    private static final String SIGN_CHARSET_NAME = "utf-8";

    @Autowired private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired private GatewayConfigRepository gatewayConfigRepository;

    @Autowired private PlatformContext thirdPlatformContext;

    @Autowired private MiniProgramSetService miniProgramSetService;

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        PayOrderDetailResponse payOrderDetailResponse = new PayOrderDetailResponse();
        // 1. 获取支付配置
        PayGatewayConfig payGatewayConfig =
                gatewayConfigRepository.queryConfigByNameAndStoreId(
                        PayGatewayEnum.WECHAT, request.getStoreId());
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        try {
            String url = String.format(WechatPayV3Constant.QUERY_URL, request.getBusinessId(), payGatewayConfig.getAccount());
            // 3.调用接口
            log.info("微信V3 - 支付单查询请求：request={}", url);
            String responseStr =
                    WechatPayHttpUtil.doGetData(
                            url,
                            payGatewayConfig);
            log.info("微信V3 - 支付单查询响应：response={}", responseStr);
            JSONObject responseJson = JSON.parseObject(responseStr);
            if (responseJson.containsKey("trade_state") && responseJson.containsKey("out_trade_no")) {
                payOrderDetailResponse.setReturn_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                payOrderDetailResponse.setResult_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                payOrderDetailResponse.setTrade_state(responseJson.getString("trade_state"));
                payOrderDetailResponse.setAppid(responseJson.getString("appid"));
                payOrderDetailResponse.setMch_id(responseJson.getString("mchid"));
                // 获取设备号
                if(responseJson.containsKey(WechatPayV3Constant.SCENE_INFO) && StringUtils.isNotBlank(responseJson.getString(WechatPayV3Constant.SCENE_INFO))) {
                    JSONObject sceneJson = responseJson.getJSONObject(WechatPayV3Constant.SCENE_INFO);
                    if (sceneJson.containsKey("device_id")) {
                        payOrderDetailResponse.setDevice_info(sceneJson.getString("device_id"));
                    }
                }
                // 获取用户信息
                if(responseJson.containsKey(WechatPayV3Constant.PAYER) && StringUtils.isNotBlank(responseJson.getString(WechatPayV3Constant.PAYER))) {
                    JSONObject payerJson = responseJson.getJSONObject(WechatPayV3Constant.PAYER);
                    if (payerJson.containsKey("openid")) {
                        payOrderDetailResponse.setOpenid(payerJson.getString("openid"));
                    }
                }
                payOrderDetailResponse.setTrade_type(responseJson.getString("trade_type"));
                payOrderDetailResponse.setBank_type(responseJson.getString("bank_type"));
                //获取支付金额
                if(responseJson.containsKey(WechatPayV3Constant.AMOUNT) && StringUtils.isNotBlank(responseJson.getString(WechatPayV3Constant.AMOUNT))) {
                    JSONObject amountJson = responseJson.getJSONObject(WechatPayV3Constant.AMOUNT);
                    if (amountJson.containsKey(WechatPayV3Constant.TOTAL)) {
                        payOrderDetailResponse.setTotal_fee(amountJson.getString(WechatPayV3Constant.TOTAL));
                    }
                    if (amountJson.containsKey("payer_total")) {
                        payOrderDetailResponse.setCash_fee(amountJson.getString("payer_total"));
                    }
                    if (amountJson.containsKey(WechatPayV3Constant.CURRENCY)) {
                        payOrderDetailResponse.setFee_type(amountJson.getString(WechatPayV3Constant.CURRENCY));
                    }
                    if (amountJson.containsKey("payer_currency")) {
                        payOrderDetailResponse.setCash_fee_type(amountJson.getString("payer_currency"));
                    }
                }
                payOrderDetailResponse.setTransaction_id(responseJson.getString("transaction_id"));
                payOrderDetailResponse.setOut_trade_no(responseJson.getString("out_trade_no"));
                payOrderDetailResponse.setAttach(responseJson.getString("attach"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success(payOrderDetailResponse);
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        PayCloseOrderResponse payCloseOrderResponse = new PayCloseOrderResponse();
        // 1. 获取支付配置
        PayGatewayConfig payGatewayConfig =
                gatewayConfigRepository.queryConfigByNameAndStoreId(
                        PayGatewayEnum.WECHAT, request.getStoreId());
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())
                || StringUtils.isBlank(payGatewayConfig.getAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        try {
            String url = String.format(WechatPayV3Constant.CLOSE_URL, request.getBusinessId());
            WxPayV3CloseOrderRequest closeOrderRequest = new WxPayV3CloseOrderRequest();
            closeOrderRequest.setMchid(payGatewayConfig.getAccount());
            // 3.调用接口
            log.info("微信V3 - 关闭支付单请求：request={}", url);
            String responseStr =
                    WechatPayHttpUtil.doPostData(
                            url,
                            JSON.toJSONString(closeOrderRequest),
                            payGatewayConfig);
            log.info("微信V3 - 关闭支付单响应：response={}", responseStr);
            // 返回空 取消成功
            if (StringUtils.isBlank(responseStr)) {
                payCloseOrderResponse.setResult_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                payCloseOrderResponse.setReturn_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
            } else {
                JSONObject responseJson = JSON.parseObject(responseStr);
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_CODE)) {
                    payCloseOrderResponse.setErr_code(responseJson.getString(WechatPayV3Constant.RETURN_CODE));
                }
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_MESSAGE)) {
                    payCloseOrderResponse.setErr_code(
                            responseJson.getString(WechatPayV3Constant.RETURN_MESSAGE));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success(payCloseOrderResponse);
    }

    @Override
    public BaseResponse payRefund(PayRefundBaseRequest request) {
        WxPayRefundRequest refundRequest = request.getWxPayRefundRequest();
        PayRefundResponse payRefundResponse = new PayRefundResponse();
        // 1. 获取支付配置
        PayGatewayConfig payGatewayConfig =
                gatewayConfigRepository.queryConfigByNameAndStoreId(
                        PayGatewayEnum.WECHAT, refundRequest.getStoreId());
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())
                || StringUtils.isBlank(payGatewayConfig.getAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        try {
            //封装请求参数
            WxPayV3RefundsRequest v3RefundsRequest = new WxPayV3RefundsRequest(refundRequest);
            //重复支付退款不需要异步回调地址
            if (StringUtils.isNotBlank(refundRequest.getRefund_type()) && !refundRequest.getRefund_type().equals(
                    "REPEATPAY")) {
                v3RefundsRequest.setNotify_url(payGatewayConfig.getBossBackUrl() + REFUND_CALLBACK);
            }

            // 3.调用接口
            log.info("微信V3 - 申请退款请求：request={}", JSON.toJSONString(v3RefundsRequest));
            String responseStr =
                    WechatPayHttpUtil.doPostData(
                            WechatPayV3Constant.REFUNDS_URL,
                            JSON.toJSONString(v3RefundsRequest),
                            payGatewayConfig);
            log.info("微信V3 - 申请退款响应：response={}", responseStr);
            // 处理退款返回
            JSONObject responseJson = JSON.parseObject(responseStr);
            if(responseJson.containsKey("refund_id") && responseJson.containsKey("status")) {
                payRefundResponse.setReturn_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                payRefundResponse.setResult_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                payRefundResponse.setTransaction_id(responseJson.getString("transaction_id"));
                payRefundResponse.setRefund_id(responseJson.getString("refund_id"));
                payRefundResponse.setOut_trade_no(responseJson.getString("out_refund_no"));
                //封装退款金额相关信息
                if(responseJson.containsKey("amount")) {
                    JSONObject amountJson = JSON.parseObject(responseJson.getString("amount"));
                    payRefundResponse.setRefund_fee(amountJson.getString("refund"));
                    payRefundResponse.setSettlement_refund_fee(amountJson.getString("settlement_refund"));
                    payRefundResponse.setTotal_fee(amountJson.getString("total"));
                    payRefundResponse.setSettlement_total_fee(amountJson.getString("settlement_total"));
                    payRefundResponse.setCash_fee_type(amountJson.getString("currency"));
                }
            } else {
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_CODE)) {
                    payRefundResponse.setErr_code(responseJson.getString(WechatPayV3Constant.RETURN_CODE));
                }
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_MESSAGE)) {
                    payRefundResponse.setErr_code(
                            responseJson.getString(WechatPayV3Constant.RETURN_MESSAGE));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success(payRefundResponse);
    }

    @Autowired
    private PayDataService payDataService;

    @Override
    @Transactional
    public BaseResponse pay(BasePayRequest basePayRequest) {
        Integer wxPayType = basePayRequest.getWxPayType();
        if(Objects.isNull(wxPayType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        WechatConfig wechatConfig = payDataService.findByWxPayTypeAndStoreId(wxPayType, null);
        if (Objects.isNull(wechatConfig)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        switch (wxPayType) {
            case Constants.ZERO:
                WxPayForNativeRequest request = basePayRequest.getWxPayForNativeRequest();
                request.setAppid(wechatConfig.getAppId());
                // 调用统一下单接口
                WxPayForNativeResponse wxPayForNativeResponse = this.wxPayForNative(request,basePayRequest.getTradeId());
                log.info("微信扫码支付请求结果:{}", wxPayForNativeResponse);
                if (StringUtils.isNotBlank(wxPayForNativeResponse.getCode_url())) {
                    return BaseResponse.success(wxPayForNativeResponse);
                }
                this.throwErrMsg(
                        wxPayForNativeResponse.getErr_code(),
                        wxPayForNativeResponse.getErr_code_des());
                return BaseResponse.error(wxPayForNativeResponse.getErr_code_des());
            case Constants.ONE:
                WxPayForMWebRequest mWebRequest = basePayRequest.getWxPayForMWebRequest();
                mWebRequest.setAppid(wechatConfig.getAppId());
                // 调用统一下单接口
                WxPayForMWebResponse wxPayForMWebResponse =
                        this.wxPayForH5(mWebRequest, basePayRequest.getTradeId());
                if (Constants.SUCCESS.equals(wxPayForMWebResponse.getReturn_code())
                        && Constants.SUCCESS.equals(wxPayForMWebResponse.getResult_code())) {
                    return BaseResponse.success(wxPayForMWebResponse);
                }
                this.throwErrMsg(
                        wxPayForMWebResponse.getErr_code(), wxPayForMWebResponse.getErr_code_des());
                return BaseResponse.error(wxPayForMWebResponse.getErr_code_des());
            case Constants.TWO:
                WxPayForJSApiRequest wxPayForJSApiRequest =
                        basePayRequest.getWxPayForJSApiRequest();
                PayGatewayConfig payGatewayConfig =
                        gatewayConfigRepository.queryConfigByNameAndStoreId(
                                PayGatewayEnum.WECHAT, wxPayForJSApiRequest.getStoreId());
                if (Objects.isNull(payGatewayConfig)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                wxPayForJSApiRequest.setAppid(wechatConfig.getAppId());
                log.info("微信支付[JSApi]统一下单接口入参:{}", wxPayForJSApiRequest);
                // 调用统一下单接口
                WxPayForJSApiResponse wxPayForJSApiResponse =
                        this.wxPayForJSApi(wxPayForJSApiRequest, payGatewayConfig,basePayRequest.getTradeId());
                if (Constants.SUCCESS.equals(wxPayForJSApiResponse.getReturn_code())
                        && Constants.SUCCESS.equals(wxPayForJSApiResponse.getResult_code())) {
                    return getSignResultCommon(
                            wechatConfig.getAppId(),
                            payGatewayConfig.getPrivateKey().replace(" ", ""),
                            wxPayForJSApiResponse.getPrepay_id());
                }
                log.error(
                        "微信支付[JSApi]统一下单接口调用失败,入参:{},返回结果为:{}",
                        wxPayForJSApiRequest,
                        wxPayForJSApiResponse);
                this.throwErrMsg(
                        wxPayForJSApiResponse.getErr_code(),
                        wxPayForJSApiResponse.getErr_code_des());
                return BaseResponse.error(wxPayForJSApiResponse.getErr_code_des());
            case Constants.THREE:
                WxPayForJSApiRequest jsApiRequest = basePayRequest.getWxPayForJSApiRequest();
                payGatewayConfig =
                        gatewayConfigRepository.queryConfigByNameAndStoreId(
                                PayGatewayEnum.WECHAT, jsApiRequest.getStoreId());
                if (Objects.isNull(payGatewayConfig)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                jsApiRequest.setAppid(wechatConfig.getAppId());
                log.info("小程序支付[JSApi]统一下单接口入参:{}", jsApiRequest);
                // 调用统一下单接口
                WxPayForJSApiResponse jsApiResponse =
                        this.wxPayForJSApi(jsApiRequest, payGatewayConfig,basePayRequest.getTradeId());
                if (Constants.SUCCESS.equals(jsApiResponse.getReturn_code())
                        && Constants.SUCCESS.equals(jsApiResponse.getResult_code())) {
                    return getSignResultCommon(
                            jsApiRequest.getAppid(),
                            payGatewayConfig.getPrivateKey().replace(" ", ""),
                            jsApiResponse.getPrepay_id());
                }
                log.error("小程序支付[小程序]统一下单接口调用失败,入参:{},返回结果为:{}", jsApiRequest, jsApiResponse);
                this.throwErrMsg(jsApiResponse.getErr_code(), jsApiResponse.getErr_code_des());
                return BaseResponse.error(jsApiResponse.getErr_code_des());
            case Constants.FOUR:
                WxPayForAppRequest appRequest = basePayRequest.getWxPayForAppRequest();
                appRequest.setNonce_str(WXPayUtil.generateNonceStr());
                payGatewayConfig =
                        gatewayConfigRepository.queryConfigByNameAndStoreId(
                                PayGatewayEnum.WECHAT, appRequest.getStoreId());
                if (Objects.isNull(payGatewayConfig)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                appRequest.setAppid(wechatConfig.getAppId());
                log.info("微信支付[App]统一下单接口入参:{}", appRequest);
                // 调用统一下单接口
                WxPayForAppResponse appResponse = this.wxPayForApp(appRequest, payGatewayConfig,basePayRequest.getTradeId());
                Map<String, String> resultMap = new HashMap<>();
                if (Constants.SUCCESS.equals(appResponse.getReturn_code())
                        && Constants.SUCCESS.equals(appResponse.getResult_code())) {
                    String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
                    String nonceStr = appRequest.getNonce_str();
                    resultMap.put("appId", wechatConfig.getAppId());
                    resultMap.put("partnerId", payGatewayConfig.getOpenPlatformAccount());
                    resultMap.put("prepayId", appResponse.getPrepay_id());
                    resultMap.put("packageValue", "Sign=WXPay");
                    resultMap.put("nonceStr", nonceStr);
                    resultMap.put("timestamp", timestamp);
                    try {
                        String message = buildMessage(wechatConfig.getAppId(), timestamp, nonceStr, appResponse.getPrepay_id());
                        resultMap.put(
                                "sign", this.sign(message, payGatewayConfig.getMerchantSerialNumber(), payGatewayConfig.getPrivateKey().replace(" ", "")));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                    }
                    log.info("微信支付V3-App 返回参数：{}", JSON.toJSONString(resultMap));
                    return BaseResponse.success(resultMap);
                }
                log.error("微信支付[app]统一下单接口调用失败,入参:{},返回结果为:{}", appRequest, appResponse);
                this.throwErrMsg(appResponse.getErr_code(), appResponse.getErr_code_des());
                return BaseResponse.success(resultMap);
            case Constants.FIVE:
                // 视频号获取支付参数处理
                WxChannelsPayRequest wxChannelsPayRequest =
                        basePayRequest.getWxChannelsPayRequest();
                PlatformOrderRequest channelsOrderRequest =
                        PlatformOrderRequest.builder()
                                .order_id(wxChannelsPayRequest.getThirdOrderId())
                                .out_order_id(wxChannelsPayRequest.getOrderId())
                                .openid(wxChannelsPayRequest.getOpenid())
                                .build();
                // 添加交易记录
                PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                payTradeRecordRequest.setBusinessId(basePayRequest.getTradeId());
                payTradeRecordRequest.setPayNo(wxChannelsPayRequest.getOrderId());
                payTradeRecordRequest.setApplyPrice(
                        new BigDecimal(wxChannelsPayRequest.getTotalPrice())
                                .divide(new BigDecimal(100))
                                .setScale(2, RoundingMode.DOWN));
                payTradeRecordRequest.setClientIp(wxChannelsPayRequest.getClientIp());
                payTradeRecordRequest.setChannelItemId(30L);
                this.addTradeRecord(payTradeRecordRequest);

                PlatformOrderService orderService =
                        thirdPlatformContext.getPlatformService(
                                SellPlatformType.WECHAT_VIDEO, PlatformServiceType.ORDER_SERVICE);

                BaseResponse<PlatformOrderPayParamResponse> channelsBaseResponse =
                        orderService.getOrderPayParam(channelsOrderRequest);
                if (!CommonErrorCodeEnum.K000000.getCode().equals(channelsBaseResponse.getCode())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                PlatformOrderPayParamResponse payParamResponse = channelsBaseResponse.getContext();
                return BaseResponse.success(
                        WxChannelsPayParamResponse.builder()
                                .payPackage(payParamResponse.getPay_package())
                                .paySign(payParamResponse.getPaySign())
                                .signType(payParamResponse.getSignType())
                                .nonceStr(payParamResponse.getNonceStr())
                                .timeStamp(payParamResponse.getTimeStamp())
                                .build());
            default:
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 统一下单接口--native扫码支付（pc扫码支付）
     *
     * @param request
     */
    public WxPayForNativeResponse wxPayForNative(WxPayForNativeRequest request,String tradeId) {
        WxPayForNativeResponse response = new WxPayForNativeResponse();
        // 1. 获取支付配置
        PayGatewayConfig payGatewayConfig =
                gatewayConfigRepository.queryConfigByNameAndStoreId(
                        PayGatewayEnum.WECHAT, request.getStoreId());
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        try {
            // 2.封装请求参数
            WxPayV3ForNativeRequest wxPayV3ForNativeRequest = new WxPayV3ForNativeRequest(request);
            wxPayV3ForNativeRequest.setAppid(request.getAppid());
            wxPayV3ForNativeRequest.setNotify_url(
                    payGatewayConfig.getBossBackUrl() + TRADE_CALLBACK);
            wxPayV3ForNativeRequest.setMchid(payGatewayConfig.getAccount());

            // 添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(
                    new BigDecimal(request.getTotal_fee())
                            .divide(new BigDecimal(100))
                            .setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(14L);
            this.addTradeRecord(payTradeRecordRequest);

            // 3.调用接口
            log.info("微信V3 - 扫码支付请求：request={}", JSON.toJSONString(wxPayV3ForNativeRequest));
            String responseStr =
                    WechatPayHttpUtil.doPostData(
                            WechatPayV3Constant.NATIVE_PAY_URL,
                            JSON.toJSONString(wxPayV3ForNativeRequest),
                            payGatewayConfig);
            log.info("微信V3 - 扫码支付响应：response={}", responseStr);
            JSONObject responseJson = JSON.parseObject(responseStr);
            // 4.处理返回数据
            if (responseJson.containsKey(WechatPayV3Constant.NATIVE_CODE_URL)) {
                response.setCode_url(responseJson.getString(WechatPayV3Constant.NATIVE_CODE_URL));
                // 填充以下两个字段是为了PC端处理和V2保持一致
                response.setResult_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                response.setReturn_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
            } else {
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_CODE)) {
                    response.setErr_code(responseJson.getString(WechatPayV3Constant.RETURN_CODE));
                }
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_MESSAGE)) {
                    response.setErr_code(
                            responseJson.getString(WechatPayV3Constant.RETURN_MESSAGE));
                } else {
                    response.setErr_code_des("支付失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 统一下单接口--非微信浏览器h5支付
     *
     * @param request
     */
    public WxPayForMWebResponse wxPayForH5(WxPayForMWebRequest request,String tradeId) {
        WxPayForMWebResponse response = new WxPayForMWebResponse();

        // 1. 获取支付配置
        PayGatewayConfig payGatewayConfig =
                gatewayConfigRepository.queryConfigByNameAndStoreId(
                        PayGatewayEnum.WECHAT, request.getStoreId());
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        try {
            // 2.封装请求参数
            WxPayV3ForH5Request wxPayV3ForH5Request = new WxPayV3ForH5Request(request);
            wxPayV3ForH5Request.setAppid(request.getAppid());
            wxPayV3ForH5Request.setNotify_url(payGatewayConfig.getBossBackUrl() + TRADE_CALLBACK);
            wxPayV3ForH5Request.setMchid(payGatewayConfig.getAccount());

            // 添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(
                    new BigDecimal(request.getTotal_fee())
                            .divide(new BigDecimal(100))
                            .setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(15L);
            this.addTradeRecord(payTradeRecordRequest);

            // 3.调用接口
            log.info("微信V3 - H5支付请求：request={}", JSON.toJSONString(wxPayV3ForH5Request));
            String responseStr =
                    WechatPayHttpUtil.doPostData(
                            WechatPayV3Constant.H5_PAY_URL,
                            JSON.toJSONString(wxPayV3ForH5Request),
                            payGatewayConfig);
            log.info("微信V3 - H5支付响应：response={}", responseStr);
            JSONObject responseJson = JSON.parseObject(responseStr);
            // 4.处理返回数据
            if (responseJson.containsKey(WechatPayV3Constant.H5_URL)) {
                response.setMweb_url(responseJson.getString(WechatPayV3Constant.H5_URL));
                // 填充以下两个字段是为了PC端处理和V2保持一致
                response.setResult_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                response.setReturn_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
            } else {
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_CODE)) {
                    response.setErr_code(responseJson.getString(WechatPayV3Constant.RETURN_CODE));
                }
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_MESSAGE)) {
                    response.setErr_code(
                            responseJson.getString(WechatPayV3Constant.RETURN_MESSAGE));
                } else {
                    response.setErr_code_des("支付失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 统一下单接口--微信浏览器内JSApi支付
     *
     * @param request
     */
    public WxPayForJSApiResponse wxPayForJSApi(
            WxPayForJSApiRequest request, PayGatewayConfig payGatewayConfig,String tradeId) {
        WxPayForJSApiResponse response = new WxPayForJSApiResponse();
        // 1. 获取支付配置
        if (Objects.isNull(payGatewayConfig)) {
            payGatewayConfig =
                    gatewayConfigRepository.queryConfigByNameAndStoreId(
                            PayGatewayEnum.WECHAT, request.getStoreId());
        }
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        try {
            // 2. 添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(
                    new BigDecimal(request.getTotal_fee())
                            .divide(new BigDecimal(100))
                            .setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(16L);
            addTradeRecord(payTradeRecordRequest);

            // 3. 封装请求参数'调用接口
            WxPayV3ForJSApiRequest jsApiRequest = new WxPayV3ForJSApiRequest(request);
            jsApiRequest.setAppid(request.getAppid());
            jsApiRequest.setNotify_url(payGatewayConfig.getBossBackUrl() + TRADE_CALLBACK);
            jsApiRequest.setMchid(payGatewayConfig.getAccount());
            log.info("微信V3 - JSAPI支付请求：request={}", JSON.toJSONString(jsApiRequest));
            String responseStr =
                    WechatPayHttpUtil.doPostData(
                            WechatPayV3Constant.JSAPI_PAY_URL,
                            JSON.toJSONString(jsApiRequest),
                            payGatewayConfig);
            log.info("微信V3 - JSAPI支付响应：response={}", responseStr);
            JSONObject responseJson = JSON.parseObject(responseStr);
            // 4.处理返回数据
            if (responseJson.containsKey(WechatPayV3Constant.JSAPI_PREPAY_ID)) {
                response.setPrepay_id(responseJson.getString(WechatPayV3Constant.JSAPI_PREPAY_ID));
                // 填充以下两个字段是为了和V2保持一致
                response.setResult_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                response.setReturn_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
            } else {
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_CODE)) {
                    response.setErr_code(responseJson.getString(WechatPayV3Constant.RETURN_CODE));
                }
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_MESSAGE)) {
                    response.setErr_code(
                            responseJson.getString(WechatPayV3Constant.RETURN_MESSAGE));
                } else {
                    response.setErr_code_des("支付失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 统一下单接口--微信App支付
     *
     * @param request
     */
    public WxPayForAppResponse wxPayForApp(
            WxPayForAppRequest request, PayGatewayConfig payGatewayConfig,String tradeId) {
        WxPayForAppResponse response = new WxPayForAppResponse();
        // 1. 获取支付配置
        if (Objects.isNull(payGatewayConfig)) {
            payGatewayConfig =
                    gatewayConfigRepository.queryConfigByNameAndStoreId(
                            PayGatewayEnum.WECHAT, request.getStoreId());
        }
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        try {
            // 2. 添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tradeId);
            payTradeRecordRequest.setPayNo(request.getOut_trade_no());
            payTradeRecordRequest.setApplyPrice(
                    new BigDecimal(request.getTotal_fee())
                            .divide(new BigDecimal(100))
                            .setScale(2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(request.getSpbill_create_ip());
            payTradeRecordRequest.setChannelItemId(20L);
            addTradeRecord(payTradeRecordRequest);

            // 3. 封装请求参数'调用接口
            WxPayV3ForAppRequest appRequest = new WxPayV3ForAppRequest(request);
            appRequest.setAppid(request.getAppid());
            appRequest.setNotify_url(payGatewayConfig.getBossBackUrl() + TRADE_CALLBACK);
            appRequest.setMchid(payGatewayConfig.getAccount());
            log.info("微信V3 - APP支付请求：request={}", JSON.toJSONString(appRequest));
            String responseStr =
                    WechatPayHttpUtil.doPostData(
                            WechatPayV3Constant.APP_PAY_URL,
                            JSON.toJSONString(appRequest),
                            payGatewayConfig);
            log.info("微信V3 - APP支付响应：response={}", responseStr);
            JSONObject responseJson = JSON.parseObject(responseStr);
            // 4.处理返回数据
            if (responseJson.containsKey(WechatPayV3Constant.JSAPI_PREPAY_ID)) {
                response.setPrepay_id(responseJson.getString(WechatPayV3Constant.JSAPI_PREPAY_ID));
                // 填充以下两个字段是为了和V2保持一致
                response.setResult_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
                response.setReturn_code(WechatPayV3Constant.PAY_RETURN_SUCCESS);
            } else {
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_CODE)) {
                    response.setErr_code(responseJson.getString(WechatPayV3Constant.RETURN_CODE));
                }
                if (responseJson.containsKey(WechatPayV3Constant.RETURN_MESSAGE)) {
                    response.setErr_code(
                            responseJson.getString(WechatPayV3Constant.RETURN_MESSAGE));
                } else {
                    response.setErr_code_des("支付失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * @description 获取微信平台证书公钥
     * @author  wur
     * @date: 2022/12/1 15:42
     * @return
     **/
    public Map<String,String> getPublicKey() throws Exception {
        PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID);
        Map<String,String> keyMap = new HashMap<>();
        if (Objects.isNull(payGatewayConfig)
                || StringUtils.isBlank(payGatewayConfig.getPrivateKey())
                || StringUtils.isBlank(payGatewayConfig.getApiV3Key())
                || StringUtils.isBlank(payGatewayConfig.getMerchantSerialNumber())) {
            return keyMap;
        }

        // 1. 签名
        // 1.1 拼接要签名的字符串
        long timestamp = System.currentTimeMillis() / 1000;
        String nonce = WXPayUtil.generateNonceStr();
        String body =  "";
        String orgSignText = "GET\n" + "/v3/certificates\n" + timestamp + "\n" + nonce + "\n" + body + "\n";
        // 1.2 创建私钥
        PrivateKey privateKey = getPrivateKey(payGatewayConfig.getPrivateKey().replace(" ", ""));
        // 1.3 生成签名
        String sign = encryptByPrivateKey(orgSignText, privateKey);

        //2. 封装头信息
        StringBuilder auth = new StringBuilder("WECHATPAY2-SHA256-RSA2048 ");
        auth.append("mchid=\"").append(payGatewayConfig.getAccount()).append("\"");
        auth.append(",nonce_str=\"").append(nonce).append("\"");
        auth.append(",signature=\"").append(sign).append("\"");
        auth.append(",timestamp=\"").append(timestamp).append("\"");
        auth.append(",serial_no=\"").append(payGatewayConfig.getMerchantSerialNumber()).append("\"");
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization",auth.toString());
        headerMap.put("Accept","application/json");
        headerMap.put("User-Agent","https://zh.wikipedia.org/wiki/User_agent");

        //3. 调用HttP请求
        log.info("================获取微信平台-公钥证书，请求header{}",JSON.toJSON(headerMap));
        String responseBody = HttpUtils.get(WechatPayV3Constant.CERTIFICATES, headerMap);

        // 4. 处理返回
        log.info("================获取微信平台-公钥证书，返回 {}", responseBody);
        if (StringUtils.isBlank(responseBody)) {
            return keyMap;
        }
        JSONObject responseJson = JSON.parseObject(responseBody);
        if (!responseJson.containsKey("data")) {
            return keyMap;
        }
        JSONArray dataArray = responseJson.getJSONArray("data");
        log.info("V3----dataArray==={}",dataArray);
        if (dataArray.size() > 0) {
            JSONObject dataJson = (JSONObject) dataArray.get(0);
            log.info("V3----dataJson==={}",dataJson);
            if (dataJson.containsKey("encrypt_certificate")) {
                JSONObject certificate = dataJson.getJSONObject("encrypt_certificate");
                String publicKey = wxPayV3GetSign(payGatewayConfig.getApiV3Key(), certificate.getString("associated_data"), certificate.getString("nonce"), certificate.getString("ciphertext"));
                if (certificate.containsKey("serial_no")) {
                    keyMap.put(certificate.getString("serial_no"),publicKey);
                } else {
                    keyMap.put(dataJson.getString("serial_no"),publicKey);
                }
            }
        }
        return keyMap;
    }

    /**
     * 微信返回敏感数据解密
     * @param apiV3Key    V3密钥
     * @param associatedData  微信接口返回
     * @param nonce           微信接口返回
     * @param ciphertext      微信接口返回
     * @return            敏感数据
     * @throws GeneralSecurityException
     */
    private String wxPayV3GetSign(String apiV3Key, String associatedData, String nonce, String ciphertext) throws GeneralSecurityException {
        AesUtil wxAesUtil = new AesUtil(apiV3Key.getBytes());
        String jsonStr = wxAesUtil.decryptToString(associatedData.getBytes(),nonce.getBytes(),ciphertext);
        return jsonStr;
    }

    /**
     * 生成微信签名
     * @param data        签名数据
     * @param privateKey  商户私钥
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signed = signature.sign();
//        return CharSequenceUtil.str(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(signed));
        return Base64.getEncoder().encodeToString(signed);
    }


    /**
     * 微信支付公共异常方法
     * @param code
     * @param msg
     */
    private void throwErrMsg(String code, String msg) throws SbcRuntimeException {
        if ("OUT_TRADE_NO_USED".equals(code)) {
            // 订单已支付
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060010);
        } else if ("ORDERNOTEXIST".equalsIgnoreCase(code)) {
            // 订单超时、关闭、单号重复
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[] {msg});
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
    }

    /**
     * 微信浏览器内,小程序内支付,获取返回对象公共方法
     *
     * @param appId
     * @param providerKey
     * @param prepayId
     * @return
     */
    private BaseResponse<Map<String, String>> getSignResultCommon(
            String appId, String providerKey, String prepayId) {
        Map<String, String> resultMap = new HashMap<>();
        String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
        String nonceStr = WXPayUtil.generateNonceStr();
        String packageStr = "prepay_id=" + prepayId;
        resultMap.put("appId", appId);
        resultMap.put("timeStamp", timestamp);
        resultMap.put("nonceStr", nonceStr);
        resultMap.put("package", packageStr);
        resultMap.put("signType", "RSA");
        try {
            String message = buildMessage(appId, timestamp, nonceStr, packageStr);
            log.info("---WechatPay---V3 getSign---{}, {}", message, providerKey);
            resultMap.put("paySign", this.sign(message.getBytes(SIGN_CHARSET_NAME), providerKey));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return BaseResponse.success(resultMap);
    }

    /**
     * 微信支付-前端唤起支付参数-签名
     * @param message 签名数据
     * @return
     */
    public String sign(byte[] message,String privateKey) {
        try{
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(getPrivateKey(privateKey));
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch(Exception e) {
            log.error("RSA签名失败");
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    public String sign(String message, String serialNumber, String privateKey) {
        try{
            com.wechat.pay.java.core.cipher.Signer  rsaSigner = new RSASigner(serialNumber, getPrivateKey(privateKey));
            com.wechat.pay.java.core.cipher.SignatureResult signatureResult = rsaSigner.sign(message);
            return signatureResult.getSign();
        } catch(Exception e) {
            log.error("RSA签名失败");
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 微信支付-前端唤起支付参数-获取商户私钥
     * @param privateKey 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String privateKey){
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            log.error("当前Java环境不支持RSA");
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        } catch (InvalidKeySpecException e) {
            log.error("无效的密钥格式");
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 微信支付-前端唤起支付参数-构建签名参数
     * @param nonceStr 签名数据
     * @return
     */
    public static String buildMessage(String appId, String timestamp, String nonceStr, String packageStr) {
        return appId + "\n" + timestamp + "\n" + nonceStr + "\n" + packageStr + "\n";
    }

    /**
     * 添加订单支付--交易记录
     *
     * @param request
     */
    private void addTradeRecord(PayTradeRecordRequest request) {
        payTradeRecordProvider.queryAndSave(request);
    }
}
