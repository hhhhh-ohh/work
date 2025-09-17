package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.invoice.InvoiceProjectSwitchQueryProvider;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectSwitchByCompanyInfoIdRequest;
import com.wanmi.sbc.account.api.response.invoice.InvoiceProjectSwitchByCompanyInfoIdResponse;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelGoodsQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelCheckSkuStateRequest;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.SkuSellingPriceResponse;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponrecord.GrouponRecordQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsListRequest;
import com.wanmi.sbc.marketing.api.request.buyoutprice.MarketingBuyoutPriceIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeValidOrderCommitRequest;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftLevelListByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.grouponrecord.GrouponRecordByCustomerRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeValidOrderCommitResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingGetByIdResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.constant.MarketingPluginConstant;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.bean.enums.GrouponOrderCheckStatus;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.PickSettingInfoVO;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitCheckInterface;
import com.wanmi.sbc.order.trade.model.entity.PickSettingInfo;
import com.wanmi.sbc.order.trade.model.entity.TradeGroupForm;
import com.wanmi.sbc.order.trade.model.entity.TradeGrouponCommitForm;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.GrouponInstance;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.util.mapper.OrderMapper;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingListRequest;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingConfigResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1CommitCheckService
 * @description TODO
 * @date 2022/2/21 3:18 下午
 */
@Slf4j
@Service
public class Trade1CommitCheckService implements Trade1CommitCheckInterface {

    @Autowired SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired MarketingQueryProvider marketingQueryProvider;

    @Autowired GrouponRecordQueryProvider grouponRecordQueryProvider;

    @Autowired GrouponOrderService grouponOrderService;

    @Autowired InvoiceProjectSwitchQueryProvider invoiceProjectSwitchQueryProvider;

    @Autowired PickupSettingQueryProvider pickupSettingQueryProvider;

    @Autowired CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired BookingSaleGoodsQueryProvider bookingSaleGoodsQueryProvider;

    @Autowired GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired DistributionSettingQueryProvider distributionSettingQueryProvider;

    @Autowired RedisUtil redisService;

    @Autowired OrderMapper orderMapper;

    @Autowired
    BargainQueryProvider bargainQueryProvider;

    @Autowired ChannelGoodsQueryProvider channelGoodsQueryProvider;

