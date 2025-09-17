package com.wanmi.sbc.crm.api.request.lifecyclegroup;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @ClassName LifeCycleGroupStatisticsRequest
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/2/25 17:09
 * @Version 1.0
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LifeCycleGroupStatisticsRequest extends BaseRequest {

    @Schema(description = "起始日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate startDate;

    @Schema(description = "结束日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate endDate;

    @Schema(description = "类型：0-天，1-周")
    private Integer type=0;

    private SortType sortType = SortType.ASC;
}
