package com.wanmi.sbc.setting.api.response.systemresource;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>平台素材资源分页结果</p>
 * @author lq
 * @date 2019-11-05 16:14:27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourcePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 平台素材资源分页结果
     */
    @Schema(description = "平台素材资源分页结果")
    private MicroServicePage<SystemResourceVO> systemResourceVOPage;
}
