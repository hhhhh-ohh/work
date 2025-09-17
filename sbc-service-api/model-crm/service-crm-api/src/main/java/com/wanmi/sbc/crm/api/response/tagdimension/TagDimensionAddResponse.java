package com.wanmi.sbc.crm.api.response.tagdimension;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.TagDimensionVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>标签维度新增结果</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的标签维度信息
     */
    @Schema(description = "已新增的标签维度信息")
    private TagDimensionVO tagDimensionVO;
}
