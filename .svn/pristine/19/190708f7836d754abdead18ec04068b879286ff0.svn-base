package com.wanmi.sbc.marketing.plugin.impl;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.MarketingLabelVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnLevelVO;
import com.wanmi.sbc.marketing.common.model.entity.TradeMarketing;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.repository.MarketingRepository;
import com.wanmi.sbc.marketing.common.request.TradeItemInfo;
import com.wanmi.sbc.marketing.common.request.TradeMarketingPluginRequest;
import com.wanmi.sbc.marketing.common.request.TradeMarketingRequest;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.common.response.TradeMarketingResponse;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnLevel;
import com.wanmi.sbc.marketing.fullreturn.repository.MarketingFullReturnLevelRepository;
import com.wanmi.sbc.marketing.plugin.IGoodsDetailPlugin;
import com.wanmi.sbc.marketing.plugin.IGoodsListPlugin;
import com.wanmi.sbc.marketing.plugin.ITradeCommitPlugin;
import com.wanmi.sbc.marketing.request.MarketingPluginRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 营销满返插件
 * Created by xufeng on 2022/5/10.
 */
@Repository("fullReturnPlugin")
public class FullReturnPlugin implements IGoodsListPlugin, IGoodsDetailPlugin, ITradeCommitPlugin {


    @Autowired
    private MarketingFullReturnLevelRepository marketingFullReturnLevelRepository;

    @Autowired
    private MarketingRepository marketingRepository;

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
                .filter(marketing -> MarketingType.RETURN == marketing.getMarketingType())
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
                            .filter(marketing -> MarketingType.RETURN == marketing.getMarketingType())
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
        List<MarketingResponse> marketingList = request.getMarketingMap().get(detailResponse.getGoodsInfo().getGoodsInfoId());

        if (CollectionUtils.isEmpty(marketingList)
                || marketingList.stream().filter(marketing -> MarketingType.RETURN == marketing.getMarketingType()).count() < 1) {
            return;
        }

        MarketingResponse marketingObj = marketingList.stream().filter(marketing ->
                MarketingType.RETURN == marketing.getMarketingType()).findFirst().orElse(null);
        //填充营销描述<营销编号,描述>
        if (Objects.nonNull(marketingObj)) {
            Map<Long, MarketingResponse> params = new HashMap<>();
            params.put(marketingObj.getMarketingId(), marketingObj);
            Map<Long, String> labelMap = this.getLabelMap(Collections.singletonList(marketingObj.getMarketingId()), params);
            MarketingLabelVO label = new MarketingLabelVO();
            label.setMarketingId(marketingObj.getMarketingId());
            label.setMarketingType(marketingObj.getMarketingType().toValue());
            label.setMarketingDesc(labelMap.get(marketingObj.getMarketingId()));
            detailResponse.getGoodsInfo().getMarketingLabels().add(label);
        }
    }

    @Override
    public TradeMarketingResponse wraperMarketingFullInfo(TradeMarketingPluginRequest marketingRequest) {
        TradeMarketingRequest tradeMarketingDTO = marketingRequest.getTradeMarketingDTO();
        if (tradeMarketingDTO == null) {
            return null;
        }
        Marketing marketing = marketingRepository.findById(tradeMarketingDTO.getMarketingId()).orElse(null);
        if (marketing.getMarketingType() != MarketingType.RETURN) {
            return null;
        }
        List<TradeItemInfo> tradeItems = marketingRequest.getTradeItems();
        // 校验营销关联商品中，是否存在分销商品
        List<String> distriSkuIds = tradeItems.stream()
                .filter(item -> item.getDistributionGoodsAudit() == DistributionGoodsAudit.CHECKED)
                .map(TradeItemInfo::getSkuId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(distriSkuIds)) {
            tradeMarketingDTO.getSkuIds().forEach(skuId -> {
                if (distriSkuIds.contains(skuId)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
        }

        MarketingFullReturnLevel level =
                marketingFullReturnLevelRepository.findById(tradeMarketingDTO.getMarketingLevelId())
                .orElseThrow(() -> new SbcRuntimeException(OrderErrorCodeEnum.K050084));
        if(!level.getMarketingId().equals(tradeMarketingDTO.getMarketingId())){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }
        BigDecimal price = tradeItems.stream().map(i -> i.getPrice().multiply(new BigDecimal(i.getNum()))).reduce(
                BigDecimal.ZERO, BigDecimal::add);

        TradeMarketingResponse response = new TradeMarketingResponse();
        response.setTradeMarketing(TradeMarketing.builder()
                .fullReturnLevel(KsBeanUtil.convert(level, MarketingFullReturnLevelVO.class))
                .discountsAmount(BigDecimal.ZERO)
                .realPayAmount(price)
                .marketingId(marketing.getMarketingId())
                .marketingName(marketing.getMarketingName())
                .marketingType(marketing.getMarketingType())
                .skuIds(tradeMarketingDTO.getSkuIds())
                .subType(marketing.getSubType())
                .build());
        return response;
    }

    /**
     * 获取营销描述<营销编号,描述>
     *
     * @param marketingIds 营销编号
     * @return
     */
    private Map<Long, String> getLabelMap(List<Long> marketingIds, Map<Long, MarketingResponse> marketingMap) {
        Map<Long, List<MarketingFullReturnLevel>> levelsMap = marketingFullReturnLevelRepository.findAll((root, cquery,
                                                                                                   cbuild) -> root.get("marketingId").in(marketingIds))
                .stream().collect(Collectors.groupingBy(MarketingFullReturnLevel::getMarketingId));
        Map<Long, String> labelMap = new HashMap<>();
        DecimalFormat fmt = new DecimalFormat("#.##");
        levelsMap.forEach((marketingId, levels) -> {
            List<String> amount = levels.stream().filter(level -> Objects.nonNull(level.getFullAmount())).map(level -> fmt.format(level.getFullAmount()).concat("元")).collect(Collectors.toList());
            labelMap.put(marketingId, String.format("满%s获优惠券，赠完为止", StringUtils.join(amount, "，")));
        });
        return labelMap;
    }

}