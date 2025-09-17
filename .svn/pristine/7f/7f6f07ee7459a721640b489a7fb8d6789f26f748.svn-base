package com.wanmi.sbc.order.trade.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.order.api.request.trade.TradeBatchDeliverRequest;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.LogisticsDTO;
import com.wanmi.sbc.order.bean.dto.TradeBatchDeliverDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.ShipperType;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.trade.model.entity.TradeDeliver;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Logistics;
import com.wanmi.sbc.order.trade.model.entity.value.ShippingItem;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.request.TradeWrapperListRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单优化版本
 *
 * @author wanggang
 */
@Slf4j
@Service
public class TradeOptimizeService {

    @Autowired private TradeCustomerService tradeCustomerService;

    @Autowired private VerifyService verifyService;

    @Autowired private TradeService tradeService;

    @Autowired private ProviderTradeService providerTradeService;

    @Autowired private GeneratorService generatorService;


    @Autowired
    private TradeCommitIncision tradeCommitIncision;

    @Autowired TradeCacheService tradeCacheService;


    /** C端下单 */
    public List<TradeCommitResult> commitFlashSale(TradeCommitRequest tradeCommitRequest) {
        // 验证用户
        CustomerSimplifyOrderCommitVO customer =
                verifyService.simplifyById(tradeCommitRequest.getOperator().getUserId());
        tradeCommitRequest.setCustomer(customer);
        Operator operator = tradeCommitRequest.getOperator();
        List<TradeItemGroup> tradeItemGroups =
                KsBeanUtil.convert(tradeCommitRequest.getTradeItemGroups(), TradeItemGroup.class);
        Map<Long, TradeItemGroup> tradeItemGroupsMap =
                tradeItemGroups.stream()
                        .collect(
                                Collectors.toMap(
                                        g -> g.getSupplier().getStoreId(), Function.identity()));
        // 从入参组装店铺信息
//        List<StoreVO> storeVOList = Lists.newArrayList();
//        StoreVO storeVO = new StoreVO();
//        storeVO.setStoreId(tradeItemGroups.get(0).getSupplier().getStoreId());
//        storeVO.setFreightTemplateType(
//                tradeItemGroups.get(0).getSupplier().getFreightTemplateType());
//        storeVOList.add(storeVO);

        List<Long> paramIds = new ArrayList<>();
        paramIds.add(tradeItemGroups.get(0).getSupplier().getStoreId());
        List<StoreVO> storeVOS = tradeCacheService.queryStoreList(paramIds);
        if (CollectionUtils.isEmpty(storeVOS) || storeVOS.size() != paramIds.size()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010105);
        }
        LocalDateTime now = LocalDateTime.now();
        storeVOS.forEach(
                store -> {
                    if (!store.getAuditState().equals(CheckState.CHECKED)) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010107);
                    }
                    if (StoreState.CLOSED.toValue() == store.getStoreState().toValue()) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010109);
                    }
                    if (store.getContractStartDate().isAfter(now)
                            || store.getContractEndDate().isBefore(now)) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010110);
                    }
                    store.setFreightTemplateType(tradeItemGroups.get(0).getSupplier().getFreightTemplateType());
                });

        // 查询店铺等级
        Map<Long, CommonLevelVO> storeLevelMap =
                tradeCustomerService.listCustomerLevelMapByCustomerIdAndIds(
                        new ArrayList<>(tradeItemGroupsMap.keySet()), customer.getCustomerId());
        // 按店铺包装多个订单信息、订单组信息
        TradeWrapperListRequest tradeWrapperListRequest = new TradeWrapperListRequest();
        tradeWrapperListRequest.setStoreLevelMap(storeLevelMap);
        tradeWrapperListRequest.setStoreVOList(storeVOS);
        tradeWrapperListRequest.setTradeCommitRequest(tradeCommitRequest);
        tradeWrapperListRequest.setTradeItemGroups(tradeItemGroups);
        List<Trade> trades = tradeService.wrapperTradeList(tradeWrapperListRequest,tradeCommitRequest.getIsOpen());

        // 处理每个点单的礼品卡
        if(CollectionUtils.isNotEmpty(tradeCommitRequest.getGiftCardTradeCommitVOList())) {
            trades = tradeService.giftCardProcess(trades, tradeCommitRequest);
        }

        // 计算总支付金额
        tradeCommitIncision.calcTotalPrice(trades);
        //处理电子口岸信息 验证收货人身份认证购买人身份证 0元订单等
        tradeCommitIncision.checkTradeData(trades, tradeCommitRequest);

        return tradeService.createFlashTrade(trades.get(0),  operator, tradeCommitRequest);
    }

    /** 批量发货 */
    @Transactional
    public void batchDeliver(TradeBatchDeliverRequest batchDeliverRequest) {
        Operator operator = batchDeliverRequest.getOperator();
        List<TradeBatchDeliverDTO> batchDeliverDTOList =
                batchDeliverRequest.getBatchDeliverDTOList();
        batchDeliverDTOList.forEach(
                batchDeliverDTO -> {
                    String tid = batchDeliverDTO.getTid();
                    TradeDeliver tradeDeliver = new TradeDeliver();
                    if (Platform.SUPPLIER == operator.getPlatform()  || Platform.STOREFRONT == operator.getPlatform()) {
                        Long storeId = Long.valueOf(operator.getStoreId());
                        Trade trade = tradeService.detail(tid);
                        this.wrapTradeDeliverInfo(trade, tradeDeliver, batchDeliverDTO);

                        // 主单有着下属子单，则tradeDeliver部分信息进行重置，增加子单发货步骤
                        List<ProviderTrade> providerTradeList =
                                providerTradeService.findListByParentId(tid);
                        Optional<ProviderTrade> providerTrade =
                                providerTradeList.stream()
                                        .filter(v -> v.getSupplier().getStoreId().equals(storeId))
                                        .findFirst();
                        providerTrade.ifPresent(
                                v -> {
                                    String deliverId =
                                            providerTradeService.dealBatchDeliver(
                                                    v, tradeDeliver, operator);
                                    tradeDeliver.setSunDeliverId(deliverId);
                                });
                        tradeService.deliver(tid, tradeDeliver, operator, BoolFlag.YES,null);
                    } else {
                        // 供应商子单发货
                        ProviderTrade providerTrade = providerTradeService.providerDetail(tid);
                        tradeDeliver.setDeliverId(generatorService.generate("TD"));
                        tradeDeliver.setLogistics(
                                KsBeanUtil.copyPropertiesThird(
                                        batchDeliverDTO.getLogistics(), Logistics.class));
                        tradeDeliver.setDeliverTime(batchDeliverDTO.getDeliverTime());
                        tradeDeliver.setStatus(DeliverStatus.SHIPPED);
                        tradeDeliver.setShipperType(ShipperType.PROVIDER);
                        tradeDeliver.setProviderName(providerTrade.getSupplier().getSupplierName());
                        String deliverId =
                                providerTradeService.dealBatchDeliver(
                                        providerTrade, tradeDeliver, operator);

                        tradeDeliver.setSunDeliverId(deliverId);
                        tradeDeliver.setTradeId(providerTrade.getParentId());
                        tradeDeliver.setShipperType(ShipperType.SUPPLIER);
                        try {
                            tradeService.deliver(
                                    providerTrade.getParentId(),
                                    tradeDeliver,
                                    operator,
                                    BoolFlag.YES,null);
                        } catch (SbcRuntimeException e) {
                            log.error("批量发货失败>>>>>子单号：{}>>>>>错误信息：{}", tid, e);
                            if (OrderErrorCodeEnum.K050024.getCode().equals(e.getErrorCode())) {
                                throw new SbcRuntimeException(OrderErrorCodeEnum.K050024, new Object[] {tid});
                            } else {
                                throw e;
                            }
                        } catch (Exception e) {
                            log.error("批量发货失败>>>>>子单号：{}>>>>>错误信息：{}", tid, e);
                            throw e;
                        }
                    }
                });
    }

    /** 组装物发货单信息 */
    public void wrapTradeDeliverInfo(
            Trade trade, TradeDeliver tradeDeliver, TradeBatchDeliverDTO batchDeliverDTO) {
        List<TradeItem> tradeItems = trade.getTradeItems();
        AtomicReference<Boolean> isAllDelivered = new AtomicReference<>(Boolean.TRUE);
        //商品
        Map<String, Integer> returnItemMap = tradeService.getReturnItemNum(trade.getId(), Boolean.FALSE);
        Map<String, Integer> returnEndItemMap = tradeService.getReturnEndItemNum(trade.getId(), Boolean.FALSE);
        //赠品
        Map<Long, Map<String, Integer>> returnGiftsMap =
                tradeService.getGiftReturnItemNum(trade.getId());
        Map<Long, Map<String, Integer>> returnEndGiftsMap =
                tradeService.getGiftReturnEndItemNum(trade.getId());
        //加价购
        Map<Long, Map<String, Integer>> returnPreferentialMap =
                tradeService.getPreferentialReturnItemNum(trade.getId());
        Map<Long, Map<String, Integer>> returnEndPreferentialMap = tradeService.getReturnEndItemNum(trade.getId());
        List<ShippingItem> shippingItems =
                tradeItems.stream()
                        .map(
                                item -> {
                                    this.dealShippingItemForBatchDeliver(item, returnItemMap, returnEndItemMap, isAllDelivered);
                                    ShippingItem shippingItem =
                                            KsBeanUtil.copyPropertiesThird(
                                                    item, ShippingItem.class);
                                    shippingItem.setItemName(item.getSkuName());
                                    shippingItem.setItemNum(item.getDeliveredNum());
                                    return shippingItem;
                                })
                        .collect(Collectors.toList());
        List<ShippingItem> giftItems =
                trade.getGifts().stream()
                        .map(
                                item -> {

                                    this.dealShippingItemForBatchDeliver(item,
                                            returnGiftsMap.get(item.getMarketingIds().get(0)),
                                            returnEndGiftsMap.get(item.getMarketingIds().get(0)),
                                            isAllDelivered);
                                    ShippingItem shippingItem =
                                            KsBeanUtil.copyPropertiesThird(
                                                    item, ShippingItem.class);
                                    shippingItem.setItemName(item.getSkuName());
                                    shippingItem.setItemNum(item.getDeliveredNum());
                                    return shippingItem;
                                })
                        .collect(Collectors.toList());
        List<ShippingItem> preferentialItems =
                trade.getPreferential().stream()
                        .map(
                                item -> {
                                    Map<String, Integer> returnMap = returnPreferentialMap.get(item.getMarketingIds().get(0));
                                    Map<String, Integer> returnEndMap = returnEndPreferentialMap.get(item.getMarketingIds().get(0));
                                    this.dealShippingItemForBatchDeliver(item, returnMap, returnEndMap, isAllDelivered);
                                    ShippingItem shippingItem =
                                            KsBeanUtil.copyPropertiesThird(
                                                    item, ShippingItem.class);
                                    shippingItem.setItemName(item.getSkuName());
                                    shippingItem.setItemNum(item.getDeliveredNum());
                                    shippingItem.setMarketingId(item.getMarketingIds().get(0));
                                    return shippingItem;
                                })
                        .collect(Collectors.toList());
        // 组装发货单
        tradeDeliver.setTradeId(trade.getId());
        tradeDeliver.setDeliverId(generatorService.generate("TD"));
        tradeDeliver.setLogistics(
                KsBeanUtil.copyPropertiesThird(batchDeliverDTO.getLogistics(), Logistics.class));
        tradeDeliver.setShippingItems(shippingItems);
        tradeDeliver.setShipperType(ShipperType.SUPPLIER);
        tradeDeliver.setDeliverTime(batchDeliverDTO.getDeliverTime());
        tradeDeliver.setConsignee(trade.getConsignee());
        tradeDeliver.setGiftItemList(giftItems);
        tradeDeliver.setPreferentialItemList(preferentialItems);
        tradeDeliver.setStatus(isAllDelivered.get() ? DeliverStatus.SHIPPED : DeliverStatus.PART_SHIPPED);
        tradeDeliver.setProviderName(trade.getSupplier().getSupplierName());
    }

    /**
     * 批量发货设置发货数量和发货状态
     * @param item 当前设置的商品
     * @param returnItemMap 售后处理中和完成的商品数量
     * @param returnEndItemMap 售后完成的商品数量
     * @param isAllDelivered 是否全部发货标识
     */
    public void dealShippingItemForBatchDeliver(TradeItem item, Map<String, Integer> returnItemMap, Map<String, Integer> returnEndItemMap, AtomicReference<Boolean> isAllDelivered) {
        //售后商品数量(处理中、完成)
        Integer returnNum = null;
        if (MapUtils.isNotEmpty(returnItemMap)){
            returnNum = returnItemMap.get(item.getSkuId());
        }
        //售后完成商品数量
        Integer returnEndNum = null;
        if (MapUtils.isNotEmpty(returnEndItemMap)){
            returnEndNum = returnEndItemMap.get(item.getSkuId());
        }
        //扣除售后商品数
        if (Objects.isNull(returnNum)) {
            returnNum = NumberUtils.INTEGER_ZERO;
        }
        item.setDeliveredNum(item.getNum() - returnNum);
        //如果售后商品都完成，设置商品已发货完
        if (returnNum == 0 || returnNum > 0 && returnNum.equals(returnEndNum)) {
            item.setDeliverStatus(DeliverStatus.SHIPPED);
        } else {
            item.setDeliverStatus(DeliverStatus.PART_SHIPPED);
            isAllDelivered.set(Boolean.FALSE);
        }
    }

    /** 根据物流单号，物流公司判断是否重复 */
    public List<String> verifyLogisticNo(List<LogisticsDTO> logisticsDTOList) {
        Map<String, String> logisticsMap =
                logisticsDTOList.stream()
                        .collect(
                                Collectors.toMap(
                                        LogisticsDTO::getLogisticNo,
                                        LogisticsDTO::getLogisticStandardCode));

        List<String> logisticNoList =
                logisticsDTOList.stream()
                        .map(LogisticsDTO::getLogisticNo)
                        .collect(Collectors.toList());
        List<Trade> trades =
                tradeService.queryAll(
                        TradeQueryRequest.builder().logisticNos(logisticNoList).build());

        List<TradeDeliver> tradeDelivers = new ArrayList<>();
        trades.forEach(v -> tradeDelivers.addAll(v.getTradeDelivers()));

        return tradeDelivers.stream()
                .filter(
                        v ->
                                logisticsMap.containsKey(v.getLogistics().getLogisticNo())
                                        && Objects.equals(
                                                v.getLogistics().getLogisticStandardCode(),
                                                logisticsMap.get(v.getLogistics().getLogisticNo())))
                .map(v -> v.getLogistics().getLogisticNo())
                .collect(Collectors.toList());
    }
}
