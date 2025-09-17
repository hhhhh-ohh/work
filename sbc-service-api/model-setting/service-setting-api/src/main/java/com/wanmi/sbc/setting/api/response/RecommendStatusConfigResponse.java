package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xufeng
 */
@Schema
@Data
public class RecommendStatusConfigResponse extends BasicResponse {
    private static final long serialVersionUID = -7619551227094936289L;
    /**
     * 种草展示 0 不展示 1 展示
     */
    @Schema(description = "种草展示 0 不展示 1 展示")
    private Integer recommendStatus;

    /**
     * 直播、种草展示等级 0 直播在前
     */
    @Schema(description = "直播、种草展示等级 0 直播在前")
    private Integer showSort;
}
