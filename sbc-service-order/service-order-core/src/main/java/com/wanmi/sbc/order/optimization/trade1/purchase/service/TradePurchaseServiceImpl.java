package com.wanmi.sbc.order.optimization.trade1.purchase.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
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
import com.wanmi.sbc.marketing.bean.enums.RecruitApplyType;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.CommunityBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.request.trade.FindTradeSnapshotRequest;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.optimization.trade1.confirm.service.query.TradeCommunityQueryService;
import com.wanmi.sbc.order.optimization.trade1.confirm.service.query.TradeConfirmQueryService;
import com.wanmi.sbc.order.optimization.trade1.purchase.TradePurchaseInterface;
import com.wanmi.sbc.order.orderperformance.util.TradeItemAnalyzer;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemService;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author liguang
 * @description 订单确认页处理服务
 * @date 2022/4/7 20:07
 */
@Service(value = "tradePurchaseService")
@Slf4j
public class TradePurchaseServiceImpl implements TradePurchaseInterface {

    @Autowired
    private TradeConfirmQueryService tradeConfirmQueryService;
    @Autowired
    private TradePurchaseDealService tradePurchaseDealService;
    @Autowired
    private TradeItemService tradeItemService;
    @Autowired
    private DistributionCacheService distributionCacheService;
    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;
    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;
    @Autowired
    private TradeCommunityQueryService tradeCommunityQueryService;
    @Autowired
    private TradeItemAnalyzer tradeItemAnalyzer;

