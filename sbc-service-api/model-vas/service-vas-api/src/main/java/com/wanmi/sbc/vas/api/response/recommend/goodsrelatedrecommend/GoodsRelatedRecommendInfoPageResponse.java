package com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName GoodsRelatedRecommendInfoPageResponse
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/24 18:20
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendInfoPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品相关性推荐分页结果
     */
    @Schema(description = "商品相关性推荐分页结果")
    private MicroServicePage<GoodsRelatedRecommendInfoVO> goodsRelatedRecommendInfoVOPage;
}
