package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据客户id更新退单中所有业务员请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderModifyEmployeeIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 业务员
     */
    @Schema(description = "业务员")
    @NotBlank
    private String employeeId;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @NotBlank
    private String customerId;
}
