package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoStockDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 商品SKU库存更新请求
 * @Author qiaokang
 * @Date：2019-06-03 16:25:11
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoBatchStockRequest extends BaseRequest {

    private static final long serialVersionUID = -5282387507503416384L;

    /**
     * 批量商品库存数据
     */
    @Schema(description = "批量商品库存数据")
    @NotEmpty
    private List<GoodsInfoStockDTO> stockList;
}
