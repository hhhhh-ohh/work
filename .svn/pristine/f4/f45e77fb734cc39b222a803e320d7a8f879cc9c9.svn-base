package com.wanmi.sbc.empower.pay.service;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.RefundRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.AliPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaIdCasherRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaIdRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unionb2b.UnionB2bPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionCloudPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxChannelsPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayRefundRequest;
import com.wanmi.sbc.empower.api.response.pay.BalanceRefundResponse;
import com.wanmi.sbc.empower.api.response.pay.ali.AliPayRefundResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaIdCasherRefundResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaIdRefundResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaPayBaseResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.PayRefundResponse;
import com.wanmi.sbc.empower.bean.enums.*;
import com.wanmi.sbc.empower.pay.model.root.PayChannelItem;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.repository.ChannelItemRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import com.wanmi.sbc.empower.pay.repository.GatewayRepository;
import com.wanmi.sbc.empower.pay.sdk.SDKConstants;
import com.wanmi.sbc.empower.pay.service.unionb2b.UnionB2bPayService;
import com.wanmi.sbc.empower.pay.service.unioncloud.UnionCloudPayServiceImpl;
import com.wanmi.sbc.empower.pay.utils.PayGateWayUtils;
import com.wanmi.sbc.empower.pay.utils.PayValidates;
import com.wanmi.sbc.order.api.provider.paycallback.PayAndRefundCallBackProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.refundcallbackresult.RefundCallBackResultProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordByParamsRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordDeleteAndSaveRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultAddRequest;
import com.wanmi.sbc.order.api.request.trade.FindByTailOrderNoInRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListByParentIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdResponse;
import com.wanmi.sbc.order.api.response.trade.TradeListByParentIdResponse;
import com.wanmi.sbc.order.bean.dto.RefundCallBackResultDTO;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayConstants;

import io.seata.spring.annotation.GlobalTransactional;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>支付交易service</p>
 * Created by of628-wenzhi on 2017-08-02-下午5:44.
 */
@Service
@Validated
@Slf4j
public class PayService {

    private static final String WXPAYATYPEFORPC = "PC"; //微信支付类型--为PC/H5/JSAPI，对应调用参数对应公众平台参数

    private static final String WXPAYATYPEFORH5 = "H5";

    private static final String WXPAYATYPEFORJSAPI = "JSAPI";

    private static final String WXPAYAPPTYPE = "APP"; //微信支付类型--为app，对应调用参数对应开放平台参数

    @Resource
    private ChannelItemRepository channelItemRepository;

    @Resource
    private GatewayRepository gatewayRepository;

    @Resource
    private GatewayConfigRepository gatewayConfigRepository;


    @Autowired
    private UnionCloudPayServiceImpl unionCloudPayService;

    @Autowired
    private UnionB2bPayService unionB2bPayService;

    @Autowired
    private PayServiceFactory payServiceFactory;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private RefundCallBackResultProvider refundCallBackResultProvider;

