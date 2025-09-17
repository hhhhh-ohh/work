package com.wanmi.sbc.crm.api.response.rfmsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.RfmSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>rfm参数配置修改结果</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfmSettingModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的rfm参数配置信息
     */
    @Schema(description = "已修改的rfm参数配置信息")
    private RfmSettingVO rfmSettingVO;
}
