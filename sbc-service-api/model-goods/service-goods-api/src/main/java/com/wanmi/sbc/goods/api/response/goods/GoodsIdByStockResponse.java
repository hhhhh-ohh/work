package com.wanmi.sbc.goods.api.response.goods;


import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsIdByStockResponse extends BasicResponse {
    private static final long serialVersionUID = 8666455265364616825L;

    /**
     * 商品goodsId
     */
    @Schema(description = "商品goodsId")
    private List<String> goodsIds;
}
