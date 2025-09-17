package com.wanmi.sbc.crm.api.response.preferencetagdetail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.PreferenceTagDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>偏好标签明细列表结果</p>
 * @author dyt
 * @date 2020-03-11 14:58:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceTagDetailListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 偏好标签明细列表结果
     */
    @Schema(description = "偏好标签明细列表结果")
    private List<PreferenceTagDetailVO> preferenceTagDetailVOList;
}
