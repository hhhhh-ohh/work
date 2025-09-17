package com.wanmi.sbc.setting.api.request.openapisetting;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 单个删除开放平台api设置请求参数
 * @author lvzhenwei
 * @date 2021/4/14 3:17 下午
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingDelByIdRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 6395774053011139086L;

    /** 主键 */
    @Schema(description = "主键")
    @NotNull
    private Long id;
}
