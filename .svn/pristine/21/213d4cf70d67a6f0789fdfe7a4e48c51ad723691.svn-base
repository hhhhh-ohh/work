package com.wanmi.sbc.order.message;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.response.finance.record.LakalaSettlementAddResponse;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.enums.storemessage.ProviderMessageNode;
import com.wanmi.sbc.common.enums.storemessage.SupplierMessageNode;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MutableMap;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderRefundRequest;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.enums.AuditState;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.setting.api.provider.stockwarning.StockWarningProvider;
import com.wanmi.sbc.setting.api.provider.stockwarning.StockWarningQueryProvider;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningAddRequest;
import com.wanmi.sbc.setting.api.request.stockWarning.StockWarningByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByStoreIdRequest;
import com.wanmi.sbc.setting.api.response.stockwarning.StockWarningByIdResponse;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description goods，商家消息具体发送业务服务，统一管理
 * @author malianfeng
 * @date 2022/7/12 14:59
 */
@Slf4j
@Service
public class StoreMessageBizService {

    @Autowired private OrderProducerService orderProducerService;

    @Autowired private ProviderTradeService providerTradeService;

    @Autowired private StoreMessageService storeMessageService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StockWarningQueryProvider stockWarningQueryProvider;

    @Autowired
    private StoreMessageNodeSettingQueryProvider storeMessageNodeSettingQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private StockWarningProvider stockWarningProvider;


    /**
     * 平台退款失败提醒
     * @param refundRequest 退款请求
     */
    public void handleForRefundFailed(RefundOrderRefundRequest refundRequest) {
        try {
            // 封装发送请求
            StoreMessageMQRequest request = new StoreMessageMQRequest();
            request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
            request.setNodeCode(BossMessageNode.REFUND_FAIL.getCode());
            request.setProduceTime(LocalDateTime.now());
            request.setContentParams(Lists.newArrayList(refundRequest.getRid()));
            request.setRouteParams(MutableMap.of("rid", refundRequest.getRid()));
            orderProducerService.sendStoreMessage(request);
        } catch (Exception e) {
            log.error("平台退款失败提醒，消息处理失败，{}", refundRequest, e);
        }
    }

    /**
     * 待审核订单提醒
     * @param trade 订单
     */
    public void handleForTradeWaitAudit(Trade trade) {
        try {
            Supplier supplier = trade.getSupplier();
            AuditState auditState = trade.getTradeState().getAuditState();
            if (AuditState.NON_CHECKED == auditState) {
                if (StoreType.SUPPLIER == supplier.getStoreType()) {
                    // 封装发送请求
                    StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
                    mqRequest.setStoreId(supplier.getStoreId());
                    mqRequest.setNodeCode(SupplierMessageNode.TRADE_WAIT_AUDIT.getCode());
                    mqRequest.setProduceTime(LocalDateTime.now());
                    mqRequest.setContentParams(Lists.newArrayList(trade.getId()));
                    mqRequest.setRouteParams(MutableMap.of("tid", trade.getId()));
                    orderProducerService.sendStoreMessage(mqRequest);
                }
            }
        } catch (Exception e) {
            log.error("待审核订单提醒，消息处理失败，{}", trade, e);
        }
    }

    /**
     * 待发货订单提醒，专门处理商家审核通过后，需要发货的场景
     * @param trade 订单
     * @param auditState 审核状态
     */
    public void handleForAuditTradeWaitDeliver(Trade trade, AuditState auditState) {
        try {
            if (AuditState.CHECKED == auditState) {
                // 是否是虚拟订单或者卡券订单
                OrderTag orderTag = trade.getOrderTag();
                boolean isVirtual = Objects.nonNull(orderTag) && (orderTag.getVirtualFlag() || orderTag.getElectronicCouponFlag());
                // 是否是视频号订单
                boolean isWechat = SellPlatformType.WECHAT_VIDEO == trade.getSellPlatformType();
                if (PaymentOrder.NO_LIMIT == trade.getPaymentOrder() && !isVirtual && !isWechat) {
                    // 处理供应商子单发送
                    this.sendToProviderForTradeWaitDeliver(trade);
                }
            }
        } catch (Exception e) {
            log.error("待发货订单提醒，消息处理失败，{} {}", trade, auditState, e);
        }
    }

