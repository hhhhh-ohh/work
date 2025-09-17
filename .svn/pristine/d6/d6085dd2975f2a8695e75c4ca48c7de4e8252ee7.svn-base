package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>供应商商品库存同步入参</p>
 * Created by of628-wenzhi on 2020-09-09-9:25 下午.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ProviderGoodsStockSyncRequest extends GoodsBaseRequest {
    private static final long serialVersionUID = 5331968806521004758L;

    @Schema(description = "待同步库存商品sku列表")
    @NotEmpty
    private List<GoodsInfoDTO> goodsInfoList;
}
