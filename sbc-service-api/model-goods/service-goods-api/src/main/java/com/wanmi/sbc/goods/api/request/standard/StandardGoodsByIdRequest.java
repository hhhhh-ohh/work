package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * <p>根据id查询商品库</p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
public class StandardGoodsByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -2181018761309586558L;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    @NotBlank
    private String goodsId;
}
