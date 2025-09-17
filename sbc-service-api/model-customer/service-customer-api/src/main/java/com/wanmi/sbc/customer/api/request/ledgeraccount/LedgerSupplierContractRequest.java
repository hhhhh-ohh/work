package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerSupplierContractRequest
 * @description
 * @date 2022/7/21 6:18 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerSupplierContractRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -1968930110429695515L;

    @Schema(description = "商户id")
    @NotBlank
    private String companyInfoId;
}
