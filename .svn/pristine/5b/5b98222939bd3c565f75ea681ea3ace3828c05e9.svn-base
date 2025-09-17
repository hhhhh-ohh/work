package com.wanmi.sbc.marketing.newplugin.impl;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoByGoodsIdRequest;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @author xuyunpeng
 * @className MarketingPayMemberPlugin
 * @description
 * @date 2022/5/27 3:39 PM
 **/
@Slf4j
@MarketingPluginService(type = MarketingPluginType.BUY_CYCLE)
public class MarketingBuyCyclePlugin implements MarketingPluginInterface {

    @Autowired
    private MarketingMapper marketingMapper;
    
    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        log.debug(" MarketingBuyCyclePlugin goodsDetail process");
        GoodsInfoSimpleVO goodsInfoSimpleVO = request.getGoodsInfoPluginRequest();
        Integer isBuyCycle = goodsInfoSimpleVO.getIsBuyCycle();
        if (Objects.equals(isBuyCycle, Constants.yes)) {
            BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = buyCycleGoodsInfoQueryProvider.getById(BuyCycleGoodsInfoByGoodsIdRequest.builder()
                    .goodsInfoId(goodsInfoSimpleVO.getGoodsInfoId())
                    .build()).getContext().getBuyCycleGoodsInfoVO();
            if (Objects.nonNull(buyCycleGoodsInfoVO)) {
                request.setExcludeMarketingPluginList(Lists.newArrayList(MarketingPluginType.GIFT,MarketingPluginType.BUYOUT_PRICE,
                        MarketingPluginType.HALF_PRICE_SECOND_PIECE,MarketingPluginType.NEW_COMER_COUPON,
                        MarketingPluginType.POINT_AND_CASH, MarketingPluginType.PREFERENTIAL));
                goodsInfoSimpleVO.setMarketPrice(buyCycleGoodsInfoVO.getCyclePrice());
                goodsInfoSimpleVO.setSalePrice(buyCycleGoodsInfoVO.getCyclePrice());
                goodsInfoSimpleVO.setBuyPoint(Constants.NUM_0L);
                MarketingPluginLabelVO label = new MarketingPluginLabelVO();
                GoodsInfoDetailPluginResponse response =
                        new GoodsInfoDetailPluginResponse();
                response.setGoodsInfoId(goodsInfoSimpleVO.getGoodsInfoId());
                response.setGoodsStatus(goodsInfoSimpleVO.getGoodsStatus());
                response.setPluginPrice(buyCycleGoodsInfoVO.getCyclePrice());
                label.setMarketingType(MarketingPluginType.BUY_CYCLE.getId());
                label.setMarketingDesc(MarketingPluginType.BUY_CYCLE.getDescription());
                label.setPluginPrice(buyCycleGoodsInfoVO.getCyclePrice());
                List<MarketingPluginLabelVO> labelList = Lists.newArrayList();
                labelList.add(label);
                response.setMarketingLabels(labelList);
                return response;
            }
        }
        return null;
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {
        log.debug(" MarketingBuyCyclePlugin goodsDetail process");
        GoodsInfoSimpleVO goodsInfoSimpleVO = request.getGoodsInfoPluginRequest();
        Integer isBuyCycle = goodsInfoSimpleVO.getIsBuyCycle();
        if (Objects.equals(isBuyCycle, Constants.yes)) {
                request.setExcludeMarketingPluginList(Lists.newArrayList(MarketingPluginType.GIFT,MarketingPluginType.BUYOUT_PRICE,
                        MarketingPluginType.HALF_PRICE_SECOND_PIECE,MarketingPluginType.NEW_COMER_COUPON,
                        MarketingPluginType.POINT_AND_CASH, MarketingPluginType.PREFERENTIAL));
                MarketingPluginSimpleLabelVO label = new MarketingPluginSimpleLabelVO();
                label.setMarketingType(MarketingPluginType.BUY_CYCLE.getId());
                label.setMarketingDesc(MarketingPluginType.BUY_CYCLE.getDescription());
                label.setPluginPrice(goodsInfoSimpleVO.getMarketPrice());
                return label;
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
}
