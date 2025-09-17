package com.wanmi.sbc.marketing.plugin.impl;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.vo.CouponLabelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.common.request.TradeItemInfo;
import com.wanmi.sbc.marketing.common.request.TradeMarketingPluginRequest;
import com.wanmi.sbc.marketing.common.response.TradeMarketingResponse;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCoupon;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.repository.CouponInfoRepository;
import com.wanmi.sbc.marketing.coupon.repository.CouponMarketingScopeRepository;
import com.wanmi.sbc.marketing.coupon.service.*;
import com.wanmi.sbc.marketing.newplugin.impl.coupon.CouponPluginContext;
import com.wanmi.sbc.marketing.newplugin.impl.coupon.MarketingCouponPluginInterface;
import com.wanmi.sbc.marketing.plugin.IGoodsDetailPlugin;
import com.wanmi.sbc.marketing.plugin.IGoodsListPlugin;
import com.wanmi.sbc.marketing.plugin.ITradeCommitPlugin;
import com.wanmi.sbc.marketing.request.MarketingPluginRequest;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 营销满赠插件
 * Created by dyt on 2016/12/8.
 */
@Repository("couponPlugin")
public class CouponPlugin implements IGoodsListPlugin, IGoodsDetailPlugin, ITradeCommitPlugin {


    @Autowired
    private CouponCacheServiceInterface couponCacheServiceInterface;
    @Resource
    private CouponCacheService couponCacheService;

    @Autowired
    private CouponCodeServiceInterface couponCodeServiceInterface;
    @Resource
    private CouponCodeService couponCodeService;

    @Autowired
    private CouponMarketingScopeRepository couponMarketingScopeRepository;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired private CouponPluginContext couponPluginContext;


    /**
     * 商品列表处理
     *
     * @param goodsInfos 商品数据
     * @param request    参数
     */
    @Override
    public void goodsListFilter(List<GoodsInfoVO> goodsInfos, MarketingPluginRequest request) {
        if (request.getCommitFlag()) {
            return;
        }
        List<String> goodsIds = new ArrayList<>();
        goodsInfos.forEach(goodsInfo -> {
            goodsIds.add(goodsInfo.getGoodsId());

        });

        //去重
        List<String> distinctGoodsIds = goodsIds.stream().distinct().collect(Collectors.toList());

        // 优化 将goodsid对应的店铺分类放置内存中
        // 组装店铺分类
        List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOS = storeCateQueryProvider.listByGoods(
                new StoreCateListByGoodsRequest(distinctGoodsIds)).getContext().getStoreCateGoodsRelaVOList();
        HashMap<String,List<Long>> storeCateIdMap = new HashMap<>();
        for(int i=0 ; i<storeCateGoodsRelaVOS.size();i++){
            List<Long> storeCateId=new ArrayList<>();
            if (storeCateIdMap.containsKey(storeCateGoodsRelaVOS.get(i).getGoodsId())) {
                storeCateId = storeCateIdMap.get(storeCateGoodsRelaVOS.get(i).getGoodsId());
            }
            storeCateId.add(storeCateGoodsRelaVOS.get(i).getStoreCateId());
            storeCateIdMap.put(storeCateGoodsRelaVOS.get(i).getGoodsId(),storeCateId);
        }

        couponCacheServiceInterface.refreshCache();
        goodsInfos.forEach(item -> {
            // 缓存商品PluginType，根据request路由
            PluginType cachePluginType = item.getPluginType();
            item.setPluginType(request.getPluginType());
            List<CouponCache> couponCacheList = couponCacheServiceInterface.listCouponForGoodsInfos(item, request.getLevelMap(), request.getStoreId(), storeCateIdMap.get(item.getGoodsId()), request.getPluginType());
//            List<CouponCache> couponCacheList = couponScopeCacheService.listCouponForGoodsInfos(item, request.getLevelMap(), request.getStoreId(), storeCateIdMap.get(item.getGoodsId()), request.getPluginType());
            List<CouponLabelVO> labelList = couponCacheList.stream().limit(6).map(cache ->
                    CouponLabelVO.builder()
                            .couponActivityId(cache.getCouponActivityId())
                            .couponInfoId(cache.getCouponInfoId())
                            .couponDesc(getLabelMap(cache))
                            .build()
            ).collect(Collectors.toList());
            item.getCouponLabels().addAll(labelList);
            item.setPluginType(cachePluginType);
        });
    }


