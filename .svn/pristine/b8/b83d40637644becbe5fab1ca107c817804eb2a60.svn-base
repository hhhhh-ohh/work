package com.wanmi.sbc.account.api.request.company;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 删除商家收款账户请求
 * Created by daiyitian on 2018/10/15.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountDeleteByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -7307827142329001082L;
    /**
     * 线下账户id
     */
    @Schema(description = "线下账户id")
    @NotNull
    private Long offlineAccountId;
}
