package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2021/3/1 11:40
 * @description <p> 授信账户详情查询 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditAccountDetailRequest extends BaseRequest {
    private static final long serialVersionUID = 8682530563521896769L;
    /**
     * 会员id
     */
    @NotBlank
    @Schema(description = "会员id")
    private String customerId;
}
