package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoPlusStockDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品SKU库存增量请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoBatchPlusStockRequest extends BaseRequest {

    private static final long serialVersionUID = 2095878622473588223L;

    /**
     * 批量商品库存数据
     */
    @Schema(description = "批量商品库存数据")
    @NotEmpty
    private List<GoodsInfoPlusStockDTO> stockList;
}
