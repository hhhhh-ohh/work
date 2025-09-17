package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className LedgerBindInfoToEsUpdateRequest
 * @description
 * @date 2022/9/13 3:26 PM
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerBindInfoToEsUpdateRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 6678024492164532100L;

    /**
     * 接收方id
     */
    @Schema(description = "接收方id")
    @NotNull
    private String receiverId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String account;
}
