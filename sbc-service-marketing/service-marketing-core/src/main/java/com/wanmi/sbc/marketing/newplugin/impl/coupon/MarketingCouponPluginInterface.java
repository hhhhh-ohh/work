package com.wanmi.sbc.marketing.newplugin.impl.coupon;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.bean.constant.CouponErrorCode;
import com.wanmi.sbc.marketing.bean.dto.CountCouponPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.countPrice.CountPriceService;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.request.CouponAutoSelectRequest;
import com.wanmi.sbc.marketing.coupon.request.CouponCounterRequest;
import com.wanmi.sbc.marketing.coupon.response.CouponAutoSelectResponse;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: wur
 * @Date: 2022/9/28 14:09
 */
public interface MarketingCouponPluginInterface extends CountPriceService {

    /**
     * @description  获取券活动类型
     * @author  wur
     * @date: 2022/9/28 16:28
     * @return
     **/
    CouponMarketingType getCouponMarketingType();

    /**
     * @description 获取优惠券描述
     * @author  wur
     * @date: 2022/9/28 16:28
     * @param coupon   券信息
     * @return
     **/
    String getLabelMap(CouponCache coupon);

    /**
     * @description
     * @author  wur
     * @date: 2022/9/30 11:09
     * @param couponInfo   优惠券信息
     * @return
     **/
    String getLabelMap(CouponInfo couponInfo);

    /**
     * @description   优惠算价逻辑
     * @author  wur
     * @date: 2022/9/28 16:28
     * @param couponCode    券码信息
     * @param couponInfo    优惠券信息
     * @param couponPriceRequest   请求对象
     * @return
     **/
    TradeCountCouponPriceResponse countCouponPrice(CouponCode couponCode, CouponInfo couponInfo, TradeCountCouponPriceRequest couponPriceRequest);


    /**
     * @description  过滤目标用商品
     * @author  wur
     * @date: 2022/2/28 15:57
     * @param goodsInfoVOList
     * @param couponInfo
     * @return
     **/
    default List<CountPriceGoodsInfoDTO> filterGoodsInfoList(List<CountCouponPriceGoodsInfoDTO> goodsInfoVOList, CouponInfo couponInfo, List<CouponMarketingScope> scopeList) {
        if (ScopeType.ALL.toValue() != couponInfo.getScopeType().toValue()
                && org.springframework.util.CollectionUtils.isEmpty(scopeList)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }

        Stream<CountCouponPriceGoodsInfoDTO> tradeItemsStream = goodsInfoVOList.stream();
        List<String> scopeIds = WmCollectionUtils.convert(scopeList, CouponMarketingScope::getScopeId);
        switch (couponInfo.getScopeType()) {
            case ALL: //全部商品
                if (couponInfo.getPlatformFlag() != com.wanmi.sbc.common.enums.DefaultFlag.YES) {
                    tradeItemsStream =
                            goodsInfoVOList.stream().filter((item) -> couponInfo.getStoreId().equals(item.getStoreId()));
                }
                break;
            case BRAND: //按品牌
                if (couponInfo.getPlatformFlag() == com.wanmi.sbc.common.enums.DefaultFlag.YES) {
                    tradeItemsStream = goodsInfoVOList.stream()
                            .filter((item) -> scopeIds.contains(String.valueOf(item.getBrandId())));
                } else {
                    tradeItemsStream = goodsInfoVOList.stream()
                            .filter((item) -> couponInfo.getStoreId().equals(item.getStoreId()))
                            .filter((item) -> scopeIds.contains(String.valueOf(item.getBrandId())));
                }
                break;
            case BOSS_CATE: //按平台分类
                tradeItemsStream = goodsInfoVOList.stream()
                        .filter((item) -> scopeIds.contains(String.valueOf(item.getCateId())));
                break;
            case STORE_CATE: //按店铺分类
                wrapperStoreCateIds(goodsInfoVOList);
                tradeItemsStream = goodsInfoVOList.stream()
                        .filter((item) -> !Collections.disjoint(scopeIds,
                                item.getStoreCateIds().stream().map(String::valueOf).collect(Collectors.toList()))
                        );
                break;
            case STORE: //按店铺分类
                tradeItemsStream = goodsInfoVOList.stream().filter((item) -> scopeIds.contains(item.getStoreId().toString()));
                break;
            case SKU: // 自定义货品
                tradeItemsStream = goodsInfoVOList.stream()
                        .filter((item) -> scopeIds.contains(item.getGoodsInfoId()));
                break;
            default:
                break;
        }
        return tradeItemsStream.collect(Collectors.toList());
    }

