package com.wanmi.sbc.setting.api.response.systemconfig;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigContextReponse extends BasicResponse {

    /**
     * 配置内容
     */
    @Schema(description = "配置内容")
    private String context;
}
