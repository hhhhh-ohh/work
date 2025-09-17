package com.wanmi.sbc.vas.provider.impl.recommend.intelligentrecommendation.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.empower.api.provider.sm.recommend.StratagemRecommendProvider;
import com.wanmi.sbc.empower.api.request.sm.recommend.RecommendGoodsRequest;
import com.wanmi.sbc.empower.api.response.sm.recommend.RecommendGoodsResponse;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsListByIdsResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation.IntelligentRecommendationRequest;
import com.wanmi.sbc.vas.api.response.recommend.IntelligentRecommendation.IntelligentRecommendationResponse;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionOpenFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.TacticsType;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description   其他数谋智能推荐处理类
 * @author  wur
 * @date: 2022/11/18 10:22
 **/
@Service
public class OtherRecommendService {

    @Autowired private StratagemRecommendProvider stratagemRecommendProvider;

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    public final static String SPLIT_COMMA = "_";
    
    public IntelligentRecommendationResponse getRecommendGoods(IntelligentRecommendationRequest request) {
        IntelligentRecommendationResponse response = new IntelligentRecommendationResponse();
        List<String> relationGoodsIdListNew = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(request.getRelationGoodsIdList())) {
            GoodsListByIdsResponse goodsListByIdsResponse = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getRelationGoodsIdList()).getGoodsInfoFlag(BoolFlag.NO).build()).getContext();
            if (Objects.nonNull(goodsListByIdsResponse) && CollectionUtils.isNotEmpty(goodsListByIdsResponse.getGoodsVOList())) {
                for (GoodsVO goodsVO: goodsListByIdsResponse.getGoodsVOList()) {
                    StringBuilder goodsId = new StringBuilder(goodsVO.getGoodsId());
                    goodsId.append(SPLIT_COMMA);
                    if (Objects.nonNull(goodsVO.getStoreId())) {
                        goodsId.append(goodsVO.getStoreId().toString());
                    } else {
                        goodsId.append("-1");
                    }
                    relationGoodsIdListNew.add(goodsId.toString());
                }
            }
        }
        RecommendGoodsRequest recommendGoodsRequest = new RecommendGoodsRequest();
        recommendGoodsRequest.setPositionType(request.getType().toValue());
        recommendGoodsRequest.setCustomerId(request.getCustomerId());
        recommendGoodsRequest.setRelationGoodsIds(relationGoodsIdListNew);
        recommendGoodsRequest.setPageSize(request.getPageSize());
        recommendGoodsRequest.setPageNum(request.getPageIndex());
        RecommendGoodsResponse goodsResponse = stratagemRecommendProvider.queryGoods(recommendGoodsRequest).getContext();
        if (Objects.nonNull(goodsResponse)) {
            if (CollectionUtils.isNotEmpty(goodsResponse.getGoodsIdList())) {
                response.setGoodsIdList(goodsResponse.getGoodsIdList());
            }
            if (Objects.nonNull(goodsResponse.getPositionVO())) {
                RecommendPositionConfigurationVO recommendPositionConfigurationVO = new RecommendPositionConfigurationVO();
                recommendPositionConfigurationVO.setIsOpen(PositionOpenFlag.fromValue(goodsResponse.getPositionVO().getIsOpen()));
                recommendPositionConfigurationVO.setName(goodsResponse.getPositionVO().getName());
                recommendPositionConfigurationVO.setTitle(goodsResponse.getPositionVO().getTitle());
                recommendPositionConfigurationVO.setTacticsType(TacticsType.fromValue(goodsResponse.getPositionVO().getTacticsType()));
                recommendPositionConfigurationVO.setType(request.getType());
                response.setRecommendPositionConfigurationVO(recommendPositionConfigurationVO);
            }
        }
        return response;
    }
}
