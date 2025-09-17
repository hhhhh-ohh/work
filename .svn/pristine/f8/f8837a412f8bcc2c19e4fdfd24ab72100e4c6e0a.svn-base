package com.wanmi.ares.view.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @ClassName CouponInfoEffectView
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/20 19:44
 * @Version 1.0
 **/
@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponInfoEffectView extends CouponEffectView {
    @Schema(description = "优惠券id")
    private String couponId;

    @Schema(description = "优惠券名称")
    private String couponName;

    @Schema(description = "优惠券面值")
    private BigDecimal denomination;

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

    @Schema(description = "起止时间类型 0：按起止时间，1：按N天有效")
    private Integer rangeDayType;

    @Schema(description = "门店名称")
    private String storeName;

    @Schema(description = "购满类型")
    private Integer fullbuyType;

    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;

    @Schema(description = "优惠券类型")
    private Integer couponType;



    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    @Schema(description = "优惠券营销类型 0满减券 1满折券 2运费券")
    private Integer couponMarketingType;

    /**
     * 优惠券优惠方式 0减免 1包邮
     */
    @Schema(description = "优惠券优惠方式 0减免 1包邮")
    private Integer couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    @Schema(description = "最大优惠金额限制（结合满折券使用）")
    private BigDecimal maxDiscountLimit;

    @Schema(description = "面值文案")
    private String denominationStr;
}
