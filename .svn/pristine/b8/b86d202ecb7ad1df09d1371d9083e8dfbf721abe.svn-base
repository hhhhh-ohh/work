package com.wanmi.sbc.marketing.api.provider.newplugin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.newplugin.*;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.info.GoodsInfoListByGoodsInfoResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 *
 * @description
 * @author  zhanggaolei
 * @date 2021/7/26 10:46 上午
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "NewMarketingPluginProvider")
public interface NewMarketingPluginProvider {

    /**
     * 商品详情处理
     * @param request 商品列表处理结构
     * @return {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goodsInfo/detail-plugin")
    BaseResponse<GoodsInfoDetailPluginResponse> goodsInfoDetailPlugin(@RequestBody @Valid GoodsInfoPluginRequest request) ;

    /**
     * 商品列表处理
     * @param request 商品列表处理结构
     * @return {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goodsInfo/list-plugin")
    BaseResponse<GoodsListPluginResponse> goodsListPlugin(@RequestBody @Valid GoodsListPluginRequest request) ;

    /**
     * 购物车商品列表处理
     * @param request 商品列表处理结构
     * @return {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goodsInfo/cart-list-plugin")
    BaseResponse<GoodsCartPluginResponse> cartListPlugin(@RequestBody @Valid GoodsListPluginRequest request);

    /**
     * 刷新缓存
     * @description
     * @author  zhanggaolei
     * @date 2021/7/26 10:47 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.info.GoodsInfoListByGoodsInfoResponse>
     **/
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/flushCache")
    BaseResponse<GoodsInfoListByGoodsInfoResponse> flushCache(@RequestBody @Valid MarketingPluginFlushCacheRequest request);


    /**
     * 商品详情预热处理
     * @param request 商品详情预热处理结构 {@link MarketingPluginGoodsListFilterRequest}
     * @return {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goodsInfo/detail-plugin-pre")
    BaseResponse<GoodsInfoDetailPluginResponse> goodsInfoDetailPrePlugin(@RequestBody @Valid MarketingPluginPreRequest request) ;

    /**
     * @description   查询商品缓存 营销信息 （秒杀 限时购 拼团 预约 预售）
     * @author  wur
     * @date: 2022/3/17 11:57
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goods-list/cache-marketing")
    BaseResponse<GoodsListCacheMarketingResponse> goodsListCacheMarketing(@RequestBody  @Valid GoodsListCacheMarketingRequest request);

    /**
     * 立即购买商品列表处理
     * @param request 商品列表处理结构
     * @return {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goodsInfo/immediate-buy-plugin")
    BaseResponse<GoodsTradePluginResponse> immediateBuyPlugin(@RequestBody @Valid GoodsListPluginRequest request);

    /**
     * 购物车去结算商品列表处理
     * @param request 商品列表处理结构
     * @return {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goodsInfo/confirm-plugin")
    BaseResponse<GoodsTradePluginResponse> confirmPlugin(@RequestBody @Valid GoodsListPluginRequest request);

    /**
     * 订单提交
     * @param request 商品列表处理结构
     * @return {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/new-plugin/goodsInfo/commit-plugin")
    BaseResponse<GoodsTradePluginResponse> commitPlugin(@RequestBody @Valid GoodsListPluginRequest request);

}
