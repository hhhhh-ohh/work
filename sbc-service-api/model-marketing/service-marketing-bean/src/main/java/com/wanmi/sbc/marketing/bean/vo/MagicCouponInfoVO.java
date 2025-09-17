package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EDZ
 * @className MagicCouponInfoVO
 * @description 魔方返回优惠券信息
 * @date 2021/6/2 18:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MagicCouponInfoVO extends BasicResponse {

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

    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;

    @Schema(description = "购买类型 0：无门槛，1：满N元可使用")
    private FullBuyType fullBuyType;

    @Schema(description = "优惠券面值")
    private BigDecimal denomination;

    @Schema(description = "优惠券营销范围 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）")
    private ScopeType scopeType;

    @Schema(description = "优惠券总张数")
    private Long totalCount;

    @Schema(description = "关联的商品范围名称集合，如类目、品牌名、店铺分类")
    private  List<String> scopeNames = new ArrayList<>();

    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    @Schema(description = "优惠券营销类型（0满减券 1满折券 2运费券）")
    private CouponMarketingType couponMarketingType;

    /**
     * 优惠券优惠方式 0减免 1包邮
     */
    @Schema(description = "优惠券优惠方式 0减免 1包邮")
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    @Schema(description = "最大优惠金额限制（结合满折券使用）")
    private BigDecimal maxDiscountLimit;
}
