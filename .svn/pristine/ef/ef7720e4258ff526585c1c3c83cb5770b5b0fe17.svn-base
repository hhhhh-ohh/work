package com.wanmi.sbc.customer.api.request.company;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据id查询单个公司信息request</p>
 * Created by daiyitian on 2018-08-13-下午3:47.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyInfoByIdRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 公司信息id
     */
    @Schema(description = "供应商id列表")
    @NotNull
    private Long companyInfoId;

}
