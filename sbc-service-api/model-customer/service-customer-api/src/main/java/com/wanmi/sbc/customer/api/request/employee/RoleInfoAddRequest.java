package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * 会员角色-新增Request
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleInfoAddRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @NotBlank
    @Length(min = 1, max = 10)
    private String roleName;

    /**
     * 公司编号
     */
    @Schema(description = "公司编号")
    private Long companyInfoId;
}
