package com.wanmi.sbc.marketing.coupon.model.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.response.CouponLeftResponse;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
public class CouponView {

    /**
     * 优惠券+活动，关系id
     */
    private String activityConfigId;

    /**
     * 优惠券Id
     */
    private String couponId;

    /**
     * 优惠券活动Id
     */
    private String activityId;

    /**
     * 购满多少钱
     */
    private Double fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    private Double denomination;

    /**
     * 商户id
     */
    private Long storeId;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    private DefaultFlag platformFlag;

    /**
     * 营销类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    private ScopeType scopeType;

    /**
     * 优惠券说明
     */
    private String couponDesc;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    private CouponType couponType;

    /**
     * 优惠券活动类型
     */
    private CouponActivityType couponActivityType;

    /**
     * 优惠券开始时间
     */
    private String couponStartTime;

    /**
     * 优惠券结束时间
     */
    private String couponEndTime;

    /**
     * 优惠券创建时间
     */
    private LocalDateTime createTime;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    private RangeDayType rangeDayType;

    /**
     * 有效天数
     */
    private Integer effectiveDays;

    /**
     * 领取状态
     */
    private boolean hasFetched;

    /**
     * 优惠券是否开始
     */
    private Boolean couponStarted;

    /**
     * 剩余状态
     */
    private boolean leftFlag;

    /**
     * 已抢百分比
     */
    private BigDecimal fetchPercent;

    /**
     * 优惠券即将过期
     */
    private boolean couponWillEnd;

    /**
     * 范围ids
     */
    private List<String> scopeIds;
    /**
     * 活动即将结束
     */
    private boolean activityWillEnd;
    /**
     * 优化券活动倒计时
     */
    private Long activityCountDown;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    private ParticipateType participateType;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private CouponMarketingType couponMarketingType;

    /**
     * 运费券是否包邮
     */
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    private Double maxDiscountLimit;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券活动券码id
     */
    private String couponCodeId;

    /**
     * @param list
     * @param leftMap
     * @param fetchMap
     * @return
     */
    public static List<CouponView> converter(List<CouponCache> list, Map<String, Map<String, CouponLeftResponse>> leftMap
            , Map<String, Map<String, String>> fetchMap) {
        return list.stream().map(item -> {
            //券是否剩余
            boolean leftFlag = item.getHasLeft() != DefaultFlag.NO &&
                    leftMap.get(item.getCouponActivityId()).get(item.getCouponInfoId()).getLeftCount() > 0;
            BigDecimal fetchPercent = BigDecimal.ZERO;
            //如果剩余，计算已抢百分比
            if (leftFlag) {
                CouponLeftResponse left = leftMap.get(item.getCouponActivityId()).get(item.getCouponInfoId());
                fetchPercent = new BigDecimal(left.getTotalCount()-left.getLeftCount()).divide(new BigDecimal(left.getTotalCount()), 2, RoundingMode.FLOOR);
            }

            boolean activityWillEnd = false;
            Long activityCountDown = 0L;
            //优惠券活动是否显示倒计时
            if (item.getCouponActivity().getEndTime().isAfter(LocalDateTime.now()) && item.getCouponActivity().getEndTime().isBefore(LocalDateTime.now().plusDays(1))) {
                activityWillEnd = true;
                activityCountDown = Duration.between(LocalDateTime.now(),item.getCouponActivity().getEndTime()).toMillis();
            }

            //券是否会过期，只统计RANGE_DAY，5天内过期为即将过期
            boolean couponWillEnd = false;
            if (item.getCouponInfo().getRangeDayType() == RangeDayType.RANGE_DAY) {
                LocalDateTime endTime = item.getCouponInfo().getEndTime();
                //如果结束时间加上5天，大于现在时间，即将过期
                if (LocalDateTime.now().plusDays(Constants.FIVE).isAfter(endTime)) {
                    couponWillEnd = true;
                }
            }

            //券的领用状态
            String couponCodeId = fetchMap.get(item.getCouponActivityId()).get(item.getCouponInfoId());

            //优惠券是否已经开始 - 建立在已经领券的基础上，并且券是未过期的
            Boolean couponStarted = null;
            if (StringUtils.isNotBlank(couponCodeId)) {
                if (item.getCouponInfo().getRangeDayType() == RangeDayType.DAYS || LocalDateTime.now().isAfter(item.getCouponInfo().getStartTime())) {
                    couponStarted = Boolean.TRUE;
                } else {
                    couponStarted = Boolean.FALSE;
                }
            }

            return CouponView.builder()
                    .activityConfigId(item.getActivityConfigId())
                    .couponId(item.getCouponInfoId())
                    .activityId(item.getCouponActivityId())
                    .fullBuyPrice(item.getCouponInfo().getFullBuyPrice())
                    .fullBuyType(item.getCouponInfo().getFullBuyType())
                    .denomination(item.getCouponInfo().getDenomination())
                    .platformFlag(item.getCouponInfo().getPlatformFlag())
                    .participateType(item.getCouponInfo().getParticipateType())
                    .storeId(item.getCouponInfo().getStoreId())
                    .scopeType(item.getCouponInfo().getScopeType())
                    .couponDesc(item.getCouponInfo().getCouponDesc())
                    .couponType(item.getCouponInfo().getCouponType())
                    .couponActivityType(item.getCouponActivity().getCouponActivityType())
                    .couponStartTime(Objects.isNull(item.getCouponInfo().getStartTime())?null:DateUtil.format(item.getCouponInfo().getStartTime(), DateUtil.FMT_TIME_1))
                    .couponEndTime(Objects.isNull(item.getCouponInfo().getEndTime())?null:DateUtil.format(item.getCouponInfo().getEndTime(), DateUtil.FMT_TIME_1))
                    .createTime(item.getCouponInfo().getCreateTime())
                    .rangeDayType(item.getCouponInfo().getRangeDayType())
                    .effectiveDays(item.getCouponInfo().getEffectiveDays())
                    .couponStarted(couponStarted)
                    .hasFetched(StringUtils.isNotBlank(couponCodeId))
                    .couponCodeId(couponCodeId)
                    .leftFlag(leftFlag)
                    .fetchPercent(fetchPercent)
                    .couponWillEnd(couponWillEnd)
                    .scopeIds(item.getScopes() != null ? item.getScopes().stream().sorted(
                            (o1, o2) -> {
                                if(Objects.equals(ScopeType.SKU, o1.getScopeType())){
                                    return -1;
                                }else{
                                    return Long.valueOf(o1.getScopeId()).compareTo(Long.valueOf(o2.getScopeId()));
                                }
                            }).map(CouponMarketingScope::getScopeId) .collect(Collectors.toList()) : null)
                    .activityCountDown(activityCountDown)
                    .activityWillEnd(activityWillEnd)
                    .couponName(item.getCouponInfo().getCouponName())
                    .couponMarketingType(item.getCouponInfo().getCouponMarketingType())
                    .couponDiscountMode(item.getCouponInfo().getCouponDiscountMode())
                    .maxDiscountLimit(item.getCouponInfo().getMaxDiscountLimit())
                    .build();
        }).collect(Collectors.toList());
    }
}
