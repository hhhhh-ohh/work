package com.wanmi.sbc.customer.api.request.ledgeraccount;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className LedgerReceiverContractApplyRequest
 * @description
 * @date 2022/9/8 5:19 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverContractApplyRequest implements Serializable {
    private static final long serialVersionUID = -161338051788121429L;

    @Schema(description = "分账绑定关系id")
    @NotBlank
    private String relId;

    @Schema(description = "商家id")
    private Long supplierId;
}
