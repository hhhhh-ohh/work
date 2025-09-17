package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className CouponActivityCloseRequest
 * @description 关闭活动请求类
 * @date 2021/6/23 10:16 上午
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponActivityCloseRequest extends BaseRequest {
    private static final long serialVersionUID = -2643016418736709155L;

    @Schema(description = "优惠券活动id")
    @NotBlank
    private String id;

    @Schema(description = "操作人")
    @NotBlank
    private String operatorId;
}
