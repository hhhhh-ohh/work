package com.wanmi.sbc.crm.api.response.tagparam;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.crm.bean.vo.TagParamVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>标签参数分页结果</p>
 * @author dyt
 * @date 2020-03-12 15:59:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagParamPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 标签参数分页结果
     */
    @Schema(description = "标签参数分页结果")
    private MicroServicePage<TagParamVO> tagParamVOPage;
}
