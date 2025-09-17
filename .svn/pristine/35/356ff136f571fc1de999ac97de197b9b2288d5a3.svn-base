package com.wanmi.sbc.elastic.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsEmployeeBatchEnableByIdsRequest extends BaseRequest {

    @Schema(description = "员工编号")
    List<String> employeeIds;
}
