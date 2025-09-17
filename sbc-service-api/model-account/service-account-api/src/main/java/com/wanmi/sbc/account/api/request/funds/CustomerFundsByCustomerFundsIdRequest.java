package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员资金-根据会员资金ID查询对象
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsByCustomerFundsIdRequest extends BaseRequest {

    /**
     * 会员资金Id
     */
    @Schema(description = "会员资金Id")
    private String customerFundsId;
}
