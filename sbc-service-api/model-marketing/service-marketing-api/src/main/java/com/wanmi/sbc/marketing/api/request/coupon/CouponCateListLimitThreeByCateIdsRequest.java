package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCateListLimitThreeByCateIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -7143821193824449165L;

    @Schema(description = "优惠券分类Id列表")
    @NotNull
    @Size(min = 1)
    private List<String> couponCateIds;
}
