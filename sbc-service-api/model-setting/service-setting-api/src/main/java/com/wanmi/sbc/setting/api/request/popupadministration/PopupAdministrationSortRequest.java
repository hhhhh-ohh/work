package com.wanmi.sbc.setting.api.request.popupadministration;


import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>弹窗管理新增参数</p>
 * @author weiwenhao
 * @date 2020-04-22
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopupAdministrationSortRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private Long popupId;

    /**
     * 应用页面
     */
    @Schema(description = "应用页面")
    @NotNull
    private String applicationPageName;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    @NotNull
    private Long sortNumber;

}
