package com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）商品相关性推荐信息response</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品相关性推荐信息
     */
    @Schema(description = "商品相关性推荐信息")
    private GoodsRelatedRecommendVO goodsRelatedRecommendVO;
}
