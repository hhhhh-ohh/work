package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>依据会员id验证分销员状态</p>
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerEnableByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 会员标识UUID
     */
    @NotNull
    private String customerId ;
}