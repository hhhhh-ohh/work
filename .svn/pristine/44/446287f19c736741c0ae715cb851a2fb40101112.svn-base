package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品更新供货价请求
 *
 * @author kkq
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoUpdateSupplyPriceRequest extends BaseRequest {

    private static final long serialVersionUID = -756726568883495098L;

    /**
     * 批量商品sku
     */
    @Schema(description = "批量商品sku")
    private List<GoodsInfoVO> goodsInfos;

}
