package com.wanmi.sbc.marketing.api.request.coupon;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 修改优惠券
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 7980447608311322872L;

    /**
     * 优惠券主键Id
     */
    @Schema(description = "优惠券Id")
    @NotBlank
    private String couponId;

    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    @NotBlank
    @Length(max = 10)
    private String couponName;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    @Schema(description = "起止时间类型")
    @NotNull
    private RangeDayType rangeDayType;

    /**
     * 有效天数
     */
    @Schema(description = "有效天数")
    @Range(min=1, max=365)
    private Integer effectiveDays;

    /**
     * 购满多少钱
     */
    @Schema(description = "购满多少钱")
    @Digits(integer = 99999, fraction = 0)
    @Range(min=1, max=99999)
    private BigDecimal fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    @Schema(description = "购满类型")
    @NotNull
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    @Schema(description = "优惠券面值")
    private BigDecimal denomination;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 营销范围类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Schema(description = "营销范围类型")
    @NotNull
    private ScopeType scopeType;


    @Schema(description = "优惠券描述")
    private String couponDesc;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    @Schema(description = "优惠券类型")
    @NotNull
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
    @Digits(integer = 999999, fraction = 0)
    @Range(min = 1, max = 999999)
    private BigDecimal maxDiscountLimit;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    @Schema(description = "门店营销范围类型")
    private ParticipateType participateType;

    /**
     * 门店促销范围Ids
     */
    @Schema(description = "门店促销范围Id列表")
    private List<Long> storeIds;


    /**
     * 促销范围Ids
     */
    @Schema(description = "促销范围Id列表")
    private List<String> scopeIds;


    /**
     * 优惠券分类Ids
     */
    @Schema(description = "优惠券分类Id列表")
    @Size(max=3)
    private List<String> cateIds;


    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;


    @Override
    public void checkParam() {
        if(this.getRangeDayType() == RangeDayType.RANGE_DAY){
            if(Objects.isNull(this.getEndTime())||Objects.isNull(this.getStartTime())){//起止时间非空验证
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(this.getEndTime().isBefore(this.getStartTime())){//起止时间
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }else{
            if(Objects.isNull(this.getEffectiveDays())){//n天内有效
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        //优惠券分类
        if(CollectionUtils.isNotEmpty(cateIds) && cateIds.size() > Constants.THREE){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //优惠券商品
        if(!Objects.equals( ScopeType.ALL,this.getScopeType())){
            if (CollectionUtils.isEmpty(scopeIds) ) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        // 1. 满减券的面值、门槛字段校验
        if (couponMarketingType == CouponMarketingType.REDUCTION_COUPON) {
            // 面值非空
            if (Objects.isNull(denomination)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 面值范围，[1, 99999] 的整数
            if (!ValidateUtil.isInteger(denomination) || !ValidateUtil.isInRange(denomination, 1, 99999)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (this.getFullBuyType() == FullBuyType.FULL_MONEY) {
                if (Objects.isNull(this.getFullBuyPrice())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else {
                    if (this.getFullBuyPrice().compareTo(denomination) <= 0) {//使用门槛值必须大于优惠券面值
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    if (this.getFullBuyPrice().compareTo(BigDecimal.ONE) < 0 || fullBuyPrice.compareTo(BigDecimal.valueOf(99999)) > 0) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                }
            }
        }

        // 2. 满折券的面值字段校验
        if (couponMarketingType == CouponMarketingType.DISCOUNT_COUPON) {
            // 面值非空
            if (Objects.isNull(denomination)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 面值范围 [0.1, 0.99] 的浮点数，且最多2位小数
            if (denomination.scale() > 2 || !ValidateUtil.isInRange(denomination, 0.1, 0.99)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        // 3. 运费券的面值、减免方式字段校验
        if (couponMarketingType == CouponMarketingType.FREIGHT_COUPON) {
            // 优惠方式非空
            if (Objects.isNull(couponDiscountMode)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 优惠方式为减免时，面值非空，面值范围，[1, 9999999] 的整数
            if (couponDiscountMode == CouponDiscountMode.REDUCTION) {
                if (Objects.isNull(denomination)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                if (!ValidateUtil.isInteger(denomination) || !ValidateUtil.isInRange(denomination, 1, 9999999)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }

    }
}
