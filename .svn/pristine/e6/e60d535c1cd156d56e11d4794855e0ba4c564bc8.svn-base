package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
public class StandardGoodsGetUsedGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 7496661666999926313L;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    @NotNull
    private List<String> goodsIds;
}
