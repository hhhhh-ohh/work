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
public class GoodsCustomerPriceBySkuIdsAndCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 6833610000603530402L;

    @Schema(description = "商品Id")
    private List<String> skuIds;

    @Schema(description = "用户Id")
    private String customerId;
}
