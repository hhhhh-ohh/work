package com.wanmi.sbc.order.optimization.trade1.confirm.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
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
import com.wanmi.sbc.order.api.optimization.trade1.request.CommunityBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmInterface;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.optimization.trade1.confirm.service.query.TradeCommunityQueryService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author edz
 * @className CommunityBuyServiceImpl
 * @description 社区团购
 * @date 2023/7/24 15:20
 **/
@Service("communityBuy")
@Slf4j
public class CommunityBuyServiceImpl implements TradeConfirmInterface {

    @Autowired
    private TradeItemSnapshotService tradeItemSnapshotService;
    @Autowired
    private TradeCommunityQueryService tradeCommunityQueryService;

    @Override
    public void getParams(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        List<TradeItemRequest> tradeItems = request.getTradeItemRequests();
        // 获取会员信息
        CustomerVO customerVO = tradeCommunityQueryService.getCustomer(request.getCustomerId());
        param.setCustomerVO(customerVO);
        // 获取skuIds storeIds
        param.setSkuIds(tradeItems.stream().map(TradeItemRequest::getSkuId).collect(Collectors.toList()));

        // 获取第三方渠道开关
        tradeCommunityQueryService.getChanelFlag(param);

        // 获取商品信息
        TradeConfirmGoodsRequest goodsRequest = TradeConfirmGoodsRequest.builder()
                .skuIds(param.getSkuIds())
                .isHavSpecText(Boolean.TRUE)
                .isHavIntervalPrice(Boolean.TRUE)
                .isHavRedisStock(Boolean.TRUE)
                .build();
        tradeCommunityQueryService.getGoodsInfos(param, goodsRequest);

        // 获取店铺信息
        param.setStoreIds(param.getGoodses().stream().map(GoodsVO::getStoreId).distinct().collect(Collectors.toList()));
        param.setStores(tradeCommunityQueryService.getStoreByStoreIds(param.getStoreIds()));
        // 系统积分开关
        param.setSystemPointFlag(tradeCommunityQueryService.querySystemPointConfig());
        // 社区团购信息
        tradeCommunityQueryService.getCommunityActivityVO(param);
        // 团长ID查询自提点信息
        tradeCommunityQueryService.getCommunityLeaderPickupPointList(param);
        //  团长信息
        tradeCommunityQueryService.getLeaderInfo(param);
        // 获取营销信息
        param.setMarketingMap(this.tradeCommunityQueryService.getMarketing(param.getGoodsInfos(), request.getCustomerId(), request.getIepCustomerFlag(), Boolean.FALSE));

        Map<String, BigDecimal> skuIdToActivityPriceMap =
                param.getCommunityActivityVO().getSkuList().stream().collect(HashMap::new, (map, item) -> map.put(item.getSkuId(),
                        item.getPrice()), HashMap::putAll);
        param.setSkuIdToActivityPriceMap(skuIdToActivityPriceMap);
    }

    @Override
    public void check(TradeConfirmParam param) {

        // 店铺检验
        tradeCommunityQueryService.checkStore(param);

        // 渠道校验
        tradeCommunityQueryService.checkChanelGoods(param);

        // 活动基本信息校验
        tradeCommunityQueryService.checkCommunityActivityVO(param);

        // 活动库存信息校验
        tradeCommunityQueryService.checkGoodsStock(param);

        // 团长校验
        tradeCommunityQueryService.checkCommunityLeader(param);

    }

