package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/5/12.
 */
@Schema
@Data
@Builder
public class EmployeeAccountResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 账号名
     */
    @Schema(description = "账号名")
    private String accountName;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String employeeName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 是否是主账号
     */
    @Schema(description = "是否是主账号", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isMasterAccount;
}
