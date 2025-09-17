package com.wanmi.sbc.marketing.newplugin.impl;

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

import java.util.*;

/**
 * 会员价插件
 *
 * @author zhanggaolei
 * @className MarketingCustomPricePlugin
 * @description TODO
 * @date 2021/7/2 16:53
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.CUSTOMER_PRICE)
public class MarketingCustomPricePlugin implements MarketingPluginInterface {

    @Autowired private MarketingPluginBaseService marketingPluginBaseService;

    @Autowired private MarketingMapper marketingMapper;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        log.debug(" MarketingCustomPricePlugin goodsDetail process");
        Map<String, GoodsInfoDetailPluginResponse> map =
                marketingPluginBaseService.setPrice(
                        Collections.singletonList(request.getGoodsInfoPluginRequest()),
                        request.getCustomerId());
        if (MapUtils.isNotEmpty(map)) {
            GoodsInfoDetailPluginResponse response =
                    map.get(request.getGoodsInfoPluginRequest().getGoodsInfoId());
            if (response != null) {
                MarketingPluginLabelVO label = new MarketingPluginLabelVO();
                label.setMarketingType(MarketingPluginType.CUSTOMER_PRICE.getId());
                label.setMarketingDesc("粉丝价");
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
        log.debug(" MarketingCustomPricePlugin goodsDetail process");
        if (request.getCustomerId() != null
                && MapUtils.isNotEmpty(MarketingContext.getBaseRequest().getCustomerPriceMap())) {
            Map<String, GoodsInfoDetailPluginResponse> map =
                    MarketingContext.getBaseRequest()
                            .getCustomerPriceMap()
                            .get(MarketingPluginType.CUSTOMER_PRICE);
            if (MapUtils.isNotEmpty(map)) {
                GoodsInfoDetailPluginResponse response =
                        map.get(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (response != null) {
                    MarketingPluginSimpleLabelVO label = new MarketingPluginSimpleLabelVO();
                    label.setMarketingType(MarketingPluginType.CUSTOMER_PRICE.getId());
                    label.setMarketingDesc("粉丝价");
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
