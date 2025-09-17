package com.wanmi.sbc.customer.api.request.company;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author zhaiqiankun
 * @className CompanyInfoModifyBusinessLicenceRequest
 * @description 修改公司营业执照
 * @date 2022/4/11 16:02
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoModifyBusinessLicenceRequest implements Serializable {
    private static final long serialVersionUID = 1142073511366309957L;
    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    @Max(9223372036854775807L)
    private Long companyInfoId;

    /**
     * 营业执照副本电子版
     */
    @Schema(description = "营业执照副本电子版")
    @Length(max=1024)
    @NotBlank
    private String businessLicence;

}
