package com.wanmi.sbc.crm.customergroup.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomGroupRelVo;
import com.wanmi.sbc.crm.bean.vo.CustomGroupVo;

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
public class CustomerGroupNameResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分群名称
     */
    @Schema(description = "分群名称")
    private List<String> groupNames;

    /**
     * 分群信息
     */
    @Schema(description = "分群信息")
    private List<CustomGroupRelVo> customGroupRelVoList;


}
