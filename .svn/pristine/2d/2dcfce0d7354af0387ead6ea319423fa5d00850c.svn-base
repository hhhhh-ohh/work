package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据优惠券范围id查询优惠券商品作用范列表
 * @Author: daiyitian
 * @Date: Created In 上午9:27 2018/11/24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponMarketingScopeByScopeIdRequest extends BaseRequest {

    private static final long serialVersionUID = -2825237958435093165L;

    /**
     * 优惠券范围id
     */
    @Schema(description = "优惠券范围id")
    @NotBlank
    private String scopeId;

}
