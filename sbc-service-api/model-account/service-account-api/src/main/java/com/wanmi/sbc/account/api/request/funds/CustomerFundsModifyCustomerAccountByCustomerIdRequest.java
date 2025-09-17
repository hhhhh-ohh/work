package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员资金-更新会员账号对象
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsModifyCustomerAccountByCustomerIdRequest extends BaseRequest {

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 会员账号
     */
    @Schema(description = "会员账号")
    private String customerAccount;
}
