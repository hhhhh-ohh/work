package com.wanmi.sbc.vas.provider.impl.recommend.intelligentrecommendation.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.constants.recommend.KafkaTopicConstant;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationBrowseGoodsRequest;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationRequest;
import com.wanmi.sbc.vas.recommend.kafka.KafkaProducer;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/** @Description @Author 10486 @Date 15:09 2020/12/18 */
@Service
public class BrowseGoodsService {

  @Autowired private KafkaProducer kafkaProducer;

  /** 浏览推荐商品埋点 */
  @Async
  public void browseGoods(IntelligentRecommendationRequest request, String goodsId) {
    IntelligentRecommendationBrowseGoodsRequest browseGoodsRequest =
        KsBeanUtil.copyPropertiesThird(request, IntelligentRecommendationBrowseGoodsRequest.class);
    browseGoodsRequest.setEventType(NumberUtils.INTEGER_ZERO);
    browseGoodsRequest.setLocation(request.getType().toValue());
    browseGoodsRequest.setItem(NumberUtils.INTEGER_ONE);
    browseGoodsRequest.setGoodsId(goodsId);
    browseGoodsRequest.setCreateTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_1));
    kafkaProducer.send(
        JSONObject.toJSONString(browseGoodsRequest), KafkaTopicConstant.INTELLIGENT_RECOMMENDATION);
  }

  /** 浏览推荐类目埋点 */
  @Async
  public void browseCate(IntelligentRecommendationRequest request, Long cateId) {
    IntelligentRecommendationBrowseGoodsRequest browseGoodsRequest =
        KsBeanUtil.copyPropertiesThird(request, IntelligentRecommendationBrowseGoodsRequest.class);
    browseGoodsRequest.setEventType(NumberUtils.INTEGER_ZERO);
    browseGoodsRequest.setLocation(request.getType().toValue());
    browseGoodsRequest.setItem(NumberUtils.INTEGER_ZERO);
    browseGoodsRequest.setCateId(cateId);
    browseGoodsRequest.setCreateTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_1));
    kafkaProducer.send(
        JSONObject.toJSONString(browseGoodsRequest), KafkaTopicConstant.INTELLIGENT_RECOMMENDATION);
  }

  /** 浏览推荐品牌埋点 */
  @Async
  public void browseBrand(IntelligentRecommendationRequest request, Long brandId) {
    IntelligentRecommendationBrowseGoodsRequest browseGoodsRequest =
        KsBeanUtil.copyPropertiesThird(request, IntelligentRecommendationBrowseGoodsRequest.class);
    browseGoodsRequest.setEventType(NumberUtils.INTEGER_ZERO);
    browseGoodsRequest.setLocation(request.getType().toValue());
    browseGoodsRequest.setItem(2);
    browseGoodsRequest.setBrandId(brandId);
    browseGoodsRequest.setCreateTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_1));
    kafkaProducer.send(
        JSONObject.toJSONString(browseGoodsRequest), KafkaTopicConstant.INTELLIGENT_RECOMMENDATION);
  }
}
