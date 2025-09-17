package com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商品相关性推荐列表结果</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品相关性推荐列表结果
     */
    @Schema(description = "商品相关性推荐列表结果")
    private List<GoodsRelatedRecommendVO> goodsRelatedRecommendVOList;
}
