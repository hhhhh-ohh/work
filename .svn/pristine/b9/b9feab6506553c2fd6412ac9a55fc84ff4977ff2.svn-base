package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 线下账户删除请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineAccountDeleteByIdRequest extends AccountBaseRequest {

    private static final long serialVersionUID = 9064255619917360275L;
    /**
     * 线下账户Id
     */
    @Schema(description = "线下账户Id")
    @NotNull
    private Long offlineAccountId;
}
