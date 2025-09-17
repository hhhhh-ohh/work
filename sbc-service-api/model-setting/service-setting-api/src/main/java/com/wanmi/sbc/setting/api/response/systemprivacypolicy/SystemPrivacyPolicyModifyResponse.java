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
 * <p>隐私政策修改结果</p>
 * @author yangzhen
 * @date 2020-09-23 14:52:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemPrivacyPolicyModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的隐私政策信息
     */
    @Schema(description = "已修改的隐私政策信息")
    private SystemPrivacyPolicyVO systemPrivacyPolicyVO;
}
