package com.wanmi.sbc.setting.api.request.platformaddress;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除平台地址信息请求参数</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAddressDelByIdRequest extends SettingBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull
    private String id;
}