    /**
     * 处理供应商子单收货消息，并返回商家是否处理标识
     * @param trade 订单
     * @return
     */
    private boolean sendToProviderForTradeWaitDeliver(Trade trade) {
        // 主订单商家id
        Long supplierStoreId = trade.getSupplier().getStoreId();
        // 商家是否处理标识
        boolean handleSupplierFlag = false;
        List<ProviderTrade> providerTrades = providerTradeService.findListByParentId(trade.getId());
        // 筛选出非已发货的
        providerTrades = providerTrades.stream()
                .filter(item -> DeliverStatus.SHIPPED != item.getTradeState().getDeliverStatus()).collect(Collectors.toList());
        for (ProviderTrade providerTrade : providerTrades) {
            Supplier provider = providerTrade.getSupplier();
            if (Objects.equals(provider.getStoreId(), supplierStoreId)) {
                // 这个循环里只处理供应的消息，所以把商家自己的单排掉，只给一个标识，由下面的代码处理
                handleSupplierFlag = true;
                continue;
            }
            // 封装发送请求
            StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
            mqRequest.setStoreId(provider.getStoreId());
            mqRequest.setNodeCode(ProviderMessageNode.TRADE_WAIT_DELIVER.getCode());
            OrderTag orderTag = trade.getOrderTag();
            //周期购
            if (Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag()) {
                TradeBuyCycleDTO tradeBuyCycle = trade.getTradeBuyCycle();
                Integer remindShipping = tradeBuyCycle.getRemindShipping();
                if (Objects.isNull(remindShipping)) {
                    mqRequest.setProduceTime(LocalDateTime.now());
                } else {
                    LocalDate buyCycleNextPlanDate = tradeBuyCycle.getBuyCycleNextPlanDate();
                    LocalDateTime localDateTime = buyCycleNextPlanDate.minusDays(remindShipping).atStartOfDay();
                    mqRequest.setProduceTime(localDateTime);
                }
            } else {
                mqRequest.setProduceTime(LocalDateTime.now());
            }
            mqRequest.setContentParams(Lists.newArrayList(providerTrade.getId()));
            mqRequest.setRouteParams(MutableMap.of("tid", providerTrade.getId()));
            orderProducerService.sendStoreMessage(mqRequest);
        }
        return handleSupplierFlag;
    }

    /**
     * 待发货订单提醒
     * @param trade 订单
     * @param hasPaid 是否已支付
     */
    public void handleForTradeWaitDeliver(Trade trade, boolean hasPaid) {
        // 这里处理2种待发货的场景：
        // 1. 下单后，未支付，已审核，支付顺序不限时，商家/供应商的待发货
        // 2. 用户支付成功后，商家/供应商的待发货
        try {
            // 是否存在供应商子单
            boolean hasProviderTrade = trade.getTradeItems().stream().map(TradeItem::getProviderId).anyMatch(Objects::nonNull);
            boolean deliverFlag = false;
            if (hasPaid) {
                // 已支付，非已发货的订单才需要发通知
                if (DeliverStatus.SHIPPED != trade.getTradeState().getDeliverStatus()) {
                    // 更改发货标识
                    deliverFlag = true;
                }
            } else {
                // 订单已审核 && 支付顺序不限 && 非虚拟商品订单 && 非视频号订单
                AuditState auditState = trade.getTradeState().getAuditState();
                if (AuditState.CHECKED == auditState) {
                    // 是否是虚拟订单或者卡券订单
                    OrderTag orderTag = trade.getOrderTag();
                    boolean isVirtual = Objects.nonNull(orderTag) && (orderTag.getVirtualFlag() || orderTag.getElectronicCouponFlag());
                    // 是否是视频号订单
                    boolean isWechat = SellPlatformType.WECHAT_VIDEO == trade.getSellPlatformType();
                    if (PaymentOrder.NO_LIMIT == trade.getPaymentOrder() && !isVirtual && !isWechat) {
                        // 更改发货标识
                        deliverFlag = true;
                    }
                }
            }
            // 处理待发货提醒，这里分两种情况
            // 1. 有供应商子单，需要同时处理商家和供应商
            // 2. 没有供应商子单，只处理商家即可
            if (deliverFlag) {
                // 主订单商家id
                Long supplierStoreId = trade.getSupplier().getStoreId();
                // 商家是否处理标识
                boolean handleSupplierFlag;
                // 处理供应商
                if (hasProviderTrade) {
                    handleSupplierFlag = this.sendToProviderForTradeWaitDeliver(trade);
                } else {
                    handleSupplierFlag = true;
                }
                // 处理商家
                if (handleSupplierFlag) {
                    // 封装发送请求
                    StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
                    mqRequest.setStoreId(supplierStoreId);
                    mqRequest.setNodeCode(SupplierMessageNode.TRADE_WAIT_DELIVER.getCode());
                    OrderTag orderTag = trade.getOrderTag();
                    //周期购处理
                    if (Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag()) {
                        TradeBuyCycleDTO tradeBuyCycle = trade.getTradeBuyCycle();
                        Integer remindShipping = tradeBuyCycle.getRemindShipping();
                        if (Objects.isNull(remindShipping)) {
                            mqRequest.setProduceTime(LocalDateTime.now());
                        } else {
                            LocalDate buyCycleNextPlanDate = tradeBuyCycle.getBuyCycleNextPlanDate();
                            LocalDateTime localDateTime = buyCycleNextPlanDate.minusDays(remindShipping).atStartOfDay();
                            mqRequest.setProduceTime(localDateTime);
                        }
                    } else {
                        mqRequest.setProduceTime(LocalDateTime.now());
                    }
                    mqRequest.setContentParams(Lists.newArrayList(trade.getId()));
                    mqRequest.setRouteParams(MutableMap.of("tid", trade.getId()));
                    orderProducerService.sendStoreMessage(mqRequest);
                }
            }
        } catch (Exception e) {
            log.error("待发货订单提醒，消息处理失败，{}", trade, e);
        }
    }

