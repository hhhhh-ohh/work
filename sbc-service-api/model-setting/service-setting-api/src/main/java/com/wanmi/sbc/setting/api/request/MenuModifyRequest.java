package com.wanmi.sbc.setting.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuModifyRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 菜单标识
     */
    @Schema(description = "菜单标识")
    @NotNull
    private String menuId;

    /**
     * 系统类别(如:s2b平台,s2b商家)
     */
    @Schema(description = "系统类别")
    @NotNull
    private Platform systemTypeCd;

    /**
     * 父菜单id
     */
    @Schema(description = "父菜单id")
    private String parentMenuId;

    /**
     * 菜单级别
     */
    @Schema(description = "菜单级别")
    private Integer menuGrade;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String menuName;

    /**
     * 菜单路径
     */
    @Schema(description = "菜单路径")
    private String menuUrl;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String menuIcon;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer sort;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 删除标识,0:未删除1:已删除
     */
    @Schema(description = "删除标识", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private DeleteFlag delFlag;
}
