package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRelCheckRequest
 * @description
 * @date 2022/9/13 11:15 AM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelCheckRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = -849876365734373884L;

    /**
     * 分账绑定关系id
     */
    @Schema(description = "分账绑定关系id")
    @NotBlank
    private String id;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long supplierId;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    @NotBlank
    @Length(max = 10)
    private String rejectReason;
}