    /**
     * 商家待审核退单提醒
     */
    public void handleForReturnOrderAudit(ReturnOrder returnOrder) {
        try {
            if (ReturnFlowState.INIT == returnOrder.getReturnFlowState() && Platform.CUSTOMER == returnOrder.getPlatform()) {
                // 仅处理用户发起的退单，不处理 代客退单
                StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
                mqRequest.setProduceTime(LocalDateTime.now());
                mqRequest.setContentParams(Lists.newArrayList(returnOrder.getId()));
                mqRequest.setRouteParams(MutableMap.of("rid", returnOrder.getId()));
                // 处理商家
                mqRequest.setStoreId(returnOrder.getCompany().getStoreId());
                mqRequest.setNodeCode(SupplierMessageNode.RETURN_ORDER_WAIT_AUDIT.getCode());
                orderProducerService.sendStoreMessage(mqRequest);
            }
        } catch (Exception e) {
            log.error("商家待审核退单提醒, 消息处理失败，{}", returnOrder, e);
        }
    }

    /**
     * 商家退单库存预警提醒
     */
    public void handleForReturnOrderStock(ReturnOrder returnOrder) {
        try {
            returnOrder.getReturnItems().forEach(item -> {
                String skuNo = item.getSkuNo();
                String skuName = item.getSkuName();
                String skuId = item.getSkuId();
//                Long storeId = item.getStoreId();
                Long storeId = returnOrder.getCompany().getStoreId();
                String nodeCode = SupplierMessageNode.GOODS_SKU_WARN_STOCK.getCode();
                // 处理商家的
                this.commonSendWaringStock(skuId, storeId, skuNo, skuName, nodeCode);
                if (StoreType.PROVIDER.equals(returnOrder.getCompany().getStoreType())) {
                    // 处理供应商的
                    skuId = item.getSkuId();
                    storeId = item.getProviderId();
                    this.commonSendWaringStock(skuId, storeId, skuNo, skuName, nodeCode);
                }
            });
        } catch (Exception e) {
            log.error("商家库存预警消息提醒,消息处理失败,{}",returnOrder,e);
        }
    }

