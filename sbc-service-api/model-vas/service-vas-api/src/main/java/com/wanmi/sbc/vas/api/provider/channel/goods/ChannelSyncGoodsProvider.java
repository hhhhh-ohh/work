package com.wanmi.sbc.vas.api.provider.channel.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author EDZ
 * @className SyncGoodsProvider
 * @description 同步商品信息
 * @date 2021/5/19 9:59
 **/
@FeignClient(value = "${application.vas.name}", contextId = "ChannelSyncGoodsProvider")
public interface ChannelSyncGoodsProvider {

    /***
     * 同步指定SKU数据
     * @param channelGoodsSyncBySkuVasRequest
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/goods/sync-goods-notice")
    BaseResponse syncGoodsNotice(@RequestBody @Valid ChannelGoodsSyncVasRequest channelGoodsSyncBySkuVasRequest);

    /***
     * 同步指定SKU数据
     * @param channelGoodsSyncBySkuVasRequest
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/goods/sync-sku-list")
    BaseResponse syncSkuList(@RequestBody @Valid ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest);

    /**
     * 同步指定SPU数据
     * @param channelGoodsSyncBySpuVasRequest
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/goods/sync-spu-list")
    BaseResponse<List<String>> syncSpuList(@RequestBody @Valid ChannelGoodsSyncBySpuVasRequest channelGoodsSyncBySpuVasRequest);

    /***
     * 同步指定SKU数据
     * @param channelGoodsSyncBySkuVasRequest
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/goods/sync-sku-price-list")
    BaseResponse syncSkuPriceList(@RequestBody @Valid ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest);


}
