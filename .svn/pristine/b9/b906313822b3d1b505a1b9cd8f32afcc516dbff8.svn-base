package com.wanmi.sbc.empower.provider.impl.channel.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.goods.ChannelGoodsSyncProvider;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncByIdsRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsUpdateSyncStatusRequest;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelGoodsSyncQueryResponse;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import com.wanmi.sbc.empower.channel.goods.ChannelGoodsSyncService;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author zhengyang
 * @className ChannelGoodsSyncController
 * @description 抽象化商品同步Controller
 * @date 2021/5/23 10:59
 */
@RestController
public class ChannelGoodsSyncController implements ChannelGoodsSyncProvider {

    @Resource private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse syncGoodsNotice(ChannelGoodsSyncRequest channelGoodsSyncRequest) {
        // 三方类型
        ThirdPlatformType type = channelGoodsSyncRequest.getThirdPlatformType();
        channelServiceFactory
                .getChannelService(ChannelGoodsSyncService.class, type)
                .syncGoodsNotice(channelGoodsSyncRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * * 分页查询同步商品
     *
     * @param goodsSyncQueryRequest 渠道商品查询请求
     * @return                      商品分页对象
     */
    @Override
    public BaseResponse<MicroServicePage<ChannelGoodsSyncQueryResponse>> syncQueryPage(
            ChannelGoodsSyncQueryRequest goodsSyncQueryRequest) {
        // 三方类型
        ThirdPlatformType type = goodsSyncQueryRequest.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsSyncService.class, type)
                        .syncQueryPage(goodsSyncQueryRequest));
    }

    /**
     * * 分页查询同步商品
     *
     * @param goodsSyncQueryRequest 渠道商品查询请求
     * @return                      商品分页对象
     */
    @Override
    public BaseResponse<MicroServicePage<ChannelGoodsSyncQueryResponse>> syncQueryPageNew(
            ChannelGoodsSyncQueryRequest goodsSyncQueryRequest) {
        // 三方类型
        ThirdPlatformType type = goodsSyncQueryRequest.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsSyncService.class, type)
                        .syncQueryPageNew(goodsSyncQueryRequest));
    }

    /**
     * * 分页查询同步商品
     *
     * @param goodsSyncQueryRequest 渠道商品查询请求
     * @return                      商品分页对象
     */
    @Override
    public BaseResponse<Long> dataCount(
            ChannelGoodsSyncQueryRequest goodsSyncQueryRequest) {
        // 三方类型
        ThirdPlatformType type = goodsSyncQueryRequest.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsSyncService.class, type)
                        .dataCount());
    }

    @Override
    public BaseResponse updateThirdGoodsSync(ChannelGoodsUpdateSyncStatusRequest updateSyncStatusRequest) {
        // 三方类型
        ThirdPlatformType type = updateSyncStatusRequest.getThirdPlatformType();
        channelServiceFactory
                .getChannelService(ChannelGoodsSyncService.class, type)
                .updateSyncStatus(updateSyncStatusRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * * 分页查询同步商品
     *
     * @param channelGoodsSyncBySkuIdsRequest 渠道商品查询请求
     * @return                                查询商品对象
     */
    @Override
    public BaseResponse<List<ChannelGoodsSyncQueryResponse>> syncGetChannelGoods(
            ChannelGoodsSyncByIdsRequest channelGoodsSyncBySkuIdsRequest) {
        // 三方类型
        ThirdPlatformType type = channelGoodsSyncBySkuIdsRequest.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsSyncService.class, type)
                        .syncGetChannelGoods(channelGoodsSyncBySkuIdsRequest));
    }
}
