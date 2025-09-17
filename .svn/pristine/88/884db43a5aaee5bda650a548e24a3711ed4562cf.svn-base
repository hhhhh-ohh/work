package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema
@Data
public class GetCouponGroupResponse extends BasicResponse {
    /**
     * 优惠券主键Id
     */
    @Schema(description = "优惠券Id")
    private String couponId;

    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    private String couponName;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    @Schema(description = "起止时间类型")
    private RangeDayType rangeDayType;

    /**
     * 优惠券开始时间
     */
    @Schema(description = "优惠券开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 优惠券结束时间
     */
    @Schema(description = "优惠券结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 有效天数
     */
    @Schema(description = "有效天数")
    private Integer effectiveDays;

    /**
     * 购满多少钱
     */
    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    @Schema(description = "购满类型")
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    @Schema(description = "优惠券面值")
    private BigDecimal denomination;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long storeId;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;

    /**
     * 营销范围类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Schema(description = "营销范围类型")
    private ScopeType scopeType;

    /**
     * 优惠券说明
     */
    @Schema(description = "优惠券说明")
    private String couponDesc;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券总张数
     */
    @Schema(description = "优惠券总张数")
    private Long totalCount;


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


}
