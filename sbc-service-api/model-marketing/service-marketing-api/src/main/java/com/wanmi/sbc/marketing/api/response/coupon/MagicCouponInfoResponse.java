package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * h5/app优惠券魔方页面，优惠券数据状态查询
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MagicCouponInfoResponse extends BasicResponse {

    @Schema(description = "优惠券主键Id")
    private String couponId;

    @Schema(description = "优惠券名称")
    private String couponName;

    @Schema(description = "起止时间类型 0：按起止时间，1：按N天有效")
    private RangeDayType rangeDayType;

    @Schema(description = "优惠券开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @Schema(description = "优惠券结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    @Schema(description = "有效天数")
    private Integer effectiveDays;


    @Schema(description = "购满类型 0：无门槛，1：满N元可使用")
    private FullBuyType fullBuyType;

    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;


    @Schema(description = "优惠券面值")
    private BigDecimal denomination;


    @Schema(description = "是否平台优惠券 1平台 0店铺")
    private DefaultFlag platformFlag;

    @Schema(description = "优惠券营销范围 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）")
    private ScopeType scopeType;

    @Schema(description = "优惠券说明")
    private String couponDesc;

    @Schema(description = "优惠券类型 0通用券 1店铺券")
    private CouponType couponType;

    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    @Schema(description = "优惠券营销类型 0满减券 1满折券 2运费券")
    private CouponMarketingType couponMarketingType;

    /**
     * 优惠券优惠方式 0减免 1包邮
     */
    @Schema(description = "优惠券优惠方式 0减免 1包邮")
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    @Schema(description = "最大优惠金额限制")
    private BigDecimal maxDiscountLimit;

    @Schema(description = "使用范围名称集合，如分类名、品牌名")
    private List<String> scopeNames = new ArrayList<>();

    @Schema(description = "优惠券查询状态 0可领取（当前账号未领取） 1查看可用（已领取但券还未生效） 2立即使用（已领取且券已生效） 3已抢光（券的上限已达到） 4活动未开始")
    private MagicCouponStatus couponStatus;

    @Schema(description = "优惠券总张数")
    private long totalCount;

    @Schema(description = "已领取数量")
    private long takeCount;

    @Schema(description = "绑定的优惠券活动ID")
    private String activityId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "商家名")
    private String storeName;

    /**
     * 领取状态
     */
    @Schema(description = "领取状态")
    private boolean hasFetched;


    /**
     * 剩余状态
     */
    @Schema(description = "剩余状态")
    private boolean leftFlag;

    /**
     * 活动即将结束
     */
    @Schema(description = "活动即将结束")
    private boolean activityWillEnd;

    /**
     * 优化券活动倒计时
     */
    @Schema(description = "优化券活动倒计时")
    private Long activityCountDown;

    /**
     * 优惠券是否即将过期
     */
    @Schema(description = "优惠券是否即将过期")
    private boolean couponWillEnd;

    @Schema(description = "优惠券是否开始")
    private Boolean couponStarted;

    /**
     * 已抢百分比
     */
    @Schema(description = "已抢百分比")
    private BigDecimal fetchPercent;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    @Schema(description = "门店营销类型(0,1) 0全部门店，1自定义门店")
    private ParticipateType participateType;

    /**
     * 优惠券活动类型
     **/
    private CouponActivityType activityCouponType;


}
