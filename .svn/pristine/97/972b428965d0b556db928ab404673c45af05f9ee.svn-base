package com.wanmi.sbc.quickorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.order.api.provider.quickorder.QuickOrderProvider;
import com.wanmi.sbc.order.api.request.purchase.CartGoodsInfoRequest;
import com.wanmi.sbc.order.api.request.purchase.PurchaseInfoRequest;
import com.wanmi.sbc.order.api.request.purchase.PurchaseListRequest;
import com.wanmi.sbc.order.api.request.quickorder.QuickOrderGoodsListRequest;
import com.wanmi.sbc.order.api.response.purchase.GoodsCartListResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @className QuickOrderController
 * @description 快速下单
 * @author edz
 * @date 2023/5/31 18:10
 **/
@Tag(name = "QuickOrderController", description = "快速下单服务API")
@RestController
@RequestMapping("/site")
@Validated
public class QuickOrderController {

    @Autowired
    private QuickOrderProvider quickOrderProvider;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * 快速下单商品列表（登录状态）
     */
    @Operation(summary = "快速下单商品列表（登录状态）")
    @RequestMapping(value = "/quickOrderGoodsList", method = RequestMethod.POST)
    public BaseResponse<GoodsCartListResponse> quickOrderGoodsList(@RequestBody @Valid QuickOrderGoodsListRequest request) {
        request.setCustomer(commonUtil.getCustomer());
        request.setAreaId(request.getAreaId());
        request.setAddress(request.getAddress());
        request.setTerminalSource(commonUtil.getTerminal());
        return quickOrderProvider.getQuickOrderGoodsList(request);
    }

    /**
     * 快速下单商品列表（未登录状态）
     */
    @Operation(summary = "快速下单商品列表（未登录状态）")
    @RequestMapping(value = "/front/quickOrderGoodsList", method = RequestMethod.POST)
    public BaseResponse<GoodsCartListResponse> frontQuickOrderGoodsList(@RequestBody @Valid QuickOrderGoodsListRequest request) {
        request.setTerminalSource(commonUtil.getTerminal());
        return quickOrderProvider.getQuickOrderGoodsList(request);
    }

}
