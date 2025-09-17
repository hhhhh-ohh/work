package com.wanmi.sbc.account;

import com.wanmi.sbc.account.api.provider.invoice.InvoiceProjectQueryProvider;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectByIdRequest;
import com.wanmi.sbc.account.api.response.invoice.InvoiceProjectByIdResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.account.response.OrderInvoiceDetailResponse;
import com.wanmi.sbc.account.response.OrderInvoiceViewResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdAndDelFlagRequest;
import com.wanmi.sbc.customer.api.response.invoice.CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse;
import com.wanmi.sbc.customer.api.response.invoice.CustomerInvoiceByCustomerIdAndDelFlagResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.order.api.provider.orderinvoice.OrderInvoiceQueryProvider;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.orderinvoice.OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderResponse;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.OrderInvoiceVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by CHENLI on 2017/5/8.
 */
@Service
public class OrderInvoiceDetailService {

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired
    private OrderInvoiceQueryProvider orderInvoiceQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CustomerInvoiceQueryProvider customerInvoiceQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private InvoiceProjectQueryProvider invoiceProjectQueryProvider;

    /**
     * 查看订单开票详情
     * @param orderNo 订单号
     * @return
     */
    public OrderInvoiceDetailResponse findOrderInvoiceDetail(String orderNo){
        OrderInvoiceDetailResponse detailResponse = new OrderInvoiceDetailResponse();
        //订单开票信息

        Optional<OrderInvoiceVO>   remotecall = Optional.ofNullable(

        orderInvoiceQueryProvider.findByOrderInvoiceIdAndDelFlag(OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest.builder()
        .id(orderNo).flag(DeleteFlag.NO).build()).getContext().getOrderInvoiceVO());



        //repository.findByOrderNoAndDelFlag(orderNo, DeleteFlag.NO)
        remotecall .ifPresent(orderInvoice -> {
            BeanUtils.copyProperties(orderInvoice,detailResponse);
            //开票项目
            InvoiceProjectByIdRequest invoiceProjectByIdRequest = new InvoiceProjectByIdRequest();
            invoiceProjectByIdRequest.setProjcetId(orderInvoice.getProjectId());
            BaseResponse<InvoiceProjectByIdResponse> baseResponse = invoiceProjectQueryProvider.getById(invoiceProjectByIdRequest);
            InvoiceProjectByIdResponse invoiceProjectByIdResponse = baseResponse.getContext();
           // InvoiceProjectVO invoiceProject = orderInvoice.getInvoiceProject();
            detailResponse.setProjectName(invoiceProjectByIdResponse.getProjectName());
        });
        //订单信息
        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                .tid(orderNo).build()).getContext().getTradeVO();
        if(trade != null) {
            detailResponse.setCustomerName(trade.getBuyer().getName());
            detailResponse.setCustomerId(trade.getBuyer().getId());
            this.transformTradeInfo(trade, detailResponse);
            //增专票信息
            BaseResponse<CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse> baseResponse =
                    customerInvoiceQueryProvider.getByCustomerIdAndDelFlagAndCheckState(
                            CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest.builder()
                                    .customerId(trade.getBuyer().getId()).build()
                    );
            CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse response = baseResponse.getContext();
            if (Objects.nonNull(response)){
                detailResponse.setInvoiceTitle(response.getCompanyName());
                //审核通过的时候才设置
                if (CheckState.CHECKED.equals(response.getCheckState())) {
                    detailResponse.setCustomerInvoiceId(response.getCustomerInvoiceId());
                }
                BeanUtils.copyProperties(response,detailResponse,"customerName");
            }
//            customerInvoiceRepository.findByCustomerId(trade.getBuyer().getId()).ifPresent(customerInvoice -> {
//
//            });
        }

