package com.wanmi.sbc.crm.api.response.customgroup;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomGroupVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomGroupListForParamResponse extends BasicResponse {

    /**
     * 系统人群列表查询结果
     */
    @Schema(description = "系统人群列表查询结果")
    private List<CustomGroupVo> customGroupVos;
}
