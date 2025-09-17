package com.wanmi.sbc.elastic.api.response.spu;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品索引SPU查询结果
 * Created by daiyitian on 2017/3/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsSpuPageResponse extends BasicResponse {

    /**
     * 商品分页数据
     */
    @Schema(description = "商品分页数据")
    private MicroServicePage<GoodsPageSimpleVO> goodsPage;

    /**
     * 商品SKU全部数据
     */
    @Schema(description = "商品SKU全部数据")
    private List<GoodsInfoForGoodsSimpleVO> goodsInfoList;

    /**
     * 商品品牌所有数据
     */
    @Schema(description = "商品品牌所有数据")
    private List<GoodsBrandSimpleVO> goodsBrandList;

    /**
     * 商品分类所有数据
     */
    @Schema(description = "商品分类所有数据")
    private List<GoodsCateSimpleVO> goodsCateList;

    /**
     * 存放导入商品库的商品
     */
    @Schema(description = "存放导入商品库的商品")
    private List<String> importStandard = new ArrayList<>();

    /**
     * 商品模版关联的所有Id
     */
    @Schema(description = "商品模版关联的所有Id")
    private List<String> goodsIdList;
}
