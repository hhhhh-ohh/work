package com.wanmi.sbc.empower.api.provider.channel.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncByIdsRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsUpdateSyncStatusRequest;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelGoodsSyncQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author zhengyang
 * @description 渠道商品同步服务Provider
 * @date 2021/5/23 15:58
 */
@FeignClient(value = "${application.empower.name}", contextId = "ChannelGoodsSyncProvider")
public interface ChannelGoodsSyncProvider {

    /****
     * 同步商品通知
     * 发送一个通知，通知同步渠道商品到本地临时表
     * @param channelGoodsSyncRequest   商品同步通知请求对象
     * @return                          是否成功
     */
    @PostMapping("/empower/${application.empower.version}/sync/goods/sync-goods-notice")
    BaseResponse syncGoodsNotice(@RequestBody @Valid ChannelGoodsSyncRequest channelGoodsSyncRequest);

    /***
     * 分页查询同步商品
     * @param goodsSyncQueryRequest 渠道商品查询请求
     * @return                      商品分页对象
     */
    @PostMapping("/empower/${application.empower.version}/sync/goods/sync-page")
    BaseResponse<MicroServicePage<ChannelGoodsSyncQueryResponse>> syncQueryPage(@RequestBody @Valid ChannelGoodsSyncQueryRequest goodsSyncQueryRequest);

    /***
     * 根据skuId或spuId查询同步商品
     * @param channelGoodsSyncBySkuIdsRequest 渠道商品查询请求
     * @return                                查询商品对象
     */
    @PostMapping("/empower/${application.empower.version}/sync/goods/sync-get-channel-goods")
    BaseResponse<List<ChannelGoodsSyncQueryResponse>> syncGetChannelGoods(@RequestBody @Valid ChannelGoodsSyncByIdsRequest channelGoodsSyncBySkuIdsRequest);

    /***
     * 分页查询同步商品
     * @param goodsSyncQueryRequest 渠道商品查询请求
     * @return                      商品分页对象
     */
    @PostMapping("/empower/${application.empower.version}/sync/goods/sync-page-new")
    BaseResponse<MicroServicePage<ChannelGoodsSyncQueryResponse>> syncQueryPageNew(@RequestBody @Valid ChannelGoodsSyncQueryRequest goodsSyncQueryRequest);

    @PostMapping("/empower/${application.empower.version}/sync/goods/data-count")
    BaseResponse<Long> dataCount(@RequestBody @Valid ChannelGoodsSyncQueryRequest goodsSyncQueryRequest);

    /**
     * @description   修改第三方平台商品同步状态
     * @author  wur
     * @date: 2023/7/12 13:59
     * @param updateSyncStatusRequest
     * @return
     **/
    @PostMapping("/empower/${application.empower.version}/sync/goods/update-status")
    BaseResponse updateThirdGoodsSync(@RequestBody @Valid ChannelGoodsUpdateSyncStatusRequest updateSyncStatusRequest);
}