        return detailResponse;
    }

    /**
     * 订单开票前判断订单的状态
     * @param orderNo
     */
    public void findOrderCheckState(String orderNo, Long storeId) {
        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                .tid(orderNo).build()).getContext().getTradeVO();
        //订单不存在
        if (Objects.isNull(trade)|| null == trade.getId()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{orderNo});
        }
        // 商家只能给自己店铺的订单开票, boss平台都可以开票
        if (null != storeId && storeId > 1L) {
            if (!storeId.equals(trade.getSupplier().getStoreId())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{orderNo});
            }
        }
        //订单未付款
        if (!trade.getTradeState().getPayState().equals(PayState.PAID)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050044);
        }
    }


    /**
     * 根据id查询开票详情
     *
     * @param invoiceId invoiceId
     * @return OrderInvoiceViewResponse
     */
    public OrderInvoiceViewResponse findByOrderInvoiceId(String invoiceId) {

        OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest orderInvoiceFindByOrderInvoiceIdAndDelFlagRequest =
                OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest.builder()
                .id(invoiceId).flag(DeleteFlag.NO).build();


        Optional<OrderInvoiceVO>   remotecall = Optional.ofNullable(orderInvoiceQueryProvider.findByOrderInvoiceIdAndDelFlag(orderInvoiceFindByOrderInvoiceIdAndDelFlagRequest).getContext()
                .getOrderInvoiceVO());



        return remotecall
                .map(orderInvoice -> {
            TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                    .tid(orderInvoice.getOrderNo()).build()).getContext().getTradeVO();
            if (Objects.isNull(trade)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{orderInvoice.getOrderNo()});
            }


                    FindPayOrderRequest findPayOrderRequest = FindPayOrderRequest.builder().value(trade.getId()).build();

            BaseResponse<FindPayOrderResponse>  findPayOrderResponse =  payOrderQueryProvider.findPayOrder(findPayOrderRequest);

            // PayOrderResponse payOrderResponse = payOrderService.findPayOrder(trade.getId());

            FindPayOrderResponse payOrderResponse = findPayOrderResponse.getContext();

            if (Objects.isNull(payOrderResponse)) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020032);
            }
            InvoiceProjectByIdRequest invoiceProjectByIdRequest = new InvoiceProjectByIdRequest();
            invoiceProjectByIdRequest.setProjcetId(orderInvoice.getProjectId());
            BaseResponse<InvoiceProjectByIdResponse> invoiceProjectByIdResponseBaseResponse = invoiceProjectQueryProvider.getAllById(invoiceProjectByIdRequest);
            InvoiceProjectByIdResponse invoiceProjectByIdResponse = invoiceProjectByIdResponseBaseResponse.getContext();
                    CustomerDetailVO customerDetail = customerDetailQueryProvider.getCustomerDetailByCustomerId(
                            CustomerDetailByCustomerIdRequest.builder()
                                    .customerId(orderInvoice.getCustomerId()).build()).getContext();
            // 普通发票
            if (orderInvoice.getInvoiceType() == InvoiceType.NORMAL) {
                return OrderInvoiceViewResponse.builder()
                        .invoiceType(orderInvoice.getInvoiceType())
                        .invoiceTime(orderInvoice.getInvoiceTime())
                        .invoiceTitle(orderInvoice.getInvoiceTitle())
                        .invoiceAddress(Objects.isNull(orderInvoice.getInvoiceAddress()) ?
                                trade.getConsignee().getDetailAddress(): orderInvoice.getInvoiceAddress())
                        .orderNo(orderInvoice.getOrderNo())
                        .orderPrice(trade.getTradePrice().getTotalPrice())
                        .customerName(customerDetail.getCustomerName())
                        .customerId(customerDetail.getCustomerId())
                        .payOrderStatus(payOrderResponse.getPayOrderStatus())
                        .flowState(trade.getTradeState() != null ? trade.getTradeState().getFlowState() : null)
                        .projectName(invoiceProjectByIdResponse.getProjectName())
                        .invoiceState(orderInvoice.getInvoiceState())
                        .supplierName( trade.getSupplier() != null? trade.getSupplier().getSupplierName() : null)
                        .companyInfoId(orderInvoice.getCompanyInfoId())
                        .taxNo( trade.getInvoice() != null? trade.getInvoice().getGeneralInvoice().getIdentification() : null)
                        .build();
            }

            // 增值税发票
            CustomerInvoiceByCustomerIdAndDelFlagRequest request = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
            request.setCustomerId(orderInvoice.getCustomerId());
            BaseResponse<CustomerInvoiceByCustomerIdAndDelFlagResponse>  baseResponse = customerInvoiceQueryProvider.getSpecialByCustomerIdAndDelFlag(request);
            CustomerInvoiceByCustomerIdAndDelFlagResponse response = baseResponse.getContext();
//            return response.map(customerInvoice -> ).orElseThrow(() -> new SbcRuntimeException("K-070008"));
            return OrderInvoiceViewResponse.builder()
                    .registerPhone(response.getCompanyPhone())
                    .bankNo(response.getBankNo())
                    .bankName(response.getBankName())
                    .registerAddress(response.getCompanyAddress())
                    .invoiceType(orderInvoice.getInvoiceType())
                    .invoiceTime(orderInvoice.getInvoiceTime())
                    .invoiceTitle(orderInvoice.getInvoiceTitle())
                    .invoiceAddress(orderInvoice.getInvoiceAddress())
                    .orderNo(orderInvoice.getOrderNo())
                    .orderPrice(trade.getTradePrice().getTotalPrice())
                    .taxNo(response.getTaxpayerNumber())
                    .customerName(customerDetail.getCustomerName())
                    .customerId(response.getCustomerId())
                    .payOrderStatus(payOrderResponse.getPayOrderStatus())
                    .flowState(trade.getTradeState() != null ? trade.getTradeState().getFlowState() : null)
                    .projectName(invoiceProjectByIdResponse.getProjectName())
                    .invoiceState(orderInvoice.getInvoiceState())
                    .supplierName( trade.getSupplier() != null ? trade.getSupplier().getSupplierName() : null)
                    .companyInfoId(orderInvoice.getCompanyInfoId())
                    .build();
        }).orElseThrow(() -> new SbcRuntimeException(AccountErrorCodeEnum.K020022));
    }

    /**
     * 查询订单数据，转换成要展示的类
     * @param trade
     * @param detailResponse
     */
    private void transformTradeInfo(TradeVO trade, OrderInvoiceDetailResponse detailResponse){
        detailResponse.setOrderNo(trade.getId());
        detailResponse.setOrderPrice(trade.getTradePrice().getTotalPrice());
        detailResponse.setPayState(trade.getTradeState().getPayState().getDescription());
    }


}
