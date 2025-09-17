package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRelBindRequest
 * @description
 * @date 2022/7/15 2:26 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelBindRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = -972028055045075834L;

    /**
     * 分账绑定关系id
     */
    @NotBlank
    @Schema(description = "分账绑定关系id")
    private String receiverRelId;

    @Schema(description = "是否同意协议")
    private Boolean acceptFlag;
}
