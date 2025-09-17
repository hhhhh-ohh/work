package com.wanmi.sbc.marketing.payingmember;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.levelrights.CustomerLevelRightsQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelListRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelListRequest;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelRightsVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberDiscountRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.marketing.api.request.payingmember.PayingMemberSkuRequest;
import com.wanmi.sbc.marketing.api.response.payingmember.PayingMemberSkuResponse;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivityConfig;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.repository.CouponInfoRepository;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityConfigService;
import com.wanmi.sbc.marketing.payingmember.bean.PayingMemberDiscount;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className PayingMemberService
 * @description
 * @date 2022/5/20 2:07 PM
 **/
@Service
public class PayingMemberService {

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

    @Autowired
    private CustomerLevelRightsQueryProvider customerLevelRightsQueryProvider;

    @Autowired
    private CouponActivityConfigService couponActivityConfigService;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    /**
     * 计算付费会员优惠信息
     * @param request
     * @return
     */
    public PayingMemberSkuResponse skuCustomer(PayingMemberSkuRequest request) {
        //用户已有付费会员不计算
        if (StringUtils.isNotBlank(request.getCustomerId())) {
            PayingMemberCustomerRelListRequest relListRequest = PayingMemberCustomerRelListRequest.builder()
                    .customerId(request.getCustomerId()).delFlag(DeleteFlag.NO).expirationDateBegin(LocalDate.now()).build();
            List<PayingMemberCustomerRelVO> customerRelVOS = payingMemberCustomerRelQueryProvider
                    .list(relListRequest).getContext().getPayingMemberCustomerRelVOList();
            if (CollectionUtils.isNotEmpty(customerRelVOS)) {
                return null;
            }
        }

        //查询付费会员等级
        PayingMemberLevelListRequest levelRequest = PayingMemberLevelListRequest.builder()
                .delFlag(DeleteFlag.NO).levelState(NumberUtils.INTEGER_ZERO).build();
        List<PayingMemberLevelVO> levelList = payingMemberLevelQueryProvider
                .listForSku(levelRequest).getContext().getPayingMemberLevelVOList();

        //计算商品折扣价
        Optional<PayingMemberDiscount> max = levelList.stream().map(level -> {
            Boolean storeFlag = Boolean.FALSE;
            BigDecimal discountPrice = null;
            //商家范围校验
            if (NumberUtils.INTEGER_ZERO.equals(level.getLevelStoreRange())) {
                storeFlag = BoolFlag.NO.equals(request.getCompanyType());
            } else {
                storeFlag = level.getPayingMemberStoreRelVOS().stream()
                        .anyMatch(rel -> rel.getStoreId().equals(request.getStoreId()));
            }

            if (storeFlag) {
                List<Integer> rightsIds = level.getPayingMemberPriceVOS().stream()
                        .flatMap(v -> v.getPayingMemberRightsRelVOS().stream())
                        .map(v -> v.getRightsId())
                        .distinct()
                        .collect(Collectors.toList());
                List<CouponInfo> couponInfos = this.getCoupon(rightsIds);
                if (NumberUtils.INTEGER_ZERO.equals(level.getLevelDiscountType())) {
                    //统一折扣
                    BigDecimal payingMemberPrice = request.getGoodsPrice()
                            .multiply(level.getLevelAllDiscount().divide(BigDecimal.TEN))
                            .setScale(2, RoundingMode.HALF_UP);
                    //叠加优惠券
                    payingMemberPrice = this.getCouponDiscount(payingMemberPrice, couponInfos);
                    discountPrice = request.getGoodsPrice().subtract(payingMemberPrice);
                } else {
                    Optional<PayingMemberDiscountRelVO> optional = level.getPayingMemberDiscountRelVOS().stream()
                            .filter(rel -> rel.getGoodsInfoId().equals(request.getGoodsInfoId()))
                            .findFirst();
                    if (optional.isPresent()) {
                        //自定义折扣
                        BigDecimal payingMemberPrice = request.getGoodsPrice()
                                .multiply(optional.get().getDiscount().divide(BigDecimal.TEN))
                                .setScale(2, RoundingMode.HALF_UP);
                        //叠加优惠券
                        payingMemberPrice = this.getCouponDiscount(payingMemberPrice, couponInfos);
                        discountPrice = request.getGoodsPrice().subtract(payingMemberPrice);
                    }
                }
            }
            PayingMemberDiscount discount = new PayingMemberDiscount();
            if (discountPrice != null) {
                discount.setLevelId(level.getLevelId());
                discount.setLevelName(level.getLevelNickName());
                discount.setDiscountPrice(discountPrice);
            }
            return discount;
        }).filter(s -> Objects.nonNull(s.getDiscountPrice())).max(Comparator.comparing(PayingMemberDiscount::getDiscountPrice));
        if (max.isPresent()) {
            return KsBeanUtil.convert(max.get(), PayingMemberSkuResponse.class);
        }
        return null;
    }

    /**
     * 查询权益优惠券
     * @param rightsIds
     * @return
     */
    public List<CouponInfo> getCoupon(List<Integer> rightsIds) {

        if (CollectionUtils.isEmpty(rightsIds)) {
            return new ArrayList<>();
        }

        CustomerLevelRightsQueryRequest rightsQueryRequest = CustomerLevelRightsQueryRequest.builder()
                .rightsIdList(rightsIds).rightsType(LevelRightsType.COUPON_GIFT)
                .delFlag(DeleteFlag.NO).status(NumberUtils.INTEGER_ONE).build();
        List<CustomerLevelRightsVO> rightsVOS = customerLevelRightsQueryProvider.list(rightsQueryRequest).getContext().getCustomerLevelRightsVOList();
        List<CouponInfo> couponInfos = rightsVOS.stream().flatMap(rights -> {
            // 查询券礼包权益关联的优惠券活动配置列表
            List<CouponActivityConfig> couponActivityConfigList = couponActivityConfigService.queryByActivityId(rights.getActivityId());
            // 根据配置查询需要发放的优惠券列表
            List<CouponInfo> couponInfoList = couponInfoRepository.queryByIds(couponActivityConfigList.stream().map(
                    CouponActivityConfig::getCouponId).collect(Collectors.toList()));
            return couponInfoList.stream();
        }).collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(CouponInfo::getCouponId))), ArrayList::new));
        return couponInfos;
    }

    /**
     * 计算最低商品价格
     * @param goodsPrice
     * @param couponInfoList
     * @return
     */
    public BigDecimal getCouponDiscount(BigDecimal goodsPrice, List<CouponInfo> couponInfoList) {
        BigDecimal minGoodsPrice = goodsPrice;
        if (CollectionUtils.isEmpty(couponInfoList)) {
            return minGoodsPrice;
        }
        Optional<BigDecimal> optional = couponInfoList.stream().map(couponInfo -> {
            Boolean useFlag = Boolean.FALSE;
            BigDecimal discountPrice = goodsPrice;
            if (FullBuyType.NO_THRESHOLD.equals(couponInfo.getFullBuyType())
                    || goodsPrice.compareTo(couponInfo.getFullBuyPrice()) >= 0) {
                useFlag = Boolean.TRUE;
            }
            if (useFlag) {
                discountPrice = goodsPrice.subtract(couponInfo.getDenomination());
                discountPrice = discountPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : discountPrice;
            }
            return discountPrice;
        }).min(BigDecimal::compareTo);
        if (optional.isPresent()) {
            minGoodsPrice = optional.get();
        }
        return minGoodsPrice;
    }
}
