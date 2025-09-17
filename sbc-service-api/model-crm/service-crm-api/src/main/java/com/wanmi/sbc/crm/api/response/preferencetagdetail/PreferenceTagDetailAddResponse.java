package com.wanmi.sbc.crm.api.response.preferencetagdetail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.PreferenceTagDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>偏好标签明细新增结果</p>
 * @author dyt
 * @date 2020-03-11 14:58:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceTagDetailAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的偏好标签明细信息
     */
    @Schema(description = "已新增的偏好标签明细信息")
    private PreferenceTagDetailVO preferenceTagDetailVO;
}
