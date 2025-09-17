package com.wanmi.sbc.account.api.request.company;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 确认收到打款参数
 * Created by daiyitian on 2018/10/15.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountAffirmRemitRequest extends BaseRequest {

    private static final long serialVersionUID = 8249062990123581218L;
    /**
     * 公司信息id
     */
    @Schema(description = "公司信息id")
    @NotNull
    private Long companyInfoId;

    /**
     * 线下账户编号
     */
    @Schema(description = "线下账户编号")
    @NotNull
    private Long accountId;

}