    @Override
    public void assembleTrade(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        Map<String, TradeItemRequest> tradeItemRequestMap = request.getTradeItemRequests()
                .stream().collect(Collectors.toMap(TradeItemRequest::getSkuId, Function.identity()));
        Map<Long, List<GoodsInfoVO>> goodsInfosMap = param.getGoodsInfos()
                .stream().collect(Collectors.groupingBy(GoodsInfoVO::getStoreId));
        Map<Long, Map<String, GoodsVO>> goodsMap = param.getGoodses()
                .stream()
                .collect(Collectors.groupingBy(GoodsVO::getStoreId, Collectors.toMap(GoodsVO::getGoodsId, Function.identity())));
        Map<String, String> specDetailMap = param.getGoodsInfoSpecDetailRelVOList()
                .stream()
                .collect(Collectors
                        .toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, 
                                (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));
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
                        BigDecimal activityPrice = param.getSkuIdToActivityPriceMap().get(goodsInfo.getGoodsInfoId());
                        if (Objects.nonNull(activityPrice)){
                            tradeItemRequest.setBuyPoint(0L);
                        }
                        tradeCommunityQueryService.fillBaseGoodsInfo(goodsInfo, goodsVO, specDetailMap);
                        // 补充基本信息
                        tradeCommunityQueryService.fillBaseTradeItem(tradeItem, goodsInfo, tradeItemRequest, request.getBuyType());
                        // 填充价格
                        tradeCommunityQueryService.fillTradeItemPrice(tradeItem, goodsInfo, goodsVO,
                                param.getGoodsIntervalPriceVOList(), param.getCommunityActivityVO().getSkuList());
                        return tradeItem;
                    }).collect(Collectors.toList());
            itemGroup.setTradeItems(tradeItems);
            itemGroup.setPluginType(PluginType.NORMAL);
            itemGroup.setStoreBagsFlag(DefaultFlag.NO);
            itemGroup.setSuitMarketingFlag(Boolean.FALSE);
            itemGroup.setTradeMarketingList(Lists.newArrayList());
            itemGroup.setOrderTag(OrderTag.builder().communityFlag(Boolean.TRUE).build());
            CommunityBuyRequest communityBuyRequest = new CommunityBuyRequest();
            BeanUtils.copyProperties(request.getCommunityBuyRequest(), communityBuyRequest);
            itemGroup.setCommunityBuyRequest(communityBuyRequest);
            itemGroups.add(itemGroup);
        }

        param.setTradeItemMap(itemGroups.stream()
                .map(TradeItemGroup::getTradeItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(TradeItem::getSkuId, Function.identity(), (v1, v2) -> v1)));

        TradeItemSnapshot tradeItemSnapshot = TradeItemSnapshot.builder()
                .id(UUIDUtil.getUUID())
                .buyerId(request.getCustomerId())
                .itemGroups(itemGroups)
                .terminalToken(request.getTerminalToken())
                .purchaseBuy(Boolean.FALSE)
                .orderTag(OrderTag.builder().communityFlag(Boolean.TRUE).build())
                .build();
        param.setTradeItemSnapshot(tradeItemSnapshot);
    }

    @Override
    public void fillPriceAndStock(TradeConfirmParam param) { }

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
                    return;
                }

                BigDecimal activityPrice = param.getSkuIdToActivityPriceMap().get(skuId);
                for (MarketingPluginLabelDetailVO marketingPlugin : marketings) {
                    TradeItem tradeItem = param.getTradeItemMap().get(skuId);
                    // 处理企业价 会员价 等级价 批发价 等等价格
                    if (MarketingPluginConstant.PRICE_EXIST.contains(marketingPlugin.getMarketingType())) {
                        // 分销商品参加了 秒杀 限时购 预约预售 营销商品设置为普通商品
                        if (MarketingPluginConstant.CHANGE_COMMON_GOODS.contains(marketingPlugin.getMarketingType())) {
                            tradeItem.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                            tradeItem.setDistributionCommission(BigDecimal.ZERO);
                        }else if (nonNull(marketingPlugin.getPluginPrice()) && isNull(activityPrice)) {
                            this.tradeCommunityQueryService.dealOtherPlugin(tradeItem, marketingPlugin);
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
                        this.tradeCommunityQueryService.dealMultiTypeMap(skuId, marketingPlugin, countPriceMarketingMap, selectGiftMarketings);
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
                        this.tradeCommunityQueryService.dealMultiTypeMap(skuId, marketingPlugin, countPriceMarketingMap, selectPreferentialMarketings);
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
            param.setCountPriceVOList(this.tradeCommunityQueryService.countPriceVOList(Lists.newArrayList(countPriceMap.values()), request.getCustomerId()));
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

    @Override
    public void snapshot(TradeConfirmParam param) {
        this.tradeItemSnapshotService.addTradeItemSnapshot(param.getTradeItemSnapshot());
    }
}
