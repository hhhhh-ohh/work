package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @author xuyunpeng
 * @className ElectronicImportRequest
 * @description 卡密确认导入
 * @date 2022/2/3 1:02 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicImportRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "卡券id")
    @NotNull
    private Long couponId;
}
