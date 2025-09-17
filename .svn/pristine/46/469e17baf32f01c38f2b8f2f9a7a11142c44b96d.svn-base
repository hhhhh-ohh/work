package com.wanmi.sbc.order.returnorder.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnItem;
import com.wanmi.sbc.order.returnorder.model.value.ReturnTag;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import io.seata.spring.annotation.GlobalTransactional;
import com.google.common.collect.Lists;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByCustomerIdsRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailListByCustomerIdsResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailBaseVO;
import com.wanmi.sbc.order.api.constant.RefundReasonConstants;
import com.wanmi.sbc.order.api.request.distribution.ReturnOrderSendMQRequest;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.enums.ReturnType;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.value.ReturnEventLog;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPoints;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPrice;
import com.wanmi.sbc.order.returnorder.mq.ReturnOrderProducerService;
import com.wanmi.sbc.order.trade.model.entity.value.Company;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.common.enums.NodeType;
import com.wanmi.sbc.common.enums.node.OrderProcessType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 拼团订单-自动退款业务处理
 *
 * @author: Geek Wang
 * @createDate: 2019/5/21 18:17
 * @version: 1.0
 */
@Slf4j
@Service
public class GrouponReturnOrderService {

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private GrouponOrderService grouponOrderService;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;



    @Autowired
    private ReturnOrderProducerService returnOrderProducerService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private OrderProducerService orderProducerService;

    @Autowired
    private ThirdPlatformTradeService thirdPlatformTradeService;


    /**
     * 用户支付回调，主动发起自动退款
     * 根据订单信息处理自动退款业务
     *
     * @param trade
     */
    @Transactional
    @GlobalTransactional
    public void handleGrouponOrderRefund(Trade trade) {
        log.info("===========订单信息：{}，单个订单自动退款开始========", trade);
        handleGrouponOrderRefund(Collections.singletonList(trade), Boolean.FALSE);
        log.info("===========订单信息：{}，单个订单自动退款结束========", trade);
    }

    /**
     * 机器人触发自动退款
     * 根据团编号处理自动退款业务
     *
     * @param grouponNo
     */
    @Transactional
    @GlobalTransactional
    public List<Trade> handleGrouponOrderRefund(String grouponNo) {
        log.info("=========团编号：{}，批量自动退款开始=======", grouponNo);
        List<Trade> tradeList = grouponOrderService.getTradeByGrouponNo(grouponNo);
        log.info("===========订单集合详细信息：{}，批量自动退款========", tradeList);
        if (CollectionUtils.isNotEmpty(tradeList)) {
            handleGrouponOrderRefund(tradeList, Boolean.TRUE);
        }
        log.info("=========团编号：{}，批量自动退款结束=======", grouponNo);
        return tradeList;
    }

    /**
     * 机器人触发自动退款
     * 根据团编号处理自动退款业务
     *
     * @param grouponNo
     */
    @Transactional
    @GlobalTransactional
    public List<Trade> handleGrouponOrderSyncChannel(String grouponNo) {
        log.info("=========团编号：{}，批量同步渠道订单开始=======", grouponNo);
        List<Trade> tradeList = grouponOrderService.getCompletedTradeByGrouponNo(grouponNo);
        log.info("===========订单集合详细信息：{}，批量同步渠道订单========", tradeList);
        if (CollectionUtils.isNotEmpty(tradeList)
                && tradeList.stream().anyMatch(t -> CollectionUtils.isNotEmpty(t.getThirdPlatformTypes()))) {
            tradeList.stream().filter(t -> CollectionUtils.isNotEmpty(t.getThirdPlatformTypes()))
                    .forEach(t -> thirdPlatformTradeService.createOrPay(t.getId()));
        }
        log.info("=========团编号：{}，批量同步渠道订单结束=======", grouponNo);
        return tradeList;
    }

