package com.wanmi.sbc.elastic.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据活动删除活动优惠券范围
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsActivityCouponDeleteByActivityIdRequest extends BaseRequest {


    /**
     * 优惠券活动id
     */
    @Schema(description = "优惠券活动id")
    @NotBlank
    private String activityId;
}
