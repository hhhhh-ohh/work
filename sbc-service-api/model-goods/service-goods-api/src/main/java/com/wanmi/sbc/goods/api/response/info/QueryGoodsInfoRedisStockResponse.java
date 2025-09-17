package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoRedisStockVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryGoodsInfoRedisStockResponse extends BasicResponse {

    private static final long serialVersionUID = 678604691614740765L;

    /**
     * 商品信息
     */
    @Schema(description = "商品缓存信息")
    private List<GoodsInfoRedisStockVO> goodsInfoRedisStockVOList;
}
