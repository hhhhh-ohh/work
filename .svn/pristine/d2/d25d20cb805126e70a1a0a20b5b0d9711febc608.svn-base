package com.wanmi.sbc.vas.channel.goods.service;

import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import com.wanmi.sbc.vas.channel.base.ChannelBaseService;

import java.util.List;

/**
 * @author EDZ
 * @className ChannelSyncGoodsTempService
 * @description 同步商品数据
 * @date 2021/5/12 18:42
 */
public interface ChannelSyncGoodsService extends ChannelBaseService {

    /***
     *  同步商品通知 发送一个通知，通知同步渠道商品到本地临时表
     * @param channelGoodsSyncBySkuVasRequest 商品同步通知请求对象
     */
    void syncGoodsNotice(ChannelGoodsSyncVasRequest channelGoodsSyncBySkuVasRequest);

    /***
     * 初始化同步SPU
     */
    void initSyncSpu();

    /***
     * 同步指定SKU数据
     * @param channelGoodsSyncBySkuVasRequest
     */
    void syncSkuList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest);

    /***
     * 同步指定SKU数据
     * @param request
     */
    List<String> syncSpuList(ChannelGoodsSyncBySpuVasRequest request);

    /***
     * 初始化同步SPU（新）
     */
    void initSyncSpuNew();

    /***
     * 同步指定SKU价格数据
     * @param channelGoodsSyncBySkuVasRequest
     */
    void syncSkuPriceList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest);
}
