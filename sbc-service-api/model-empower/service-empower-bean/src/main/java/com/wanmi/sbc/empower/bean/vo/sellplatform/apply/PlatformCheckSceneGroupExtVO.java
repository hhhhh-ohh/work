package com.wanmi.sbc.empower.bean.vo.sellplatform.apply;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckSceneGroupExtVO   场景相关审核结果
 * @description 场景相关审核结果
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformCheckSceneGroupExtVO implements Serializable {
    /**
     * 审核事项id，1:客服售后，2:电商平台
     */
    @Schema(description = "审核事项id，1:客服售后，2:电商平台")
    private Integer ext_id;
    /**
     * 场景相关审核结果，0:审核中，1:审核成功，2:审核失败，3未审核
     */
    @Schema(description = "场景相关审核结果，0:审核中，1:审核成功，2:审核失败，3未审核")
    private Integer status;
}