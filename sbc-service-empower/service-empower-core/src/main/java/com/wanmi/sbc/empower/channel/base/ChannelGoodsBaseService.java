package com.wanmi.sbc.empower.channel.base;

import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsSaleabilityQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsStockQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelCheckSkuStateRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsDetailRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsDetailResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsSaleabilityQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsStockQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.SkuSellingPriceResponse;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @description 对接第三方渠道商品服务
 * @author daiyitian
 * @date 2021/5/11 14:36
 */
public interface ChannelGoodsBaseService extends ChannelBaseService {

    /**
     * 查询库存
     *
     * @description 查询库存
     * @author daiyitian
     * @date 2021/5/11 16:17
     * @param request 商品id入参
     * @return com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsStockQueryResponse
     */
    ChannelGoodsStockQueryResponse queryStock(ChannelGoodsStockQueryRequest request);

    /**
     * 查询可售性
     *
     * @description 查询可售性
     * @author daiyitian
     * @date 2021/5/11 16:17
     * @param request 商品id入参
     * @return com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsSaleabilityQueryResponse
     */
    ChannelGoodsSaleabilityQueryResponse querySaleability(
            ChannelGoodsSaleabilityQueryRequest request);

    /**
     * 商品详情查询
     *
     * @param request 商品id查询请求 {@link ChannelGoodsDetailRequest}
     * @return 商品详情             {@link ChannelGoodsDetailResponse}
     */
    ChannelGoodsDetailResponse queryDetail(@RequestBody @Valid ChannelGoodsDetailRequest request);

    List<SkuSellingPriceResponse> queryChannelGoodsPrice(@RequestBody @Valid ChannelCheckSkuStateRequest request);
}
