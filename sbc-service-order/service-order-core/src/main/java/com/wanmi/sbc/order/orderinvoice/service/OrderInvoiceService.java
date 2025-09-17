package com.wanmi.sbc.order.orderinvoice.service;


import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.account.bean.enums.IsCompany;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceQueryProvider;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdAndDelFlagRequest;
import com.wanmi.sbc.customer.api.response.invoice.CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse;
import com.wanmi.sbc.customer.api.response.invoice.CustomerInvoiceListResponse;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;
import com.wanmi.sbc.order.api.request.trade.EsOrderInvoiceGenerateRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;

import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.orderinvoice.model.root.OrderInvoice;
import com.wanmi.sbc.order.orderinvoice.repository.OrderInvoiceRepository;
import com.wanmi.sbc.order.orderinvoice.request.OrderInvoiceQueryRequest;
import com.wanmi.sbc.order.orderinvoice.request.OrderInvoiceSaveRequest;
import com.wanmi.sbc.order.orderinvoice.response.OrderInvoiceResponse;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.repository.TradeRepository;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 订单开票服务层
 *
 * @author CHENLI
 * @date 2017/5/5
 */
@Slf4j
@Service
public class OrderInvoiceService {
    @Autowired
    private OrderInvoiceRepository repository;

    @Autowired
    private CustomerInvoiceQueryProvider customerInvoiceQueryProvider;

