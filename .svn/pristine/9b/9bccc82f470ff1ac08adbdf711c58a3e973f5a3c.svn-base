package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>依据分销员id验证分销员状态</p>
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerEnableByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 分销员标识UUID
     */
    @NotNull
    private String distributionId;
}