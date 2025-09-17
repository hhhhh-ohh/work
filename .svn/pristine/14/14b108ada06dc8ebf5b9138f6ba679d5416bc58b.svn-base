package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 根据sku查询商品简易信息
 * @author yangzhen
 * @date 2020/9/8 11:40
 */
@Schema
@Data
public class GoodsDetailSimpleRequest extends BaseRequest {

    private static final long serialVersionUID = -3587233666435184327L;

    @NotBlank
    @Schema(description = "商品Id")
    private String goodsId;

}
