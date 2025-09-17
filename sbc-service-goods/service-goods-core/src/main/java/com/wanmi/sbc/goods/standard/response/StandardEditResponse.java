package com.wanmi.sbc.goods.standard.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.Data;

import java.util.List;

/**
 * 商品库编辑视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class StandardEditResponse extends BasicResponse {

    /**
     * 商品信息
     */
    private StandardGoodsVO goods;

    /**
     * 商品相关图片
     */
    private List<StandardImageVO> images;

    /**
     * 商品属性列表
     */
    private List<StandardPropDetailRelVO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    private List<StandardSpecVO> goodsSpecs;

    /**
     * 商品规格值列表
     */
    private List<StandardSpecDetailVO> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    private List<StandardSkuVO> goodsInfos;

}