    @Autowired
    private PayAndRefundCallBackProvider payCallBackProvider;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    /**
     * 退款
     *
     * @param request
     */
    @GlobalTransactional
    @Transactional(noRollbackFor = SbcRuntimeException.class)
    public Object refund(@Valid RefundRequest request) {
        //重复退款校验
        TradeStatus status = queryRefundResult(request.getRefundBusinessId());
        if (!Objects.isNull(status)) {
            if (status == TradeStatus.SUCCEED) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060032);
            } else if (status == TradeStatus.PROCESSING) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060033);
            }
        }
        PayTradeRecordByParamsRequest payTradeRecordByParamsRequest = new PayTradeRecordByParamsRequest();
        payTradeRecordByParamsRequest.setBusinessId(request.getBusinessId());
        payTradeRecordByParamsRequest.setTradeStatus(TradeStatus.SUCCEED);
        //未退款或退款失败的退单，调用网关执行退款操作
        PayTradeRecordResponse payRecord = payTradeRecordQueryProvider.findTopByBusinessIdAndStatus(payTradeRecordByParamsRequest).getContext();
        Long storeId = Constants.BOSS_DEFAULT_STORE_ID;
        request.setStoreId(storeId);
        PayChannelItem item = getPayChannelItem(payRecord.getChannelItemId(), storeId);
        //历史订单兼容，如果没有payNo则拿订单号填充
        if (StringUtils.isEmpty(request.getPayNo())) {
            request.setPayNo(request.getBusinessId());
        }
        if (payRecord.getChannelItemId().equals(Constants.NUM_30L)) {
            //微信视频好退单
            PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
            WxChannelsPayRefundRequest refundRequest = new WxChannelsPayRefundRequest();
            refundRequest.setOut_aftersale_id(request.getRefundBusinessId());
            payRefundBaseRequest.setWxChannelsRefundRequest(refundRequest);

            PayBaseService payBaseService = payServiceFactory.create(PayType.WXCHANNELSPAY);
            BaseResponse baseResponse = payBaseService.payRefund(payRefundBaseRequest);
            if (baseResponse.isSuccess()) {
                saveRefundRecord(request, item, TradeStatus.SUCCEED);
            } else {
                saveRefundRecord(request, item, TradeStatus.FAILURE);
            }
            return null;
        } else if (payRecord.getChannelItemId().equals(Constants.NUM_11L)) {
            //银联企业支付退款
            PayValidates.verifyGateway(item.getGateway());
            PayTradeRecordResponse record = saveRefundRecord(request, item, null);
//            payRecord.setBusinessId(record.getBusinessId());
            record.setTradeNo(payRecord.getTradeNo());
            PayGatewayConfig gatewayConfig =
                    gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.UNIONB2B, storeId);
            UnionB2bPayRefundRequest unionB2bPayRefundRequest = unionB2bPayService.buildUnionB2bPayRefundRequest(record
                    , gatewayConfig, payRecord);
            PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
            payRefundBaseRequest.setUnionB2bPayRefundRequest(unionB2bPayRefundRequest);
            PayBaseService payBaseService = payServiceFactory.create(PayType.UNIONB2BPAY);
            Map<String, String> resultMap = (Map<String, String>) payBaseService.payRefund(payRefundBaseRequest).getContext();
            log.info(">>>>>>>>>>>>>>>>>>respCode:{} respMsg:{}", resultMap.get("respCode"), resultMap.get("respMsg"));
//            if (Constants.STR_00.equals(resultMap.get("respCode"))) {
//                record.setTradeNo(resultMap.get("orderId"));
//                recordRepository.save(record);
//            } else {
            //提交退款申请失败
            //throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060033);
