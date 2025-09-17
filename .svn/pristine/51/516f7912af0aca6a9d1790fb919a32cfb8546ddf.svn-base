package com.wanmi.sbc.order.paycallback.union;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CustomerCreditRepayProvider;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayByRepayCodeRequest;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayAndOrdersByRepayCodeResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisPayUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.unionb2b.UnionB2BProvider;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionCloudPayRefundRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.request.trade.CreditCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.RefundChannel;
import com.wanmi.sbc.order.paycallback.PayAndRefundCallBackBaseService;
import com.wanmi.sbc.order.paycallback.PayCallBackUtil;
import com.wanmi.sbc.order.paycallbackresult.model.root.PayCallBackResult;
import com.wanmi.sbc.order.paycallbackresult.service.PayCallBackResultService;
import com.wanmi.sbc.order.payingmemberpayrecord.model.root.PayingMemberPayRecord;
import com.wanmi.sbc.order.payingmemberpayrecord.service.PayingMemberPayRecordService;
import com.wanmi.sbc.order.payingmemberrecordtemp.model.root.PayingMemberRecordTemp;
import com.wanmi.sbc.order.payingmemberrecordtemp.service.PayingMemberRecordTempService;
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

import io.seata.spring.annotation.GlobalTransactional;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UnionCloudPayAndRefundCallBackService implements PayAndRefundCallBackBaseService {

    private static final String UNION_PAY_GET_WAY_INFO_KEY = "UNION_PAY_GETWAY_INFO_KEY:";

    @Autowired
    private PayCallBackUtil payCallBackUtil;

    @Autowired
    private PayProvider payProvider;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PayCallBackResultService payCallBackResultService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private RefundCallBackResultService refundCallBackResultService;

    @Autowired
    private UnionB2BProvider unionB2BProvider;

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    @Autowired
    private CustomerCreditRepayProvider customerCreditRepayProvider;

    @Autowired
    private CreditRepayQueryProvider customerCreditRepayQueryProvider;

    @Autowired
    private PayingMemberPayRecordService payingMemberPayRecordService;

    @Autowired
    private PayingMemberRecordTempService payingMemberRecordTempService;

    @Autowired
    private PayTimeSeriesService payTimeSeriesService;

    /**
     * @return void
     * @Author zhangyong
     * @Description 银联支付回调处理
     * @Date 14:56 2021/3/17
     * @Param [tradePayOnlineCallBackRequest]
     **/
    @Transactional
    @GlobalTransactional
    @Override
    public BaseResponse payCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        String businessId = "";
        String outTradeNo = "";
        try {
            //获取银联支付网关信息
            PayGatewayConfigResponse payGatewayConfig = payCallBackUtil.getGatewayConfigByGateway(UNION_PAY_GET_WAY_INFO_KEY,
                    PayGatewayEnum.UNIONPAY);

            String apiKey = payGatewayConfig.getApiKey();
            //获取数据库 或者 回调值
            String unionPayCallBackResultStr = tradePayOnlineCallBackRequest.getUnionPayCallBackResultStr();
            //字符串转成map
            Map<String, String> respParam =
                    JSONObject.parseObject(unionPayCallBackResultStr, Map.class);
            log.info("-------------银联支付回调,unionPayCallBackResultStr：{}------------", unionPayCallBackResultStr);
            outTradeNo =  respParam.get("orderId");

            // 如果是付费会员 回调
            if (outTradeNo.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
                RLock rLock = redissonClient.getFairLock(outTradeNo);
                rLock.lock();
                try {

                    String respCode = respParam.get("respCode");
                    log.info("-------------银联支付回调,respParam：{}------------", JSONObject.toJSONString(respParam));
                    if (Constants.STR_00.equals(respCode)) {
                        PayingMemberPayRecord payingMemberPayRecord = payingMemberPayRecordService.findByBusinessId(businessId);
                        PayCallBackResult payCallBackResult =
                                payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(outTradeNo).build()).get(0);
                        PayingMemberRecordTemp payingMemberRecordTemp = payingMemberRecordTempService.getOne(outTradeNo);
                        Integer payState = payingMemberRecordTemp.getPayState();
                        if (NumberUtils.INTEGER_ONE.equals(payState) && payingMemberPayRecord.getChannelItemId() != 18 && payingMemberPayRecord.getChannelItemId()
                                != 24 && payingMemberPayRecord.getChannelItemId() != 25) {
                            log.error("订单重复支付或过期作废,不直接退款unionPayResultResponse:{}.businessId:{},storeId:{}", payCallBackResult,
                                    outTradeNo);
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        } else {
                            //添加交易数据
                            unionB2BProvider.unionCallBack(respParam);
                        }
                    }
                    payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.SUCCESS);
                    payingMemberRecordTempService.addForCallBack(outTradeNo);
                }  catch (Exception e) {
                    log.error("银联支付回调：", e);
                    payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.FAILED);
                } finally {
                    rLock.unlock();
                }
                return BaseResponse.SUCCESSFUL();
            }
            PayTimeSeries payTimeSeries = payTimeSeriesService.getOne(outTradeNo);
            if(payTimeSeries ==null){
                log.error("查询不到支付流水记录，流水号为：{}",outTradeNo);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            //判断当前回调是否是合并支付
            businessId = payTimeSeries.getBusinessId();
            boolean isMergePay = payCallBackUtil.isMergePayOrder(businessId);
            // 是否是授信还款
            boolean isCreditRepay = payCallBackUtil.isCreditRepayFlag(businessId);
            log.info("-------------银联支付回调,单号：{}，流水：{}，交易状态：{}，金额：{}，是否合并支付：{}------------", businessId, respParam.get(
                    "merId"), respParam.get("respCode"), isMergePay, isMergePay);
            String lockName;
            //非组合支付，则查出该单笔订单。
            if (isMergePay) {
                lockName = businessId;
            } else if (isCreditRepay) {
                lockName = RedisPayUtil.getCallbackLockName(businessId);
            } else {
                Trade trade = new Trade();
                if (payCallBackUtil.isTailPayOrder(businessId)) {
                    trade = tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                } else {
                    trade = tradeService.detail(businessId);
                }
                // 锁资源：无论是否组合支付，都锁父单号，确保串行回调
                lockName = trade.getParentId();
            }
            //日志记录
            Operator operator =
                    Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.UNIONPAY.name())
                            .account(PayGatewayEnum.UNIONPAY.name()).platform(Platform.THIRD).build();
            //redis锁，防止同一订单重复回调
            RLock rLock = redissonClient.getFairLock(lockName);
            rLock.lock();
            //执行回调
            try {
                List<Trade> trades = new ArrayList<>();
                String respCode = respParam.get("respCode");
                log.info("-------------银联支付回调,respParam：{}------------", JSONObject.toJSONString(respParam));
                if (Constants.STR_00.equals(respCode)) {
//                        //1/判断respCode=00 后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
//                        UnionPayRequest unionPayRequest = new UnionPayRequest();
//                        unionPayRequest.setApiKey(respParam.get("merId"));
//                        unionPayRequest.setBusinessId(respParam.get("orderId"));
//                        unionPayRequest.setTxnTime(respParam.get("txnTime"));
//                        Map<String, String> resultMap =
//                                unionCloudPayProvider.getUnionCloudPayResult(unionPayRequest).getContext();
//                        if (resultMap != null && "00".equals(resultMap.get("respCode"))) {
                    //查询支付记录
                    PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);
                    //查询支付回调记录
                    PayCallBackResult payCallBackResult =
                            payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(outTradeNo).build()).get(0);
                    if (isMergePay) {
                        /*
                         * 合并支付
                         * 查询订单是否已支付或过期作废
                         */
                        trades = tradeService.detailsByParentId(businessId);
                        //订单合并支付场景状态采样
                        boolean paid =
                                trades.stream().anyMatch(i -> i.getTradeState().getPayState() == PayState.PAID);
                        boolean cancel =
                                trades.stream().anyMatch(i -> i.getTradeState().getFlowState() == FlowState.VOID);
                        //或者已支付 不是银联通道24 25 渠道
                        if (cancel || (paid && (payTradeRecord.getStatus()!=null)&&payTradeRecord.getStatus().equals(TradeStatus.SUCCEED))) {
                            //同一批订单重复支付或过期作废，直接退款
//                            unionPayRefundHandle(businessId);
                            log.error("订单重复支付或过期作废,不直接退款wxPayResultResponse:{}.businessId:{},storeId:{}",payCallBackResult,
                                    businessId);
                            this.modifyPayTimeSeries(respParam,payTimeSeries);
                          //  throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            unionPayCallbackHandle(operator, respParam, trades,
                                    true, false , null,payTimeSeries);
                        }
                    } else if (isCreditRepay) {
                        // 授信还款回调
                        creditUnionPayCallbackHandle(operator, respParam,payTimeSeries);
                    } else {
                        //单笔支付
                        Trade trade = new Trade();
                        if (payCallBackUtil.isTailPayOrder(businessId)) {
                            trade =
                                    tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                        } else {
                            trade = tradeService.detail(businessId);
                        }
                        if (trade.getTradeState().getFlowState() == FlowState.VOID || (trade.getTradeState()
                                .getPayState() == PayState.PAID)) {
                            //同一批订单重复支付或过期作废，直接退款
//                            unionPayRefundHandle(businessId);
                            log.error("订单重复支付或过期作废,不直接退款unionPayResultResponse:{}.businessId:{},storeId:{}",payCallBackResult,
                                    businessId);
                            this.modifyPayTimeSeries(respParam,payTimeSeries);
//                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            trades.add(trade);
                            unionPayCallbackHandle(operator, respParam, trades,
                                    false, false , null,payTimeSeries);
                        }
                    }
                }
