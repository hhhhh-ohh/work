package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>批量查询积分商品信息-goodsInfoIds请求参数</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsSimplePageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "积分商品ids-批量查询")
    private List<String> pointsGoodsIds;

    @Schema(description = "商品skuIds-批量查询")
    private List<String> goodsInfoIds;

    @Schema(description = "商品spuIds-批量查询")
    private List<String> goodsIds;
}