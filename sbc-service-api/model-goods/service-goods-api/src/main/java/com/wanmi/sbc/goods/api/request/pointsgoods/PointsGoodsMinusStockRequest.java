package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>积分商品扣除库存请求参数</p>
 *
 * @author yinxianzhi
 * @date 2019-05-20 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsMinusStockRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品id")
    @NotNull
    private String pointsGoodsId;

    /**
     * 库存数
     */
    @Schema(description = "库存数")
    private Long stock;

}