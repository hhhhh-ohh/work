package com.wanmi.sbc.empower.channel.goods;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncByIdsRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsUpdateSyncStatusRequest;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelGoodsSyncQueryResponse;
import com.wanmi.sbc.empower.channel.base.ChannelBaseService;

import java.util.List;

/**
 * @author zhengyang
 * @className ChannelGoodsSyncService
 * @description 渠道商品初始化接口
 * @date 2021/5/23 14:38
 **/
public interface ChannelGoodsSyncService extends ChannelBaseService {

    /****
     * 同步商品通知
     * 发送一个通知，通知同步渠道商品到本地临时表
     * @param channelGoodsSyncRequest   商品同步通知请求对象
     */
    void syncGoodsNotice(ChannelGoodsSyncRequest channelGoodsSyncRequest);

    /***
     * 分页查询同步商品
     * @param goodsSyncQueryRequest 渠道商品查询请求
     * @return
     */
    MicroServicePage<ChannelGoodsSyncQueryResponse> syncQueryPage(ChannelGoodsSyncQueryRequest goodsSyncQueryRequest);

    /***
     * 保存京东SPU到临时表，并返回保存的对象
     * @param channelGoodsSyncBySkuIdsRequest   同步-查询请求对象
     * @return                                  SKUID/SPUID对应的对象
     */
    List<ChannelGoodsSyncQueryResponse> syncGetChannelGoods(ChannelGoodsSyncByIdsRequest channelGoodsSyncBySkuIdsRequest);

    /***
     * 分页查询同步商品
     * @param goodsSyncQueryRequest 渠道商品查询请求
     * @return
     */
    MicroServicePage<ChannelGoodsSyncQueryResponse> syncQueryPageNew(ChannelGoodsSyncQueryRequest goodsSyncQueryRequest);

    Long dataCount();

    /**
     * 修改临时表商品同步状态
     * @param updateSyncStatusRequest
     * @return
     */
    void updateSyncStatus(ChannelGoodsUpdateSyncStatusRequest updateSyncStatusRequest);
}
