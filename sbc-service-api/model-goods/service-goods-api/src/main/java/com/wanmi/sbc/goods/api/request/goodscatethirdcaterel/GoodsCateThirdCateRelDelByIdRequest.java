package com.wanmi.sbc.goods.api.request.goodscatethirdcaterel;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除平台类目和第三方平台类目映射请求参数</p>
 * @author 
 * @date 2020-08-18 19:51:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateThirdCateRelDelByIdRequest extends GoodsBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;
}