    @Autowired GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Override
    public void checkCustomerAndPoint(Trade1CommitParam param, TradeCommitRequest request) {

        SystemPointsConfigQueryResponse pointConfig = param.getSystemPointsConfigQueryResponse();
        if (EnableStatus.ENABLE.equals(pointConfig.getStatus())) {
            Long sum = 0L;
            if (pointConfig.getPointsUsageFlag().equals(PointsUsageFlag.GOODS)) {
                sum =
                        param.getGoodsInfoTradeVOS().stream()
                                .filter(g -> g.getBuyPoint() != null)
                                .mapToLong(GoodsInfoTradeVO::getBuyPoint)
                                .sum();
            } else {
                sum = request.getPoints();
            }
            if (sum != null && sum > param.getCustomerVO().getPointsAvailable()) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050090);
            }
        }
    }

    @Override
    public void checkPayType(PayType payType) {
        // 如果选择的是线下支付
        if (payType == PayType.OFFLINE) {
            Integer status;

            // 先从redis缓存获取
            String cacheStatus = redisService.getString(CacheKeyConstant.OFFLINE_PAY_SETTING);

            if (StringUtils.isBlank(cacheStatus)) {
                // redis缓存获取不到则查库获取
                status = systemConfigQueryProvider.getOfflinePaySetting().getContext().getStatus();
            } else {
                status = Integer.valueOf(cacheStatus);
            }

            if (status == null || status == EnableStatus.DISABLE.toValue()) {
                throw new SbcRuntimeException(SettingErrorCodeEnum.K070019);
            }
        }
    }

    @Override
    public void checkSuitMarketing(
            TradeItemSnapshot itemSnapshot, String customerId, boolean isForceCommit) {
        TradeItemGroup tradeItemGroup = itemSnapshot.getItemGroups().get(0);
        // 开店礼包、组合购不需要走营销插件
        if (tradeItemGroup.getSuitMarketingFlag()) {
            // 获取并校验组合购活动信息
            MarketingGetByIdRequest marketingSuitsValidRequest = new MarketingGetByIdRequest();
            marketingSuitsValidRequest.setMarketingId(
                    tradeItemGroup
                            .getTradeMarketingList()
                            .get(NumberUtils.INTEGER_ZERO)
                            .getMarketingId());

            BaseResponse<MarketingGetByIdResponse> marketingSuits =
                    marketingQueryProvider.getById(marketingSuitsValidRequest);
            MarketingVO marketingVO = marketingSuits.getContext().getMarketingVO();
            if (marketingVO.getIsPause() == BoolFlag.YES
                    || marketingVO.getDelFlag() == DeleteFlag.YES
                    || marketingVO.getBeginTime().isAfter(LocalDateTime.now())
                    || marketingVO.getEndTime().isBefore(LocalDateTime.now())) {
                if (isForceCommit) {
                    tradeItemGroup.setSuitMarketingFlag(false);
                    tradeItemGroup.setTradeMarketingList(null);
                    tradeItemGroup.getTradeItems().forEach(item -> item.setMarketingIds(null));

                } else {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050161);
                }
            }
        }
    }

    @Override
    public void checkStoreBags(TradeItemSnapshot itemSnapshot) {
        if (DefaultFlag.YES.equals(itemSnapshot.getItemGroups().get(0).getStoreBagsFlag())) {
            Boolean isStoreBag =
                    distributionSettingQueryProvider
                            .isStoreBag(
                                    itemSnapshot
                                            .getItemGroups()
                                            .get(0)
                                            .getTradeItems()
                                            .get(0)
                                            .getSkuId())
                            .getContext();
            if (!isStoreBag) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
            }

        }
    }

    @Override
    public void checkMarketing(Trade1CommitParam param, TradeCommitRequest request) {
        Map<String, List<MarketingPluginLabelDetailVO>> marketingMap =
                param.getGoodsTradePluginResponse().getSkuMarketingLabelMap();
        TradeItemSnapshot tradeItemSnapshot = param.getSnapshot();
        String customerId = param.getCustomerVO().getCustomerId();
        tradeItemSnapshot
                .getItemGroups()
                .forEach(
                        group -> {
                            // 过滤开店礼包和组合购，上面已经做了判断
                            if (group.getSuitMarketingFlag()
                                    || DefaultFlag.YES.equals(group.getStoreBagsFlag())) {
                                return;
                            }
                            group.getTradeItems()
                                    .forEach(
                                            item -> {
                                                List<MarketingPluginLabelDetailVO>
                                                        labelDetailVOList =
                                                                marketingMap == null
                                                                        ? null
                                                                        : marketingMap.get(
                                                                                item.getSkuId());
                                                Map<Integer, MarketingPluginLabelDetailVO>
                                                        marketingTypeMap = new HashMap<>();
                                                if (CollectionUtils.isNotEmpty(labelDetailVOList)) {

                                                    marketingTypeMap =
                                                            labelDetailVOList.stream()
                                                                    .collect(
                                                                            Collectors.toMap(
                                                                                    MarketingPluginLabelDetailVO
                                                                                            ::getMarketingType,
                                                                                    Function
                                                                                            .identity()));
                                                } else {
                                                    labelDetailVOList = new ArrayList<>();
                                                }

                                                // 预售活动判断
                                                this.checkBookingMarketing(item, marketingTypeMap);

                                                // 预约活动判断
                                                this.checkAppointmentMarketing(
                                                        item, marketingTypeMap);

                                                // 拼团
                                                this.checkGrouponMarketing(
                                                        group, marketingTypeMap, customerId);

                                                // 判断营销符不符合活动（满减、满折、满赠、打包一口价、第二件半件）
                                                this.checkPublicMarketing(
                                                        item,
                                                        marketingTypeMap,
                                                        group,
                                                        request.isForceCommit());
                                            });
                        });
    }

    // 校验限售
    @Override
    public void checkRestricted(Trade1CommitParam param, TradeCommitRequest request) {
        Map<String, GoodsInfoTradeVO> goodsInfoMap =
                param.getGoodsInfoTradeVOS().stream()
                        .collect(
                                Collectors.toMap(
                                        GoodsInfoTradeVO::getGoodsInfoId, Function.identity()));
        param.getSnapshot()
                .getItemGroups()
                .forEach(
                        tradeGroup -> {
                            // 开店礼包，组合购不要判断限售
                            if (tradeGroup.getSuitMarketingFlag()
                                    || DefaultFlag.YES.equals(tradeGroup.getStoreBagsFlag())) {
                                return;
                            }
                            tradeGroup
                                    .getTradeItems()
                                    .forEach(
                                            tradeItem -> {
                                                GoodsInfoTradeVO goodsInfo =
                                                        goodsInfoMap.get(tradeItem.getSkuId());
                                                List<MarketingPluginLabelDetailVO>
                                                        marketingPluginLabelDetailVOS =
                                                                goodsInfo
                                                                        .getMarketingPluginLabels();
                                                if (CollectionUtils.isNotEmpty(
                                                        marketingPluginLabelDetailVOS)) {
                                                    Map<Integer, MarketingPluginLabelDetailVO>
                                                            marketingTypeMap =
                                                                    marketingPluginLabelDetailVOS
                                                                            .stream()
                                                                            .collect(
                                                                                    Collectors
                                                                                            .toMap(
                                                                                                    MarketingPluginLabelDetailVO
                                                                                                            ::getMarketingType,
                                                                                                    Function
                                                                                                            .identity()));
                                                    // 拼团走单独限售
                                                    if (marketingTypeMap.containsKey(
                                                            MarketingPluginType.GROUPON.getId())) {
                                                        // 已超过拼团商品限购数
                                                        GrouponRecordVO record =
                                                                grouponRecordQueryProvider
                                                                        .getByCustomerIdAndGoodsInfoId(
                                                                                new GrouponRecordByCustomerRequest(
                                                                                        marketingTypeMap
                                                                                                .get(
                                                                                                        MarketingPluginType
                                                                                                                .GROUPON
                                                                                                                .getId())
                                                                                                .getMarketingId(),
                                                                                        param.getCustomerVO()
                                                                                                .getCustomerId(),
                                                                                        goodsInfo
                                                                                                .getGoodsInfoId()))
                                                                        .getContext()
                                                                        .getGrouponRecordVO();

                                                        // 限售数如果为0，则不限制拼团数量
                                                        if (Objects.nonNull(record)
                                                                && record.getLimitSellingNum()
                                                                        != 0) {

                                                            if (Objects.nonNull(record)
                                                                    && Objects.nonNull(
                                                                            record
                                                                                    .getLimitSellingNum())
                                                                    && (record.getBuyNum()
                                                                                    + tradeItem
                                                                                            .getNum())
                                                                            > record
                                                                                    .getLimitSellingNum()) {
                                                                throw new SbcRuntimeException(
                                                                        OrderErrorCodeEnum.K050098);
                                                            } else {
                                                                // 第一次购买拼团商品
                                                                if (Objects.nonNull(
                                                                                record
                                                                                        .getLimitSellingNum())
                                                                        && tradeItem.getNum()
                                                                                > record
                                                                                        .getLimitSellingNum()) {
                                                                    throw new SbcRuntimeException(
                                                                            OrderErrorCodeEnum.K050098);
                                                                }
                                                            }
                                                        }
                                                        return;
                                                    }
                                                    // 预售判断预售数据量
                                                    if (marketingTypeMap.containsKey(
                                                            MarketingPluginType.BOOKING_SALE
                                                                    .getId())) {
                                                        List<BookingSaleGoodsVO>
                                                                bookingSaleGoodsVOList =
                                                                        bookingSaleGoodsQueryProvider
                                                                                .list(
                                                                                        BookingSaleGoodsListRequest
                                                                                                .builder()
                                                                                                .goodsInfoId(
                                                                                                        tradeItem
                                                                                                                .getSkuId())
                                                                                                .bookingSaleId(
                                                                                                        marketingTypeMap
                                                                                                                .get(
                                                                                                                        MarketingPluginType
                                                                                                                                .GROUPON
                                                                                                                                .getId())
                                                                                                                .getMarketingId())
                                                                                                .build())
                                                                                .getContext()
                                                                                .getBookingSaleGoodsVOList();
                                                        if (bookingSaleGoodsVOList
                                                                        .get(0)
                                                                        .getCanBookingCount()
                                                                < tradeItem.getNum()) {
                                                            // 预售商品可售数量不足
                                                            throw new SbcRuntimeException(
                                                                    OrderErrorCodeEnum.K050152);
                                                        }
                                                    }
                                                }
                                            });
                        });
        TradeItemGroup tradeItemGroup = param.getSnapshot().getItemGroups().get(0);
        // 开店礼包，组合购不要判断限售
        if (tradeItemGroup.getSuitMarketingFlag()
                || DefaultFlag.YES.equals(tradeItemGroup.getStoreBagsFlag())) {
            return;
        }
        // 重新校验限售，带有地区和自提的
        Boolean openGroup = Boolean.FALSE;
        if (Objects.nonNull(tradeItemGroup.getGrouponForm())
                && Objects.nonNull(
                tradeItemGroup
                                .getGrouponForm()
                                .getOpenGroupon())) {
            openGroup =
                    tradeItemGroup.getGrouponForm().getOpenGroupon();
        }
        Boolean storeBagsFlag = Boolean.FALSE;
        if (DefaultFlag.YES.equals(tradeItemGroup.getStoreBagsFlag())) {
            storeBagsFlag = Boolean.TRUE;
        }
        // 组装请求的数据
        Map<Long, List<String>> storeSkus = new HashMap<>();
        List<GoodsRestrictedValidateVO> list = new ArrayList<>();
        param.getSnapshot()
                .getItemGroups()
                .forEach(
                        g -> {
                            g.getTradeItems()
                                    .forEach(
                                            item -> {
                                                list.add(
                                                        GoodsRestrictedValidateVO.builder()
                                                                .skuId(item.getSkuId())
                                                                .num(item.getNum())
                                                                .build());
                                            });
                            storeSkus.put(
                                    g.getSupplier().getStoreId(),
                                    g.getTradeItems().stream()
                                            .map(TradeItem::getSkuId)
                                            .collect(Collectors.toList()));
                        });

        List<String> pickUpSkuIds = new ArrayList<>();
        if (MapUtils.isNotEmpty(param.getPickSettingInfoMap())) {
            param.getPickSettingInfoMap()
                    .keySet()
                    .forEach(
                            k -> {
                                pickUpSkuIds.addAll(storeSkus.get(k));
                            });
        }
        // 取第1个商品的类型
        Integer goodsType = param.getGoodsInfoTradeVOS().stream().findFirst().map(GoodsInfoTradeVO::getGoodsType).orElse(null);
        goodsRestrictedSaleQueryProvider.validateOrderRestricted(
                GoodsRestrictedBatchValidateRequest.builder()
                        .goodsRestrictedValidateVOS(list)
                        .snapshotType(param.getSnapshot().getSnapshotType())
                        .customerVO(param.getCustomerVO())
                        .openGroupon(openGroup)
                        .storeId(null)
                        .storeBagsFlag(storeBagsFlag)
                        .addressId(request.getAddressId())
                        .pickUpSkuIds(pickUpSkuIds)
                        .goodsType(goodsType)
                        .buyCycleFlag(Objects.nonNull(tradeItemGroup.getTradeBuyCycleDTO()))
                        .build());
    }

    @Override
    public Map<Long, PickSettingInfo> checkPickUp(
            TradeCommitRequest request, Trade1CommitParam param) {
        Map<Long, PickSettingInfo> retMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(request.getPickUpInfos())) {
            Map<Long, StoreVO> storeVOMap =
                    param.getStoreVOS().stream()
                            .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            // 订单快照中自营店铺
            Map<Long, TradeItemGroup> tradeItemGroupsMap =
                    param.getSnapshot().getItemGroups().stream()
                            .collect(
                                    Collectors.toMap(
                                            g -> g.getSupplier().getStoreId(),
                                            Function.identity()));
            Map<String, GoodsInfoTradeVO> goodsInfoTradeVOMap =
                    param.getGoodsInfoTradeVOS().stream()
                            .collect(
                                    Collectors.toMap(
                                            GoodsInfoTradeVO::getGoodsInfoId, Function.identity()));
            List<Long> snapshotStoreIds = new ArrayList<>(tradeItemGroupsMap.keySet());
            // 前端传过来的自提信息
            Map<Long, PickSettingInfoVO> pickUpInfoMap =
                    request.getPickUpInfos().stream()
                            .collect(
                                    Collectors.toMap(
                                            PickSettingInfoVO::getStoreId, Function.identity()));
            List<Long> paramStoreIds = new ArrayList<>(pickUpInfoMap.keySet());

            // 快照中的店铺和传入的自提店铺校验
            if (!CollectionUtils.containsAll(snapshotStoreIds, paramStoreIds)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050055);
            } else {
                // 获取自提配置
                PickupSettingConfigResponse response =
                        pickupSettingQueryProvider.pickupSettingConfigShow().getContext();
                // 获取自提商品
                for (Long paramStoreId : paramStoreIds) {
                    // 查询supplier端是否配置自提
                    StoreVO storeVO = storeVOMap.get(paramStoreId);
                    if (Objects.nonNull(storeVO) && Constants.ONE != storeVO.getPickupStateVo()) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050054);
                    }

                    // 查询boss端是否配置自提自营商家开关
                    if (Objects.nonNull(storeVO) && Objects.nonNull(storeVO.getCompanyType())
                            && BoolFlag.NO.equals(storeVO.getCompanyType())) {
                        Integer selfMerchantStatus = response.getSelfMerchantStatus();
                        if (Objects.isNull(selfMerchantStatus)
                                || Constants.ONE != selfMerchantStatus) {
                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050054);
                        }
                    } else {
                        // 查询boss端是否配置自提第三方商家开关
                        Integer thirdMerchantStatus = response.getThirdMerchantStatus();
                        if (Objects.isNull(thirdMerchantStatus)
                                || Constants.ONE != thirdMerchantStatus) {
                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050054);
                        }
                    }
                    PickSettingInfoVO pickSettingInfoVO = pickUpInfoMap.get(paramStoreId);
                    // 查询自提列表
                    PickupSettingListRequest listReq = new PickupSettingListRequest();
                    listReq.setDelFlag(DeleteFlag.NO);
                    listReq.setEnableStatus(Constants.ONE);
                    listReq.setStoreId(paramStoreId);
                    listReq.setId(pickSettingInfoVO.getId());
                    List<PickupSettingVO> pickupSettingVOList =
                            pickupSettingQueryProvider
                                    .list(listReq)
                                    .getContext()
                                    .getPickupSettingVOList();
                    // 获取配置的自提点ids
                    if (CollectionUtils.isEmpty(pickupSettingVOList)) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050056);
                    } else {
                        retMap.put(
                                paramStoreId,
                                orderMapper.pickupSettingVOToPickupSettingInfo(
                                        pickupSettingVOList.get(0)));
                    }
                    // 验证商品是否支持自提
                    List<String> skuIds = pickSettingInfoVO.getSkuIds();

                    for (String skuId : skuIds) {
                        GoodsInfoTradeVO goodsInfoTradeVO = goodsInfoTradeVOMap.get(skuId);
                        // 自提商品非自营
                        if (goodsInfoTradeVO == null || goodsInfoTradeVO.getProviderId() != null) {
                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050055);
                        }
                    }
                }
            }
        }
        return retMap;
    }

    @Override
    public void checkInvoice(
            List<StoreCommitInfoDTO> storeCommitInfoList, List<StoreVO> storeVOList) {
        if (CollectionUtils.isNotEmpty(storeCommitInfoList)) {
            Map<Long, StoreVO> storeVOMap =
                    storeVOList.stream()
                            .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));

            storeCommitInfoList.forEach(
                    storeCommitInfoDTO -> {
                        if (storeCommitInfoDTO.getInvoiceType() != null) {
                            StoreVO storeVO = storeVOMap.get(storeCommitInfoDTO.getStoreId());
                            if (storeVO == null) {
                                throw new SbcRuntimeException(OrderErrorCodeEnum.K050175);
                            }
                            InvoiceProjectSwitchByCompanyInfoIdRequest request =
                                    new InvoiceProjectSwitchByCompanyInfoIdRequest();
                            request.setCompanyInfoId(storeVO.getCompanyInfoId());
                            BaseResponse<InvoiceProjectSwitchByCompanyInfoIdResponse> baseResponse =
                                    invoiceProjectSwitchQueryProvider.getByCompanyInfoId(request);
                            InvoiceProjectSwitchByCompanyInfoIdResponse response =
                                    baseResponse.getContext();

                            boolean flag =
                                    (response.getIsSupportInvoice().equals(DefaultFlag.NO)
                                                    && !storeCommitInfoDTO
                                                            .getInvoiceType()
                                                            .equals(-1))
                                            || (response.getIsPaperInvoice().equals(DefaultFlag.NO)
                                                    && storeCommitInfoDTO
                                                            .getInvoiceType()
                                                            .equals(0))
                                            || (response.getIsValueAddedTaxInvoice()
                                                            .equals(DefaultFlag.NO)
                                                    && storeCommitInfoDTO
                                                            .getInvoiceType()
                                                            .equals(1));
                            if (flag) {
                                throw new SbcRuntimeException(
                                        OrderErrorCodeEnum.K050071, new String[] {storeVO.getStoreName()});
                            }
                        }
                    });
        }
    }

    @Override
    public void checkCoupon(
            TradeCommitRequest request, GoodsTradePluginResponse goodsTradePluginResponse, Boolean bargain) {

        // 处理砍价订单 叠加优惠券业务
        if (Objects.equals(Boolean.TRUE, bargain)) {
            ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
            configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
            configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            SystemConfigTypeResponse response =
                    systemConfigQueryProvider
                            .findByConfigTypeAndDelFlag(configQueryRequest)
                            .getContext();

            if(Objects.isNull(response.getConfig())
                    || Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
                return;
            }
        }

        // 2.验证优惠券
        List<String> couponCodeIds = new ArrayList<>();
        request.getStoreCommitInfoList()
                .forEach(
                        item -> {
                            if (Objects.nonNull(item.getCouponCodeId())) {
                                couponCodeIds.add(item.getCouponCodeId());
                            }
                            if(StringUtils.isNotBlank(item.getFreightCouponCodeId())) {
                                couponCodeIds.add(item.getFreightCouponCodeId());
                            }
                        });
        if (Objects.nonNull(request.getCommonCodeId())) {
            couponCodeIds.add(request.getCommonCodeId());
        }
        String validInfo = null;
        if (CollectionUtils.isNotEmpty(couponCodeIds)) {
            CouponCodeValidOrderCommitRequest validOrderCommitRequest =
                    CouponCodeValidOrderCommitRequest.builder()
                            .customerId(request.getOperator().getUserId())
                            .couponCodeIds(couponCodeIds)
                            .build();
            CouponCodeValidOrderCommitResponse validOrderCommitResponse =
                    couponCodeQueryProvider.validOrderCommit(validOrderCommitRequest).getContext();

            // 2.5.从request对象中移除过期的优惠券
            List<String> invalidCodeIds = validOrderCommitResponse.getInvalidCodeIds();
            validInfo = validOrderCommitResponse.getValidInfo();
            if (invalidCodeIds.contains(request.getCommonCodeId())) {
                request.setCommonCodeId(null);
            }
            request.getStoreCommitInfoList()
                    .forEach(
                            item -> {
                                if (invalidCodeIds.contains(item.getCouponCodeId())) {
                                    item.setCouponCodeId(null);
                                }
                            });
        }

        // 2.6.如果存在提示信息、且为非强制提交，则抛出异常
        if (StringUtils.isNotEmpty(validInfo) && !request.isForceCommit()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "很抱歉，" + validInfo);
        }
    }

    @Override
    public void checkGoods(
            List<GoodsInfoTradeVO> goodsInfoList, TradeItemSnapshot tradeItemSnapshot) {
        Map<String, GoodsInfoTradeVO> goodsInfoMap =
                goodsInfoList.stream()
                        .collect(
                                Collectors.toMap(
                                        GoodsInfoTradeVO::getGoodsInfoId, Function.identity()));
        tradeItemSnapshot
                .getItemGroups()
                .forEach(
                        tradeItemGroup -> {
                            tradeItemGroup
                                    .getTradeItems()
                                    .forEach(
                                            item -> {
                                                GoodsInfoTradeVO goodsInfo =
                                                        goodsInfoMap.get(item.getSkuId());
                                                if (goodsInfo == null) {
                                                    throw new SbcRuntimeException(
                                                            OrderErrorCodeEnum.K050067);
                                                }
                                                if (goodsInfo.getStock() == null
                                                        || goodsInfo.getStock() < item.getNum()) {
                                                    throw new SbcRuntimeException(
                                                            OrderErrorCodeEnum.K050026);
                                                }
                                                if (GoodsStatus.NO_SALE.equals(goodsInfo
                                                                .getGoodsStatus())) {
                                                    throw new SbcRuntimeException(
                                                            OrderErrorCodeEnum.K050053);
                                                }
                                                if (goodsInfo
                                                        .getVendibility()
                                                        .equals(Constants.no)) {
                                                    throw new SbcRuntimeException(
                                                            OrderErrorCodeEnum.K050027);
                                                }
                                                if (!goodsInfo
                                                        .getGoodsStatus()
                                                        .equals(GoodsStatus.OK)) {
                                                    throw new SbcRuntimeException(
                                                            OrderErrorCodeEnum.K050027);
                                                }
                                            });
                        });
    }

    /**
     * 验证拼团
     * @param param
     */
    @Override
    public void checkBargain(Trade1CommitParam param) {

    }

    /**
     * @description   验证礼品卡数量是否一致
     * @author  wur
     * @date: 2022/12/13 18:50
     * @param param
     * @param request
     * @return
     **/
    @Override
    public void chekGiftCard(Trade1CommitParam param, TradeCommitRequest request) {
        if (Objects.nonNull(param.getSnapshot().getOrderTag())
                && Objects.nonNull(param.getSnapshot().getOrderTag().getPickupCardFlag())
                && param.getSnapshot().getOrderTag().getPickupCardFlag()){
            UserGiftCardInfoVO userGiftCardInfoVO = param.getUserGiftCardInfoVOList().get(0);
            if (Objects.isNull(userGiftCardInfoVO)){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
            }
            // 提货卡有效期校验
            switch (userGiftCardInfoVO.getExpirationType()){
                case MONTH:
                    Long rangeMonth = userGiftCardInfoVO.getRangeMonth();
                    LocalDateTime activationTime = userGiftCardInfoVO.getActivationTime();
                    if (activationTime.plusMonths(rangeMonth).isBefore(LocalDateTime.now())){
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
                    }
                    break;
                case SPECIFIC_TIME:
                    LocalDateTime expirationTime = userGiftCardInfoVO.getExpirationTime();
                    if (expirationTime.isBefore(LocalDateTime.now())){
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
                    }
                    break;
                default:
                    log.error("未有匹配，默认输出");
                    break;
            }

            // 提货卡状态校验
            if (Objects.nonNull(userGiftCardInfoVO.getInvalidStatus())){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
            }

            // 适用商品校验
            param.getGoodsInfoTradeVOS().forEach(g -> {
                if (!userGiftCardInfoVO.getSkuIdList().contains(g.getGoodsInfoId())){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
                }
            });

            int total = param.getGoodsInfoTradeVOS().size();
            Integer i = userGiftCardInfoVO.getScopeGoodsNum();
            if (i == -1 && total > 1){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050176, new Object[]{1});
            } else if (i > 0 && total > i){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050176, new Object[]{i});
            }
        } else if (!request.isForceCommit()
                && CollectionUtils.isNotEmpty(request.getGiftCardTradeCommitVOList())
                && (CollectionUtils.isEmpty(param.getUserGiftCardInfoVOList())
                ||  request.getGiftCardTradeCommitVOList().size() != param.getUserGiftCardInfoVOList().size())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080041);
        }
    }

    @Override
    public void checkGoodsChannelPrice(Trade1CommitParam param, TradeCommitRequest request) {
        List<GoodsInfoTradeVO> goodsInfoTradeVOS = param.getGoodsInfoTradeVOS();
        List<GoodsInfoTradeVO> vopGoodsInfoVOS = goodsInfoTradeVOS.stream()
                .filter(goodsInfoTradeVO -> goodsInfoTradeVO.getThirdPlatformType() == ThirdPlatformType.VOP)
                .collect(Collectors.toList());
        // 校验VOP商品最新价格
        if (CollectionUtils.isNotEmpty(vopGoodsInfoVOS)){
            // 根据供应商Id查询商品
            List<String> providerSkuIdList = vopGoodsInfoVOS.stream().map(GoodsInfoTradeVO::getProviderGoodsInfoId).collect(Collectors.toList());
            List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(providerSkuIdList).build()).getContext().getGoodsInfos();
            if (CollectionUtils.isEmpty(goodsInfos)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050154);
            }
            String skuIds = goodsInfos.stream().map(GoodsInfoVO::getThirdPlatformSkuId).collect(Collectors.joining(","));
            ChannelCheckSkuStateRequest channelCheckSkuStateRequest = ChannelCheckSkuStateRequest.builder()
                    .skuIds(skuIds)
                    .thirdPlatformType(ThirdPlatformType.VOP).build();
            List<SkuSellingPriceResponse> skuSellingPriceResponses =
                    BaseResUtils.getContextFromRes(channelGoodsQueryProvider.queryChannelPrice(channelCheckSkuStateRequest));
            if (CollectionUtils.isNotEmpty(skuSellingPriceResponses)){
                Map<String, SkuSellingPriceResponse> skuSellingPriceResponseMap =
                        skuSellingPriceResponses.stream().collect(Collectors.toMap(SkuSellingPriceResponse::getSkuId, v -> v));
                goodsInfos.forEach(goodsInfoVO->{
                    SkuSellingPriceResponse skuSellingPriceResponse = skuSellingPriceResponseMap.get(goodsInfoVO.getThirdPlatformSkuId());
                    if (Objects.nonNull(skuSellingPriceResponse)
                            && (goodsInfoVO.getMarketPrice().compareTo(skuSellingPriceResponse.getPrice()) != 0)) {
                        log.info("下单商品价格校验 - VOP 验证失败Goods：{}， VOPPrice:{}", JSONObject.toJSONString(goodsInfoVO), skuSellingPriceResponse.getPrice().toString());
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050171);
                    }
                });

                vopGoodsInfoVOS.forEach(vopGoodsInfo->{
                    if (vopGoodsInfo.getMarketPrice().compareTo(BigDecimal.ZERO) <= 0) {
                        log.info("下单商品价格校验 - VOP 商品价格不可以小于等于0 Goods：{}， VOPPrice:{}", JSONObject.toJSONString(vopGoodsInfo));
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050171);
                    }
                });
            }
        }
        // TODO  校验LINKED_MALL商品最新价格

    }

    /**
     * @param param
     * @return void
     * @description 社区团购活动信息校验
     * @author edz
     * @date: 2023/7/26 17:56
     */
    @Override
    public void checkCommunityActivity(Trade1CommitParam param) {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.COMMUNITY_CONFIG.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        if(configVO == null || configVO.getStatus() == null ||Constants.no.equals(configVO.getStatus())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "平台已关闭了社区团购业务");
        }
        CommunityActivityVO communityActivityVO = param.getCommunityActivityByIdResponse().getCommunityActivityVO();
        // 活动时间校验
        if (LocalDateTime.now().isBefore(communityActivityVO.getStartTime())
                || communityActivityVO.getEndTime().isBefore(LocalDateTime.now())){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050178);
        }

        // 自营销售范围和团长销售范围校验
        TradeItemSnapshot snapshot = param.getSnapshot();
        String leaderId = snapshot.getItemGroups().get(0).getCommunityBuyRequest().getLeaderId();
        CommunityLeaderPickupPointVO communityLeaderPickupPointVO = null;
        if (CollectionUtils.isNotEmpty(param.getCommunityLeaderPickupPointVOS())){
            communityLeaderPickupPointVO = param.getCommunityLeaderPickupPointVOS().get(0);
        }

        if (StringUtils.isNotBlank(leaderId)){
            if (CommunityLeaderRangeType.CUSTOM.equals(communityActivityVO.getLeaderRange())){
                String pickupPointId = param.getCommunityLeaderPickupPointVOS().get(0).getPickupPointId();
                boolean t = communityActivityVO.getCommunityLeaderRelVOList().stream()
                        .map(CommunityLeaderRelVO::getPickupPointId)
                        .noneMatch(g -> g.equals(pickupPointId));
                if (t){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050180);
                }
            } else if (CommunityLeaderRangeType.AREA.equals(communityActivityVO.getLeaderRange())){
                if (Objects.isNull(communityLeaderPickupPointVO)) throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
                List<String> leaderRangeContext = communityActivityVO.getLeaderRangeContext();
                Long pickupProvinceId = communityLeaderPickupPointVO.getPickupProvinceId();
                Long pickupAreaId = communityLeaderPickupPointVO.getPickupAreaId();
                Long pickupCityId = communityLeaderPickupPointVO.getPickupCityId();
                Long pickupStreetId = communityLeaderPickupPointVO.getPickupStreetId();
                List<Long> areas = Arrays.asList(pickupProvinceId, pickupAreaId, pickupCityId, pickupStreetId);
                if (areas.stream().noneMatch(g -> leaderRangeContext.contains(g.toString()))){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050180);
                }
                if (leaderRangeContext.contains(leaderId)){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050180);
                }
            }
        } else if (StringUtils.isBlank(leaderId) && Objects.nonNull(communityLeaderPickupPointVO)){
            if (CommunitySalesRangeType.CUSTOM.equals(communityActivityVO.getSalesRange())){
                CommunityLeaderPickupPointVO finalCommunityLeaderPickupPointVO = communityLeaderPickupPointVO;
                boolean t = communityActivityVO.getSalesRangeContext().stream()
                        .noneMatch(g -> g.equals(finalCommunityLeaderPickupPointVO.getPickupPointId()));
                if (t){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050180);
                }
            } else if (CommunitySalesRangeType.AREA.equals(communityActivityVO.getSalesRange())){
                List<String> leaderRangeContext = communityActivityVO.getSalesRangeContext();
                Long pickupProvinceId = communityLeaderPickupPointVO.getPickupProvinceId();
                Long pickupAreaId = communityLeaderPickupPointVO.getPickupAreaId();
                Long pickupCityId = communityLeaderPickupPointVO.getPickupCityId();
                Long pickupStreetId = communityLeaderPickupPointVO.getPickupStreetId();
                List<Long> areas = Arrays.asList(pickupProvinceId, pickupAreaId, pickupCityId, pickupStreetId);
                if (areas.stream().noneMatch(g -> leaderRangeContext.contains(g.toString()))){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050180);
                }
            }
        }
    }

    /**
     * @param param
     * @return void
     * @description 校验社区团购团长信息
     * @author edz
     * @date: 2023/7/27 15:58
     */
    @Override
    public void checkCommunityLeader(Trade1CommitParam param) {
        CommunityLeaderVO communityLeaderVO = param.getCommunityLeaderVO();
        if (Objects.isNull(communityLeaderVO)) return;
        if (!LeaderCheckStatus.CHECKED.equals(communityLeaderVO.getCheckStatus())
                || 0 == communityLeaderVO.getAssistFlag()
                || LogOutStatus.LOGGED_OUT.equals(communityLeaderVO.getLogOutStatus())){
            log.error("Trade1CommitCheckService.checkCommunityLeader:团长信息::{}", communityLeaderVO);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050181);
        }
    }

    /**
     * @param param
     * @return void
     * @description 校验社区团购自提点信息
     * @author edz
     * @date: 2023/7/27 15:58
     */
    @Override
    public void checkCommunityPickup(Trade1CommitParam param) {
        if (CollectionUtils.isEmpty(param.getCommunityLeaderPickupPointVOS())) return;
        CommunityLeaderPickupPointVO communityLeaderPickupPointVO = param.getCommunityLeaderPickupPointVOS().get(0);
        if (!LeaderCheckStatus.CHECKED.equals(communityLeaderPickupPointVO.getCheckStatus())){
            log.error("Trade1CommitCheckService.checkCommunityPickup:自提点信息::{}", communityLeaderPickupPointVO);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050182);
        }
    }

    // 拼团
    private void checkGrouponMarketing(
            TradeItemGroup group,
            Map<Integer, MarketingPluginLabelDetailVO> marketingTypeMap,
            String customerId) {
        TradeGrouponCommitForm grouponForm = group.getGrouponForm();
        if (grouponForm != null) {
            if (grouponForm.getGrouponActivityId() == null
                    || marketingTypeMap.get(MarketingPluginType.GROUPON.getId()) == null
                    || !marketingTypeMap
                            .get(MarketingPluginType.GROUPON.getId())
                            .getMarketingId()
                            .equals(grouponForm.getGrouponActivityId())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050093);
            }
            if (!grouponForm.getOpenGroupon()) {
                GrouponInstance grouponInstance =
                        grouponOrderService.getGrouponInstanceByActivityIdAndGroupon(
                                grouponForm.getGrouponNo());
                if (Objects.isNull(grouponInstance)
                        || StringUtils.isBlank(grouponInstance.getGrouponActivityId())) {
                    log.error("团编号:{},未查询到拼团订单数据，请检查团编号是否正确！", grouponForm.getGrouponNo());
                    throw new SbcRuntimeException(
                            OrderErrorCodeEnum.K050100);
                }
            }
            TradeGroupForm tradeGroupForm =
                    TradeGroupForm.builder()
                            .buyCustomerId(customerId)
                            .goodsId(group.getTradeItems().get(0).getSpuId())
                            .grouponActivityId(grouponForm.getGrouponActivityId())
                            .grouponNo(grouponForm.getGrouponNo())
                            .leader(group.getGrouponForm().getOpenGroupon())
                            .build();
            GrouponOrderCheckStatus checkStatus =
                    grouponOrderService.checkLeaderIsOpenGrouponOrMemberIsJoinGroupon(
                            tradeGroupForm);

            // 已开团，不可再开团
            if (GrouponOrderCheckStatus.ORDER_MARKETING_GROUPON_WAIT == checkStatus) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050094);
            }

            // 已参同一团活动，不可再参团
            if (GrouponOrderCheckStatus.ORDER_MARKETING_GROUPON_ALREADY_JOINED == checkStatus) {

                throw new SbcRuntimeException(
                        OrderErrorCodeEnum.K050095);
            }

            // 已成团/团已作废，不可参团
            if (GrouponOrderCheckStatus.ORDER_MARKETING_GROUPON_ORDER_INST_ALREADY_OR_FAIL
                            == checkStatus
                    || GrouponOrderCheckStatus.ORDER_MARKETING_GROUPON_COUNT_DOWN_END
                            == checkStatus) {

                throw new SbcRuntimeException(OrderErrorCodeEnum.K050096);
            }
        }
