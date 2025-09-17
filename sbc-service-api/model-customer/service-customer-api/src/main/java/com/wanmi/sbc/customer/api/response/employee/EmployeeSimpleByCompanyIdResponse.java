package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 根据商家id获取简单主账号的出参结构
 * @author  daiyitian
 * @date 2021/4/28 18:08
 **/
@Schema
@Data
public class EmployeeSimpleByCompanyIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "业务员Id")
    private String employeeId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String employeeName;

    /**
     * 账户名
     */
    @Schema(description = "账户名")
    private String accountName;
}
