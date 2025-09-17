package com.wanmi.sbc.goods.info.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.goods.bean.dto.GoodsCustomerPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsIntervalPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsLevelPriceDTO;
import lombok.Data;

import java.util.List;

/**
 * 商品新增/编辑请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class GoodsInfoSaveRequest extends BaseRequest {

    /**
     * 商品SKU信息
     */
    private GoodsInfoSaveDTO goodsInfo;

    /**
     * 商品等级价格列表
     */
    private List<GoodsLevelPriceDTO> goodsLevelPrices;

    /**
     * 商品客户价格列表
     */
    private List<GoodsCustomerPriceDTO> goodsCustomerPrices;

    /**
     * 商品订货区间价格列表
     */
    private List<GoodsIntervalPriceDTO> goodsIntervalPrices;

    /**
     * sku维度设价
     */
    BoolFlag skuEditPrice = BoolFlag.NO;

}
