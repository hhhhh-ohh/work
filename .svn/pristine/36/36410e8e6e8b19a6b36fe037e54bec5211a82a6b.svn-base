package com.wanmi.sbc.elastic.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className EsGoodsSelectOptionsResponse
 * @description TODO
 * @date 2021/10/18 4:08 下午
 * 商品列表筛选属性
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsGoodsSelectOptionsResponse extends BasicResponse {
    /**
     * 品牌
     */
    @Schema(description = "品牌")
    private List<GoodsListBrandVO> brands = new ArrayList<>();

    /**
     * 品牌分组
     */
    @Schema(description = "品牌分组")
    private Map<String,List<GoodsBrandVO>> brandMap;

    /**
     * 分类
     */
    @Schema(description = "分类")
    private List<GoodsCateVO> cateList = new ArrayList<>();

    /**
     * 规格
     */
    @Schema(description = "规格")
    private List<GoodsSpecVO> goodsSpecs = new ArrayList<>();

    /**
     * 规格值
     */
    @Schema(description = "规格值")
    private List<GoodsSpecDetailVO> goodsSpecDetails = new ArrayList<>();

    /**
     * 商品标签
     */
    @Schema(description = "商品标签")
    private List<GoodsLabelVO> goodsLabelVOList;

    /**
     * 商品属性
     */
    @Schema(description = "商品属性")
    private List<GoodsPropertyVO> goodsPropertyVOS;
}