    @Override
    public void getParams(TradeConfirmParam param) {
        FindTradeSnapshotRequest request = param.getFindTradeSnapshotRequest();
        // 查询用户信息
        param.setCustomerVO(this.tradeConfirmQueryService.getCustomer(request.getCustomerId()));
        // 用户是否是企业会员
        param.setIsIepCustomerFlag(EnterpriseCheckState.CHECKED.equals(param.getCustomerVO().getEnterpriseCheckState()) && request.getVasIpeFlag());
        // 查询订单快照
        TradeItemSnapshot tradeItemSnapshot = this.tradeItemService.findByTerminalToken(request.getTerminalToken());
        param.setTradeItemSnapshot(tradeItemSnapshot);
        List<TradeItemGroup> tradeItemGroups = param.getTradeItemSnapshot().getItemGroups();
        // 查询商品信息
        List<String> skuIds = tradeItemGroups.stream().map(TradeItemGroup::getTradeItems).flatMap(Collection::stream).map(TradeItem::getSkuId).collect(Collectors.toList());
        TradeConfirmGoodsRequest goodsRequest = TradeConfirmGoodsRequest.builder()
                .skuIds(skuIds)
                .isHavSpecText(Boolean.TRUE)
                .isHavRedisStock(Boolean.TRUE)
                .build();
        this.tradeConfirmQueryService.getGoodsInfos(param, goodsRequest);
        // 砍价商品不处理分销 营销
        if(!Objects.equals(Boolean.TRUE, tradeItemSnapshot.getBargain())) {
            // 是否是分销
            param.setIsDistributFlag(this.tradeConfirmQueryService.querySystemDistributionConfig()
                    && !ChannelType.PC_MALL.equals(request.getDistributeChannel().getChannelType())
                    && (Objects.isNull(tradeItemSnapshot.getOrderTag()) || !tradeItemSnapshot.getOrderTag().getCommunityFlag()));
            // 获取店铺是否开启分销开关
            param.setStoreOpenFlag(this.distributionCacheService.queryStoreListOpenFlag(param.getStoreIds()));

            // 查询赠品
            List<String> giftIds = tradeItemGroups.stream()
                    .map(TradeItemGroup::getTradeMarketingList).filter(Objects::nonNull).flatMap(Collection::stream)
                    .map(TradeMarketingDTO::getGiftSkuIds).filter(Objects::nonNull).flatMap(Collection::stream)
                    .distinct().collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(giftIds)){
                TradeConfirmGoodsRequest giftGoodsRequest = TradeConfirmGoodsRequest.builder()
                        .skuIds(giftIds)
                        .isHavSpecText(Boolean.TRUE)
                        .build();
                this.tradeConfirmQueryService.getGiftGoodsInfos(param, giftGoodsRequest);
            }

            // 查询加价购商品
            List<String> preferentialIds = tradeItemGroups.stream()
                    .map(TradeItemGroup::getTradeMarketingList).filter(Objects::nonNull).flatMap(Collection::stream)
                    .map(TradeMarketingDTO::getPreferentialSkuIds).filter(Objects::nonNull).flatMap(Collection::stream)
                    .distinct().collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(preferentialIds)){
                TradeConfirmGoodsRequest request1equest = TradeConfirmGoodsRequest.builder()
                        .skuIds(preferentialIds)
                        .isHavSpecText(Boolean.TRUE)
                        .build();
                this.tradeConfirmQueryService.getPreferentialGoodsInfos(param, request1equest);
            }
        }

        // 查询店铺信息
        param.setStoreIds(tradeItemGroups.stream().map(g -> g.getSupplier().getStoreId()).collect(Collectors.toList()));
        param.setStores(this.tradeConfirmQueryService.getStoreByStoreIds(param.getStoreIds()));
        // 系统积分开关
        param.setSystemPointFlag(this.tradeConfirmQueryService.querySystemPointConfig());

        // 非砍价订单
        if (!Objects.equals(Boolean.TRUE, tradeItemSnapshot.getBargain())) {
            // 获取店铺是否开启分销开关
            param.setStoreOpenFlag(this.distributionCacheService.queryStoreListOpenFlag(param.getStoreIds()));
        }
        // 设置返回对象
        TradeItemGroup tradeItemGroup = tradeItemGroups.get(NumberUtils.INTEGER_ZERO);
        boolean grouponFlag =
                Objects.equals(Boolean.TRUE, tradeItemSnapshot.getBargain())
                        ? Boolean.FALSE
                        : nonNull(tradeItemGroup.getGrouponForm())
                        && nonNull(tradeItemGroup.getGrouponForm().getOpenGroupon());
        boolean suitMarketingFlag =
                Objects.equals(Boolean.TRUE, tradeItemSnapshot.getBargain())
                        ? Boolean.FALSE
                        : Boolean.TRUE.equals(tradeItemGroup.getSuitMarketingFlag());
        // 判断分销开关，如果系统分销或者店铺分销开关开启设置商品为普通商品
        this.tradeConfirmQueryService.checkDistributFlag(param, grouponFlag, suitMarketingFlag);

        // 获取营销信息
        param.setMarketingMap(this.tradeConfirmQueryService.getMarketing(param.getGoodsInfos(), request.getCustomerId(), param.getIsIepCustomerFlag(),tradeItemSnapshot.getBargain()));

        if (Objects.nonNull(tradeItemSnapshot.getOrderTag()) && tradeItemSnapshot.getOrderTag().getPickupCardFlag()){
            UserGiftCardInfoVO userGiftCardInfoVO = tradeConfirmQueryService.getUserGiftCard(tradeItemSnapshot.getUserGiftCardId(),
                    request.getCustomerId());
            param.setUserGiftCardInfoVO(userGiftCardInfoVO);
        }

        // 社区团购信息
        if (Objects.nonNull(tradeItemSnapshot.getOrderTag()) && tradeItemSnapshot.getOrderTag().getCommunityFlag()){
            CommunityBuyRequest communityBuyRequest = new CommunityBuyRequest();
            communityBuyRequest.setActivityId(tradeItemGroup.getCommunityBuyRequest().getActivityId());
            communityBuyRequest.setLeaderId(tradeItemGroup.getCommunityBuyRequest().getLeaderId());
            TradeBuyRequest tradeBuyRequest = new TradeBuyRequest();
            tradeBuyRequest.setCommunityBuyRequest(communityBuyRequest);
            param.setTradeBuyRequest(tradeBuyRequest);
            tradeCommunityQueryService.getCommunityActivityVO(param);
            Map<String, BigDecimal> skuIdToActivityPriceMap =
                    param.getCommunityActivityVO().getSkuList().stream().collect(HashMap::new, (map, item) -> map.put(item.getSkuId(),
                            item.getPrice()), HashMap::putAll);
            param.setSkuIdToActivityPriceMap(skuIdToActivityPriceMap);
        }
    }

    @Override
    public void check(TradeConfirmParam param) {
        DistributeChannel distributeChannel = param.getFindTradeSnapshotRequest().getDistributeChannel();
        TradeItemGroup tradeItemGroup = param.getTradeItemSnapshot().getItemGroups().get(NumberUtils.INTEGER_ZERO);
        List<TradeItem> tradeItems = tradeItemGroup.getTradeItems();
        // 验证小店商品
        if(DefaultFlag.YES.equals(tradeItemGroup.getStoreBagsFlag())){
            // 开店礼包商品校验
            RecruitApplyType applyType = this.distributionCacheService.getApplyType();
            if(RecruitApplyType.REGISTER.equals(applyType)){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
            }
            List<String> goodsInfoIds = this.distributionCacheService.queryStoreBags()
                    .stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
            if (!goodsInfoIds.contains(tradeItemGroup.getTradeItems().get(0).getSkuId())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
            }
        } else if(distributeChannel.getChannelType() == ChannelType.SHOP){
            List<String> skuIds = tradeItems.stream().map(TradeItem::getSkuId).collect(Collectors.toList());
            // 1.验证商品是否是小店商品
            List<String> invalidIds = this.tradeConfirmQueryService.getInvalidDistributeSkuIds(skuIds, distributeChannel.getInviteeId());
            if (CollectionUtils.isNotEmpty(invalidIds)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
            }
            // 2.验证商品对应商家的分销开关有没有关闭
            for (TradeItem tradeItem : tradeItems) {
                if(DefaultFlag.NO.equals(param.getStoreOpenFlag().get(tradeItem.getStoreId()))){
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
                }
            }
        }
    }


    @Override
    public void assembleTrade(TradeConfirmParam param) {
        FindTradeSnapshotRequest request = param.getFindTradeSnapshotRequest();
        TradeItemSnapshot tradeItemSnapshot = param.getTradeItemSnapshot();
        List<TradeItemGroup> tradeItemGroups = tradeItemSnapshot.getItemGroups();
        // 下单人自己是否是分销员
        DistributionCustomerVO distributionSelf = this.tradeConfirmQueryService.getDistributionCustomer(request.getCustomerId());
        // 查询分销员信息
        DistributionCustomerVO distributionCustomer = this.tradeConfirmQueryService.getDistributionCustomer(request.getDistributeChannel().getInviteeId());
        // 设置返回对象
        TradeItemGroup tradeItemGroup = tradeItemGroups.get(NumberUtils.INTEGER_ZERO);
        TradeConfirmResponse tradeConfirmResponse = new TradeConfirmResponse();
        boolean isDistributor = param.getIsDistributFlag() && nonNull(distributionSelf) && Objects.equals(DefaultFlag.YES, distributionSelf.getDistributorFlag()) && Objects.equals(DefaultFlag.NO, distributionSelf.getForbiddenFlag());
        tradeConfirmResponse.setIsDistributor(isDistributor ? DefaultFlag.YES : DefaultFlag.NO);
        tradeConfirmResponse.setShopName(distributionCacheService.getShopName());
        if(nonNull(distributionCustomer)){
            tradeConfirmResponse.setInviteeName(distributionCustomer.getCustomerName());
        }
        tradeConfirmResponse.setPurchaseBuy(param.getTradeItemSnapshot().getPurchaseBuy());
        tradeConfirmResponse.setStoreBagsFlag(tradeItemGroup.getStoreBagsFlag());
        tradeConfirmResponse.setSuitMarketingFlag(tradeItemGroup.getSuitMarketingFlag());
        // 如果是秒杀则设置是否包邮
        if(Constants.FLASH_SALE_GOODS_ORDER_TYPE.equals(tradeItemGroup.getSnapshotType()) && CollectionUtils.isNotEmpty(tradeItemGroup.getTradeItems())){
            Integer postage = this.flashSaleGoodsQueryProvider.getById(new FlashSaleGoodsByIdRequest(tradeItemGroup.getTradeItems().get(0).getFlashSaleGoodsId())).getContext().getFlashSaleGoodsVO().getPostage();
            tradeConfirmResponse.setFlashFreeDelivery(postage.equals(NumberUtils.INTEGER_ONE) ? Boolean.TRUE : Boolean.FALSE);
        }
        tradeConfirmResponse.setOrderTagVO(KsBeanUtil.convert(ObjectUtils.defaultIfNull(tradeItemGroups.get(NumberUtils.INTEGER_ZERO).getOrderTag(), new OrderTag()), OrderTagVO.class));

        if (Objects.nonNull(param.getUserGiftCardInfoVO())){
            tradeConfirmResponse.setPickUpCardName(param.getUserGiftCardInfoVO().getName());
        }

        Map<String, GoodsInfoVO> goodsInfosMap = param.getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        Map<String, GoodsVO> goodsMap = param.getGoodses().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        Map<String, String> specDetailMap = param.getGoodsInfoSpecDetailRelVOList()
                .stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));

        Map<String, GoodsInfoVO> giftGoodsInfosMap = param.getGiftGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        Map<String, GoodsVO> giftGoodsMap = param.getGiftGoodses().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        Map<String, String> giftSpecDetailMap = param.getGiftGoodsInfoSpecDetailRelVOList()
                .stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));

        Map<String, GoodsInfoVO> preferentialGoodsInfosMap =
                param.getPreferentialGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        Map<String, GoodsVO> preferentialGoodsMap =
                param.getPreferentialGoodses().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        Map<String, String> preferentialSpecDetailMap = param.getPreferentialGoodsInfoSpecDetailRelVOList()
                .stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));

        // 计算营销
        // <storeId, {storeId,skus,marketing}>
        Map<String, List<MarketingPluginLabelDetailVO>> marketingMap = param.getMarketingMap();
        Map<Long, CountPriceDTO> countPriceMap = Maps.newHashMap();
        for (TradeItemGroup itemGroup : tradeItemGroups) {
            Map<Long, CountPriceMarketingDTO> countPriceMarketingMap = Maps.newHashMap();
            List<TradeMarketingDTO> tradeMarketingList = itemGroup.getTradeMarketingList();
            if(CollectionUtils.isEmpty(tradeMarketingList) || Boolean.TRUE.equals(tradeItemGroup.getSuitMarketingFlag())){
                continue;
            }
            Long storeId = itemGroup.getSupplier().getStoreId();
            CountPriceDTO countPrice = countPriceMap.getOrDefault(storeId, CountPriceDTO.builder().storeId(storeId).goodsInfoVOList(Lists.newArrayList()).marketingVOList(Lists.newArrayList()).build());
            countPriceMap.put(storeId, countPrice);
            List<TradeItem> tradeItems = itemGroup.getTradeItems();
            Map<String, TradeItem> tradeItemMap = tradeItems.stream().collect(Collectors.toMap(TradeItem::getSkuId, Function.identity(), (v1, v2) -> v1));
            List<String> storeSkuIds = Lists.newArrayList();
            Boolean buyCycleFlag = itemGroup.getOrderTag().getBuyCycleFlag();
            for (TradeItem tradeItem : tradeItems) {
                storeSkuIds.add(tradeItem.getSkuId());
                CountPriceGoodsInfoDTO countPriceGoodsInfo = new CountPriceGoodsInfoDTO();
                countPriceGoodsInfo.setGoodsInfoId(tradeItem.getSkuId());
                countPriceGoodsInfo.setGoodsId(tradeItem.getSpuId());
                countPriceGoodsInfo.setPrice(tradeItem.getPrice());
                countPriceGoodsInfo.setNum(tradeItem.getNum());
                countPriceGoodsInfo.setBrandId(tradeItem.getBrand());
                countPriceGoodsInfo.setCateId(tradeItem.getCateId());
                if (buyCycleFlag) {
                    TradeBuyCycleDTO tradeBuyCycleDTO = itemGroup.getTradeBuyCycleDTO();
                    countPriceGoodsInfo.setNum(tradeItem.getNum() * tradeBuyCycleDTO.getDeliveryCycleNum());
                }
                countPrice.getGoodsInfoVOList().add(countPriceGoodsInfo);
            }
            // 快照中选中的营销
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
            marketingMap.forEach((skuId, marketings) -> {
                if (!storeSkuIds.contains(skuId)) {
                    return;
                }
                // 该商品关联的所有营销
                List<String> allMarketingIds = marketings.stream().map(MarketingPluginLabelDetailVO::getMarketingId).filter(Objects::nonNull).map(Object::toString).collect(Collectors.toList());
                // 该快照中选中的营销
                List<String> marketingIds = skuMarketingIds.getOrDefault(skuId, Lists.newArrayList());
                List<TradeMarketingDTO> selectMarketings = skuMarketings.getOrDefault(skuId, Lists.newArrayList());
                if (!allMarketingIds.containsAll(marketingIds)) {
                    // 不存在已选的营销则直接报错
                    log.error("部分营销已失效");
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050132);
                }
                for (MarketingPluginLabelDetailVO marketingPlugin : marketings) {
                    TradeItem tradeItem = tradeItemMap.get(skuId);
                    BigDecimal activityPrice =  param.getSkuIdToActivityPriceMap().get(skuId);
                    // 处理企业价 会员价 等级价 批发价 等等价格
                    if (MarketingPluginConstant.PRICE_EXIST.contains(marketingPlugin.getMarketingType())) {
                        // 互斥营销将商品设置为普通商品
                        if (MarketingPluginConstant.CHANGE_COMMON_GOODS.contains(marketingPlugin.getMarketingType())) {
                            tradeItem.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                            tradeItem.setDistributionCommission(BigDecimal.ZERO);
                        }else if (nonNull(marketingPlugin.getPluginPrice()) && isNull(activityPrice)) {
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

                        // 排除加价购活动
                        List<TradeMarketingDTO> dtos =
                                selectMarketings.stream().filter(g -> CollectionUtils.isEmpty(g.getPreferentialSkuIds())).collect(Collectors.toList());
                        // 处理满系营销活动(此处随可以多个，但是页面也只能选择一个)
                        this.tradeConfirmQueryService.dealMultiTypeMap(skuId, marketingPlugin, countPriceMarketingMap, dtos);
                    }
                    // 加价购
                    if (MarketingPluginType.PREFERENTIAL.getId() == marketingPlugin.getMarketingType()){
                        String marketingId = marketingPlugin.getMarketingId().toString();
                        // 满系营销 不选择不处理
                        if (!marketingIds.contains(marketingId)) {
                            continue;
                        }

                        // 处理加价购活动
                        List<TradeMarketingDTO> dtos =
                                selectMarketings.stream().filter(g -> Objects.nonNull(g.getPreferentialSkuIds())).collect(Collectors.toList());
                        this.tradeConfirmQueryService.dealMultiTypeMap(skuId, marketingPlugin, countPriceMarketingMap, dtos);
                    }
                }
            });
            countPrice.setMarketingVOList(Lists.newArrayList(countPriceMarketingMap.values()));
        }
        // 营销价格获取
        if (countPriceMap.values().stream().map(CountPriceDTO::getMarketingVOList).mapToLong(Collection::size).sum() > 0) {
            param.setCountPriceVOList(this.tradeConfirmQueryService.countPriceVOList(Lists.newArrayList(countPriceMap.values()), request.getCustomerId()));
        }
        Map<Long, CountPriceItemVO> priceItemMap = param.getCountPriceVOList().stream().collect(Collectors.toMap(CountPriceItemVO::getStoreId, Function.identity(), (v1, v2) -> v1));

        tradeConfirmResponse.setTradeConfirmItems(Lists.newArrayList());
        for (TradeItemGroup itemGroup : tradeItemGroups) {
            CountPriceItemVO countPriceItemVO = priceItemMap.get(itemGroup.getSupplier().getStoreId());
            TradeConfirmItemVO tradeConfirmItem = new TradeConfirmItemVO();
            List<TradeItem> tradeItems = itemGroup.getTradeItems();
            tradeConfirmItem.setSupplier(KsBeanUtil.convert(itemGroup.getSupplier(), SupplierVO.class));
            TradePriceVO tradePrice = TradePriceVO.builder()
                    .goodsPrice(BigDecimal.ZERO)
                    .originPrice(BigDecimal.ZERO)
                    .totalPrice(BigDecimal.ZERO)
                    .totalTax(BigDecimal.ZERO)
                    .buyPoints(null)
                    .build();
            for (TradeItem tradeItem : tradeItems) {
                GoodsInfoVO goodsInfoVO = goodsInfosMap.get(tradeItem.getSkuId());
                GoodsVO goodsVO = goodsMap.get(tradeItem.getSpuId());
                this.tradeConfirmQueryService.fillBaseGoodsInfo(goodsInfoVO, goodsVO, specDetailMap);
                this.tradeConfirmQueryService.fillBaseTradeItem(
                        tradeItem, goodsInfoVO, null, null);
                Long num = tradeItem.getNum();
                OrderTag orderTag = tradeItemGroup.getOrderTag();
                boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
                if (buyCycleFlag) {
                    // 加入快照中周期购的信息
                    TradeBuyCycleVO tradeBuyCycleVO = KsBeanUtil.convert(tradeItemGroup.getTradeBuyCycleDTO(), TradeBuyCycleVO.class);
                    Objects.requireNonNull(tradeBuyCycleVO).setDeliveryPlanS(KsBeanUtil.convert(tradeItemGroup.getTradeBuyCycleDTO().getDeliveryPlanS(),CycleDeliveryPlanVO.class));
                    tradeConfirmItem.setTradeBuyCycleVO(tradeBuyCycleVO);
                    tradeConfirmItem.setBuyCycleNum(tradeBuyCycleVO.getDeliveryCycleNum());
                    num = tradeConfirmItem.getBuyCycleNum() * num;
                    //周期购不支持商品积分抵扣
                    tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                }
                BigDecimal buyItemPrice = tradeItem.getPrice().multiply(BigDecimal.valueOf(num));
                // 订单商品总价
                tradePrice.setGoodsPrice(tradePrice.getGoodsPrice().add(buyItemPrice));
                // 订单应付总金额
                tradePrice.setTotalPrice(tradePrice.getTotalPrice().add(buyItemPrice));
                // 订单原始总金额
                tradePrice.setOriginPrice(tradePrice.getOriginPrice().add(buyItemPrice));
                // 非商品积分抵扣设置积分值为0
                if(!param.getSystemPointFlag()){
                    tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                }

                if (Objects.nonNull(itemGroup.getOrderTag()) && itemGroup.getOrderTag().getCommunityFlag() && nonNull(param.getSkuIdToActivityPriceMap().get(tradeItem.getSkuId()))){
                    tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                }

                // 订单积分价商品总积分
                if (nonNull(tradeItem.getBuyPoint())) {
                    tradePrice.setBuyPoints(tradeItem.getBuyPoint() * num + ObjectUtils.defaultIfNull(tradePrice.getBuyPoints(), NumberUtils.LONG_ZERO));
                }
            }

            //砍价订单  实际支付金额=订单金额-帮砍金额
            if(Objects.equals(Boolean.TRUE, tradeItemSnapshot.getBargain())) {
                BigDecimal bargainPrice = tradeItemSnapshot.getItemGroups().get(0).getTradeItems().get(0).getBargainPrice();
                tradePrice.setBargainPrice(bargainPrice);
                tradePrice.setTotalPrice(tradePrice.getTotalPrice().subtract(bargainPrice));
            }
            tradeConfirmItem.setTradePrice(tradePrice);
            tradeConfirmItem.setTradeItems(KsBeanUtil.convert(tradeItems, TradeItemVO.class));
            //判断每个订单项是否送校徽
            for (TradeItemVO tradeItemVo : tradeConfirmItem.getTradeItems()) {
                String skuName = tradeItemVo.getSkuName();
                String theSpecDetails = tradeItemVo.getSpecDetails();
                boolean needSchoolBadge = tradeItemAnalyzer.isNeedSchoolBadge(skuName,theSpecDetails);
                tradeItemVo.setNeedSendSchoolBadgeFlag(needSchoolBadge);
            }

            // 设置赠品
            tradeConfirmItem.setGifts(Lists.newArrayList());
            Map<Long, List<String>> giftskuIds =
                    ObjectUtils.defaultIfNull(itemGroup.getTradeMarketingList(),
                    Lists.<TradeMarketingDTO>newArrayList())
                    .stream()
                    .filter(g -> CollectionUtils.isNotEmpty(g.getGiftSkuIds()))
                    .collect(Collectors.toMap(TradeMarketingDTO::getMarketingId,
                            TradeMarketingDTO::getGiftSkuIds));
            giftskuIds.forEach((k, v) -> {
                v.forEach(goodsInfoId -> {
                    GoodsInfoVO goodsInfoVO = giftGoodsInfosMap.get(goodsInfoId);
                    GoodsVO goodsVO = giftGoodsMap.get(goodsInfoVO.getGoodsId());
                    TradeItem tradeItem = new TradeItem();
                    this.tradeConfirmQueryService.fillBaseGoodsInfo(goodsInfoVO, goodsVO, giftSpecDetailMap);
                    this.tradeConfirmQueryService.fillBaseTradeItem(
                            tradeItem, goodsInfoVO, null, null);
                    tradeItem.setNum(goodsInfoVO.getStock());
                    tradeItem.setMarketingIds(Collections.singletonList(k));
                    tradeItem.setMarketingLevelIds(Lists.newArrayList());
                    tradeItem.setPrice(BigDecimal.ZERO);
                    tradeItem.setOriginalPrice(BigDecimal.ZERO);
                    tradeItem.setSplitPrice(BigDecimal.ZERO);
                    tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                    tradeConfirmItem.getGifts().add(KsBeanUtil.convert(tradeItem, TradeItemVO.class));
                });
            });
            // 设置加价购商品
            tradeConfirmItem.setPreferential(Lists.newArrayList());
            Map<Long, List<String>> preferentialSkuIds = ObjectUtils.defaultIfNull(itemGroup.getTradeMarketingList(),
                            Lists.<TradeMarketingDTO>newArrayList())
                    .stream()
                    .filter(g -> CollectionUtils.isNotEmpty(g.getPreferentialSkuIds()))
                    .collect(Collectors.toMap(TradeMarketingDTO::getMarketingId,
                            TradeMarketingDTO::getPreferentialSkuIds));
            preferentialSkuIds.forEach((k, v) -> {
                v.forEach(goodsInfoId -> {
                    GoodsInfoVO goodsInfoVO = preferentialGoodsInfosMap.get(goodsInfoId);
                    GoodsVO goodsVO = preferentialGoodsMap.get(goodsInfoVO.getGoodsId());
                    TradeItem tradeItem = new TradeItem();
                    this.tradeConfirmQueryService.fillBaseGoodsInfo(goodsInfoVO, goodsVO, preferentialSpecDetailMap);
                    this.tradeConfirmQueryService.fillBaseTradeItem(
                            tradeItem, goodsInfoVO, null, null);
                    tradeItem.setNum(1L);
                    tradeItem.setMarketingIds(Lists.newArrayList());
                    tradeItem.setMarketingLevelIds(Lists.newArrayList());
                    tradeItem.setPrice(goodsInfoVO.getMarketPrice());
                    tradeItem.setOriginalPrice(goodsInfoVO.getMarketPrice());
                    tradeItem.setSplitPrice(goodsInfoVO.getMarketPrice());
                    tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                    tradeItem.setMarketingIds(Collections.singletonList(k));
                    tradeConfirmItem.getPreferential().add(KsBeanUtil.convert(tradeItem, TradeItemVO.class));

                    // 非商品积分抵扣设置积分值为0
                    if(!param.getSystemPointFlag()){
                        tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                    }
                    // 订单积分价商品总积分
                    if (nonNull(tradeItem.getBuyPoint())) {
                        tradePrice.setBuyPoints(tradeItem.getBuyPoint() + ObjectUtils.defaultIfNull(tradePrice.getBuyPoints(), NumberUtils.LONG_ZERO));
                    }
                });
            });

            // 处理营销描述，价格，赠品等信息
            this.tradePurchaseDealService.dealMarketingInfoNew(tradeConfirmItem, itemGroup.getTradeMarketingList(), countPriceItemVO);

            tradeConfirmResponse.getTradeConfirmItems().add(tradeConfirmItem);
        }
        param.setTradeConfirmResponse(tradeConfirmResponse);
    }

    @Override
    public void fillPriceAndStock(TradeConfirmParam param) {

    }


    @Override
    public void calcMarketing(TradeConfirmParam param) {
        boolean isBargain = Objects.equals(Boolean.TRUE, param.getTradeItemSnapshot().getBargain());
        // 设置购买总积分
        param.getTradeConfirmResponse().setTotalBuyPoint(
                param.getTradeConfirmResponse().getTradeConfirmItems()
                        .stream().map(TradeConfirmItemVO::getTradeItems)
                        .flatMap(Collection::stream)
                        .mapToLong(v -> ObjectUtils.defaultIfNull(v.getBuyPoint(), NumberUtils.LONG_ZERO) * v.getNum())
                        .sum()
        );
        if (Objects.nonNull(param.getTradeItemSnapshot().getOrderTag())
                && param.getTradeItemSnapshot().getOrderTag().getPickupCardFlag()){
            return;
        }
        // 填充礼品卡数量
        this.tradePurchaseDealService.dealGiftCard(param);
        if (!isBargain) {
            // 计算返利总额
            this.tradePurchaseDealService.dealTotalCommission(param);
            // 校验拼团信息
            this.tradePurchaseDealService.validGrouponOrder(param);
            // 校验组合购活动信息
            this.tradePurchaseDealService.validSuitOrder(param);
            // 填充优惠券列表
            this.tradePurchaseDealService.dealCouponCodes(param);
        } else {
            // 查询优惠券叠加开关是否打开
            ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
            configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
            configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            SystemConfigTypeResponse response =
                    systemConfigQueryProvider
                            .findByConfigTypeAndDelFlag(configQueryRequest)
                            .getContext();
            if (Objects.isNull(response.getConfig())
                    || Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
                return;
            }
            // 填充优惠券列表
            this.tradePurchaseDealService.dealCouponCodes(param);
        }
    }

    @Override
    public void calcPrice(TradeConfirmParam param) {
        // 提货卡支付金额0元
        if (param.getTradeConfirmResponse().getOrderTagVO().getPickupCardFlag()){
            param.getTradeConfirmResponse().getTradeConfirmItems().forEach(g -> {
                g.getTradePrice().setTotalPrice(BigDecimal.ZERO);
                // 提货卡付 0.01元
                BigDecimal pickupPayPrice = BigDecimal.valueOf(0.01);
                g.getTradePrice().setTotalPrice(pickupPayPrice);
            });
        }
    }

    @Override
    public void snapshot(TradeConfirmParam param) {

    }
}
