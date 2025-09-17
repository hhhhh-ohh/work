package com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName GoodsRelatedRecommendAddListRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/25 14:12
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendAddListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商品相关性推荐新增参数List
     */
    @Schema(description = "商品相关性推荐新增参数List")
    List<GoodsRelatedRecommendAddRequest> addGoodsRelatedRecommendAddList;
}
