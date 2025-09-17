package com.wanmi.sbc.account.api.request.company;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询商家收款账户请求
 * Created by chenli on 2018/12/13.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountFindByAccountIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1752695081467978959L;
    /**
     * 账户id
     */
    @Schema(description = "账户id")
    @NotNull
    private Long accountId;
}
