package com.wanmi.sbc.order.provider.impl.quickorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.order.api.provider.quickorder.QuickOrderProvider;
import com.wanmi.sbc.order.api.request.purchase.*;
import com.wanmi.sbc.order.api.request.quickorder.QuickOrderGoodsListRequest;
import com.wanmi.sbc.order.api.response.purchase.*;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.purchase.GoodsMarketingService;
import com.wanmi.sbc.order.purchase.PurchaseService;
import com.wanmi.sbc.order.purchase.request.PurchaseRequest;
import com.wanmi.sbc.order.purchase.service.CartAdaptor;
import com.wanmi.sbc.order.quickorder.QuickOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @className QuickOrderController
 * @description TODO
 * @author edz
 * @date 2023/6/2 13:59
 **/
@Validated
@RestController
public class QuickOrderController implements QuickOrderProvider {

    @Autowired
    private QuickOrderService quickOrderService;

    @Override
    public BaseResponse<GoodsCartListResponse> getQuickOrderGoodsList(@RequestBody @Valid QuickOrderGoodsListRequest request){
        return BaseResponse.success(quickOrderService.getQuickOrderGoods(request));
    }
}
