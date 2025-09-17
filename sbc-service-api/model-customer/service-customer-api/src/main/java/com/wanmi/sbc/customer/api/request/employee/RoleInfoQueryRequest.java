package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 会员角色-根据角色ID查询Request
 */
@Schema
@Data
public class RoleInfoQueryRequest extends CustomerBaseRequest {

    /**
     * 角色Id
     */
    @Schema(description = "角色Id")
    private Long roleInfoId;
}
