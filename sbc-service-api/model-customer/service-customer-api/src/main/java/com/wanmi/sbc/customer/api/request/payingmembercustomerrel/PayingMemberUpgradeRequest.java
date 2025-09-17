package com.wanmi.sbc.customer.api.request.payingmembercustomerrel;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className PayingMemberUpdateDiscountRequest
 * @description
 * @date 2022/5/23 4:09 PM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberUpgradeRequest implements Serializable {
    private static final long serialVersionUID = -395148542521701758L;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    @NotBlank
    private String customerId;

    /**
     * 总消费
     */
    @Schema(description = "总消费")
    @NotBlank
    private BigDecimal totalAmount;


}
