package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest
 * 根据编号查询商品信息请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午9:40
 */
@Schema
@Data
public class GoodsViewByIdAndSkuIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 616717662122084615L;

    @Schema(description = "商品Id")
    @NotBlank
    private String goodsId;

    @Schema(description = "skuId集合")
    @NotNull
    private List<String> skuIds;
}
