package com.wanmi.sbc.marketing.api.request.pointscoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户领取积分优惠券请求结构
 *
 * @author minchen
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsCouponFetchRequest extends BaseRequest {

    private static final long serialVersionUID = -3197241046917249109L;
    /**
     * 领券用户Id
     */
    @Schema(description = "领券用户Id")
    @NotBlank
    private String customerId;

    /**
     * 积分优惠券Id
     */
    @Schema(description = "积分优惠券Id")
    @NotNull
    private Long pointsCouponId;

}