    @Autowired
    public OrderProducerService orderProducerService;

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * 生成开票
     * @param orderInvoiceSaveRequest orderInvoiceSaveRequest
     * @return Optional<OrderInvoice>
     */
    @Transactional
    @GlobalTransactional
    public Optional<OrderInvoice> generateOrderInvoice(OrderInvoiceSaveRequest orderInvoiceSaveRequest, String employeeId, InvoiceState invoiceState){
        OrderInvoice orderInvoice = new OrderInvoice();
        if (!CollectionUtils.isEmpty(repository.findByDelFlagAndOrderNo(DeleteFlag.NO, orderInvoiceSaveRequest.getOrderNo()))) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020021);
        }
        BeanUtils.copyProperties(orderInvoiceSaveRequest, orderInvoice);
        if(Objects.nonNull(orderInvoiceSaveRequest.getInvoiceTime())){
            orderInvoice.setInvoiceTime(DateUtil.parse(orderInvoiceSaveRequest.getInvoiceTime(),DateUtil.FMT_TIME_1));
        }
        wrapperOrderInvoice(orderInvoiceSaveRequest, orderInvoice);
        orderInvoice.setDelFlag(DeleteFlag.NO);
        orderInvoice.setCreateTime(LocalDateTime.now());
        orderInvoice.setOperateId(employeeId);
        orderInvoice.setInvoiceState(invoiceState);
        return Optional.ofNullable(repository.save(orderInvoice));
    }

    /**
     * 包装订单发票
     * @param orderInvoiceSaveRequest
     * @param orderInvoice
     */
    private void wrapperOrderInvoice(OrderInvoiceSaveRequest orderInvoiceSaveRequest, OrderInvoice orderInvoice) {
        if (Objects.isNull(orderInvoice)){
            return;
        }
        if (InvoiceType.SPECIAL.equals(orderInvoiceSaveRequest.getInvoiceType())) {
            CustomerInvoiceByCustomerIdAndDelFlagRequest byCustomerIdAndDelFlagRequest = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
            byCustomerIdAndDelFlagRequest.setCustomerId(orderInvoiceSaveRequest.getCustomerId());
            byCustomerIdAndDelFlagRequest.setInvoiceStyle(InvoiceStyle.SPECIAL);
            CustomerInvoiceListResponse customerInvoiceListResponse =  customerInvoiceQueryProvider.getByCustomerIdAndDelFlagAndStyle(byCustomerIdAndDelFlagRequest).getContext();
            if(!CollectionUtils.isEmpty(customerInvoiceListResponse.getCustomerInvoiceVOList())){
                orderInvoice.setInvoiceTitle(customerInvoiceListResponse.getCustomerInvoiceVOList().get(0).getCompanyName());
            }
            orderInvoice.setIsCompany(IsCompany.YES);
        } else {
            if (StringUtils.isBlank(orderInvoiceSaveRequest.getInvoiceTitle())) {
                orderInvoice.setIsCompany(IsCompany.NO);
            } else {
                orderInvoice.setIsCompany(IsCompany.YES);
            }
        }
    }

    /**
     * 修改订单开票
     * @param orderInvoiceSaveRequest orderInvoiceSaveRequest
     * @return Optional<OrderInvoice>
     */
    @Transactional
    public Optional<OrderInvoice> updateOrderInvoice(OrderInvoiceSaveRequest orderInvoiceSaveRequest){
        if(StringUtils.isEmpty(orderInvoiceSaveRequest.getOrderInvoiceId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);

        }
        OrderInvoice orderInvoice = findByOrderInvoiceId(orderInvoiceSaveRequest.getOrderInvoiceId());
        KsBeanUtil.copyProperties(orderInvoiceSaveRequest, orderInvoice);
        wrapperOrderInvoice(orderInvoiceSaveRequest, orderInvoice);
        if (Objects.nonNull(orderInvoice)) {
            orderInvoice.setUpdateTime(LocalDateTime.now());
        }
        return Optional.ofNullable(repository.save(orderInvoice));
    }

    /**
     * 根据订单号查询订单是否已开过票
     * @param orderNo
     * @return
     */
    public Optional<OrderInvoice> findByOrderNo(String orderNo){
        return repository.findByOrderNoAndDelFlag(orderNo,DeleteFlag.NO);
    }

    /**
     * 订单批量/单个开票
     * @param orderInvoiceIds
     */
    @Modifying
    @Transactional(rollbackFor = {Exception.class})
    public void updateOrderInvoiceState(List<String> orderInvoiceIds){
        int num = repository.checkInvoiceState(orderInvoiceIds, LocalDateTime.now());
        if(num == Constants.ZERO){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050108);
        }
    }

    /**
     * 订单发票作废
     * @param orderInvoiceId
     */
    @Modifying
    @Transactional
    public void invalidInvoice(String orderInvoiceId){
        repository.invalidInvoice(orderInvoiceId, LocalDateTime.now());
    }

    /**
     * 订单开票导出
     * @param orderInvoiceResponses
     * @param outputStream
     */
    public void export(List<OrderInvoiceResponse> orderInvoiceResponses, OutputStream outputStream){
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper
                .addSheet(
                        "订单开票导出",
                        getColumn(),
                        orderInvoiceResponses
                );
        excelHelper.write(outputStream);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn() {
        return new Column[]{
                new Column("客户名称", new SpelColumnRender<OrderInvoiceResponse>("customerName")),
                new Column("开票时间", new SpelColumnRender<OrderInvoiceResponse>("invoiceTime")),
                new Column("订单编号", new SpelColumnRender<OrderInvoiceResponse>("orderNo")),
                new Column("订单金额", new SpelColumnRender<OrderInvoiceResponse>("orderPrice")),
                new Column("付款状态", new SpelColumnRender<OrderInvoiceResponse>("payState")),
                new Column("发票类型", new SpelColumnRender<OrderInvoiceResponse>("invoiceType == 0 ? '普通发票' : '增值税发票' ")),
                new Column("发票抬头", new SpelColumnRender<OrderInvoiceResponse>("invoiceTitle")),
                new Column("开票状态", new SpelColumnRender<OrderInvoiceResponse>("invoiceState == 0 ? '待开票' : '已开票' "))
        };
    }

    /**
     * 根据ID删除订单开票信息
     * @param orderInvoiceId
     * @return
     */
    @Modifying
    @Transactional
    public void deleteOrderInvoice(String orderInvoiceId){
        repository.deleteOrderInvoice(orderInvoiceId, LocalDateTime.now());
    }

    /**
     * 根据订单号删除订单开票
     * @param orderNo
     */
    @Modifying
    @Transactional
    public void deleteOrderInvoiceByOrderNo(String orderNo){
        repository.deleteOrderInvoiceByOrderNo(orderNo, LocalDateTime.now());
    }

    /**
     * 根据状态统计开票信息
     * @param invoiceState
     * @return
     */
    public long countInvoiceByState(Long companyInfoId, Long storeId, InvoiceState invoiceState){
        OrderInvoiceQueryRequest orderInvoiceQueryRequest = new OrderInvoiceQueryRequest();
        orderInvoiceQueryRequest.setInvoiceState(invoiceState);
        orderInvoiceQueryRequest.setCompanyInfoId(companyInfoId);
        orderInvoiceQueryRequest.setStoreId(storeId);
        return repository.count(orderInvoiceQueryRequest.getWhereCriteria());
    }

    public Optional<OrderInvoice> findByOrderInvoiceIdAndDelFlag(String orderInvoiceId, DeleteFlag delFlag){
       return repository.findByOrderInvoiceIdAndDelFlag(orderInvoiceId,delFlag);
    }

    public Page<OrderInvoice> findAll(Specification<OrderInvoice> specification, PageRequest pageRequest){
        return repository.findAll(specification,pageRequest);

    }

    /**
     * @description 查询数量
     * @author  xuyunpeng
     * @date 2021/6/8 9:45 上午
     * @param specification
     * @return
     */
    public Long count(Specification<OrderInvoice> specification) {
        return repository.count(specification);
    }

    /**
     * 根据id获取订单开票详情
     * @param orderInvoiceId
     * @return
     */
    public OrderInvoice findByOrderInvoiceId(String orderInvoiceId){
        OrderInvoice orderInvoice = repository.findById(orderInvoiceId).orElse(null);
        if(orderInvoice == null || DeleteFlag.YES.equals(orderInvoice.getDelFlag())){
            throw new SbcRuntimeException(getDeleteIndex(orderInvoiceId), AccountErrorCodeEnum.K020022);
        }
        return orderInvoice;
    }

    /**
     * 拼凑删除es-提供给findOne去调
     * @param id 编号
     * @return "{index}:{id}"
     */
    public Object getDeleteIndex(String id){
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.ORDER_INVOICE, id);
    }

    /**
     * @description 同步订单状态至ES的订单开票索引
     * @author  EDZ
     * @date 2021/6/11 14:33
     * @param tid
     * @return void
     **/
    public void syncStateToInvoice(String tid) {
        try {
            log.info("syncStateToInvoice tid={}");
            //1、查找订单信息
            Trade trade = tradeRepository.findById(tid).orElse(null);
            if (trade == null) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{tid});
            }
            this.syncStateToInvoice(trade);
        } catch (Exception e) {
            log.error("syncStateToInvoice error ", e);
        }
    }

    /**
     * @description 同步订单状态至ES的订单开票索引
     * @author  EDZ
     * @date 2021/6/21 10:33
     * @param trade
     * @return void
     **/
    public void syncStateToInvoice(Trade trade) {
        try {
            FlowState flowState = trade.getTradeState().getFlowState();
            log.info("syncStateToInvoice tid={},flowState={}", trade.getId(), flowState);
            Optional<OrderInvoice> orderInvoiceOptional = this.findByOrderNo(trade.getId());
            if (orderInvoiceOptional.isPresent()) {
                //该订单有开票数据，通过mq，将流程状态（订单状态）同步到es中的开票数据中
                OrderInvoice orderInvoice = orderInvoiceOptional.get();
                EsOrderInvoiceGenerateRequest request = new EsOrderInvoiceGenerateRequest();
                request.setOrderInvoiceId(orderInvoice.getOrderInvoiceId());
                request.setFlowState(flowState);
                log.info(
                        "syncStateToInvoice orderInvoiceId={}, flowState={}",
                        request.getOrderInvoiceId(),
                        request.getFlowState());
                orderProducerService.updateFlowStateOrderInvoice(request);
            }
        } catch (Exception e) {
            log.error("syncStateToInvoice error ", e);
        }
    }
}
