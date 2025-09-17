package com.wanmi.sbc.order.trade.fsm.action;

import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.order.bean.dto.CycleDeliveryPlanDTO;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.bean.enums.CycleDeliveryState;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.paycallback.PayCallBackUtil;
import com.wanmi.sbc.order.paycallbackresult.model.root.PayCallBackResult;
import com.wanmi.sbc.order.trade.fsm.TradeAction;
import com.wanmi.sbc.order.trade.fsm.TradeStateContext;
import com.wanmi.sbc.order.trade.fsm.params.StateRequest;
import com.wanmi.sbc.order.trade.model.entity.TradeDeliver;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.ShippingItem;
import com.wanmi.sbc.order.trade.model.entity.value.TradeEventLog;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.service.WxPayUploadShippingInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/4/21.
 */
@Component
@Slf4j
public class DeliverAction extends TradeAction {

    @Autowired
    private PayCallBackUtil payCallBackUtil;

    @Autowired
    private WxPayUploadShippingInfoService wxPayUploadShippingInfoService;

    @Override
    protected void evaluateInternal(Trade trade, StateRequest request, TradeStateContext tsc) {
        Integer remindShipping = request.getRemindShipping();
        if (Objects.nonNull(remindShipping)) {
            trade.getTradeBuyCycle().setRemindShipping(remindShipping);
        }
        deliver(trade, tsc.getOperator(), tsc.getRequestData());
    }

