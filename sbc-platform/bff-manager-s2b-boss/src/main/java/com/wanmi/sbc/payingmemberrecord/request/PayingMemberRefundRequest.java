package com.wanmi.sbc.payingmemberrecord.request;



import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Schema
@Data
public class PayingMemberRefundRequest {

    /**
     * 记录id
     */
    @Schema(description = "记录id")
    @NotBlank
    private String recordId;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    @NotNull
    private BigDecimal returnAmount;

    /**
     * 退款原因
     */
    @Schema(description = "退款原因")
    @NotBlank
    @Length(max=100)
    private String returnCause;

    /**
     * 退款回收券 0.是，1.否
     */
    @Schema(description = "退款回收券 0.是，1.否")
    @NotNull
    @Min(0)
    @Max(1)
    private Integer returnCoupon;

    /**
     * 退款回收积分 0.是，1.否
     */
    @Schema(description = "退款回收积分 0.是，1.否")
    @NotNull
    @Min(0)
    @Max(1)
    private Integer returnPoint;
}
