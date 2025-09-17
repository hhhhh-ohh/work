package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 线下账户禁用请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineDisableByIdRequest extends AccountBaseRequest {

    private static final long serialVersionUID = -9133785800072015024L;
    /**
     * 线下账户id
     */
    @Schema(description = "线下账户id")
    @NotNull
    private Long offlineAccountId;
}
