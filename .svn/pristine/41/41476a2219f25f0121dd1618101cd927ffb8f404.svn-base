package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 根据商家id获取简单主账号的入参结构
 * @author  daiyitian
 * @date 2021/4/28 18:08
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSimpleByCompanyIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商家id")
    @NotNull
    private Long companyInfoId;
}
