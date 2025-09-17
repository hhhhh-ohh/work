package com.wanmi.sbc.goods.api.response.price;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据skuIds查询区间价列表响应
 *
 * @author daiyitian
 * @dateTime 2018/11/13 上午9:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsIntervalPriceListBySkuIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -1608258667050050471L;

    @Schema(description = "商品订货区间价格")
    private List<GoodsIntervalPriceVO> goodsIntervalPriceVOList;
}
