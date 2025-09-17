package com.wanmi.sbc.marketing.api.request.communityregionsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除社区拼团区域设置表请求参数</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingDelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 区域id
     */
    @Schema(description = "区域id")
    @NotNull
    private Long regionId;
}
