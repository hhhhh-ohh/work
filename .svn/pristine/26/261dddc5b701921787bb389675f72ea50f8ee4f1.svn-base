package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionListRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 角色标识
     */
    @Schema(description = "角色标识")
    private Long roleInfoId;

    @Schema(description = "角色名")
    private List<String> authorityNames;
}
