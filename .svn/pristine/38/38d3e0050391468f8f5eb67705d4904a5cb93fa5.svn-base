package com.wanmi.sbc.pay.service;

import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByConditionRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import org.apache.commons.collections4.CollectionUtils;
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

/**
 * <p>客户端支付公共方法</p>
 * Created by of628-wenzhi on 2019-07-24-19:56.
 */
@Service
public class PayServiceHelper {

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    /**
     * 判断是否为授信还款支付
     * @param businessId
     * @return
     */
    public Boolean isCreditRepayFlag(String businessId) {
        return businessId.startsWith(GeneratorService._PREFIX_CREDIT_REPAY_ID);
    }

    /**
     * 封装还款订单-可还款金额
     * @param tradeVOS
     * @return
     */
    public void wrapperCreditTrade(List<TradeVO> tradeVOS) {
        if (CollectionUtils.isNotEmpty(tradeVOS)) {
            List<String> orderIds = tradeVOS.stream().map(TradeVO::getId).collect(Collectors.toList());
            List<ReturnOrderVO> returnOrderList = returnOrderQueryProvider.listByCondition(ReturnOrderByConditionRequest.builder()
                    .tids(orderIds)
                    .build()).getContext().getReturnOrderList();

            tradeVOS.forEach(tradeVO -> {
                // 默认都不是退货退款中的订单
                tradeVO.setReturningFlag(Boolean.FALSE);
                // 授信信息
                CreditPayInfoVO creditPayInfoVO = tradeVO.getCreditPayInfo();
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
                Map<String, List<ReturnOrderVO>> map =
                        returnOrderList.stream().collect(Collectors.groupingBy(ReturnOrderVO::getTid));
                tradeVOS.stream().map(tradeVO -> {
                    List<ReturnOrderVO> returnOrderVOList = map.get(tradeVO.getId());
                    if (CollectionUtils.isNotEmpty(returnOrderVOList)) {

                        // 查询是否存在已完成的退单
                        Optional<ReturnOrderVO> returnComplete = returnOrderVOList.stream().filter(item ->
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
                        Optional<ReturnOrderVO> returningOrder = returnOrderVOList.stream().filter(item ->
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
     * 未完全支付的定金预售订单状态填充为已作废状态
     * <p>
     * （主要订单真实作废比较延迟，计时过后仍然处于待支付尾款情况，前端由订单状态判断来控制支付尾款按钮的展示）
     *
     * @param detail 订单
     */
    public void fillTradeBookingTimeOut(TradeVO detail) {
        //未完全支付的定金预售订单
        if (Boolean.TRUE.equals(detail.getIsBookingSaleGoods())
                && BookingType.EARNEST_MONEY.equals(detail.getBookingType())
                && Objects.nonNull(detail.getTradeState())
                && (!PayState.PAID.equals(detail.getTradeState().getPayState()))) {
            //尾款时间 < 今天
            if (Objects.nonNull(detail.getTradeState().getTailEndTime())
                    && detail.getTradeState().getTailEndTime().isBefore(LocalDateTime.now())) {
                //作废
                detail.getTradeState().setFlowState(FlowState.VOID);
            }
        }
    }

}
