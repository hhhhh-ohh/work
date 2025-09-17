package com.wanmi.sbc.mq.report.service.base;


import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.OrderInvoiceResponse;
import com.wanmi.sbc.order.api.provider.orderinvoice.OrderInvoiceQueryProvider;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.orderinvoice.OrderInvoiceFindAllRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.bean.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @return
 * @description 订单开票
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class OrderTicketBaseService {

    @Autowired
    private OrderInvoiceQueryProvider orderInvoiceQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider;

    @ReturnSensitiveWords(functionName = "f_supplier_order_ticket_export_sign_word")
    public List<OrderInvoiceResponse> queryExport(Operator operator, OrderInvoiceFindAllRequest request) {
        return getOrderInvoiceResponse(orderInvoiceQueryProvider.findAll(request).getContext().getValue().getContent(), request);
    }

    /**
     * 转换对象
     *
     * @param orderInvoices
     * @return
     */
    private List<OrderInvoiceResponse> getOrderInvoiceResponse(List<OrderInvoiceVO> orderInvoices, OrderInvoiceFindAllRequest queryRequest) {
        //拼接返回的数据
        return orderInvoices.parallelStream().map((OrderInvoiceVO orderInvoice) -> {
            OrderInvoiceResponse invoiceResponse = new OrderInvoiceResponse();
            BeanUtils.copyProperties(orderInvoice, invoiceResponse);
            //订单相关信息
            TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                    .tid(orderInvoice.getOrderNo()).build()).getContext().getTradeVO();
            if (Objects.nonNull(trade)) {
                invoiceResponse.setOrderNo(trade.getId());
                if (Objects.nonNull(trade.getTradePrice())) {
                    invoiceResponse.setOrderPrice(trade.getTradePrice().getTotalPrice());
                }
                if (Objects.nonNull(trade.getBuyer())) {
                    //客户相关信息
                    invoiceResponse.setCustomerId(trade.getBuyer().getId());
                    invoiceResponse.setCustomerName(trade.getBuyer().getName());
                }
                //商家名称 兼容老数据
                SupplierVO supplier;
                if ((supplier = trade.getSupplier()) != null) {
                    invoiceResponse.setSupplierName(supplier.getSupplierName());
                }
                TradeStateVO tradeState = trade.getTradeState();
                if (Objects.nonNull(tradeState)) {
                    invoiceResponse.setFlowState(tradeState.getFlowState());
                }
            }
            FindPayOrderByOrderCodeRequest requeststr = FindPayOrderByOrderCodeRequest.builder().value(orderInvoice.getOrderNo()).build();


            PayOrderVO value = payOrderQueryProvider.findPayOrderByOrderCode(requeststr).getContext().getValue();

            if (value != null) {

                invoiceResponse.setPayOrderStatus(value.getPayOrderStatus());
            }

            return invoiceResponse;
        }).filter(invoiceResponse -> {
            if (Objects.isNull(queryRequest.getFlowState())) {
                return true;
            }
            if (Objects.isNull(invoiceResponse.getFlowState())) {
                return false;
            }
            return queryRequest.getFlowState().equals(invoiceResponse.getFlowState());
        }).collect(Collectors.toList());
    }


}
