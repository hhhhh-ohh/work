package com.wanmi.sbc.empower.provider.impl.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelGoodsQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsSaleabilityQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsStockQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelCheckSkuStateRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsDetailRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsDetailResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsSaleabilityQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsStockQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.SkuSellingPriceResponse;
import com.wanmi.sbc.empower.channel.base.ChannelGoodsBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @description 渠道商品查询服务实现
 * @author daiyitian
 * @date 2021/5/10 17:22
 */
@RestController
@Validated
public class ChannelGoodsQueryController implements ChannelGoodsQueryProvider {

    @Autowired private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse<ChannelGoodsSaleabilityQueryResponse> querySaleability(
            @RequestBody @Valid ChannelGoodsSaleabilityQueryRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsBaseService.class, type)
                        .querySaleability(request));
    }

    @Override
    public BaseResponse<ChannelGoodsStockQueryResponse> queryStock(
            @RequestBody @Valid ChannelGoodsStockQueryRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsBaseService.class, type)
                        .queryStock(request));
    }

    @Override
    public BaseResponse<ChannelGoodsDetailResponse> queryDetail(ChannelGoodsDetailRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsBaseService.class, type)
                        .queryDetail(request));
    }

    @Override
    public BaseResponse<List<SkuSellingPriceResponse>> queryChannelPrice(ChannelCheckSkuStateRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelGoodsBaseService.class, type)
                        .queryChannelGoodsPrice(request));
    }
}
