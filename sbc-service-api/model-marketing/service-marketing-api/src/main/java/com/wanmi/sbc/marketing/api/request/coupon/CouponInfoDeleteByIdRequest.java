package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据id删除优惠券的请求结构
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoDeleteByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -9130195635676668766L;

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券Id")
    @NotBlank
    private String couponId;

    /**
     * 记录操作人
     */
    @Schema(description = "记录操作人")
    @NotBlank
    private String operatorId;
}
