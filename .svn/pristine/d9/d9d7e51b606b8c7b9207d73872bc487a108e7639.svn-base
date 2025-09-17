package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeListByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "员工编号")
    private List<String> employeeIds;

    @Override
    public void checkParam() {
        if (CollectionUtils.isEmpty(employeeIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        boolean hasEmpty = employeeIds.stream().anyMatch(String::isEmpty);
        if (hasEmpty) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

}
