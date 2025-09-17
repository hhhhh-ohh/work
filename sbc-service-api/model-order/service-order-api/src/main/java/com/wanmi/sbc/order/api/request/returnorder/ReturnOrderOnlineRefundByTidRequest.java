package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderOnlineRefundByTidRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;


    /**
     * 退单ID
     */
    @Schema(description = "退单Id")
    @NotBlank
    private String returnOrderCode;

    /**
     * 操作人
     */
    private Operator operator;

    /**
     * 线下退款账号
     */
    private Long offlineAccountId;


}
