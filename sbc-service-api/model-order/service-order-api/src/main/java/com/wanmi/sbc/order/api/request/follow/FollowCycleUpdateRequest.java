package com.wanmi.sbc.order.api.request.follow;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className FollowCycleUpdateRequest
 * @description
 * @date 2022/10/31 10:24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class FollowCycleUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = -3999274730466371603L;

    /**
     * 商品id
     */
    @NotEmpty
    @Schema(description = "商品id")
    private List<String> goodsInfoIds;

    /**
     * 是否周期购商品
     */
    @NotNull
    @Schema(description = "是否周期购商品")
    private Integer isBuyCycle;
}
