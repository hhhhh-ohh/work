package com.wanmi.sbc.recommend.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author xufeng
 */
@Data
public class RecommendStatisticsRequest extends BaseRequest {

    /**
     * 种草pageCode
     */
    @Schema(description = "种草pageCode")
    @NotNull
    private String pageCode;

    /**
     * 点赞 1 取消 0
     */
    @Schema(description = "点赞 1 取消 0")
    private BoolFlag thumbsUpFlag;

    /**
     * 转发 1
     */
    @Schema(description = "转发 1 ")
    private BoolFlag forwardFlag;
}