    /**
     * 根据订单集合处理自动退款业务
     *
     * @param tradeList
     */
    public void handleGrouponOrderRefund(List<Trade> tradeList, Boolean isAuto) {
        log.info("handleGrouponOrderRefund start");
        Operator operator =
                Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                        .PLATFORM).build();
        //1、生成退单数据
        List<ReturnOrder> returnOrders = getReturnOrderByTradeList(tradeList, operator, isAuto);
        log.info("===========生成退单数据,详细信息：{}========", returnOrders);
        // 处理礼品卡
        this.returnGiftCardProcess(returnOrders);
        List<ReturnOrder> returnOrderList = new ArrayList<>(returnOrders.size());
        returnOrders.forEach(returnOrder -> returnOrderList.add(returnOrderService.addReturnOrder(returnOrder)));

        //2、根据退单数据，获取出会员详情基础信息集合
        List<CustomerDetailBaseVO> customerDetailBaseVOList = listCustomerDetailBaseByCustomerIds(returnOrderList);
        log.info("===========会员详情基础信息集合,详细信息：{}========", customerDetailBaseVOList);

        //3、生成退款单数据集合
        List<RefundOrder> refundOrderList =
                getRefundOrderByReturnOrderListAndCustomerDetailBaseVOList(returnOrderList, customerDetailBaseVOList);

        refundOrderList = refundOrderService.batchAdd(refundOrderList);
        log.info("===========生成退款单数据集合,详细信息：{}========", refundOrderList);

        //4、自动退款
		refundOrderService.autoRefund(tradeList, returnOrderList, refundOrderList, operator , null);

