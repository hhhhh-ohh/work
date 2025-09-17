package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品SKU视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoAndOtherByIdResponse extends BasicResponse {

    private static final long serialVersionUID = 3228836714138747313L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private GoodsInfoVO goodsInfo;

    /**
     * 商品关联的规格信息
     */
    @Schema(description = "商品关联的规格信息")
    private List<GoodsInfoSpecDetailRelVO> specDetailRelVOList = new ArrayList<>();

    /**
     * 商品属性
     */
    @Schema(description = "商品属性")
    private List<GoodsPropertyDetailRelVO> goodsPropertyDetailRelVOList = new ArrayList<>();

    /**
     * 商品关联的图篇
     */
    private List<GoodsImageVO> goodsImageVOList = new ArrayList<>();

    /**
     * 商品关联的类目
     */
    private GoodsCateVO goodsCateVO;

}