    /**
     * 是否全部发货
     *
     * @return
     */
    private boolean isAllShipped(Trade trade) {
        List<TradeItem> allCollect = new ArrayList<>();
        //退货商品数量
        Map<String, Integer> returnItemMap = tradeService.getReturnEndItemNum(trade.getId(), Boolean.FALSE);
        Map<Long, Map<String, Integer>> giftMarketingIdToNumMap =
                tradeService.getGiftReturnEndItemNum(trade.getId());
        Map<Long, Map<String, Integer>> marketingIdToNumMap = tradeService.getReturnEndItemNum(trade.getId());
        OrderTag orderTag = trade.getOrderTag();
        if (Objects.nonNull(orderTag) && Objects.equals(orderTag.getBuyCycleFlag(),Boolean.TRUE)) {
            TradeItem tradeItem = trade.getTradeItems().stream().findFirst()
                    .orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030047));
            Integer returnNum = returnItemMap.get(tradeItem.getSkuId());
            //说明周期购订单已经售后，不能发货
            if (Objects.nonNull(returnNum) && returnNum > 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050047, new Object[]{trade.getId()});
            }
            TradeBuyCycleDTO tradeBuyCycle = trade.getTradeBuyCycle();
            List<CycleDeliveryPlanDTO> deliveryPlanS = tradeBuyCycle.getDeliveryPlanS();
            long count = deliveryPlanS.parallelStream().filter(cycleDeliveryPlanDTO ->
                    Objects.equals(CycleDeliveryState.NOT_SHIP, cycleDeliveryPlanDTO.getCycleDeliveryState())).count();
            if (count == 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050087);
            }
            //如果只剩下一期未发货，就是全部发货，否则部分发货
            return Objects.equals(count,BigDecimal.ONE.longValue());
        }
        //主订单的发货状态不受作废子订单影响
        List<ProviderTrade> providerTrades = providerTradeService.findListByParentId(trade.getId());
        List<ProviderTrade> voidProviderTradeList
                = providerTrades.stream().filter(providerTrade -> providerTrade.getTradeState().getFlowState() == FlowState.VOID).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(voidProviderTradeList)){
            List<Long> providerAndStoreIds = new ArrayList<>();
            voidProviderTradeList.forEach(providerTrade -> {
                if (CollectionUtils.isNotEmpty(providerTrade.getTradeItems())){
                    if (providerTrade.getTradeItems().get(0).getProviderId() != null) {
                        providerAndStoreIds.add(providerTrade.getTradeItems().get(0).getProviderId());
                    } else {
                        providerAndStoreIds.add(providerTrade.getTradeItems().get(0).getStoreId());
                    }
                }else if (CollectionUtils.isNotEmpty(providerTrade.getGifts())){
                    if (providerTrade.getGifts().get(0).getProviderId() != null) {
                        providerAndStoreIds.add(providerTrade.getGifts().get(0).getProviderId());
                    } else {
                        providerAndStoreIds.add(providerTrade.getGifts().get(0).getStoreId());
                    }
                } else if (CollectionUtils.isNotEmpty(providerTrade.getPreferential())){
                    if (providerTrade.getPreferential().get(0).getProviderId() != null) {
                        providerAndStoreIds.add(providerTrade.getPreferential().get(0).getProviderId());
                    } else {
                        providerAndStoreIds.add(providerTrade.getPreferential().get(0).getStoreId());
                    }
                }
            });
            allCollect.addAll(trade.getTradeItems().stream().filter(item -> {
                if (item.getProviderId() != null){
                    return !providerAndStoreIds.contains(item.getProviderId());
                }else{
                    return !providerAndStoreIds.contains(item.getStoreId());
                }
            }).map(item -> {
                //拷贝新数据，防止下面操作修改原数据
                TradeItem tradeItem = KsBeanUtil.convert(item, TradeItem.class);
                //购买数量扣除售后完成的数量
                Integer returnNum = returnItemMap.get(item.getSkuId());
                if (Objects.nonNull(returnNum) && returnNum > 0) {
                    tradeItem.setNum(item.getNum() - returnNum);
                }
                return tradeItem;
            }).collect(Collectors.toList()));
            allCollect.addAll(trade.getGifts().stream().filter(item -> {
                if (item.getProviderId() != null){
                    return !providerAndStoreIds.contains(item.getProviderId());
                }else{
                    return !providerAndStoreIds.contains(item.getStoreId());
                }
            }).map(item -> {
                //拷贝新数据，防止下面操作修改原数据
                TradeItem gifts = KsBeanUtil.convert(item, TradeItem.class);
                Map<String, Integer> returnGiftsMap =
                        giftMarketingIdToNumMap.getOrDefault(item.getMarketingIds().get(0), new HashMap<>());
                //购买数量扣除售后完成的数量
                Integer returnNum = returnGiftsMap.get(item.getSkuId());
                if (Objects.nonNull(returnNum) && returnNum > 0) {
                    gifts.setNum(item.getNum() - returnNum);
                }
                return gifts;
            }).collect(Collectors.toList()));
            // 加价购商品
            allCollect.addAll(trade.getPreferential().stream().filter(item -> {
                if (item.getProviderId() != null){
                    return !providerAndStoreIds.contains(item.getProviderId());
                }else{
                    return !providerAndStoreIds.contains(item.getStoreId());
                }
            }).map(item -> {
                //拷贝新数据，防止下面操作修改原数据
                TradeItem preferential = KsBeanUtil.convert(item, TradeItem.class);
                Map<String, Integer> returnPreferentialMap =
                        marketingIdToNumMap.getOrDefault(item.getMarketingIds().get(0), new HashMap<>());
                //购买数量扣除售后完成的数量
                Integer returnNum = returnPreferentialMap.get(item.getSkuId());
                if (Objects.nonNull(returnNum) && returnNum > 0) {
                    preferential.setNum(item.getNum() - returnNum);
                }
                return preferential;
            }).collect(Collectors.toList()));
        }else{
            List<TradeItem> tradeItems = trade.getTradeItems().stream().map(item -> {
                //拷贝新数据，防止下面操作修改原数据
                TradeItem tradeItem = KsBeanUtil.convert(item, TradeItem.class);
                //购买数量扣除售后完成的数量
                Integer returnNum = returnItemMap.get(item.getSkuId());
                if (Objects.nonNull(returnNum) && returnNum > 0) {
                    tradeItem.setNum(item.getNum() - returnNum);
                }
                return tradeItem;
            }).collect(Collectors.toList());
            List<TradeItem> tradeGifts = trade.getGifts().stream().map(item -> {
                //拷贝新数据，防止下面操作修改原数据
                TradeItem gifts = KsBeanUtil.convert(item, TradeItem.class);
                //购买数量扣除售后完成的数量
                Map<String, Integer> returnGiftMap =
                        giftMarketingIdToNumMap.get(item.getMarketingIds().get(0));
                if (MapUtils.isNotEmpty(returnGiftMap)){
                    Integer returnNum = returnGiftMap.get(item.getSkuId());
                    if (Objects.nonNull(returnNum) && returnNum > 0) {
                        gifts.setNum(item.getNum() - returnNum);
                    }
                }
                return gifts;
            }).collect(Collectors.toList());
            // 加价购
            List<TradeItem> tradePreferential = trade.getPreferential().stream().map(item -> {
                //拷贝新数据，防止下面操作修改原数据
                TradeItem preferential = KsBeanUtil.convert(item, TradeItem.class);
                Map<String, Integer> returnPreferentialMap = marketingIdToNumMap.get(item.getMarketingIds().get(0));
                if (MapUtils.isNotEmpty(returnPreferentialMap)){
                    //购买数量扣除售后完成的数量
                    Integer returnNum = returnPreferentialMap.get(item.getSkuId());
                    if (Objects.nonNull(returnNum) && returnNum > 0) {
                        preferential.setNum(item.getNum() - returnNum);
                    }
                }

                return preferential;
            }).collect(Collectors.toList());
            allCollect.addAll(tradeItems);
            allCollect.addAll(tradeGifts);
            allCollect.addAll(tradePreferential);
        }
        List<TradeItem> collect = allCollect
                .stream()
                .filter(tradeItem -> tradeItem.getNum() > 0 && !Objects.equals(tradeItem.getDeliveredNum(), tradeItem.getNum()))
                .collect(Collectors.toList());
        return collect.isEmpty();
    }

    /**
     * 发货
     *
     * @param trade
     * @param tradeDeliver
     */
    private void deliver(Trade trade, Operator operator, TradeDeliver tradeDeliver) {
        // 保存原始发货状态
        DeliverStatus originalDeliverStatus = trade.getTradeState().getDeliverStatus();
        // 如果没有收货信息, 就用之前的存的收货信息
        if (tradeDeliver.getConsignee() == null) {
            tradeDeliver.setConsignee(trade.getConsignee());
        }

        StringBuilder stringBuilder = new StringBuilder(200);

        //处理商品
        handleNormalShippingItems(trade, tradeDeliver.getShippingItems(),
                stringBuilder, operator);

        //处理赠品
        handleGiftShippingItems(trade, tradeDeliver.getGiftItemList(),
                stringBuilder, operator);

        // 加价购商品处理
        handlePreferentialShippingItems(trade, tradeDeliver.getPreferentialItemList(),
                stringBuilder, operator);

        stringBuilder.trimToSize();

        // 更改发货状态
        boolean allShipped = isAllShipped(trade);
        OrderTag orderTag = trade.getOrderTag();
        if (Objects.nonNull(orderTag) && Objects.equals(orderTag.getBuyCycleFlag(),Boolean.TRUE)) {
            TradeBuyCycleDTO tradeBuyCycle = trade.getTradeBuyCycle();
            List<CycleDeliveryPlanDTO> deliveryPlanS = tradeBuyCycle.getDeliveryPlanS();
            CycleDeliveryPlanDTO planDTO = deliveryPlanS.parallelStream()
                    .filter(cycleDeliveryPlanDTO -> Objects.equals(CycleDeliveryState.NOT_SHIP, cycleDeliveryPlanDTO.getCycleDeliveryState()))
                    .min(Comparator.comparingInt(CycleDeliveryPlanDTO::getDeliveryNum))
                    .orElseThrow(() -> new SbcRuntimeException(OrderErrorCodeEnum.K050157));
            Integer deliveryNum = planDTO.getDeliveryNum();
            planDTO.setCycleDeliveryState(CycleDeliveryState.SHIPPED);
            tradeDeliver.setCycleDeliveryPlan(planDTO);
            if (allShipped) {
                //全部发货 没有下一期
                for (CycleDeliveryPlanDTO plan: deliveryPlanS) {
                    if (Objects.equals(deliveryNum,plan.getDeliveryNum())) {
                        plan.setCycleDeliveryState(CycleDeliveryState.SHIPPED);
                        break;
                    }
                }
                trade.getTradeBuyCycle().setBuyCycleNextPlanDate(null);
            } else {
                Integer nextNum = deliveryNum + Constants.ONE;
                CycleDeliveryPlanDTO nextPlan = new CycleDeliveryPlanDTO();
                deliveryPlanS = deliveryPlanS.stream()
                        .sorted(Comparator.comparingInt(CycleDeliveryPlanDTO::getDeliveryNum))
                        .collect(Collectors.toList());
                for (CycleDeliveryPlanDTO plan: deliveryPlanS) {
                    if (Objects.equals(deliveryNum,plan.getDeliveryNum())) {
                        plan.setCycleDeliveryState(CycleDeliveryState.SHIPPED);
                    }
                    if (plan.getDeliveryNum() >= nextNum && CycleDeliveryState.NOT_SHIP.equals(plan.getCycleDeliveryState())) {
                        nextPlan = plan;
                        break;
                    }
                }
                trade.getTradeBuyCycle().setBuyCycleNextPlanDate(nextPlan.getDeliveryDate());
                storeMessageBizService.handleForTradeWaitDeliver(trade, true);
            }
            trade.getTradeBuyCycle().setDeliveryPlanS(deliveryPlanS);
        }
        if (allShipped) {
            tradeDeliver.setStatus(DeliverStatus.SHIPPED);
            trade.addTradeDeliver(tradeDeliver);
            trade.getTradeState().setDeliverTime(LocalDateTime.now());
            trade.getTradeState().setDeliverStatus(DeliverStatus.SHIPPED);
            trade.getTradeState().setFlowState(FlowState.DELIVERED);
            trade.appendTradeEventLog(TradeEventLog
                    .builder()
                    .operator(operator)
                    .eventType(FlowState.DELIVERED.getDescription())
                    .eventDetail(String.format("订单[%s],已全部发货,发货人:%s", trade.getId(), operator.getName()))
                    .eventTime(LocalDateTime.now())
                    .build());
        } else {
            tradeDeliver.setStatus(DeliverStatus.PART_SHIPPED);
            trade.addTradeDeliver(tradeDeliver);
            trade.getTradeState().setDeliverStatus(DeliverStatus.PART_SHIPPED);
            trade.getTradeState().setFlowState(FlowState.DELIVERED_PART);
            trade.appendTradeEventLog(TradeEventLog
                    .builder()
                    .operator(operator)
                    .eventType(FlowState.DELIVERED_PART.getDescription())
                    .eventDetail(String.format("订单[%s],已部分发货,发货人:%s", trade.getId(), operator.getName()))
                    .eventTime(LocalDateTime.now())
                    .build());
        }

        if (logger.isInfoEnabled()) {
            logger.info("订单[{}] => 发货状态[ {} ], 流程状态[ {} ]", trade.getId(), trade.getTradeState().getDeliverStatus(), trade.getTradeState().getFlowState());
        }

        save(trade);
        // 微信小程序后台自动发货
        handleWxPayUploadShippingInfo(trade, originalDeliverStatus);
        if (allShipped) {
            super.operationLogMq.convertAndSend(operator, FlowState.DELIVERED.getDescription(), trade.getTradeEventLogs().get(0).getEventDetail());
            super.operationLogMq.convertAndSend(operator, FlowState.DELIVERED.getDescription(), trade.getTradeEventLogs().get(1).getEventDetail());
        } else {
            super.operationLogMq.convertAndSend(operator, FlowState.DELIVERED.getDescription(), trade.getTradeEventLogs().get(0).getEventDetail());
        }
    }

    /**
     * 处理微信小程序后台自动发货
     * @param trade 订单信息
     * @param originalDeliverStatus 原始发货状态
     */
    private void handleWxPayUploadShippingInfo(Trade trade, DeliverStatus originalDeliverStatus) {
        String tradeId = trade.getId();
        try {
            log.info("开始处理微信小程序自动发货, 订单号: {}, 原始发货状态: {}", tradeId, originalDeliverStatus);

            // 0. 判断支付方式是否为微信支付
            if (trade.getPayWay() == null || !PayWay.WECHAT.equals(trade.getPayWay())) {
                log.info("订单支付方式不是微信支付, 订单号: {}, 支付方式: {}", tradeId, trade.getPayWay());
                return;
            }

            // 1. 检查是否已经调用过小程序自动发货接口
            boolean hasCalledShipping = false;

            // 检查当前订单是否已调用
            if (originalDeliverStatus == DeliverStatus.SHIPPED || originalDeliverStatus == DeliverStatus.PART_SHIPPED) {
                hasCalledShipping = true;
                log.info("当前订单已发货, 订单号: {}, 发货状态: {}", tradeId, originalDeliverStatus);
            }

            // 检查商家订单是否已调用
            if (!hasCalledShipping && StringUtils.isNotEmpty(trade.getParentId())) {
                List<Trade> tradeList = tradeService.detailsByParentId(trade.getParentId());
                if (CollectionUtils.isNotEmpty(tradeList)) {
                    for (Trade tradeItem : tradeList) {
                        if (trade.getId().equals(tradeItem.getId())) {
                            continue;
                        }
                        if (tradeItem.getTradeState().getDeliverStatus() == DeliverStatus.SHIPPED ||
                                tradeItem.getTradeState().getDeliverStatus() == DeliverStatus.PART_SHIPPED) {
                            hasCalledShipping = true;
                            log.info("商家订单已发货, 当前订单号: {}, 商家订单号: {}, 发货状态: {}",
                                    tradeId, tradeItem.getId(), tradeItem.getTradeState().getDeliverStatus());
                            break;
                        }
                    }
                }
            }

            // 如果已经调用过，则不再处理
            if (hasCalledShipping) {
                log.info("订单已调用过小程序自动发货接口, 不再处理, 订单号: {}", tradeId);
                return;
            }

            // 2. 处理主订单
            log.info("开始处理主订单微信支付自动发货, 订单号: {}", tradeId);
            processOrderWxPayUploadShipping(trade, tradeId, true);

            // 3. 如果是定金预售，处理尾款订单
            String tailOrderNo = trade.getTailOrderNo();
            if (StringUtils.isNotBlank(tailOrderNo) && payCallBackUtil.isTailPayOrder(tailOrderNo)) {
                log.info("开始处理尾款订单微信支付自动发货, 订单号: {}, 尾款订单号: {}", tradeId, tailOrderNo);
                processOrderWxPayUploadShipping(trade, tailOrderNo, false);
            }
        } catch (Exception e) {
            log.error("微信支付自动发货处理异常, 订单号: {}, 异常信息: ", tradeId, e);
        }
    }

    /**
     * 处理订单的微信支付自动发货
     * @param trade 订单信息
     * @param businessId 业务ID(订单ID)
     * @param needQueryParentId 是否需要根据parentId查询
     */
    private void processOrderWxPayUploadShipping(Trade trade, String businessId, boolean needQueryParentId) {
        log.info("开始处理订单微信支付回调查询, 订单号: {}, 业务ID: {}, 是否需要查询parentId: {}",
                trade.getId(), businessId, needQueryParentId);

        // 1、根据订单号调用微信小程序后台发货信息录入接口
        List<String> businessIdList = Arrays.asList(businessId);

        List<PayCallBackResult> payCallBackResults =
                wxPayUploadShippingInfoService.handleWxPayUploadShippingInfo(businessIdList);
        log.info("查询微信支付回调结果, 订单号: {}, 业务ID: {}, 查询结果数量: {}",
                trade.getId(), businessId, CollectionUtils.isEmpty(payCallBackResults) ? 0 : payCallBackResults.size());

        // 2. 订单号没有查询到微信支付回调结果，则根据parentId调用微信小程序后台发货信息录入接口
        if (needQueryParentId && CollectionUtils.isEmpty(payCallBackResults) && StringUtils.isNotEmpty(trade.getParentId())) {
            businessIdList = Arrays.asList(trade.getParentId());

            payCallBackResults =
                    wxPayUploadShippingInfoService.handleWxPayUploadShippingInfo(businessIdList);

            log.info("根据parentId查询微信支付回调结果完成, 订单号: {}, parentId: {}, 查询结果数量: {}",
                    trade.getId(), trade.getParentId(), CollectionUtils.isEmpty(payCallBackResults) ? 0 : payCallBackResults.size());
        }

        // 3. 如果仍然没有回调结果，则不处理
        if (CollectionUtils.isEmpty(payCallBackResults)) {
            log.info("未查询到微信支付回调结果, 不处理, 订单号: {}, 业务ID: {}", trade.getId(), businessId);
        }
    }

    /**
     * 处理商品（赠品、加价购单独处理）
     * @param trade
     * @param shippingItems
     * @param stringBuilder
     * @param operator
     * @return
     */
    private Boolean handleNormalShippingItems(Trade trade, List<ShippingItem> shippingItems, StringBuilder stringBuilder, Operator operator){
        AtomicBoolean flag = new AtomicBoolean(false);
        ConcurrentHashMap<String, TradeItem> skuItemMap;
        skuItemMap = trade.skuItemMap();

        //处理中的退货商品
        List<String> returnItemIds =
                tradeService.getReturnItemInProcessing(trade.getId());

        shippingItems.forEach(shippingItem -> {
            TradeItem tradeItem = skuItemMap.get(shippingItem.getSkuId());

            //1. 增加发货数量
            Long hasNum = tradeItem.getDeliveredNum();
            if (hasNum != null) {
                hasNum += shippingItem.getItemNum();
            } else {
                hasNum = shippingItem.getItemNum();
            }
            tradeItem.setDeliveredNum(hasNum);

            OrderTag orderTag = trade.getOrderTag();
            Long num = tradeItem.getNum();
            // 周期购
            if (Objects.nonNull(orderTag) && Objects.equals(orderTag.getBuyCycleFlag(),Boolean.TRUE)) {
                TradeBuyCycleDTO tradeBuyCycle = trade.getTradeBuyCycle();
                Integer deliveryCycleNum = tradeBuyCycle.getDeliveryCycleNum();
                num = num * deliveryCycleNum;
            }
            //2. 更新发货状态
            if (!returnItemIds.contains(shippingItem.getSkuId()) && hasNum.equals(num)) {
                tradeItem.setDeliverStatus(DeliverStatus.SHIPPED);
                //flag.set(true);
            } else if (hasNum > num) {  //什么鬼, 发多了？
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050019, new Object[]{tradeItem.getSkuId(), num, hasNum});
            } else {
                tradeItem.setDeliverStatus(DeliverStatus.PART_SHIPPED);
            }
            stringBuilder.append(String.format("订单%s,商品[%s], 发货数：%s, 目前状态:[%s],发货人:%s\r\n",
                    trade.getId(),
                    tradeItem.getSkuName(),
                    shippingItem.getItemNum().toString(),
                    tradeItem.getDeliverStatus().getDescription(),
                    operator.getName()));

            //3. 合并数据
            shippingItem.setItemName(tradeItem.getSkuName());
            shippingItem.setSpuId(tradeItem.getSpuId());
            shippingItem.setPic(tradeItem.getPic());
            shippingItem.setSpecDetails(tradeItem.getSpecDetails());
            shippingItem.setUnit(tradeItem.getUnit());
        });
        return flag.get();
    }

    /**
     * 处理赠品
     * @param trade
     * @param shippingItems
     * @param stringBuilder
     * @param operator
     * @return
     */
    private Boolean handleGiftShippingItems(Trade trade,
                                         List<ShippingItem> shippingItems, StringBuilder stringBuilder, Operator operator){
        AtomicBoolean flag = new AtomicBoolean(false);
        ConcurrentHashMap<Long, List<TradeItem>> marketingIdToItemMap =
                trade.giftSkuItemMap();
        //处理中的退货商品
        List<TradeService.MarketingIdToSku> marketingIdToSkuList =
                tradeService.getReturnItemInProcessing(trade.getId(), 1);
        Map<Long, List<TradeService.MarketingIdToSku>> marketingIdSkusMap =
                marketingIdToSkuList.stream().collect(Collectors.groupingBy(TradeService.MarketingIdToSku::getMarketingId));

        shippingItems.forEach(shippingItem -> {
            List<TradeItem> tradeItems = marketingIdToItemMap.get(shippingItem.getMarketingId());
            Map<String, TradeItem> skuIdToTradeItemMap =
                    tradeItems.stream().collect(Collectors.toMap(TradeItem::getSkuId,
                            Function.identity()));
            TradeItem tradeItem = skuIdToTradeItemMap.get(shippingItem.getSkuId());

            //1. 增加发货数量
            Long hasNum = tradeItem.getDeliveredNum();
            if (hasNum != null) {
                hasNum += shippingItem.getItemNum();
            } else {
                hasNum = shippingItem.getItemNum();
            }
            tradeItem.setDeliveredNum(hasNum);

            Long num = tradeItem.getNum();
            List<String> returnItemIds =
                    marketingIdSkusMap.getOrDefault(shippingItem.getMarketingId(), new ArrayList<>()).stream()
                            .map(TradeService.MarketingIdToSku::getSkuId).collect(Collectors.toList());
            //2. 更新发货状态
            if (!returnItemIds.contains(shippingItem.getSkuId()) && hasNum.equals(num)) {
                tradeItem.setDeliverStatus(DeliverStatus.SHIPPED);
                //flag.set(true);
            } else if (hasNum > num) {  //什么鬼, 发多了？
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050019, new Object[]{tradeItem.getSkuId(), num, hasNum});
            } else {
                tradeItem.setDeliverStatus(DeliverStatus.PART_SHIPPED);
            }
            stringBuilder.append(String.format("订单%s,商品[%s], 发货数：%s, 目前状态:[%s],发货人:%s\r\n",
                    trade.getId(),
                    ( "【赠品】") + tradeItem.getSkuName(),
                    shippingItem.getItemNum().toString(),
                    tradeItem.getDeliverStatus().getDescription(),
                    operator.getName()));

            //3. 合并数据
            shippingItem.setItemName(tradeItem.getSkuName());
            shippingItem.setSpuId(tradeItem.getSpuId());
            shippingItem.setPic(tradeItem.getPic());
            shippingItem.setSpecDetails(tradeItem.getSpecDetails());
            shippingItem.setUnit(tradeItem.getUnit());
        });
        return flag.get();
    }

    /**
     * @description 处理加价购商品
     * @author  edz
     * @date: 2022/11/30 17:38
     * @param trade
     * @param shippingItems
     * @param stringBuilder
     * @param operator
     * @return java.lang.Boolean
     */
    private Boolean handlePreferentialShippingItems(Trade trade,
                                                    List<ShippingItem> shippingItems, StringBuilder stringBuilder,
                                         Operator operator){
        AtomicBoolean flag = new AtomicBoolean(false);
        ConcurrentHashMap<Long, List<TradeItem>> marketingIdToItemMap = trade.preferentialItemMap();

        //处理中的退货商品
        List<TradeService.MarketingIdToSku> marketingIdToSkuList =
                tradeService.getReturnItemInProcessing(trade.getId(), 2);
        Map<Long, List<TradeService.MarketingIdToSku>> marketingIdSkusMap =
                marketingIdToSkuList.stream().collect(Collectors.groupingBy(TradeService.MarketingIdToSku::getMarketingId));

        shippingItems.forEach(shippingItem -> {
            List<TradeItem> tradeItems = marketingIdToItemMap.get(shippingItem.getMarketingId());
            Map<String, TradeItem> skuIdToTradeItemMap =
                    tradeItems.stream().collect(Collectors.toMap(TradeItem::getSkuId,
                    Function.identity()));
            TradeItem tradeItem = skuIdToTradeItemMap.get(shippingItem.getSkuId());

            //1. 增加发货数量
            Long hasNum = tradeItem.getDeliveredNum();
            if (hasNum != null) {
                hasNum += shippingItem.getItemNum();
            } else {
                hasNum = shippingItem.getItemNum();
            }
            tradeItem.setDeliveredNum(hasNum);

            Long num = tradeItem.getNum();
           List<String> returnItemIds =
                   marketingIdSkusMap.getOrDefault(shippingItem.getMarketingId(), new ArrayList<>()).stream()
                           .map(TradeService.MarketingIdToSku::getSkuId).collect(Collectors.toList());
            //2. 更新发货状态
            if (!returnItemIds.contains(shippingItem.getSkuId()) && hasNum.equals(num)) {
                tradeItem.setDeliverStatus(DeliverStatus.SHIPPED);
                //flag.set(true);
            } else if (hasNum > num) {  //什么鬼, 发多了？
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050019, new Object[]{tradeItem.getSkuId(), num, hasNum});
            } else {
                tradeItem.setDeliverStatus(DeliverStatus.PART_SHIPPED);
            }
            stringBuilder.append(String.format("订单%s,商品[%s], 发货数：%s, 目前状态:[%s],发货人:%s\r\n",
                    trade.getId(),
                    ( "【加价购商品】") + tradeItem.getSkuName(),
                    shippingItem.getItemNum().toString(),
                    tradeItem.getDeliverStatus().getDescription(),
                    operator.getName()));

            //3. 合并数据
            shippingItem.setItemName(tradeItem.getSkuName());
            shippingItem.setSpuId(tradeItem.getSpuId());
            shippingItem.setPic(tradeItem.getPic());
            shippingItem.setSpecDetails(tradeItem.getSpecDetails());
            shippingItem.setUnit(tradeItem.getUnit());
        });
        return flag.get();
    }
}
