package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
public class StandardGoodsPageResponse extends BasicResponse {

    private static final long serialVersionUID = 2413877141645305289L;

    /**
     * 商品分页数据
     */
    @Schema(description = "商品分页数据")
    private MicroServicePage<StandardGoodsVO> standardGoodsPage ;

    /**
     * 商品SKU全部数据
     */
    @Schema(description = "商品SKU全部数据")
    private List<StandardSkuVO> standardSkuList = new ArrayList<>();

    /**
     * 商品SKU的规格值全部数据
     */
    @Schema(description = "商品SKU的规格值全部数据")
    private List<StandardSkuSpecDetailRelVO> standardSkuSpecDetails = new ArrayList<>();

    /**
     * 商品品牌所有数据
     */
    @Schema(description = "商品品牌所有数据")
    private List<GoodsBrandVO> goodsBrandList = new ArrayList<>();

    /**
     * 商品分类所有数据
     */
    @Schema(description = "商品分类所有数据")
    private List<GoodsCateVO> goodsCateList = new ArrayList<>();

    /**
     * 已被导入的商品库ID
     */
    @Schema(description = "已被导入的商品库ID")
    private List<String> usedStandard = new ArrayList<>();

    /**
     * 需要同步的商品库ID
     */
    @Schema(description = "需要同步的商品库ID")
    private List<String> needSynStandard = new ArrayList<>();

}
