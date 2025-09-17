package com.wanmi.sbc.goods.api.response.marketing;

import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;

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
public class ListByCustomerIdAndGoodsInfoIdResp implements Serializable {

    @Schema(description = "商品营销")
    private List<GoodsMarketingVO> goodsMarketings;

}
