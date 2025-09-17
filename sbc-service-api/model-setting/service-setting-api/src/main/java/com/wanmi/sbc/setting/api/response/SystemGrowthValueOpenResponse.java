package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 成长值开关
 */
@Data
public class SystemGrowthValueOpenResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 1 true:开启 0 false:关闭
     */
    @Schema(description = "成长值开关-true:开启 false:关闭")
    private boolean open;
}