package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author edz
 * @className FlashSaleGoodsCloseRequest
 * @description 关闭秒杀商品请求体
 * @date 2021/6/25 10:22 上午
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsCloseRequest extends GoodsBaseRequest {

    private static final long serialVersionUID = 6073544319257358377L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

}