        //5、处理其他无关业务
        handleOther(tradeList, returnOrderList);
    }

    /**
     * 处理退单礼品卡退还
     * @param returnOrderList   退单列表
     */
    private void returnGiftCardProcess(List<ReturnOrder> returnOrderList) {
        if (CollectionUtils.isEmpty(returnOrderList)) {
            return;
        }
        returnOrderList.forEach(returnOrder -> {BigDecimal giftCardPrice = BigDecimal.ZERO;
            //组装退单详情
            if (CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
                for (ReturnItem returnItemDTO : returnOrder.getReturnItems()) {
                    if(CollectionUtils.isNotEmpty(returnItemDTO.getGiftCardItemList())) {
                        giftCardPrice = giftCardPrice.add(returnItemDTO.getGiftCardItemList().stream().map(item->Objects.isNull(item.getReturnPrice()) ? BigDecimal.ZERO: item.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
                for (ReturnItem returnItemDTO : returnOrder.getReturnPreferential()) {
                    if(CollectionUtils.isNotEmpty(returnItemDTO.getGiftCardItemList())) {
                        giftCardPrice = giftCardPrice.add(returnItemDTO.getGiftCardItemList().stream().map(item->Objects.isNull(item.getReturnPrice())? BigDecimal.ZERO:item.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                }
            }
            returnOrder.getReturnPrice().setGiftCardPrice(giftCardPrice);;}
        );
    }

    /**
     * 根据订单数据生成对应退单数据
     *
     * @param tradeList
     * @return
     */
    protected List<ReturnOrder> getReturnOrderByTradeList(List<Trade> tradeList, Operator operator, Boolean isAuto) {

        log.info("getReturnOrderByTradeList start");
        List<ReturnOrder> returnOrderList = new ArrayList<>();

        for (Trade trade : tradeList) {
            ReturnOrder returnOrder = new ReturnOrder();
            //退单ReturnTag标记
            ReturnTag returnTag = new ReturnTag();
            OrderTag orderTag = trade.getOrderTag();
            returnTag.setVirtualFlag(orderTag.getVirtualFlag());
            returnTag.setElectronicCouponFlag(orderTag.getElectronicCouponFlag());
            returnOrder.setReturnTag(returnTag);
            //String rid = generatorService.generate("R");
            //退单号
            //returnOrder.setId(rid);
            //订单编号
            returnOrder.setTid(trade.getId());
            //购买人信息
            returnOrder.setBuyer(trade.getBuyer());
            //商家信息
            returnOrder.setCompany(Company.builder().companyInfoId(trade.getSupplier().getSupplierId())
                    .companyCode(trade.getSupplier().getSupplierCode()).supplierName(trade.getSupplier().getSupplierName())
                    .storeId(trade.getSupplier().getStoreId()).storeName(trade.getSupplier().getStoreName()).storeType(trade.getSupplier().getStoreType())
                    .companyType(trade.getSupplier().getIsSelf() ? BoolFlag.NO : BoolFlag.YES)
                    .build());
            //描述信息
            returnOrder.setDescription(isAuto ? RefundReasonConstants.Q_ORDER_SERVICE_GROUPON_AUTO_REFUND :
                    RefundReasonConstants.Q_ORDER_SERVICE_GROUPON_AUTO_REFUND_USER);
            //退货商品
            returnOrder.setReturnItems(returnOrderService.chgTradeTime(trade.getTradeItems()));
            returnOrder.setReturnGifts(returnOrderService.chgTradeTime(trade.getGifts()));
            returnOrder.setReturnPreferential(returnOrderService.chgTradeTime(trade.getPreferential()));

            TradePrice tradePrice = trade.getTradePrice();
            ReturnPrice returnPrice = new ReturnPrice();

            returnPrice.setApplyPrice(tradePrice.getTotalPrice());
            //实退金额
            returnPrice.setActualReturnPrice(returnPrice.getApplyPrice());
            //商品总金额
            returnPrice.setTotalPrice(tradePrice.getTotalPrice());
            //申请金额可用
            returnPrice.setApplyStatus(Boolean.TRUE);
            //退货总金额
            returnOrder.setReturnPrice(returnPrice);
            //收货人信息
            returnOrder.setConsignee(trade.getConsignee());
            //退货单状态
            returnOrder.setReturnFlowState(ReturnFlowState.AUDIT);

            //记录日志
            returnOrder.appendReturnEventLog(
                    new ReturnEventLog(operator, "拼团订单自动退单", "拼团订单自动退单", "拼团订单自动退款", LocalDateTime.now())
            );

            // 支付方式
            returnOrder.setPayType(PayType.valueOf(trade.getPayInfo().getPayTypeName()));
            //退单类型
            returnOrder.setReturnType(ReturnType.REFUND);
            //退单来源
            returnOrder.setPlatform(operator.getPlatform());
            //退积分信息
            returnOrder.setReturnPoints(ReturnPoints.builder()
                    .applyPoints(Objects.isNull(tradePrice.getPoints()) ? NumberUtils.LONG_ZERO :
                            tradePrice.getPoints())
                    .actualPoints(Objects.isNull(tradePrice.getPoints()) ? NumberUtils.LONG_ZERO :
                            tradePrice.getPoints()).build());
            //创建时间
            returnOrder.setCreateTime(LocalDateTime.now());

            //考虑商品渠道问题，拆分退单
            returnOrderService.splitReturnTrade(returnOrder, trade, returnOrderList);
        }

        for (ReturnOrder newReturnOrder : returnOrderList) {
            newReturnOrder.setId(generatorService.generate("R"));
        }
        return returnOrderList;
    }

    /**
     * 订单日志、发送MQ消息
     *
     * @param tradeList
     * @param returnOrderList
     */
    protected void handleOther(List<Trade> tradeList, List<ReturnOrder> returnOrderList) {
        Map<String, ReturnOrder> map = returnOrderList.stream().collect(Collectors.toMap(ReturnOrder::getTid, Function.identity()));
        for (Trade trade : tradeList) {
            ReturnOrder returnOrder = map.get(trade.getId());
            ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                    .addFlag(true)
                    .customerId(trade.getBuyer().getId())
                    .orderId(trade.getId())
                    .returnId(returnOrder.getId())
                    .build();
            returnOrderProducerService.returnOrderFlow(sendMQRequest);

            Map<String, Object> routeParam = new HashMap<>();
            routeParam.put("type", NodeType.ORDER_PROGRESS_RATE.toValue());
            routeParam.put("node",OrderProcessType.JOIN_GROUP_FAIL.toValue());
            routeParam.put("id", trade.getTradeGroupon().getGrouponNo());

            MessageMQRequest messageMQRequest = new MessageMQRequest();
            messageMQRequest.setNodeCode(OrderProcessType.JOIN_GROUP_FAIL.getType());
            messageMQRequest.setNodeType(NodeType.ORDER_PROGRESS_RATE.toValue());
            messageMQRequest.setParams(Lists.newArrayList(trade.getTradeItems().get(0).getSkuName()));
            messageMQRequest.setRouteParam(routeParam);
            messageMQRequest.setCustomerId(trade.getBuyer().getId());
            messageMQRequest.setPic(trade.getTradeItems().get(0).getPic());
            messageMQRequest.setMobile(trade.getBuyer().getAccount());
            orderProducerService.sendMessage(messageMQRequest);
            // 拼团失败，发送订阅消息
            try {
                // 商品名称
                String skuName = trade.getTradeItems().get(0).getSkuName();
                // 拼团价
                BigDecimal price = trade.getTradeItems().get(0).getPrice();
                // 订单状态
                String tradeStateDes = "拼团失败";
                // 退款金额
                BigDecimal actualReturnPrice = returnOrder.getReturnPrice().getActualReturnPrice();

                orderProducerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                        .triggerNodeId(TriggerNodeType.GROUPON_FAIL).skuName(skuName).price(String.valueOf(price)).tradeId(trade.getId())
                        .customerId(trade.getBuyer().getId()).tradeStateDes(tradeStateDes).actualReturnPrice(String.valueOf(actualReturnPrice)).build());
            }catch (Exception e){
                log.error("拼团失败，订阅消息发送失败，trade={}, returnOrder={}", JSON.toJSONString(trade), JSON.toJSONString(returnOrder));
            }
        }

    }

    /**
     * 根据退单数据、会员详情信息集合，生成对应的退款单数据集合
     *
     * @param returnOrderList
     * @param customerDetailBaseVOList
     * @return
     */
    protected List<RefundOrder> getRefundOrderByReturnOrderListAndCustomerDetailBaseVOList(List<ReturnOrder> returnOrderList, List<CustomerDetailBaseVO> customerDetailBaseVOList) {
        List<RefundOrder> refundOrderList = new ArrayList<>(returnOrderList.size());
        Map<String, String> map =
                customerDetailBaseVOList.stream().collect(Collectors.toMap(CustomerDetailBaseVO::getCustomerId,
                        CustomerDetailBaseVO::getCustomerDetailId));
        for (ReturnOrder returnOrder : returnOrderList) {
            RefundOrder refundOrder = new RefundOrder();
            refundOrder.setReturnOrderCode(returnOrder.getId());
            refundOrder.setCustomerDetailId(map.get(returnOrder.getBuyer().getId()));
            refundOrder.setCreateTime(LocalDateTime.now());
            refundOrder.setRefundCode(generatorService.generateRid());
            refundOrder.setRefundStatus(RefundStatus.TODO);
            refundOrder.setReturnPrice(returnOrder.getReturnPrice().getApplyPrice());
            refundOrder.setReturnPoints(Objects.nonNull(returnOrder.getReturnPoints()) ?
                    returnOrder.getReturnPoints().getApplyPoints() : null);
            refundOrder.setGiftCardPrice(returnOrder.getReturnPrice().getGiftCardPrice());
            refundOrder.setDelFlag(DeleteFlag.NO);
            refundOrder.setPayType(returnOrder.getPayType());
            refundOrder.setSupplierId(returnOrder.getCompany().getCompanyInfoId());
            refundOrderList.add(refundOrder);
        }
        return refundOrderList;
    }

    /**
     * 根据会员ID集合查询会员详情基础信息集合
     *
     * @param returnOrderList
     * @return
     */
    protected List<CustomerDetailBaseVO> listCustomerDetailBaseByCustomerIds(List<ReturnOrder> returnOrderList) {
        List<String> customerIds =
                returnOrderList.stream().map(returnOrder -> returnOrder.getBuyer().getId()).collect(Collectors.toList());
        BaseResponse<CustomerDetailListByCustomerIdsResponse> baseResponse =
                customerDetailQueryProvider.listCustomerDetailBaseByCustomerIds(new CustomerDetailListByCustomerIdsRequest(customerIds));
        return baseResponse.getContext().getList();
    }
}
