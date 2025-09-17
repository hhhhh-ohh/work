package com.wanmi.sbc.elastic.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsEmployeeListByIdsRequest extends BaseRequest {

    @Schema(description = "员工编号")
    @NotNull
    private List<String> employeeIds;

}
