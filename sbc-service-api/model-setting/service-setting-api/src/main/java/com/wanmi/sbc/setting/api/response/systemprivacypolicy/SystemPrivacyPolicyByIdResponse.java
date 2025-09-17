package com.wanmi.sbc.setting.api.response.systemprivacypolicy;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.SystemPrivacyPolicyVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）隐私政策信息response</p>
 * @author yangzhen
 * @date 2020-09-23 14:52:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemPrivacyPolicyByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 隐私政策信息
     */
    @Schema(description = "隐私政策信息")
    private SystemPrivacyPolicyVO systemPrivacyPolicyVO;
}
