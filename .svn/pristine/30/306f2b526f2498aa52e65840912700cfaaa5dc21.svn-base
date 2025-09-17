package com.wanmi.sbc.vas.api.provider.recommend.IntelligentRecommendation;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationClickGoodsRequest;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationRequest;
import com.wanmi.sbc.vas.api.response.recommend.IntelligentRecommendation.IntelligentRecommendationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 商品智能推荐服务Provider
 * @author  lvzhenwei
 * @date 2021/4/9 4:14 下午
 **/
@FeignClient(value = "${application.vas.name}", contextId = "IntelligentRecommendationProvider")
public interface IntelligentRecommendationProvider {

  /** 查询智能推荐商品数据API */
  @PostMapping("/vas/${application.vas.version}/intelligentrecommendationprovider/get")
  BaseResponse<IntelligentRecommendationResponse> intelligentRecommendation(
      @RequestBody @Valid IntelligentRecommendationRequest request);

  /** 点击推荐商品埋点 */
  @PostMapping("/vas/${application.vas.version}/intelligentrecommendationprovider/click/goods")
  BaseResponse clickGoods(@RequestBody @Valid IntelligentRecommendationClickGoodsRequest request);
}
