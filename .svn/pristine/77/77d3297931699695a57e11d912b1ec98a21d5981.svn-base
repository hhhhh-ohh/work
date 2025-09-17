package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenli
 * @date 2021/3/3 11:40
 * @description <p> 在线还款查询授信账户详情 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditAccountForRepayRequest extends BaseRequest {
    private static final long serialVersionUID = -4863459557631770739L;
    /**
     * 客户id
     */
    @NotBlank
    @Schema(description = "客户id")
    private String customerId;
}
