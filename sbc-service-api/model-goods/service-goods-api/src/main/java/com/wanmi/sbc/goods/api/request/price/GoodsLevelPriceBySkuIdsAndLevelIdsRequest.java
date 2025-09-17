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
public class GoodsLevelPriceBySkuIdsAndLevelIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 5051541417325116744L;

    @Schema(description = "商品Id")
    private List<String> skuIds;

    @Schema(description = "等级Id")
    private List<Long> levelIds;
}
