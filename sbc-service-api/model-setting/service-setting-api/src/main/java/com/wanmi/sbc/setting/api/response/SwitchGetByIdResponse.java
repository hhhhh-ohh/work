package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 系统开关
 * Created by yuanlinling on 2017/4/26.
 */
@Schema
@Data
public class SwitchGetByIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 开关名称
     */
    @Schema(description = "开关名称")
    private String switchName;

    /**
     *开关状态 0：关闭 1：开启
     */
    @Schema(description = "开关状态-0：关闭 1：开启", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer status;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    @NotNull
    private DeleteFlag delFlag;

}
