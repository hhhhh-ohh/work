package com.wanmi.sbc.customer.api.request.company;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sunkun on 2017/11/6.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInfoForDistributionRecordRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    @NotNull
    private String companyCode;
}
