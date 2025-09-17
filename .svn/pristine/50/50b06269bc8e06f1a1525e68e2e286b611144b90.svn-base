package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
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

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 15:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponNoScopeVO extends BasicResponse {

    private static final long serialVersionUID = -7413669478575515605L;

    /**
     * 优惠券Id
     */
    @Schema(description = "优惠券Id")
    private String couponId;

    /**
     * 优惠券活动Id
     */
    @Schema(description = "优惠券活动Id")
    private String activityId;

    /**
     * 购满多少钱
     */
    @Schema(description = "购满多少钱")
    private Double fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    @Schema(description = "购满类型")
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    @Schema(description = "优惠券面值")
    private Double denomination;

    /**
     * 商户id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;

    /**
     * 营销类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Schema(description = "优惠券营销范围")
    private ScopeType scopeType;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;


    /**
     * 优惠券开始时间
     */
    @Schema(description = "优惠券开始时间")
    private String couponStartTime;

    /**
     * 优惠券结束时间
     */
    @Schema(description = "优惠券结束时间")
    private String couponEndTime;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    @Schema(description = "起止时间类型")
    private RangeDayType rangeDayType;

    /**
     * 有效天数
     */
    @Schema(description = "有效天数")
    private Integer effectiveDays;

    /**
     * 优惠券是否已领取
     */
    @Schema(description = "优惠券是否已领取")
    private boolean hasFetched;

    /**
     * 优惠券是否开始
     */
    @Schema(description = "优惠券是否开始")
    private Boolean couponStarted;

    /**
     * 优惠券是否有剩余
     */
    @Schema(description = "优惠券是否有剩余")
    private boolean leftFlag;

    /**
     * 已抢百分比
     */
    @Schema(description = "已抢百分比")
    private BigDecimal fetchPercent;

    /**
     * 优惠券是否即将过期
     */
    @Schema(description = "优惠券是否即将过期")
    private boolean couponWillEnd;

    /**
     * 优惠券活动是否即将结束
     */
    @Schema(description = "优惠券活动是否即将结束")
    private boolean activityWillEnd;
    /**
     * 优化券活动倒计时
     */
    @Schema(description = "优化券活动倒计时")
    private Long activityCountDown;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    @Schema(description = "门店营销类型(0,1) 0全部门店，1自定义门店")
    private ParticipateType participateType;

    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    private String couponName;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    @Schema(description = "优惠券营销类型（0满减券 1满折券 2运费券）")
    private CouponMarketingType couponMarketingType;

    /**
     * 运费券是否包邮
     */
    @Schema(description = "运费券是否包邮")
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    @Schema(description = "最大优惠金额限制（结合满折券使用）")
    private Double maxDiscountLimit;

    /**
     *  优惠券创建时间
     */
    @Schema(description = "优惠券创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 优惠券活动券码id
     */
    @Schema(description = "优惠券活动券码id")
    private String couponCodeId;

}



