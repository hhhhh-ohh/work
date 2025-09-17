package com.wanmi.sbc.goods.standard.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品库SKU编辑视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class StandardSkuEditResponse extends BasicResponse {

    /**
     * 商品SKU信息
     */
    private StandardSkuVO goodsInfo;

    /**
     * 相关商品SPU信息
     */
    private StandardGoodsVO goods;

    /**
     * 商品规格列表
     */
    private List<StandardSpecVO> goodsSpecs = new ArrayList<>();

    /**
     * 商品规格值列表
     */
    private List<StandardSpecDetailVO> goodsSpecDetails = new ArrayList<>();

    /**
     * 商品相关图片
     */
    private List<StandardImageVO> images = new ArrayList<>();

}
