package com.wanmi.sbc.crm.api.response.tagparam;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.TagParamVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>标签参数列表结果</p>
 * @author dyt
 * @date 2020-03-12 15:59:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagParamListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 标签参数列表结果
     */
    @Schema(description = "标签参数列表结果")
    private List<TagParamVO> tagParamVOList;
}
