package com.wanmi.sbc.customer.api.request.department;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除部门管理请求参数</p>
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDelByIdRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private String departmentId;
}
