package com.wanmi.sbc.marketing.newplugin.impl;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsListRequest;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 秒杀插件
 *
 * @author zhanggaolei
 * @className MarketingFlashSalePlugin
 * @description
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.FLASH_SALE)
public class MarketingFlashSalePlugin implements MarketingPluginInterface {

    @Autowired private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired private MarketingMapper marketingMapper;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {

        GoodsInfoDetailPluginResponse detailResponse =
                getMarketingLabel(request.getGoodsInfoPluginRequest());

        log.debug(" MarketingFlashSalePlugin goodsDetail process");
        return detailResponse;
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {

        // 含购买积分的商品不允许参与秒杀

        Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>> map =
                MarketingContext.getBaseRequest()
                        .getSkuMarketingMap()
                        .get(request.getGoodsInfoPluginRequest().getGoodsInfoId());
        if (MapUtils.isNotEmpty(map)) {

            List<GoodsInfoMarketingCacheDTO> flashSaleList =
                    map.get(MarketingPluginType.FLASH_SALE);
            if (CollectionUtils.isNotEmpty(flashSaleList)) {
                GoodsInfoMarketingCacheDTO cacheDTO = flashSaleList.get(0);

                MarketingPluginSimpleLabelVO labelVO = new MarketingPluginSimpleLabelVO();
                labelVO.setMarketingId(cacheDTO.getId());
                labelVO.setMarketingType(MarketingPluginType.FLASH_SALE.getId());
                labelVO.setMarketingDesc("秒杀中");
                labelVO.setPluginPrice(cacheDTO.getPrice());
                return labelVO;
            }
        }
        return null;
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {
        return marketingMapper.simpleLabelVOToLabelDetailVO(this.check(request));
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return marketingMapper.simpleLabelVOToLabelDetailVO(this.check(request));
    }

    private GoodsInfoDetailPluginResponse getMarketingLabel(GoodsInfoSimpleVO request) {
        Map<String, GoodsInfoDetailPluginResponse> retMap =
                getMarketingLabel(Collections.singletonList(request));
        if (MapUtils.isNotEmpty(retMap)) {
            return retMap.get(request.getGoodsInfoId());
        }
        return null;
    }

    private Map<String, GoodsInfoDetailPluginResponse> getMarketingLabel(
            List<GoodsInfoSimpleVO> requestList) {
        Map<String, GoodsInfoDetailPluginResponse> retMap = new HashMap<>();
        // 含购买积分的商品不允许参与秒杀
        List<GoodsInfoSimpleVO> tempGoodsInfos = requestList;
        if (CollectionUtils.isEmpty(tempGoodsInfos)) {
            return null;
        }

        List<Long> ids = new ArrayList<>();
        for (GoodsInfoSimpleVO request : requestList) {
            if (MarketingContext.isNotNullSkuMarketingMap(request.getGoodsInfoId())
                    && MarketingContext.getBaseRequest()
                                    .getSkuMarketingMap()
                                    .get(request.getGoodsInfoId())
                                    .get(MarketingPluginType.FLASH_SALE)
                            != null) {
                ids =
                        MarketingContext.getBaseRequest()
                                .getSkuMarketingMap()
                                .get(request.getGoodsInfoId())
                                .get(MarketingPluginType.FLASH_SALE)
                                .stream()
                                .map(GoodsInfoMarketingCacheDTO::getId)
                                .map(l -> Long.valueOf(String.valueOf(l)))
                                .collect(Collectors.toList());
            }
        }
        if (CollectionUtils.isNotEmpty(ids)) {
            List<FlashSaleGoodsSimpleVO> listResponse =
                    flashSaleGoodsQueryProvider
                            .querySimpleByIds(
                                    FlashSaleGoodsListRequest.builder().idList(ids).build())
                            .getContext();
            Map<String, FlashSaleGoodsSimpleVO> goodsVOMap =
                    listResponse.stream()
                            .collect(
                                    Collectors.toMap(
                                            FlashSaleGoodsSimpleVO::getGoodsInfoId,
                                            Function.identity()));

            tempGoodsInfos.forEach(
                    goodsInfoVO -> {
                        if (Objects.nonNull(goodsVOMap)
                                && Objects.nonNull(goodsVOMap.get(goodsInfoVO.getGoodsInfoId()))) {
                            FlashSaleGoodsSimpleVO vo =
                                    goodsVOMap.get(goodsInfoVO.getGoodsInfoId());
                            // 最小起订量大于库存量时认为活动结束
                            if (vo.getStock() >= vo.getMinNum()) {
                                GoodsInfoDetailPluginResponse response =
                                        new GoodsInfoDetailPluginResponse();
                                MarketingPluginLabelVO pluginLabelVO = new MarketingPluginLabelVO();
                                pluginLabelVO.setMarketingType(
                                        MarketingPluginType.FLASH_SALE.getId());
                                pluginLabelVO.setMarketingDesc("秒杀");
                                pluginLabelVO.setStartTime(vo.getActivityFullTime());

                                pluginLabelVO.setEndTime(
                                        vo.getActivityFullTime()
                                                .plusHours(Constants.FLASH_SALE_LAST_HOUR));

                                pluginLabelVO.setMarketingStatus(
                                        getActivityStatus(
                                                        pluginLabelVO.getStartTime(),
                                                        pluginLabelVO.getEndTime())
                                                .toValue());
                                pluginLabelVO.setPluginPrice(vo.getPrice());
                                if (vo.getStock() > 0) {
                                    pluginLabelVO.setProgressRatio(
                                            BigDecimal.valueOf(
                                                            vo.getSalesVolume() == null
                                                                    ? 0
                                                                    : vo.getSalesVolume())
                                                    .divide(
                                                            BigDecimal.valueOf(vo.getStock()),
                                                            2,
                                                            RoundingMode.HALF_UP)
                                                    .multiply(BigDecimal.valueOf(100)));
                                }
                                response.setPluginPrice(vo.getPrice());
                                response.setMarketingLabels(
                                        Collections.singletonList(pluginLabelVO));
                                retMap.put(goodsInfoVO.getGoodsInfoId(), response);
                            }
                        }
                    });
        }
        return retMap;
    }

    private MarketingStatus getActivityStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            return MarketingStatus.NOT_START;
        }
        if (now.isEqual(startTime) || now.isEqual(endTime) || now.isBefore(endTime)) {
            return MarketingStatus.STARTED;
        }
        return MarketingStatus.ENDED;
    }
}
