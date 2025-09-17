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
 * <p>根据id查询任意（包含已删除）标签维度信息response</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 标签维度信息
     */
    @Schema(description = "标签维度信息")
    private TagDimensionVO tagDimensionVO;
}
