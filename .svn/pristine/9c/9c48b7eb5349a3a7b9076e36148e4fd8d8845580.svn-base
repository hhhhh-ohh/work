package com.wanmi.sbc.marketing.newplugin;

import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;

/**
 * @author zhanggaolei
 * @className MarketingPluginAbstract
 * @description
 * @date 2021/6/28 10:58
 **/
public interface MarketingPluginInterface extends MarketingPlugin {

    /**
     * 重写方法，将 <T>泛型类型转为具体的返回值，所有新增的插件服务都需要实现该接口
     * @author  zhanggaolei
     * @date 2021/6/29 9:55
     * @param request
     * @return GoodsInfoDetailPluginResponse
     **/
    @Override
    GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request);

    /**
     * 重写方法，将 <T>泛型类型转为具体的返回值，所有新增的插件服务都需要实现该接口
     * @description
     * @author  zhanggaolei
     * @date 2021/8/12 8:29 下午
     * @param request
     * @return com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse
     **/
    @Override
    GoodsListPluginResponse goodsList(GoodsListPluginRequest request);


    /**
     *
     * @param request
     * @return
     */
    @Override
    MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request);

    /**
     * 购物车
     * @param request
     * @return
     */
    @Override
    MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request);

    /**
     * 订单
     * @param request
     * @return
     */
    @Override
    MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request);
}
