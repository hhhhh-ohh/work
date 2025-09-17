package com.wanmi.sbc.marketing.newplugin.impl;

import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsQueryRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.marketingsuits.model.root.MarketingSuits;
import com.wanmi.sbc.marketing.marketingsuits.service.MarketingSuitsService;
import com.wanmi.sbc.marketing.marketingsuitssku.service.MarketingSuitsSkuService;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组合购
 *
 * @author zhanggaolei
 * @className MarketingSuitsPlugin
 * @description TODO
 * @date 2021/5/19 14:08
// */
//@Slf4j
//@MarketingPluginService(type = MarketingPluginType.SUITS)
//public class MarketingSuitsPlugin implements MarketingPluginInterface {
//    @Autowired private MarketingSuitsService marketingSuitsService;
//    @Autowired private MarketingSuitsSkuService marketingSuitsSkuService;
//
//    @Override
//    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
//        List<MarketingResponse> suitsList =
//                MarketingContext.getBaseRequest()
//                        .getMultiTypeMarketingMap()
//                        .get(request.getGoodsInfoPluginRequest().getGoodsInfoId())
//                        .stream()
//                        .filter(marketing -> MarketingType.SUITS == marketing.getMarketingType())
//                        .collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(suitsList)) {
//
//            List<MarketingPluginLabelVO> labelList = getLabelList(suitsList);
//
//            GoodsInfoDetailPluginResponse detailResponse = new GoodsInfoDetailPluginResponse();
//            detailResponse.setMarketingLabels(labelList);
//            log.info(" marketingSuitsService goodsDetail process");
//            return detailResponse;
//        }
//        return null;
//    }
//
//    @Override
//    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
//        return null;
//    }
//
//    @Override
//    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {
//        List<MarketingResponse> marketingList =
//                MarketingContext.getBaseRequest()
//                        .getMultiTypeMarketingMap()
//                        .get(request.getGoodsInfoPluginRequest().getGoodsInfoId())
//                        .stream()
//                        .filter(marketing -> MarketingType.SUITS == marketing.getMarketingType())
//                        .collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(marketingList)) {
//            MarketingPluginSimpleLabelVO label = new MarketingPluginSimpleLabelVO();
//            label.setMarketingType(MarketingPluginType.SUITS.getId());
//            label.setMarketingDesc("组合购");
//            return label;
//        }
//        return null;
//    }
//
//    /**
//     * 获取营销描述<营销编号,描述>
//     *
//     * @param list
//     * @return
//     */
//    private List<MarketingPluginLabelVO> getLabelList(List<MarketingResponse> list) {
//
//        List<MarketingPluginLabelVO> labelList = new ArrayList<>();
//        List<Long> marketingIds =
//                list.stream().map(MarketingResponse::getMarketingId).collect(Collectors.toList());
//        List<MarketingSuits> suitsList =
//                this.marketingSuitsService.list(
//                        MarketingSuitsQueryRequest.builder().marketingIds(marketingIds).build());
//        Map<Long, MarketingSuits> suitsMap = new HashMap<>();
//        if (CollectionUtils.isNotEmpty(suitsList)) {
//            suitsMap =
//                    suitsList.stream()
//                            .collect(Collectors.toMap(MarketingSuits::getMarketingId, x -> x));
//        }
//
//        for (MarketingResponse marketingResponse : list) {
//            MarketingPluginLabelVO label = new MarketingPluginLabelVO();
//            label.setMarketingId(marketingResponse.getMarketingId());
//            label.setMarketingType(MarketingPluginType.SUITS.getId());
//            // 组合购主图
//            label.setLinkId(suitsMap.get(marketingResponse.getMarketingId()).getMainImage());
//            label.setMarketingDesc("组合购：" + marketingResponse.getMarketingName());
//            labelList.add(label);
//        }
//        return labelList;
//    }
//}
