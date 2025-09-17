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
public class OfflinePaySettingResponse extends BasicResponse {

    /**
     * 状态 0:未启用1:已启用
     */
    @Schema(description = "状态 0:未启用 1:已启用")
    private Integer status;
}
