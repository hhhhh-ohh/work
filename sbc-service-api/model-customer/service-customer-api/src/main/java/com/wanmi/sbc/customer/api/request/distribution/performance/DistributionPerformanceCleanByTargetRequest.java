package com.wanmi.sbc.customer.api.request.distribution.performance;

import static com.wanmi.sbc.common.util.ValidateUtil.DATE_RANGE_EX_MESSAGE;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.Validate;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

/**
 * <p>根据指定年和月清理业绩数据请求参数</p>
 * Created by of628-wenzhi on 2019-04-18-17:32.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class DistributionPerformanceCleanByTargetRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 年
     */
    @Schema(description = "年")
    @NotNull
    private Integer year;

    /**
     * 月
     */
    @Schema(description = "月")
    @NotNull
    @Range(min = 1, max = 12)
    private Integer month;

    @Override
    public void checkParam() {
        LocalDate date = LocalDate.of(year, month, LocalDate.now().getDayOfMonth());
        Validate.isTrue(year <= LocalDate.now().getYear() && LocalDate.now().minusMonths(6).compareTo(date) > 0
                , DATE_RANGE_EX_MESSAGE, year, month);
    }

}
