package com.wanmi.sbc.order.provider.impl.small;


import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.small.SmallProvider;
import com.wanmi.sbc.order.api.request.small.ReturnSmallOrderRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.swdh5.service.SmallService;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>调用small平台</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@RestController
@Validated
@Slf4j
public class SmallController implements SmallProvider {

    @Autowired
    private SmallService smallService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Override
    public BaseResponse testCreateSmallOrder(TradeGetByIdRequest tradeGetByIdRequest) {

        String tid = tradeGetByIdRequest.getTid();

        List<Trade> trades = new ArrayList<>();
        Trade trade = tradeService.detail(tid);
        trades.add(trade);
        smallService.createSmallOrder(trades);

        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse createH5OrderByTid(@RequestParam("tid") String  tid){
        List<Trade> trades = new ArrayList<>();
        Trade trade = tradeService.detail(tid);
        if(trade == null){
            return BaseResponse.success("订单不存在");
        }
        trades.add(trade);
        BaseResponse baseResponse = smallService.createSmallOrder(trades);
        return baseResponse;
    }

    @Override
    public BaseResponse refundH5OrderByRefundId(@RequestParam("refundId") String  refundId){
        ReturnOrder returnOrder = returnOrderService.findById(refundId);
        smallService.newReturnSmallOrder(Collections.singletonList(returnOrder));

        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse returnSmallOrder(ReturnSmallOrderRequest request) {
        String orderSn = request.getTid();
        Integer type = request.getType();
        log.info("开始处理small订单退单逻辑: {}", JSONObject.toJSONString(request));
        smallService.returnSmallOrder(orderSn, type);
        log.info("结束处理small订单退单逻辑: {}", JSONObject.toJSONString(request));

        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse getFullGiftNum(String phone) {
        return BaseResponse.success(smallService.getGiftNum(phone));
    }
    @Override
    public BaseResponse checkFullGift(String phone) {
        return BaseResponse.success(smallService.checkFullGift(phone));
    }
    @Override
    @PostMapping("/order/${application.order.version}/small/checkOrderIsAfterSale")
    public BaseResponse<Boolean> checkOrderIsAfterSale(@RequestParam("orderSn") String orderSn){
        Boolean isAfterSale =smallService.checkOrderIsAfterSale(orderSn);
        return BaseResponse.success(isAfterSale);
    }
}
