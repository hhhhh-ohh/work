package com.wanmi.sbc.empower.pay.service.lakala;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
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
import com.wanmi.sbc.empower.bean.enums.LklPayType;
import com.wanmi.sbc.empower.legder.lakala.LakalaCasherUtils;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.service.PayBaseService;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListByParentIdRequest;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdResponse;
import com.wanmi.sbc.order.api.response.trade.TradeListByParentIdResponse;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
 * @className LakalaCasherPayServiceImpl
 * @description TODO
 * @date
 */
@Service(PayServiceConstants.LAKALA_CASH_SERVICE)
@Slf4j
public class LakalaCasherPayServiceImpl implements PayBaseService {

    /**
     * 接入方唯一编号（appid）：OP00000003
     * 证书序列号（serial_no）：00dfba8194c41b84cf
     * 商户号（merchant_no）：822290070111135
     * 终端号（term_no）：29034705
     * 国密4（SM4Key）：UWBPi0shIG0N4RsejkR+Sg==
     */
    @Value("${lakala.casher.pay.create_order}")
    private String createOrder;

    @Value("${lakala.casher.pay.trade_query}")
    private String trade_query;

    @Value("${lakala.casher.pay.close}")
    private String close;

    @Value("${lakala.casher.pay.id_m_refund}")
    private String id_m_refund;

    @Value("${lakala.casher.pay.trade_refund}")
    private String trade_refund;

    @Value("${lakala.casher.pay.trade_pay_type}")
    private Integer trade_pay_type;


    private static final String LAKALA_PAY_SUCCESS_CODE = "000000";

