package com.wanmi.sbc.setting.api.request.openapisetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.EnableStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 开放平台权限禁用/启用 request
 * @author  lvzhenwei
 * @date 2021/4/14 4:36 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingChangeDisableStateRequest extends BaseRequest {

    /** 主键 */
    @Schema(description = "主键")
    @NotNull
    private Long id;


    /** 禁用状态:0:禁用；1:启用 */
    @NotNull
    @Schema(description = "禁用状态:0:禁用；1:启用")
    private EnableStatus disableState;

    /** 禁用原因 */
    @Schema(description = "禁用原因")
    private String disableReason;
}
