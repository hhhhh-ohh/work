package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 会员资金-根据会员ID查询对象
 * @author: yangzhen
 * @createDate: 2020/12/16 11:06
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsByCustomerIdListRequest extends BaseRequest {

    /**
     * 会员Id
     */
    @Schema(description = "会员Id")
    private List<String> customerIds;
}
