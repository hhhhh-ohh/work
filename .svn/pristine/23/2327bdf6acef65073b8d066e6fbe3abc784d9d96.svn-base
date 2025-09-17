package com.wanmi.sbc.marketing.newplugin.impl.coupon;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateGoodsRelaQueryProvider;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateGoodsRelaListByGoodsIdsRequest;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.bean.dto.CountPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeAutoSelectDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.CountCouponPriceVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.repository.CouponMarketingScopeRepository;
import com.wanmi.sbc.marketing.coupon.request.CouponAutoSelectRequest;
import com.wanmi.sbc.marketing.coupon.request.CouponCounterRequest;
import com.wanmi.sbc.marketing.coupon.response.CouponAutoSelectResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wur
 * @className MarketingReductionCouponPlugin   满减券
 * @description 满减券处理
 * @date 2022/9/27 16:49
 **/
@Slf4j
@Service
public class MarketingReductionCouponPlugin implements MarketingCouponPluginInterface {

    @Autowired
    private StoreCateGoodsRelaQueryProvider storeCateGoodsRelaQueryProvider;

    @Autowired
    private CouponMarketingScopeRepository couponMarketingScopeRepository;

    @Autowired
    private CouponCounterCommonService couponCounterCommonService;

    @Override
    public CouponMarketingType getCouponMarketingType() {
        return CouponMarketingType.REDUCTION_COUPON;
    }

    @Override
    public TradeCountCouponPriceResponse countCouponPrice(CouponCode couponCode, CouponInfo couponInfo, TradeCountCouponPriceRequest couponPriceRequest) {
        TradeCountCouponPriceResponse response = new TradeCountCouponPriceResponse();
        //验证优惠券码使用时间
        if (LocalDateTime.now().isAfter(couponCode.getEndTime()) || LocalDateTime.now().isBefore(couponCode.getStartTime())) {
            StringBuilder sb = new StringBuilder("很抱歉，");
            sb.append(this.getLabelMap(couponInfo));
            sb.append("优惠券不在使用时间内");
            log.warn("优惠券-算价服务 不满足使用规则:{}", sb);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, sb.toString());
        }

        // 处理优惠券使用的目标商品
        List<CountPriceGoodsInfoDTO> goodsInfoVOList =
                filterGoodsInfoList(
                        couponPriceRequest.getCountPriceGoodsInfoDTOList(),
                        couponInfo,
                        couponMarketingScopeRepository.findByCouponId(couponInfo.getCouponId()));
        BigDecimal splitPriceTotal = goodsInfoVOList.stream().map(i -> i.getSplitPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (splitPriceTotal.compareTo(BigDecimal.ZERO) <= 0) {
            return response;
        }
        List<String> goodsInfoIds = goodsInfoVOList.stream().map(CountPriceGoodsInfoDTO :: getGoodsInfoId).collect(Collectors.toList());

        //计算优惠券的优惠金额
        if (FullBuyType.FULL_MONEY.equals(couponInfo.getFullBuyType())
                && couponInfo.getFullBuyPrice().compareTo(splitPriceTotal) > 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }
        BigDecimal discountsAmount = splitPriceTotal.compareTo(couponInfo.getDenomination()) < 0 ? splitPriceTotal : couponInfo.getDenomination();

        //封装返回数据
        List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList = wrapperItemGoodsInfo(couponPriceRequest.getCountPriceGoodsInfoDTOList());

        //计算目标商品均摊优惠金额
        discountsAmount = divideAmount(itemGoodsInfoVOList.stream().filter(goodsInfo -> goodsInfoIds.contains(goodsInfo.getGoodsInfoId())).collect(Collectors.toList()), discountsAmount);

        //封装优惠券信息
        response.setGoodsInfoList(itemGoodsInfoVOList);
        response.setCountCouponPriceVO(CountCouponPriceVO.builder()
                .couponCode(couponCode.getCouponCode())
                .couponCodeId(couponCode.getCouponCodeId())
                .couponType(couponInfo.getCouponType())
                .couponMarketingType(CouponMarketingType.REDUCTION_COUPON)
                .fullBuyPrice(couponInfo.getFullBuyPrice())
                .discountsAmount(discountsAmount)
                .goodsInfoIds(goodsInfoIds)
                .marketingCustomerType(couponCode.getMarketingCustomerType())
                .build());
        log.info("优惠券-算价服务 End，响应:{}", JSONObject.toJSONString(response));
        return response;
    }

    @Override
    public String getLabelMap(CouponCache coupon) {
        if (coupon.getCouponInfo().getFullBuyType() == FullBuyType.FULL_MONEY) {
            return String.format(
                    "满%s减%s",
                    coupon.getCouponInfo().getFullBuyPrice().intValue(),
                    coupon.getCouponInfo().getDenomination().intValue());
        } else {
            return String.format("直减%s", coupon.getCouponInfo().getDenomination().intValue());
        }
    }

    @Override
    public String getLabelMap(CouponInfo couponInfo) {
        if (couponInfo.getFullBuyType() == FullBuyType.FULL_MONEY) {
            return String.format(
                    "满%s减%s",
                    couponInfo.getFullBuyPrice().setScale(0),
                    couponInfo.getDenomination().setScale(0));
        } else {
            return String.format("直减%s", couponInfo.getDenomination().setScale(0));
        }
    }

