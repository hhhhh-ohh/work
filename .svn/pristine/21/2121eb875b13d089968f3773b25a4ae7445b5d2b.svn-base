package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className ElectronicCardNumByOrderNoRequest
 * @description
 * @date 2023/5/26 16:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectronicCardNumByOrderNoRequest extends BaseRequest {
    private static final long serialVersionUID = 8102106987317600328L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String orderNo;

    /**
     * 卡券id
     */
    @Schema(description = "卡券id")
    @NotNull
    private Long couponId;
}
