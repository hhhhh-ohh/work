package com.wanmi.sbc.order.api.request.orderperformance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPerformanceSummaryRequest extends BaseRequest {


    /**
     * 系统唯一码
     */
    @NotBlank(message = "系统唯一码不可为空")
    @Schema(description = "系统唯一码")
    private String agentUniqueCode;

    /**
     * 开始时间（自定义时间范围时使用）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间（自定义时间范围时使用）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "结束时间")
    private LocalDateTime endTime;


}
