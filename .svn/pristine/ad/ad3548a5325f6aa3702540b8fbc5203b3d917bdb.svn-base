package com.wanmi.sbc.setting.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>移动端悬浮导航栏实体类</p>
 *
 * @author dyt
 * @date 2020-04-29 14:28:21
 */
@Data
public class BottomNavMobileItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 导航标识，例：tabIndex0
     */
    @Schema(description = "导航标识")
    @NotBlank
    private String key;

    /**
     * 导航标识，例：tabIndex0
     */
    @Schema(description = "导航名称")
    @NotBlank
    @Length(max = 4)
    private String title;

    /**
     * 导航图标地址
     */
    @Schema(description = "导航图标地址")
    @NotBlank
    private String iconPath;

    /**
     * 选中后的导航图标地址
     */
    @Schema(description = "选中后的导航图标地址")
    @NotBlank
    private String selectedIconPath;

    /**
     * 落地页地址
     */
    @Schema(description = "落地页地址")
    @NotBlank
    private String link;
}