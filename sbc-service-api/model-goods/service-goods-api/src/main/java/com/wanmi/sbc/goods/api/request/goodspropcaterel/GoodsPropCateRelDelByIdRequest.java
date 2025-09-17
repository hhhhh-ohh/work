package com.wanmi.sbc.goods.api.request.goodspropcaterel;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除商品类目与属性关联请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropCateRelDelByIdRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 关联表主键
     */
    @Schema(description = "关联表主键")
    @NotNull
    private Long relId;
}
