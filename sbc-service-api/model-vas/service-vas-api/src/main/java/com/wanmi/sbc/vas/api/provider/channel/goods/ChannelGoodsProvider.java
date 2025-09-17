package com.wanmi.sbc.vas.api.provider.channel.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsStatusGetRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsVerifyRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsStatusGetResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 商品服务接口
 * @author daiyitian
 * @date 2021/5/21 20:53
 */
@FeignClient(value = "${application.vas.name}", contextId = "ChannelGoodsProvider")
public interface ChannelGoodsProvider {

    /**
     * 渠道商品状态获取
     *
     * @param request 商品信息 {@link ChannelGoodsStatusGetRequest}
     * @return 商品状态列表 {@link ChannelGoodsStatusGetResponse}
     */
    @PostMapping("/channel/${application.vas.version}/goods/get-goods-status")
    BaseResponse<ChannelGoodsStatusGetResponse> getGoodsStatus(
            @RequestBody @Valid ChannelGoodsStatusGetRequest request);

    /**
     * 渠道商品状态验证
     *
     * @param request 商品信息 {@link ChannelGoodsVerifyRequest}
     * @return 商品异常列表 {@link ChannelGoodsVerifyResponse}
     */
    @PostMapping("/channel/${application.vas.version}/goods/verify-goods")
    BaseResponse<ChannelGoodsVerifyResponse> verifyGoods(
            @RequestBody @Valid ChannelGoodsVerifyRequest request);
}
