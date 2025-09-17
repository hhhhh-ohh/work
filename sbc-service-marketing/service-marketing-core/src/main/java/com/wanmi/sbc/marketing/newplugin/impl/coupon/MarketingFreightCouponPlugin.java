package com.wanmi.sbc.marketing.newplugin.impl.coupon;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.bean.dto.CountCouponPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeAutoSelectDTO;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponDiscountMode;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.CountCouponPriceVO;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
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
 * @className MarketingFreightCouponPlugin  运费券
 * @description 运费券处理
 * @date 2022/9/27 16:49
 **/
@Slf4j
@Service
public class MarketingFreightCouponPlugin implements MarketingCouponPluginInterface {

    @Autowired
    private CouponCounterCommonService couponCounterCommonService;

    @Override
    public CouponMarketingType getCouponMarketingType() {
        return CouponMarketingType.FREIGHT_COUPON;
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

        //处理优惠券使用的目标商品
        List<CountCouponPriceGoodsInfoDTO> goodsInfoVOList = couponPriceRequest.getCountPriceGoodsInfoDTOList();
        BigDecimal splitPriceTotal = couponPriceRequest.getTotalPrice();
        if (Objects.isNull(splitPriceTotal)) {
            splitPriceTotal = goodsInfoVOList.stream().map(i -> i.getSplitPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        List<String> goodsInfoIds = goodsInfoVOList.stream().map(CountPriceGoodsInfoDTO :: getGoodsInfoId).collect(Collectors.toList());

        if(DefaultFlag.NO.equals(couponInfo.getPlatformFlag())) {
            splitPriceTotal = goodsInfoVOList.stream().filter(i -> i.getStoreId().equals(couponInfo.getStoreId())).map(CountPriceGoodsInfoDTO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            goodsInfoIds = goodsInfoVOList.stream().filter(i -> i.getStoreId().equals(couponInfo.getStoreId())).map(CountPriceGoodsInfoDTO :: getGoodsInfoId).collect(Collectors.toList());
        }
        //计算优惠券的优惠金额
        if (FullBuyType.FULL_MONEY.equals(couponInfo.getFullBuyType())
                && couponInfo.getFullBuyPrice().compareTo(splitPriceTotal) > 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }
        BigDecimal freightPrice = couponPriceRequest.getFreightPrice();
        BigDecimal discountsAmount = BigDecimal.ZERO;
        // 判断是否包邮
        if (Objects.equals(CouponDiscountMode.FREIGHT_FREE, couponInfo.getCouponDiscountMode())) {
            discountsAmount =
                    freightPrice.compareTo(BigDecimal.ZERO) > 0
                            ? freightPrice
                            : couponInfo.getDenomination();
        } else {
            discountsAmount =
                    freightPrice.compareTo(BigDecimal.ZERO) > 0
                                    && freightPrice.compareTo(couponInfo.getDenomination()) < 0
                            ? freightPrice
                            : couponInfo.getDenomination();
        }

        //封装优惠券信息
        response.setCountCouponPriceVO(CountCouponPriceVO.builder()
                .couponCode(couponCode.getCouponCode())
                .couponCodeId(couponCode.getCouponCodeId())
                .couponType(couponInfo.getCouponType())
                .couponMarketingType(CouponMarketingType.FREIGHT_COUPON)
                .couponDiscountMode(couponInfo.getCouponDiscountMode())
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
            //是否包邮
            if (Objects.equals(CouponDiscountMode.FREIGHT_FREE, coupon.getCouponInfo().getCouponDiscountMode())) {
                return String.format(
                        "满%s包邮",
                        coupon.getCouponInfo().getFullBuyPrice().intValue());
            } else {
                return String.format(
                        "满%s减%s运费",
                        coupon.getCouponInfo().getFullBuyPrice().intValue(),
                        coupon.getCouponInfo().getDenomination().intValue());
            }
        } else {
            if (Objects.equals(CouponDiscountMode.FREIGHT_FREE, coupon.getCouponInfo().getCouponDiscountMode())) {
                return "立享包邮";
            } else {
                return String.format("直减%s运费", coupon.getCouponInfo().getDenomination().intValue());
            }
        }
    }

    @Override
    public String getLabelMap(CouponInfo couponInfo) {
        if (Objects.equals(FullBuyType.FULL_MONEY, couponInfo.getFullBuyType())) {
            //是否包邮
            if (Objects.equals(CouponDiscountMode.FREIGHT_FREE, couponInfo.getCouponDiscountMode())) {
                return String.format(
                        "满%s包邮",
                        couponInfo.getFullBuyPrice().intValue());
            } else {
                return String.format(
                        "满%s减%s运费",
                        couponInfo.getFullBuyPrice().intValue(),
                        couponInfo.getDenomination().intValue());
            }
        } else {
            //是否包邮
            if (Objects.equals(CouponDiscountMode.FREIGHT_FREE, couponInfo.getCouponDiscountMode())) {
                return "立享包邮";
            } else {
                return String.format("直减%s运费", couponInfo.getDenomination().intValue());
            }
        }
    }

    @Override
    public List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList(List<String> goodsIds) {
        return new ArrayList<>();
    }

    @Override
    public CouponAutoSelectResponse autoSelect(CouponAutoSelectRequest request) {

        // 筛选出所有的运费券
        List<TradeCouponSnapshot.CheckCouponCode> couponInfos = this.filterAutoSelectCoupon(request.getCheckCouponCodes());
        // 店铺运费列表
        List<StoreFreightDTO> storeFreights = request.getStoreFreights();
        // 数据空值校验
        if (this.invalidAutoSelectRequest(request) || CollectionUtils.isEmpty(couponInfos) || CollectionUtils.isEmpty(storeFreights)) {
            return new CouponAutoSelectResponse(new ArrayList<>());
        }

        List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos = request.getCheckGoodsInfos();

        // 选券列表
        List<CouponCodeAutoSelectDTO> selectCoupons = new ArrayList<>();
        // 将checkCouponCodes转为Map，[couponCodeId] => [CheckCouponCode]
        Map<String, TradeCouponSnapshot.CheckCouponCode> checkCouponCodeMap = request.getCheckCouponCodes().stream()
                .collect(Collectors.toMap(TradeCouponSnapshot.CheckCouponCode::getCouponCodeId, Function.identity()));
        // 将storeFreights转为Map，[storeId] => [freight]
        Map<Long, BigDecimal> storeFreightMap = storeFreights.stream()
                .collect(Collectors.toMap(StoreFreightDTO::getStoreId, StoreFreightDTO::getFreight));
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
            // 适用的店铺运费
            BigDecimal applyFreightPrice = storeFreightMap.get(storeId);
            if (applyFreightPrice.compareTo(BigDecimal.ZERO) <= 0) {
                // 运费为0，无需使用运费券，直接返回
                continue;
            }
            // 重新计算运费券的面值，结合减免方式和店铺运费
            for (TradeCouponSnapshot.CheckCouponCode storeCoupon : storeCouponCodes) {
                if (CouponDiscountMode.FREIGHT_FREE == storeCoupon.getCouponDiscountMode()) {
                    // 如果是包邮，店铺运费即为面值
                    storeCoupon.setDenomination(storeFreightMap.get(storeId));
                }
            }
            // 命中最优运费券
            CouponCodeAutoSelectDTO targetCoupon = couponCounterCommonService.getTargetCoupon(applyFreightPrice, storeCheckGoodsInfo, storeCheckCouponCodes);
            if (Objects.nonNull(targetCoupon)) {
                // 计算并填充该券的实际优惠金额
                TradeCouponSnapshot.CheckCouponCode checkCouponCode = checkCouponCodeMap.get(targetCoupon.getCouponCodeId());
                targetCoupon.setActualDiscount(this.calcActualDiscount(checkCouponCode, applyFreightPrice));
                // 将目标券添加至选券列表
                selectCoupons.add(targetCoupon);
            }
        }
        return new CouponAutoSelectResponse(selectCoupons);
    }

    @Override
    public BigDecimal calcAndSplitPriceForCheckoutCoupons(CouponCounterRequest request) {
        // 计算实际优惠金额
        return this.calcActualDiscount(request.getCheckCouponCode(), request.getFreight());
    }

    /**
     * 计算券实际优惠金额
     * @param checkCouponCode 券快照
     * @param applyFreightPrice 运费金额
     * @return
     */
    private BigDecimal calcActualDiscount(TradeCouponSnapshot.CheckCouponCode checkCouponCode, BigDecimal applyFreightPrice) {
        if (Objects.nonNull(checkCouponCode) && Objects.nonNull(applyFreightPrice)) {
            if (CouponDiscountMode.FREIGHT_FREE == checkCouponCode.getCouponDiscountMode()) {
                // 如果是包邮，直接返回运费
                return applyFreightPrice;
            }
            // 否则返回 运费券面值 和 店铺运费 的较小值
            return applyFreightPrice.min(checkCouponCode.getDenomination());
        }
        return BigDecimal.ZERO;
    }
}