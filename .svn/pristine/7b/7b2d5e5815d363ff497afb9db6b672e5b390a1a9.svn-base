package com.wanmi.sbc.marketing.newplugin.impl;

import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginBaseService;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className MarketingPayMemberPlugin
 * @description
 * @date 2022/5/27 3:39 PM
 **/
@Slf4j
@MarketingPluginService(type = MarketingPluginType.PAYING_MEMBER)
public class MarketingPayMemberPlugin implements MarketingPluginInterface {

    @Autowired
    private MarketingPluginBaseService marketingPluginBaseService;

    @Autowired
    private MarketingMapper marketingMapper;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        log.debug(" MarketingPayMemberPlugin goodsDetail process");
        Map<String, GoodsInfoDetailPluginResponse> map =
                marketingPluginBaseService.setPayingMemberPrice(
                        Collections.singletonList(request.getGoodsInfoPluginRequest()));
        if (MapUtils.isNotEmpty(map)) {
            GoodsInfoDetailPluginResponse response =
                    map.get(request.getGoodsInfoPluginRequest().getGoodsInfoId());
            if (response != null) {
                PayingMemberLevelVO payingMemberLevelVO = MarketingContext.getBaseRequest().getPayingMemberLevel();
                MarketingPluginLabelVO label = new MarketingPluginLabelVO();
                label.setMarketingType(MarketingPluginType.PAYING_MEMBER.getId());
                label.setMarketingDesc(payingMemberLevelVO.getLabel());
                label.setPluginPrice(response.getPluginPrice());
                List<MarketingPluginLabelVO> labelList = new ArrayList();
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
        log.debug(" MarketingPayMemberPlugin goodsDetail process");
        PayingMemberLevelVO payingMemberLevel = MarketingContext.getBaseRequest().getPayingMemberLevel();
        if (payingMemberLevel != null && payingMemberLevel.getLevelId() != null) {
            Map<String, GoodsInfoDetailPluginResponse> map =
                    MarketingContext.getBaseRequest()
                            .getCustomerPriceMap()
                            .get(MarketingPluginType.PAYING_MEMBER);
            if (MapUtils.isNotEmpty(map)) {
                GoodsInfoDetailPluginResponse response =
                        map.get(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (response != null) {
                    PayingMemberLevelVO payingMemberLevelVO = MarketingContext.getBaseRequest().getPayingMemberLevel();
                    MarketingPluginSimpleLabelVO label = new MarketingPluginSimpleLabelVO();
                    label.setMarketingType(MarketingPluginType.PAYING_MEMBER.getId());
                    label.setMarketingDesc(payingMemberLevelVO.getLabel());
                    label.setPluginPrice(response.getPluginPrice());
                    return label;
                }
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
}
