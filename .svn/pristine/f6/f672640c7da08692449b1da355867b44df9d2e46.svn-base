package com.wanmi.sbc.order.provider.impl.refund;


import com.alibaba.fastjson2.JSONWriter;
import com.google.common.collect.Lists;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByIdRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByReturnOrderNoRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderPageRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderResponseByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderWithoutPageRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByIdResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByReturnCodeResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByReturnOrderNoResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderGetSumReturnPriceResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderListReponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderPageResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderWithoutPageResponse;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: wanggang
 * @createDate: 2018/12/3 13:46
 * @version: 1.0
 */
@Validated
@RestController
public class RefundOrderQueryController implements RefundOrderQueryProvider{

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Resource
    private PayTradeRecordService payTradeRecordService;

    /**
     * 查询退款单
     * @param refundOrderPageRequest {@link RefundOrderPageRequest }
     * @return {@link RefundOrderPageResponse }
    */
    @Override
    public BaseResponse<RefundOrderPageResponse> page(@RequestBody @Valid RefundOrderPageRequest refundOrderPageRequest){
        RefundOrderPageResponse refundOrderPageResponse = refundOrderService.findByRefundOrderRequest(KsBeanUtil.convert(refundOrderPageRequest,RefundOrderRequest.class));
        return BaseResponse.success(refundOrderPageResponse);
    }

    /**
     * 查询不带分页的退款单
     * @param refundOrderWithoutPageRequest {@link RefundOrderWithoutPageRequest }
     * @return {@link RefundOrderWithoutPageResponse }
    */
    @Override
    public BaseResponse<RefundOrderWithoutPageResponse> list(@RequestBody @Valid RefundOrderWithoutPageRequest refundOrderWithoutPageRequest){
        RefundOrderPageResponse refundOrderPageResponse = refundOrderService.findByRefundOrderRequestWithNoPage(KsBeanUtil.convert(refundOrderWithoutPageRequest,RefundOrderRequest.class));
        return BaseResponse.success(KsBeanUtil.convert(refundOrderPageResponse,RefundOrderWithoutPageResponse.class));
    }

    /**
     * 根据退单编号查询退款单
     * @param refundOrderByReturnOrderCodeRequest 包含：退单编号 {@link RefundOrderByReturnOrderCodeRequest }
     * @return  {@link RefundOrderByReturnCodeResponse }
    */
    @Override
    public BaseResponse<RefundOrderByReturnCodeResponse> getByReturnOrderCode(@RequestBody @Valid RefundOrderByReturnOrderCodeRequest refundOrderByReturnOrderCodeRequest){
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(refundOrderByReturnOrderCodeRequest.getReturnOrderCode());
        return BaseResponse.success(KsBeanUtil.convert(refundOrder,RefundOrderByReturnCodeResponse.class, JSONWriter.Feature.ReferenceDetection));
    }

    /**
     * 根据退单ID查询退款单
     * @param refundOrderByIdRequest 包含：退单ID {@link RefundOrderByIdRequest }
     * @return  {@link RefundOrderByIdResponse }
     */
    @Override
    public BaseResponse<RefundOrderByIdResponse> getById(@RequestBody @Valid RefundOrderByIdRequest refundOrderByIdRequest){
        RefundOrder refundOrder = refundOrderService.findById(refundOrderByIdRequest.getRefundId()).orElseGet(() -> new RefundOrder());
        return BaseResponse.success(KsBeanUtil.convert(refundOrder,RefundOrderByIdResponse.class));
    }

    /**
     * 根据退单编号查询退款单
     * @param refundOrderResponseByReturnOrderCodeRequest {@link RefundOrderResponseByReturnOrderCodeRequest }
     * @return {@link RefundOrderResponse }
    */
    @Override
    public BaseResponse<RefundOrderListReponse> getRefundOrderRespByReturnOrderCode(@RequestBody @Valid RefundOrderResponseByReturnOrderCodeRequest refundOrderResponseByReturnOrderCodeRequest){
        List<RefundOrderResponse> list = new ArrayList<>();
        ReturnOrder returnOrder = returnOrderService.findById(refundOrderResponseByReturnOrderCodeRequest.getReturnOrderCode());

        List<String> businessIds = Lists.newArrayList(returnOrder.getId());

        com.wanmi.sbc.order.bean.vo.RefundOrderResponse refundOrderResponse = refundOrderService
                .findRefundOrderRespByReturnOrderNo(refundOrderResponseByReturnOrderCodeRequest.getReturnOrderCode());
        list.add(KsBeanUtil.convert(refundOrderResponse, RefundOrderResponse.class));
        if(StringUtils.isNotBlank(returnOrder.getBusinessTailId())) {
            RefundOrderResponse convert = KsBeanUtil.convert(refundOrderService
                    .findRefundOrderRespByReturnOrderNo(returnOrder.getBusinessTailId()), RefundOrderResponse.class);
            list.add(convert);
            businessIds.add(returnOrder.getBusinessTailId());
        }
        Map<String, String> tradeNoMap = payTradeRecordService.queryTradeNoMapByBusinessIds(businessIds);

        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(response -> {
                com.wanmi.sbc.order.bean.vo.RefundOrderResponse orderResponse = KsBeanUtil.convert(response, com.wanmi.sbc.order.bean.vo.RefundOrderResponse.class);
                String payChannelValue = refundOrderService.setPayChannelValue(orderResponse);
                response.setPayChannelValue(payChannelValue);
                // 设置流水号,成功才设置值
                if(RefundStatus.FINISH == response.getRefundStatus()) {
                    response.setTradeNo(tradeNoMap.get(response.getReturnOrderCode()));
                }
            });
        }

       return BaseResponse.success(new RefundOrderListReponse(list));
    }

    /**
     * 合计退款金额
     * @param refundOrderRequest {@link RefundOrderRequest }
     * @return {@link RefundOrderGetSumReturnPriceResponse }
    */
    @Override
    public BaseResponse<RefundOrderGetSumReturnPriceResponse> getSumReturnPrice(@RequestBody @Valid RefundOrderRequest refundOrderRequest){
        BigDecimal result = refundOrderService.sumReturnPrice(refundOrderRequest);
        return BaseResponse.success(new RefundOrderGetSumReturnPriceResponse(result));
    }

    @Override
    public BaseResponse<RefundOrderByReturnOrderNoResponse> getByReturnOrderNo(@RequestBody @Valid RefundOrderByReturnOrderNoRequest refundOrderByReturnOrderCodeRequest){
        RefundOrder refundOrder = refundOrderService.getRefundOrderByReturnOrderNo(refundOrderByReturnOrderCodeRequest.getReturnOrderCode());
        RefundOrderByReturnOrderNoResponse response = new RefundOrderByReturnOrderNoResponse();
        if(refundOrder != null) {
            response.setRefundStatus(refundOrder.getRefundStatus());
        }
        return BaseResponse.success(response);
    }
}
