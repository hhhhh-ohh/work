package com.wanmi.sbc.customer.api.response.payingmembercustomerrel;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenli
 * @className PayingMemberCustomerResponse
 * @description 个人中心-付费会员入口-查询结果
 * @date 2022/5/20 10:29 AM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberCustomerResponse implements Serializable {
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

    /**
     * 会员是否开通(未开通过包含会员过期和会员设置开关关闭，已开通：会员开通且未过期)
     */
    @Schema(description = "会员是否开通(未开通过包含会员过期和会员设置开关关闭，已开通：会员开通且未过期)")
    private Boolean hasOpen = Boolean.FALSE;
}
