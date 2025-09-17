package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * @author houshuai
 * @date 2022/3/29 14:47
 * @description <p> 注销原因 </p>
 */
@Schema
@Data
public class CancellationReasonVO implements Serializable {

    private static final long serialVersionUID = -20929569370786627L;

    /** id 主键 */
    @Schema(description = "id 主键")
    @NotBlank
    private String id;

    /** 注销原因 */
    @Schema(description = "注销原因")
    @NotBlank
    private String reason;

    /** 排序 */
    @Schema(description = "排序")
    @NotNull
    private Long sort;

    /** 删除标识 */
    @Schema(description = "删除标识")
    private DeleteFlag delFlag;

}