    public void commonSendWaringStock(String skuId, Long storeId, String skuNo, String skuName, String nodeCode) {
        // 1. 查预警记录表，判断此商品是否已预警过
        StockWarningByIdResponse response = stockWarningQueryProvider
                .findIsWarning(StockWarningByIdRequest.builder().storeId(storeId).skuId(skuId).build()).getContext();
        boolean hasWaring = Objects.equals(BoolFlag.YES, response.getIsWarning());
        // 2. 已预警则不处理
        if (hasWaring) {
            return;
        }
        // 3. 待预警，需要判断需要达到预警阈值
        // 3.1 获取当前商家配置的预警值
        Long warningStock = null;
        StoreMessageNodeSettingVO storeMessageNodeSettingVO = storeMessageNodeSettingQueryProvider
                .getWarningStock(StoreMessageNodeSettingByStoreIdRequest.builder().storeId(storeId).build()).getContext().getStoreMessageNodeSettingVO();
        if (Objects.nonNull(storeMessageNodeSettingVO)) {
            warningStock = storeMessageNodeSettingVO.getWarningStock();
        }
        // 若未配置预警值，直接返回
        if (Objects.isNull(warningStock)) {
            return;
        }
        // 3.2 获取redis中的商品库存
        Map<String, Long> stockRedisMap = goodsInfoQueryProvider
                .getStockByGoodsInfoIds(
                        GoodsInfoListByIdsRequest.builder()
                                .goodsInfoIds(Collections.singletonList(skuId))
                                .build())
                .getContext();
        Long currentStock;
        if (MapUtils.isEmpty(stockRedisMap) || Objects.isNull(currentStock = stockRedisMap.get(skuId))) {
            return;
        }
        if (currentStock < warningStock) {
            // 3.3 否达到阈值，发送消息
            storeMessageService.convertAndSend(
                    storeId,
                    nodeCode,
                    Lists.newArrayList(skuName, skuNo, warningStock),
                    MutableMap.of("skuId", skuId)
            );
            // 3.4 新增商家预警记录
            if (Objects.equals(BoolFlag.YES, storeMessageNodeSettingVO.getStatus())){
                stockWarningProvider.add(StockWarningAddRequest.builder()
                        .skuId(skuId)
                        .isWarning(Constants.ONE)
                        .storeId(storeId)
                        .delFlag(DeleteFlag.NO)
                        .createTime(LocalDateTime.now())
                        .build());
            }
        }
    }

    /**
     * 商家下单库存预警消息提醒
     */
    public void handleForTradeWaitStock (Trade trade) {
        try {
            // [skuId, TradeItem]
            trade.getTradeItems().forEach(item -> {
                String skuNo = item.getSkuNo();
                String skuName = item.getSkuName();
                String skuId = item.getSkuId();
                Long storeId = item.getStoreId();
                String nodeCode = SupplierMessageNode.GOODS_SKU_WARN_STOCK.getCode();
                // 处理商家的
                this.commonSendWaringStock(skuId, storeId, skuNo, skuName, nodeCode);
                if (Objects.nonNull(item.getProviderSkuId())) {
                    // 处理供应商的
                    skuId = item.getProviderSkuId();
                    storeId = item.getProviderId();
                    this.commonSendWaringStock(skuId, storeId, skuNo, skuName, nodeCode);
                }
            });
        } catch (Exception e) {
            log.error("商家库存预警消息提醒,消息处理失败,{}",trade,e);
        }
    }

    /**
     * 平台/商家/供应商拉卡拉待结算单生成提醒
     */
    public void handleForLakalaBossSettlementProduce(String nodeCode, List<LakalaSettlementAddResponse> settlementViewList) {
        try {
            for (LakalaSettlementAddResponse settlementViewVO : settlementViewList) {
                if (StoreType.SUPPLIER.equals(settlementViewVO.getStoreType())) {
                    String settlementCode = String.format("JS%07d", settlementViewVO.getSettleId());
                    storeMessageService.convertAndSend(
                            Constants.BOSS_DEFAULT_STORE_ID,
                            nodeCode,
                            Lists.newArrayList(settlementCode),
                            MutableMap.of(
                                    "settleId", settlementViewVO.getSettleId(),
                                    "settleUuid", settlementViewVO.getSettleUuid()
                            )
                    );
                }
            }
        } catch (Exception e) {
            log.error("商家/供应商拉卡拉待结算单生成提醒, 消息处理失败，{} {}", nodeCode, settlementViewList, e);
        }
    }

