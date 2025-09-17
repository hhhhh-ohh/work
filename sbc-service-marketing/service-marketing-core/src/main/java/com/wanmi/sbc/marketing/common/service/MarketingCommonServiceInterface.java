package com.wanmi.sbc.marketing.common.service;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.api.request.market.InfoForPurchseRequest;
import com.wanmi.sbc.marketing.api.response.market.MarketInfoForPurchaseResponse;

/***
 * O2O营销通用Service
 * @className MarketingCommonServiceInterface
 * @author zhengyang
 * @date 2021/10/25 14:03
 **/
public interface MarketingCommonServiceInterface {

    /***
     * 查询购物车使用的营销信息
     * @param request       查询请求
     * @param pluginType    插件类型，路由用
     * @return
     */
    MarketInfoForPurchaseResponse queryInfoForPurchase(InfoForPurchseRequest request, PluginType pluginType);
}
