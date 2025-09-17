package com.wanmi.sbc.marketing.api.request.communitydeliveryorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除社区团购发货单请求参数</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDeliveryOrderDelByActivityIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private String activityId;
}