    /**
     *
     * @description 封装商品的店铺分类信息
     * @author  wur
     * @date: 2022/2/28 11:13
     * @param goodsInfoVOList   目标商品信息
     **/
    default void wrapperStoreCateIds(List<CountCouponPriceGoodsInfoDTO> goodsInfoVOList) {
        List<String> goodsIds =
                goodsInfoVOList.stream().filter(goodsInfo -> org.springframework.util.CollectionUtils.isEmpty(goodsInfo.getStoreCateIds())).collect(Collectors.toList()).stream().map(CountPriceGoodsInfoDTO :: getGoodsId).collect(Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(goodsIds)) {
            List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList = this.storeCateGoodsRelaVOList(goodsIds);
            if (org.springframework.util.CollectionUtils.isEmpty(storeCateGoodsRelaVOList)) {
                return;
            }
            Map<String, List<StoreCateGoodsRelaVO>> relaVOMap = storeCateGoodsRelaVOList.stream().collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));
            goodsInfoVOList.stream().filter(goodsInfo -> org.springframework.util.CollectionUtils.isEmpty(goodsInfo.getStoreCateIds())).forEach(goodsInfo -> {
                if (relaVOMap.containsKey(goodsInfo.getGoodsId())) {
                    goodsInfo.setStoreCateIds(relaVOMap.get(goodsInfo.getGoodsId()).stream().map(StoreCateGoodsRelaVO :: getStoreCateId).collect(Collectors.toList()));
                }
            });
        }
    }

    /**
    *
     * @description  查询商品对应的商家类目信息
     * @author  wur
     * @date: 2022/9/28 15:35
     * @param goodsIds    商品Id
     * @return  类目与商品关联关系
     **/
    List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList(List<String> goodsIds);

    /**
     * @description 封装最后返回数据
     * @author  wur
     * @date: 2022/2/28 15:57
     * @param goodsInfoVOList
     * @return
     **/
    default List<CountPriceItemGoodsInfoVO> wrapperItemGoodsInfo(List<CountCouponPriceGoodsInfoDTO> goodsInfoVOList) {
        List<CountPriceItemGoodsInfoVO> goodsInfoList = new ArrayList<>();
        goodsInfoVOList.forEach(goodsInfoVO -> {
            goodsInfoList.add(CountPriceItemGoodsInfoVO.builder()
                    .goodsInfoId(goodsInfoVO.getGoodsInfoId())
                    .splitPrice(goodsInfoVO.getSplitPrice())
                    .num(goodsInfoVO.getNum())
                    .price(goodsInfoVO.getPrice())
                    .discountsAmount(BigDecimal.ZERO)
                    .storeId(goodsInfoVO.getStoreId())
                    .build());
        });
        return goodsInfoList;
    }


    // ========================================= 自动选券相关接口 start =========================================

    /**
     * 自动选券接口
     * @param request 请求
     * @return
     */
    CouponAutoSelectResponse autoSelect(CouponAutoSelectRequest request);

    /**
     * 计算券并均摊券的实际优惠，这个方法主要是给 checkoutCoupons 使用（订单确认页使用优惠券）
     * @see CouponCodeService#checkoutCoupons(com.wanmi.sbc.marketing.api.request.coupon.CouponCheckoutRequest)
     * @param request
     * @return
     */
    BigDecimal calcAndSplitPriceForCheckoutCoupons(CouponCounterRequest request);

    /**
     * 校验自动选券请求是否非法
     * @param request 参数
     * @return
     */
    default boolean invalidAutoSelectRequest(CouponAutoSelectRequest request) {
        List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos = request.getCheckGoodsInfos();
        List<TradeCouponSnapshot.CheckCouponCode> checkCouponCodes = request.getCheckCouponCodes();
        return CollectionUtils.isEmpty(checkGoodsInfos) || CollectionUtils.isEmpty(checkCouponCodes);
    }

    /**
     * 根据支持的策略券类型，过滤选券入参
     * @param checkCouponCodes 券列表
     * @return
     */
    default List<TradeCouponSnapshot.CheckCouponCode> filterAutoSelectCoupon(List<TradeCouponSnapshot.CheckCouponCode> checkCouponCodes) {
        if (CollectionUtils.isEmpty(checkCouponCodes)) {
            return checkCouponCodes;
        }
        return checkCouponCodes.stream().filter(item -> getCouponMarketingType() == item.getCouponMarketingType()).collect(Collectors.toList());
    }

    // ========================================= 自动选券相关接口 end =========================================

}
