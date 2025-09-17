package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleInProgressRequest;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.request.purchase.Purchase4DistributionSimplifyRequest;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.purchase.Purchase4DistributionResponse;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.TradeConfirmItemVO;
import com.wanmi.sbc.order.bean.vo.TradeGoodsListVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.common.SystemPointsConfigService;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmInterface;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.optimization.trade1.purchase.TradePurchaseInterface;
import com.wanmi.sbc.order.purchase.Purchase;
import com.wanmi.sbc.order.purchase.PurchaseService;
import com.wanmi.sbc.order.trade.model.entity.TradeGrouponCommitForm;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>订单商品操作Service</p>
 * Created by of628-wenzhi on 2017-07-13-上午10:48.
 */
@Slf4j
@Service
public class TradeItemService {

    @Autowired
    private TradeItemSnapshotService tradeItemSnapshotService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    protected MongoTemplate mongoTemplate;
    @Autowired
    private VerifyService verifyService;
    @Autowired
    private VerifyServiceInterface verifyServiceInterface;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private TradeGoodsService tradeGoodsService;

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private StockService stockService;
    @Autowired
    private TradePurchaseInterface tradePurchaseService;
    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Autowired
    private TradeItemService _self;

    /**
     * 获取用户已确认订单的商品快照
     *
     * @param terminalToken 客户id
     * @return 按照商家、店铺分组后的商品快照，只包含skuId与购买数量
     */
    public TradeItemSnapshot findByTerminalToken(String terminalToken) {
        TradeItemSnapshot tradeItemSnapshot = tradeItemSnapshotService.getTradeItemSnapshot(terminalToken);
        if(tradeItemSnapshot == null){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050063);
        }
        return tradeItemSnapshot;
    }

    /**
     * 获取用户已确认订单的商品快照
     *
     * @param terminalToken 客户id
     * @return 按照商家、店铺分组后的商品快照，只包含skuId与购买数量
     */
    public TradeItemSnapshot getTradeItemSnapshot(String terminalToken) {
        return tradeItemSnapshotService.getTradeItemSnapshot(terminalToken);
    }


    /**
     * 获取用户已确认订单的商品快照
     *
     * @param terminalToken 客户id
     * @return 按照商家、店铺分组后的商品快照，只包含skuId与购买数量
     */
    public List<TradeItemGroup> find(String terminalToken) {
          TradeItemSnapshot tradeItemSnapshot = tradeItemSnapshotService.getTradeItemSnapshot(terminalToken);
          if(tradeItemSnapshot == null){
              throw new SbcRuntimeException(OrderErrorCodeEnum.K050063);
          }
          return tradeItemSnapshot.getItemGroups();
    }


    /**
     * 保存订单商品快照
     *
     * @param request            客户id、是否开店礼包
     * @param tradeItems         商品快照，只包含skuId与购买数量，营销id（用于确认订单页面展示营销活动信息）
     * @param tradeMarketingList
     * @param skuList            快照商品详细信息，包含所属商家，店铺等信息
     */
    @GlobalTransactional
    @Transactional
    public void snapshot(TradeItemSnapshotRequest request, List<TradeItem> tradeItems, List<TradeMarketingDTO> tradeMarketingList,
                         List<GoodsInfoDTO> skuList) {
        TradeItemSnapshot snapshot = assembleSnapshot(request,tradeItems,tradeMarketingList,skuList);
        tradeItemSnapshotService.addTradeItemSnapshot(snapshot);
    }


    /**
     * 组装订单快照
     * @param request
     * @param tradeItems
     * @param tradeMarketingList
     * @param skuList
     * @return
     */
    public TradeItemSnapshot assembleSnapshot(TradeItemSnapshotRequest request, List<TradeItem> tradeItems, List<TradeMarketingDTO> tradeMarketingList,
                         List<GoodsInfoDTO> skuList) {
        // 如果为空默认为普通的商品
        tradeItems.forEach(tradeItem -> {
            if(Objects.isNull(tradeItem.getPluginType())){
                tradeItem.setPluginType(PluginType.NORMAL);
            }
        });
        long count = tradeItems.stream().filter(t->t.getPluginType()== PluginType.CROSS_BORDER).count();
        // 如果一个订单快照的商品都不是一种的商品 即跨境和普通的商品 则前端提示
        if( count > 0 && count != tradeItems.size()){
            // 订单确认不能同时含有跨境商品和普通商品
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050059);
        }

        String customerId = request.getCustomerId();
        //验证积分商品的积分与会员可用积分
        if (Boolean.TRUE.equals(request.getPointGoodsFlag())) {
            long sumPoint = tradeItems.stream().mapToLong(t -> t.getNum() *
                    skuList.stream()
                            .filter(s -> s.getGoodsInfoId().equals(t.getSkuId()) && s.getBuyPoint() != null && s.getBuyPoint() > 0)
                            .mapToLong(GoodsInfoDTO::getBuyPoint)
                            .findFirst().orElse(0L)).sum();
            //订单抵扣不需要校验积分
            SystemPointsConfigQueryResponse pointsConfig = systemPointsConfigService.querySettingCache();
            if (sumPoint > 0 && (!verifyService.verifyBuyPoints(sumPoint, customerId)) &&
                    !PointsUsageFlag.ORDER.equals(pointsConfig.getPointsUsageFlag())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050089);
            }
        }
        //商品按店铺分组
        Map<Long, Map<String, List<GoodsInfoDTO>>> skuMap = skuList.stream().collect(
                Collectors.groupingBy(GoodsInfoDTO::getStoreId,
                        Collectors.groupingBy(GoodsInfoDTO::getGoodsInfoId)));
        List<TradeItemGroup> itemGroups = new ArrayList<>();
        skuMap.forEach((key, value) -> {
            //获取商品所属商家，店铺信息
            StoreVO store = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(key)
                    .build())
                    .getContext().getStoreVO();
            Supplier supplier = Supplier.builder()
                    .storeId(store.getStoreId())
                    .storeName(store.getStoreName())
                    .isSelf(store.getCompanyType() == BoolFlag.NO)
                    .supplierCode(store.getCompanyInfo().getCompanyCode())
                    .supplierId(store.getCompanyInfo().getCompanyInfoId())
                    .supplierName(store.getCompanyInfo().getSupplierName())
                    .freightTemplateType(store.getFreightTemplateType())
                    .storeType(store.getStoreType())
                    .build();
            //分组后的商品快照
            List<TradeItem> items = tradeItems.stream().filter(i -> value.containsKey(i.getSkuId()))
                    .collect(Collectors.toList());
            //分组后的营销快照
            List<String> ids = items.stream().map(TradeItem::getSkuId).collect(Collectors.toList());
            List<TradeMarketingDTO> marketings = tradeMarketingList.stream().filter(i -> i.getSkuIds().stream()
                    .anyMatch(ids::contains)).collect(Collectors.toList());

            items.forEach(tradeItem -> {
                List<Long> marketingIds = marketings.stream().filter(i -> i.getSkuIds().contains(tradeItem.getSkuId()))
                        .map(TradeMarketingDTO::getMarketingId).collect(Collectors.toList());
                List<Long> marketingLevelIds = marketings.stream().filter(i -> i.getSkuIds().contains(tradeItem.getSkuId()))
                        .map(TradeMarketingDTO::getMarketingLevelId).collect(Collectors.toList());
                tradeItem.setMarketingIds(marketingIds);
                tradeItem.setMarketingLevelIds(marketingLevelIds);
            });
            TradeGrouponCommitForm grouponForm = null;
            if (Objects.nonNull(request.getOpenGroupon())) {
                // 当为拼团单时，设置拼团form
                grouponForm = new TradeGrouponCommitForm();
                grouponForm.setOpenGroupon(request.getOpenGroupon());
                grouponForm.setGrouponNo(request.getGrouponNo());
                grouponForm.setShareUserId(request.getShareUserId());
            }
            OrderTag orderTag = new OrderTag();
            IteratorUtils.zip(skuList, items,
                    (a, b) ->
                            a.getGoodsInfoId().equals(b.getSkuId())
                    ,
                    (c, d) -> {
                        d.setBuyPoint(c.getBuyPoint());
                        if (NumberUtils.INTEGER_ONE.equals(c.getGoodsType())) {
                            orderTag.setVirtualFlag(Boolean.TRUE);
                        } else if (Constants.TWO == c.getGoodsType()) {
                            orderTag.setElectronicCouponFlag(Boolean.TRUE);
                        }
                    });
            TradeItemGroup tradeItemGroup = new TradeItemGroup();
            tradeItemGroup.setTradeItems(tradeItems);
            tradeItemGroup.setSupplier(supplier);
            tradeItemGroup.setTradeMarketingList(marketings);
            tradeItemGroup.setStoreBagsFlag(request.getStoreBagsFlag());
            tradeItemGroup.setGrouponForm(grouponForm);
            tradeItemGroup.setSuitMarketingFlag(request.getSuitMarketingFlag());
            tradeItemGroup.setOrderTag(orderTag);
            if (StringUtils.isNotBlank(request.getSnapshotType())) {
                tradeItemGroup.setSnapshotType(request.getSnapshotType());

            }
            itemGroups.add(tradeItemGroup);

        });
        //生成快照
        TradeItemSnapshot snapshot = TradeItemSnapshot.builder()
                .id(UUIDUtil.getUUID())
                .buyerId(customerId)
                .itemGroups(itemGroups)
                .snapshotType(request.getSnapshotType())
                .terminalToken(request.getTerminalToken())
                .purchaseBuy(request.getPurchaseBuy())
                .shareUserId(request.getShareUserId())
                .build();
        return snapshot;
    }

    /**
     * 保存订单商品快照
     * @param request    请求参数
     */
    public void fullGiftSnapshot(TradeItemSnapshotGiftRequest request) {
        RLock rLock = redissonClient.getFairLock(RedisKeyConstant.CUSTOMER_TRADE_SNAPSHOT_LOCK_KEY + request.getTerminalToken());
        try {
            if(rLock.tryLock(10, 10, TimeUnit.SECONDS)) {
                //快照生成采用幂等操作
                TradeItemSnapshot tradeItemSnapshot = tradeItemSnapshotService.getTradeItemSnapshot(request.getTerminalToken());

                if (Objects.isNull(tradeItemSnapshot) || CollectionUtils.isEmpty(tradeItemSnapshot.getItemGroups())) {
                    return;
                }
                TradeItemGroup tradeItemGroup = tradeItemSnapshot.getItemGroups()
                        .stream()
                        .filter(group -> group.getSupplier().getStoreId().equals(request.getTradeItems().get(0).getStoreId()))
                        .findFirst()
                        .orElse(null);
                if (Objects.isNull(tradeItemGroup) || CollectionUtils.isEmpty(tradeItemGroup.getTradeMarketingList())) {
                    return;
                }
                //符合以上条件修改快照
                tradeItemGroup.getTradeMarketingList().forEach(m -> {
                    if (m.getMarketingId().equals(request.getTradeMarketingDTO().getMarketingId())
                            && org.apache.commons.collections4.CollectionUtils.isEqualCollection(m.getSkuIds(), request.getTradeMarketingDTO().getSkuIds())) {
                        m.setMarketingLevelId(request.getTradeMarketingDTO().getMarketingLevelId());
                        m.setGiftSkuIds(request.getTradeMarketingDTO().getGiftSkuIds());
                    }
                });
                tradeItemSnapshotService.updateTradeItemSnapshot(tradeItemSnapshot);
            }
        } catch (Exception e) {
            log.error("订单快照操作失败", e);
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 更新订单商品快照中的商品数量
     *
     * @param request 客户id、是否开店礼包
     */
    @GlobalTransactional
    @Transactional
    public TradeItemSnapshot modifyGoodsNum(TradeItemModifyGoodsNumRequest request) {
        String customerId = request.getCustomerId();
        List<GoodsInfoDTO> skuList = request.getSkuList();
        //验证积分商品的积分与会员可用积分
        if (skuList.get(0).getBuyPoint() != null && skuList.get(0).getBuyPoint() > 0) {
            Long sumPoint = request.getNum() * skuList.get(0).getBuyPoint();
            if (sumPoint > 0 && (!verifyService.verifyBuyPoints(sumPoint, customerId))) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050089);
            }
        }
        //快照生成采用幂等操作
        TradeItemSnapshot tradeItemSnapshot = tradeItemSnapshotService.getTradeItemSnapshot(request.getTerminalToken());
        if (Objects.isNull(tradeItemSnapshot) || CollectionUtils.isEmpty(tradeItemSnapshot.getItemGroups())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050075);
        }
        tradeItemSnapshot.getItemGroups().stream()
                .filter(i -> CollectionUtils.isNotEmpty(i.getTradeItems()))
                .forEach(i -> i.getTradeItems().forEach(j -> {
                    if (j.getSkuId().equalsIgnoreCase(request.getSkuId())) {
                        if (Objects.nonNull(j.getIsBookingSaleGoods()) && j.getIsBookingSaleGoods() && j.getBookingType() == BookingType.EARNEST_MONEY) {
                            j.setEarnestPrice(j.getEarnestPrice().divide(BigDecimal.valueOf(j.getNum())).multiply(BigDecimal.valueOf(request.getNum())));
                            j.setSwellPrice(j.getSwellPrice().divide(BigDecimal.valueOf(j.getNum())).multiply(BigDecimal.valueOf(request.getNum())));
                        }
                        j.setNum(request.getNum());
                    }
                }));

        tradeItemSnapshotService.updateTradeItemSnapshot(tradeItemSnapshot);
        return tradeItemSnapshot;
    }

    /**
     * 删除订单商品快照
     *
     * @param terminalToken 用户终端token
     */
    public void remove(String terminalToken) {
        tradeItemSnapshotService.deleteTradeItemSnapshot(terminalToken);
    }


    /**
     * 计算平摊价
     *
     * @param tradeItems 订单商品
     * @param newTotal   新的总价
     * @param preferentialTradeItem   换购商品
     */
    public void clacSplitPrice(List<TradeItem> tradeItems, BigDecimal newTotal, List<TradeItem> preferentialTradeItem) {
        preferentialTradeItem = ObjectUtils.defaultIfNull(preferentialTradeItem, new ArrayList<TradeItem>());
        // 1.如果新的总价为0或空，设置所有商品均摊价为0
        if (newTotal == null || BigDecimal.ZERO.compareTo(newTotal) == 0) {
            tradeItems.forEach(tradeItem -> tradeItem.setSplitPrice(BigDecimal.ZERO));
            preferentialTradeItem.forEach(tradeItem -> tradeItem.setSplitPrice(BigDecimal.ZERO));
            return;
        }

        // 2.计算商品旧的总价
        BigDecimal total = this.calcSkusTotalPrice(tradeItems);
        total = total.add(this.calcSkusTotalPrice(preferentialTradeItem));

        // 3.计算商品均摊价
        this.calcSplitPrice(tradeItems, newTotal, total, preferentialTradeItem);
    }

    /**
     * 计算每个商品的均摊价以及此商品参加的营销对应的营销优惠价
     *
     * @param trade
     */
    public void clacSplitPriceAndMarketingSettlements(Trade trade){
        trade.getTradeMarketings().stream().forEach(i -> {
            List<TradeItem> items = trade.getTradeItems().stream().filter(t -> i.getSkuIds().contains(t.getSkuId()))
                    .collect(Collectors.toList());
            if (i.getMarketingType() == MarketingType.GIFT){
                items.forEach(t -> {
                    List<TradeItem.MarketingSettlement> settlements = new ArrayList<>();
                    settlements.add(TradeItem.MarketingSettlement.builder().marketingType(i.getMarketingType())
                            .marketingId(i.getMarketingId())
                            .splitPrice(t.getSplitPrice()).discountsAmount(BigDecimal.ZERO).build());
                    t.setMarketingSettlements(settlements);
                });
            }else{
                BigDecimal newTotal = i.getRealPayAmount();
                Map<String, BigDecimal> skuOldSplitPrice = items.stream().collect(Collectors.toMap(TradeItem::getSkuId,TradeItem::getSplitPrice));
                // 2.计算商品旧的总价
                BigDecimal total = this.calcSkusTotalPrice(items);
                if (newTotal == null || BigDecimal.ZERO.compareTo(newTotal) == 0) {
                    items.forEach(tradeItem -> tradeItem.setSplitPrice(BigDecimal.ZERO));
                }else{
                    // 3.计算商品均摊价
                    this.calcSplitPrice(items, newTotal, total, null);
                }
                items.forEach(t -> {
                    List<TradeItem.MarketingSettlement> settlements = new ArrayList<>();
                    settlements.add(TradeItem.MarketingSettlement.builder().marketingType(i.getMarketingType())
                            .marketingId(i.getMarketingId())
                            .splitPrice(t.getSplitPrice()).discountsAmount(skuOldSplitPrice.get(t.getSkuId()).subtract(t.getSplitPrice())).build());
                    t.setMarketingSettlements(settlements);
                });
            }

        });
    }

    /**
     * 计算商品集合的均摊总价
     */
    public BigDecimal calcSkusTotalPrice(List<TradeItem> tradeItems) {
        if (CollectionUtils.isNotEmpty(tradeItems) && Objects.nonNull(tradeItems.get(0).getIsBookingSaleGoods())
                && tradeItems.get(0).getIsBookingSaleGoods()) {
            TradeItem tradeItem = tradeItems.get(0);
            if (tradeItem.getBookingType() == BookingType.EARNEST_MONEY && Objects.nonNull(tradeItem.getTailPrice())) {
                return tradeItem.getTailPrice();
            }
        }
        return tradeItems.stream()
                .filter(tradeItem -> tradeItem.getSplitPrice() != null && tradeItem.getSplitPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(TradeItem::getSplitPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * 计算商品集合的积分抵扣均摊总价
     */
    BigDecimal calcSkusTotalPointsPrice(List<TradeItem> tradeItems) {
        return tradeItems.stream()
                .filter(tradeItem -> tradeItem.getPointsPrice() != null && tradeItem.getPointsPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(TradeItem::getPointsPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * 计算商品集合的积分抵扣均摊总数
     */
    Long calcSkusTotalPoints(List<TradeItem> tradeItems) {
        return tradeItems.stream()
                .filter(tradeItem -> tradeItem.getPoints() != null && tradeItem.getPoints() > 0)
                .map(TradeItem::getPoints)
                .reduce(0L, (a, b) -> a + b);
    }

    /**
     * 计算商品均摊价
     *
     * @param tradeItems 待计算的商品列表
     * @param newTotal   新的总价
     * @param total      旧的商品总价
     */
    public void calcSplitPrice(List<TradeItem> tradeItems, BigDecimal newTotal, BigDecimal total, List<TradeItem> preferentialTradeItems) {
        preferentialTradeItems = ObjectUtils.defaultIfNull(preferentialTradeItems, new ArrayList<TradeItem>());
        //内部总价为零或相等不用修改
        if (total.compareTo(newTotal) == 0) {
            return;
        }
        // 尾款情况重新计算实际总价
        if (CollectionUtils.isNotEmpty(tradeItems)) {
            TradeItem tradeItem = tradeItems.get(0);
            if (Objects.nonNull(tradeItem.getIsBookingSaleGoods()) && tradeItem.getIsBookingSaleGoods() && tradeItem.getBookingType() == BookingType.EARNEST_MONEY
                    && total.compareTo(tradeItem.getTailPrice()) == 0) {
                newTotal = tradeItem.getEarnestPrice().add(newTotal);
                total = tradeItem.getEarnestPrice().add(total);
            }
        }

        int size = tradeItems.size();
        BigDecimal splitPriceTotal = BigDecimal.ZERO;//累积平摊价，将剩余扣给最后一个元素
        Long totalNum = tradeItems.stream().map(TradeItem::getNum).reduce(0L, Long::sum);

        for (TradeItem tradeItem : preferentialTradeItems){
            BigDecimal splitPrice =
                    tradeItem.getSplitPrice() != null
                            ? tradeItem.getSplitPrice()
                            : BigDecimal.ZERO;
            // 全是零元商品按数量均摊
            if (BigDecimal.ZERO.compareTo(total) == 0) {
                tradeItem.setSplitPrice(
                        newTotal.multiply(BigDecimal.valueOf(tradeItem.getNum()))
                                .divide(
                                        BigDecimal.valueOf(totalNum),
                                        2,
                                        RoundingMode.HALF_UP));
            } else {
                BigDecimal newSplitPrice = splitPrice
                        .divide(total, 10, RoundingMode.DOWN)
                        .multiply(newTotal)
                        .setScale(2, RoundingMode.HALF_UP);
                tradeItem.setSplitPrice(newSplitPrice);
            }
            splitPriceTotal = splitPriceTotal.add(tradeItem.getSplitPrice());
        }

        for (int i = 0; i < size; i++) {
            TradeItem tradeItem = tradeItems.get(i);
            if (i == size - 1) {
                tradeItem.setSplitPrice(newTotal.subtract(splitPriceTotal));
            } else {
                BigDecimal splitPrice = tradeItem.getSplitPrice() != null ? tradeItem.getSplitPrice() : BigDecimal.ZERO;
                //全是零元商品按数量均摊
                if (BigDecimal.ZERO.compareTo(total) == 0) {
                    tradeItem.setSplitPrice(
                            newTotal.multiply(BigDecimal.valueOf(tradeItem.getNum()))
                                    .divide(BigDecimal.valueOf(totalNum), 2, RoundingMode.HALF_UP));
                } else {
                    tradeItem.setSplitPrice(
                            splitPrice
                                    .divide(total, 10, RoundingMode.DOWN)
                                    .multiply(newTotal)
                                    .setScale(2, RoundingMode.HALF_UP));
                }
                splitPriceTotal = splitPriceTotal.add(tradeItem.getSplitPrice());
            }
        }
    }

    /**
     * 计算积分抵扣均摊价、均摊数量
     *
     * @param tradeItems       待计算的商品列表
     * @param pointsPriceTotal 积分抵扣总额
     * @param pointsTotal      积分抵扣总数
     */
    void calcPoints(List<TradeItem> tradeItems, BigDecimal pointsPriceTotal, Long pointsTotal, BigDecimal pointWorth) {
        BigDecimal totalPrice = tradeItems.stream()
                .filter(tradeItem -> tradeItem.getSplitPrice() != null && tradeItem.getSplitPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(TradeItem::getSplitPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        int size = tradeItems.size();
        //累积积分平摊价，将剩余扣给最后一个元素
        BigDecimal splitPriceTotal = BigDecimal.ZERO;
        //累积积分数量，将剩余扣给最后一个元素
        Long splitPointsTotal = 0L;

        for (int i = 0; i < size; i++) {
            TradeItem tradeItem = tradeItems.get(i);
            if (i == size - 1) {
                tradeItem.setPointsPrice(pointsPriceTotal.subtract(splitPriceTotal));
                tradeItem.setPoints(pointsTotal - splitPointsTotal);
            } else {
                BigDecimal splitPrice = tradeItem.getSplitPrice() != null ? tradeItem.getSplitPrice() : BigDecimal.ZERO;
                tradeItem.setPointsPrice(
                        splitPrice
                                .divide(totalPrice, 10, RoundingMode.DOWN)
                                .multiply(pointsPriceTotal)
                                .setScale(2, RoundingMode.HALF_UP));
                splitPriceTotal = splitPriceTotal.add(tradeItem.getPointsPrice());

                tradeItem.setPoints(tradeItem.getPointsPrice().multiply(pointWorth).longValue());
                splitPointsTotal = splitPointsTotal + tradeItem.getPoints();
            }
        }
    }


    /**
     * 确认结算
     * @param request
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void confirmSettlement(TradeItemConfirmSettlementRequest request){
        // 验证采购单
        List<String> skuIds = request.getSkuIds();
        String customerId = request.getCustomerId();
        String inviteeId = request.getInviteeId();
        Boolean isO2O = Nutils.defaultVal(request.getIsO2O(), false);
        List<Purchase> purchaseList = purchaseService.queryPurchase(customerId,skuIds,inviteeId,isO2O);

        if (CollectionUtils.isEmpty(purchaseList)) {
            throw new SbcRuntimeException(skuIds, OrderErrorCodeEnum.K050067);
        }
        List<String> existIds = purchaseList.stream().map(Purchase::getGoodsInfoId).collect(Collectors.toList());
        List<String> errorSkuIds = skuIds.stream().filter(skuId -> !existIds.contains(skuId)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorSkuIds)) {
            throw new SbcRuntimeException(errorSkuIds, OrderErrorCodeEnum.K050067);
        }

        //验证用户
        CustomerSimplifyOrderCommitVO customer = verifyService.simplifyById(customerId);

        GoodsInfoResponse response = tradeGoodsService.getGoodsResponse(skuIds, customer, request.getStoreId());
        List<GoodsInfoVO> goodsInfoVOList = response.getGoodsInfos();


        ChannelType channelType = request.getChannelType();
        DistributeChannel distributeChannel = request.getDistributeChannel();
        //根据开关重新设置分销商品标识
        tradeGoodsService.checkDistributionSwitch(goodsInfoVOList,channelType);
        //社交分销业务
        Purchase4DistributionSimplifyRequest purchase4DistributionRequest = new Purchase4DistributionSimplifyRequest();
        purchase4DistributionRequest.setGoodsInfos(response.getGoodsInfos());
        purchase4DistributionRequest.setGoodsIntervalPrices(response.getGoodsIntervalPrices());
        purchase4DistributionRequest.setCustomer(customer);
        purchase4DistributionRequest.setDistributeChannel(distributeChannel);
        Purchase4DistributionResponse purchase4DistributionResponse = tradeGoodsService.distribution
                (purchase4DistributionRequest);
        response.setGoodsInfos(purchase4DistributionResponse.getGoodsInfos());
        response.setGoodsIntervalPrices(purchase4DistributionResponse.getGoodsIntervalPrices());
        //验证分销商品状态
        tradeGoodsService.validShopGoods(purchase4DistributionResponse.getGoodsInfos());

        //商品验证

        List<TradeItem> verifyGoods = verifyService.verifyGoods(
                KsBeanUtil.convertList(request.getTradeItems(),TradeItem.class), Collections.emptyList(),
                KsBeanUtil.convert(response, TradeGoodsListVO.class), request.getStoreId(), false,null);
        verifyGoods.forEach(req -> request.getTradeItems().forEach(tra -> {
            if (tra.getSkuId().equals(req.getSkuId())) {
                tra.setPluginType(req.getPluginType());
            }
        }));


        List<TradeItem>  tradeItems = KsBeanUtil.convert(request.getTradeItems(),TradeItem.class);



        verifyService.verifyGoods(tradeItems, Collections.emptyList(), KsBeanUtil.convert(response, TradeGoodsListVO.class), request.getStoreId(),false,request.getAreaId());

        // 根据是否O2O判断StoreId来源,需要使用self调用使AOP生效
        List<Long> storeIds = _self.getTradeStoreIds(response.getGoodsInfos(), request.getStoreId(), isO2O);
        verifyService.verifyStore(storeIds);

        Map<String,GoodsInfoVO> goodsInfoVOMap = goodsInfoVOList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        tradeItems.stream().forEach(tradeItem -> {
            if(!goodsInfoVOMap.containsKey(tradeItem.getSkuId())) {
                throw new SbcRuntimeException(Arrays.asList(tradeItem.getSkuId()), OrderErrorCodeEnum.K050027);
            }
            tradeItem.setBuyPoint(goodsInfoVOMap.get(tradeItem.getSkuId()).getBuyPoint());
            tradeItem.setStoreId(goodsInfoVOMap.get(tradeItem.getSkuId()).getStoreId());
        });
        List<TradeMarketingDTO> tradeMarketingList = request.getTradeMarketingList();
        verifyServiceInterface.verifyTradeMarketing(request.getTradeMarketingList(),
                Collections.emptyList(), tradeItems, customerId,
                request.isForceConfirm(), isO2O ? PluginType.O2O : PluginType.NORMAL, request.getStoreId());

        tradeItems.stream().forEach(tradeItem -> {
            tradeMarketingList.stream().forEach(tradeMarketingDTO -> {
                        if (tradeMarketingDTO.getSkuIds().contains(tradeItem.getSkuId()))
                        {
                            tradeItem.getMarketingIds().add(tradeMarketingDTO.getMarketingId());
                            tradeItem.getMarketingLevelIds().add(tradeMarketingDTO.getMarketingLevelId());
                        }
                    }
            );
        });

        // 校验商品限售信息
        TradeItemGroup tradeItemGroupVOS = new TradeItemGroup();
        tradeItemGroupVOS.setTradeItems(tradeItems);
        if(isO2O) {
            Supplier supplier = new Supplier();
            supplier.setStoreType(StoreType.O2O);
            supplier.setStoreId(request.getStoreId());
            tradeItemGroupVOS.setSupplier(supplier);
        }
        tradeGoodsService.validateRestrictedGoods(tradeItemGroupVOS, customer, request.getAddressId());

        //普通商品不能参与预售预约活动
        List<String> skuIdList = tradeItems.stream()
                .filter(i ->
                        (!Boolean.TRUE.equals(i.getIsBookingSaleGoods()))
                                && (!Boolean.TRUE.equals(i.getIsAppointmentSaleGoods()))
                                && (Objects.isNull(i.getBuyPoint()) || i.getBuyPoint() == 0))
                .map(TradeItem::getSkuId).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(skuIdList)){
            appointmentSaleQueryProvider.containAppointmentSaleAndBookingSale(AppointmentSaleInProgressRequest.builder().goodsInfoIdList(skuIdList).build());
        }

        // 预约活动校验是否有资格
        List<TradeItem> excludeBuyPointList = tradeItems.stream()
                .filter(tradeItem -> (Objects.isNull(tradeItem.getBuyPoint()) || tradeItem.getBuyPoint() == 0)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(excludeBuyPointList)) {
            tradeItemGroupVOS.setTradeItems(excludeBuyPointList);
            tradeGoodsService.validateAppointmentQualification(Collections.singletonList(tradeItemGroupVOS), customerId);
        }


        /**
         *  校验预售活动资格，初始化价格
         */
        tradeItems = tradeGoodsService.fillActivityPrice(tradeItems, goodsInfoVOList,customerId);

        boolean pointGoodsFlag = false;
        if (EnableStatus.ENABLE.equals(systemPointsConfigQueryProvider.querySystemPointsConfig().getContext().getStatus())) {
            if(tradeItems.stream().anyMatch(t -> Objects.nonNull(t.getBuyPoint()) && t.getBuyPoint() > 0)){
                pointGoodsFlag = true;
            }
        }
        if(isO2O) {
            response.setGoodsInfos(response.getGoodsInfos().parallelStream()
                    .peek(goodsInfoVO ->
                        goodsInfoVO.setStoreId(request.storeId))
                    .collect(Collectors.toList()));
            tradeItems = tradeItems.parallelStream().map(tradeItem -> {
                tradeItem.setStoreId(request.storeId);
                tradeItem.setPluginType(PluginType.O2O);
                return stockService.getFreightTempId(tradeItem);
            }).collect(Collectors.toList());
        }
        List<TradeItemDTO> tradeItemDTOs = KsBeanUtil.convert(tradeItems,TradeItemDTO.class);
        TradeItemSnapshotRequest snapshotRequest =  TradeItemSnapshotRequest.builder().customerId(customerId).pointGoodsFlag(pointGoodsFlag)
                .tradeItems(tradeItemDTOs)
                .tradeMarketingList(request.getTradeMarketingList())
                .skuList(KsBeanUtil.convertList(response.getGoodsInfos(), GoodsInfoDTO.class))
                .terminalToken(request.getTerminalToken())
                .build();

        this.snapshot(snapshotRequest, tradeItems,
                request.getTradeMarketingList(), KsBeanUtil.convertList(response.getGoodsInfos(), GoodsInfoDTO.class));
    }

    /**
     * 确认结算
     * @param request
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void confirmPlugin(TradeBuyRequest request){
        TradeConfirmInterface tradeConfirmService = SpringContextHolder.getBean(request.getBuyType().getBuyType());
        TradeConfirmParam confirmParam = TradeConfirmParam.builder().tradeBuyRequest(request).build();
        // 获取参数
        tradeConfirmService.getParams(confirmParam);
        // 校验
        tradeConfirmService.check(confirmParam);
        // 组装订单
        tradeConfirmService.assembleTrade(confirmParam);
        // 填充区间价和商品库存
        tradeConfirmService.fillPriceAndStock(confirmParam);
        // 处理营销
        tradeConfirmService.calcMarketing(confirmParam);
        // 计算订单价格
        tradeConfirmService.calcPrice(confirmParam);
        int num = 0;
        for (TradeItemGroup tradeConfirmItem :  confirmParam.getTradeItemSnapshot().getItemGroups()) {
            List<TradeItem> tradeItems = tradeConfirmItem.getTradeItems();
            for (TradeItem tradeItem : tradeItems) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (tradeItem.getSkuNo().length()>10){
                        num = Math.toIntExact(num + (tradeItem.getNum() * 2));
                    }else {
                        num = Math.toIntExact(num + (tradeItem.getNum()));
                    }
                }
            }
        }
        if(num>0){
            //获取当前年订单内校服件数
            int schoolGoodsNumByOrder = getSchoolGoodsNumByOrder2(request.getCustomerId());
            //限售
            replacePriceToLine2(confirmParam.getTradeItemSnapshot(), schoolGoodsNumByOrder,num);
        }
        // 生成快照
        tradeConfirmService.snapshot(confirmParam);
    }

    /**
     * 拼团订单提交
     * @param request
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void groupBuySnapshot(TradeBuyRequest request){
        TradeConfirmInterface tradeGroupBuyService = SpringContextHolder.getBean(request.getBuyType().getBuyType());
        TradeConfirmParam confirmParam = TradeConfirmParam.builder().tradeBuyRequest(request).build();
        // 获取参数
        tradeGroupBuyService.getParams(confirmParam);
        // 校验
        tradeGroupBuyService.check(confirmParam);
        // 组装订单
        tradeGroupBuyService.assembleTrade(confirmParam);
        // 填充区间价和商品库存
        tradeGroupBuyService.fillPriceAndStock(confirmParam);
        // 处理营销
        tradeGroupBuyService.calcMarketing(confirmParam);
        // 计算订单价格
        tradeGroupBuyService.calcPrice(confirmParam);
        // 生成快照
        tradeGroupBuyService.snapshot(confirmParam);
    }

    /**
     * 查询订单快照
     * @param request
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public TradeConfirmResponse findTradeSnapshot(FindTradeSnapshotRequest request){
        TradeConfirmParam confirmParam = TradeConfirmParam.builder().findTradeSnapshotRequest(request).build();
        // 获取参数
        tradePurchaseService.getParams(confirmParam);
        // 校验
        tradePurchaseService.check(confirmParam);
        // 组装订单
        tradePurchaseService.assembleTrade(confirmParam);
        // 填充区间价和商品库存
        tradePurchaseService.fillPriceAndStock(confirmParam);
        // 处理营销
        tradePurchaseService.calcMarketing(confirmParam);
        // 计算订单价格
        tradePurchaseService.calcPrice(confirmParam);
        // 返回快照
        tradePurchaseService.snapshot(confirmParam);
        TradeConfirmResponse tradeConfirmResponse = confirmParam.getTradeConfirmResponse();
        tradeConfirmResponse.setBargain(confirmParam.getTradeItemSnapshot().getBargain());
        tradeConfirmResponse.setBargainId(confirmParam.getTradeItemSnapshot().getBargainId());

        return tradeConfirmResponse;
    }
    void replacePriceToLine(TradeConfirmResponse tradeConfirmResponse,int schoolGoodsNumByOrder,int num){
        List<TradeConfirmItemVO> tradeConfirmItems = tradeConfirmResponse.getTradeConfirmItems();
        boolean flag = (num + schoolGoodsNumByOrder) > 24;
//        tradeConfirmResponse.setSchoolNum(num + schoolGoodsNumByOrder);
        if (!flag){
            return;
        }
        for (TradeConfirmItemVO tradeConfirmItem : tradeConfirmItems) {
            List<TradeItemVO> tradeItems = tradeConfirmItem.getTradeItems();
            BigDecimal splitPrices = new BigDecimal(0);
            BigDecimal prices = new BigDecimal(0);
            BigDecimal originalPrices = new BigDecimal(0);
            for (TradeItemVO tradeItem : tradeItems) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (flag){
                        GoodsInfoViewByIdRequest goodsInfoViewByIdRequest = new GoodsInfoViewByIdRequest();
                        goodsInfoViewByIdRequest.setGoodsInfoId(tradeItem.getSkuId());
                        BaseResponse<GoodsInfoViewByIdResponse> viewById = goodsInfoQueryProvider.getViewById(goodsInfoViewByIdRequest);
                        GoodsInfoViewByIdResponse context = viewById.getContext();
                        tradeItem.setSplitPrice(context.getGoodsInfo().getLinePrice().multiply(BigDecimal.valueOf(tradeItem.getNum())).setScale(2,RoundingMode.HALF_UP));
                        tradeItem.setPrice(context.getGoodsInfo().getLinePrice());
                        tradeItem.setLevelPrice(context.getGoodsInfo().getLinePrice());
                        tradeItem.setOriginalPrice(context.getGoodsInfo().getLinePrice());
                    }
                }
                splitPrices = splitPrices.add(tradeItem.getSplitPrice());
                prices = prices.add(tradeItem.getPrice());
                originalPrices = originalPrices.add(tradeItem.getOriginalPrice());
            }
            tradeConfirmItem.getTradePrice().setGoodsPrice(splitPrices);
            tradeConfirmItem.getTradePrice().setOriginPrice(splitPrices);
            tradeConfirmItem.getTradePrice().setTotalPrice(splitPrices);
        }
    }
    int getSchoolGoodsNumByOrder(String customerId){
        TradeQueryRequest request = new TradeQueryRequest();
        request.setBuyerId(customerId);
        LocalDate localDate = DateUtil.firstDayOfYear();
        String format = DateUtil.format(localDate, "yyyy-MM-dd");
//        format=format+" 00:00:00";
        List<FlowState> notFlowStates = new ArrayList<>();
        notFlowStates.add(FlowState.REFUND);
        notFlowStates.add(FlowState.VOID);
        notFlowStates.add(FlowState.CANCEL_DELIVERED);
        request.setNotFlowStates(notFlowStates);
        request.setBeginTime(format);
        TradeState tradeState = new TradeState();
        tradeState.setPayState(PayState.PAID);
        request.setTradeState(tradeState);
        Criteria whereCriteria = request.getWhereCriteria();
        Query query = new Query(whereCriteria);
        List<Trade> trades = mongoTemplate.find(query, Trade.class);
        int num = 0;
        for (Trade trade : trades) {
            List<TradeItem> tradeItems = trade.getTradeItems();
            for (TradeItem tradeItem : tradeItems) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (tradeItem.getSkuNo().length()>10){
                        num = Math.toIntExact(num + (tradeItem.getNum() * 2));
                    }else {
                        num = Math.toIntExact(num + (tradeItem.getNum()));
                    }
                }
            }
        }
        return num;
    }
    void replacePriceToLine2(TradeItemSnapshot tradeConfirmResponse,int schoolGoodsNumByOrder,int num){
        List<TradeItemGroup> tradeConfirmItems = tradeConfirmResponse.getItemGroups();
        boolean flag = (num + schoolGoodsNumByOrder) > 24;
//        tradeConfirmResponse.setSchoolNum(num + schoolGoodsNumByOrder);
        if (!flag){
            return;
        }
        for (TradeItemGroup tradeConfirmItem : tradeConfirmItems) {
            List<TradeItem> tradeItems = tradeConfirmItem.getTradeItems();
            BigDecimal splitPrices = new BigDecimal(0);
            BigDecimal prices = new BigDecimal(0);
            BigDecimal originalPrices = new BigDecimal(0);
            for (TradeItem tradeItem : tradeItems) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (flag){
                        GoodsInfoViewByIdRequest goodsInfoViewByIdRequest = new GoodsInfoViewByIdRequest();
                        goodsInfoViewByIdRequest.setGoodsInfoId(tradeItem.getSkuId());
                        BaseResponse<GoodsInfoViewByIdResponse> viewById = goodsInfoQueryProvider.getViewById(goodsInfoViewByIdRequest);
                        GoodsInfoViewByIdResponse context = viewById.getContext();
                        tradeItem.setSplitPrice(context.getGoodsInfo().getLinePrice().multiply(BigDecimal.valueOf(tradeItem.getNum())).setScale(2,RoundingMode.HALF_UP));
                        tradeItem.setPrice(context.getGoodsInfo().getLinePrice());
                        tradeItem.setLevelPrice(context.getGoodsInfo().getLinePrice());
                        tradeItem.setOriginalPrice(context.getGoodsInfo().getLinePrice());
                    }
                }
                splitPrices = splitPrices.add(tradeItem.getSplitPrice());
                prices = prices.add(tradeItem.getPrice());
                originalPrices = originalPrices.add(tradeItem.getOriginalPrice());
            }
//            tradeConfirmItem.getTradePrice().setGoodsPrice(splitPrices);
//            tradeConfirmItem.getTradePrice().setOriginPrice(splitPrices);
//            tradeConfirmItem.getTradePrice().setTotalPrice(splitPrices);
        }
    }
    int getSchoolGoodsNumByOrder2(String customerId){
        TradeQueryRequest request = new TradeQueryRequest();
        request.setBuyerId(customerId);
        LocalDate localDate = DateUtil.firstDayOfYear();
        String format = DateUtil.format(localDate, "yyyy-MM-dd");
//        format=format+" 00:00:00";
        List<FlowState> notFlowStates = new ArrayList<>();
        notFlowStates.add(FlowState.REFUND);
        notFlowStates.add(FlowState.VOID);
        notFlowStates.add(FlowState.CANCEL_DELIVERED);
        request.setNotFlowStates(notFlowStates);
        request.setBeginTime(format);
        TradeState tradeState = new TradeState();
        tradeState.setPayState(PayState.PAID);
        request.setTradeState(tradeState);
        Criteria whereCriteria = request.getWhereCriteria();
        Query query = new Query(whereCriteria);
        List<Trade> trades = mongoTemplate.find(query, Trade.class);
        int num = 0;
        for (Trade trade : trades) {
            List<TradeItem> tradeItems = trade.getTradeItems();
            for (TradeItem tradeItem : tradeItems) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (tradeItem.getSkuNo().length()>10){
                        num = Math.toIntExact(num + (tradeItem.getNum() * 2));
                    }else {
                        num = Math.toIntExact(num + (tradeItem.getNum()));
                    }
                }
            }
        }
        return num;
    }
    /***
     * 获得订单关联店铺/门店ID
     * SBC从商品中聚合；O2O插件中直接从StoreId中获取
     * @param goodsInfos    商品集合
     * @param storeId       O2O模式下前端传参
     * @param isO2O         是否O2O模式，不可删除
     * @return
     */
    public List<Long> getTradeStoreIds(List<GoodsInfoVO> goodsInfos, Long storeId, Boolean isO2O){
        return goodsInfos.stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList());
    }
}
