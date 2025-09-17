package com.wanmi.sbc.customer.api.request.account;

import com.wanmi.sbc.customer.bean.dto.CustomerAccountAddOrModifyDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 会员银行账户-根据用户ID修改Request
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerAccountModifyByCustomerIdRequest extends CustomerAccountAddOrModifyDTO{

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员标识UUID")
    private String customerId;
}
