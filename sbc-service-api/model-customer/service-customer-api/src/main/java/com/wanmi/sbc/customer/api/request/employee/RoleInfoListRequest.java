package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 会员角色-根据公司ID查询Request
 */
@Schema
@Data
public class RoleInfoListRequest extends CustomerBaseRequest {

    /**
     * 公司编号
     */
    @Schema(description = "公司编号")
    private Long companyInfoId;
}
