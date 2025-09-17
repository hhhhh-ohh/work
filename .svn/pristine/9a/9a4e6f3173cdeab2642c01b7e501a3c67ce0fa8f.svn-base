package com.wanmi.sbc.crm.api.request.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName GoodsRelatedRecommendInfoListRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/24 17:11
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendInfoListRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String goodsId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String storeName;

    /**
     * 商品SPU编码
     */
    @Schema(description = "商品SPU编码")
    private String goodsNo;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    private String goodsCate;

    /**
     * 商品类目id
     */
    @Schema(description = "商品类目id")
    private Long goodsCateId;

    /**
     * 商品类目idList
     */
    @Schema(description = "商品类目idList")
    List<Long> goodsCateIds;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌")
    private String goodsBrand;

    /**
     * 商品品牌id
     */
    @Schema(description = "商品品牌id")
    private Long goodsBrandId;
}
