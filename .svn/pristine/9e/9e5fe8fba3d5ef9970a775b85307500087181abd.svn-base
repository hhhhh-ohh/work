package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.io.Serializable;

/**
 * @author houshuai
 * @date 2021/3/3 10:20
 * @description <p> 查看订单详情 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditRepayDetailRequest extends BaseQueryRequest implements Serializable {
    private static final long serialVersionUID = 8416658528912101037L;

    /**
     * 还款单号
     */
    @Schema(description = "还款单号")
    @NotBlank
    private String repayOrderCode;
}