//                    }
                //回调表更新成功 失败 会触发定时任务扫描
                payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.SUCCESS);
                log.info("银联支付异步通知回调end---------");
            } catch (Exception e) {
                log.error(e.getMessage());
                //支付处理结果回写回执支付结果表
                payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.FAILED);
            } finally {
                //解锁
                rLock.unlock();
            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(outTradeNo)) {
                payCallBackResultService.updateStatus(outTradeNo, PayCallBackResultStatus.FAILED);
            }
            log.error(ex.getMessage());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse refundCallBack(TradeRefundOnlineCallBackRequest request) {
        String out_refund_no = request.getOut_refund_no();
        //获取数据库 或者 回调值
        String unionPayCallBackResultStr = request.getUnionRefundCallBackResultStr();
        //字符串转成map
        Map<String, String> reqParam =
                JSONObject.parseObject(unionPayCallBackResultStr, Map.class);
        try {
            log.info("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
            String respCode = reqParam.get("respCode");
            if (Constants.STR_00.equals(respCode)) {
                //  退款状态   SUCCESS SUCCESS-退款成功、CHANGE-退款异常、REFUNDCLOSE—退款关闭
                PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(out_refund_no);
                ReturnOrder returnOrder;
                if (payTradeRecord.getBusinessId().startsWith(Constants.STR_RT)) {
                    returnOrder = returnOrderService.findByCondition(ReturnQueryRequest.builder()
                            .businessTailId(out_refund_no).build()).get(0);
                } else {
                    returnOrder = returnOrderService.findById(out_refund_no);
                }
                RefundOrder refundOrder =
                        refundOrderService.findRefundOrderByReturnOrderNo(out_refund_no);
                if (payTradeRecord.getBusinessId().startsWith(Constants.STR_RT)) {
                    refundOrder.setRefundChannel(RefundChannel.TAIL);
                }
                Operator operator =
                        Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.UNIONPAY.name())
                                .account(PayGatewayEnum.UNIONPAY.name()).platform(Platform.THIRD).build();
                unionB2BProvider.unionCallBack(reqParam);
                returnOrderService.refundOnline(returnOrder, refundOrder, operator);
                refundCallBackResultService.updateStatus(out_refund_no,
                        PayCallBackResultStatus.SUCCESS);

            }
        } catch (Exception e) {
            log.error("refund:银行退款回调发布异常：", e);
            if (StringUtils.isNotBlank(out_refund_no)) {
                refundCallBackResultService.updateStatus(out_refund_no, PayCallBackResultStatus.FAILED);
            }
        }
        return null;
    }


    private void unionPayRefundHandle(String orderId) {
        UnionCloudPayRefundRequest unionPayRequest = new UnionCloudPayRefundRequest();
        unionPayRequest.setBusinessId(orderId);
        unionPayRequest.setNeedCallback(false);
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setUnionCloudPayRefundRequest(unionPayRequest);
        payRefundBaseRequest.setPayType(PayType.UNIONCLONDPAY);
        payProvider.payRefund(payRefundBaseRequest);
        log.info("银联重复支付、超时订单退款,单号：{}", orderId);
    }

    /***
     * 银联支付回调操作逻辑
     * @date 13:48 2021/3/18
     * @author zhangyong
     * @param operator
     * @param resultMap
     * @param trades
     * @param isMergePay
     * @return
     */
    private void unionPayCallbackHandle(Operator operator, Map<String, String> resultMap, List<Trade> trades,
                                        boolean isMergePay, boolean isCreditRepay, CustomerCreditRepayVO customerCreditRepay,PayTimeSeries payTimeSeries) {
        //添加交易数据
//        unionB2BProvider.unionCallBack(resultMap);
        payTimeSeries = this.buildPayTimeSeries(resultMap,payTimeSeries);
        payTradeRecordService.addPayRecordForCallBackByTimeSeries( payTimeSeries);
        if (isCreditRepay) {
//            String businessId = resultMap.get("orderId");
            String businessId = payTimeSeries.getBusinessId();
            List<String> ids = trades.stream().map(Trade::getId).collect(Collectors.toList());
            payCallBackUtil.creditRepayCallbackOnline(
                    CreditCallBackRequest.builder()
                            .repayOrderCode(businessId)
                            .ids(ids)
                            .userId(operator.getUserId())
                            .repayType(CreditRepayTypeEnum.UNIONPAY).build()
            );
        } else {
            payCallBackUtil.updateTradeSellInfo(trades);
            payCallBackUtil.payCallbackOnline(trades, operator, isMergePay);
        }
    }

    /**
     * 授信还款-处理银联云闪付回调
     * @param operator
     * @param resultMap
     */
    private void creditUnionPayCallbackHandle(Operator operator, Map<String, String> resultMap,PayTimeSeries payTimeSeries) {
        String businessId = payTimeSeries.getBusinessId();
        CustomerCreditRepayAndOrdersByRepayCodeResponse creditRepayAndOrders =
                customerCreditRepayQueryProvider.getCreditRepayAndOrdersByRepayCode(CustomerCreditRepayByRepayCodeRequest.builder()
                        .repayCode(businessId)
                        .build()).getContext();
        List<Trade> tradeVOList = tradeService.queryAll(TradeQueryRequest.builder()
                .ids(creditRepayAndOrders.getCreditOrderVOList().stream()
                        .map(CustomerCreditOrderVO::getOrderId)
                        .distinct()
                        .toArray(String[]::new))
                .build());

        tradeVOList.forEach((trade -> payCallBackUtil.fillTradeBookingTimeOut(trade)));
        payCallBackUtil.wrapperCreditTrade(tradeVOList);
        //订单合并支付场景状态采样
        boolean paid =
                tradeVOList.stream().anyMatch(i -> i.getCreditPayInfo().getHasRepaid());
        boolean cancel =
                CreditRepayStatus.VOID.equals(creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        //添加已经支付判断
        boolean finish = CreditRepayStatus.FINISH.equals(
                creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        boolean returning = tradeVOList.stream().anyMatch(Trade::getReturningFlag);
        boolean correctAmount =
                tradeVOList.stream().map(Trade::getCanRepayPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(BigDecimal.valueOf(Double.valueOf(resultMap.get("txnAmt"))).
                        divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN)) == 0;
        if (cancel || paid || !correctAmount || returning || finish) {
            //直接退款
            unionPayRefundHandle(payTimeSeries.getPayNo());
        } else {
            creditRepayAndOrders.getCustomerCreditRepayVO().setRepayType(CreditRepayTypeEnum.UNIONPAY);
            unionPayCallbackHandle(operator, resultMap, tradeVOList, false, true, creditRepayAndOrders.getCustomerCreditRepayVO(),payTimeSeries);
        }
    }

    private PayTimeSeries buildPayTimeSeries(Map<String, String> respParam , PayTimeSeries payTimeSeries){
        payTimeSeries.setStatus(Constants.STR_00.equals(respParam.get("respCode"))?TradeStatus.SUCCEED:TradeStatus.FAILURE);
        payTimeSeries.setTradeNo(respParam.get("queryId"));
        payTimeSeries.setCallbackTime(LocalDateTime.now());
        payTimeSeries.setPracticalPrice(BigDecimal.valueOf(Double.valueOf(respParam.get("txnAmt"))).
                divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
        return payTimeSeries;
    }

    private void modifyPayTimeSeries(Map<String, String> respParam,PayTimeSeries payTimeSeries){
        payTimeSeries = this.buildPayTimeSeries(respParam,payTimeSeries);
        payTimeSeriesService.modify(payTimeSeries);
    }
}
