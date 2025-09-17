package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 线下账户启动请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineEnableByIdRequest extends AccountBaseRequest {

    private static final long serialVersionUID = 519296597155015492L;
    /**
     * 线下账户Id
     */
    @Schema(description = "线下账户Id")
    @NotNull
    private Long offlineAccountId;
}
