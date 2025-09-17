package com.wanmi.sbc.customer.api.request.company;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyInfoQueryByIdsRequest extends BaseRequest {

    @Schema(description = "公司信息IDs")
    @NotEmpty
    private List<Long> companyInfoIds;

    @Schema(description = "是否删除")
    @NotNull
    private DeleteFlag deleteFlag;
}
