package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 批量精选拼团活动请求对象
 */
@Schema
@Data
public class GrouponActivityBatchStickyRequest extends BaseRequest {

    private static final long serialVersionUID = -1103031714507524106L;

    /**
     * 批量精选拼团活动
     */
    @Schema(description = "批量活动ids")
    @NotNull
    private List<String> grouponActivityIdList;

    /**
     * 是否精选
     */
    @Schema(description = "是否精选")
    @NotNull
    private  Boolean sticky;

}
