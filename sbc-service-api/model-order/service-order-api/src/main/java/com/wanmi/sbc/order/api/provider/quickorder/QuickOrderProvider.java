package com.wanmi.sbc.order.api.provider.quickorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.purchase.*;
import com.wanmi.sbc.order.api.request.quickorder.QuickOrderGoodsListRequest;
import com.wanmi.sbc.order.api.response.purchase.GoodsCartListResponse;
import com.wanmi.sbc.order.api.response.purchase.PurchaseCalcMarketingResponse;
import com.wanmi.sbc.order.api.response.purchase.PurchaseFillBuyCountResponse;
import com.wanmi.sbc.order.api.response.purchase.PurchaseListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @className QuickOrderProvider
 * @description 快速下单操作
 * @author edz
 * @date 2023/6/2 13:58
 **/
@FeignClient(value = "${application.order.name}", contextId = "QuickOrderProvider")
public interface QuickOrderProvider {

    /**
     * @description 获取快速下单商品列表
     * @author  edz
     * @date 2023/6/2 13:58
     **/
    @PostMapping("/order/${application.order.version}/quickorder/get-quick-order-goods-list")
    BaseResponse<GoodsCartListResponse> getQuickOrderGoodsList(@RequestBody @Valid QuickOrderGoodsListRequest request);
}

