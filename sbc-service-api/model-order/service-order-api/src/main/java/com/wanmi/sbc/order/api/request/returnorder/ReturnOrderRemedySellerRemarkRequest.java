package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退单修改卖家备注请求结构
 * @Author: chenli
 * @Description:
 * @Date: 2021-12-07 15:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderRemedySellerRemarkRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    @NotBlank
    private String rid;

    /**
     * 退单的卖家备注
     */
    @Schema(description = "卖家备注")
    private String sellerRemark;

    /**
     * 操作人信息
     */
    @Schema(description = "操作人信息")
    @NotNull
    private Operator operator;
}
