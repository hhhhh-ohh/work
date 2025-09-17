package com.wanmi.sbc.goods.api.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsMarketingDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>商品营销</p>
 * author: sunkun
 * Date: 2018-11-02
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMarketingBatchAddRequest extends BaseRequest {

    private static final long serialVersionUID = -1104650734607227726L;

    /**
     * 商品营销实体
     */
    @Schema(description = "商品营销实体")
    @NotNull
    private List<GoodsMarketingDTO> goodsMarketings;
}
