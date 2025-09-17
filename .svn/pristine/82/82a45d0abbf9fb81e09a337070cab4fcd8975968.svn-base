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
 * Created by daiyitian on 2018/10/15.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountCountByCompanyInfoIdRequest extends BaseRequest {

    private static final long serialVersionUID = -4234492278195573361L;
    /**
     * 公司信息id
     */
    @Schema(description = "公司信息id")
    @NotNull
    private Long companyInfoId;
}
