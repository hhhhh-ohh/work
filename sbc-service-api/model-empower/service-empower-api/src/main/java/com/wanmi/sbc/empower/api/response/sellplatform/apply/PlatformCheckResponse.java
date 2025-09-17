package com.wanmi.sbc.empower.api.response.sellplatform.apply;

import com.wanmi.sbc.empower.bean.vo.sellplatform.apply.PlatformCheckAccessInfoVO;
import com.wanmi.sbc.empower.bean.vo.sellplatform.apply.PlatformCheckSceneGroupVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className ThirdPlatformCheckResponse
 * @description TODO
 * @date 2022/4/1 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformCheckResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 审核状态, 2: 已接入, 3: 封禁中
     */
    @Schema(description = "审核状态, 2: 已接入, 3: 封禁中")
    private int status;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private PlatformCheckAccessInfoVO access_info;

    /**
     * 场景接入相关
     */
    @Schema(description = "场景接入相关")
    private List<PlatformCheckSceneGroupVO> scene_group_list;
}