package com.wanmi.sbc.elastic.api.response.sku;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.OrderForCustomerGoodsInfoExcelVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author EDZ
 * @className EsSkuOrderForCustomerResponse
 * @description TODO
 * @date 2022/4/11 18:27
 **/
public class EsSkuOrderForCustomerResponse extends EsSkuPageBaseResponse{

    /**
     * 分页商品SKU信息
     */
    @Schema(description = "分页商品SKU信息")
    private MicroServicePage<OrderForCustomerGoodsInfoExcelVO> goodsInfoPage;
}