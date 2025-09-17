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
public class HoverNavMobileItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String imgSrc;

    /**
     * 导航名称
     */
    @Schema(description = "导航名称")
    @Length(max=5)
    @NotBlank
    private String title;

    /**
     * 落地页的json字符串
     */
    @Schema(description = "落地页的json字符串")
    private String linkInfoPage;
}