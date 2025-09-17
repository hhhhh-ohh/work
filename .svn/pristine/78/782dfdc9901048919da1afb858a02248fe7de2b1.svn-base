package com.wanmi.sbc.order.paycallback;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.credit.CustomerCreditRepayProvider;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayModifyRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerAccountModifyStateRequest;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.request.trade.CreditCallBackRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.entity.CreditPayInfo;
import com.wanmi.sbc.order.trade.model.entity.PayCallBackOnlineBatch;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PayCallBackUtil {


    @Autowired
    private RedisUtil redisService;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private CustomerCreditRepayProvider customerCreditRepayProvider;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private CustomerProvider customerProvider;
    /**
     * 获取支付方式配置信息
     *
     * @param payGetWayInfoKey
     * @param payGatewayEnum
     * @return
     */
    public PayGatewayConfigResponse getGatewayConfigByGateway(String payGetWayInfoKey, PayGatewayEnum payGatewayEnum) {
        PayGatewayConfigResponse payGatewayConfig = redisService.getObj(payGetWayInfoKey, PayGatewayConfigResponse.class);
        if (Objects.isNull(payGatewayConfig)) {
            payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(new GatewayConfigByGatewayRequest(payGatewayEnum, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
            redisService.setObj(payGetWayInfoKey, payGatewayConfig, 6000);
        }
        return payGatewayConfig;
    }

    /**
     * @return boolean
     * @Author lvzhenwei
     * @Description 判断是否为主订单
     * @Date 15:36 2020/7/2
     * @Param [businessId]
     **/
    public boolean isMergePayOrder(String businessId) {
        return businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID) || businessId.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID);
    }
    /**
     * 判断是否为授信还款支付
     *
     * @param businessId
     * @return
     */
    public Boolean isCreditRepayFlag(String businessId) {
        return businessId.startsWith(GeneratorService._PREFIX_CREDIT_REPAY_ID);
    }

    /**
     * 是否是尾款订单号
     *
     * @param businessId
     * @return
     */
    public boolean isTailPayOrder(String businessId) {
        return businessId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID);
    }

    /**
     * 授信还款支付成功-更新相关数据
     *
     * @param creditCallBackRequest
     */
    public void creditRepayCallbackOnline(CreditCallBackRequest creditCallBackRequest) {
        //更新还款记录
        customerCreditRepayProvider.modifyByPaySuccess(CustomerCreditRepayModifyRequest.builder()
                .repayOrderCode(creditCallBackRequest.getRepayOrderCode())
                .repayType(creditCallBackRequest.getRepayType())
                .updatePerson(creditCallBackRequest.getUserId())
                .repayTime(LocalDateTime.now())
                .build());

        //更新订单中还款状态
        creditCallBackRequest.getIds().forEach(item -> {
            tradeService.updateCreditHasRepaid(item, Boolean.TRUE);
        });
    }

    /**
     * 线上订单支付回调
     * 订单 支付单 操作信息
     *
     * @return 操作结果
     */
    public void payCallbackOnline(List<Trade> trades, Operator operator, boolean isMergePay) {
        List<PayCallBackOnlineBatch> payCallBackOnlineBatchList = trades.stream().map(trade -> {
            //每笔订单做是否合并支付标识
            trade.getPayInfo().setMergePay(isMergePay);
            log.info("--------------------{}", JSONObject.toJSONString(trade));
            tradeService.updateTrade(trade);
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && trade.getBookingType() == BookingType.EARNEST_MONEY &&
                    StringUtils.isNotEmpty(trade.getTailOrderNo()) && StringUtils.isNotEmpty(trade.getTailPayOrderId())) {
                //支付单信息
                PayOrder payOrder = tradeService.findPayOrder(trade.getTailPayOrderId());
                PayCallBackOnlineBatch backOnlineBatch = new PayCallBackOnlineBatch();
                backOnlineBatch.setTrade(trade);
                backOnlineBatch.setPayOrderOld(payOrder);
                return backOnlineBatch;
            } else {
                //支付单信息
                PayOrder payOrder = tradeService.findPayOrder(trade.getPayOrderId());
                PayCallBackOnlineBatch backOnlineBatch = new PayCallBackOnlineBatch();
                backOnlineBatch.setTrade(trade);
                backOnlineBatch.setPayOrderOld(payOrder);
                return backOnlineBatch;
            }
        }).collect(Collectors.toList());
        tradeService.payCallBackOnlineBatch(payCallBackOnlineBatchList, operator);
        //更新新人状态
        customerProvider.modifyNewCustomerState(CustomerAccountModifyStateRequest.builder()
                .customerId(trades.get(0).getBuyer().getId())
                .isNew(Constants.ONE)
                .build());
    }

    /**
     * 未完全支付的定金预售订单状态填充为已作废状态
     * <p>
     * （主要订单真实作废比较延迟，计时过后仍然处于待支付尾款情况，前端由订单状态判断来控制支付尾款按钮的展示）
     *
     * @param detail 订单
     */
    public void fillTradeBookingTimeOut(Trade detail) {
        //未完全支付的定金预售订单
        if (Boolean.TRUE.equals(detail.getIsBookingSaleGoods())
                && BookingType.EARNEST_MONEY == detail.getBookingType()
                && Objects.nonNull(detail.getTradeState())
                && (PayState.PAID != detail.getTradeState().getPayState())) {
            //尾款时间 < 今天
            if (Objects.nonNull(detail.getTradeState().getTailEndTime())
                    && detail.getTradeState().getTailEndTime().isBefore(LocalDateTime.now())) {
                //作废
                detail.getTradeState().setFlowState(FlowState.VOID);
            }
        }
    }

    /**
     * 封装还款订单-可还款金额
     *
     * @param tradeVOS
     * @return
     */
    public void wrapperCreditTrade(List<Trade> tradeVOS) {
        if (CollectionUtils.isNotEmpty(tradeVOS)) {
            List<String> orderIds = tradeVOS.stream().map(Trade::getId).collect(Collectors.toList());
            List<ReturnOrder> returnOrderList = returnOrderService.findByCondition(ReturnQueryRequest.builder()
                    .tids(orderIds)
                    .build());
            tradeVOS.forEach(tradeVO -> {
                // 默认都不是退货退款中的订单
                tradeVO.setReturningFlag(Boolean.FALSE);
                // 授信信息
                CreditPayInfo creditPayInfoVO = tradeVO.getCreditPayInfo();
                // 判断是否为空
                if (Objects.nonNull(creditPayInfoVO.getCreditPayState())) {
                    // 可还款金额
                    switch (creditPayInfoVO.getCreditPayState()) {
                        case PAID:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTotalPrice());
                            break;
                        case DEPOSIT:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getEarnestPrice());
                            break;
                        case BALANCE:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTailPrice());
                            break;
                        case ALL:
                            tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTotalPrice());
                            break;
                        default:
                            break;
                    }
                } else {
                    tradeVO.setCanRepayPrice(tradeVO.getTradePrice().getTotalPrice());
                }
            });

            // 处理退货退款中的订单
            if (CollectionUtils.isNotEmpty(returnOrderList)) {
                Map<String, List<ReturnOrder>> map =
                        returnOrderList.stream().collect(Collectors.groupingBy(ReturnOrder::getTid));
                tradeVOS.stream().map(tradeVO -> {
                    List<ReturnOrder> returnOrderVOList = map.get(tradeVO.getId());
                    if (CollectionUtils.isNotEmpty(returnOrderVOList)) {

                        // 查询是否存在已完成的退单
                        Optional<ReturnOrder> returnComplete = returnOrderVOList.stream().filter(item ->
                                item.getReturnFlowState() == ReturnFlowState.COMPLETED
                        ).findFirst();
                        // 如果订单状态为已完成 如果存在符合条件的退单
                        if (tradeVO.getTradeState().getFlowState() == FlowState.COMPLETED || tradeVO.getTradeState().getFlowState() == FlowState.VOID) {
                            // 订单已完成 且 退单已完成
                            if (returnComplete.isPresent()) {
                                // 退单总金额
                                BigDecimal returnPrice = returnOrderVOList.stream().filter(returnOrderVO -> returnOrderVO.getReturnFlowState() == ReturnFlowState.COMPLETED)
                                        .map(returnOrder -> returnOrder.getReturnPrice().getActualReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                // 订单总金额
                                BigDecimal orderPrice = tradeVO.getTradePrice().getTotalPrice();
                                // 金额差异：订单金额-退单金额
                                BigDecimal canRepayPrice = orderPrice.subtract(returnPrice).setScale(2, RoundingMode.DOWN);
                                // 可还款金额
                                tradeVO.setCanRepayPrice(canRepayPrice);
                            }
                        }

                        // 查询是否存在正在进行中的退单(不是作废,不是拒绝退款,不是已结束)
                        Optional<ReturnOrder> returningOrder = returnOrderVOList.stream().filter(item ->
                                item.getReturnFlowState() != ReturnFlowState.VOID
                                        && item.getReturnFlowState() != ReturnFlowState.REJECT_REFUND
                                        && item.getReturnFlowState() != ReturnFlowState.COMPLETED
                                        // 不是退款失败,不是拒绝收货
                                        && item.getReturnFlowState() != ReturnFlowState.REFUND_FAILED
                                        && item.getReturnFlowState() != ReturnFlowState.REJECT_RECEIVE
                        ).findFirst();
                        if (returningOrder.isPresent()) {
                            tradeVO.setReturningFlag(Boolean.TRUE);
                        }
                    }

                    return tradeVO;
                }).collect(Collectors.toList());
            }
        }
    }

    /**
     * @description   将代销订单更新为普通订单
     * @author  wur
     * @date: 2022/4/22 9:56
     * @param tradeList
     * @return
     **/
    public List<Trade> updateTradeSellInfo(List<Trade> tradeList) {
        if( Objects.isNull(tradeList.get(0).getSellPlatformType())) {
            return tradeList;
        }
        tradeList.forEach(trade -> {
            trade.setSellPlatformType(SellPlatformType.NOT_SELL);
            trade.setSellPlatformOrderId(null);
            trade.setSceneGroup(null);
        });
        return tradeList;
    }
}
