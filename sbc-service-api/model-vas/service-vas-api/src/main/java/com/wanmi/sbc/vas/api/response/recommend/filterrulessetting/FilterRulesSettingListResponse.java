package com.wanmi.sbc.vas.api.response.recommend.filterrulessetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.FilterRulesSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>列表结果</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRulesSettingListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 列表结果
     */
    @Schema(description = "列表结果")
    private List<FilterRulesSettingVO> filterRulesSettingVOList;
}