    @Override
    public List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList(List<String> goodsIds) {
        return storeCateGoodsRelaQueryProvider.listByGoodsIds(new StoreCateGoodsRelaListByGoodsIdsRequest(goodsIds)).getContext().getStoreCateGoodsRelaVOList();
    }

    @Override
    public CouponAutoSelectResponse autoSelect(CouponAutoSelectRequest request) {

        // 筛选出所有的满减券
        List<TradeCouponSnapshot.CheckCouponCode> couponInfos = this.filterAutoSelectCoupon(request.getCheckCouponCodes());
        // 数据空值校验
        if (this.invalidAutoSelectRequest(request) || CollectionUtils.isEmpty(couponInfos)) {
            return new CouponAutoSelectResponse(new ArrayList<>());
        }

        List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos = request.getCheckGoodsInfos();

        // 选券列表
        List<CouponCodeAutoSelectDTO> selectCoupons = new ArrayList<>();
        // 将checkCouponCodes转为Map，[couponCodeId] => [CheckCouponCode]
        Map<String, TradeCouponSnapshot.CheckCouponCode> checkCouponCodeMap = request.getCheckCouponCodes().stream()
                .collect(Collectors.toMap(TradeCouponSnapshot.CheckCouponCode::getCouponCodeId, Function.identity()));
        // 以店铺维度将券分组
        Map<Long, List<TradeCouponSnapshot.CheckCouponCode>> storeCouponMap =
                couponInfos.stream().collect(Collectors.groupingBy(TradeCouponSnapshot.CheckCouponCode::getStoreId));
        // 以店铺维度将商品分组
        Map<Long, List<TradeCouponSnapshot.CheckGoodsInfo>> storeGoodsInfoMap = checkGoodsInfos.stream().collect(
                Collectors.groupingBy(TradeCouponSnapshot.CheckGoodsInfo::getStoreId));

        // 遍历店铺券Map
        for (Map.Entry<Long, List<TradeCouponSnapshot.CheckCouponCode>> entry : storeCouponMap.entrySet()) {
            // 店铺ID
            Long storeId = entry.getKey();
            // 店铺券列表
            List<TradeCouponSnapshot.CheckCouponCode> storeCouponCodes = entry.getValue();
            // 店铺商品列表
            List<TradeCouponSnapshot.CheckGoodsInfo> storeCheckGoodsInfo = storeGoodsInfoMap.get(storeId);
            // 店铺券快照列表
            List<TradeCouponSnapshot.CheckCouponCode> storeCheckCouponCodes = storeCouponCodes.stream()
                    .map(item -> checkCouponCodeMap.get(item.getCouponCodeId())).collect(Collectors.toList());

            if (Constants.BOSS_DEFAULT_STORE_ID.equals(storeId)) {
                // 如果处理的是平台券，应重新设置券快照列表
                storeCheckGoodsInfo = checkGoodsInfos;
                storeCheckCouponCodes = couponInfos;
            }

            // 店铺商品总额
            BigDecimal storeSkuPrice = storeCheckGoodsInfo.stream().map(TradeCouponSnapshot.CheckGoodsInfo::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            // 命中满减券
            CouponCodeAutoSelectDTO targetCoupon = couponCounterCommonService.getTargetCoupon(storeSkuPrice, storeCheckGoodsInfo, storeCheckCouponCodes);
            if (Objects.nonNull(targetCoupon)) {
                // 计算并填充该券的实际优惠金额
                TradeCouponSnapshot.CheckCouponCode checkCouponCode = checkCouponCodeMap.get(targetCoupon.getCouponCodeId());
                targetCoupon.setActualDiscount(this.calcActualDiscount(checkGoodsInfos, checkCouponCode));
                // 将目标券添加至选券列表
                selectCoupons.add(targetCoupon);
            }
        }
        return new CouponAutoSelectResponse(selectCoupons);
    }

    @Override
    public BigDecimal calcAndSplitPriceForCheckoutCoupons(CouponCounterRequest request) {
        List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos = request.getGoodsInfos();
        TradeCouponSnapshot.CheckCouponCode checkCouponCode = request.getCheckCouponCode();
        BigDecimal actualDiscount = this.calcActualDiscount(checkGoodsInfos, request.getCheckCouponCode());
        couponCounterCommonService.splitSkuPriceForCoupon(checkGoodsInfos, checkCouponCode, actualDiscount);
        return actualDiscount;
    }

    /**
     * 计算券实际优惠金额
     * @param checkGoodsInfos 商品列表
     * @param checkCouponCode 券快照
     * @return
     */
    private BigDecimal calcActualDiscount(List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos,
                                          TradeCouponSnapshot.CheckCouponCode checkCouponCode) {
        if (CollectionUtils.isNotEmpty(checkGoodsInfos) && Objects.nonNull(checkCouponCode)) {
            // 计算指定券适用商品的总额
            BigDecimal applySkuTotalPrice = couponCounterCommonService.calcApplySkuTotalPriceForCoupon(checkGoodsInfos, checkCouponCode);
            // 实际优惠金额为 满减券面值 和 商品总额中的较小值
            return applySkuTotalPrice.min(checkCouponCode.getDenomination());
        }
        return BigDecimal.ZERO;
    }

}