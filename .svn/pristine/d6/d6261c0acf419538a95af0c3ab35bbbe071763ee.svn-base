package com.wanmi.sbc.crm.api.response.tagdimension;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.TagDimensionVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionBigJsonResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 偏好类标签列表
     */
    @Schema(description = "偏好类标签指标行为列表")
    private List<TagDimensionVO> preferenceList;

    /**
     * 偏好类标签列表
     */
    @Schema(description = "偏好类标签指标行为条件列表")
    private List<TagDimensionVO> preferenceParamList;

    /**
     * 范围类和综合类标签列表
     */
    @Schema(description = "范围类和综合类标签列表")
    private List<TagDimensionVO> otherList;

    /**
     * 指标值类标签列表
     */
    @Schema(description = "指标值类标签列表")
    private List<TagDimensionVO> quotaList;




}
