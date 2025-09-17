package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 线下账户列表请求
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OfflineAccountListByBankNoRequest extends AccountBaseRequest {

    private static final long serialVersionUID = 5510482478127604179L;
    /**
     * 线下银行账号
     */
    @Schema(description = "线下银行账号")
    @NotNull
    private String bankNo;
}
