package com.wanmi.sbc.vas.provider.impl.recommend.intelligentrecommendation;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.dw.api.provider.RecommendListProvider;
import com.wanmi.sbc.dw.api.request.RelationRecommendRequest;
import com.wanmi.sbc.dw.bean.recommend.RecommendData;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.constants.recommend.KafkaTopicConstant;
import com.wanmi.sbc.vas.api.provider.recommend.IntelligentRecommendation.IntelligentRecommendationProvider;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationClickGoodsRequest;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationRequest;
import com.wanmi.sbc.vas.api.response.recommend.IntelligentRecommendation.IntelligentRecommendationResponse;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;
import com.wanmi.sbc.vas.provider.impl.recommend.intelligentrecommendation.service.BrowseGoodsService;
import com.wanmi.sbc.vas.provider.impl.recommend.intelligentrecommendation.service.OtherRecommendService;
import com.wanmi.sbc.vas.provider.impl.recommend.intelligentrecommendation.service.SbcRecommendService;
import com.wanmi.sbc.vas.recommend.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品相关性推荐保存服务接口实现
 *
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@RestController
@Validated
@Slf4j
public class IntelligentRecommendationController implements IntelligentRecommendationProvider {

    @Autowired private KafkaProducer kafkaProducer;

    @Autowired private RedisUtil redisService;
    
    @Autowired private SbcRecommendService sbcRecommendService;

    @Autowired private OtherRecommendService otherRecommendService;

    @Override
    public BaseResponse<IntelligentRecommendationResponse> intelligentRecommendation(
            @Valid @RequestBody IntelligentRecommendationRequest request) {
        // 验证是否购买S2B数谋
        IntelligentRecommendationResponse response = null;
        if (this.getVasRecommendFlag()) {
            response = sbcRecommendService.getRecommendGoods(request);
        } else {
            response = otherRecommendService.getRecommendGoods(request);
        }

        return BaseResponse.success(response);
    }

    /**
     * @description  验证服务是否购买数谋增值服务
     * @author  wur
     * @date: 2022/11/18 13:46
     * @return
     **/
    public Boolean getVasRecommendFlag() {
        Map<String, String> vasList = redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
        Boolean vasRecommendFlag = Boolean.FALSE;
        if(Objects.nonNull(vasList.get(VASConstants.VAS_RECOMMEND_SETTING.toValue()))&&
                VASStatus.ENABLE.toValue().equals(vasList.get(VASConstants.VAS_RECOMMEND_SETTING.toValue()))
        ){
            vasRecommendFlag = Boolean.TRUE;
        }
        return vasRecommendFlag;
    }

    @Override
    public BaseResponse clickGoods(
            @Valid @RequestBody IntelligentRecommendationClickGoodsRequest request) {
        try {
            request.setLocation(request.getType().toValue());
            request.setCreateTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4));
            kafkaProducer.send(
                    JSONObject.toJSONString(request), KafkaTopicConstant.INTELLIGENT_RECOMMENDATION);
        } catch (Exception e) {
            log.error("埋点处理异常 e:{}", e);
        }
        
        return BaseResponse.SUCCESSFUL();
    }
}
