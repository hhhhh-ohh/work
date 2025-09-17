package com.wanmi.sbc.order.optimization.trade1.confirm.service.query;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderByIdRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoBaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.bean.enums.CommunityLeaderRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityLeaderRelVO;
import com.wanmi.sbc.marketing.bean.vo.CommunitySkuRelVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.CommunityBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.service.VerifyService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author edz
 * @className TradeCommunityQueryService
 * @description 社区团购
 * @date 2023/7/24 16:28
 **/
@Service
public class TradeCommunityQueryService extends TradeConfirmQueryService{

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;


    /**
     * @description 社区团购信息
     * @author  edz
     * @date: 2023/7/24 17:08
     * @param param
     * @return void
     */
    public void getCommunityActivityVO(TradeConfirmParam param){
        CommunityBuyRequest communityBuyRequest = param.getTradeBuyRequest().getCommunityBuyRequest();
        // 社区团购活动
        CommunityActivityByIdResponse communityActivityByIdResponse =
                communityActivityQueryProvider.getById(CommunityActivityByIdRequest.builder()
                                .skuRelFlag(Boolean.TRUE)
                                .saleRelFlag(Boolean.TRUE)
                                .leaderId(communityBuyRequest.getLeaderId())
                                .activityId(communityBuyRequest.getActivityId()).build())
                        .getContext();
        param.setCommunityActivityVO(communityActivityByIdResponse.getCommunityActivityVO());
    }

    /**
     * @description 团长ID查询自提点
     * @author  edz
     * @date: 2023/7/31 17:47
     * @param param
     * @return void
     */
    public void getCommunityLeaderPickupPointList(TradeConfirmParam param){
        CommunityBuyRequest communityBuyRequest = param.getTradeBuyRequest().getCommunityBuyRequest();
        if (Objects.nonNull(communityBuyRequest.getLeaderId())){
            List<CommunityLeaderPickupPointVO> communityLeaderPickupPointList =
                    communityLeaderPickupPointQueryProvider.list(CommunityLeaderPickupPointListRequest.builder()
                            .leaderId(communityBuyRequest.getLeaderId()).build())
                            .getContext()
                            .getCommunityLeaderPickupPointList();
            param.setCommunityLeaderPickupPointList(communityLeaderPickupPointList);
        }

    }

    public void getLeaderInfo(TradeConfirmParam param) {
        TradeBuyRequest request = param.getTradeBuyRequest();
        if (Objects.isNull(request.getCommunityBuyRequest().getLeaderId())) return;
        CommunityLeaderVO communityLeaderVO = communityLeaderQueryProvider.getById(CommunityLeaderByIdRequest.builder()
                        .leaderId(request.getCommunityBuyRequest().getLeaderId())
                        .build())
                .getContext()
                .getCommunityLeaderVO();
        param.setCommunityLeaderVO(communityLeaderVO);
    }

    /**
     * @description 校验店铺
     * @author  edz
     * @date: 2023/7/25 16:10
     * @param param
     * @return void
     */
    public void checkStore(TradeConfirmParam param){
        // 校验店铺状态
        if (!verifyService.checkStore(param.getStores())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }
    }




    /**
     * @description 社区团购活动时间校验
     * @author  edz
     * @date: 2023/7/24 16:52
     * @param tradeConfirmParam
     * @return void
     */
    public void checkCommunityActivityVO(TradeConfirmParam tradeConfirmParam){
        TradeBuyRequest request = tradeConfirmParam.getTradeBuyRequest();
        CommunityActivityVO communityActivityVO = tradeConfirmParam.getCommunityActivityVO();
        // 活动时间校验
        if (LocalDateTime.now().isBefore(communityActivityVO.getStartTime())
                || communityActivityVO.getEndTime().isBefore(LocalDateTime.now())){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050178);
        }

        List<CommunitySalesType> salesTypes = communityActivityVO.getSalesTypes();
        String leaderId = request.getCommunityBuyRequest().getLeaderId();
        if (salesTypes.size() == 1){
            CommunitySalesType salesType = salesTypes.get(0);
            if (CommunitySalesType.LEADER == salesType && StringUtils.isBlank(leaderId)){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050184);
            }

