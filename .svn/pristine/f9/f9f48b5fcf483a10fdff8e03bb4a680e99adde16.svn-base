package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerByInviteCodeRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 邀请码
     */
    @NotNull
    private String inviteCode ;
}