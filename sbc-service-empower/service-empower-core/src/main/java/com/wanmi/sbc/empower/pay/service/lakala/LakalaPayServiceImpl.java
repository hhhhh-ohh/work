package com.wanmi.sbc.empower.pay.service.lakala;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayCloseOrderRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.*;
import com.wanmi.sbc.empower.api.response.pay.lakala.*;
import com.wanmi.sbc.empower.bean.constant.PayServiceConstants;
import com.wanmi.sbc.empower.legder.lakala.LakalaUtils;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListByParentIdRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdResponse;
import com.wanmi.sbc.order.api.response.trade.TradeListByParentIdResponse;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author edz
 * @className LakalaPayServiceImpl
 * @description TODO
 * @date 2022/6/29 19:35
 */
@Service(PayServiceConstants.LAKALA_SERVICE)
@Slf4j
public class LakalaPayServiceImpl implements PayBaseService {

    /**
     * 接入方唯一编号（appid）：OP00000003
     * 证书序列号（serial_no）：00dfba8194c41b84cf
     * 商户号（merchant_no）：822290070111135
     * 终端号（term_no）：29034705
     * 国密4（SM4Key）：UWBPi0shIG0N4RsejkR+Sg==
     */
    @Value("${lakala.pay.preorder}")
    private String preorder;

    @Value("${lakala.pay.merge_preorder}")
    private String mergePreorder;

    @Value("${lakala.pay.trade_query}")
    private String trade_query;

    @Value("${lakala.pay.close}")
    private String close;

    @Value("${lakala.pay.id_m_refund}")
    private String id_m_refund;

    @Value("${lakala.pay.refund}")
    private String refund;

    private static final String LAKALA_PAY_SUCCESS_CODE = "BBS00000";

    @Autowired private TradeQueryProvider tradeQueryProvider;

