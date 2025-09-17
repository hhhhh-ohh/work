package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 采购单商品SKU视图VO
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PurchaseGoodsViewVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<GoodsInfoVO> goodsInfos;

    /**
     * 商品SPU信息
     */
    @Schema(description = "商品SPU信息")
    private List<GoodsVO> goodses;
}
