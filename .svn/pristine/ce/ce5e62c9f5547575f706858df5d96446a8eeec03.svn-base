package com.wanmi.sbc.customer.api.request.communitypickup;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除团长自提点表请求参数</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderPickupPointDelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 自提点id
     */
    @Schema(description = "自提点id")
    @NotNull
    private String pickupPointId;
}
