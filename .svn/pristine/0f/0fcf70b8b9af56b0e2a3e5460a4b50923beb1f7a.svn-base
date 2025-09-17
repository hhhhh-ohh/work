package com.wanmi.sbc.trade;

import com.wanmi.sbc.account.api.provider.credit.CreditOrderQueryProvider;
import com.wanmi.sbc.account.api.request.credit.RepayOrderPageRequest;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByConditionRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListAllRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListByParentIdRequest;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>客户端支付公共方法</p>
 * Created by of628-wenzhi on 2019-07-24-19:56.
 */
@Service
public class PayServiceHelper {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CreditOrderQueryProvider customerCreditOrderQueryProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 获取可用于支付交易的订单号（子订单号或父订单号）
     *
     * @param id
     * @param parentId
     * @return
     */
    public String getPayBusinessId(String id, String parentId, String creditRepayId) {
        String oid = StringUtils.isNotBlank(id) ? id : StringUtils.isNotBlank(parentId) ? parentId : creditRepayId;
        List<TradeVO> trades = findTrades(oid);
        if (CollectionUtils.isNotEmpty(trades)) {
            TradeVO trade = trades.get(0);
            // 定金预售商品且已支付定金
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() == BookingType.EARNEST_MONEY && trade.getTradeState().getPayState() == PayState.PAID_EARNEST) {

                // 如果是授信支付还款则直接返回
                Boolean isRepay = Boolean.FALSE;
                if(Objects.nonNull(trade.getCreditPayInfo())){
                    //过滤定金支付使用授信的订单校验  和  定金支付且作废的订单
                    CreditPayState creditPayState = trade.getCreditPayInfo().getCreditPayState();
                    FlowState flowState = trade.getTradeState().getFlowState();
                    if(null != creditPayState){
                        if(!creditPayState.equals(CreditPayState.DEPOSIT)
                                || (creditPayState.equals(CreditPayState.DEPOSIT)
                                && (flowState.equals(FlowState.COMPLETED) || flowState.equals(FlowState.VOID))))
                            isRepay = Boolean.TRUE;
                    }
                }

                if(isRepay){
                    return oid;
                }

                if (trade.getTailOrderNo() == null) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
                }
                oid = trade.getTailOrderNo();
            }
        }
        return oid;
    }

    public String getPayBusinessId(String tid){
        String oid = tid;
        List<TradeVO> trades = findTrades(tid);
        if (CollectionUtils.isNotEmpty(trades)) {
            TradeVO trade = trades.get(0);
            // 定金预售商品且已支付定金
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() == BookingType.EARNEST_MONEY && trade.getTradeState().getPayState() == PayState.PAID_EARNEST) {

                // 如果是授信支付还款则直接返回
                Boolean isRepay = Boolean.FALSE;
                if(Objects.nonNull(trade.getCreditPayInfo())){
                    //过滤定金支付使用授信的订单校验  和  定金支付且作废的订单
                    CreditPayState creditPayState = trade.getCreditPayInfo().getCreditPayState();
                    FlowState flowState = trade.getTradeState().getFlowState();
                    if(null != creditPayState){
                        if(!creditPayState.equals(CreditPayState.DEPOSIT)
                                || (creditPayState.equals(CreditPayState.DEPOSIT)
                                && (flowState.equals(FlowState.COMPLETED) || flowState.equals(FlowState.VOID)))){
                            isRepay = Boolean.TRUE;
                        }
                    }
                }

                if(isRepay){
                    return oid;
                }

                if (trade.getTailOrderNo() == null) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
                }
                oid = trade.getTailOrderNo();
            }
        }
        return oid;
    }

    /**
     * 根据订单号或父订单号获取订单信息，用于支付前获取订单信息
     *
     * @param businessId 订单号（单笔支付）或 父订单号（多笔订单合并支付）
     * @return 订单信息集合
     */
    public List<TradeVO> findTrades(String businessId) {
        String customerId = commonUtil.getOperator().getUserId();
        List<TradeVO> tradeVOList = new ArrayList<>();
        if (businessId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID)) {
            tradeVOList.addAll(tradeQueryProvider.listAll(TradeListAllRequest.builder().tradeQueryDTO(
                    TradeQueryDTO.builder().tailOrderNo(businessId).build()).build()).getContext().getTradeVOList());
        } else if (businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID) || businessId.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)) {
            tradeVOList.addAll(tradeQueryProvider.getListByParentId(TradeListByParentIdRequest.builder().parentTid(businessId).customerId(customerId)
                    .build()).getContext().getTradeVOList());
        } else if (businessId.startsWith(GeneratorService._PREFIX_TRADE_ID) || businessId.startsWith(GeneratorService.NEW_PREFIX_TRADE_ID)) {
            tradeVOList.add(tradeQueryProvider.getByIdForBalancePay(TradeGetByIdRequest.builder().customerId(customerId)
                    .tid(businessId).build()).getContext().getTradeVO());
        } else if (businessId.startsWith(GeneratorService._PREFIX_CREDIT_REPAY_ID)) {
            List<CustomerCreditOrderVO> creditOrderVOList = customerCreditOrderQueryProvider.list(RepayOrderPageRequest.builder()
                    .repayOrderCode(businessId)
                    .build()).getContext().getCustomerCreditOrderVOList();
            if (CollectionUtils.isNotEmpty(creditOrderVOList)){
                String[] orderIds = creditOrderVOList.stream()
                        .map(CustomerCreditOrderVO::getOrderId)
                        .distinct()
                        .toArray(String[]::new);
                List<TradeVO> trades = tradeQueryProvider.listAll(TradeListAllRequest.builder()
                        .tradeQueryDTO(TradeQueryDTO.builder().ids(orderIds).build())
                        .build()).getContext().getTradeVOList();

                //扭转预售商品支付尾款状态为已作废
                trades.forEach(this::fillTradeBookingTimeOut);
                this.wrapperCreditTrade(trades);
                tradeVOList.addAll(trades);
            }
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return tradeVOList;
    }

    /**
     * 公共方法，支付前校验订单状态，已作废，未审核并且已支付的订单做异常处理
     *
     * @param tradeList 订单列表
     */
    public void checkPayBefore(List<TradeVO> tradeList) {
        tradeList.forEach(i -> {

            Boolean isRepay = Boolean.FALSE;
            if(Objects.nonNull(i.getCreditPayInfo())){
                //过滤定金支付使用授信的订单校验  和  定金支付且作废的订单
                CreditPayState creditPayState = i.getCreditPayInfo().getCreditPayState();
                FlowState flowState = i.getTradeState().getFlowState();
                if(null != creditPayState){
                    if(!creditPayState.equals(CreditPayState.DEPOSIT)
                            || (creditPayState.equals(CreditPayState.DEPOSIT)
                            && (flowState.equals(FlowState.COMPLETED) || flowState.equals(FlowState.VOID)))) {
                        isRepay = Boolean.TRUE;
                    }
                }
            }

            //如果是还款 终止后面的校验
            if(isRepay){
                if (i.getCreditPayInfo().getHasRepaid()) {
                    // 部分订单已还款，请重新选择关联订单
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020029);
                }
                if (Objects.nonNull(i.getReturningFlag()) && i.getReturningFlag()) {
                    // 部分订单退货/退款中，请重新选择关联订单
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020030);
                }
                return;
            }

            //添加订单失效时间过去了就不能支付
            if ((null != i.getOrderTimeOut() && i.getOrderTimeOut().isBefore(LocalDateTime.now()))
                    || (i.getTradeState().getFlowState() == FlowState.INIT) || (i.getTradeState().getFlowState() ==
                    FlowState.VOID)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050068);
            }
            if (i.getTradeState().getPayState() == PayState.PAID) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
            }
            PayInfoVO payInfo = i.getPayInfo();
            //若商家后台修改了支付方式，则通知前端
            if (payInfo.getPayTypeId().equals(String.valueOf(Constants.ONE))) {
                throw new SbcRuntimeException(SettingErrorCodeEnum.K070085);
            }
        });
    }

    public List<TradeVO> checkTrades(String id) {
        List<TradeVO> trades = findTrades(id);
        if (CollectionUtils.isEmpty(trades)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        checkPayBefore(trades);
        return trades;
    }

    public BigDecimal calcTotalPriceByPenny(List<TradeVO> trades) {
        BigDecimal totalPrice;
        TradeVO trade = trades.get(0);
        //如果授信付款，订单已支付
        if(Objects.nonNull(trade.getCreditPayInfo())){
            if (trade.getTradeState().getPayState() == PayState.PAID) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
            }
        }
        if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getBookingType() == BookingType.EARNEST_MONEY
                && trade.getTradeState().getPayState() == PayState.NOT_PAID) {
            totalPrice = trade.getTradePrice().getEarnestPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN);
        } else if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()
                && trade.getBookingType() == BookingType.EARNEST_MONEY && trade.getTradeState().getPayState() == PayState.PAID_EARNEST) {
            totalPrice = trade.getTradePrice().getTailPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN);
        } else {
            //订单总金额
            totalPrice = trades.stream().map(i -> i.getTradePrice().getTotalPrice().multiply(new BigDecimal(100))
                    .setScale(0, RoundingMode.DOWN)).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //订单总金额
        return totalPrice;
    }

    public BigDecimal calcTotalPriceByYuan(List<TradeVO> trades) {
        BigDecimal totalPrice;
        TradeVO trade = trades.get(0);

        //如果授信付款，订单已支付
        if(Objects.nonNull(trade.getCreditPayInfo())){
            if (trade.getTradeState().getPayState() == PayState.PAID) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
            }
        }


        if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getBookingType() == BookingType.EARNEST_MONEY
                && trade.getTradeState().getPayState() == PayState.NOT_PAID) {
            totalPrice = trade.getTradePrice().getEarnestPrice();
        } else if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()
                && trade.getBookingType() == BookingType.EARNEST_MONEY && trade.getTradeState().getPayState() == PayState.PAID_EARNEST) {
            totalPrice = trade.getTradePrice().getTailPrice();
        } else {
            //订单总金额
            totalPrice = trades.stream().map(i -> i.getTradePrice().getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //订单总金额
        return totalPrice;
    }

    public String buildBody(List<TradeVO> trades) {
        TradeVO trade = trades.get(0);
        String body = StringUtils.substring(trade.getTradeItems().get(0).getSkuName(), 0, 20) + (trade.getTradeItems().get(0).getSpecDetails
                () == null ? "" : StringUtils.substring(trade.getTradeItems().get(0).getSpecDetails(), 0, 18));
        if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
            body = body + "等多件商品";
        }
        return body.trim();
    }

    public String buildTitle(List<TradeVO> trades) {
        TradeVO trade = trades.get(0);
        String title = trade.getTradeItems().get(0).getSkuName();
        if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
            if (title.length() > Constants.NUM_23) {
                title = title.substring(0, 22) + "...等多件商品";
            } else {
                title = title + "等多件商品";
            }
        } else {
            if (title.length() > Constants.NUM_29) {
                title = title.substring(0, 28) + "...";
            }
        }
        return title;
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
     * 计算授信订单还款金额
     *
     * @param trades
     * @return
     */
    public BigDecimal calcCreditTotalPriceByPenny(List<TradeVO> trades) {
        //订单总金额
        return trades.stream().map(i -> i.getCanRepayPrice().multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.DOWN)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 计算授信订单还款金额
     *
     * @param trades
     * @return
     */
    public BigDecimal calcCreditTotalPriceByYuan(List<TradeVO> trades) {
        //订单总金额
        return trades.stream()
                .map(TradeVO::getCanRepayPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    /**
     * 封装还款订单-可还款金额
     * <p>
     * 关联订单可以选择的订单：
     * 1、已完成且无退单
     * 2、定金支付且已作废
     * 3、已完成且取消退单
     * 4、已完成且部分退货退款的订单
     *
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
                // 默认都不可选中
                tradeVO.setCanCheckFlag(Boolean.FALSE);
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

                TradeStateVO tradeState = tradeVO.getTradeState();
                // 只有当订单完成 或 已支付定金但已作废 才可以选中
                if ((tradeState.getFlowState().equals(FlowState.COMPLETED)
                            && tradeState.getFinalTime() != null
                            && tradeState.getFinalTime().isBefore(LocalDateTime.now()))
                        || (Objects.equals(tradeVO.getBookingType(), BookingType.EARNEST_MONEY)
                            && tradeState.getFlowState().equals(FlowState.VOID))) {
                    tradeVO.setCanCheckFlag(Boolean.TRUE);
                }
            });
            // 处理退货退款中的订单
            if (CollectionUtils.isNotEmpty(returnOrderList)) {
                Map<String, List<ReturnOrderVO>> map =
                        returnOrderList.stream().collect(Collectors.groupingBy(ReturnOrderVO::getTid));
                tradeVOS.stream().map(tradeVO -> {
                    List<ReturnOrderVO> returnOrderVOList = map.get(tradeVO.getId());
                    if (CollectionUtils.isNotEmpty(returnOrderVOList)) {
                        // 查询是否存在已作废、拒绝退款、拒绝退货、退款失败的退单
                        Optional<ReturnOrderVO> canCheckObj = returnOrderVOList.stream().filter(item ->
                                item.getReturnFlowState() == ReturnFlowState.VOID
                                        || item.getReturnFlowState() == ReturnFlowState.REJECT_REFUND
                                        // 退款失败,拒绝收货
                                        || item.getReturnFlowState() == ReturnFlowState.REFUND_FAILED
                                        || item.getReturnFlowState() == ReturnFlowState.REJECT_RECEIVE
                        ).findFirst();

                        // 查询是否存在已完成的退单
                        Optional<ReturnOrderVO> returnComplete = returnOrderVOList.stream().filter(item ->
                                item.getReturnFlowState() == ReturnFlowState.COMPLETED
                        ).findFirst();
                        // 如果订单状态为已完成 如果存在符合条件的退单
                        if (tradeVO.getTradeState().getFlowState() == FlowState.COMPLETED || tradeVO.getTradeState().getFlowState() == FlowState.VOID) {
                            // 如果存在符合条件的退单
                            if (canCheckObj.isPresent() && tradeVO.getTradeState().getFinalTime() != null
                                    && tradeVO.getTradeState().getFinalTime().isBefore(LocalDateTime.now())) {
                                // 则可以选
                                tradeVO.setCanCheckFlag(Boolean.TRUE);
                            }
                            // 订单已完成 且 退单已完成
                            if (returnComplete.isPresent()) {
                                // 退单总金额
                                BigDecimal returnPrice = returnOrderVOList.stream().filter(returnOrderVO -> returnOrderVO.getReturnFlowState() == ReturnFlowState.COMPLETED)
                                        .map(returnOrder -> returnOrder.getReturnPrice().getActualReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                // 退单件数
//                            Long returnNums = returnOrderVOList.stream().filter(returnOrderVO -> returnOrderVO.getReturnFlowState() == ReturnFlowState.COMPLETED)
//                                    .mapToLong(returnOrderVO -> returnOrderVO.getReturnItems().stream().map(ReturnItemVO::getNum).reduce(0, (a, b) -> a + b)).sum();
                                // 订单总金额
                                BigDecimal orderPrice = tradeVO.getTradePrice().getTotalPrice();
                                // 订单件数
//                            Long orderNums = tradeVO.getTradeItems().stream().map(TradeItemVO::getNum).reduce(0L, (a, b) -> a + b);
                                // 金额差异：订单金额-退单金额
                                BigDecimal canRepayPrice = orderPrice.subtract(returnPrice).setScale(2, RoundingMode.DOWN);
                                // 可还款金额
                                tradeVO.setCanRepayPrice(canRepayPrice);

                                // 订单总金额 大于 退单总金额 则是 可以选择
                                if (canRepayPrice.compareTo(BigDecimal.ZERO) > 0 && tradeVO.getTradeState().getFinalTime() != null
                                        && tradeVO.getTradeState().getFinalTime().isBefore(LocalDateTime.now())) {
                                    // 则可以选
                                    tradeVO.setCanCheckFlag(Boolean.TRUE);
                                } else {
                                    // 则不可以选
                                    tradeVO.setCanCheckFlag(Boolean.FALSE);
                                }
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
                            tradeVO.setCanCheckFlag(Boolean.FALSE);
                        }
                    }
                    return tradeVO;
                }).collect(Collectors.toList());
            }
        }
    }

    /**
     * @description 校验是否为代销订单，是的话封装成普通订单
     * @author malianfeng 
     * @date 2022/5/14 14:14
     * @param tradeDTO 订单
     */
    public boolean checkAndWrapperTradeSellInfo(TradeDTO tradeDTO) {
        if (Objects.nonNull(tradeDTO) && Objects.nonNull(tradeDTO.getSellPlatformType())) {
            tradeDTO.setSellPlatformType(SellPlatformType.NOT_SELL);
            tradeDTO.setSellPlatformOrderId(null);
            tradeDTO.setSceneGroup(null);
            return true;
        }
        return false;
    }

    /**
     * 是否是付费会员支付
     * @param tId
     * @return
     */
    public boolean isPayMember(String tId) {
        return StringUtils.isNotEmpty(tId) &&
                tId.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID);
    }

    /**
     * 重新生成id
     *
     * @param tid
     * @return
     */
    public String regenerateId(String tid) {
        if (StringUtils.isEmpty(tid)) {
            return "";
        } else if (tid.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
            return generatorService.generatePayingMemberRecordId();
        } else if (tid.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)
                || tid.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID)) {
            return generatorService.generatePoId();
        } else if (tid.startsWith(GeneratorService.NEW_PREFIX_TRADE_ID)
                || tid.startsWith(GeneratorService._PREFIX_TRADE_ID)) {
            return generatorService.generateTid();
        } else if (tid.startsWith(GeneratorService._PREFIX_CREDIT_REPAY_ID)) {
            return generatorService.generateRepayOrderCode();
        } else if (tid.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID)) {
            return generatorService.generateTailTid();
        }

        return "";
    }
}
