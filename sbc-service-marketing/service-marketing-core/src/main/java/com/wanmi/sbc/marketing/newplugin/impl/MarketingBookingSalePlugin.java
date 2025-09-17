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
import com.wanmi.sbc.marketing.newplugin.common.MarketingPluginLabelBuild;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 预售插件
 *
 * @author zhanggaolei
 * @className MarketingBookingSalePlugin
 * @description
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.BOOKING_SALE)
public class MarketingBookingSalePlugin implements MarketingPluginInterface {


    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {

        return MarketingPluginLabelBuild.getDetailResponse(
                this.setLabel(request, MarketingPluginLabelVO.class));
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {

        return this.setLabel(request, MarketingPluginSimpleLabelVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request, MarketingPluginLabelDetailVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request, MarketingPluginLabelDetailVO.class);
    }

    private <T extends MarketingPluginSimpleLabelVO> T setLabel(
            GoodsInfoPluginRequest request, Class<T> c) {
        if (MarketingContext.isNotNullSkuMarketingMap(
                request.getGoodsInfoPluginRequest().getGoodsInfoId())) {
            List<GoodsInfoMarketingCacheDTO> cacheList =
                    MarketingContext.getBaseRequest()
                            .getSkuMarketingMap()
                            .get(request.getGoodsInfoPluginRequest().getGoodsInfoId())
                            .get(MarketingPluginType.BOOKING_SALE);
            if (CollectionUtils.isNotEmpty(cacheList)) {
                GoodsInfoMarketingCacheDTO cacheDTO = cacheList.get(0);
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.BOOKING_SALE.getId());
                label.setMarketingDesc("预售中");
                label.setPluginPrice(cacheDTO.getPrice());
                label.setMarketingId(cacheDTO.getId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                label.setStartTime(cacheDTO.getBeginTime());
                label.setEndTime(cacheDTO.getEndTime());

                return (T) label;
            }
        }
        return null;
    }
}
