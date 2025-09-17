package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品库Sku获取响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class StandardSkuByIdResponse extends BasicResponse {
    private static final long serialVersionUID = -8162177874085991281L;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private StandardGoodsVO goods;

    /**
     * 商品SKU列表
     */
    @Schema(description = "商品SKU列表")
    private StandardSkuVO goodsInfo;

    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private List<StandardSpecDetailVO> goodsSpecDetails;

    /**
     * 商品规格列表
     */
    @Schema(description = "商品规格列表")
    private List<StandardSpecVO> goodsSpecs;

    /**
     * 商品相关图片
     */
    @Schema(description = "商品相关图片")
    private List<StandardImageVO> images;
}
