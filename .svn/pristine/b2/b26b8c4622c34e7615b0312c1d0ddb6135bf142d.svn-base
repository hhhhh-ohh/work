package com.wanmi.sbc.goods.standard.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.*;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 商品库新增/编辑请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class StandardSaveRequest extends BaseRequest {

    /**
     * 商品信息
     */
    @NotNull
    private StandardGoodsDTO goods;

    /**
     * 商品相关图片
     */
    private List<StandardImageDTO> images;

    /**
     * 商品属性列表
     */
    private List<StandardPropDetailRelDTO> goodsPropDetailRels;

    /**
     * 商品规格列表
     */
    private List<StandardSpecDTO> goodsSpecs;

    /**
     * 商品规格值列表
     */
    private List<StandardSpecDetailDTO> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    @NotNull
    private List<StandardSkuDTO> goodsInfos;

    /**
     * 属性信息
     */
    private List<GoodsPropertyDetailRelSaveDTO> goodsPropertyDetailRel;

}
