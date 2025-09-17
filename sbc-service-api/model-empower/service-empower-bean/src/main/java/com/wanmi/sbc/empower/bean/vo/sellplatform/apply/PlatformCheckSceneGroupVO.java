package com.wanmi.sbc.empower.bean.vo.sellplatform.apply;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className ChannelsCheckSceneGroupVO
 * @description 场景接入相关
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformCheckSceneGroupVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 场景枚举，1:视频号、公众号场景
     */
    @Schema(description = "场景枚举，1:视频号、公众号场景")
    private Integer group_id;
    /**
     * 场景名称
     */
    @Schema(description = "场景名称")
    private String name;
    /**
     * 审核状态，0:审核中，1:审核完成，2:审核失败，3未审核
     */
    @Schema(description = "审核状态，0:审核中，1:审核完成，2:审核失败，3未审核")
    private Integer status;
    /**
     * 审核理由
     */
    @Schema(description = "审核理由")
    private String reason;
    /**
     * 场景相关审核结果
     */
    @Schema(description = "场景相关审核结果")
    private List<PlatformCheckSceneGroupExtVO> scene_group_ext_list;
}