package com.wanmi.sbc.recommend.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据商品编号批量查询响应结果
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntelligentRecommendationBaseResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 商品SPU信息
     */
    @Schema(description = "商品SPU信息")
    private List<GoodsVO> goodsVOList = new ArrayList<>();

    /**
     * 索引SKU
     */
    @Schema(description = "索引SKU")
    private MicroServicePage<EsGoodsInfoVO> esGoodsInfoPage = new MicroServicePage<>(new ArrayList<>());

    /**
     * 商品类目信息
     */
    @Schema(description = "商品类目信息")
    private List<GoodsCateVO> goodsCateVOList = new ArrayList<>();

    /**
     * 坑位设置
     */
    @Schema(description = "坑位设置")
    private RecommendPositionConfigurationVO recommendPositionConfigurationVO;

    /**
     * 商品品牌信息
     */
    @Schema(description = "商品品牌信息")
    private List<GoodsBrandVO> goodsBrandVOList = new ArrayList<>();
}
