package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DistributionCommissionUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressByIdRequest;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderByIdRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.common.GoodsInfoTradeRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.bargain.BargainByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardDetailQueryRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardTradeRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardTradeResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.CommunityBuyRequest;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.GiftCardTradeCommitVO;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.common.SystemPointsConfigService;
import com.wanmi.sbc.order.optimization.trade1.commit.common.Trade1CommitPriceUtil;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitCheckInterface;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitGetDataInterface;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import com.wanmi.sbc.order.util.mapper.GoodsMapper;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressVerifyRequest;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1CommitGetDataService
 * @description TODO
 * @date 2022/3/1 2:16 下午
 */
@Slf4j
@Service
public class Trade1CommitGetDataService implements Trade1CommitGetDataInterface {

    @Autowired Trade1CommitCheckInterface trade1CommitCheckInterface;

    @Autowired TradeItemSnapshotService tradeItemSnapshotService;

    @Autowired GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired TradeCacheService tradeCacheService;

    @Autowired ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired DistributionCacheService distributionCacheService;

    @Autowired DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired SystemPointsConfigService systemPointsConfigService;

    @Autowired NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired CustomerQueryProvider customerQueryProvider;

    @Autowired PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired AuditQueryProvider auditQueryProvider;

    @Autowired GoodsMapper goodsMapper;

    @Autowired UserGiftCardProvider userGiftCardProvider;

    @Autowired
    CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Override
    public CustomerVO getCustomer(String operatorId) {
        if (StringUtils.isNotEmpty(operatorId)) {
            // 获取会员和等级
            CustomerGetByIdResponse customer =
                    customerQueryProvider
                            .getCustomerNoThirdImgById(new CustomerGetByIdRequest(operatorId))
                            .getContext();
            if (customer == null || !CheckState.CHECKED.equals(customer.getCheckState())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
            return customer;
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
    }

    @Override
    public TradeItemSnapshot getSnapshot(TradeCommitRequest request) {
        // 订单快照
        TradeItemSnapshot tradeItemSnapshot =
                tradeItemSnapshotService.getTradeItemSnapshot(request.getTerminalToken());
        if (tradeItemSnapshot == null) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050063);
        }
        return tradeItemSnapshot;
    }