    /**
     * 商品详情处理
     *
     * @param detailResponse 商品详情数据
     * @param request        参数
     */
    @Override
    public void goodsDetailFilter(GoodsInfoDetailByGoodsInfoResponse detailResponse, MarketingPluginRequest request) {
        //把品牌从goods搬运到goodsInfo
        detailResponse.getGoodsInfo().setBrandId(detailResponse.getGoods().getBrandId());
        detailResponse.getGoodsInfo().setCateId(detailResponse.getGoods().getCateId());
        List<CouponCache> couponCacheList = couponCacheServiceInterface.listCouponForGoodsInfo(MarketingGoods
                .builder()
                .goodsId(detailResponse.getGoodsInfo().getGoodsId())
                .brandId(detailResponse.getGoodsInfo().getBrandId())
                .cateId(detailResponse.getGoodsInfo().getCateId())
                .goodsInfoId(detailResponse.getGoodsInfo().getGoodsInfoId())
                .storeId(detailResponse.getGoodsInfo().getStoreId()).build(),
                request.getLevelMap(), request.getStoreId(), request.getPluginType());
        List<CouponLabelVO> labelList = couponCacheList.stream().limit(3).map(cache ->
                CouponLabelVO.builder()
                        .couponActivityId(cache.getCouponActivityId())
                        .couponInfoId(cache.getCouponInfoId())
                        .couponDesc(getLabelMap(cache))
                        .build()
        ).collect(Collectors.toList());
        detailResponse.getGoodsInfo().getCouponLabels().addAll(labelList);
    }


    /**
     * 订单营销处理
     */
    @Override
    public TradeMarketingResponse wraperMarketingFullInfo(TradeMarketingPluginRequest request) {
        String couponCodeId = request.getCouponCodeId();
        List<TradeItemInfo> tradeItems = request.getTradeItems();
        if (StringUtils.isEmpty(couponCodeId)) {
            return null;
        }

        // 1.查询我的未使用优惠券
        List<CouponCode> couponCodes = couponCodeService.listCouponCodeByCondition(CouponCodeQueryRequest.builder()
                .customerId(request.getCustomerId())
                .useStatus(DefaultFlag.NO)
                .delFlag(DeleteFlag.NO).build());

        // 2.判断所传优惠券，是否是我的未用优惠券
        CouponCode couponCode = couponCodes.stream().filter(item ->
                StringUtils.equals(item.getCouponCodeId(), couponCodeId)
        ).findFirst().orElseThrow(() -> new SbcRuntimeException(MarketingErrorCodeEnum.K080090));

        // 3.判断所传优惠券，是否在使用时间
        if (LocalDateTime.now().isAfter(couponCode.getEndTime()) || LocalDateTime.now().isBefore(couponCode.getStartTime())) {
            if (!request.isForceCommit()) {
                CouponInfo couponInfo = couponInfoRepository.findById(couponCode.getCouponId()).orElse(null);
                StringBuilder sb = new StringBuilder("很抱歉，");
                if (FullBuyType.NO_THRESHOLD == couponInfo.getFullBuyType()) {
                    sb.append("无门槛减").append(couponInfo.getDenomination().setScale(0));
                } else {
                    sb.append('满').append(couponInfo.getFullBuyPrice().setScale(0))
                            .append('减').append(couponInfo.getDenomination().setScale(0));
                }
                sb.append("优惠券不在使用时间内");
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, sb.toString());
            } else {
                return null;
            }
        }

        // 4.查找出优惠券关联的商品列表
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponId(couponCode.getCouponId());

        CouponInfo couponInfo = couponInfoRepository.findById(couponCode.getCouponId()).orElse(null);
        CouponCodeVO couponCodeVo = new CouponCodeVO();
        KsBeanUtil.copyPropertiesThird(couponCode, couponCodeVo);
        couponCodeVo.setScopeType(couponInfo.getScopeType());
        couponCodeVo.setPlatformFlag(couponInfo.getPlatformFlag());
        couponCodeVo.setStoreId(couponInfo.getStoreId());
        couponCodeVo.setFullBuyPrice(couponInfo.getFullBuyPrice());
        couponCodeVo.setCouponType(couponInfo.getCouponType());
        couponCodeVo.setPluginType((CouponType.STOREFRONT_VOUCHER == couponInfo.getCouponType()
                || CouponType.BOSS_STOREFRONT_VOUCHER == couponInfo.getCouponType())
                ? PluginType.O2O: PluginType.NORMAL);
        List<String> goodsInfoIds = couponCodeServiceInterface.listCouponSkuIds(tradeItems, couponCodeVo, scopeList)
                .stream().map(TradeItemInfo::getSkuId).collect(Collectors.toList());

        // 6.如果优惠券没有关联下单商品
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }

        TradeMarketingResponse response = new TradeMarketingResponse();
        response.setTradeCoupon(TradeCoupon.builder()
                .couponCodeId(couponCode.getCouponCodeId())
                .couponCode(couponCode.getCouponCode())
                .goodsInfoIds(goodsInfoIds)
                .discountsAmount(couponInfo.getDenomination())
                .couponType(couponInfo.getCouponType())
                .couponMarketingType(couponInfo.getCouponMarketingType())
                .couponDiscountMode(couponInfo.getCouponDiscountMode())
                .maxDiscountLimit(couponInfo.getMaxDiscountLimit())
                .fullBuyPrice(couponInfo.getFullBuyPrice()).build());
        return response;
    }

    /**
     * 获取优惠券描述
     *
     * @return
     */
    private String getLabelMap(CouponCache coupon) {
        MarketingCouponPluginInterface couponPluginService = couponPluginContext.getCouponService(coupon.getCouponInfo().getCouponMarketingType());
        if (Objects.isNull(couponPluginService)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return couponPluginService.getLabelMap(coupon);
    }


}