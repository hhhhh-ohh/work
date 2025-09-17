package com.wanmi.sbc.marketing.api.request.pointscoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>单个查询积分兑换券表请求参数</p>
 *
 * @author yang
 * @date 2019-06-11 10:07:09
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsCouponByIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 积分兑换券id
     */
    @Schema(description = "积分兑换券id")
    @NotNull
    private Long pointsCouponId;
}