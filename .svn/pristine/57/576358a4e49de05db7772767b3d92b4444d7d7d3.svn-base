package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.order.api.request.trade.ConfirmProviderOrderRequest;
import com.wanmi.sbc.order.api.request.trade.TradeUpdateRequest;
import com.wanmi.sbc.order.bean.dto.CycleDeliveryPlanDTO;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.common.OperationLogMq;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.repository.ReturnOrderRepository;
import com.wanmi.sbc.order.trade.fsm.event.TradeEvent;
import com.wanmi.sbc.order.trade.model.entity.TradeDeliver;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.ShippingItem;
import com.wanmi.sbc.order.trade.model.entity.value.TradeEventLog;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.repository.ProviderTradeRepository;
import com.wanmi.sbc.order.trade.repository.TradeRepository;
import com.wanmi.sbc.order.trade.request.ProviderTradeQueryRequest;
import com.wanmi.sbc.order.trade.request.TradeDeliverRequest;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 供应商订单处理服务层
 * @Autho qiaokang
 * @Date：2020-02-11 22:56
 */
@Service
@Slf4j
public class ProviderTradeService {

    @Autowired
    private ProviderTradeRepository providerTradeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private OperationLogMq operationLogMq;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeCacheService tradeCacheService;

    @Autowired
    private TradeRepository tradeRepository;


    /**
     * 新增文档
     * 专门用于数据新增服务,不允许数据修改的时候调用
     *
     * @param trade
     */
    public void addProviderTrade(ProviderTrade trade) {
        providerTradeRepository.save(trade);
    }

    /**
     * 修改文档
     * 专门用于数据修改服务,不允许数据新增的时候调用
     *
     * @param trade
     */
    public void updateProviderTrade(ProviderTrade trade) {
        providerTradeRepository.save(trade);
    }

    /**
     * 修改文档
     * 专门用于数据修改服务,不允许数据新增的时候调用
     *
     * @param tradeList
     */
    public void updateProviderTradeList(List<ProviderTrade> tradeList) {
        providerTradeRepository.saveAll(tradeList);
    }

    /**
     * 根据父订单号查询供货商订单
     *
     * @param parentTid
     */
    public List<ProviderTrade> findListByParentId(String parentTid) {
        return providerTradeRepository.findListByParentId(parentTid);
    }

    /**
     * 根据父订单列表查询供货商订单
     */
    public List<ProviderTrade> findListByParentIdList(List<String> parentTidList) {
        return providerTradeRepository.findByParentIdIn(parentTidList);
    }

    /**
     *
     */
    public ProviderTrade findbyId(String id) {
        return providerTradeRepository.findFirstById(id);
    }

    /**
     * 查询订单
     *
     * @param tid
     */
    public ProviderTrade providerDetail(String tid) {
        return providerTradeRepository.findById(tid).orElse(null);
    }

    /**
     * 更新订单
     *
     * @param tradeUpdateRequest
     */
    @GlobalTransactional
    @Transactional
    public void updateProviderTrade(TradeUpdateRequest tradeUpdateRequest) {
        this.updateProviderTrade(KsBeanUtil.convert(tradeUpdateRequest.getTrade(), ProviderTrade.class));
    }

    /**
     * 订单分页
     *
     * @param whereCriteria 条件
     * @param request       参数
     * @return
     */
    public Page<ProviderTrade> providerPage(Criteria whereCriteria, ProviderTradeQueryRequest request) {
        long totalSize = this.countNum(whereCriteria, request);
        if (totalSize < 1) {
            return new PageImpl<>(new ArrayList<>(), request.getPageRequest(), totalSize);
        }
        request.putSort(request.getSortColumn(), request.getSortRole());
        Query query = new Query(whereCriteria);
        return new PageImpl<>(mongoTemplate.find(query.with(request.getPageRequest()), ProviderTrade.class), request
                .getPageable(), totalSize);
    }

    /**
     * 统计数量
     *
     * @param whereCriteria
     * @param request
     * @return
     */
    public long countNum(Criteria whereCriteria, ProviderTradeQueryRequest request) {
        request.putSort(request.getSortColumn(), request.getSortRole());
        Query query = new Query(whereCriteria);
        long totalSize = mongoTemplate.count(query, ProviderTrade.class);
        return totalSize;
    }

    /**
     * 取消供应商订单
     *
     * @param parentId 父订单id
     * @param operator 操作人
     * @param isAuto   是否定时取消
     */
    @Transactional
    @GlobalTransactional
    public void providerCancel(String parentId, Operator operator, boolean isAuto) {
        List<ProviderTrade> providerTradeList = this.findListByParentId(parentId);

        if (CollectionUtils.isNotEmpty(providerTradeList)) {
            String msg = "用户取消订单";
            if (isAuto) {
                msg = "订单超时未支付，系统自动取消";
            }
            final String data = msg;

            providerTradeList.forEach(providerTrade -> {
                // 更新供应商订单状态为已作废
                providerTrade.getTradeState().setFlowState(FlowState.VOID);
                providerTrade.getTradeState().setEndTime(LocalDateTime.now());
                providerTrade.appendTradeEventLog(new TradeEventLog(operator, "取消订单", data, LocalDateTime.now()));
                this.updateProviderTrade(providerTrade);
            });
        }
    }

