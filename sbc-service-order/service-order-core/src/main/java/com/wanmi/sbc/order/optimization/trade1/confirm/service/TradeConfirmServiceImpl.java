package com.wanmi.sbc.order.optimization.trade1.confirm.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.marketing.bean.constant.MarketingPluginConstant;
import com.wanmi.sbc.marketing.bean.dto.CountPriceDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemMarketingVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmInterface;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.optimization.trade1.confirm.service.query.TradeConfirmQueryService;
import com.wanmi.sbc.order.purchase.Purchase;
import com.wanmi.sbc.order.purchase.PurchaseService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import com.wanmi.sbc.order.trade.service.VerifyService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liguang
 * @description 订单确认页去结算处理
 * @date 2022/2/24 13:54
 */
@Service(value = "confirmBuy")
@Slf4j
public class TradeConfirmServiceImpl implements TradeConfirmInterface {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private VerifyService verifyService;
    @Autowired
    private TradeConfirmQueryService tradeConfirmQueryService;
    @Autowired
    private DistributionCacheService distributionCacheService;
    @Autowired
    private TradeItemSnapshotService tradeItemSnapshotService;

    /**
     * 获取参数
     */
    @Override
    public void getParams(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        List<TradeItemRequest> tradeItems = request.getTradeItemRequests();
        // 获取会员信息
        CustomerVO customerVO = tradeConfirmQueryService.getCustomer(request.getCustomerId());
        param.setCustomerVO(customerVO);
        // 获取skuIds storeIds
        param.setSkuIds(tradeItems.stream().map(TradeItemRequest::getSkuId).collect(Collectors.toList()));

        // 获取采购单
        List<Purchase> purchaseList = purchaseService.queryPurchase(request.getCustomerId(), param.getSkuIds(), request.getDistributeChannel().getInviteeId(), Boolean.FALSE);
        if (CollectionUtils.isEmpty(purchaseList)) {
            throw new SbcRuntimeException(param.getSkuIds(), OrderErrorCodeEnum.K050067);
        }

        // 是否是分销
        param.setIsDistributFlag(this.tradeConfirmQueryService.querySystemDistributionConfig() && !ChannelType.PC_MALL.equals(request.getDistributeChannel().getChannelType()));

        // 获取第三方渠道开关
        this.tradeConfirmQueryService.getChanelFlag(param);

        // 获取商品信息
        TradeConfirmGoodsRequest goodsRequest = TradeConfirmGoodsRequest.builder()
                .skuIds(param.getSkuIds())
                .isHavSpecText(Boolean.TRUE)
                .isHavIntervalPrice(Boolean.TRUE)
                .isHavRedisStock(Boolean.TRUE)
                .build();
        this.tradeConfirmQueryService.getGoodsInfos(param, goodsRequest);

        // 获取店铺信息
        param.setStoreIds(param.getGoodses().stream().map(GoodsVO::getStoreId).distinct().collect(Collectors.toList()));
        param.setStores(tradeConfirmQueryService.getStoreByStoreIds(param.getStoreIds()));
        // 校验店铺状态
        if (!verifyService.checkStore(param.getStores())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }
        // 获取店铺是否开启分销开关
        param.setStoreOpenFlag(this.distributionCacheService.queryStoreListOpenFlag(param.getStoreIds()));
        // 系统积分开关
        param.setSystemPointFlag(this.tradeConfirmQueryService.querySystemPointConfig());
        // 判断分销开关，如果系统分销或者店铺分销开关开启设置商品为普通商品
        this.tradeConfirmQueryService.checkDistributFlag(param, false, false);
        // 获取营销信息
        param.setMarketingMap(this.tradeConfirmQueryService.getMarketing(param.getGoodsInfos(), request.getCustomerId(), request.getIepCustomerFlag(), Boolean.FALSE));
        // 获取购物车中商品选中的营销
        param.setGoodsMarketingMap(this.tradeConfirmQueryService.getGoodsMarketing(param.getSkuIds(), request.getCustomerId()));
        // 获取预售商品
        param.setBookingSaleVOS(this.tradeConfirmQueryService.queryBookingSales(param));
    }

