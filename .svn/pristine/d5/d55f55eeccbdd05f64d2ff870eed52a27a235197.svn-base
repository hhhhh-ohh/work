package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询商品属性请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsGoodsPropertyByIdListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    @Schema(description = "属性id")
    @NotNull
    private Long propId;

}