    /**
     * 审核供应商订单
     *
     * @param parentId
     * @param reason
     * @param auditState
     */
    public void providerAudit(String parentId, String reason, AuditState auditState) {
        List<ProviderTrade> providerTradeList = this.findListByParentId(parentId);

        if (CollectionUtils.isNotEmpty(providerTradeList)) {
            providerTradeList.forEach(providerTrade -> {
                // 更新供应商订单状态为已作废
                providerTrade.getTradeState().setAuditState(auditState);
                if (AuditState.REJECTED == auditState) {
                    providerTrade.getTradeState().setObsoleteReason(reason);
                } else {
                    providerTrade.getTradeState().setFlowState(FlowState.AUDIT);
                }
                this.updateProviderTrade(providerTrade);
            });
        }

    }

    /**
     * 发货校验,检查请求发货商品数量是否符合应发货数量
     *
     * @param tid                 订单id
     * @param tradeDeliverRequest 发货请求参数结构
     */
    public void deliveryCheck(String tid, TradeDeliverRequest tradeDeliverRequest, Operator operator) {
        ProviderTrade providerTrade = providerDetail(tid);
        if (Objects.isNull(providerTrade)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 验证当前用户是否允许操作
        if (Objects.nonNull(operator)
                && (Objects.isNull(providerTrade.getSupplier())
                        || !Objects.equals(
                                operator.getStoreId(), providerTrade.getSupplier().getStoreId().toString()))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        Map<String, TradeItem> skusMap =
                providerTrade.getTradeItems().stream().collect(Collectors.toMap(TradeItem::getSkuId,
                        Function.identity()));
        Map<Long, Map<String, TradeItem>> giftsMap =
                providerTrade.getGifts().stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0),
                        Collectors.toMap(TradeItem::getSkuId, Function.identity())));
        Map<Long, Map<String, TradeItem>> map =
                providerTrade.getPreferential().stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0),
                        Collectors.toMap(TradeItem::getSkuId, Function.identity())));
        Map<String, Integer> returnItemMap = tradeService.getReturnItemNum(tid, Boolean.FALSE);
        OrderTag orderTag = providerTrade.getOrderTag();
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        tradeDeliverRequest.getShippingItemList().forEach(i -> {
            TradeItem tradeItem = skusMap.get(i.getSkuId());
            //退货数量
            Integer returnNum = returnItemMap.get(tradeItem.getSkuId());
            if (Objects.isNull(returnNum)) {
                returnNum = NumberUtils.INTEGER_ZERO;
            }
            if (!buyCycleFlag && (tradeItem.getDeliveredNum() + i.getItemNum() > tradeItem.getNum() - returnNum)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050087);
            }
            if (buyCycleFlag && returnNum > 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050047, new Object[]{providerTrade.getId()});
            }
        });

        Map<Long, Map<String, Integer>> returnGiftsMap = tradeService.getGiftReturnItemNum(tid);
        tradeDeliverRequest.getGiftItemList().forEach(i -> {
            TradeItem tradeItem = giftsMap.getOrDefault(i.getMarketingId(), new HashMap<>()).get(i.getSkuId());
            //退货数量
            Integer returnNum = returnGiftsMap.getOrDefault(tradeItem.getMarketingIds().get(0), new HashMap<>()).get(tradeItem.getSkuId());
            if (Objects.isNull(returnNum)) {
                returnNum = NumberUtils.INTEGER_ZERO;
            }
            if (!buyCycleFlag && (tradeItem.getDeliveredNum() + i.getItemNum() > tradeItem.getNum() - returnNum)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050087);
            }
        });
        Map<Long, Map<String, Integer>> returnPreferentialMap = tradeService.getPreferentialReturnItemNum(tid);
        tradeDeliverRequest.getPreferentialItemList().forEach(i -> {
            TradeItem tradeItem = map.getOrDefault(i.getMarketingId(), new HashMap<>()).get(i.getSkuId());
            //退货数量
            Integer returnNum =
                    returnPreferentialMap.getOrDefault(tradeItem.getMarketingIds().get(0), new HashMap<>()).get(tradeItem.getSkuId());
            if (Objects.isNull(returnNum)) {
                returnNum = NumberUtils.INTEGER_ZERO;
            }
            if (!buyCycleFlag && (tradeItem.getDeliveredNum() + i.getItemNum() > tradeItem.getNum() - returnNum)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050087);
            }
        });
    }

    /**
     * 发货
     *
     * @param tid
     * @param tradeDeliver
     * @param operator
     * @return
     */
    public String deliver(String tid, TradeDeliver tradeDeliver, Operator operator,Integer remindShipping) {
        ProviderTrade providerTrade = providerDetail(tid);
        //是否开启订单审核
        if (auditQueryProvider.isSupplierOrderAudit().getContext().isAudit() && providerTrade.getTradeState().getAuditState()
                != AuditState.CHECKED) {
            //只有已审核订单才能发货
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050089);
        }
        // 先款后货并且未支付的情况下禁止发货
        if (providerTrade.getPaymentOrder() == PaymentOrder.PAY_FIRST && providerTrade.getTradeState().getPayState() == PayState.NOT_PAID && providerTrade.getPayInfo().getPayTypeId().equals(Constants.STR_0)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050091);
        }
        if (tradeService.tradeVerifyAfterProcessingAll(providerTrade.getId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050024, new Object[]{providerTrade.getParentId()});
        }

        //京东订单多个订单使用同一个物流单号，暂时屏蔽校验物流单号唯一