//        else{
//            if(marketingTypeMap.get(MarketingPluginType.GROUPON.getId()) != null){
//                throw new SbcRuntimeException(OrderErrorCodeEnum.K050133);
//            }
//        }
    }

    // 预售
    private void checkBookingMarketing(
            TradeItem item, Map<Integer, MarketingPluginLabelDetailVO> marketingTypeMap) {
        if (item.getIsBookingSaleGoods()) {
            if (marketingTypeMap.get(MarketingPluginType.BOOKING_SALE.getId()) == null
                    || ((Number)
                                            marketingTypeMap
                                                    .get(MarketingPluginType.BOOKING_SALE.getId())
                                                    .getMarketingId())
                                    .longValue()
                            != item.getBookingSaleId()) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050151);
            }
        }else{
            if(marketingTypeMap.get(MarketingPluginType.BOOKING_SALE.getId()) != null){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050133);
            }
        }
    }

    private void checkAppointmentMarketing(
            TradeItem item, Map<Integer, MarketingPluginLabelDetailVO> marketingTypeMap) {
        if (item.getIsAppointmentSaleGoods()) {
            if (marketingTypeMap.get(MarketingPluginType.APPOINTMENT_SALE.getId()) == null
                    || ((Number)
                                            marketingTypeMap
                                                    .get(
                                                            MarketingPluginType.APPOINTMENT_SALE
                                                                    .getId())
                                                    .getMarketingId())
                                    .longValue()
                            != item.getAppointmentSaleId()) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050150);
            }
        }else{
            if(marketingTypeMap.get(MarketingPluginType.APPOINTMENT_SALE.getId()) != null){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050133);
            }
        }
    }

    private void checkPublicMarketing(
            TradeItem item,
            Map<Integer, MarketingPluginLabelDetailVO> marketingTypeMap,
            TradeItemGroup group,
            boolean isForceCommit) {
        if (CollectionUtils.isNotEmpty(item.getMarketingIds())) {
            Map<Long, MarketingPluginLabelDetailVO> onlyOneMap = new HashMap<>();
            Map<Long, MarketingPluginLabelDetailVO> marketingMap = new HashMap<>();
            marketingTypeMap
                    .values()
                    .forEach(
                            m -> {
                                if (MarketingPluginConstant.SELECT_ONE.contains(
                                        m.getMarketingType())) {
                                    onlyOneMap.put(((Number) m.getMarketingId()).longValue(), m);
                                } else if (MarketingPluginType.PREFERENTIAL.getId() == m.getMarketingType()){
                                    marketingMap.put(((Number) m.getMarketingId()).longValue(), m);
                                }
                            });
            Map<Long, TradeMarketingDTO> tradeMarketingDTOMap =
                    group.getTradeMarketingList().stream()
                            .collect(
                                    Collectors.toMap(
                                            TradeMarketingDTO::getMarketingId,
                                            Function.identity()));
            for (Long marketingId : item.getMarketingIds()) {
                if (onlyOneMap.get(marketingId) == null && marketingMap.get(marketingId) == null) {
                    if (isForceCommit) {
                        item.setMarketingIds(
                                item.getMarketingIds().stream()
                                        .filter(m -> !m.equals(marketingId))
                                        .collect(Collectors.toList()));
                        group.setTradeMarketingList(
                                group.getTradeMarketingList().stream()
                                        .filter(m -> !m.getMarketingId().equals(marketingId))
                                        .collect(Collectors.toList()));
                    } else {
                        String invalidMsg =
                                this.getInvalidMarketingInfo(
                                        tradeMarketingDTOMap.get(marketingId), marketingId);
                        throw new SbcRuntimeException(
                                CommonErrorCodeEnum.K999999, "很抱歉，" + invalidMsg + "失效");
                    }
                }
                if (!tradeMarketingDTOMap.keySet().contains(marketingId)) {
                    item.setMarketingIds(
                            item.getMarketingIds().stream()
                                    .filter(m -> m.equals(marketingId))
                                    .collect(Collectors.toList()));
                }
            }
        }
    }

    private String getInvalidMarketingInfo(TradeMarketingDTO tradeMarketing, Long marketingId) {
        MarketingSimpleVO marketing =
                marketingQueryProvider
                        .getSimpleByIdForCustomer(MarketingGetByIdRequest.buildRequest(marketingId))
                        .getContext()
                        .getMarketingSimpleVO();
        String info = "";
        DecimalFormat fm = new DecimalFormat("#.##");
        MarketingSubType subType = marketing.getSubType();
        Long l = tradeMarketing.getMarketingLevelId();
        switch (marketing.getMarketingType()) {
            case REDUCTION:
                Map<Long, MarketingFullReductionLevelVO> reductionMap =
                        marketing.getFullReductionLevelList().stream()
                                .collect(
                                        Collectors.toMap(
                                                MarketingFullReductionLevelVO::getReductionLevelId,
                                                Function.identity()));

                MarketingFullReductionLevelVO reductionLevel = reductionMap.get(l);
                if (reductionLevel == null) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                }
                info = "满%s减%s活动";
                if (subType == MarketingSubType.REDUCTION_FULL_AMOUNT) {
                    info =
                            String.format(
                                    info,
                                    fm.format(reductionLevel.getFullAmount()) + "元",
                                    reductionLevel.getReduction());
                } else if (subType == MarketingSubType.REDUCTION_FULL_COUNT) {
                    info =
                            String.format(
                                    info,
                                    reductionLevel.getFullCount() + "件",
                                    reductionLevel.getReduction());
                }
                break;
            case DISCOUNT:
                Map<Long, MarketingFullDiscountLevelVO> discountMap =
                        marketing.getFullDiscountLevelList().stream()
                                .collect(
                                        Collectors.toMap(
                                                MarketingFullDiscountLevelVO::getDiscountLevelId,
                                                Function.identity()));
                MarketingFullDiscountLevelVO discountLevel = discountMap.get(l);
                if (discountLevel == null) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                }
                info = "满%s享%s折活动";
                BigDecimal discount =
                        discountLevel
                                .getDiscount()
                                .multiply(BigDecimal.TEN)
                                .setScale(1, RoundingMode.HALF_UP);
                if (subType == MarketingSubType.DISCOUNT_FULL_AMOUNT) {
                    info = String.format(info, fm.format(discountLevel.getFullAmount()) + "元", discount);
                } else if (subType == MarketingSubType.DISCOUNT_FULL_COUNT) {
                    info = String.format(info, discountLevel.getFullCount() + "件", discount);
                }
                break;
            case GIFT:
                FullGiftLevelListByMarketingIdRequest fullGiftLevelListByMarketingIdRequest =
                        new FullGiftLevelListByMarketingIdRequest();
                fullGiftLevelListByMarketingIdRequest.setMarketingId(marketing.getMarketingId());
                Map<Long, MarketingFullGiftLevelVO> giftMap =
                        marketing.getFullGiftLevelList().stream()
                                .collect(
                                        Collectors.toMap(
                                                MarketingFullGiftLevelVO::getGiftLevelId,
                                                Function.identity()));
                MarketingFullGiftLevelVO giftLevel = giftMap.get(l);
                if (giftLevel == null) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                }
                info = "满%s获赠品活动";
                if (subType == MarketingSubType.GIFT_FULL_AMOUNT) {
                    info = String.format(info, fm.format(giftLevel.getFullAmount()) + "元");
                } else if (subType == MarketingSubType.GIFT_FULL_COUNT) {
                    info = String.format(info, giftLevel.getFullCount() + "件");
                }
                break;
            case BUYOUT_PRICE:
                MarketingBuyoutPriceIdRequest request = new MarketingBuyoutPriceIdRequest();
                request.setMarketingId(marketing.getMarketingId());
                Map<Long, MarketingBuyoutPriceLevelVO> levelMap =
                        marketing.getBuyoutPriceLevelList().stream()
                                .collect(
                                        Collectors.toMap(
                                                MarketingBuyoutPriceLevelVO::getReductionLevelId,
                                                c -> c));
                MarketingBuyoutPriceLevelVO level = levelMap.get(l);
                if (level == null) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                }
                info = "%s件%s元";
                info =
                        String.format(
                                info, level.getChoiceCount(), fm.format(level.getFullAmount()));
                break;
            case HALF_PRICE_SECOND_PIECE:
                MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
                marketingGetByIdRequest.setMarketingId(marketing.getMarketingId());
                Map<Long, MarketingHalfPriceSecondPieceLevelVO> halfPriceSecondPieceLevelMap =
                        marketing.getHalfPriceSecondPieceLevel().stream()
                                .collect(
                                        Collectors.toMap(
                                                MarketingHalfPriceSecondPieceLevelVO::getId,
                                                c -> c));
                MarketingHalfPriceSecondPieceLevelVO halfPriceSecondPieceLevel =
                        halfPriceSecondPieceLevelMap.get(l);
                if (halfPriceSecondPieceLevel == null) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                }
                if (halfPriceSecondPieceLevel.getDiscount().intValue() == 0) {
                    info = "买" + (halfPriceSecondPieceLevel.getNumber() - 1) + "送1";
                } else {
                    info =
                            "第"
                                    + halfPriceSecondPieceLevel.getNumber()
                                    + "件"
                                    + halfPriceSecondPieceLevel.getDiscount()
                                    + "折";
                }
                break;
            case SUITS:
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050161);
            default:
                break;
        }
        return info;
    }
}
