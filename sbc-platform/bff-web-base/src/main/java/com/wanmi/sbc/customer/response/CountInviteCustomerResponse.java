package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Schema
@Data
public class CountInviteCustomerResponse extends BasicResponse {

    @Schema(description = "邀新人数")
    private Long inviteNum;

    @Schema(description = "有效邀新人数")
    private Long validInviteNum;

    @Schema(description = "我的客户")
    private Integer myCustomerNum;
}
