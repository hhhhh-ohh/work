package com.wanmi.sbc.customer.api.request.ledgeraccount;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className LedgerAccountCheckRequest
 * @description
 * @date 2022/9/9 2:14 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountCheckRequest implements Serializable {
    private static final long serialVersionUID = -7741254288072603798L;

    @Schema(description = "商户id")
    @NotNull
    private Long supplierId;


}
