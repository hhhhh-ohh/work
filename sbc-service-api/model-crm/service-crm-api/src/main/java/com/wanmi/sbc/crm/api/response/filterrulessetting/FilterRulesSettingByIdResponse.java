package com.wanmi.sbc.crm.api.response.filterrulessetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.FilterRulesSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）信息response</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRulesSettingByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 信息
     */
    @Schema(description = "信息")
    private FilterRulesSettingVO filterRulesSettingVO;
}