            if ((CommunitySalesType.SELF == salesType && StringUtils.isNotEmpty(leaderId))){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050183);
            }
        }
    }

    /**`
     * @description 商品校验
     * @author  edz
     * @date: 2023/7/24 17:49
     * @param param
     * @return void
     */
    public void checkGoodsStock(TradeConfirmParam param) {
        List<GoodsInfoVO> goodsInfos = param.getGoodsInfos();
        List<GoodsInfoVO> channelSkus = Lists.newArrayList();
        for (GoodsInfoVO goodsInfo : goodsInfos) {
            if (goodsInfo.getGoodsStatus().equals(GoodsStatus.OK)) {
                if (StringUtils.isNotEmpty(goodsInfo.getProviderGoodsInfoId())) {
                    if (nonNull(goodsInfo.getThirdPlatformType())) {
                        channelSkus.add(goodsInfo);
                    }
                }
            }
        }

        // 正常商品库存 / 供应商商品库存
        if (MapUtils.isNotEmpty(param.getSkuRedisStockMap())) {
            for (GoodsInfoVO goodsInfo : goodsInfos) {
                if (GoodsStatus.OK.equals(goodsInfo.getGoodsStatus())) {
                    Long stock = StringUtils.isNotEmpty(goodsInfo.getProviderGoodsInfoId())
                            ? param.getSkuRedisStockMap().get(goodsInfo.getProviderGoodsInfoId())
                            : param.getSkuRedisStockMap().get(goodsInfo.getGoodsInfoId());
                    if (nonNull(stock)) {
                        goodsInfo.setStock(stock);
                    }
                    if (isNull(stock) || stock < 1) {
                        goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                }
            }
        }
        // 渠道商品库存
        if (CollectionUtils.isNotEmpty(channelSkus)) {
            String[] address = StringUtils.defaultString(param.getTradeBuyRequest().getAddressId(), StringUtils.EMPTY).split(Constants.CATE_PATH_SPLITTER);
            PlatformAddress platformAddress = PlatformAddress.builder()
                    .provinceId(address[0])
                    .cityId(address[1])
                    .areaId(address[2])
                    .streetId(address[3])
                    .build();
            List<GoodsInfoBaseVO> goodsInfoBaseVOS = KsBeanUtil.convert(goodsInfos, GoodsInfoBaseVO.class);
            thirdPlatformTradeService.cartStatus(goodsInfoBaseVOS, platformAddress);
            Map<String, GoodsInfoBaseVO> goodsInfoBaseVoMap = goodsInfoBaseVOS.stream().collect(Collectors.toMap(GoodsInfoBaseVO::getGoodsInfoId, Function.identity()));
            for (GoodsInfoVO goodsInfo : goodsInfos) {
                GoodsInfoBaseVO goodsInfoBaseVO = goodsInfoBaseVoMap.get(goodsInfo.getGoodsInfoId());
                if (nonNull(goodsInfoBaseVO)) {
                    goodsInfo.setGoodsStatus(goodsInfoBaseVO.getGoodsStatus());
                    goodsInfo.setVendibility(goodsInfoBaseVO.getVendibility());
                    goodsInfo.setStock(goodsInfoBaseVO.getStock());
                }
            }
        }
        Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        List<CommunitySkuRelVO> communitySkuRelVOS = param.getCommunityActivityVO().getSkuList();
        Map<String, CommunitySkuRelVO> skuIdToIdentityMap =
                communitySkuRelVOS.stream().collect(Collectors.toMap(CommunitySkuRelVO::getSkuId, Function.identity()));
        List<TradeItemRequest> tradeItemRequests = param.getTradeBuyRequest().getTradeItemRequests();
        for (TradeItemRequest tradeItemRequest : tradeItemRequests) {
            CommunitySkuRelVO communitySkuRelVO = skuIdToIdentityMap.get(tradeItemRequest.getSkuId());
            if (Objects.isNull(communitySkuRelVO)){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050179);
            }
            // 活动库存：非必填，填写则以活动库存&实际库存中较小的数值为上限，不填写则以实际库存未上限
            Long sales = communitySkuRelVO.getSales();
            Long activityStock = communitySkuRelVO.getActivityStock();
            Long stock = goodsInfoVOMap.get(tradeItemRequest.getSkuId()).getStock();
            if (Objects.nonNull(activityStock) && activityStock <= stock){
                if ((tradeItemRequest.getNum() + sales) > activityStock){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050026);
                }
            } else {
                if (tradeItemRequest.getNum() > stock){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050026);
                }
            }
        }
    }

    /**
     * @description 校验团长与活动关系
     * @author  edz
     * @date: 2023/7/25 16:42
     * @param param
     * @return void
     */
    public void checkCommunityLeader(TradeConfirmParam param){
        CommunityBuyRequest communityBuyRequest = param.getTradeBuyRequest().getCommunityBuyRequest();
        CommunityActivityVO communityActivityVO = param.getCommunityActivityVO();
        if (Objects.nonNull(communityBuyRequest.getLeaderId())) {
            LeaderCheckStatus leaderCheckStatus = param.getCommunityLeaderVO().getCheckStatus();
            if (!LeaderCheckStatus.CHECKED.equals(leaderCheckStatus)){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050181);
            }

            if (CommunityLeaderRangeType.AREA.equals(communityActivityVO.getLeaderRange())){
                List<String> leaderRangeContext = communityActivityVO.getLeaderRangeContext();
                List<Long> areas =
                        param.getCommunityLeaderPickupPointList().stream().map(CommunityLeaderPickupPointVO::getPickupProvinceId).collect(Collectors.toList());
                areas.addAll(param.getCommunityLeaderPickupPointList().stream().map(CommunityLeaderPickupPointVO::getPickupCityId).collect(Collectors.toList()));
                areas.addAll(param.getCommunityLeaderPickupPointList().stream().map(CommunityLeaderPickupPointVO::getPickupAreaId).collect(Collectors.toList()));
                areas.addAll(param.getCommunityLeaderPickupPointList().stream().map(CommunityLeaderPickupPointVO::getPickupStreetId).collect(Collectors.toList()));
                if (areas.stream().noneMatch(g -> leaderRangeContext.contains(g.toString()))){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050180);
                }
            } else if (CommunityLeaderRangeType.CUSTOM.equals(communityActivityVO.getLeaderRange())) {
                String pickupPointId = param.getCommunityLeaderPickupPointList().get(0).getPickupPointId();
                boolean t = communityActivityVO.getCommunityLeaderRelVOList().stream()
                                .map(CommunityLeaderRelVO::getPickupPointId)
                                .noneMatch(g -> g.equals(pickupPointId));
                if (t){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050180);
                }
            }
        }
    }

    public void fillTradeItemPrice(TradeItem tradeItem, GoodsInfoVO goodsInfo, GoodsVO goods,
                                   List<GoodsIntervalPriceVO> goodsIntervalPrices,
                                   List<CommunitySkuRelVO> communitySkuRelVOS) {
        Map<String, BigDecimal> skuIdToActivityPriceMap =
                communitySkuRelVOS.stream().collect(HashMap::new, (map, item) -> map.put(item.getSkuId(),
                        item.getPrice()), HashMap::putAll);
        BigDecimal activityPrice = skuIdToActivityPriceMap.get(goodsInfo.getGoodsInfoId());
        if (Objects.isNull(activityPrice)){
            if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
                // 订货区间设价
                goodsIntervalPrices.stream()
                        .filter(item -> item.getGoodsInfoId().equals(tradeItem.getSkuId()))
                        .filter(intervalPrice -> tradeItem.getNum() >= intervalPrice.getCount())
                        .max(Comparator.comparingLong(GoodsIntervalPriceVO::getCount))
                        .ifPresent(goodsIntervalPrice -> {
                            tradeItem.setLevelPrice(goodsIntervalPrice.getPrice());
                            tradeItem.setOriginalPrice(goodsInfo.getMarketPrice());
                            tradeItem.setPrice(goodsIntervalPrice.getPrice());
                            tradeItem.setSplitPrice(tradeItem.getLevelPrice()
                                    .multiply(new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
                        });
            } else {
                BigDecimal price = ObjectUtils.defaultIfNull(goodsInfo.getSalePrice(), goodsInfo.getMarketPrice());
                tradeItem.setLevelPrice(price);
                tradeItem.setOriginalPrice(price);
                tradeItem.setPrice(price);
                tradeItem.setSplitPrice(tradeItem.getLevelPrice()
                        .multiply(new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
            }
        } else {
            tradeItem.setLevelPrice(activityPrice);
            tradeItem.setOriginalPrice(goodsInfo.getMarketPrice());
            tradeItem.setPrice(activityPrice);
            tradeItem.setSplitPrice(activityPrice
                    .multiply(new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
        }

    }

}