    @Autowired private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired private PayTradeRecordProvider payTradeRecordProvider;

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        log.info("拉卡拉查询订单接口入参PayOrderDetailRequest：{}", JSON.toJSONString(request));
        List<TradeVO> trades = new ArrayList<>();
        String businessId = request.getBusinessId();
        if (businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID)
                || businessId.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)) {
            TradeListByParentIdResponse tradeListByParentIdResponse =
                    tradeQueryProvider
                            .getListByParentId(
                                    TradeListByParentIdRequest.builder()
                                            .parentTid(businessId)
                                            .build())
                            .getContext();
            trades = tradeListByParentIdResponse.getTradeVOList();
        } else {
            TradeGetByIdResponse tradeGetByIdResponse =
                    tradeQueryProvider
                            .getById(TradeGetByIdRequest.builder().tid(businessId).build())
                            .getContext();
            trades.add(tradeGetByIdResponse.getTradeVO());
        }

        TradeVO maxPriceTrade =
                trades.stream()
                        .max(Comparator.comparing(trade2 -> trade2.getTradePrice().getTotalPrice()))
                        .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
        LedgerAccountByIdResponse ledgerAccountByIdResponse =
                ledgerAccountQueryProvider
                        .findByBusiness(
                                LedgerAccountFindRequest.builder()
                                        .businessId(
                                                maxPriceTrade
                                                        .getSupplier()
                                                        .getSupplierId()
                                                        .toString())
                                        .setFileFlag(Boolean.FALSE)
                                        .build())
                        .getContext();

        PayTradeRecordResponse payTradeRecordResponse = payTradeRecordQueryProvider.getTradeRecordByOrderCode(
                TradeRecordByOrderCodeRequest.builder()
                        .orderId(request.getBusinessId())
                        .build()).getContext();

        //重新设置查询支付参数
        if(Objects.nonNull(payTradeRecordResponse) && StringUtils.isNotBlank(payTradeRecordResponse.getPayNo())){
            request.setBusinessId(payTradeRecordResponse.getPayNo());
        }

        LedgerAccountVO ledgerAccountVO = ledgerAccountByIdResponse.getLedgerAccountVO();

        LakalaTradeQueryRequest lakalaTradeQueryRequest = new LakalaTradeQueryRequest();
        lakalaTradeQueryRequest.setTradeNo(request.getOut_trade_no());
        lakalaTradeQueryRequest.setOutTradeNo(request.getBusinessId());

        lakalaTradeQueryRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
        lakalaTradeQueryRequest.setTermNo(ledgerAccountVO.getTermNo());
        PayGateway payGateway = LakalaUtils.getPayGateway();
        LakalaPayBaseRequest lakalaPayBaseRequest = new LakalaPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayBaseRequest.setOutOrgCode(payGateway.getConfig().getAccount());
        lakalaPayBaseRequest.setReqData(lakalaTradeQueryRequest);
        String body = JSON.toJSONString(lakalaPayBaseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        LakalaPayBaseResponse<LakalaTradeQueryResponse> baseResponse =
                LakalaUtils.postForPay(
                        trade_query, body, authorization, LakalaTradeQueryResponse.class);
        if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
            log.error("lakala pay trade query error:{}", baseResponse.getMsg());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return BaseResponse.success(baseResponse.getRespData());
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        log.info("拉卡拉关单接口入参PayCloseOrderRequest：{}", JSON.toJSONString(request));
        LakalaCloseRequest lakalaCloseRequest = new LakalaCloseRequest();
        String businessId = request.getBusinessId();
        List<TradeVO> trades = new ArrayList<>();
        if (businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID)
                || businessId.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)) {
            TradeListByParentIdResponse tradeListByParentIdResponse =
                    tradeQueryProvider
                            .getListByParentId(
                                    TradeListByParentIdRequest.builder()
                                            .parentTid(businessId)
                                            .build())
                            .getContext();
            trades = tradeListByParentIdResponse.getTradeVOList();
        } else {
            TradeGetByIdResponse tradeGetByIdResponse =
                    tradeQueryProvider
                            .getById(TradeGetByIdRequest.builder().tid(businessId).build())
                            .getContext();
            trades.add(tradeGetByIdResponse.getTradeVO());
        }

        TradeVO maxPriceTrade =
                trades.stream()
                        .max(Comparator.comparing(trade2 -> trade2.getTradePrice().getTotalPrice()))
                        .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
        LedgerAccountByIdResponse ledgerAccountByIdResponse =
                ledgerAccountQueryProvider
                        .findByBusiness(
                                LedgerAccountFindRequest.builder()
                                        .businessId(
                                                maxPriceTrade
                                                        .getSupplier()
                                                        .getSupplierId()
                                                        .toString())
                                        .setFileFlag(Boolean.FALSE)
                                        .build())
                        .getContext();
        LedgerAccountVO ledgerAccountVO = ledgerAccountByIdResponse.getLedgerAccountVO();
        PayOrderVO payOrderVO =
                payOrderQueryProvider
                        .findPayOrderByOrderId(
                                new FindPayOrderRequest(maxPriceTrade.getPayOrderId()))
                        .getContext();
        lakalaCloseRequest.setOriginTradeNo(payOrderVO.getPayOrderNo());
        lakalaCloseRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
        lakalaCloseRequest.setTermNo(ledgerAccountVO.getTermNo());
        LakalaCloseRequest.LocationInfo locationInfo = new LakalaCloseRequest.LocationInfo();
        locationInfo.setRequestIp(maxPriceTrade.getRequestIp());
        lakalaCloseRequest.setLocationInfo(locationInfo);

        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaPayBaseRequest lakalaPayBaseRequest = new LakalaPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayBaseRequest.setOutOrgCode(payGateway.getConfig().getAccount());
        lakalaPayBaseRequest.setReqData(lakalaCloseRequest);
        String body = JSON.toJSONString(lakalaPayBaseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        LakalaPayBaseResponse<LakalaCloseResponse> baseResponse =
                LakalaUtils.postForPay(close, body, authorization, LakalaCloseResponse.class);
        if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
            log.error("lakala pay trade close error:{}", baseResponse.getMsg());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return BaseResponse.success(baseResponse.getRespData());
    }

    @Override
    public BaseResponse payRefund(PayRefundBaseRequest request) {
        log.info("拉卡拉退款接口入参PayRefundBaseRequest：{}", JSON.toJSONString(request));
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        LakalaIdRefundRequest lakalaIdRefundRequest = request.getLakalaIdRefundRequest();
        LakalaPayBaseRequest lakalaPayBaseRequest = new LakalaPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayBaseRequest.setOutOrgCode(payGateway.getConfig().getAccount());
        lakalaPayBaseRequest.setReqData(lakalaIdRefundRequest);
        String body = JSON.toJSONString(lakalaPayBaseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        LakalaPayBaseResponse<LakalaIdRefundResponse> baseResponse =
                LakalaUtils.postForPay(
                        refund, body, authorization, LakalaIdRefundResponse.class);
        if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
            log.error("lakala refund api error:{}", baseResponse.getMsg());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, baseResponse.getMsg());
        }
        return BaseResponse.success(baseResponse.getRespData());
    }

    @Override
    public BaseResponse pay(BasePayRequest basePayRequest) {
        log.info("拉卡拉支付接口入参basePayRequest：{}", JSON.toJSONString(basePayRequest));
        PayGateway payGateway = LakalaUtils.getPayGateway();
        LakalaAllPayRequest lakalaAllPayRequest = basePayRequest.getLakalaAllPayRequest();
        LakalaPayBaseRequest lakalaPayBaseRequest = new LakalaPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayBaseRequest.setOutOrgCode(payGateway.getConfig().getAccount());
        lakalaAllPayRequest.setNotifyUrl(
                payGateway.getConfig().getBossBackUrl().concat(LakalaUtils.PAY_CALLBACK_URL));
        String tid =
                basePayRequest.getTradeId();
        if (lakalaAllPayRequest.getOutSplitInfo().size() > 1) {
            lakalaPayBaseRequest.setReqData(lakalaAllPayRequest);
            String body = JSON.toJSONString(lakalaPayBaseRequest);
            String authorization = LakalaUtils.getAuthorization(body);
            LakalaPayBaseResponse<LakalaAllPayResponse> baseResponse =
                    LakalaUtils.postForPay(
                            mergePreorder, body, authorization, LakalaAllPayResponse.class);
            if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
                log.error("lakala pay api error:{}", baseResponse.getMsg());
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            // 添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tid);
            payTradeRecordRequest.setApplyPrice(
                    new BigDecimal(lakalaAllPayRequest.getTotalAmount())
                            .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(lakalaAllPayRequest.getLocationInfo().getRequestIp());
            payTradeRecordRequest.setChannelItemId(
                    Long.parseLong(basePayRequest.getChannelItemId()));
            payTradeRecordRequest.setPayNo(lakalaAllPayRequest.getOutTradeNo());
            payTradeRecordProvider.queryAndSave(payTradeRecordRequest);
            return BaseResponse.success(baseResponse.getRespData());
        } else {
            LakalaPayRequest lakalaPayRequest =
                    KsBeanUtil.convert(lakalaAllPayRequest, LakalaPayRequest.class);
            lakalaPayBaseRequest.setReqData(lakalaPayRequest);
            String body = JSON.toJSONString(lakalaPayBaseRequest);
            String authorization = LakalaUtils.getAuthorization(body);
            LakalaPayBaseResponse<LakalaPayResponse> baseResponse =
                    LakalaUtils.postForPay(preorder, body, authorization, LakalaPayResponse.class);
            if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
                log.error("lakala pay api error:{}", baseResponse.getMsg());
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            // 添加交易记录
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(tid);
            payTradeRecordRequest.setApplyPrice(
                    new BigDecimal(lakalaAllPayRequest.getTotalAmount())
                            .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
            payTradeRecordRequest.setClientIp(lakalaAllPayRequest.getLocationInfo().getRequestIp());
            payTradeRecordRequest.setChannelItemId(
                    Long.parseLong(basePayRequest.getChannelItemId()));
            payTradeRecordRequest.setPayNo(lakalaPayRequest.getOutTradeNo());
            payTradeRecordProvider.queryAndSave(payTradeRecordRequest);
            return BaseResponse.success(baseResponse.getRespData());
        }
    }
}
