package com.wanmi.sbc.goods.api.request.goodspropertydetail;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商品属性值请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailDelByIdRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 属性值id
     */
    @Schema(description = "属性值id")
    @NotNull
    private Long detailId;
}
