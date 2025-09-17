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
 * @author edz
 * @className PointsCouponCloseRequest
 * @description 兑换券关闭请求体
 * @date 2021/6/23 3:39 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsCouponCloseRequest extends BaseRequest {

    private static final long serialVersionUID = 7748474465702523288L;

    @Schema(description = "兑换券id")
    @NotNull
    private Long pointsCouponId;

    @Schema(description = "操作人id")
    @NotBlank
    private String operateId;
}
