package com.wanmi.sbc.elastic.api.request.ledger;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoUpdateRequest
 * @description
 * @date 2022/9/13 2:54 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsLedgerBindInfoUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 6642308471799997588L;

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
