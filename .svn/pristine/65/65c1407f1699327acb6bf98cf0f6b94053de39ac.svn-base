package com.wanmi.sbc.goods.api.request.goodsrestrictedsale;


import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedSaleByInfoIdRequest  extends GoodsBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 批量删除-限售主键List
     */
    @Schema(description = "skuId")
    private String goodsInfoId;
}
