package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 据商品skuNo查询商品sku视图
 * @author  wur
 * @date: 2021/6/8 19:48
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoViewBySkuNoRequest extends BaseRequest {

    private static final long serialVersionUID = -2265501195719873212L;

    /**
     * 商品sku编码
     */
    @Schema(description = "商品sku编码")
    @NotBlank
    private String skuNo;
}
