package com.wanmi.sbc.elastic.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 删除优惠券活动
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsCouponActivityDeleteByIdRequest extends BaseRequest {


    /**
     * 活动ID
     */
    @Schema(description = "活动ID")
    @NotBlank
    private String activityId;
}
