package com.wanmi.sbc.order.paycallback.ali;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
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
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingProvider;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.AliPayRefundRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.trade.CreditCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradeRefundOnlineCallBackRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayState;
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
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AliPayAndRefundCallBackService  implements PayAndRefundCallBackBaseService {

    private static final String ALIPAY_GET_WAY_INFO_KEY = "ALI_PAY_GETWAY_INFO_KEY:";

    @Autowired
    private PayCallBackUtil payCallBackUtil;

    @Autowired
    private PaySettingProvider paySettingProvider;

    @Autowired
    private PayProvider payProvider;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PayCallBackResultService payCallBackResultService;

    @Autowired
    private CreditRepayQueryProvider customerCreditRepayQueryProvider;

    @Autowired
    private PayTradeRecordService payTradeRecordService;


    @Autowired
    private PayingMemberPayRecordService payingMemberPayRecordService;

    @Autowired
    private PayingMemberRecordTempService payingMemberRecordTempService;

    @Autowired
    private PayTimeSeriesService payTimeSeriesService;

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 支付回调处理，将原有逻辑迁移到order处理
     * @Date 14:56 2020/7/2
     * @Param [tradePayOnlineCallBackRequest]
     **/
    @Transactional
    @GlobalTransactional
    @Override
    public BaseResponse payCallBack(TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        log.info("===============支付宝回调开始==============");

        PayGatewayConfigResponse payGatewayConfigResponse = payCallBackUtil.getGatewayConfigByGateway(ALIPAY_GET_WAY_INFO_KEY, PayGatewayEnum.ALIPAY);

        //支付宝公钥
        String aliPayPublicKey = payGatewayConfigResponse.getPublicKey();
        boolean signVerified = false;
        Map<String, String> params =
                JSONObject.parseObject(tradePayOnlineCallBackRequest.getAliPayCallBackResultStr(), Map.class);
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "UTF-8", "RSA2"); //调用SDK验证签名
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名校验异常：", e);
        }
        //商户订单号
        String out_trade_no = params.get("out_trade_no");
        //支付宝交易号
        String trade_no = params.get("trade_no");
        //交易状态
        String trade_status = params.get("trade_status");
        //订单金额
        String total_amount = params.get("total_amount");
        //支付终端类型
        String type = params.get("passback_params");
        if(!signVerified){
            payCallBackResultService.updateStatus(out_trade_no, PayCallBackResultStatus.FAILED);
            log.info("支付宝回调验签不正确：");
        }

        // 如果是付费会员 回调
        if (signVerified && out_trade_no.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            log.info("-------------支付回调,单号：{}，流水：{}，交易状态：{}，金额：{}------------",
                    out_trade_no, trade_no, trade_status, total_amount);
            RLock rLock = redissonClient.getFairLock(out_trade_no);

            rLock.lock();
            try {
                PayingMemberPayRecord payingMemberPayRecord = payingMemberPayRecordService.findByBusinessId(out_trade_no);
                PayCallBackResult payCallBackResult =
                        payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(out_trade_no).build()).get(0);
                PayingMemberRecordTemp payingMemberRecordTemp = payingMemberRecordTempService.getOne(out_trade_no);
                Integer payState = payingMemberRecordTemp.getPayState();
                //订单的支付渠道。17、18、19是我们自己对接的支付宝渠道
                if (NumberUtils.INTEGER_ONE.equals(payState) && payingMemberPayRecord.getChannelItemId() != 17 && payingMemberPayRecord.getChannelItemId()
                        != 18 && payingMemberPayRecord.getChannelItemId() != 19) {
                    log.error("订单重复支付或过期作废,不直接退款wxPayResultResponse:{}.businessId:{}", payCallBackResult,
                            out_trade_no);
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                } else {
                    boolean correctAmount =  payingMemberPayRecord.getApplyPrice().compareTo(new BigDecimal(total_amount)) == 0;
                    if (correctAmount
                            && trade_status.equals("TRADE_SUCCESS")) {
                        //异步回调添加交易数据
                        PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                        //流水号
                        payTradeRecordRequest.setTradeNo(trade_no);
                        //商品订单号
                        payTradeRecordRequest.setBusinessId(out_trade_no);
                        payTradeRecordRequest.setResult_code("SUCCESS");
                        payTradeRecordRequest.setPracticalPrice(new BigDecimal(total_amount));
                        payTradeRecordRequest.setChannelItemId(Long.valueOf(type));
                        if (StringUtils.isNotEmpty(type)) {
                            //更新支付记录支付项字段
                            payingMemberPayRecord.setChannelItemId(Integer.valueOf(type));
                        }
                        payTradeRecordService.addPayRecordForCallBack(payTradeRecordRequest, payingMemberPayRecord);
                    }
                }
                payCallBackResultService.updateStatus(out_trade_no, PayCallBackResultStatus.SUCCESS);
                payingMemberRecordTempService.addForCallBack(out_trade_no);
            }  catch (Exception e) {
                log.error("支付宝回调异常：", e);
                payCallBackResultService.updateStatus(out_trade_no, PayCallBackResultStatus.FAILED);
            } finally {
                rLock.unlock();
            }
            return BaseResponse.SUCCESSFUL();
        }
        if (signVerified) {
            try {

                PayTimeSeries payTimeSeries = payTimeSeriesService.getOne(out_trade_no);
                if(payTimeSeries==null){
                    log.error("查询不到支付流水记录，流水号为：{}",out_trade_no);
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }

                String businessId = payTimeSeries.getBusinessId();
                // 是否是合并支付
                boolean isMergePay = payCallBackUtil.isMergePayOrder(businessId);
                // 是否是授信还款
                boolean isCreditRepay = payCallBackUtil.isCreditRepayFlag(businessId);
                log.info("-------------支付回调,支付单号：{}，订单号{}，流水：{}，交易状态：{}，金额：{}，是否合并支付：{}，是否授信还款：{}------------",
                        out_trade_no,businessId, trade_no, trade_status, total_amount, isMergePay, isCreditRepay);
                String lockName;

                //非组合支付，则查出该单笔订单。
                //非组合支付，则查出该单笔订单。
                if (isMergePay) {
                    lockName = businessId;
                } else if (isCreditRepay) {
                    lockName = RedisPayUtil.getCallbackLockName(businessId);
                } else {
                    Trade trade = new Trade();
                    if (payCallBackUtil.isTailPayOrder(businessId)) {
                        trade =
                                tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                    } else {
                        trade = tradeService.detail(businessId);
                    }
                    // 锁资源：无论是否组合支付，都锁父单号，确保串行回调
                    lockName = trade.getParentId();
                }
                Operator operator =
                        Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.ALIPAY.name())
                                .account(PayGatewayEnum.ALIPAY.name()).platform(Platform.THIRD).build();
                //redis锁，防止同一订单重复回调
                RLock rLock = redissonClient.getFairLock(lockName);
                //执行
                try {
                    rLock.lock();
                    List<Trade> trades = new ArrayList<>();
                    //查询交易记录
//                    PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);
                    PayCallBackResult payCallBackResult =
                            payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessId(out_trade_no).build()).get(0);
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
                        //订单的支付渠道。17、18、19是我们自己对接的支付宝渠道， 表：pay_channel_item
                        if (cancel || paid ) {
                            //重复支付，直接退款
//                            alipayRefundHandle(out_trade_no, total_amount);
                            log.error("订单重复支付或过期作废,不直接退款payCallBackResult:{}.businessId:{}",payCallBackResult,
                                    businessId);
                            //更新支付流水记录
                            alipayCallbackTimeSeriesHandle(businessId, trade_no, trade_status, total_amount, type,
                                    operator, trades, true, payTimeSeries, false, null);
//                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            alipayCallbackHandle(businessId, trade_no, trade_status, total_amount, type,
                                    operator, trades, true, payTimeSeries, false, null);
                        }
                    } else if (isCreditRepay) {
                        creditAlipayCallbackHandle(businessId, trade_no, trade_status, total_amount, type,
                                operator, payTimeSeries, -1L);
                    } else {
                        //单笔支付
                        //单笔支付
                        Trade trade = new Trade();
                        if (payCallBackUtil.isTailPayOrder(businessId)) {
                            trade =
                                    tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
                        } else {
                            trade = tradeService.detail(businessId);
                        }
                        if (trade.getTradeState().getFlowState() == FlowState.VOID || (trade.getTradeState()
                                .getPayState() == PayState.PAID )) {
                            //同一批订单重复支付或过期作废，直接退款
//                            alipayRefundHandle(out_trade_no, total_amount);
                            log.error("订单重复支付或过期作废,不直接退款payCallBackResult:{}.businessId:{}",payCallBackResult,
                                    businessId);
                            alipayCallbackTimeSeriesHandle(businessId, trade_no, trade_status, total_amount, type,
                                    operator, trades, false, payTimeSeries, false, null);
//                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        } else if (payCallBackResult.getResultStatus() != PayCallBackResultStatus.SUCCESS) {
                            trades.add(trade);
                            alipayCallbackHandle(businessId, trade_no, trade_status, total_amount, type,
                                    operator, trades, false, payTimeSeries, false, null);
                        }
                    }

                    payCallBackResultService.updateStatus(out_trade_no, PayCallBackResultStatus.SUCCESS);
                } finally {
                    //解锁
                    rLock.unlock();
                }
            } catch (Exception e) {
                log.error("支付宝回调异常：", e);
                payCallBackResultService.updateStatus(out_trade_no, PayCallBackResultStatus.FAILED);
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse refundCallBack(TradeRefundOnlineCallBackRequest tradeRefundOnlineCallBackRequest) {
        return null;
    }

    /**
     * 支付宝退款处理
     *
     * @param out_trade_no
     * @param total_amount
     */
    private void alipayRefundHandle(String out_trade_no, String total_amount) {
        AliPayRefundRequest aliPayRefundRequest = AliPayRefundRequest.builder().businessId(out_trade_no)
                .amount(new BigDecimal(total_amount)).description("重复支付退款").build();
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setAliPayRefundRequest(aliPayRefundRequest);
        payRefundBaseRequest.setPayType(PayType.ALIPAY);
        //调用退款接口。直接退款。不走退款流程，没有交易对账，只记了操作日志
        payProvider.payRefund(payRefundBaseRequest);
        log.info("支付宝重复支付、超时订单退款,单号：{}", out_trade_no);
    }

    private void alipayCallbackHandle(String businessId, String trade_no, String trade_status, String total_amount,
                                      String type, Operator operator, List<Trade> trades, boolean isMergePay,
                                      PayTimeSeries payTimeSeries, boolean isCreditRepay, CustomerCreditRepayVO customerCreditRepay) {

        boolean correctAmount;
        if (isCreditRepay) {
            correctAmount = customerCreditRepay.getRepayAmount().compareTo(new BigDecimal(total_amount)) == 0;
        } else {
            correctAmount = payTimeSeries.getApplyPrice().compareTo(new BigDecimal(total_amount)) == 0;
        }
        if (correctAmount
                && trade_status.equals("TRADE_SUCCESS")) {

            payTimeSeries.setStatus(TradeStatus.SUCCEED);
            payTimeSeries.setPracticalPrice(new BigDecimal(total_amount));
//            payTimeSeries.setChannelItemId(Long.valueOf(type));
            payTimeSeries.setTradeNo(trade_no);
            payTimeSeries.setCallbackTime(payTimeSeries.getCallbackTime() == null ? LocalDateTime.now() : payTimeSeries.getCallbackTime());

            payTradeRecordService.addPayRecordForCallBackByTimeSeries( payTimeSeries);
            if (isCreditRepay) {
                List<String> ids = trades.stream().map(Trade::getId).collect(Collectors.toList());
                payCallBackUtil.creditRepayCallbackOnline(
                        CreditCallBackRequest.builder()
                                .repayOrderCode(businessId)
                                .ids(ids)
                                .userId(operator.getUserId())
                                .repayType(CreditRepayTypeEnum.ALIPAY).build());
            } else {
                payCallBackUtil.updateTradeSellInfo(trades);
                payCallBackUtil.payCallbackOnline(trades, operator, isMergePay);
            }
            log.info("支付回调成功,单号：{}", businessId);
        }
    }

    private void alipayCallbackTimeSeriesHandle(
            String businessId,
            String trade_no,
            String trade_status,
            String total_amount,
            String type,
            Operator operator,
            List<Trade> trades,
            boolean isMergePay,
            PayTimeSeries payTimeSeries,
            boolean isCreditRepay,
            CustomerCreditRepayVO customerCreditRepay) {

        boolean correctAmount;
        if (isCreditRepay) {
            correctAmount =
                    customerCreditRepay.getRepayAmount().compareTo(new BigDecimal(total_amount))
                            == 0;
        } else {
            correctAmount =
                    payTimeSeries.getApplyPrice().compareTo(new BigDecimal(total_amount)) == 0;
        }
        if (correctAmount && trade_status.equals("TRADE_SUCCESS")) {

            payTimeSeries.setStatus(TradeStatus.SUCCEED);
            payTimeSeries.setPracticalPrice(new BigDecimal(total_amount));
            //            payTimeSeries.setChannelItemId(Long.valueOf(type));
            payTimeSeries.setTradeNo(trade_no);
            payTimeSeries.setCallbackTime(
                    payTimeSeries.getCallbackTime() == null
                            ? LocalDateTime.now()
                            : payTimeSeries.getCallbackTime());
            payTimeSeriesService.modify(payTimeSeries);
        }
    }


    /**
     * 授信退款-处理支付宝回调
     */
    private void creditAlipayCallbackHandle(String businessId, String trade_no, String trade_status, String total_amount,
                                            String type, Operator operator, PayTimeSeries payTimeSeries, Long storeId) {
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
                tradeVOList.stream().anyMatch(i -> i.getCreditPayInfo().getHasRepaid())
                        && payTimeSeries.getChannelItemId() != 17L
                        && payTimeSeries.getChannelItemId() != 18L
                        && payTimeSeries.getChannelItemId() != 19L;
        boolean cancel =
                CreditRepayStatus.VOID.equals(creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        //添加已经支付判断
        boolean finish = CreditRepayStatus.FINISH.equals(
                creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        boolean correctAmount =
                tradeVOList.stream().map(Trade::getCanRepayPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(new BigDecimal(total_amount)) == 0;
        boolean returning = tradeVOList.stream().anyMatch(Trade::getReturningFlag);
        if (cancel || paid || !correctAmount || returning || finish) {
            // 重复支付，直接退款
            alipayRefundHandle(payTimeSeries.getPayNo(), total_amount);
        } else {
            creditRepayAndOrders.getCustomerCreditRepayVO().setRepayType(CreditRepayTypeEnum.ALIPAY);
            alipayCallbackHandle(businessId, trade_no, trade_status, total_amount, type,
                    operator, tradeVOList, false, payTimeSeries, true, creditRepayAndOrders.getCustomerCreditRepayVO());
        }
    }
}
