package com.wanmi.sbc.customer.api.request.distribution.performance;

import static com.wanmi.sbc.common.util.ValidateUtil.DATE_EX_MESSAGE;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.Validate;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>汇总指定分销员指定日期范围内业绩数据请求参数</p>
 * Created by of628-wenzhi on 2019-04-23-18:38.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributionPerformanceSummaryQueryRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    @NotNull
    @NotEmpty
    private List<String> distributionId;

    /**
     * 开始日期，开始日期必须小于结束日期
     */
    @Schema(description = "开始日期，开始日期必须小于结束日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate startDate;

    /**
     * 结束日期，开始日期必须小于结束日期
     */
    @Schema(description = "结束日期，开始日期必须小于结束日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate endDate;

    @Override
    public void checkParam() {
        Validate.isTrue(endDate.compareTo(startDate) >= 0, DATE_EX_MESSAGE,
                startDate + "-" + endDate);
    }

}