    /**
     * 参数校验
     *
     * @return
     */
    @Override
    public void check(TradeConfirmParam param) {
        // 收货地址校验
        this.tradeConfirmQueryService.checkAddress(param);
        // 商品校验
        this.tradeConfirmQueryService.checkGoodsStock(param);
        // 周期购校验
        this.tradeConfirmQueryService.checkBuyCycle(param);
        // 渠道校验
        this.tradeConfirmQueryService.checkChanelGoods(param);
    }

    @Override
    public void assembleTrade(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        Map<String, TradeItemRequest> tradeItemRequestMap = request.getTradeItemRequests().stream().collect(Collectors.toMap(TradeItemRequest::getSkuId, Function.identity()));
        Map<Long, List<GoodsInfoVO>> goodsInfosMap = param.getGoodsInfos().stream().collect(Collectors.groupingBy(GoodsInfoVO::getStoreId));
        Map<Long, Map<String, GoodsVO>> goodsMap = param.getGoodses().stream().collect(Collectors.groupingBy(GoodsVO::getStoreId, Collectors.toMap(GoodsVO::getGoodsId, Function.identity())));
        Map<String, String> specDetailMap = param.getGoodsInfoSpecDetailRelVOList()
                .stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));
        List<TradeItemGroup> itemGroups = Lists.newArrayList();
        List<StoreVO> stores = param.getStores();
        for (StoreVO store : stores) {
            TradeItemGroup itemGroup = new TradeItemGroup();
            // 店铺信息
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
            itemGroup.setSupplier(supplier);

            List<GoodsInfoVO> storeGoodsInfos = goodsInfosMap.getOrDefault(store.getStoreId(), Lists.newArrayList());
            // 填充 tradeItem 信息
            List<TradeItem> tradeItems = storeGoodsInfos.stream()
                    .map(goodsInfo -> {
                        TradeItem tradeItem = new TradeItem();
                        tradeItem.setMarketingIds(Lists.newArrayList());
                        tradeItem.setMarketingLevelIds(Lists.newArrayList());
                        GoodsVO goodsVO = goodsMap.get(store.getStoreId()).get(goodsInfo.getGoodsId());
                        TradeItemRequest tradeItemRequest = tradeItemRequestMap.get(goodsInfo.getGoodsInfoId());
                        this.tradeConfirmQueryService.fillBaseGoodsInfo(goodsInfo, goodsVO, specDetailMap);
                        // 补充基本信息
                        this.tradeConfirmQueryService.fillBaseTradeItem(tradeItem, goodsInfo, tradeItemRequest, request.getBuyType());
                        // 填充价格
                        this.tradeConfirmQueryService.fillTradeItemPrice(tradeItem, goodsInfo, goodsVO, param.getGoodsIntervalPriceVOList());
                        return tradeItem;
                    }).collect(Collectors.toList());
            itemGroup.setTradeItems(tradeItems);
            itemGroup.setPluginType(PluginType.NORMAL);
            itemGroup.setStoreBagsFlag(DefaultFlag.NO);
            itemGroup.setSuitMarketingFlag(Boolean.FALSE);
            itemGroup.setTradeMarketingList(Lists.newArrayList());
            // 订单标签
            boolean virtualFlag = storeGoodsInfos.stream().anyMatch(goodsInfoVO -> NumberUtils.INTEGER_ONE.equals(goodsInfoVO.getGoodsType()));
            boolean electronicCouponFlag = storeGoodsInfos.stream().anyMatch(goodsInfoVO -> Constants.TWO == (goodsInfoVO.getGoodsType()));
            OrderTag orderTag = OrderTag.builder().virtualFlag(virtualFlag).electronicCouponFlag(electronicCouponFlag).build();
            itemGroup.setOrderTag(orderTag);
            itemGroups.add(itemGroup);
        }

        param.setTradeItemMap(itemGroups.stream().map(TradeItemGroup::getTradeItems).flatMap(Collection::stream).collect(Collectors.toMap(TradeItem::getSkuId, Function.identity(), (v1, v2) -> v1)));

        TradeItemSnapshot tradeItemSnapshot = TradeItemSnapshot.builder()
                .id(UUIDUtil.getUUID())
                .buyerId(request.getCustomerId())
                .itemGroups(itemGroups)
                .terminalToken(request.getTerminalToken())
                .purchaseBuy(Boolean.TRUE)
                .build();
        param.setTradeItemSnapshot(tradeItemSnapshot);
    }


    @Override
    public void fillPriceAndStock(TradeConfirmParam param) {

    }

    @Override
    public void calcMarketing(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        // 请求营销信息 <skuId, 营销信息>
        Map<String, List<MarketingPluginLabelDetailVO>> marketingMap = param.getMarketingMap();
        // 购物车中默认选中的营销，即 去结算接口中没有传营销的话 使用这个营销
        Map<String, Long> goodsMarketingMap = param.getGoodsMarketingMap();
        // 商品没有营销不进行计算
        if (MapUtils.isEmpty(marketingMap)) {
            return;
        }
        // 页面已选择的营销
        List<TradeMarketingDTO> tradeMarketingList = Objects.nonNull(request.getTradeMarketingList()) ? request.getTradeMarketingList() : Arrays.asList();
        Map<String, List<String>> skuMarketingIds = Maps.newHashMap();
        Map<String, List<TradeMarketingDTO>> skuMarketings = Maps.newHashMap();
        for (TradeMarketingDTO tradeMarketingDTO : tradeMarketingList) {
            List<String> skuIds = tradeMarketingDTO.getSkuIds();
            for (String skuId : skuIds) {
                List<String> marketingIds = skuMarketingIds.getOrDefault(skuId, Lists.newArrayList());
                marketingIds.add(tradeMarketingDTO.getMarketingId().toString());
                skuMarketingIds.put(skuId, marketingIds);

                List<TradeMarketingDTO> marketings = skuMarketings.getOrDefault(skuId, Lists.newArrayList());
                marketings.add(tradeMarketingDTO);
                skuMarketings.put(skuId, marketings);
            }
        }
        List<Long> preferentialMarketingIds =
                tradeMarketingList.stream().filter(g -> Objects.nonNull(g.getPreferentialSkuIds()))
                        .map(TradeMarketingDTO::getMarketingId).collect(Collectors.toList());
        goodsMarketingMap.forEach((k, v) -> {
            List<String> marketingIds = skuMarketingIds.get(k);
            if (CollectionUtils.isEmpty(marketingIds)) {
                // 接口中没有传营销，则记录购物车默认营销即 切换的营销
                skuMarketingIds.put(k, Lists.newArrayList(v.toString()));
                skuMarketings.put(k, Lists.newArrayList(TradeMarketingDTO.builder().marketingId(v).build()));
            } else if((marketingIds.size() == 1 && marketingIds.stream()
                    .anyMatch(g -> preferentialMarketingIds.contains(Long.parseLong(g))))){
                marketingIds.add(v.toString());
                skuMarketings.get(k).add(TradeMarketingDTO.builder().marketingId(v).build());
            }
        });
        //region 整理计算营销参数
        // <storeId, sku>
        Map<Long, List<GoodsInfoVO>> goodsInfosMap = param.getGoodsInfos().stream().collect(Collectors.groupingBy(GoodsInfoVO::getStoreId));
        // <storeId, {storeId,skus,marketing}>
        Map<Long, CountPriceDTO> countPriceMap = Maps.newHashMap();
        List<Long> storeIds = param.getStoreIds();
        for (Long storeId : storeIds) {
            List<GoodsInfoVO> storeGoodsInfos = goodsInfosMap.get(storeId);
            CountPriceDTO countPrice = countPriceMap.getOrDefault(storeId, CountPriceDTO.builder().storeId(storeId).goodsInfoVOList(Lists.newArrayList()).marketingVOList(Lists.newArrayList()).build());
            countPriceMap.put(storeId, countPrice);
            List<String> storeSkuIds = storeGoodsInfos.stream().map(GoodsInfoVO::getGoodsInfoId).distinct().collect(Collectors.toList());
            Map<Long, CountPriceMarketingDTO> countPriceMarketingMap = Maps.newHashMap();
            marketingMap.forEach((skuId, marketings) -> {
                if (!storeSkuIds.contains(skuId)) {
                    return;
                }
                // 该商品关联的所有营销
                List<String> allMarketingIds = marketings.stream().map(MarketingPluginLabelDetailVO::getMarketingId).filter(Objects::nonNull).map(Object::toString).collect(Collectors.toList());
                // 该商品购物车页面选中的营销
                List<String> marketingIds = skuMarketingIds.getOrDefault(skuId, Lists.newArrayList());
                List<TradeMarketingDTO> selectMarketings = skuMarketings.getOrDefault(skuId, Lists.newArrayList());
                if (!allMarketingIds.containsAll(marketingIds)) {
                    // 不存在已选的营销则直接报错
                    log.error("部分营销已失效");
                    if (Objects.nonNull(request.getForceConfirm()) && request.getForceConfirm()) {
                        return;
                    }
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050132);
                }
                long count = marketings.stream().filter(plugin -> MarketingPluginConstant.SELECT_ONE.contains(plugin.getMarketingType())).count();
                // 有营销没选  提示有其他营销生效
                if(CollectionUtils.isEmpty(marketingIds) && count > 0){
                    if (Objects.nonNull(request.getForceConfirm()) && request.getForceConfirm()) {
                        return;
                    }
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050133);
                }

                for (MarketingPluginLabelDetailVO marketingPlugin : marketings) {
                    TradeItem tradeItem = param.getTradeItemMap().get(skuId);
                    // 处理企业价 会员价 等级价 批发价 等等价格
                    if (MarketingPluginConstant.PRICE_EXIST.contains(marketingPlugin.getMarketingType())) {
                        // 分销商品参加了 秒杀 限时购 预约预售 营销商品设置为普通商品
                        if (MarketingPluginConstant.CHANGE_COMMON_GOODS.contains(marketingPlugin.getMarketingType())) {
                            tradeItem.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                            tradeItem.setDistributionCommission(BigDecimal.ZERO);
                        }else if (nonNull(marketingPlugin.getPluginPrice())) {
                            this.tradeConfirmQueryService.dealOtherPlugin(tradeItem, marketingPlugin);
                        }
                    }
                    // 处理满系营销价格
                    if (MarketingPluginConstant.SELECT_ONE.contains(marketingPlugin.getMarketingType())) {
                        String marketingId = marketingPlugin.getMarketingId().toString();
                        // 满系营销 不选择不处理
                        if (!marketingIds.contains(marketingId)) {
                            continue;
                        }
                        //满赠活动  验证请求参数中是否有有指定的赠品
                        List<TradeMarketingDTO> selectGiftMarketings = new ArrayList<>();
                        if (Objects.equals(MarketingPluginType.GIFT.getId(), marketingPlugin.getMarketingType())) {
                            if (CollectionUtils.isEmpty(tradeMarketingList)) {
                                continue;
                            }
                            Optional<TradeMarketingDTO> optional =
                                    tradeMarketingList.stream().filter(
                                                    tradeMarketingDTO -> Objects.nonNull(tradeMarketingDTO.getMarketingId())
                                                            && Objects.equals(marketingPlugin.getMarketingId().toString(), tradeMarketingDTO.getMarketingId().toString()))
                                            .findFirst();
                            if (!optional.isPresent()) {
                                continue;
                            }
                            TradeMarketingDTO tradeMarketingDTO = optional.get();
                            if (Objects.isNull(tradeMarketingDTO.getMarketingLevelId()) ||  CollectionUtils.isEmpty(tradeMarketingDTO.getGiftSkuIds())) {
                                continue;
                            }

                            Optional<TradeMarketingDTO> dtoOptional =
                                    selectMarketings.stream().filter(g -> nonNull(g.getGiftSkuIds())).findFirst();
                            TradeMarketingDTO selectMarketing = dtoOptional.orElseGet(() -> TradeMarketingDTO.builder().marketingId(marketingPlugin.getMarketingId()).build());
                            selectMarketing.setMarketingLevelId(tradeMarketingDTO.getMarketingLevelId());
                            selectMarketing.setGiftSkuIds(tradeMarketingDTO.getGiftSkuIds());
                            selectGiftMarketings.add(selectMarketing);
                        }
                        // 处理满系营销活动(此处随可以多个，但是页面也只能选择一个)
                        this.tradeConfirmQueryService.dealMultiTypeMap(skuId, marketingPlugin, countPriceMarketingMap, selectGiftMarketings);
                        //验证赠品是否有误
                        if (Objects.equals(MarketingPluginType.GIFT.getId(), marketingPlugin.getMarketingType()) && countPriceMarketingMap.containsKey(Long.valueOf(marketingPlugin.getMarketingId().toString()))) {
                            CountPriceMarketingDTO countPriceMarketingDTO = countPriceMarketingMap.get(Long.valueOf(marketingPlugin.getMarketingId().toString()));
                            if (Objects.isNull(countPriceMarketingDTO) || CollectionUtils.isEmpty(countPriceMarketingDTO.getGiftSkuIds())){
                                throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
                            }
                        }
                    }

                    if (MarketingPluginType.PREFERENTIAL.getId() == marketingPlugin.getMarketingType()){
                        String marketingId = marketingPlugin.getMarketingId().toString();
                        // 满系营销 不选择不处理
                        if (!marketingIds.contains(marketingId)) {
                            continue;
                        }
                        //满赠活动  验证请求参数中是否有有指定的赠品
                        List<TradeMarketingDTO> selectPreferentialMarketings = new ArrayList<>();
                            if (CollectionUtils.isEmpty(tradeMarketingList)) {
                                continue;
                            }
                            Optional<TradeMarketingDTO> optional =
                                    tradeMarketingList.stream().filter(
                                                    tradeMarketingDTO -> Objects.nonNull(tradeMarketingDTO.getMarketingId())
                                                            && Objects.equals(marketingPlugin.getMarketingId().toString(), tradeMarketingDTO.getMarketingId().toString()))
                                            .findFirst();
                            if (!optional.isPresent()) {
                                continue;
                            }
                            TradeMarketingDTO tradeMarketingDTO = optional.get();
                            if (CollectionUtils.isEmpty(tradeMarketingDTO.getPreferentialSkuIds())) {
                                continue;
                            }

                        Optional<TradeMarketingDTO> dtoOptional =
                                selectMarketings.stream().filter(g -> nonNull(g.getPreferentialSkuIds())).findFirst();
                        TradeMarketingDTO selectMarketing = dtoOptional.orElseGet(() -> TradeMarketingDTO.builder().marketingId(marketingPlugin.getMarketingId()).build());
                        selectMarketing.setPreferentialSkuIds(tradeMarketingDTO.getPreferentialSkuIds());
                        selectMarketing.setMarketingLevelId(tradeMarketingDTO.getMarketingLevelId());
                        selectPreferentialMarketings.add(selectMarketing);
                        this.tradeConfirmQueryService.dealMultiTypeMap(skuId, marketingPlugin, countPriceMarketingMap, selectPreferentialMarketings);
                    }
                }
            });
            countPrice.setMarketingVOList(Lists.newArrayList(countPriceMarketingMap.values()));
            for (GoodsInfoVO storeGoodsInfo : storeGoodsInfos) {
                storeSkuIds.add(storeGoodsInfo.getGoodsInfoId());
                TradeItem tradeItem = param.getTradeItemMap().get(storeGoodsInfo.getGoodsInfoId());
                CountPriceGoodsInfoDTO countPriceGoodsInfo = new CountPriceGoodsInfoDTO();
                countPriceGoodsInfo.setGoodsInfoId(storeGoodsInfo.getGoodsInfoId());
                countPriceGoodsInfo.setGoodsId(storeGoodsInfo.getGoodsId());
                countPriceGoodsInfo.setPrice(tradeItem.getPrice());
                countPriceGoodsInfo.setNum(tradeItem.getNum());
                countPriceGoodsInfo.setBrandId(storeGoodsInfo.getBrandId());
                countPriceGoodsInfo.setCateId(storeGoodsInfo.getCateId());
                countPriceGoodsInfo.setStoreCateIds(storeGoodsInfo.getStoreCateIds());
                countPrice.getGoodsInfoVOList().add(countPriceGoodsInfo);
            }
        }
        //endregion
        // 营销价格获取
        if (countPriceMap.values().stream().map(CountPriceDTO::getMarketingVOList).mapToLong(Collection::size).sum() > 0) {
            // 合并 同一个活动id 和 同一个活动级别
            param.setCountPriceVOList(this.tradeConfirmQueryService.countPriceVOList(Lists.newArrayList(countPriceMap.values()), request.getCustomerId()));
        }
    }

    @Override
    public void calcPrice(TradeConfirmParam param) {
        // 1. 填充营销价格
        Map<Long, CountPriceItemVO> priceItemMap = param.getCountPriceVOList().stream().collect(Collectors.toMap(CountPriceItemVO::getStoreId, Function.identity(), (v1, v2) -> v1));
        Map<Long, TradeItemGroup> itemGroupMap = param.getTradeItemSnapshot().getItemGroups().stream().collect(Collectors.toMap(group -> group.getSupplier().getStoreId(), Function.identity(), (v1, v2) -> v1));
        List<StoreVO> stores = param.getStores();
        for (StoreVO store : stores) {
            CountPriceItemVO countPriceItemVO = priceItemMap.get(store.getStoreId());
            TradeItemGroup tradeItemGroup = itemGroupMap.get(store.getStoreId());
            List<TradeItem> tradeItems = tradeItemGroup.getTradeItems();
            if (isNull(countPriceItemVO)) {
                continue;
            }
            List<TradeMarketingDTO> tradeMarketingVoMap = countPriceItemVO.getTradeMarketings()
                    .stream()
                    .map(vo -> TradeMarketingDTO.builder()
                            .marketingId(vo.getMarketingId())
                            .marketingLevelId(vo.getMarketingLevelId())
                            .skuIds(vo.getSkuIds())
                            .giftSkuIds(vo.getGiftIds())
                            .preferentialSkuIds(vo.getPreferentialIds())
                            .build())
                    .collect(Collectors.toList());
            tradeItemGroup.setTradeMarketingList(tradeMarketingVoMap);
            Map<String, CountPriceItemGoodsInfoVO> countGoodsInfoMap = countPriceItemVO.getGoodsInfoList().stream().collect(Collectors.toMap(CountPriceItemGoodsInfoVO::getGoodsInfoId, Function.identity(), (v1, v2) -> v1));

            for (TradeItem tradeItem : tradeItems) {
                CountPriceItemGoodsInfoVO countPriceItemGoodsInfoVO = countGoodsInfoMap.get(tradeItem.getSkuId());
                if (nonNull(countPriceItemGoodsInfoVO)) {
                    tradeItem.setSplitPrice(countPriceItemGoodsInfoVO.getSplitPrice());
                    if (CollectionUtils.isNotEmpty(countPriceItemGoodsInfoVO.getMarketingList())) {
                        List<Long> marketingIds = countPriceItemGoodsInfoVO.getMarketingList().stream()
                                .map(CountPriceItemMarketingVO::getMarketingId)
                                .distinct()
                                .collect(Collectors.toList());
                        List<Long> marketingLevelIds = countPriceItemGoodsInfoVO.getMarketingList().stream()
                                .map(CountPriceItemMarketingVO::getMarketingLevelId)
                                .distinct()
                                .collect(Collectors.toList());
                        tradeItem.setMarketingIds(marketingIds);
                        tradeItem.setMarketingLevelIds(marketingLevelIds);
                    }
                }
            }
        }
    }

    /**
     * 提交订单快照
     */
    @Override
    public void snapshot(TradeConfirmParam param) {
        this.tradeItemSnapshotService.addTradeItemSnapshot(param.getTradeItemSnapshot());
    }
}
