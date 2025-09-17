package com.wanmi.sbc.marketing.newplugin.service;

import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/***
 * SBC优惠券插件Service
 * 实现SBC和插件的区别部分，方便路由
 * @author zhengyang
 * @className MarketingCouponService
 * @date 2022/4/1 10:45 上午
 **/
@Slf4j
@Service
public class MarketingCouponService implements MarketingCouponServiceInterface {

    /***
     * 根据请求获得营销VO标签-优惠券
     * @param request
     * @return
     */
    @Override
    public MarketingPluginSimpleLabelVO getCouponMarketingVo(GoodsInfoPluginRequest request) {
        List<CouponCache> couponCacheList = MarketingContext.getBaseRequest().getCouponCaches();
        GoodsInfoSimpleVO goodsInfo = request.getGoodsInfoPluginRequest();
        MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
        label.setMarketingType(MarketingPluginType.COUPON.getId());
        label.setMarketingDesc("券");
        List<String> couponIds = new ArrayList<>();
        for (CouponCache couponCache : couponCacheList) {

            switch (couponCache.getCouponInfo().getScopeType()) {
                case ALL:
                    if (couponCache.getCouponInfo().getStoreId().equals(goodsInfo.getStoreId())
                            || couponCache.getCouponInfo().getStoreId() == -1) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case BRAND:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(s -> s.getScopeId().equals(goodsInfo.getBrandId() + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case STORE_CATE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(
                                            s ->
                                                    goodsInfo
                                                            .getStoreCateIds()
                                                            .contains(
                                                                    Long.parseLong(s.getScopeId())))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case O2O_CATE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(
                                            s ->
                                                    goodsInfo
                                                            .getStoreCateIds()
                                                            .contains(
                                                                    Long.parseLong(s.getScopeId())))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case SKU:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(
                                            s ->
                                                    s.getScopeId()
                                                            .equals(
                                                                    goodsInfo.getGoodsInfoId()
                                                                            + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case BOSS_CATE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(s -> s.getScopeId().equals(goodsInfo.getCateId() + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case STORE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(s -> s.getScopeId().equals(goodsInfo.getStoreId() + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                default:
                    break;
            }
        }
        if (CollectionUtils.isNotEmpty(couponIds)) {
            label.setMarketingId(StringUtils.join(couponIds, ","));

            return  label;
        }

        return null;
    }

    /***
     * 返回一个固定的优惠券VO对象
     * @return
     */
    protected MarketingPluginSimpleLabelVO wrapLabelVo(){
        MarketingPluginSimpleLabelVO label = new MarketingPluginSimpleLabelVO();
        label.setMarketingType(MarketingPluginType.COUPON.getId());
        label.setMarketingDesc("券");
        return label;
    }
}
