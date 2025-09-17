package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.GoodsDetailType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 根据sku查询商品图文信息和属性
 * @author yangzhen
 * @date 2020/9/3 11:40
 */
@Schema
@Data
public class GoodsDetailProperBySkuIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1130042540295309783L;

    @Schema(description = "skuId")
    @NotBlank
    private String skuId;

    @Schema(description = "渠道商品类型")
    private GoodsDetailType goodsDetailType;
}
