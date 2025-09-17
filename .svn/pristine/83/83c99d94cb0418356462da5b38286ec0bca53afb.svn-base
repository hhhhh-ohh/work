package com.wanmi.sbc.crm.api.request.rfmgroupstatistics;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName RfmgroupstatisticsListRequest
 * @Description 列表查询rfm系统人群request
 * @Author lvzhenwei
 * @Date 2019/10/15 16:33
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfmGroupStatisticsListRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 统计日期
     */
    @Schema(description = "统计日期")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate statDate;



}
