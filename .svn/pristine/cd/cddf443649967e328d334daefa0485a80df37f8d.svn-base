package com.wanmi.sbc.goods.api.provider.freight;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.freight.*;
import com.wanmi.sbc.goods.api.response.freight.CartListResponse;
import com.wanmi.sbc.goods.api.response.freight.CollectPageInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
*
 * @description
 * @author  wur
 * @date: 2022/7/6 15:16
 **/
@FeignClient(value = "${application.goods.name}", contextId = "FreightProvider")
public interface FreightProvider {

    /**
     * 商品详情页运费查询
     *
     * @param request 商品详情页运费查询 {@link GetFreightInGoodsInfoRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/goods/${application.goods.version}/freight/in-goods-info")
    BaseResponse inGoodsInfo(@RequestBody @Valid GetFreightInGoodsInfoRequest request);

    /**
     * 运费凑单页凑单信息查询
     *
     * @param request 运费凑单页凑单信息查询 {@link CollectPageInfoRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/goods/${application.goods.version}/freight/in-collect-page-info")
    BaseResponse<CollectPageInfoResponse> collectPageInfo(@RequestBody @Valid CollectPageInfoRequest request);

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/freight/cart-list")
    BaseResponse<CartListResponse> cartList(@RequestBody @Valid CartListRequest request);

}
