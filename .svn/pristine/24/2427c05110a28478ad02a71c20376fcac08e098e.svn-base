package com.wanmi.sbc.elastic.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 批量审核通过拼团活动请求对象
 * @author houshuai
 */
@Schema
@Data
public class EsGrouponActivityBatchCheckRequest extends BaseRequest {

    /**
     * 批量审核拼团活动
     */
    @Schema(description = "批量活动ids")
    @NotNull
    private List<String> grouponActivityIdList;

}