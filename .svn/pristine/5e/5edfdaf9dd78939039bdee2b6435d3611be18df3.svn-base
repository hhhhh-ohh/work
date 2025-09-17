package com.wanmi.sbc.customer.api.request.detail;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>根据会员id查询单个会员明细request</p>
 * Created by daiyitian on 2018-08-13-下午3:47.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDetailWithNotDeleteByCustomerIdRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    @NotBlank
    private String customerId;

}
