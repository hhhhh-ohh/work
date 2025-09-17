package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author EDZ
 * @className EsMagicCouponInfoPageRequest
 * @description 魔方优惠券列表查询
 * @date 2021/6/2 17:17
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MagicCouponInfoPageRequest extends BaseQueryRequest {

    @Schema(description = "优惠券活动ID【必传】")
    @NotBlank
    private String activityId;

    @Schema(description = "优惠券名称模糊条件查询【非必传】")
    private String likeCouponName;

    @Schema(description = "使用范围 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用） 注：此参数不传就表示不限【非必传】")
    private ScopeType scopeType;

    @Schema(description = "优惠券营销类型")
    private CouponMarketingType couponMarketingType;
}

