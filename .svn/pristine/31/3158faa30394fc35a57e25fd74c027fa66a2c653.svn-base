package com.wanmi.sbc.customer.api.request.distribution.performance;

import static com.wanmi.sbc.common.util.ValidateUtil.DATE_EX_MESSAGE;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.dto.DistributionPerformanceDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

/**
 * <p>日分销业绩录入请求参数bean</p>
 * Created by of628-wenzhi on 2019-04-18-15:58.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class DistributionPerformanceEnteringRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "日分销业绩数据")
    @NotNull
    @Valid
    private DistributionPerformanceDTO data;

    @Override
    public void checkParam() {
        Validate.isTrue(data.getTargetDate().compareTo(LocalDate.now()) <= 0, DATE_EX_MESSAGE,
                data.getTargetDate());
    }
}
