package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutotagPreferenceVO extends BasicResponse {
    // 标签id
    @Schema(description = "id")
    private String tagId;

    // 维度类型
    @Schema(description = "维度类型")
    private String dimensionType;

    // 维度id
    @Schema(description = "维度id")
    private String dimensionId;

    // 数量统计
    @Schema(description = "数量统计")
    private int num;

    // 时间
    @Schema(description = "时间")
    private Date pDate;

    // 标签名称
    @Schema(description = "标签名称")
    private String detailName;
}
