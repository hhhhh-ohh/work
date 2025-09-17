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
 * <p>根据id查询任意（包含已删除）偏好标签明细信息response</p>
 * @author dyt
 * @date 2020-03-11 14:58:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceTagDetailByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 偏好标签明细信息
     */
    @Schema(description = "偏好标签明细信息")
    private PreferenceTagDetailVO preferenceTagDetailVO;
}
