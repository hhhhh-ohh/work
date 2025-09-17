package com.wanmi.sbc.marketing.newplugin.common;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * @author zhanggaolei
 * @className MarketingResponseProcess
 * @description
 * @date 2021/7/1 17:52
 */
public class MarketingResponseProcess {

    public static void setGoodsDetailResponseByLabel(GoodsInfoDetailPluginResponse response) {
        if (MarketingContext.getResponse() != null) {
            GoodsInfoDetailPluginResponse detailResponse = MarketingContext.getResponse();
            if (CollectionUtils.isNotEmpty(detailResponse.getMarketingLabels())) {
                detailResponse.getMarketingLabels().addAll(detailResponse.getMarketingLabels());
            } else {

                detailResponse.setMarketingLabels(detailResponse.getMarketingLabels());
            }
        } else {
            MarketingContext.setResponse(response);
        }
    }

    public static void setGoodsDetailDefaultResponse(GoodsInfoSimpleVO request) {
        GoodsInfoDetailPluginResponse detailResponse = new GoodsInfoDetailPluginResponse();
        detailResponse.setGoodsInfoId(request.getGoodsInfoId());
        detailResponse.setGoodsStatus(request.getGoodsStatus());
        if (request.getSalePrice() != null) {
            detailResponse.setPluginPrice(request.getSalePrice());
        } else {
            detailResponse.setPluginPrice(request.getMarketPrice());
        }

        List<MarketingPluginLabelVO> marketingLabels = new ArrayList<>();
        detailResponse.setMarketingLabels(marketingLabels);
        MarketingContext.setResponse(detailResponse);
    }

    public static void setGoodsDetailResponseNotNUll(GoodsInfoDetailPluginResponse response) {
        setGoodsDetailResponseNotNUll(response, null);
    }

    public static void setGoodsDetailResponseNotNUll(GoodsInfoDetailPluginResponse response, MarketingPluginType removeType) {
        GoodsInfoDetailPluginResponse currentResponse = MarketingContext.getResponse();
        if (response.getPluginPrice() != null) {
            currentResponse.setPluginPrice(response.getPluginPrice());
        }
        if(CollectionUtils.isNotEmpty(response.getMarketingLabels())){
            if (removeType != null) {
                currentResponse.getMarketingLabels().removeIf(label -> removeType.getId() == label.getMarketingType());
            }
            currentResponse.getMarketingLabels().addAll(response.getMarketingLabels());
        }
        if(response.getGoodsStatus() != null){
            currentResponse.setGoodsStatus(response.getGoodsStatus());
        }
        MarketingContext.setResponse(currentResponse);

    }

    public static void setGoodsListDefaultResponse(GoodsListPluginRequest request) {
        GoodsListPluginResponse response = new GoodsListPluginResponse();
        Map<String,List<MarketingPluginSimpleLabelVO>> map = new HashMap<>(8);
        response.setSkuMarketingLabelMap(map);
        MarketingContext.setResponse(response);
    }

    public static void setMarketingLabel(MarketingPluginSimpleLabelVO label){
        if(label.getMarketingDesc()==null && label.getMarketingType()==null&&label.getPluginPrice()==null){
            return;
        }
        List<MarketingPluginSimpleLabelVO> labelVOList = MarketingContext.getResponse();
        if (labelVOList == null) {
            labelVOList = new ArrayList<>();
        }
        if(label != null){
            labelVOList.add(label);
        }
        MarketingContext.setResponse(labelVOList);
    }

    public static void removeMarketingLabel(MarketingPluginType pluginType) {
        if (pluginType == null) {
            return;
        }
        List<MarketingPluginSimpleLabelVO> labelVOList = MarketingContext.getResponse();
        if (labelVOList != null) {
            labelVOList.removeIf(label -> pluginType.getId() == label.getMarketingType());
        }
    }

    public static MarketingPluginSimpleLabelVO getMarketingLabel(MarketingPluginType pluginType) {
        if (pluginType == null) {
            return null;
        }
        List<MarketingPluginSimpleLabelVO> labelVOList = MarketingContext.getResponse();
        if (CollectionUtils.isNotEmpty(labelVOList)) {
            Optional<MarketingPluginSimpleLabelVO> optional = labelVOList.stream()
                    .filter(label -> pluginType.getId() == label.getMarketingType()).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}
