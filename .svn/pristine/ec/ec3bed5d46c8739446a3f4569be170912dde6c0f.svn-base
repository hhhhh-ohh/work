package com.wanmi.sbc.goods.api.request.goodstemplate;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除GoodsTemplate请求参数</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTemplateDelByIdRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 操作人Id
     */
    @Schema(description = "操作人Id")
    private String updatePerson;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;
}
