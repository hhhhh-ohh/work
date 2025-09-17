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

import java.math.BigDecimal;

/**
 * 退单退款请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderRefundRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    @NotBlank
    private String rid;

    @Schema(description = "价钱")
    @NotNull
    private BigDecimal price;

    /**
     * 操作人信息
     */
    @Schema(description = "操作人信息")
    @NotNull
    private Operator operator;
}
