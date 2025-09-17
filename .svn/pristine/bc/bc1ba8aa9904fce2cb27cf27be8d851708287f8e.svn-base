package com.wanmi.sbc.elastic.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 批量精选拼团活动请求对象
 * @author houshuai
 */
@Schema
@Data
public class EsGrouponActivityBatchStickyRequest extends BaseRequest {

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
