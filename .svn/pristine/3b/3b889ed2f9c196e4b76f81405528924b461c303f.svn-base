package com.wanmi.sbc.setting.api.response.systemconfig;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PayingMemberSettingResponse
 * @description
 * @date 2022/5/13 10:29 AM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberSettingResponse implements Serializable {
    private static final long serialVersionUID = -1081432625381220098L;

    /**
     * 开启状态 0、未启用 1、弃用
     */
    @Schema(description = "开启状态 0、未启用 1、弃用")
    private Integer enable;

    /**
     * 未开通入口文案
     */
    @Schema(description = "未开通入口文案")
    private String notHasContent;

    /**
     * 未开通字体色
     */
    @Schema(description = "未开通字体色")
    private String notHasColor;

    /**
     * 未开通背景图
     */
    @Schema(description = "未开通背景图")
    private String notHasBackground;

    /**
     * 已开通入口文案
     */
    @Schema(description = "已开通入口文案")
    private String hasContext;

    /**
     * 已开通字体色
     */
    @Schema(description = "已开通字体色")
    private String hasColor;

    /**
     * 已开通背景图
     */
    @Schema(description = "已开通背景图")
    private String hasBackground;

    /**
     * 会员标签价
     */
    @Schema(description = "会员标签价")
    private String label;

    /**
     * 是否开启过
     */
    @Schema(description = "是否开启过")
    private Boolean openFlag;
}
