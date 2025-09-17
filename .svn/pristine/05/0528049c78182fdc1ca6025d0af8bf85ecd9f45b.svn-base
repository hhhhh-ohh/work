package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 线下账户统计请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineCountRequest extends AccountBaseRequest {

    private static final long serialVersionUID = 2596598976331332355L;
    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    @NotNull
    private Long companyInfoId;
}
