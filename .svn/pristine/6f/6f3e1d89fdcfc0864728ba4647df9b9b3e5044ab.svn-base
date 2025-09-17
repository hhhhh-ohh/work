package com.wanmi.sbc.customer.api.request.distribution.performance;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.dto.DistributionMonthPerformanceDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>月分销业绩批量录入请求参数bean</p>
 * Created by of628-wenzhi on 2019-04-18-15:58.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class DistributionMonthPerformanceListEnteringRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "月分销业绩数据")
    @NotNull
    @NotEmpty
    @Valid
    private List<DistributionMonthPerformanceDTO> dataList;

}