    /**
     * 商家/供应商拉卡拉待结算单生成提醒
     */
    public void handleForLakalaSupplierSettlementProduce(List<LakalaSettlementAddResponse> settlementViewList) {
        try {
            // 结算单中的storeId集合
            List<Long> storeIds = settlementViewList.stream().map(LakalaSettlementAddResponse::getStoreId).distinct().collect(Collectors.toList());
            // 根据storeIds查询storeList
            List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext().getStoreVOList();
            // 判断出店铺是商家店铺还是供应商店铺
            List<Long> supplierStoreIds = new ArrayList<>();
            List<Long> providerStoreIds = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(storeVOList)){
                // 拉卡拉商家结算店铺ID
                supplierStoreIds = storeVOList.stream().filter(storeVO -> storeVO.getStoreType().equals(StoreType.SUPPLIER)).map(StoreVO::getStoreId).collect(Collectors.toList());
                // 拉卡拉供应商结算店铺ID
                providerStoreIds = storeVOList.stream().filter(storeVO -> storeVO.getStoreType().equals(StoreType.PROVIDER)).map(StoreVO::getStoreId).collect(Collectors.toList());
            }
            for (LakalaSettlementAddResponse settlementViewVO : settlementViewList) {
                String settlementCode = String.format("JS%07d", settlementViewVO.getSettleId());
                if (CollectionUtils.isNotEmpty(supplierStoreIds) && supplierStoreIds.contains(settlementViewVO.getStoreId())) {
                    storeMessageService.convertAndSend(
                        settlementViewVO.getStoreId(),
                        SupplierMessageNode.LAKALA_SETTLEMENT_SETTLE.getCode(),
                        Lists.newArrayList(settlementCode),
                        MutableMap.of(
                        "settleId", settlementViewVO.getSettleId(),
                        "settleUuid", settlementViewVO.getSettleUuid()
                        )
                    );
                } else if (CollectionUtils.isNotEmpty(providerStoreIds) && providerStoreIds.contains(settlementViewVO.getStoreId())) {
                    storeMessageService.convertAndSend(
                        settlementViewVO.getStoreId(),
                        ProviderMessageNode.LAKALA_SETTLEMENT_SETTLE.getCode(),
                        Lists.newArrayList(settlementCode),
                        MutableMap.of(
                        "settleId", settlementViewVO.getSettleId(),
                        "settleUuid", settlementViewVO.getSettleUuid()
                        )
                    );
                }
            }
        } catch (Exception e) {
            log.error("商家/供应商拉卡拉待结算单生成提醒, 消息处理失败，{}", settlementViewList, e);
        }
    }


    /**
     * 买家修改收货信息变更提醒
     * @param trade 订单
     */
    public void handleForModifyConsigneeByBuyer(Trade trade) {
        try {
            // 1. 处理商家的订单
            Long supplierStoreId = trade.getSupplier().getStoreId();
            StoreMessageMQRequest supplierMqRequest = new StoreMessageMQRequest();
            supplierMqRequest.setStoreId(supplierStoreId);
            supplierMqRequest.setNodeCode(SupplierMessageNode.TRADE_BUYER_MODIFY_CONSIGNEE.getCode());
            supplierMqRequest.setProduceTime(LocalDateTime.now());
            supplierMqRequest.setContentParams(Lists.newArrayList(trade.getId()));
            supplierMqRequest.setRouteParams(MutableMap.of("tid", trade.getId()));
            orderProducerService.sendStoreMessage(supplierMqRequest);

            // 2. 处理供应商子单
            boolean hasProviderTrade = trade.getTradeItems().stream().map(TradeItem::getProviderId).anyMatch(Objects::nonNull);
            AuditState auditState = trade.getTradeState().getAuditState();
            PayState payState = trade.getTradeState().getPayState();
            PaymentOrder paymentOrder = trade.getPaymentOrder();
            // 需发送消息标识，订单已审核 && (订单支付顺序不限 || (先款后货 && 订单已支付))
            boolean needSend = auditState == AuditState.CHECKED
                    && (paymentOrder == PaymentOrder.NO_LIMIT || (paymentOrder == PaymentOrder.PAY_FIRST && payState == PayState.PAID));
            if (hasProviderTrade && needSend) {
                List<ProviderTrade> providerTrades = providerTradeService.findListByParentId(trade.getId());
                for (ProviderTrade providerTrade : providerTrades) {
                    Supplier provider = providerTrade.getSupplier();
                    // 这个循环里只处理供应的消息，所以把商家自己的单排掉
                    if (!Objects.equals(provider.getStoreId(), supplierStoreId)) {
                        // 封装供应商的发送请求
                        StoreMessageMQRequest providerMqRequest = new StoreMessageMQRequest();
                        providerMqRequest.setStoreId(provider.getStoreId());
                        providerMqRequest.setNodeCode(ProviderMessageNode.TRADE_BUYER_MODIFY_CONSIGNEE.getCode());
                        providerMqRequest.setProduceTime(LocalDateTime.now());
                        providerMqRequest.setContentParams(Lists.newArrayList(providerTrade.getId()));
                        providerMqRequest.setRouteParams(MutableMap.of("tid", providerTrade.getId()));
                        orderProducerService.sendStoreMessage(providerMqRequest);
                    }
                }
            }
        } catch (Exception e) {
            log.error("买家修改收货信息变更提醒，消息处理失败，{} {}", trade, e);
        }
    }
}

