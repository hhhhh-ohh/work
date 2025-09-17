package com.wanmi.sbc.customer.api.request.address;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 会员收货地址-根据用户ID查询Request
 */
@Schema
@Data
public class CustomerDeliveryAddressRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 收货地址ID
     */
    @Schema(description = "收货地址ID")
    private String deliveryAddressId;

    /**
     * 客户ID
     */
    @Schema(description = "会员标识UUID")
    private String customerId;

    /**
     * 是否是默认地址 0：否 1：是
     */
    @Schema(description = "是否是默认地址(0:否,1:是)", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isDefaltAddress;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志(0:未删除,1:已删除)", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;
}
