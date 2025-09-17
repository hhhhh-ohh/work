package com.wanmi.sbc.goods.api.request.goodspropertydetailrel;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商品与属性值关联请求参数</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailRelDelByIdRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * detailRelId
     */
    @Schema(description = "detailRelId")
    @NotNull
    private Long detailRelId;
}
