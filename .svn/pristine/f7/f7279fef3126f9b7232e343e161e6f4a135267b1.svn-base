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
 * <p>根据id查询任意（包含已删除）rfm参数配置信息response</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfmSettingByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * rfm参数配置信息
     */
    @Schema(description = "rfm参数配置信息")
    private RfmSettingVO rfmSettingVO;
}
