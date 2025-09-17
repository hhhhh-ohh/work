package com.wanmi.sbc.goods.api.response.price;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCustomerPriceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCustomerPriceBySkuIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -5449723950968096788L;

    @Schema(description = "商品客户价格")
    private List<GoodsCustomerPriceVO> goodsCustomerPriceVOList;
}
