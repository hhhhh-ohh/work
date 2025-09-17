package com.wanmi.sbc.goods.api.request.price;

import com.wanmi.sbc.common.base.BaseRequest;

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
public class GoodsLevelPriceBySkuIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -5885584224904838232L;

    @Schema(description = "商品Id")
    private List<String> skuIds;
}
