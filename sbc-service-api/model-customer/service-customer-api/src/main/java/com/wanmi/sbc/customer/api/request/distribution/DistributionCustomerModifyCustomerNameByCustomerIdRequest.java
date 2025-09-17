package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员资金-更新会员名称对象
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
public class DistributionCustomerModifyCustomerNameByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;


    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;
}
