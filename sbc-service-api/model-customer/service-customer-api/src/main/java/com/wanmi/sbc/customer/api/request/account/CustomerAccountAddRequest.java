package com.wanmi.sbc.customer.api.request.account;

import com.wanmi.sbc.customer.bean.dto.CustomerAccountAddOrModifyDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 会员银行账户添加Request
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerAccountAddRequest extends CustomerAccountAddOrModifyDTO{

    private static final long serialVersionUID = 1L;

    @Schema(description = "操作人员id")
    private String employeeId;
}
