package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 条件查询线下账户列表请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineAccountListByConditionRequest extends AccountBaseRequest {

    private static final long serialVersionUID = -1640517226243440173L;
    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    @NotNull
    private Long companyInfoId;

    /**
     * 默认标识
     */
    @Schema(description = "默认标识")
    @NotNull
    private DefaultFlag defaultFlag;
}
