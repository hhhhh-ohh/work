package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 开票项返回
 */
@Schema
@Data
public class InvoiceProjectListVO extends BasicResponse {

    /**
     * 主键
     */
    @Schema(description = "开票项id")
    private String projectId;

    /**
     * 开票项目名称
     */
    @Schema(description = "开票项目名称")
    private String projectName;

    /**
     * 开票项目修改时间
     */
    @Schema(description = "开票项目修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime projectUpdateTime;
}
