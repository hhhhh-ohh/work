package com.wanmi.sbc.elastic.api.response.sku;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品SKU视图分页响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class EsSkuPageBaseResponse extends BasicResponse {

    /**
     * 商品SPU信息
     */
    @Schema(description = "商品SPU信息")
    private List<GoodsVO> goodses;

    /**
     * 品牌列表
     */
    @Schema(description = "品牌列表")
    private List<GoodsBrandSimpleVO> brands = new ArrayList<>();

    /**
     * 分类列表
     */
    @Schema(description = "分类列表")
    private List<GoodsCateSimpleVO> cates = new ArrayList<>();

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 商家信息数据
     */
    @Schema(description = "商家信息数据")
    private List<CompanyInfoVO> companyInfoList = new ArrayList<>();
}
