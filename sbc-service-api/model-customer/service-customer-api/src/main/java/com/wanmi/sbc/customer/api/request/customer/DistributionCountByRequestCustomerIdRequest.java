package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 邀新人数统计请求
 * @Autho qiaokang
 * @Date：2019-03-07 19:08:05
 */
@Schema
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DistributionCountByRequestCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @NotNull
    private String customerId;

}
