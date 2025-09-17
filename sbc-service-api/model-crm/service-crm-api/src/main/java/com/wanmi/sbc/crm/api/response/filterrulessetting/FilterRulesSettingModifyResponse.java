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
 * <p>修改结果</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRulesSettingModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的信息
     */
    @Schema(description = "已修改的信息")
    private FilterRulesSettingVO filterRulesSettingVO;
}
