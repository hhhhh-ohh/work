package com.wanmi.sbc.marketing.api.request.communitystockorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除社区团购备货单请求参数</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStockOrderDelByActivityIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    @Schema(description = "activityId")
    @NotNull
    private String activityId;
}
