package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className ElectronicCardDelALLRequest
 * @description
 * @date 2022/1/27 3:10 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardDelAllRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 卡券id
     */
    @Schema(description = "卡券id")
    @NotNull
    private Long couponId;

    /**
     * 批次id
     */
    @Schema(description = "批次id")
    private String recordId;
}
