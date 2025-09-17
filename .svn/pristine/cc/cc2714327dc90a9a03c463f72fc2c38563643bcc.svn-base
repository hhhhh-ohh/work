package com.wanmi.sbc.elastic.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.customer.EsEmployeePageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsEmployeePageResponse extends BasicResponse {

    @Schema(description = "业务员列表")
    private MicroServicePage<EsEmployeePageVO> employeePageVOPage;
}
