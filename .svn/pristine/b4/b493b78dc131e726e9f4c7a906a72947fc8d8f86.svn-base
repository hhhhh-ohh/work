package com.wanmi.sbc.setting.api.request.popupadministration;


import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>弹窗&页面管理</p>
 * @author weiwenhao
 * @date 2020-04-23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageManagementRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 应用页面
     */
    @Schema(description = "应用页面")
    @NotNull
    private String applicationPageName;

}
