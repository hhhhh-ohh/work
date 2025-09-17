package com.wanmi.sbc.empower.api.provider.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsSaleabilityQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsStockQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelCheckSkuStateRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsDetailRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsDetailResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsSaleabilityQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsStockQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.SkuSellingPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author daiyitian
 * @description 渠道商品查询服务Provider
 * @date 2021/5/10 15:58
 */
@FeignClient(value = "${application.empower.name}", contextId = "ChannelGoodsQueryProvider")
public interface ChannelGoodsQueryProvider {

    /**
     * 商品可售性查询
     *
     * @param request 商品id查询请求 {@link ChannelGoodsSaleabilityQueryRequest}
     * @return 商品可售性结果 {@link ChannelGoodsSaleabilityQueryResponse}
     */
    @PostMapping("/channel/${application.empower.version}/goods/query-saleability")
    BaseResponse<ChannelGoodsSaleabilityQueryResponse> querySaleability(
            @RequestBody @Valid ChannelGoodsSaleabilityQueryRequest request);

    /**
     * 商品库存查询
     *
     * @param request 商品id查询请求 {@link ChannelGoodsStockQueryRequest}
     * @return 商品库存结果 {@link ChannelGoodsStockQueryResponse}
     */
    @PostMapping("/channel/${application.empower.version}/goods/query-stock")
    BaseResponse<ChannelGoodsStockQueryResponse> queryStock(
            @RequestBody @Valid ChannelGoodsStockQueryRequest request);

    /**
     * 商品详情查询
     *
     * @param request 商品id查询请求 {@link ChannelGoodsDetailRequest}
     * @return 商品详情             {@link ChannelGoodsDetailResponse}
     */
    @PostMapping("/channel/${application.empower.version}/goods/query-detail")
    BaseResponse<ChannelGoodsDetailResponse> queryDetail(@RequestBody @Valid ChannelGoodsDetailRequest request);


    /**
     * 商品详情查询
     *
     * @param request 商品id查询请求 {@link ChannelGoodsDetailRequest}
     * @return 商品详情             {@link ChannelGoodsDetailResponse}
     */
    @PostMapping("/channel/${application.empower.version}/goods/query-channel-price")
    BaseResponse<List<SkuSellingPriceResponse>> queryChannelPrice(@RequestBody @Valid ChannelCheckSkuStateRequest request);
}
