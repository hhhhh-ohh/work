package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsPageResponse
 * 商品分页响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:34
 */
@Schema
@Data
public class GoodsPageResponse extends BasicResponse {

    private static final long serialVersionUID = 2435750105858150399L;

    /**
     * 商品分页数据
     */
    @Schema(description = "商品分页数据")
    private MicroServicePage<GoodsVO> goodsPage;

    /**
     * 商品SKU全部数据
     */
    @Schema(description = "商品SKU全部数据")
    private List<GoodsInfoVO> goodsInfoList;

    /**
     * 商品品牌所有数据
     */
    @Schema(description = "商品品牌所有数据")
    private List<GoodsBrandVO> goodsBrandList;

    /**
     * 商品分类所有数据
     */
    @Schema(description = "商品分类所有数据")
    private List<GoodsCateVO> goodsCateList;

    /**
     * 存放导入商品库的商品
     */
    @Schema(description = "存放导入商品库的商品")
    private List<String> importStandard = new ArrayList<>();

}
