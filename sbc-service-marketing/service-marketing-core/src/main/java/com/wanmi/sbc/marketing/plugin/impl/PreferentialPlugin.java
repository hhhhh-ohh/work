package com.wanmi.sbc.marketing.plugin.impl;

import com.wanmi.sbc.goods.api.response.info.GoodsInfoDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.MarketingLabelVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.common.request.TradeMarketingPluginRequest;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.common.response.TradeMarketingResponse;
import com.wanmi.sbc.marketing.plugin.IGoodsDetailPlugin;
import com.wanmi.sbc.marketing.plugin.IGoodsListPlugin;
import com.wanmi.sbc.marketing.plugin.ITradeCommitPlugin;
import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialLevel;
import com.wanmi.sbc.marketing.preferential.repository.MarketingPreferentialLevelRepository;
import com.wanmi.sbc.marketing.request.MarketingPluginRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/** 营销加价购插件 Created by dyt on 2016/12/8. */
@Repository("preferential")
public class PreferentialPlugin
        implements IGoodsListPlugin, IGoodsDetailPlugin, ITradeCommitPlugin {

    @Autowired
    private MarketingPreferentialLevelRepository marketingPreferentialLevelRepository;

    /**
     * 商品列表处理
     *
     * @param goodsInfos 商品数据
     * @param request    参数
     */
    @Override
    public void goodsListFilter(List<GoodsInfoVO> goodsInfos, MarketingPluginRequest request) {
        if (request.getCommitFlag()){
            return;
        }
        List<MarketingResponse> marketingList = request.getMarketingMap().values().stream()
                .flatMap(Collection::stream)
                .filter(marketing -> MarketingType.PREFERENTIAL == marketing.getMarketingType())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(marketingList)) {
            return;
        }

        Map<Long, MarketingResponse> params = new HashMap<>();
        marketingList.forEach(marketingResponse -> params.put(marketingResponse.getMarketingId(), marketingResponse));
        List<Long> marketingIds = marketingList.stream().map(MarketingResponse::getMarketingId).collect(Collectors.toList());
        //填充营销描述<营销编号,描述>
        Map<Long, String> labelMap = this.getLabelMap(marketingIds, params);

        goodsInfos.stream()
                .filter(goodsInfo -> request.getMarketingMap().containsKey(goodsInfo.getGoodsInfoId()))
                .forEach(goodsInfo -> {
                    request.getMarketingMap().get(goodsInfo.getGoodsInfoId()).stream()
                            .filter(marketing -> MarketingType.PREFERENTIAL == marketing.getMarketingType())
                            .forEach(marketing -> {
                                MarketingLabelVO label = new MarketingLabelVO();
                                label.setMarketingId(marketing.getMarketingId());
                                label.setMarketingType(marketing.getMarketingType().toValue());
                                label.setMarketingDesc(labelMap.get(marketing.getMarketingId()));
                                goodsInfo.getMarketingLabels().add(label);
                            });
                });
    }

    /**
     * 商品详情处理
     *
     * @param detailResponse 商品详情数据
     * @param request        参数
     */
    @Override
    public void goodsDetailFilter(GoodsInfoDetailByGoodsInfoResponse detailResponse, MarketingPluginRequest request) {
        // 加价购迭代遗漏，暂未测试其他使用此方法场景，所以暂不实现
    }

    @Override
    public TradeMarketingResponse wraperMarketingFullInfo(TradeMarketingPluginRequest marketingRequest) {
        // 加价购迭代遗漏，暂未测试其他使用此方法场景，所以暂不实现
        TradeMarketingResponse response = new TradeMarketingResponse();
        return response;
    }

    /**
     * 获取营销描述<营销编号,描述>
     *
     * @param marketingIds 营销编号
     * @return
     */
    private Map<Long, String> getLabelMap(List<Long> marketingIds, Map<Long, MarketingResponse> marketingMap) {
        Map<Long, List<MarketingPreferentialLevel>> levelsMap = marketingPreferentialLevelRepository.findAll((root, cquery, cbuild) -> root.get("marketingId").in(marketingIds), Sort.by(Arrays.asList(new Sort.Order(Sort.Direction.ASC, "fullAmount"), new Sort.Order(Sort.Direction.ASC, "fullCount"))))
                .stream().collect(Collectors.groupingBy(MarketingPreferentialLevel::getMarketingId));
        Map<Long, String> labelMap = new HashMap<>();
        DecimalFormat fmt = new DecimalFormat("#.##");
        levelsMap.forEach((marketingId, levels) -> {
            if (MarketingSubType.GIFT_FULL_COUNT == marketingMap.get(marketingId).getSubType()) {
                List<String> count = levels.stream().filter(level -> Objects.nonNull(level.getFullCount())).map(level -> ObjectUtils.toString(level.getFullCount()).concat("件")).collect(Collectors.toList());
                labelMap.put(marketingId, String.format("满%s享超值换购", StringUtils.join(count, "，")));
            } else {
                List<String> amount = levels.stream().filter(level -> Objects.nonNull(level.getFullAmount())).map(level -> fmt.format(level.getFullAmount()).concat("元")).collect(Collectors.toList());
                labelMap.put(marketingId, String.format("满%s享超值换购", StringUtils.join(amount, "，")));
            }
        });
        return labelMap;
    }
}