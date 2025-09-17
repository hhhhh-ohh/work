package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.EnableStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>积分商品表修改参数</p>
 *
 * @author yang
 * @date 2021-05-07 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsModifyStatusByStoreIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @NotNull
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态")
    @NotNull
    private EnableStatus status;
}