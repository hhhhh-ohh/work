package com.wanmi.sbc.goods.info.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品SKU编辑视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class GoodsInfoEditResponse extends BasicResponse {

    /**
     * 商品SKU信息
     */
    private GoodsInfoSaveVO goodsInfo;

    /**
     * 相关商品SPU信息
     */
    private GoodsSaveVO goods;

    /**
     * 商品规格列表
     */
    private List<GoodsSpecSaveVO> goodsSpecs = new ArrayList<>();

    /**
     * 商品规格值列表
     */
    private List<GoodsSpecDetailSaveVO> goodsSpecDetails = new ArrayList<>();

    /**
     * 商品等级价格列表
     */
    private List<GoodsLevelPriceVO> goodsLevelPrices = new ArrayList<>();

    /**
     * 商品客户价格列表
     */
    private List<GoodsCustomerPriceVO> goodsCustomerPrices = new ArrayList<>();

    /**
     * 商品订货区间价格列表
     */
    private List<GoodsIntervalPriceVO> goodsIntervalPrices = new ArrayList<>();

    /**
     * 商品相关图片
     */
    private List<GoodsImageVO> images = new ArrayList<>();

    /**
     * 审核商品信息
     */
    private GoodsAuditSaveVO goodsAudit;

}
