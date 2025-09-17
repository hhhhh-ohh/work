package com.wanmi.sbc.vas.provider.impl.channel.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import com.wanmi.sbc.vas.channel.base.ChannelServiceFactory;
import com.wanmi.sbc.vas.channel.goods.service.ChannelSyncGoodsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhengyang
 * @className SyncGoodsController
 * @description 三方商品同步Controller
 * @date 2021/5/19 9:54
 **/
@Slf4j
@RestController
public class ChannelSyncGoodsController implements ChannelSyncGoodsProvider {

    @Resource
    private ChannelServiceFactory channelServiceFactory;

    /***
     * 同步商品通知 发送一个通知，通知同步渠道商品到本地临时表
     * @param channelGoodsSyncBySkuVasRequest   商品同步通知请求对象
     * @return
     */
    @Async
    @Override
    public BaseResponse syncGoodsNotice(ChannelGoodsSyncVasRequest channelGoodsSyncBySkuVasRequest){
        ChannelSyncGoodsService syncGoodsTempService = channelServiceFactory.getChannelService(
                        ChannelSyncGoodsService.class, channelGoodsSyncBySkuVasRequest.getThirdPlatformType());
        syncGoodsTempService.syncGoodsNotice(channelGoodsSyncBySkuVasRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /***
     * 同步指定SKU的数据
     * @param channelGoodsSyncBySkuVasRequest
     * @return
     */
    @Override
    public BaseResponse syncSkuList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest){
        ChannelSyncGoodsService syncGoodsTempService = channelServiceFactory
                .getChannelService(ChannelSyncGoodsService.class, channelGoodsSyncBySkuVasRequest.getThirdPlatformType());
        syncGoodsTempService.syncSkuList(channelGoodsSyncBySkuVasRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<List<String>> syncSpuList(ChannelGoodsSyncBySpuVasRequest channelGoodsSyncBySpuVasRequest) {
        ChannelSyncGoodsService syncGoodsTempService = channelServiceFactory
                .getChannelService(ChannelSyncGoodsService.class, channelGoodsSyncBySpuVasRequest.getThirdPlatformType());
        List<String> spuList = syncGoodsTempService.syncSpuList(channelGoodsSyncBySpuVasRequest);
        return BaseResponse.success(spuList);
    }

    @Override
    public BaseResponse syncSkuPriceList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest) {
        ChannelSyncGoodsService syncGoodsTempService = channelServiceFactory
                .getChannelService(ChannelSyncGoodsService.class, channelGoodsSyncBySkuVasRequest.getThirdPlatformType());
        syncGoodsTempService.syncSkuPriceList(channelGoodsSyncBySkuVasRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
