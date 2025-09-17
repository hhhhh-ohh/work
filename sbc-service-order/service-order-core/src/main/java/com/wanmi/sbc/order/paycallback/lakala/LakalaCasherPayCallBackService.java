package com.wanmi.sbc.order.paycallback.lakala;

import com.alibaba.fastjson.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaCasherNotifyRequest;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaCasherTradeQueryResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaIdCasherRefundResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.RefundChannel;
import com.wanmi.sbc.order.bean.vo.LklOrderTradeInfoVO;
import com.wanmi.sbc.order.paycallback.PayAndRefundCallBackBaseService;
import com.wanmi.sbc.order.paycallback.PayCallBackUtil;
import com.wanmi.sbc.order.paycallbackresult.model.root.PayCallBackResult;
import com.wanmi.sbc.order.paycallbackresult.service.PayCallBackResultService;
import com.wanmi.sbc.order.payorder.service.PayOrderService;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytimeseries.service.PayTimeSeriesService;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.refundcallbackresult.service.RefundCallBackResultService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.order.util.GeneratorUtils;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayConstants;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className LakalaCasherPayCallBackService
 * @description 拉卡拉收银台回调处理
 * @date 2023/7/25 15:30
 */
@Service
@Slf4j
public class LakalaCasherPayCallBackService implements PayAndRefundCallBackBaseService {

    @Autowired
    private PayCallBackUtil payCallBackUtil;

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    @Autowired
    private PayCallBackResultService payCallBackResultService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private RefundCallBackResultService refundCallBackResultService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayProvider payProvider;

    @Autowired
    private PayTimeSeriesService payTimeSeriesService;