    private static final String LAKALA_REFUND_SUCCESS_CODE = "BBS00000";

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Override
    public BaseResponse getPayOrderDetail(PayOrderDetailRequest request) {
        log.info("拉卡拉聚合收银台查询订单接口入参PayOrderDetailRequest：{}", JSON.toJSONString(request));
        List<TradeVO> trades = Lists.newArrayList();
        String orderCode = request.getOrderCode();
        if (orderCode.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID)
                || orderCode.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)) {
            TradeListByParentIdResponse tradeListByParentIdResponse =
                    tradeQueryProvider
                            .getListByParentId(
                                    TradeListByParentIdRequest.builder()
                                            .parentTid(orderCode)
                                            .build())
                            .getContext();
            trades = tradeListByParentIdResponse.getTradeVOList();
        } else {
            TradeGetByIdResponse tradeGetByIdResponse =
                    tradeQueryProvider
                            .getById(TradeGetByIdRequest.builder().tid(orderCode).build())
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

        LakalaCasherTradeQueryRequest lakalaTradeQueryRequest = new LakalaCasherTradeQueryRequest();
        lakalaTradeQueryRequest.setOutOrderNo(request.getBusinessId());
        lakalaTradeQueryRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
        LakalaCasherPayBaseRequest lakalaPayBaseRequest = new LakalaCasherPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayBaseRequest.setReqData(lakalaTradeQueryRequest);
        String body = JSON.toJSONString(lakalaPayBaseRequest);
        LakalaCasherUtils.getPayGateway();
        String authorization = LakalaCasherUtils.getAuthorization(body);
        LakalaPayBaseResponse<LakalaCasherTradeQueryResponse> baseResponse =
                LakalaCasherUtils.postForPay(
                        trade_query, body, authorization, LakalaCasherTradeQueryResponse.class);
        if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
            log.error("lakala pay trade query error:{}", baseResponse.getMsg());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return BaseResponse.success(baseResponse.getRespData());
    }

    @Override
    public BaseResponse payCloseOrder(PayCloseOrderRequest request) {
        log.info("拉卡拉收银台关单接口入参PayCloseOrderRequest：{}", JSON.toJSONString(request));
        LakalaCasherCloseRequest lakalaCloseRequest = new LakalaCasherCloseRequest();
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
        lakalaCloseRequest.setOutOrderNo(businessId);
        lakalaCloseRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());


        LakalaCasherUtils.getPayGatewayIgnoreSwitch();
        LakalaCasherPayBaseRequest lakalaPayBaseRequest = new LakalaCasherPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayBaseRequest.setReqData(lakalaCloseRequest);
        String body = JSON.toJSONString(lakalaPayBaseRequest);
        String authorization = LakalaCasherUtils.getAuthorization(body);
        LakalaPayBaseResponse<LakalaCloseResponse> baseResponse =
                LakalaCasherUtils.postForPay(close, body, authorization, LakalaCloseResponse.class);
        if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
            log.error("lakala pay trade close error:{}", baseResponse.getMsg());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return BaseResponse.success(baseResponse.getRespData());
    }

    @Override
    public BaseResponse payRefund(PayRefundBaseRequest request) {
        log.info("拉卡拉收银台退款接口入参PayRefundBaseRequest：{}", JSON.toJSONString(request));
        LakalaCasherUtils.getPayGatewayIgnoreSwitch();
        LakalaIdCasherRefundRequest lakalaIdRefundRequest = request.getLakalaIdCasherRefundRequest();
        LakalaPayBaseRequest lakalaPayBaseRequest = new LakalaPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayBaseRequest.setReqData(lakalaIdRefundRequest);
        String body = JSON.toJSONString(lakalaPayBaseRequest);
        String authorization = LakalaCasherUtils.getAuthorization(body);
        LakalaPayBaseResponse<LakalaIdCasherRefundResponse> baseResponse =
                LakalaCasherUtils.postForPay(
                        trade_refund, body, authorization, LakalaIdCasherRefundResponse.class);
//        if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
//            log.error("lakala casher refund api error:{}", baseResponse.getMsg());
//            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, baseResponse.getMsg());
//        }
        return BaseResponse.success(baseResponse);
    }

    @Override
    public BaseResponse pay(BasePayRequest basePayRequest) {
        log.info("拉卡拉收银台支付接口入参basePayRequest：{}", JSON.toJSONString(basePayRequest));
        PayGateway payGateway = LakalaCasherUtils.getPayGateway();
        LakalaCasherAllPayRequest lakalaPayRequest = basePayRequest.getLakalaCasherAllPayRequest();
        LakalaCasherPayBaseRequest lakalaPayBaseRequest = new LakalaCasherPayBaseRequest();
        lakalaPayBaseRequest.setReqTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaPayRequest.setNotifyUrl(
                payGateway.getConfig().getBossBackUrl().concat(LakalaCasherUtils.LA_KA_LA_CASHER_PAY_CALLBACK_URL));

        lakalaPayRequest.setSupportCancel(Constants.STR_1);
        lakalaPayRequest.setSupportRefund(Constants.STR_1);
        lakalaPayRequest.setSupportRepeatPay(Constants.STR_1);
        lakalaPayRequest.setSettleType(Constants.STR_1);
        if(LklPayType.ALIPAY.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"ALIPAY\"}");
        } else if(LklPayType.WECHAT.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"WECHAT\"}");
        } else if(LklPayType.UNION.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"UNION\"}");
        } else if(LklPayType.CARD.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"CARD\"}");
        } else if(LklPayType.LKLAT.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"LKLAT\"}");
        } else if(LklPayType.QUICK_PAY.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"QUICK_PAY\"}");
        } else if(LklPayType.EBANK.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"EBANK\"}");
        } else if(LklPayType.UNION_CC.equals(basePayRequest.getLklPayType())){
            lakalaPayRequest.setCounterParam("{\"pay_mode\" : \"UNION_CC\"}");
        }

        lakalaPayBaseRequest.setReqData(lakalaPayRequest);
        String body = JSON.toJSONString(lakalaPayBaseRequest);
        String authorization = LakalaCasherUtils.getAuthorization(body);
        LakalaPayBaseResponse<LakalaCasherAllPayResponse> baseResponse =
                LakalaCasherUtils.postForPay(
                        createOrder, body, authorization, LakalaCasherAllPayResponse.class);
        if (!LAKALA_PAY_SUCCESS_CODE.equals(baseResponse.getCode())) {
            log.error("lakala casher pay api error:{}", baseResponse.getMsg());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        // 添加交易记录
        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
        payTradeRecordRequest.setBusinessId(basePayRequest.getOrderCode());
        payTradeRecordRequest.setApplyPrice(
                new BigDecimal(lakalaPayRequest.getTotalAmount())
                        .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
        payTradeRecordRequest.setClientIp(HttpUtil.getIpAddr());
        payTradeRecordRequest.setChannelItemId(
                Long.parseLong(basePayRequest.getChannelItemId()));
        payTradeRecordProvider.queryAndSave(payTradeRecordRequest);
        return BaseResponse.success(baseResponse.getRespData());
    }
}
