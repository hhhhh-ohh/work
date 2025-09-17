package com.wanmi.sbc.setting.api.request.openapisetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 单个查询开放平台api设置请求参数
 * @author lvzhenwei
 * @date 2021/4/14 3:13 下午
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingByIdRequest extends BaseRequest {
    private static final long serialVersionUID = -2412971238313840661L;

    /** 主键 */
    @Schema(description = "主键")
    @NotNull
    private Long id;
}
