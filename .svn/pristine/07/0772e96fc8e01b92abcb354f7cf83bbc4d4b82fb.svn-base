package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.List;

/**
 * 批量审核通过拼团活动请求对象
 */
@Schema
@Data
public class GrouponActivityBatchCheckRequest extends BaseRequest {

    private static final long serialVersionUID = 1667727002468608203L;

    /**
     * 批量审核拼团活动
     */
    @Schema(description = "批量活动ids")
    @NotEmpty
    private List<String> grouponActivityIdList;

}
