package com.wanmi.sbc.vas.provider.impl.recommend.intelligentrecommendation.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.dw.api.provider.RecommendListProvider;
import com.wanmi.sbc.dw.api.request.RelationRecommendRequest;
import com.wanmi.sbc.dw.bean.recommend.RecommendData;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationQueryRequest;
import com.wanmi.sbc.vas.api.response.recommend.IntelligentRecommendation.IntelligentRecommendationResponse;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionOpenFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;
import com.wanmi.sbc.vas.bean.enums.recommen.TacticsType;
import com.wanmi.sbc.vas.recommend.kafka.KafkaProducer;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.model.root.RecommendPositionConfiguration;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.service.RecommendPositionConfigurationService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description   SBC数谋智能推荐处理类
 * @author  wur
 * @date: 2022/11/18 10:22
 **/
@Service
public class SbcRecommendService {

    @Autowired
    private RecommendListProvider recommendListProvider;

    @Autowired private BrowseGoodsService browseGoodsService;

    @Autowired private RecommendPositionConfigurationService recommendPositionConfigurationService;

    @Autowired private GoodsCateQueryProvider goodsCateQueryProvider;

    public IntelligentRecommendationResponse getRecommendGoods(IntelligentRecommendationRequest request) {
        IntelligentRecommendationResponse response = new IntelligentRecommendationResponse();

        //1. 查询坑位信息并验证 如果是魔方数据无需验证坑位直接查询
        if (PositionType.MAGIC_BOX == request.getType()) {
            // 1.1
            request.setRecommendType(TacticsType.HOT.toValue());
        } else {
            // 1.2.1
            List<RecommendPositionConfiguration> positionConfigList = recommendPositionConfigurationService.list(
                    RecommendPositionConfigurationQueryRequest.builder().type(request.getType()).build());
            if (CollectionUtils.isEmpty(positionConfigList)) {
                return response;
            }
            RecommendPositionConfiguration positionConfig = positionConfigList.get(NumberUtils.INTEGER_ZERO);
            if (Objects.isNull(positionConfig) || Objects.equals(PositionOpenFlag.CLOSED, positionConfig.getIsOpen())) {
                return response;
            }
            //1.2.2 根据坑位封装策略
            request.setRecommendType(positionConfig.getTacticsType().toValue());
            if (PositionType.CATE_PAGE == request.getType()) {
                request.setInterestItem(String.join(",", positionConfig.getContent()));
            }
            // 1.2.3 商品分类
            if (PositionType.GOODS_CATE == request.getType()) {
                request.setItem(NumberUtils.INTEGER_ONE);
                List<Long> relationCateIdList = request.getRelationCateIdList();
                if (CollectionUtils.isNotEmpty(relationCateIdList)) {
                    GoodsCateChildCateIdsByIdRequest idRequest =
                            new GoodsCateChildCateIdsByIdRequest();
                    idRequest.setCateId(relationCateIdList.get(0));
                    request.setRelationCateIdList(
                            goodsCateQueryProvider
                                    .getChildCateIdById(idRequest)
                                    .getContext()
                                    .getChildCateIdList());
                }
            }
            response.setRecommendPositionConfigurationVO(recommendPositionConfigurationService.wrapperVo(positionConfig));
        }

        //2. 封装查询智能推荐商品请求参数
        RelationRecommendRequest recommendRequest =
                KsBeanUtil.copyPropertiesThird(request, RelationRecommendRequest.class);
        RecommendData recommendData =
                recommendListProvider.queryGoodsRecommend(recommendRequest).getContext();

        //3. 封装响应数据并处理埋点
        if (PositionType.GOODS_CATE == request.getType()) {
            List<Long> relationCateIdList = recommendData.getRelationCateIdList();
            if (CollectionUtils.isEmpty(relationCateIdList)) {
                relationCateIdList = new ArrayList<>();
            }
            response.setCateIdList(relationCateIdList);
            // 浏览埋点接口
            for (Long cateId : relationCateIdList) {
                browseGoodsService.browseCate(request, cateId);
            }
        } else if (PositionType.CATE_PAGE == request.getType()) {
            List<Long> brandList = recommendData.getInterestBrandList();
            if (CollectionUtils.isEmpty(brandList)) {
                brandList = new ArrayList<>();
            }
            response.setInterestBrandList(brandList);
            // 浏览埋点接口
            for (Long brandId : brandList) {
                browseGoodsService.browseBrand(request, brandId);
            }
            List<Long> cateList = recommendData.getInterestCateList();
            if (CollectionUtils.isEmpty(cateList)) {
                cateList = new ArrayList<>();
            }
            response.setCateIdList(cateList);
            // 浏览埋点接口
            for (Long cateId : cateList) {
                browseGoodsService.browseCate(request, cateId);
            }
        } else {
            List<String> goodsIdList = recommendData.getRelationGoodsIdList();
            if (CollectionUtils.isEmpty(goodsIdList)) {
                goodsIdList = new ArrayList<>();
            }
            response.setGoodsIdList(goodsIdList);
            // 浏览埋点接口
            for (String goodsId : goodsIdList) {
                browseGoodsService.browseGoods(request, goodsId);
            }
        }
        return response;
    }
}
