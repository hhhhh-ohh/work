package com.wanmi.sbc.empower.api.provider.sm.recommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sm.recommend.RecommendGoodsRequest;
import com.wanmi.sbc.empower.api.response.sm.recommend.RecommendGoodsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



/**
 * @author wur
 * @className StratagemRecommendProvider
 * @description 数谋智能推荐主键
 * @date 2022/11/17 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "StratagemRecommendProvider")
public interface StratagemRecommendProvider {

    /**
     * 推荐商品
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/stratagem/recommend/get_goods")
    BaseResponse<RecommendGoodsResponse> queryGoods(@RequestBody RecommendGoodsRequest request);

}