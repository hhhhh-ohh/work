package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service;

import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;

import java.util.Map;

public interface TradeBuyMarketingInterface {

    /*
     * @description TradeBuyService中MULTI_TYPE_MAP定义的活动类型处理
     * @author  edz
     * @date: 2022/4/6 16:27
     * @param skuId
     * @param marketingPluginLabelDetailVO
     * @param skuTradeItemDTOMap
     * @param marketingPluginType
     * @param countPriceMarketingDTO
     * @return boolean
     **/
    boolean multiTypeMapHandle(
            String skuId,
            MarketingPluginLabelDetailVO marketingPluginLabelDetailVO,
            Map<String, TradeItemDTO> skuTradeItemDTOMap,
            MarketingPluginType marketingPluginType,
            Map<String, CountPriceMarketingDTO> marketingIdCountPriceMarketingDTOMap,
            Long assignMarketingId);

    /*
     * @description 预售
     * @author  edz
     * @date: 2022/4/6 16:35
     * @param paramsDataVO
     * @param skuId
     * @param skuTradeItemDTOMap
     * @param marketingPluginLabelDetailVO
     * @return void
     **/
    void bookingSaleHandle(
            ParamsDataVO paramsDataVO,
            String skuId,
            Map<String, TradeItemDTO> skuTradeItemDTOMap,
            MarketingPluginLabelDetailVO marketingPluginLabelDetailVO);

    /*
     * @description 除去multiTypeMapHandle和bookingSaleHandle以外的活动处理
     * @author  edz
     * @date: 2022/4/6 16:35
     * @param skuId
     * @param skuTradeItemDTOMap
     * @param marketingPluginLabelDetailVO
     * @param marketingPluginType
     * @return void
     **/
    void otherMarketingHandle(String skuId,
                              Map<String, TradeItemDTO> skuTradeItemDTOMap,
                              MarketingPluginLabelDetailVO marketingPluginLabelDetailVO,
                              MarketingPluginType marketingPluginType
    );

    /*
     * @description 营销活动最后的处理
     * @author  edz
     * @date: 2022/4/6 16:36
     * @param paramsDataVO
     * @param skuTradeItemDTOMap
     * @param countPriceMarketingDTO
     * @return void
     **/
    void marketingHandle(
            ParamsDataVO paramsDataVO,
            Map<String, TradeItemDTO> skuTradeItemDTOMap,
            Map<String, CountPriceMarketingDTO> marketingIdCountPriceMarketingDTOMap);
}
