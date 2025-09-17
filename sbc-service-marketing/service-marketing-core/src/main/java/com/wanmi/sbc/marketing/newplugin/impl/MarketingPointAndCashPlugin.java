package com.wanmi.sbc.marketing.newplugin.impl;

import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 预售插件
 *
 * @author zhanggaolei
 * @className MarketingPointAndCashPlugin
 * @description TODO
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.POINT_AND_CASH)
public class MarketingPointAndCashPlugin implements MarketingPluginInterface {

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {

        if (request.getGoodsInfoPluginRequest().getBuyPoint() != null
                && 0 < request.getGoodsInfoPluginRequest().getBuyPoint()) {

            log.debug(" MarketingPointAndCashPlugin goodsDetail process");
            // 返回一个空实体
            return new GoodsInfoDetailPluginResponse();
        }
        return null;
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {

        if (request.getGoodsInfoPluginRequest().getBuyPoint() != null
                && 0 < request.getGoodsInfoPluginRequest().getBuyPoint()) {

            log.debug(" MarketingPointAndCashPlugin check process");
            // 返回一个空实体
            return new MarketingPluginSimpleLabelVO();
        }
        return null;
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {
        if (request.getGoodsInfoPluginRequest().getBuyPoint() != null
                && 0 < request.getGoodsInfoPluginRequest().getBuyPoint()) {

            log.debug(" MarketingPointAndCashPlugin cartMarketing process");
            // 返回一个空实体
            return new MarketingPluginLabelDetailVO();
        }
        return null;
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        if (request.getGoodsInfoPluginRequest().getBuyPoint() != null
                && 0 < request.getGoodsInfoPluginRequest().getBuyPoint()) {

            log.debug(" MarketingPointAndCashPlugin tradeMarketing process");
            // 返回一个空实体
            return new MarketingPluginLabelDetailVO();
        }
        return null;
    }
}