    @Override
    public List<GoodsInfoTradeVO> getGoodsInfoData(
            TradeItemSnapshot tradeItemSnapshot, CustomerVO customer) {
        List<String> goodsInfoIds = new ArrayList<>();
        Map<String, Long> skuNum = new HashMap<>();
        tradeItemSnapshot
                .getItemGroups()
                .forEach(
                        g -> {
                            g.getTradeItems()
                                    .forEach(
                                            t -> {
                                                goodsInfoIds.add(t.getSkuId());
                                                skuNum.put(t.getSkuId(), t.getNum());
                                            });
                        });
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {

            List<GoodsInfoTradeVO> goodsInfoList =
                    goodsInfoQueryProvider
                            .getTradeGoodsInfoByIds(
                                    GoodsInfoTradeRequest.builder()
                                            .goodsInfoIds(goodsInfoIds)
                                            .customer(customer)
                                            .build())
                            .getContext();
            if (CollectionUtils.isEmpty(goodsInfoList)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050067);
            }

            //非开店礼包享受批发价
            if (DefaultFlag.NO.equals(tradeItemSnapshot.getItemGroups().get(0).getStoreBagsFlag())) {
                goodsInfoList.forEach(
                        g -> {
                            if (g.getSaleType().equals(SaleType.WHOLESALE.toValue())) {
                                BigDecimal price =
                                        Trade1CommitPriceUtil.getPrice(
                                                g, skuNum.get(g.getGoodsInfoId()),DefaultFlag.NO);
                                g.setMarketPrice(price);
                            }
                        });
            }
            return goodsInfoList;

        } else {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050063);
        }
    }

    @Override
    public List<StoreVO> getStoreInfoData(List<GoodsInfoTradeVO> goodsInfoList) {
        List<Long> paramIds = new ArrayList<>();
        List<Long> storeIds =
                goodsInfoList.stream()
                        .map(GoodsInfoTradeVO::getStoreId)
                        .distinct()
                        .collect(Collectors.toList());
        List<Long> providerStoreIds =
                goodsInfoList.stream()
                        .filter(g -> g.getProviderId() != null)
                        .map(GoodsInfoTradeVO::getProviderId)
                        .distinct()
                        .collect(Collectors.toList());
        paramIds.addAll(storeIds);
        if (CollectionUtils.isNotEmpty(providerStoreIds)) {
            paramIds.addAll(providerStoreIds);
        } else {
            providerStoreIds = Collections.EMPTY_LIST;
        }
        List<StoreVO> storeVOS = tradeCacheService.queryStoreList(paramIds);
        if (CollectionUtils.isEmpty(storeVOS) || storeVOS.size() != paramIds.size()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010105);
        }
        LocalDateTime now = LocalDateTime.now();
        storeVOS.forEach(
                storeVO -> {
                    if (!storeVO.getAuditState().equals(CheckState.CHECKED)) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010107);
                    }
                    if (StoreState.CLOSED.toValue() == storeVO.getStoreState().toValue()) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010109);
                    }
                    if (storeVO.getContractStartDate().isAfter(now)
                            || storeVO.getContractEndDate().isBefore(now)) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010110);
                    }
                });

        return storeVOS.stream()
                .filter(s -> storeIds.contains(s.getStoreId()))
                .collect(Collectors.toList());
    }

    @Autowired private BargainQueryProvider bargainQueryProvider;

    @Override
    public List<GoodsInfoTradeVO> getStock(
            List<GoodsInfoTradeVO> goodsInfoTradeVOS,
            TradeItemSnapshot snapshot) {
        List<String> goodsInfoIds = new ArrayList<>();
        //处理商品库存
        if(Objects.equals(Boolean.TRUE, snapshot.getBargain())) {
            Long stock = bargainQueryProvider.getStock(snapshot.getBargainId()).getContext();
            GoodsInfoTradeVO goodsInfoTradeVO = goodsInfoTradeVOS.get(0);
            goodsInfoTradeVO.setStock(stock);
            if (stock.compareTo(1L) < 0) {
                goodsInfoTradeVO.setGoodsStatus(GoodsStatus.OUT_STOCK);
            } else {
                goodsInfoTradeVO.setGoodsStatus(GoodsStatus.OK);
            }
        } else {
            goodsInfoTradeVOS.forEach(
                    g -> {
                        if (g.getGoodsStatus().equals(GoodsStatus.OK)) {
                            if (g.getThirdPlatformType() == null) {
                                if (StringUtils.isNotEmpty(g.getProviderGoodsInfoId())) {
                                    goodsInfoIds.add(g.getProviderGoodsInfoId());
                                } else {
                                    goodsInfoIds.add(g.getGoodsInfoId());
                                }
                            }
                        }
                    });
        }

        // 正常商品库存/ 供应商商品库存F
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            Map<String, Long> skuStockMap =
                    goodsInfoQueryProvider
                            .getStockByGoodsInfoIds(
                                    GoodsInfoListByIdsRequest.builder()
                                            .goodsInfoIds(goodsInfoIds)
                                            .build())
                            .getContext();
            if (MapUtils.isNotEmpty(skuStockMap)) {
                goodsInfoTradeVOS.stream()
                        .filter(g -> GoodsStatus.OK.equals(g.getGoodsStatus()))
                        .forEach(
                                g -> {
                                    Long stock = 0L;
                                    if (StringUtils.isNotEmpty(g.getProviderGoodsInfoId())) {
                                        stock = skuStockMap.get(g.getProviderGoodsInfoId());
                                    } else {
                                        stock = skuStockMap.get(g.getGoodsInfoId());
                                    }
                                    g.setStock(stock);
                                    if (stock == null || stock < 1) {
                                        g.setGoodsStatus(GoodsStatus.OUT_STOCK);
                                    }
                                });
            }
        }

        // 校验商品状态
        trade1CommitCheckInterface.checkGoods(goodsInfoTradeVOS, snapshot);
        return goodsInfoTradeVOS;
    }

    @Override
    public List<GoodsInfoTradeVO> getDistribution(
            List<GoodsInfoTradeVO> goodsInfoList, TradeCommitRequest request) {
        List<Long> storeIds = new ArrayList<>();
        List<String> distributionIds = new ArrayList<>();
        // 分销开关
        DefaultFlag distributionFlag = distributionCacheService.queryOpenFlag();
        goodsInfoList.forEach(
                g -> {
                    if (DefaultFlag.YES.equals(distributionFlag) && g.getDistributionGoodsAudit().equals(DistributionGoodsAudit.CHECKED)) {
                        storeIds.add(g.getStoreId());
                        distributionIds.add(g.getGoodsInfoId());
                    } else if (DefaultFlag.NO.equals(distributionFlag)){
                        g.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                    }
                });

        Map<Long, DefaultFlag> storeDistributionMap = new HashMap<>();
        if (distributionFlag.equals(DefaultFlag.YES)) {
            request.setOpenFlag(DefaultFlag.YES);
            storeDistributionMap.putAll(
                    distributionCacheService.queryStoreListOpenFlag(new ArrayList<>(storeIds)));
        }
        // 设置分销等级价
        if (distributionFlag.equals(DefaultFlag.YES)
                && CollectionUtils.isNotEmpty(distributionIds)) {

            BaseResponse<DistributorLevelByCustomerIdResponse> baseResponse =
                    request.getOperator().getUserId() == null
                            ? null
                            : distributorLevelQueryProvider.getByCustomerId(
                                    new DistributorLevelByCustomerIdRequest(
                                            request.getOperator().getUserId()));
            goodsInfoList.stream()
                    .filter(g -> DistributionGoodsAudit.CHECKED == g.getDistributionGoodsAudit())
                    .forEach(
                            g -> {
                                if (distributionFlag.equals(DefaultFlag.NO)
                                        || storeDistributionMap.get(g.getStoreId()) == null
                                        || storeDistributionMap
                                                .get(g.getStoreId())
                                                .equals(DefaultFlag.NO)) {
                                    g.setDistributionGoodsAudit(
                                            DistributionGoodsAudit.COMMON_GOODS);
                                }
                                // 不是分销员则佣金为：0
                                if ((StringUtils.isBlank(request.getShareUserId())
                                                || Constants.PURCHASE_DEFAULT.equals(
                                                        request.getShareUserId()))
                                        && Objects.nonNull(baseResponse)
                                        && Objects.nonNull(
                                                baseResponse
                                                        .getContext()
                                                        .getDistributorLevelVO())) {
                                    DistributorLevelVO distributorLevelVO =
                                            baseResponse.getContext().getDistributorLevelVO();
                                    if (Objects.nonNull(g.getDistributionCommission())
                                            && Objects.nonNull(
                                                    distributorLevelVO.getCommissionRate())) {
                                        BigDecimal commissionRate =
                                                distributorLevelVO.getCommissionRate();
                                        BigDecimal distributionCommission =
                                                g.getDistributionCommission();
                                        distributionCommission =
                                                DistributionCommissionUtils
                                                        .calDistributionCommission(
                                                                distributionCommission,
                                                                commissionRate);
                                        g.setDistributionCommission(distributionCommission);
                                    }
                                } else {
                                    g.setDistributionCommission(BigDecimal.ZERO);
                                }
                            });
        }
        return goodsInfoList;
    }

    @Override
    public SystemPointsConfigQueryResponse getPointSetting(
            List<GoodsInfoTradeVO> goodsInfoList, TradeCommitRequest request) {
        SystemPointsConfigQueryResponse response = systemPointsConfigService.querySettingCache();
        // 积分抵扣

        if (EnableStatus.DISABLE.equals(response.getStatus())
                || PointsUsageFlag.GOODS.equals(response.getPointsUsageFlag())) {
            if (request.getPoints() != null && request.getPoints() > 0L) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050110);
            }
        }
        if (EnableStatus.ENABLE.equals(response.getStatus())
                && PointsUsageFlag.ORDER.equals(response.getPointsUsageFlag())) {
            goodsInfoList.forEach(
                    g -> {
                        g.setBuyPoint(0L);
                    });
        }
        return response;
    }


    @Autowired private SystemConfigQueryProvider systemConfigQueryProvider;

    @Override
    public GoodsTradePluginResponse getMarketing(
            List<GoodsInfoTradeVO> goodsInfoList,
            TradeItemSnapshot snapshot,
            CustomerVO customerVO) {

        List<GoodsInfoSimpleVO> goodsInfoSimpleVOS =
                goodsMapper.goodsInfoTradeVOsToGoodsInfoSimpleVOs(goodsInfoList);
        GoodsListPluginRequest goodsListPluginRequest = new GoodsListPluginRequest();
        goodsListPluginRequest.setGoodsInfoPluginRequests(goodsInfoSimpleVOS);
        goodsListPluginRequest.setCustomerId(customerVO.getCustomerId());

        if (Objects.equals(Boolean.TRUE, snapshot.getBargain())) {
            //验证是否可以叠加使用优惠券
            ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
            configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
            configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            SystemConfigTypeResponse response =
                    systemConfigQueryProvider
                            .findByConfigTypeAndDelFlag(configQueryRequest)
                            .getContext();

            if(Objects.isNull(response.getConfig())
                    || Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
                GoodsTradePluginResponse marketingResponse = new GoodsTradePluginResponse();
                marketingResponse.setSkuMarketingLabelMap(new HashMap<>());
                return marketingResponse;
            }
            goodsListPluginRequest.setMarketingPluginType(MarketingPluginType.COUPON);
            goodsListPluginRequest.setHandlePosit(Boolean.FALSE);
        } else {
            TradeItemGroup tradeItemGroup = snapshot.getItemGroups().get(0);
            // 开店礼包、组合购不需要走营销插件
            if (tradeItemGroup.getSuitMarketingFlag()
                    || tradeItemGroup.getStoreBagsFlag().equals(DefaultFlag.YES)) {
                // 组合购积分+现金   积分不生效
                if (tradeItemGroup.getSuitMarketingFlag()) {
                    goodsInfoList.forEach(g -> g.setBuyPoint(0L));
                }
                return new GoodsTradePluginResponse();
            }
            // 如果不是企业会员则需要过滤企业价，设置企业会员标识
            if (customerVO.getEnterpriseCheckState() != null
                    && customerVO.getEnterpriseCheckState().equals(EnterpriseCheckState.CHECKED)) {
                goodsListPluginRequest.setIepCustomerFlag(true);
            }
        }
        GoodsTradePluginResponse pluginResponse =
                newMarketingPluginProvider.commitPlugin(goodsListPluginRequest).getContext();

        List<TradeMarketingDTO> tradeMarketingList  = new ArrayList<>();
        Map<String, TradeMarketingDTO> tradeMarketingMap = new HashMap<>();
        snapshot.getItemGroups().stream().forEach(tradeItemGroup -> {
            if (CollectionUtils.isNotEmpty(tradeItemGroup.getTradeMarketingList())) {
                tradeMarketingList.addAll(tradeItemGroup.getTradeMarketingList());
            }
        });
        if (CollectionUtils.isNotEmpty(tradeMarketingList)) {
            for (TradeMarketingDTO tradeMarketingDTO : tradeMarketingList) {
                tradeMarketingMap.put(tradeMarketingDTO.getMarketingId().toString(), tradeMarketingDTO);
            }
        }
        //过滤赠品为空的满赠活动
        Map<String, TradeMarketingDTO> finalTradeMarketingMap = tradeMarketingMap;
        List<String> deleteMarketingId = new ArrayList<>();
        pluginResponse.getSkuMarketingLabelMap().entrySet()
                .forEach(
                        entry -> {
                            if (CollectionUtils.isNotEmpty(entry.getValue())) {
                                List<MarketingPluginLabelDetailVO> retList = new ArrayList<>();
                                entry.getValue()
                                        .forEach(
                                                m -> {
                                                    if (m.getMarketingType().equals(MarketingPluginType.GIFT.getId())) {
                                                        if (finalTradeMarketingMap.containsKey(m.getMarketingId().toString())
                                                                && CollectionUtils.isNotEmpty(finalTradeMarketingMap.get(m.getMarketingId().toString()).getGiftSkuIds())) {
                                                            retList.add(m);
                                                        } else {
                                                            deleteMarketingId.add(m.getMarketingId().toString());
                                                        }
                                                    } else if (m.getMarketingType().equals(MarketingPluginType.PREFERENTIAL.getId())) {
                                                        if (finalTradeMarketingMap.containsKey(m.getMarketingId().toString())
                                                                && CollectionUtils.isNotEmpty(finalTradeMarketingMap.get(m.getMarketingId().toString()).getPreferentialSkuIds())) {
                                                            retList.add(m);
                                                        } else {
                                                            deleteMarketingId.add(m.getMarketingId().toString());
                                                        }
                                                    } else {
                                                        retList.add(m);
                                                    }
                                                });
                                entry.setValue(retList);
                            }
                        });
        if (CollectionUtils.isNotEmpty(deleteMarketingId)) {
            snapshot.getItemGroups().stream().forEach(tradeItemGroup -> {
                if (CollectionUtils.isNotEmpty(tradeItemGroup.getTradeItems())) {
                    tradeItemGroup.getTradeItems().stream().forEach(tradeItem -> {
                        if (CollectionUtils.isNotEmpty(tradeItem.getMarketingIds())) {
                            List<Long> marketingIds = new ArrayList<>();
                            for (Long marketingId : tradeItem.getMarketingIds()) {
                                if (!deleteMarketingId.contains(marketingId.toString())) {
                                    marketingIds.add(marketingId);
                                }
                            }
                            tradeItem.setMarketingIds(marketingIds);
                        }

                    });
                }
                if (CollectionUtils.isNotEmpty(tradeItemGroup.getTradeMarketingList())) {
                    List<TradeMarketingDTO> tradeMarketingListNew = new ArrayList<>();
                    //符合以上条件修改快照
                    tradeItemGroup.getTradeMarketingList().forEach(m -> {
                        if (!deleteMarketingId.contains(m.getMarketingId().toString())) {
                            tradeMarketingListNew.add(m);
                        }
                    });
                    tradeItemGroup.setTradeMarketingList(tradeMarketingListNew);
                }
            });
        }
        return pluginResponse;
    }

    @Override
    public void getSetting() {}

    @Override
    public void getFreight() {}

    @Override
    public CustomerDeliveryAddressVO getAddress(
            TradeCommitRequest tradeCommitRequest, TradeItemSnapshot snapshot) {
        // 限售地址
        String addressId = "";
        // 校验是否需要完善地址信息
        Operator operator = tradeCommitRequest.getOperator();
        CustomerDeliveryAddressVO address = null;
        OrderTag orderTag =
                Objects.isNull(snapshot.getItemGroups().get(0).getOrderTag())
                        ? new OrderTag()
                        : snapshot.getItemGroups().get(0).getOrderTag();
        // 是否是虚拟订单或者卡券订单
        boolean isVirtual = orderTag.getVirtualFlag() || orderTag.getElectronicCouponFlag();
        // 是否是周期购订单
        Boolean buyCycleFlag = orderTag.getBuyCycleFlag();
        if (buyCycleFlag) {
            List<StoreCommitInfoDTO> storeCommitInfoList = tradeCommitRequest.getStoreCommitInfoList();
            //周期购的配送仅支持快递
            Optional<StoreCommitInfoDTO> optional = storeCommitInfoList.parallelStream()
                    .filter(storeCommitInfoDTO -> !Objects.equals(DeliverWay.EXPRESS, storeCommitInfoDTO.getDeliverWay())).findFirst();
            if (optional.isPresent()) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        // 如果是实体订单
        if (!(isVirtual)) {
            if (StringUtils.isEmpty(tradeCommitRequest.getConsigneeId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (StringUtils.isEmpty(tradeCommitRequest.getConsigneeAddress())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            address =
                    customerDeliveryAddressQueryProvider
                            .getById(
                                    new CustomerDeliveryAddressByIdRequest(
                                            tradeCommitRequest.getConsigneeId()))
                            .getContext();
            if (address == null
                    || StringUtils.isBlank(address.getDeliveryAddressId())
                    || address.getDelFlag().equals(DeleteFlag.YES)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
            }
        } else {
            // 虚拟订单或者卡券订单取地址
            CustomerDeliveryAddressRequest addressRequest = new CustomerDeliveryAddressRequest();
            addressRequest.setCustomerId(operator.getUserId());
            address =
                    customerDeliveryAddressQueryProvider
                            .getDefaultOrAnyOneByCustomerId(addressRequest)
                            .getContext();
            // 如果有，就设置，没有则空
            if (Objects.nonNull(address)) {
                tradeCommitRequest.setConsigneeId(address.getDeliveryAddressId());
                PlatformAddressListRequest request =
                        PlatformAddressListRequest.builder()
                                .addrIdList(
                                        Lists.newArrayList(
                                                String.valueOf(address.getProvinceId()),
                                                String.valueOf(address.getCityId()),
                                                String.valueOf(address.getAreaId()),
                                                String.valueOf(address.getStreetId())))
                                .delFlag(DeleteFlag.NO)
                                .build();
                List<PlatformAddressVO> platformAddressVOList =
                        platformAddressQueryProvider
                                .list(request)
                                .getContext()
                                .getPlatformAddressVOList();
                String consigneeAddress = "";
                PlatformAddressVO addressVO = platformAddressVOList.stream()
                        .filter(a -> StringUtils.isNotBlank(a.getAddrName()))
                        .findFirst().orElse(null);
                // 考虑收货详情地址是否存在省份名称，如果不含就加上
                if(Objects.nonNull(addressVO) && !address.getDeliveryAddress().contains(addressVO.getAddrName())){
                    consigneeAddress = platformAddressVOList.parallelStream()
                            .map(PlatformAddressVO::getAddrName)
                            .collect(Collectors.joining());
                }
                consigneeAddress = consigneeAddress.concat(address.getDeliveryAddress());
                //拼接收货地址楼层号
                if (Objects.nonNull(addressVO) && StringUtils.isNotBlank(address.getHouseNum())
                        && !consigneeAddress.contains(address.getHouseNum())) {
                    consigneeAddress = consigneeAddress.concat(address.getHouseNum());
                }
                tradeCommitRequest.setConsigneeAddress(consigneeAddress);
            }
            tradeCommitRequest
                    .getStoreCommitInfoList()
                    .forEach(
                            storeCommitInfoDTO -> {
                                if (storeCommitInfoDTO.getInvoiceType() != -1) {
                                    // 如果不是否单独的开票收货地址，则参数错误，虚拟订单或者卡券订单必须是单独的开票地址
                                    if (!Boolean.TRUE.equals(
                                            storeCommitInfoDTO.isSpecialInvoiceAddress())) {
                                        throw new SbcRuntimeException(
                                                OrderErrorCodeEnum.K050156);
                                    }
                                    // 发票的收货地址ID,如果需要开票,则必传
                                    if (StringUtils.isEmpty(
                                            storeCommitInfoDTO.getInvoiceAddressId())) {
                                        throw new SbcRuntimeException(
                                                OrderErrorCodeEnum.K050156);
                                    }
                                    // 收货地址详细信息（包含省市区），如果需要开票,则必传
                                    if (StringUtils.isEmpty(
                                            storeCommitInfoDTO.getInvoiceAddressDetail())) {
                                        throw new SbcRuntimeException(
                                                OrderErrorCodeEnum.K050156);
                                    }
                                }
                            });
        }

        if (address != null) {

            if (!operator.getUserId().equals(address.getCustomerId())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050149);
            }
            PlatformAddressVerifyRequest platformAddressVerifyRequest =
                    new PlatformAddressVerifyRequest();
            if (Objects.nonNull(address.getProvinceId())) {
                addressId += address.getProvinceId() + "|";
                platformAddressVerifyRequest.setProvinceId(String.valueOf(address.getProvinceId()));
            }
            if (Objects.nonNull(address.getCityId())) {
                addressId += address.getCityId() + "|";
                platformAddressVerifyRequest.setCityId(String.valueOf(address.getCityId()));
            }
            if (Objects.nonNull(address.getAreaId())) {
                addressId += address.getAreaId() + "|";
                platformAddressVerifyRequest.setAreaId(String.valueOf(address.getAreaId()));
            }
            if (Objects.nonNull(address.getStreetId())) {
                addressId += address.getStreetId() + "|";
                platformAddressVerifyRequest.setStreetId(String.valueOf(address.getStreetId()));
            }
            if (Boolean.TRUE.equals(
                    platformAddressQueryProvider
                            .verifyAddressSimple(platformAddressVerifyRequest)
                            .getContext())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
            }

            // 门牌号为null时，设为空串，避免拼接时NPE
            address.setHouseNum(Optional.ofNullable(address.getHouseNum()).orElse(""));
        }
        tradeCommitRequest.setAddressId(addressId);
        return address;
    }

    /**
     * 查询订单在途退货设置
     *
     * @return
     */
    @Override
    public Boolean isTransitReturn() {
        TradeConfigGetByTypeRequest tradeConfigGetByTypeRequest = new TradeConfigGetByTypeRequest();
        tradeConfigGetByTypeRequest.setConfigType(ConfigType.ORDER_SETTING_ALONG_REFUND);
        Integer status =
                auditQueryProvider
                        .getTradeConfigByType(tradeConfigGetByTypeRequest)
                        .getContext()
                        .getStatus();
        return NumberUtils.INTEGER_ONE.equals(status) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public BargainVO getBargain(TradeItemSnapshot snapshot) {
        if (Objects.equals(Boolean.TRUE, snapshot.getBargain())) {
            BargainVO bargainVO = bargainQueryProvider.getByIdWithBargainGoods(BargainByIdRequest.builder().bargainId(snapshot.getBargainId()).build()).getContext();
            if (Objects.isNull(bargainVO)) {
                throw new SbcRuntimeException(
                        OrderErrorCodeEnum.K050154);
            }
            return bargainVO;
        }
        return null;
    }

    @Override
    public List<UserGiftCardInfoVO> getUserGiftCardInfo(List<GoodsInfoTradeVO> goodsInfoTradeVOList, TradeCommitRequest request, TradeItemSnapshot snapshot) {
        if (Objects.nonNull(snapshot.getOrderTag()) && Objects.nonNull(snapshot.getOrderTag().getPickupCardFlag()) && snapshot.getOrderTag().getPickupCardFlag()){
            UserGiftCardInfoVO userGiftCardInfoVO =
                    userGiftCardProvider.getUserGiftCardDetail(UserGiftCardDetailQueryRequest.builder()
                    .userGiftCardId(snapshot.getUserGiftCardId())
                    .customerId(snapshot.getBuyerId()).build())
                    .getContext()
                    .getUserGiftCardInfoVO();
            return Collections.singletonList(userGiftCardInfoVO);
        } else {
            if(CollectionUtils.isEmpty(request.getGiftCardTradeCommitVOList())) {
                return null;
            }
            List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
            // 处理加价购商品
            List<String> preferentialSkuIds = new ArrayList<>();
            for (TradeItemGroup tradeItem : snapshot.getItemGroups()) {
                if (CollectionUtils.isNotEmpty(tradeItem.getTradeMarketingList())) {
                    tradeItem.getTradeMarketingList().forEach(mar -> {
                        if (CollectionUtils.isNotEmpty(mar.getPreferentialSkuIds())) {
                            preferentialSkuIds.addAll(mar.getPreferentialSkuIds());
                        }
                    });
                }
            }
            if (CollectionUtils.isNotEmpty(preferentialSkuIds)) {
                GoodsInfoListByIdsResponse response = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(preferentialSkuIds).build()).getContext();
                if (Objects.nonNull(response)  && CollectionUtils.isNotEmpty(response.getGoodsInfos())) {
                    goodsInfoVOList.addAll(response.getGoodsInfos());
                }
            }
            if(CollectionUtils.isNotEmpty(goodsInfoTradeVOList)) {
                goodsInfoVOList.addAll(KsBeanUtil.convertList(goodsInfoTradeVOList, GoodsInfoVO.class));
            }
            if(CollectionUtils.isEmpty(goodsInfoVOList)) {
                return null;
            }
            List<Long> userGiftCardIdList = request.getGiftCardTradeCommitVOList().stream().map(GiftCardTradeCommitVO::getUserGiftCardId).collect(Collectors.toList());
            // 封装信息
            UserGiftCardTradeRequest userGiftCardTradeRequest = new UserGiftCardTradeRequest();
            userGiftCardTradeRequest.setCustomerId(request.getOperator().getUserId());
            userGiftCardTradeRequest.setGoodsInfoVOList(goodsInfoVOList);
            userGiftCardTradeRequest.setUserGiftCardIdList(userGiftCardIdList);
            UserGiftCardTradeResponse tradeResponse = userGiftCardProvider.tradeUserGiftCard(userGiftCardTradeRequest).getContext();
            return tradeResponse.getValidGiftCardInfoVO();
        }
    }

    /**
     * @param tradeItemSnapshot
     * @return com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse
     * @description 社区团购活动信息
     * @author edz
     * @date: 2023/7/26 17:37
     */
    @Override
    public CommunityActivityByIdResponse getCommunityActivity(TradeItemSnapshot tradeItemSnapshot) {
        CommunityBuyRequest communityBuyRequest = tradeItemSnapshot.getItemGroups().get(0).getCommunityBuyRequest();
        return communityActivityQueryProvider.getById(CommunityActivityByIdRequest.builder()
                                .skuRelFlag(Boolean.TRUE)
                                .saleRelFlag(Boolean.TRUE)
                                .commissionRelFlag(Boolean.TRUE)
                                .leaderId(communityBuyRequest.getLeaderId())
                                .activityId(communityBuyRequest.getActivityId()).build())
                        .getContext();
    }

    /**
     * @description 社区团购自提点信息
     * @author  edz
     * @date: 2023/7/27 15:27
     * @return com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO
     */
    @Override
    public List<CommunityLeaderPickupPointVO> getCommunityPickupPoint(String communityPickUpId, String leaderId){
        if (Objects.isNull(communityPickUpId) && Objects.isNull(leaderId)) return null;
        return communityLeaderPickupPointQueryProvider.list(CommunityLeaderPickupPointListRequest.builder()
                .pickupPointId(communityPickUpId)
                .leaderId(leaderId)
                .build())
                .getContext()
                .getCommunityLeaderPickupPointList();
    }

    /**
     * @param leaderId
     * @return com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO
     * @description 社区团购团长信息
     * @author edz
     * @date: 2023/7/27 15:36
     */
    @Override
    public CommunityLeaderVO getLeaderInfo(String leaderId) {
        if (Objects.isNull(leaderId)) return null;
        return communityLeaderQueryProvider.getById(CommunityLeaderByIdRequest.builder().leaderId(leaderId).build())
                .getContext()
                .getCommunityLeaderVO();
    }
}
