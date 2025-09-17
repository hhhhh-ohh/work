package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单个查询积分商品表请求参数
 * @author  wur
 * @date: 2021/6/8 20:35
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsByGoodsInfoIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 货品Id
     */
    @Schema(description = "货品Id ")
    @NotNull
    private String goodsInfoId;
}