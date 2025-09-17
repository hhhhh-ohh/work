package com.wanmi.sbc.setting.api.request.systemconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @author xuyunpeng
 * @className PayingMemberModifyRequest
 * @description
 * @date 2022/5/13 9:53 AM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberModifyRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 4930294483422980280L;

    /**
     * 开启状态 0、未启用 1、启用
     */
    @Schema(description = "开启状态 0、未启用 1、启用")
    @NotNull
    @Range(min=0, max=1)
    private Integer enable;

    /**
     * 未开通入口文案
     */
    @Schema(description = "未开通入口文案")
    @NotBlank
    @Length(max = 20)
    private String notHasContent;

    /**
     * 未开通字体色
     */
    @Schema(description = "未开通字体色")
    @NotBlank
    private String notHasColor;

    /**
     * 未开通背景图
     */
    @Schema(description = "未开通背景图")
    @NotBlank
    private String notHasBackground;

    /**
     * 已开通入口文案
     */
    @Schema(description = "已开通入口文案")
    @NotBlank
    @Length(max = 20)
    private String hasContext;

    /**
     * 已开通字体色
     */
    @Schema(description = "已开通字体色")
    @NotBlank
    private String hasColor;

    /**
     * 已开通背景图
     */
    @Schema(description = "已开通背景图")
    @NotBlank
    private String hasBackground;

    /**
     * 会员标签价
     */
    @Schema(description = "会员标签价")
    @NotBlank
    private String label;

    /**
     * 是否开启过
     */
    private Boolean openFlag;

}
