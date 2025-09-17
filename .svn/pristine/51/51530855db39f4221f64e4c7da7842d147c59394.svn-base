package com.wanmi.sbc.crm.api.request.rfmgroupstatistics;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName RfmgroupstatisticsRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/10/15 10:57
 **/
@Schema
@Data
public class RfmGroupStatisticsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 统计日期
     */
    @Schema(description = "统计日期")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate statDate;

    /**
     * 统计开始时间
     */
    @Schema(description = "统计开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 统计结束时间
     */
    @Schema(description = "统计结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

}
