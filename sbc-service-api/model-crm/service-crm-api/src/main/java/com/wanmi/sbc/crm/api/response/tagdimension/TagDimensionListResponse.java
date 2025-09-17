package com.wanmi.sbc.crm.api.response.tagdimension;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.TagDimensionVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>标签维度列表结果</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 标签维度列表结果
     */
    @Schema(description = "标签维度列表结果")
    private List<TagDimensionVO> tagDimensionVOList;
}
