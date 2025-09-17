package com.wanmi.sbc.customer.api.request.payingmemberlevel;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className PayingMemberLevelStatusRequest
 * @description
 * @date 2022/5/17 10:15 AM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberLevelStatusRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = -3922594334552215331L;

    /**
     * 付费会员等级状态 0.开启 1.暂停
     */
    @Schema(description = "付费会员等级状态 0.开启 1.暂停")
    @NotNull
    private Integer status;
}