    @Override
    public BaseResponse payCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        LakalaCasherNotifyRequest notifyRequest =
                JSON.parseObject(
                        tradePayOnlineCallBackRequest.getLakalaPayCallBack(), LakalaCasherNotifyRequest.class);
        // 订单id
        PayTimeSeries payTimeSeries = payTimeSeriesService.getOne(notifyRequest.getOutOrderNo());
        if(payTimeSeries == null){
            log.error("查询不到支付流水记录，流水号为：{}", notifyRequest.getOutOrderNo());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        String tid = payTimeSeries.getBusinessId();
        log.info("lakala casher pay call back start,notifyRequest：{}", notifyRequest);
        boolean isMergePay = payCallBackUtil.isMergePayOrder(tid);
        String lockName;
        // 非组合支付，则查出该单笔订单。
        if (isMergePay) {
            lockName = tid;
        } else {
            Trade trade;
            if (payCallBackUtil.isTailPayOrder(tid)) {
                trade =
                        tradeService
                                .queryAll(TradeQueryRequest.builder().tailOrderNo(tid).build())
                                .get(0);
            } else {
                trade = tradeService.detail(tid);
            }
            // 锁资源：无论是否组合支付，都锁父单号，确保串行回调
            lockName = trade.getParentId();
        }
        // redis锁，防止同一订单重复回调
        RLock rLock = redissonClient.getFairLock(lockName);
        // 执行回调
        try {
            rLock.lock();
            payCallBackEvent(notifyRequest, isMergePay, payTimeSeries);
            log.info("lakala casher pay call back end");
        } catch (Exception e) {
            log.error("goods order lakala pay call back exception：{}", e);
            // 支付处理结果回写回执支付结果表
            payCallBackResultService.updateStatus(tid, PayCallBackResultStatus.FAILED);
        } finally {
            // 解锁
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse refundCallBack(
            TradeRefundOnlineCallBackRequest tradeRefundOnlineCallBackRequest) {
        String out_refund_no = tradeRefundOnlineCallBackRequest.getOut_refund_no();
        LakalaIdCasherRefundResponse lakalaIdRefundResponse =
                JSON.parseObject(
                        tradeRefundOnlineCallBackRequest.getLakalaIdRefundRequest(),
                        LakalaIdCasherRefundResponse.class);

        //  退款状态   SUCCESS SUCCESS-退款成功、CHANGE-退款异常、REFUNDCLOSE—退款关闭
        PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(out_refund_no);

        ReturnOrder returnOrder;
        if (out_refund_no.startsWith(Constants.STR_RT)) {
            returnOrder =
                    returnOrderService
                            .findByCondition(
                                    ReturnQueryRequest.builder()
                                            .businessTailId(payTradeRecord.getBusinessId())
                                            .build())
                            .get(0);
        } else {
            returnOrder = returnOrderService.findById(payTradeRecord.getBusinessId());
        }

        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(out_refund_no);

        if (out_refund_no.startsWith(Constants.STR_RT)) {
            refundOrder.setRefundChannel(RefundChannel.TAIL);
        }
        Operator operator =
                Operator.builder()
                        .ip(HttpUtil.getIpAddr())
                        .adminId("-1")
                        .name("LAKALACASHER")
                        .platform(Platform.PLATFORM)
                        .build();
        // 微信支付异步回调添加交易数据

        // 异步回调添加交易数据
        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
        // 微信支付订单号--及流水号
        payTradeRecordRequest.setTradeNo(lakalaIdRefundResponse.getAccTradeNo());
        // 商户订单号--业务id(商品退单号)
        payTradeRecordRequest.setBusinessId(refundOrder.getReturnOrderCode());
        payTradeRecordRequest.setResult_code(WXPayConstants.SUCCESS);
        payTradeRecordRequest.setApplyPrice(
                new BigDecimal(lakalaIdRefundResponse.getRefundAmount())
                        .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
        payTradeRecordRequest.setPracticalPrice(
                new BigDecimal(lakalaIdRefundResponse.getTotalAmount())
                        .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
        payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
        returnOrderService.refundOnline(returnOrder, refundOrder, operator);
        refundCallBackResultService.updateStatus(out_refund_no, PayCallBackResultStatus.SUCCESS);
        return BaseResponse.SUCCESSFUL();
    }

    @Transactional
    @GlobalTransactional
    public void payCallBackEvent(
            LakalaCasherNotifyRequest lakalaNotifyRequest, boolean isMergePay, PayTimeSeries payTimeSeries) {
        if (Constants.STR_2.equals(lakalaNotifyRequest.getOrderStatus())) {
            List<Trade> trades = new ArrayList<>();
            // 查询交易记录
            PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(payTimeSeries.getBusinessId());
            List<PayCallBackResult> payCallBackResultList =
                    payCallBackResultService.list(
                            PayCallBackResultQueryRequest.builder().businessId(payTimeSeries.getBusinessId()).build());
            PayCallBackResult payCallBackResult = new PayCallBackResult();
            if (payCallBackResultList.size() > 0) {
                payCallBackResult = payCallBackResultList.get(0);
            }
            if (isMergePay) {
                /*
                 * 合并支付
                 * 查询订单是否已支付或过期作废
                 */
                trades = tradeService.detailsByParentId(payTimeSeries.getBusinessId());
                // 订单合并支付场景状态采样
                boolean paid =
                        trades.stream().anyMatch(i -> i.getTradeState().getPayState() == PayState.PAID);
                boolean cancel =
                        trades.stream()
                                .anyMatch(i -> i.getTradeState().getFlowState() == FlowState.VOID);
                if (cancel
                        || (paid
                        && !payTradeRecord
                        .getTradeNo()
                        .equals(lakalaNotifyRequest.getOrderTradeInfo().getTradeNo()))) {
                    log.error(
                            "订单重复支付或过期作废,不直接退款lakalaCasherNotifyRequest:{}.businessId:{}",
                            lakalaNotifyRequest,
                            payTimeSeries.getBusinessId());
                    this.modifyPayTimeSeries(lakalaNotifyRequest, payTimeSeries);
//                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                } else if (payCallBackResultList.size() == 0
                        || payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                    lakalaPayCallbackHanlakdle(lakalaNotifyRequest, trades, true, payTimeSeries);
                }
            } else {
                // 单笔支付
                Trade trade;
                if (payCallBackUtil.isTailPayOrder(payTimeSeries.getBusinessId())) {
                    trade =
                            tradeService
                                    .queryAll(
                                            TradeQueryRequest.builder().tailOrderNo(payTimeSeries.getBusinessId()).build())
                                    .get(0);
                } else {
                    trade = tradeService.detail(payTimeSeries.getBusinessId());
                }
                if (trade.getTradeState().getFlowState() == FlowState.VOID
                        || (trade.getTradeState().getPayState() == PayState.PAID
                        && !payTradeRecord
                        .getTradeNo()
                        .equals(lakalaNotifyRequest.getOrderTradeInfo().getTradeNo()))) {
                    // 同一批订单重复支付或过期作废，直接退款
                    log.error(
                            "订单重复支付或过期作废,不直接退款lakalaCasherNotifyRequest:{}.businessId:{}",
                            lakalaNotifyRequest,
                            payTimeSeries.getBusinessId());
                    this.modifyPayTimeSeries(lakalaNotifyRequest, payTimeSeries);
//                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                } else if (payCallBackResultList.size() == 0
                        || payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                    trades.add(trade);
                    lakalaPayCallbackHanlakdle(lakalaNotifyRequest, trades, false, payTimeSeries);
                }
            }
        } else {
            log.error("拉卡拉收银台交易失败，回调内容lakalaNotifyRequest：{}", JSON.toJSONString(lakalaNotifyRequest));
        }
        // 支付回调处理成功
        payCallBackResultService.updateStatus(payTimeSeries.getBusinessId(), PayCallBackResultStatus.SUCCESS);
    }

    private void lakalaPayCallbackHanlakdle(
            LakalaCasherNotifyRequest request,
            List<Trade> trades,
            boolean isMergePay,
            PayTimeSeries payTimeSeries) {

        if (isMergePay) {
            PayOrderDetailRequest payOrderDetailRequest = PayOrderDetailRequest.builder()
                    .payType(PayType.LAKALA_CASHER_PAY)
                    .orderCode(payTimeSeries.getBusinessId())
                    .businessId(payTimeSeries.getPayNo())
                    .build();
            BaseResponse queryBaseResponse = payProvider.getPayOrderDetail(payOrderDetailRequest);
            if (Objects.nonNull(queryBaseResponse)) {
                // 异步回调添加交易数据
                PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                // 支付订单号--及流水号
                payTradeRecordRequest.setTradeNo(request.getOrderTradeInfo().getTradeNo());
                // 商户订单号或父单号
                payTradeRecordRequest.setBusinessId(payTimeSeries.getBusinessId());
                payTradeRecordRequest.setResult_code(request.getOrderStatus());
                payTradeRecordRequest.setPracticalPrice(
                        new BigDecimal(request.getTotalAmount())
                                .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
                // 更新支付项
                payTradeRecordRequest.setChannelItemId(Constants.NUM_39L);
                PayTradeRecord payTradeRecord =
                        payTradeRecordService.queryByBusinessId(payTradeRecordRequest.getBusinessId());
                // 支付异步回调添加交易数据
                if (payTradeRecord == null) {
                    payTradeRecord = new PayTradeRecord();
                    payTradeRecord.setId(GeneratorUtils.generatePT());
                }
                if (payTradeRecordRequest.getChannelItemId() != null) {
                    // 更新支付记录支付项字段
                    payTradeRecord.setChannelItemId(payTradeRecordRequest.getChannelItemId());
                }
                // 更新父订单交易记录相关信息
                payTradeRecordService.addLklCasherPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);

                // 拉卡拉分账需要拆单后的每笔订单的交易流水号。特在此记录
                LakalaCasherTradeQueryResponse lakalaTradeQueryResponse =
                        JSON.parseObject(JSON.toJSONString(queryBaseResponse.getContext()),
                                LakalaCasherTradeQueryResponse.class);
                List<LakalaCasherTradeQueryResponse.SplitInfoBean> splitInfos = lakalaTradeQueryResponse.getSplitInfo();
                trades.forEach(trade -> {
                    Optional<LakalaCasherTradeQueryResponse.SplitInfoBean> splitInfoOperator =
                            splitInfos.stream().filter(g -> g.getOutSubOrderNo().startsWith(trade.getId())).findFirst();
                    splitInfoOperator.ifPresent(splitInfo -> {
                        // 异步回调添加交易数据
                        PayTradeRecordRequest lakalaPayTradeRecordRequest = new PayTradeRecordRequest();
                        // 支付订单号--及流水号
                        lakalaPayTradeRecordRequest.setTradeNo(splitInfo.getSubTradeNo());
                        // 商户订单号
                        lakalaPayTradeRecordRequest.setBusinessId(trade.getId());
                        lakalaPayTradeRecordRequest.setResult_code(request.getOrderStatus());
                        lakalaPayTradeRecordRequest.setPracticalPrice(
                                new BigDecimal(splitInfo.getAmount())
                                        .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
                        lakalaPayTradeRecordRequest.setApplyPrice(new BigDecimal(splitInfo.getAmount())
                                .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
                        // 更新支付项
                        lakalaPayTradeRecordRequest.setChannelItemId(Constants.NUM_39L);
                        // 支付异步回调添加交易数据
                        PayTradeRecord lakalaPayTradeRecord = new PayTradeRecord();
                        lakalaPayTradeRecord.setId(GeneratorUtils.generatePT());
                        if (lakalaPayTradeRecordRequest.getChannelItemId() != null) {
                            // 更新支付记录支付项字段
                            lakalaPayTradeRecord.setChannelItemId(lakalaPayTradeRecordRequest.getChannelItemId());
                        }
                        lakalaPayTradeRecord.setApplyPrice(lakalaPayTradeRecordRequest.getApplyPrice());
                        lakalaPayTradeRecord.setCreateTime(LocalDateTime.now());
                        payTradeRecordService.addLklCasherPayRecordForCallBack(lakalaPayTradeRecordRequest, lakalaPayTradeRecord);
                        payOrderService.updatePayOrderNo(trade.getId(), splitInfo.getSubLogNo());
                    });
                });
            }
        } else {
            // 异步回调添加交易数据
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            // 支付订单号--及流水号
            payTradeRecordRequest.setTradeNo(request.getOrderTradeInfo().getTradeNo());
            // 商户订单号或父单号
            payTradeRecordRequest.setBusinessId(payTimeSeries.getBusinessId());
            payTradeRecordRequest.setResult_code(request.getOrderStatus());
            payTradeRecordRequest.setPracticalPrice(
                    new BigDecimal(request.getTotalAmount())
                            .divide(new BigDecimal(100), 2, RoundingMode.DOWN));
            // 更新支付项
            payTradeRecordRequest.setChannelItemId(Constants.NUM_39L);
            PayTradeRecord payTradeRecord =
                    payTradeRecordService.queryByBusinessId(payTradeRecordRequest.getBusinessId());
            // 支付异步回调添加交易数据
            if (payTradeRecord == null) {
                payTradeRecord = new PayTradeRecord();
                payTradeRecord.setId(GeneratorUtils.generatePT());
            }
            if (payTradeRecordRequest.getChannelItemId() != null) {
                // 更新支付记录支付项字段
                payTradeRecord.setChannelItemId(payTradeRecordRequest.getChannelItemId());
            }
            payTradeRecordService.addLklCasherPayRecordForCallBack(payTradeRecordRequest, payTradeRecord);
            payOrderService.updatePayOrderNo(payTimeSeries.getBusinessId(), request.getOrderTradeInfo().getLogNo());
        }
        LakalaCasherNotifyRequest.OrderTradeInfoBean orderTradeInfoBean = request.getOrderTradeInfo();
        if (trades.size() > 1) {
            Map<String, LakalaCasherNotifyRequest.SplitInfoBean> splitInfoBeanMap = request.getSplitInfo().stream()
                    .collect(Collectors.toMap(LakalaCasherNotifyRequest.SplitInfoBean::getOutSubOrderNo, Function.identity(), (v1, v2) -> v2));
            trades.forEach(t -> splitInfoBeanMap.forEach((k, v) -> {
                if (k.contains(t.getId())) {
                    t.setLklOrderTradeInfo(LklOrderTradeInfoVO.builder().termNo(v.getTermNo())
                            .logNo(v.getSubLogNo()).tradeNo(v.getSubTradeNo()).merchantNo(v.getMerchantNo())
                                    .accType(orderTradeInfoBean.getAccType())
                                    .busiType(orderTradeInfoBean.getBusiType())
                                    .payMode(orderTradeInfoBean.getPayMode())
                                    .payerAccountNo(orderTradeInfoBean.getPayerAccountNo())
                                    .tradeTime(orderTradeInfoBean.getTradeTime())
                            .build());
                }
            }));
        } else {
            Trade trade = trades.get(0);
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && BookingType.EARNEST_MONEY.equals(trade.getBookingType())
                && PayState.PAID_EARNEST.equals(trade.getTradeState().getPayState())) {
                trades.forEach(t -> {
                    if (Objects.isNull(t.getLklOrderTradeInfo())) {
                        t.setLklOrderTradeInfo(LklOrderTradeInfoVO.builder()
                                .termNo(request.getTermNo())
                                .tailLogNo(orderTradeInfoBean.getLogNo())
                                .tailTradeNo(orderTradeInfoBean.getTradeNo())
                                .merchantNo(request.getMerchantNo())
                                .accType(orderTradeInfoBean.getAccType())
                                .busiType(orderTradeInfoBean.getBusiType())
                                .payMode(orderTradeInfoBean.getPayMode())
                                .payerAccountNo(orderTradeInfoBean.getPayerAccountNo())
                                .tradeTime(orderTradeInfoBean.getTradeTime())
                                .build());
                    } else {
                        LklOrderTradeInfoVO lklOrderTradeInfo = t.getLklOrderTradeInfo();
                        lklOrderTradeInfo.setTailLogNo(request.getOrderTradeInfo().getLogNo());
                        lklOrderTradeInfo.setTailTradeNo(request.getOrderTradeInfo().getTradeNo());
                        lklOrderTradeInfo.setAccTradeNo(orderTradeInfoBean.getAccTradeNo());
                        lklOrderTradeInfo.setAccType(orderTradeInfoBean.getAccType());
                        lklOrderTradeInfo.setBusiType(orderTradeInfoBean.getBusiType());
                        lklOrderTradeInfo.setPayMode(orderTradeInfoBean.getPayMode());
                        lklOrderTradeInfo.setPayerAccountNo(orderTradeInfoBean.getPayerAccountNo());
                        lklOrderTradeInfo.setTradeTime(orderTradeInfoBean.getTradeTime());
                        t.setLklOrderTradeInfo(lklOrderTradeInfo);
                    }
                });
            } else {
                trades.forEach(t -> t.setLklOrderTradeInfo(LklOrderTradeInfoVO.builder().termNo(request.getTermNo())
                        .logNo(request.getOrderTradeInfo().getLogNo()).tradeNo(request.getOrderTradeInfo().getTradeNo()).merchantNo(request.getMerchantNo())
                        .accType(orderTradeInfoBean.getAccType())
                        .busiType(orderTradeInfoBean.getBusiType())
                        .payMode(orderTradeInfoBean.getPayMode())
                        .payerAccountNo(orderTradeInfoBean.getPayerAccountNo())
                        .tradeTime(orderTradeInfoBean.getTradeTime())
                        .build()));
            }
        }
        // 订单 支付单 操作信息
        Operator operator =
                Operator.builder()
                        .ip(HttpUtil.getIpAddr())
                        .adminId("-1")
                        .name(PayGatewayEnum.LAKALACASHIER.name())
                        .account(PayGatewayEnum.LAKALACASHIER.name())
                        .platform(Platform.THIRD)
                        .build();
        payCallBackUtil.updateTradeSellInfo(trades);
        payCallBackUtil.payCallbackOnline(trades, operator, isMergePay);
    }

    private void modifyPayTimeSeries(LakalaCasherNotifyRequest wxPayResultResponse, PayTimeSeries payTimeSeries){
        payTimeSeries = this.buildPayTimeSeries(wxPayResultResponse,payTimeSeries);
        payTimeSeriesService.modify(payTimeSeries);
    }

    private PayTimeSeries buildPayTimeSeries(LakalaCasherNotifyRequest lakalaNotifyRequest, PayTimeSeries payTimeSeries){
        payTimeSeries.setStatus(Constants.STR_2.equals(lakalaNotifyRequest.getOrderStatus()) ? TradeStatus.SUCCEED : TradeStatus.FAILURE);
        payTimeSeries.setTradeNo(lakalaNotifyRequest.getPayOrderNo());
        payTimeSeries.setCallbackTime(LocalDateTime.now());
        payTimeSeries.setPracticalPrice(new BigDecimal(lakalaNotifyRequest.getTotalAmount())
                .divide(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_DOWN));
        return payTimeSeries;
    }
}
