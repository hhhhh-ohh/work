package com.wanmi.sbc.marketing.newplugin;

import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;

/**
 * @author zhanggaolei
 * @description
 * @date 2021/5/19 10:41
 **/
public interface MarketingPlugin {

    /**
     * 商品详情的营销插件
     * @author  zhanggaolei
     * @date 2021/6/29 9:48
     * @param request
     * @return T
     **/
    <T> T goodsDetail( GoodsInfoPluginRequest request);

    /**
     * 商品列表营销插件
     * @description
     * @author  zhanggaolei
     * @date 2021/8/12 8:40 下午
     * @param request
     * @return T
     **/
    <T> T goodsList(GoodsListPluginRequest request);

    /**
     * 校验sku存在的营销活动
     * @description
     * @author  zhanggaolei
     * @date 2021/8/13 10:14 上午
     * @param request
     * @return MarketingPluginSimpleLabelVO
     **/
    <T> T check(GoodsInfoPluginRequest request);

    /**
     * 购物车
     * @param request
     * @param <T>
     * @return
     */
    <T> T cartMarketing(GoodsInfoPluginRequest request);

    /**
     * 订单
     * @param request
     * @param <T>
     * @return
     */
    <T> T tradeMarketing(GoodsInfoPluginRequest request);
}
