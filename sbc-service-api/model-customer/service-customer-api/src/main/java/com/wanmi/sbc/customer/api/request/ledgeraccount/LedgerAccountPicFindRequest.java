package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerAccountPicFindRequest
 * @description
 * @date 2022/10/20 4:13 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountPicFindRequest extends BaseRequest {
    private static final long serialVersionUID = 1579518529411086216L;

    /**
     * 业务id
     */
    @Schema(description = "业务id")
    @NotBlank
    private String businessId;


}