//        checkLogisticsNo(tradeDeliver.getLogistics().getLogisticNo(), tradeDeliver.getLogistics()
//                .getLogisticStandardCode());

        // 生成ID
        tradeDeliver.setDeliverId(generatorService.generate("TD"));
        tradeDeliver.setStatus(DeliverStatus.SHIPPED);
        tradeDeliver.setProviderName(providerTrade.getSupplier().getSupplierName());
        tradeDeliver.setTradeId(tid);

        List<TradeDeliver> tradeDelivers = providerTrade.getTradeDelivers();


        Map<String, Integer> returnEndItemMap = tradeService.getReturnEndItemNum(providerTrade.getId(), Boolean.FALSE);
        OrderTag orderTag = providerTrade.getOrderTag();
        providerTrade.getTradeItems().forEach(tradeItem -> {
            // 当前商品本次发货信息
            List<ShippingItem> shippingItems = tradeDeliver.getShippingItems().stream()
                    .filter(shippingItem -> tradeItem.getSkuId().equals(shippingItem.getSkuId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(shippingItems)) {
                // 当前商品发货数量加上本次发货数量
                tradeItem.setDeliveredNum(shippingItems.get(0).getItemNum() + tradeItem.getDeliveredNum());
                //已发货+售后完成(完成、拒绝退款)
                Integer returnNum = returnEndItemMap.get(tradeItem.getSkuId());
                Long sum = tradeItem.getDeliveredNum();
                if (Objects.nonNull(returnNum) && returnNum > 0) {
                    sum += returnNum;
                }
                Long num = tradeItem.getNum();
                // 周期购
                if (Objects.nonNull(orderTag) && Objects.equals(orderTag.getBuyCycleFlag(),Boolean.TRUE)) {
                    TradeBuyCycleDTO tradeBuyCycle = providerTrade.getTradeBuyCycle();
                    Integer deliveryCycleNum = tradeBuyCycle.getDeliveryCycleNum();
                    num = num * deliveryCycleNum;
                }
                // 判断当前商品是否已全部发货
                if (num.equals(sum)) {
                    tradeItem.setDeliverStatus(DeliverStatus.SHIPPED);
                } else if (num.compareTo(sum) > 0) {
                    tradeItem.setDeliverStatus(DeliverStatus.PART_SHIPPED);
                } else {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050019, new Object[]{tradeItem.getSkuId(), tradeItem.getNum(), tradeItem.getDeliveredNum()});
                }
            }
        });

        //赠品
        Map<Long, Map<String, Integer>> returnEndGiftsMap =
                tradeService.getGiftReturnEndItemNum(providerTrade.getId());
        providerTrade.getGifts().forEach(gift -> {
            // 当前赠品本次发货信息
            List<ShippingItem> shippingItems = tradeDeliver.getGiftItemList().stream()
                    .filter(shippingItem -> gift.getSkuId().equals(shippingItem.getSkuId())
                            && Objects.nonNull(shippingItem.getMarketingId())
                            && shippingItem.getMarketingId().equals(gift.getMarketingIds().get(0))).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(shippingItems)) {
                // 当前赠品发货数量加上本次发货数量
                gift.setDeliveredNum(shippingItems.get(0).getItemNum() + gift.getDeliveredNum());
                //已发货+售后完成(完成、拒绝退款)
                Integer returnNum = NumberUtils.INTEGER_ZERO;
                if(MapUtils.isNotEmpty(returnEndGiftsMap) && MapUtils.isNotEmpty(returnEndGiftsMap.get(gift.getMarketingIds().get(0)))){
                    returnNum = returnEndGiftsMap.getOrDefault(gift.getMarketingIds().get(0), new HashMap<>()).get(gift.getSkuId());
                }
                Long sum = gift.getDeliveredNum();
                if (Objects.nonNull(returnNum) && returnNum > 0) {
                    sum += returnNum;
                }
                // 判断赠品商品是否已全部发货
                if (gift.getNum().equals(sum)) {
                    gift.setDeliverStatus(DeliverStatus.SHIPPED);
                } else if (gift.getNum().compareTo(sum) > 0) {
                    gift.setDeliverStatus(DeliverStatus.PART_SHIPPED);
                } else {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050019, new Object[]{gift.getSkuId(), gift.getNum(), gift.getDeliveredNum()});
                }
            }
        });

        // 加价购
        Map<Long, Map<String, Integer>> returnEndMap = tradeService.getReturnEndItemNum(providerTrade.getId());
        providerTrade.getPreferential().forEach(item -> {
            // 当前加价购本次发货信息
            List<ShippingItem> shippingItems = tradeDeliver.getPreferentialItemList().stream()
                    .filter(shippingItem -> item.getSkuId().equals(shippingItem.getSkuId())
                    && Objects.nonNull(shippingItem.getMarketingId())
                            && shippingItem.getMarketingId().equals(item.getMarketingIds().get(0))).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(shippingItems)) {
                // 当前加价购发货数量加上本次发货数量
                item.setDeliveredNum(shippingItems.get(0).getItemNum() + item.getDeliveredNum());
                //已发货+售后完成(完成、拒绝退款)
                Integer returnNum = NumberUtils.INTEGER_ZERO;
                if(MapUtils.isNotEmpty(returnEndMap) && MapUtils.isNotEmpty(returnEndMap.get(item.getMarketingIds().get(0)))){
                    returnNum = returnEndMap.getOrDefault(item.getMarketingIds().get(0), new HashMap<>()).get(item.getSkuId());
                }
                Long sum = item.getDeliveredNum();
                if (Objects.nonNull(returnNum) && returnNum > 0) {
                    sum += returnNum;
                }
                // 判断加价购商品是否已全部发货
                if (item.getNum().equals(sum)) {
                    item.setDeliverStatus(DeliverStatus.SHIPPED);
                } else if (item.getNum().compareTo(sum) > 0) {
                    item.setDeliverStatus(DeliverStatus.PART_SHIPPED);
                } else {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050019, new Object[]{item.getSkuId(), item.getNum(), item.getDeliveredNum()});
                }
            }
        });

        // 判断本次发货后，是否还有部分发货或未发货的商品，来设置订单发货状态
        Long partShippedNum = providerTrade.getTradeItems().stream()
                .filter(tradeItem -> (tradeItem.getDeliverStatus() == DeliverStatus.PART_SHIPPED ||
                        tradeItem.getDeliverStatus() == DeliverStatus.NOT_YET_SHIPPED)).count();
        //赠品
        Long giftsNum = providerTrade.getGifts().stream().filter(gift -> DeliverStatus.NOT_YET_SHIPPED == gift.getDeliverStatus() ||
                DeliverStatus.PART_SHIPPED == gift.getDeliverStatus()).count();
        partShippedNum += giftsNum;

        // 加价购
        long num =
                providerTrade.getPreferential().stream().filter(g -> DeliverStatus.NOT_YET_SHIPPED == g.getDeliverStatus() ||
                DeliverStatus.PART_SHIPPED == g.getDeliverStatus()).count();
        partShippedNum += num;

        // 周期购
        if (Objects.nonNull(orderTag) && Objects.equals(orderTag.getBuyCycleFlag(),Boolean.TRUE)) {
            providerTrade.getTradeBuyCycle().setRemindShipping(remindShipping);
            TradeBuyCycleDTO tradeBuyCycle = providerTrade.getTradeBuyCycle();
            List<CycleDeliveryPlanDTO> deliveryPlanS = tradeBuyCycle.getDeliveryPlanS();
            CycleDeliveryPlanDTO planDTO = deliveryPlanS.stream()
                    .filter(cycleDeliveryPlanDTO -> Objects.equals(CycleDeliveryState.NOT_SHIP, cycleDeliveryPlanDTO.getCycleDeliveryState()))
                    .min(Comparator.comparingInt(CycleDeliveryPlanDTO::getDeliveryNum))
                    .orElseThrow(() -> new SbcRuntimeException(OrderErrorCodeEnum.K050157));
            Integer deliveryNum = planDTO.getDeliveryNum();
            planDTO.setCycleDeliveryState(CycleDeliveryState.SHIPPED);
            tradeDeliver.setCycleDeliveryPlan(planDTO);
            TradeItem tradeItem = providerTrade.getTradeItems().stream().findFirst()
                    .orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030047));
            Integer returnNum = returnEndItemMap.get(tradeItem.getSkuId());
            //说明周期购订单已经售后，不能发货
            if (Objects.nonNull(returnNum) && returnNum > 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050047, new Object[]{tid});
            }
            long count = deliveryPlanS.parallelStream().filter(cycleDeliveryPlanDTO ->
                    Objects.equals(CycleDeliveryState.NOT_SHIP, cycleDeliveryPlanDTO.getCycleDeliveryState())).count();
            if (Objects.equals(count, BigDecimal.ZERO.longValue())) {
                //全部发货 没有下一期
                for (CycleDeliveryPlanDTO plan: deliveryPlanS) {
                    if (Objects.equals(deliveryNum,plan.getDeliveryNum())) {
                        plan.setCycleDeliveryState(CycleDeliveryState.SHIPPED);
                        break;
                    }
                }
                providerTrade.getTradeBuyCycle().setBuyCycleNextPlanDate(null);
                partShippedNum = 0L;
            } else {
                CycleDeliveryPlanDTO nextPlan = deliveryPlanS.stream().peek(plan -> {
                    if (Objects.equals(deliveryNum,plan.getDeliveryNum())) {
                        plan.setCycleDeliveryState(CycleDeliveryState.SHIPPED);
                    }
                }).filter(cycleDeliveryPlanDTO -> Objects.equals(CycleDeliveryState.NOT_SHIP, cycleDeliveryPlanDTO.getCycleDeliveryState()))
                .min(Comparator.comparingInt(CycleDeliveryPlanDTO::getDeliveryNum)).orElse(new CycleDeliveryPlanDTO());
                providerTrade.getTradeBuyCycle().setBuyCycleNextPlanDate(nextPlan.getDeliveryDate());
                partShippedNum = 1L;
            }
            providerTrade.getTradeBuyCycle().setDeliveryPlanS(deliveryPlanS);
        }

        //添加操作日志
        String detail = String.format("订单[%s]已%s,操作人：%s", providerTrade.getId(), "发货", operator.getName());
        if (partShippedNum.intValue() != 0) {
            providerTrade.getTradeState().setFlowState(FlowState.DELIVERED_PART);
            providerTrade.getTradeState().setDeliverStatus(DeliverStatus.PART_SHIPPED);
            detail = String.format("订单[%s]已%s,操作人：%s", providerTrade.getId(), "部分发货", operator.getName());
        } else {
            providerTrade.getTradeState().setFlowState(FlowState.DELIVERED);
            providerTrade.getTradeState().setDeliverStatus(DeliverStatus.SHIPPED);
        }

        providerTrade.appendTradeEventLog(TradeEventLog
                .builder()
                .operator(operator)
                .eventType(partShippedNum.intValue() != 0 ? FlowState.DELIVERED_PART.getDescription() : TradeEvent.DELIVER.getDescription())
                .eventTime(LocalDateTime.now())
                .eventDetail(detail)
                .build());

        tradeDelivers.add(0, tradeDeliver);
        providerTrade.setTradeDelivers(tradeDelivers);
        this.updateProviderTrade(providerTrade);

        tradeDeliver.setSunDeliverId(tradeDeliver.getDeliverId());
        tradeDeliver.setShipperType(ShipperType.PROVIDER);
        tradeService.deliver(providerTrade.getParentId(), tradeDeliver, operator, BoolFlag.NO,remindShipping);

        return tradeDeliver.getDeliverId();
    }

    /**
     * 子单批量发货处理
     */
    public String dealBatchDeliver(ProviderTrade providerTrade, TradeDeliver tradeDeliver, Operator operator) {

        Boolean isAllDelivered = Boolean.TRUE;
        //商品
        Map<String, Integer> returnItemMap = tradeService.getReturnItemNum(providerTrade.getId(), Boolean.FALSE);
        Map<String, Integer> returnEndItemMap = tradeService.getReturnEndItemNum(providerTrade.getId(), Boolean.FALSE);
        //赠品
        Map<Long, Map<String, Integer>> returnGiftsMap =
                tradeService.getGiftReturnItemNum(providerTrade.getId());
        Map<Long, Map<String, Integer>> returnEndGiftsMap =
                tradeService.getGiftReturnEndItemNum(providerTrade.getId());
        //加价购
        Map<Long, Map<String, Integer>> returnPreferentialMap =
                tradeService.getPreferentialReturnItemNum(providerTrade.getId());
        Map<Long, Map<String, Integer>> returnEndPreferentialMap = tradeService.getReturnEndItemNum(providerTrade.getId());

        List<ShippingItem> shippingItems = new ArrayList<>();
        List<ShippingItem> giftItems = new ArrayList<>();
        List<ShippingItem> preferentialItems = new ArrayList<>();
        for (TradeItem tradeItem : providerTrade.getTradeItems()) {
            isAllDelivered = this.dealShippingItemForBatchDeliver(tradeItem, returnItemMap, returnEndItemMap, isAllDelivered);
            ShippingItem shippingItem = KsBeanUtil.copyPropertiesThird(tradeItem, ShippingItem.class);
            shippingItem.setItemName(tradeItem.getSkuName());
            shippingItem.setItemNum(tradeItem.getDeliveredNum());
            shippingItems.add(shippingItem);
        }

        for (TradeItem gift : providerTrade.getGifts()) {
            Map<String, Integer> returnMapNew =
                    returnGiftsMap.get(gift.getMarketingIds().get(0));
            Map<String, Integer> returnEndMapNew =
                    returnEndGiftsMap.get(gift.getMarketingIds().get(0));
            isAllDelivered = this.dealShippingItemForBatchDeliver(gift, returnMapNew, returnEndMapNew, isAllDelivered);
            ShippingItem shippingItem = KsBeanUtil.copyPropertiesThird(gift, ShippingItem.class);
            shippingItem.setItemName(gift.getSkuName());
            shippingItem.setItemNum(gift.getDeliveredNum());
            giftItems.add(shippingItem);
        }

        for (TradeItem item : providerTrade.getPreferential()) {
            Map<String, Integer> returnMap = returnPreferentialMap.get(item.getMarketingIds().get(0));
            Map<String, Integer> returnEndMap = returnEndPreferentialMap.get(item.getMarketingIds().get(0));
            isAllDelivered = this.dealShippingItemForBatchDeliver(item, returnMap, returnEndMap, isAllDelivered);
            ShippingItem shippingItem = KsBeanUtil.copyPropertiesThird(item, ShippingItem.class);
            shippingItem.setItemName(item.getSkuName());
            shippingItem.setItemNum(item.getDeliveredNum());
            shippingItem.setMarketingId(item.getMarketingIds().get(0));
            preferentialItems.add(shippingItem);
        }
        tradeDeliver.setShippingItems(shippingItems);
        tradeDeliver.setGiftItemList(giftItems);
        tradeDeliver.setPreferentialItemList(preferentialItems);
        tradeDeliver.setTradeId(providerTrade.getId());
        providerTrade.addTradeDeliver(tradeDeliver);

        providerTrade.getTradeState().setFlowState(isAllDelivered ? FlowState.DELIVERED : FlowState.DELIVERED_PART);
        providerTrade.getTradeState().setDeliverStatus(isAllDelivered ? DeliverStatus.SHIPPED : DeliverStatus.PART_SHIPPED);
        //添加操作日志
        String detail = String.format("订单[%s]已%s,操作人：%s", providerTrade.getId(), isAllDelivered ? "全部发货" : "部分发货", operator.getName());
        providerTrade.appendTradeEventLog(TradeEventLog
                .builder()
                .operator(operator)
                .eventType(isAllDelivered ? FlowState.DELIVERED.getDescription() : FlowState.DELIVERED_PART.getDescription())
                .eventTime(LocalDateTime.now())
                .eventDetail(detail)
                .build());

        // 更新发货信息
        this.updateProviderTrade(providerTrade);
        return tradeDeliver.getDeliverId();
    }

    /**
     * 批量发货设置发货数量和发货状态
     * @param item 当前设置的商品
     * @param returnItemMap 售后处理中和完成的商品数量
     * @param returnEndItemMap 售后完成的商品数量
     * @param isAllDelivered 是否全部发货标识
     */
    public Boolean dealShippingItemForBatchDeliver(TradeItem item, Map<String, Integer> returnItemMap, Map<String, Integer> returnEndItemMap, Boolean isAllDelivered) {
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
            isAllDelivered = Boolean.FALSE;
        }

        return isAllDelivered;
    }

    /**
     * 查询导出数据
     *
     * @param queryRequest
     */
    public List<ProviderTrade> listProviderTradeExport(ProviderTradeQueryRequest queryRequest) {
        queryRequest.putSort(queryRequest.getSortColumn(), queryRequest.getSortRole());

        //设置返回字段
        Map fieldsObject = new HashMap(32);
        fieldsObject.put("_id", Boolean.TRUE);
        fieldsObject.put("parentId", Boolean.TRUE);
        fieldsObject.put("tradeState.createTime", Boolean.TRUE);
        fieldsObject.put("tradeState.endTime", Boolean.TRUE);
        fieldsObject.put("supplierName", Boolean.TRUE);
        fieldsObject.put("supplierCode", Boolean.TRUE);
        fieldsObject.put("consignee.name", Boolean.TRUE);
        fieldsObject.put("consignee.phone", Boolean.TRUE);
        fieldsObject.put("consignee.detailAddress", Boolean.TRUE);
        fieldsObject.put("deliverWay", Boolean.TRUE);
        fieldsObject.put("tradePrice.goodsPrice", Boolean.TRUE);

        fieldsObject.put("tradePrice.totalPrice", Boolean.TRUE);
        fieldsObject.put("tradeItems.skuId", Boolean.TRUE);
        fieldsObject.put("tradeItems.skuNo", Boolean.TRUE);
        fieldsObject.put("tradeItems.providerSkuNo", Boolean.TRUE);
        fieldsObject.put("tradeItems.providerSkuId", Boolean.TRUE);
        fieldsObject.put("tradeItems.specDetails", Boolean.TRUE);
        fieldsObject.put("tradeItems.skuName", Boolean.TRUE);
        fieldsObject.put("tradeItems.num", Boolean.TRUE);
        fieldsObject.put("tradeItems.cateId", Boolean.TRUE);
        fieldsObject.put("tradeItems.supplyPrice", Boolean.TRUE);
        fieldsObject.put("tradeItems.providerName", Boolean.TRUE);
        fieldsObject.put("tradeItems.totalSupplyPrice", Boolean.TRUE);

        fieldsObject.put("gifts.skuId",Boolean.TRUE);
        fieldsObject.put("gifts.skuNo",Boolean.TRUE);
        fieldsObject.put("gifts.skuName",Boolean.TRUE);
        fieldsObject.put("gifts.num",Boolean.TRUE);
        fieldsObject.put("gifts.cateId",Boolean.TRUE);
        fieldsObject.put("gifts.supplyPrice",Boolean.TRUE);
        fieldsObject.put("gifts.totalSupplyPrice",Boolean.TRUE);
        fieldsObject.put("gifts.providerSkuNo", Boolean.TRUE);
        fieldsObject.put("gifts.providerSkuId", Boolean.TRUE);

        fieldsObject.put("preferential.skuId", Boolean.TRUE);
        fieldsObject.put("preferential.skuNo", Boolean.TRUE);
        fieldsObject.put("preferential.providerSkuNo", Boolean.TRUE);
        fieldsObject.put("preferential.providerSkuId", Boolean.TRUE);
        fieldsObject.put("preferential.specDetails", Boolean.TRUE);
        fieldsObject.put("preferential.skuName", Boolean.TRUE);
        fieldsObject.put("preferential.num", Boolean.TRUE);
        fieldsObject.put("preferential.cateId", Boolean.TRUE);
        fieldsObject.put("preferential.supplyPrice", Boolean.TRUE);
        fieldsObject.put("preferential.providerName", Boolean.TRUE);
        fieldsObject.put("preferential.totalSupplyPrice", Boolean.TRUE);

        fieldsObject.put("buyerRemark", Boolean.TRUE);
        fieldsObject.put("sellerRemark", Boolean.TRUE);
        fieldsObject.put("tradeState.flowState", Boolean.TRUE);
        fieldsObject.put("tradeState.payState", Boolean.TRUE);
        fieldsObject.put("tradeState.deliverStatus", Boolean.TRUE);
        fieldsObject.put("tradeItems.points", Boolean.TRUE);
        fieldsObject.put("tradeItems.pointsPrice", Boolean.TRUE);
        fieldsObject.put("preferential.points", Boolean.TRUE);
        fieldsObject.put("preferential.pointsPrice", Boolean.TRUE);
        fieldsObject.put("tradeBuyCycle", Boolean.TRUE);
        fieldsObject.put("grouponFlag", Boolean.TRUE);
        fieldsObject.put("isFlashSaleGoods", Boolean.TRUE);
        fieldsObject.put("isBookingSaleGoods", Boolean.TRUE);
        fieldsObject.put("orderTag", Boolean.TRUE);
        fieldsObject.put("bargain", Boolean.TRUE);
        fieldsObject.put("orderType", Boolean.TRUE);

        // 设置订单状态为已支付
        Integer paymentOrder = tradeCacheService.getTradeConfigByType(ConfigType.ORDER_SETTING_PAYMENT_ORDER).getStatus();
        if (PaymentOrder.PAY_FIRST == PaymentOrder.values()[paymentOrder]) {
            if (Objects.isNull(queryRequest.getTradeState())) {
                queryRequest.setTradeState(new TradeState());
            }
            queryRequest.getTradeState().setPayState(PayState.PAID);
            //包含历史订单中 不限的
            queryRequest.setNeedNoLimit(Boolean.TRUE);
        }
        queryRequest.setOrderType(OrderType.ALL_ORDER);
        // 供应商订单列表要过滤待审核和待成团的订单
        queryRequest.setNotFlowStates(Arrays.asList(FlowState.INIT, FlowState.GROUPON));
        Query query = new BasicQuery(new Document(), new Document(fieldsObject));
        query.addCriteria(queryRequest.getWhereCriteria());
        List<ProviderTrade> tradeList = mongoTemplate.find(query.with(queryRequest.getPageRequest()), ProviderTrade.class);

        return tradeList;
    }


    /**
     * 查询订单集合
     *
     * @param tids
     */
    public List<ProviderTrade> details(List<String> tids) {
        return org.apache.commons.collections4.IteratorUtils.toList(providerTradeRepository.findAllById(tids).iterator());
    }

    /**
     * 查询全部订单
     *
     * @param request
     * @return
     */
    public List<ProviderTrade> queryAll(ProviderTradeQueryRequest request) {
        return mongoTemplate.find(new Query(request.getWhereCriteria()), ProviderTrade.class);
    }


    /**
     * 修改备注
     *
     * @param tid
     * @param sellerRemark
     */
    @Transactional
    public void remedySellerRemark(String tid, String sellerRemark, Operator operator) {
        //1、查找订单信息
        ProviderTrade providerTrade = providerDetail(tid);
        providerTrade.setSellerRemark(sellerRemark);
        Trade trade = tradeService.detail(providerTrade.getParentId());
        providerTrade.appendTradeEventLog(new TradeEventLog(operator, "修改备注", "修改供应商订单备注", LocalDateTime.now()));
        trade.appendTradeEventLog(new TradeEventLog(operator, "修改备注", "修改供应商订单备注", LocalDateTime.now()));
        //保存
        providerTradeRepository.save(providerTrade);
        tradeService.updateTrade(trade);
        this.operationLogMq.convertAndSend(operator, "修改供应商订单备注", "修改供应商订单备注");
    }

    public String providerByidAndPid(String tid, String providerId) {
        String providerTradeId = StringUtils.EMPTY;
        List<ProviderTrade> providerTrades = this.findListByParentId(tid);
        for (ProviderTrade providerTrade : providerTrades) {
            if (Long.parseLong(providerId) == providerTrade.getSupplier().getStoreId()) {
                providerTradeId = providerTrade.getId();
            }
        }
        return providerTradeId;
    }

    /**
     * 根据主订单id，供应商(或商家)id获取子订单
     *
     * @param tid
     * @param providerId
     * @return
     */
    public ProviderTrade getProviderTradeByIdAndPid(String tid, Long providerId) {
        List<ProviderTrade> providerTrades = this.findListByParentId(tid);
        Optional<ProviderTrade> optional = providerTrades.stream()
                .filter(trade -> providerId.longValue() == trade.getSupplier().getStoreId().longValue())
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }


    public List<ProviderTrade> findTradeListForSettlement(Long storeId, Date startTime, Date endTime, PageRequest pageRequest) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("supplier.storeId").is(storeId)
                , new Criteria().orOperator(
                        Criteria.where("tradeState.flowState").is(FlowState.COMPLETED),
                        new Criteria().andOperator(Criteria.where("tradeState.payState").is(PayState.PAID) , Criteria.where("tradeState.flowState").is(FlowState.VOID)),
                        Criteria.where("refundFlag").is(Boolean.TRUE))
                , Criteria.where("returnOrderNum").is(0)
                , Criteria.where("tradeState.finalTime").lt(endTime).gte(startTime)
        );

        return mongoTemplate.find(
                new Query(criteria).skip(pageRequest.getPageNumber() * pageRequest.getPageSize() * 1L).limit(pageRequest
                        .getPageSize())
                , ProviderTrade.class);
    }

    public List<ProviderTrade> findLakalaTradeListForSettlement(Long providerStoreId, Date startTime, Date endTime,
                                                           PageRequest pageRequest, Long storeId) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("supplier.storeId").is(providerStoreId),
                Criteria.where("storeId").is(storeId)
                , new Criteria().orOperator(
                        Criteria.where("tradeState.flowState").is(FlowState.COMPLETED),
                        new Criteria().andOperator(Criteria.where("tradeState.payState").is(PayState.PAID) , Criteria.where("tradeState.flowState").is(FlowState.VOID)),
                        Criteria.where("refundFlag").is(Boolean.TRUE))
                , Criteria.where("returnOrderNum").is(0)
                , Criteria.where("tradeState.finalTime").lt(endTime).gte(startTime)
        );

        return mongoTemplate.find(
                new Query(criteria).skip(pageRequest.getPageNumber() * pageRequest.getPageSize() * 1L).limit(pageRequest
                        .getPageSize())
                , ProviderTrade.class);
    }

    /**
     * 更新订单的待确认标识
     *
     * @param providerTradeId 供应商订单号
     * @param payState        付款标识
     */
    @Transactional
    public void updateThirdPlatformPayState(String providerTradeId, PayState payState) {
        mongoTemplate.updateMulti(new Query(Criteria.where("id").is(providerTradeId)), new Update().set("tradeState.payState", payState), ProviderTrade.class);
    }

    /**
     * 更新订单的错误标识
     *
     * @param providerTradeId 供应商订单号
     * @param errorFlag       错误标识
     */
    @Transactional
    public void updateThirdPlatformPayFlag(String providerTradeId, Boolean errorFlag) {
        mongoTemplate.updateMulti(new Query(Criteria.where("id").is(providerTradeId)), new Update().set("thirdPlatformPayErrorFlag", errorFlag), ProviderTrade.class);
    }

    /**
     * 更新正在进行的供应商订单数量
     *
     * @param returnOrderId 退单id
     * @param addFlag       退单数加减状态
     */
    @Transactional
    public void updateReturnOrderNumByRid(String returnOrderId, boolean addFlag) {
        ReturnOrder order = returnOrderRepository.findById(returnOrderId).orElse(null);
        if (Objects.isNull(order)) {
            log.error("退单ID:{},查询不到退单信息", returnOrderId);
            return;
        }

        if (StringUtils.isNotBlank(order.getPtid())) {
            ProviderTrade trade = this.findbyId(order.getPtid());
            if (Objects.nonNull(trade)) {
                // 1.根据addFlag加减正在进行的退单
                Integer num = trade.getReturnOrderNum() == null ? Integer.valueOf("0") : trade.getReturnOrderNum();
                mongoTemplate.updateFirst(new Query(Criteria.where("id").is(trade.getId())),
                        new Update().set("hasReturn", Boolean.TRUE)
                        .set("returnOrderNum", addFlag ? ++num : --num), ProviderTrade.class);
            }
        }
    }

    /**
     * 根据条件查询父订单id
     *
     * @param queryRequest
     * @return
     */
    public List<String> findParentIdByCondition(ProviderTradeQueryRequest queryRequest) {
        //设置返回字段
        Map<String, Object> fieldsObject = new HashMap();
        fieldsObject.put("parentId", Boolean.TRUE);
        Query query = new BasicQuery(new Document(), new Document(fieldsObject));
        query.addCriteria(queryRequest.getWhereCriteria());
        List<ProviderTrade> providerTrades = mongoTemplate.find(query, ProviderTrade.class);
        List<String> parentIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(providerTrades)) {
            List<String> ids = providerTrades.stream().map(ProviderTrade::getParentId).collect(Collectors.toList());
            parentIds.addAll(ids);
        }
        return parentIds;
    }

    public void confirmProviderOrder(ConfirmProviderOrderRequest request) {

        //获取供应商子单
        List<ProviderTrade> tradeList = findListByParentId(request.getId());
        List<ProviderTrade> providerTradeList = tradeList.stream().filter(trade -> trade.getId().startsWith("P")).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(providerTradeList.stream()
                .filter(providerTrade -> !Objects.equals(DeliverStatus.SHIPPED,providerTrade.getTradeState().getDeliverStatus()))
                .collect(Collectors.toList()))){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050106);
        }

        tradeList.removeAll(providerTradeList);

        //自营订单子单只存在一个
        ProviderTrade trade = tradeList.get(0);

        confirm(trade, providerTradeList, request);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void confirm(ProviderTrade trade, List<ProviderTrade> providerTradeList, ConfirmProviderOrderRequest request) {
        //修改供应商子单状态
        providerTradeList.forEach(providerTrade -> {
            providerTrade.getTradeState().setFlowState(FlowState.COMPLETED);
            providerTrade.getTradeState().setEndTime(LocalDateTime.now());
            providerTradeRepository.save(providerTrade);
        });

        //修改主单状态
        Trade parentTrade = tradeRepository.findById(request.getId()).orElse(null);

        if (Objects.isNull(parentTrade)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }

        if (DeliverStatus.SHIPPED.equals(trade.getTradeState().getDeliverStatus())) {
            parentTrade.getTradeState().setFlowState(FlowState.DELIVERED);
        }
        if (DeliverStatus.NOT_YET_SHIPPED.equals(trade.getTradeState().getDeliverStatus())) {
            parentTrade.getTradeState().setFlowState(FlowState.DELIVERED_PART);
        }

        //记录确认收货日志
        String detail = String.format("订单%s已确认收货", trade.getId());
        parentTrade.appendTradeEventLog(TradeEventLog
                .builder()
                .operator(request.getOperator())
                .eventType(FlowState.CONFIRMED.getDescription())
                .eventDetail(detail)
                .eventTime(LocalDateTime.now())
                .build());

        tradeRepository.save(parentTrade);
    }

}
