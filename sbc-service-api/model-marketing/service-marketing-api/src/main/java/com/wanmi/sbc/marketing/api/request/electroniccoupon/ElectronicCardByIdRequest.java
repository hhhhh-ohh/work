package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className ElectronicCardByIdRequest
 * @description
 * @date 2022/5/31 5:46 PM
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectronicCardByIdRequest extends BaseRequest {
    private static final long serialVersionUID = -7066903122237290332L;

    /**
     * 卡密id
     */
    @Schema(description = "卡密id")
    @NotBlank
    private String id;
}
