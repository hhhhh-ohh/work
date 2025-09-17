package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 是否分销员
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerCheckRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 会员标识UUID
     */
    @NotBlank
    private String customerId ;

    /**
     * 是否开启社交分销
     */
    private DefaultFlag openFlag;
}