package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponActivityGetDetailByIdAndStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = -2171672448461394627L;

    /**
     * 活动id
     */
    @Schema(description = "优惠券活动id")
    @NotBlank
    private String id;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 任务版本号
     */
    @Schema(description = "任务版本号")
    private String scanVersion;
}
