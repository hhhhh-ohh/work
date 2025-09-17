package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * 邀请记录分页查询参数
 * Created by baijz on 2019/3/08.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInviteNewByCustomerIdRequest extends BaseRequest {
    /**
     * 受邀人ID
     */
    @NotNull
    @Schema(description = "受邀人ID")
    private String  invitedNewCustomerId;

}
