package com.wanmi.sbc.customer.api.request.communityleader;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除社区团购团长表请求参数</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderDelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 团长id
     */
    @Schema(description = "团长id")
    @NotNull
    private String leaderId;
}