//            }
            return resultMap;
        } else if (payRecord.getChannelItemId().equals(Constants.NUM_14L) || payRecord.getChannelItemId().equals(Constants.NUM_15L) ||
                payRecord.getChannelItemId().equals(Constants.NUM_16L)) {
            //微信支付退款--PC、H5、JSAPI支付对应付退款
            return wxPayRefundForPcH5Jsapi(item, request, payRecord);
        } else if (payRecord.getChannelItemId().equals(Constants.NUM_20L)) {
            //微信支付--app支付退款
            return wxPayRefundForApp(item, request, payRecord);
        } else if (Objects.equals(payRecord.getChannelItemId(), Constants.NUM_17L) || Objects.equals(payRecord.getChannelItemId(), Constants.NUM_18L) || Objects.equals(payRecord.getChannelItemId(), Constants.NUM_19L)) {
            //支付宝退款
            PayTradeRecordResponse data = saveRefundRecord(request, item, null);
            AliPayRefundRequest aliPayRefundRequest = KsBeanUtil.convert(request, AliPayRefundRequest.class);
            //退单使用原来的支付单号
            aliPayRefundRequest.setBusinessId(request.getPayNo());
            aliPayRefundRequest.setAppid(item.getGateway().getConfig().getAppId());
            aliPayRefundRequest.setAppPrivateKey(item.getGateway().getConfig().getPrivateKey());
            aliPayRefundRequest.setAliPayPublicKey(item.getGateway().getConfig().getPublicKey());
            PayBaseService payBaseService = payServiceFactory.create(PayType.ALIPAY);
            PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
            payRefundBaseRequest.setAliPayRefundRequest(aliPayRefundRequest);
            AliPayRefundResponse aliPayRefundResponse = (AliPayRefundResponse) payBaseService.payRefund(payRefundBaseRequest).getContext();

            AlipayTradeRefundResponse payRefundResponse = aliPayRefundResponse.getAlipayTradeRefundResponse();

            if (Objects.isNull(payRefundResponse)) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060011);
            }

            if (payRefundResponse.getCode().equals(Constants.STR_10000)
                    && payRefundResponse.getFundChange().equals(SDKConstants.Y)) {
                //更新记录
                data.setChargeId(request.getRefundBusinessId());
                data.setTradeNo(aliPayRefundResponse.getAlipayTradeRefundResponse().getTradeNo());
                data.setFinishTime(LocalDateTime.now());
                data.setStatus(TradeStatus.SUCCEED);
                data.setCallbackTime(LocalDateTime.now());
                data.setPracticalPrice(new BigDecimal(aliPayRefundResponse.getAlipayTradeRefundResponse().getRefundFee()));
                PayTradeRecordRequest payTradeRecordRequest = KsBeanUtil.convert(data, PayTradeRecordRequest.class);
                payTradeRecordProvider.saveAndFlush(payTradeRecordRequest);
            } else {
                //退款失败
                log.info(">>>>>>>>>>>>>>>>>>支付宝退款失败:err_code{} respMsg:{}", payRefundResponse.getCode(),
                        payRefundResponse.getSubMsg());
                String errMsg = "退款失败原因：";

                errMsg = errMsg + payRefundResponse.getSubMsg() + ";";

                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060011, new Object[]{errMsg});
            }
            return aliPayRefundResponse;
        } else if (PayGateWayUtils.isBalance(payRecord.getChannelItemId())
                || PayGateWayUtils.isCredit(payRecord.getChannelItemId())) {
            // 余额支付、授信支付退款操作
            // add by zhengyang for Task: 【ID1035468】 授信支付退款和余额支付同样处理
            // 根据支付渠道ID判断是哪条渠道支付的
            PayGatewayEnum payGateway = PayGateWayUtils
                    .isBalance(payRecord.getChannelItemId())
                    ? PayGatewayEnum.BALANCE
                    : PayGatewayEnum.CREDIT;

            return balanceRefund(item, request, payGateway);
        } else if (payRecord.getChannelItemId().equals(Constants.NUM_27L) || payRecord.getChannelItemId().equals(Constants.NUM_28L)
                || payRecord.getChannelItemId().equals(Constants.NUM_29L)) {
            //银联支付退款
            PayValidates.verifyGateway(item.getGateway());
            PayTradeRecordResponse record = saveRefundRecord(request, item, null);
            record.setTradeNo(payRecord.getTradeNo());
            PayGatewayConfig gatewayConfig =
                    gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.UNIONPAY, storeId);
            UnionCloudPayRefundRequest unionPayRefundRequest = unionCloudPayService.buildUnionPayRefundRequest(record,
                    gatewayConfig, true);
            Map<String, String> resultMap =
                    (Map<String, String>) unionCloudPayService.dealPayRefund(unionPayRefundRequest).getContext();
            log.info(">>>>>>>>>>>>>>>>>>respCode:" + resultMap.get("respCode") + "respMsg:" + resultMap.get("respMsg"));
            if (Constants.STR_00.equals(resultMap.get("respCode"))) {
                record.setTradeNo(resultMap.get("orderId"));
                PayTradeRecordRequest payTradeRecordRequest = KsBeanUtil.convert(record, PayTradeRecordRequest.class);
                payTradeRecordProvider.saveAndFlush(payTradeRecordRequest);
            } else {
                //退款失败
                log.info(">>>>>>>>>>>>>>>>>>银联支付退款失败:respCode" + resultMap.get("respCode") + "respMsg:" + resultMap.get("respMsg"));
                String errMsg = "退款失败原因：";
                errMsg = errMsg + resultMap.get("respMsg") + ";";
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060014, new Object[]{errMsg});
            }
            return resultMap;
        } else if (Arrays.asList(
                        Constants.NUM_31L,
                        Constants.NUM_32L,
                        Constants.NUM_34L,
                        Constants.NUM_35L,
                        Constants.NUM_37L,
                        Constants.NUM_38L,
                        Constants.NUM_39L,
                        Constants.NUM_40L)
                .contains(payRecord.getChannelItemId())) {
            PayTradeRecordResponse record = saveRefundRecord(request, item, null);
            record.setTradeNo(payRecord.getTradeNo());
            String businessId = request.getBusinessId();
            List<TradeVO> trades = new ArrayList<>();
            if (businessId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID)) {
                List<TradeVO> tradeVOList = tradeQueryProvider
                        .findByTailOrderNoIn(FindByTailOrderNoInRequest.builder()
                                .tailOrderIds(Collections.singletonList(businessId)).build())
                        .getContext().getTradeVOList();
                if (CollectionUtils.isNotEmpty(tradeVOList)) {
                    trades.add(tradeVOList.get(Constants.ZERO));
                }
            } else if (businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID)
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
                            .max(
                                    Comparator.comparing(
                                            trade2 -> trade2.getTradePrice().getTotalPrice()))
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

            PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
            PayBaseService payBaseService;
            if (Constants.NUM_39L.equals(payRecord.getChannelItemId()) || Constants.NUM_40L.equals(payRecord.getChannelItemId())) {
                LakalaIdCasherRefundRequest lakalaIdRefundRequest = new LakalaIdCasherRefundRequest();
                TradeVO tradeVO = trades.stream().filter(t -> t.getId().equals(request.getTid())).findFirst().get();
                if (request.getBusinessId().startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID)) {
                    lakalaIdRefundRequest.setOriginLogNo(tradeVO.getLklOrderTradeInfo().getTailLogNo());
                } else {
                    lakalaIdRefundRequest.setOriginLogNo(tradeVO.getLklOrderTradeInfo().getLogNo());
                }
                lakalaIdRefundRequest.setMerchantNo(tradeVO.getLklOrderTradeInfo().getMerchantNo());
                lakalaIdRefundRequest.setTermNo(tradeVO.getLklOrderTradeInfo().getTermNo());
                lakalaIdRefundRequest.setOutTradeNo(request.getRefundBusinessId());
                lakalaIdRefundRequest.setRefundAmount(
                        request.getAmount()
                                .multiply(new BigDecimal(100))
                                .setScale(0, RoundingMode.DOWN)
                                .toString());
                if (tradeVO.getLklOrderTradeInfo().getBusiType().contains("ONLINE_")) {
                    //网银
                    lakalaIdRefundRequest.setOriginBizType("4");
                    lakalaIdRefundRequest.setOriginCardNo(tradeVO.getLklOrderTradeInfo().getPayerAccountNo());
                } else if (tradeVO.getLklOrderTradeInfo().getBusiType().equals("SCPAY")) {
                    //支付宝微信扫码
                    lakalaIdRefundRequest.setOriginBizType("3");
                }
                lakalaIdRefundRequest.setOriginTradeDate(tradeVO.getLklOrderTradeInfo().getTradeTime().substring(0, 8));
                lakalaIdRefundRequest.setLocationInfo(
                        new LakalaIdCasherRefundRequest.LocationInfo(maxPriceTrade.getRequestIp()));
                payBaseService = payServiceFactory.create(PayType.LAKALA_CASHER_PAY);
                payRefundBaseRequest.setLakalaIdCasherRefundRequest(lakalaIdRefundRequest);
                LakalaPayBaseResponse lakalaIdRefundResponse = (LakalaPayBaseResponse)
                        payBaseService.payRefund(payRefundBaseRequest).getContext();
                LakalaIdCasherRefundResponse lakalaIdCasherRefundResponse = (LakalaIdCasherRefundResponse)lakalaIdRefundResponse.getRespData();
                RefundCallBackResultAddRequest refundCallBackResultAddRequest = new RefundCallBackResultAddRequest(RefundCallBackResultDTO.builder()
                        .businessId(request.getRefundBusinessId())
                        .resultXml(JSON.toJSONString(lakalaIdCasherRefundResponse))
                        .resultContext(JSON.toJSONString(lakalaIdCasherRefundResponse))
                        .resultStatus(PayCallBackResultStatus.TODO)
                        .errorNum(0)
                        .payType(PayCallBackType.LAKALA_CASHER)
                        .createTime(LocalDateTime.now())
                        .build());
                refundCallBackResultProvider.add(refundCallBackResultAddRequest);
                if (Constants.SUCCESS_000000.equals(lakalaIdRefundResponse.getCode())) {
                    // 更新记录
                    record.setTradeNo(lakalaIdCasherRefundResponse.getLogNo());
                    PayTradeRecordRequest payTradeRecordRequest =
                            KsBeanUtil.convert(record, PayTradeRecordRequest.class);
                    payTradeRecordProvider.saveAndFlush(payTradeRecordRequest);
                    payCallBackProvider.refundCallBack(
                            TradeRefundOnlineCallBackRequest.builder()
                                    .out_refund_no(request.getRefundBusinessId())
                                    .lakalaIdRefundRequest(JSON.toJSONString(lakalaIdCasherRefundResponse))
                                    .payCallBackType(PayCallBackType.LAKALA_CASHER)
                                    .build());
                } else {
                    //退款失败
                    log.info(">>>>>>>>>>>>>>>>>>收银台退款失败:err_code{} respMsg:{}", lakalaIdRefundResponse.getCode(),
                            lakalaIdRefundResponse.getMsg());
                    String errMsg = "退款失败原因：";

                    errMsg = errMsg + lakalaIdRefundResponse.getMsg() + ";";

                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060044, new Object[]{errMsg});
                }
                return lakalaIdRefundResponse;
            } else {
                LakalaIdRefundRequest lakalaIdRefundRequest = new LakalaIdRefundRequest();
                lakalaIdRefundRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
                lakalaIdRefundRequest.setTermNo(ledgerAccountVO.getTermNo());
                lakalaIdRefundRequest.setOutRefundOrderNo(request.getRefundBusinessId());
                lakalaIdRefundRequest.setRefundAmount(
                        request.getAmount()
                                .multiply(new BigDecimal(100))
                                .setScale(0, RoundingMode.DOWN)
                                .toString());
                lakalaIdRefundRequest.setOriginTradeNo(payRecord.getTradeNo());
                lakalaIdRefundRequest.setLocationInfo(
                        new LakalaIdRefundRequest.LocationInfo(maxPriceTrade.getRequestIp()));
                payBaseService = payServiceFactory.create(PayType.LAKALA_PAY);
                payRefundBaseRequest.setLakalaIdRefundRequest(lakalaIdRefundRequest);
                LakalaIdRefundResponse lakalaIdRefundResponse = (LakalaIdRefundResponse)
                        payBaseService.payRefund(payRefundBaseRequest).getContext();
                // 更新记录
                record.setTradeNo(lakalaIdRefundResponse.getTradeNo());
                PayTradeRecordRequest payTradeRecordRequest =
                        KsBeanUtil.convert(record, PayTradeRecordRequest.class);
                payTradeRecordProvider.saveAndFlush(payTradeRecordRequest);
                RefundCallBackResultAddRequest refundCallBackResultAddRequest = new RefundCallBackResultAddRequest(RefundCallBackResultDTO.builder()
                        .businessId(request.getRefundBusinessId())
                        .resultXml(JSON.toJSONString(lakalaIdRefundResponse))
                        .resultContext(JSON.toJSONString(lakalaIdRefundResponse))
                        .resultStatus(PayCallBackResultStatus.TODO)
                        .errorNum(0)
                        .payType(PayCallBackType.LAKALA)
                        .createTime(LocalDateTime.now())
                        .build());
                refundCallBackResultProvider.add(refundCallBackResultAddRequest);
                payCallBackProvider.refundCallBack(
                        TradeRefundOnlineCallBackRequest.builder()
                                .out_refund_no(request.getRefundBusinessId())
                                .lakalaIdRefundRequest(JSON.toJSONString(lakalaIdRefundResponse))
                                .payCallBackType(PayCallBackType.LAKALA)
                                .build());

                return lakalaIdRefundResponse;
            }
        } else {
            return null;
        }
    }

    /**
     * @return com.wanmi.sbc.empower.api.response.PayRefundResponse
     * @Author lvzhenwei
     * @Description 余额支付订单在线退款
     * @Date 15:59 2019/7/11
     * @Param [item, request, payRecord]
     **/
    private BalanceRefundResponse balanceRefund(PayChannelItem item, RefundRequest request, PayGatewayEnum payGateway) {
        PayTradeRecordResponse data = saveRefundRecord(request, item, null);
        // 更新记录
        data.setChargeId(request.getRefundBusinessId());
        data.setFinishTime(LocalDateTime.now());
        data.setStatus(TradeStatus.SUCCEED);
        data.setCallbackTime(LocalDateTime.now());
        data.setPracticalPrice(request.getAmount());
        PayTradeRecordRequest payTradeRecordRequest = KsBeanUtil.convert(data, PayTradeRecordRequest.class);
        payTradeRecordProvider.saveAndFlush(payTradeRecordRequest);
        BalanceRefundResponse balanceRefundResponse = new BalanceRefundResponse();
        balanceRefundResponse.setPayType(payGateway.name());
        return balanceRefundResponse;
    }

    /**
     * 微信支付--PC、H5、JSAPI支付对应付退款
     *
     * @param item
     * @param request
     * @param payRecord
     * @return
     */
    private PayRefundResponse wxPayRefundForPcH5Jsapi(PayChannelItem item, RefundRequest request,
                                                      PayTradeRecordResponse payRecord) {
        PayValidates.verifyGateway(item.getGateway());
        PayTradeRecordResponse record = saveRefundRecord(request, item, null);
        record.setTradeNo(payRecord.getTradeNo());
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        if (payRecord.getChannelItemId().equals(Constants.NUM_14L)) {//pc native支付
            refundRequest.setPay_type(WXPAYATYPEFORPC);
            refundRequest.setPayType(WXPAYATYPEFORPC);
        } else if (payRecord.getChannelItemId().equals(Constants.NUM_15L)) {//微信支付h5支付
            refundRequest.setPay_type(WXPAYATYPEFORH5);
            refundRequest.setPayType(WXPAYATYPEFORH5);
        } else if (payRecord.getChannelItemId().equals(Constants.NUM_16L)) {//微信支付jsapi支付
            refundRequest.setPay_type(WXPAYATYPEFORJSAPI);
            refundRequest.setPayType(WXPAYATYPEFORJSAPI);
        }
        refundRequest.setRefund_type("WXPAY");
        refundRequest.setOut_refund_no(request.getRefundBusinessId());
//        refundRequest.setOut_trade_no(request.getBusinessId());
        //退单使用原来的支付单号
        refundRequest.setOut_trade_no(request.getPayNo());
        refundRequest.setTotal_fee(request.getTotalPrice().multiply(new BigDecimal(100)).
                setScale(0, RoundingMode.DOWN).toString());
        refundRequest.setRefund_fee(request.getAmount().multiply(new BigDecimal(100)).
                setScale(0, RoundingMode.DOWN).toString());
        refundRequest.setStoreId(request.getStoreId());
        PayBaseService payBaseService = payServiceFactory.create(PayType.WXPAY);
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setWxPayRefundRequest(refundRequest);
        PayRefundResponse payRefundResponse = (PayRefundResponse) payBaseService.payRefund(payRefundBaseRequest).getContext();

        if (payRefundResponse.getReturn_code().equals(WXPayConstants.SUCCESS) &&
                payRefundResponse.getResult_code().equals(WXPayConstants.SUCCESS)) {
            record.setTradeNo(payRefundResponse.getTransaction_id());
            PayTradeRecordRequest payTradeRecordRequest = KsBeanUtil.convert(record, PayTradeRecordRequest.class);
            payTradeRecordProvider.saveAndFlush(payTradeRecordRequest);
        } else {
            //退款失败
            log.info(">>>>>>>>>>>>>>>>>>微信退款失败:return_code{} respMsg:{}", payRefundResponse.getReturn_code(),
                    payRefundResponse.getReturn_msg());
            log.info(">>>>>>>>>>>>>>>>>>微信退款失败:err_code{} respMsg:{}", payRefundResponse.getErr_code(),
                    payRefundResponse.getErr_code_des());
            String errMsg = "退款失败原因：";
            if (!WXPayConstants.SUCCESS.equals(payRefundResponse.getReturn_code())) {
                errMsg = errMsg + payRefundResponse.getReturn_msg() + ";";
            }
            if (!WXPayConstants.SUCCESS.equals(payRefundResponse.getResult_code())) {
                errMsg = errMsg + payRefundResponse.getErr_code_des() + ";";
            }
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060012, new Object[]{errMsg});
        }
        return payRefundResponse;
    }

    /**
     * 微信支付退款--app支付退款
     *
     * @param item
     * @param request
     * @param payRecord
     * @return
     */
    private PayRefundResponse wxPayRefundForApp(PayChannelItem item, RefundRequest request, PayTradeRecordResponse payRecord) {
        //微信支付退款
        PayValidates.verifyGateway(item.getGateway());
        PayTradeRecordResponse record = saveRefundRecord(request, item, null);
        record.setTradeNo(payRecord.getTradeNo());
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        refundRequest.setPay_type(WXPAYAPPTYPE);
        refundRequest.setRefund_type("WXPAY");
        refundRequest.setOut_refund_no(request.getRefundBusinessId());
//        refundRequest.setOut_trade_no(request.getBusinessId());
        //退单使用原来的支付单号
        refundRequest.setOut_trade_no(request.getPayNo());
        refundRequest.setTotal_fee(request.getTotalPrice().multiply(new BigDecimal(100)).
                setScale(0, RoundingMode.DOWN).toString());
        refundRequest.setRefund_fee(request.getAmount().multiply(new BigDecimal(100)).
                setScale(0, RoundingMode.DOWN).toString());
        refundRequest.setPayType(WXPAYAPPTYPE);
        refundRequest.setStoreId(request.getStoreId());
        PayBaseService payBaseService = payServiceFactory.create(PayType.WXPAY);
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setWxPayRefundRequest(refundRequest);
        PayRefundResponse payRefundResponse = (PayRefundResponse) payBaseService.payRefund(payRefundBaseRequest).getContext();
        if (Objects.nonNull(payRefundResponse) && WXPayConstants.SUCCESS.equals(payRefundResponse.getResult_code())) {
            record.setTradeNo(payRefundResponse.getTransaction_id());
            PayTradeRecordRequest payTradeRecordRequest = KsBeanUtil.convert(record, PayTradeRecordRequest.class);
            payTradeRecordProvider.saveAndFlush(payTradeRecordRequest);
        } else {
            //提交退款申请失败
            //退款失败
            String errMsg = "微信支付退款：";
            if (Objects.isNull(payRefundResponse)) {
                log.info(">>>>>>>>>>>>>>>>>>微信退款失败:wxPayRefundResponse为null");
            } else {
                log.info(">>>>>>>>>>>>>>>>>>微信退款失败:return_code{} respMsg:{}", payRefundResponse.getReturn_code(),
                        payRefundResponse.getReturn_msg());
                log.info(">>>>>>>>>>>>>>>>>>微信退款失败:err_code{} respMsg:{}", payRefundResponse.getErr_code(),
                        payRefundResponse.getErr_code_des());
                if (StringUtils.isNotBlank(payRefundResponse.getErr_code())) {
                    errMsg = errMsg + payRefundResponse.getErr_code() + ";";
                }
                if (StringUtils.isNotBlank(payRefundResponse.getErr_code_des())) {
                    errMsg = errMsg + payRefundResponse.getErr_code_des() + ";";
                }
            }
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060012, new Object[]{errMsg});

        }
        return payRefundResponse;
    }

    /**
     * 根据退单与相关订单号号查询退单退款状态
     *
     * @param rid 业务退单号
     * @return null-无退款记录 | TradeStatus-退款状态
     */
    @Transactional
    public TradeStatus queryRefundResult(String rid) {
        PayTradeRecordResponse refundRecord =
                payTradeRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
                        (rid)).getContext();
        if (!Objects.isNull(refundRecord)) {
            if (refundRecord.getStatus() == TradeStatus.SUCCEED) {
                return TradeStatus.SUCCEED;
            } else if (refundRecord.getStatus() == TradeStatus.PROCESSING && !Objects.isNull(refundRecord.getChargeId())) {
                //处理中退单，跟踪状态
                return TradeStatus.FAILURE;
            }
        }
        return null;
    }

    private PayChannelItem getPayChannelItem(Long channelItemId, Long storeId) {
        PayChannelItem item = channelItemRepository.getOne(channelItemId);
        PayValidates.verfiyPayChannelItem(item);
        // 获取网关
        PayGateway gateway = gatewayRepository.queryByNameAndStoreId(item.getGatewayName(), storeId);
        item.setGateway(gateway);
        return item;
    }

    private PayTradeRecordResponse saveRefundRecord(RefundRequest request, PayChannelItem channelItem, TradeStatus tradeStatus) {
        PayTradeRecordDeleteAndSaveRequest recordDeleteAndSaveRequest = new PayTradeRecordDeleteAndSaveRequest();
        recordDeleteAndSaveRequest.setApplyPrice(request.getAmount());
        recordDeleteAndSaveRequest.setBusinessId(request.getRefundBusinessId());
        recordDeleteAndSaveRequest.setClientIp(request.getClientIp());
        recordDeleteAndSaveRequest.setChannelItemId(channelItem.getId());
        recordDeleteAndSaveRequest.setTradeType(TradeType.REFUND);
        recordDeleteAndSaveRequest.setStatus(tradeStatus);
        recordDeleteAndSaveRequest.setPayNo(request.getRefundBusinessId());
        if (Objects.isNull(tradeStatus)) {
            recordDeleteAndSaveRequest.setStatus(TradeStatus.PROCESSING);
        }
        recordDeleteAndSaveRequest.setCreateTime(LocalDateTime.now());

        return payTradeRecordProvider.deleteAndSave(recordDeleteAndSaveRequest).getContext();
    }

    // 商户发送交易时间 格式:YYYYMMDDhhmmss
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    // AN8..40 商户订单号，不能含"-"或"_"
    public static String getOrderId() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

}

