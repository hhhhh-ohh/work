package com.wanmi.sbc.elastic.orderinvoice.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceDeleteRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceGenerateRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceInitRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceModifyStateRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.orderinvoice.model.root.EsOrderInvoice;
import com.wanmi.sbc.elastic.orderinvoice.repository.EsOrderInvoiceRepository;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.order.api.provider.orderinvoice.OrderInvoiceQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.orderinvoice.OrderInvoiceFindAllRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.vo.OrderInvoiceVO;
import com.wanmi.sbc.order.bean.vo.SupplierVO;
import com.wanmi.sbc.order.bean.vo.TradeStateVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2020/12/30 14:10
 * @description <p> 订单开票 </p>
 */
@Slf4j
@Service
public class EsOrderInvoiceService {

    @Autowired
    private EsOrderInvoiceRepository esOrderInvoiceRepository;

    @Autowired
    private OrderInvoiceQueryProvider orderInvoiceQueryProvider;
    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esOrderInvoice.json")
    private Resource mapping;

    /**
     * 新增订单开票信息
     * @param request
     */
    public void addEsOrderInvoice(EsOrderInvoiceGenerateRequest request) {
        EsOrderInvoice esOrderInvoice = new EsOrderInvoice();
        BeanUtils.copyProperties(request, esOrderInvoice);
        esOrderInvoice.setDelFlag(DeleteFlag.NO);
        esOrderInvoice.setCreateTime(LocalDateTime.now());
        if (Objects.equals(esOrderInvoice.getInvoiceState(), 1)) {
            esOrderInvoice.setInvoiceTime(LocalDateTime.now());
        }
        FlowState flowState = request.getFlowState();
        if (Objects.nonNull(flowState)) {
            esOrderInvoice.setFlowState(flowState.toValue());
        }
        saveAll(Collections.singletonList(esOrderInvoice));
    }

    /**
     * 更新流程状态（订单状态）
     * @param request
     */
    public void updateFlowStateOrderInvoice(EsOrderInvoiceGenerateRequest request) {
        String orderInvoiceId = request.getOrderInvoiceId();
        FlowState flowState = request.getFlowState();
        Optional<EsOrderInvoice> esOrderInvoiceOptional = esOrderInvoiceRepository.findById(orderInvoiceId);
        if (esOrderInvoiceOptional.isPresent()) {
            EsOrderInvoice esOrderInvoice = esOrderInvoiceOptional.get();
            String flowStateQuery = esOrderInvoice.getFlowState();
            if (!StringUtils.equals(flowStateQuery, flowState.toValue())) {
                esOrderInvoice.setFlowState(flowState.toValue());
                this.save(esOrderInvoice);
            }
        }
    }

    /**
     *
     * 扭转发票状态
     * @param stateRequest
     */
    public void modifyState(EsOrderInvoiceModifyStateRequest stateRequest) {
        List<String> orderInvoiceIds = stateRequest.getOrderInvoiceIds();
        if (CollectionUtils.isEmpty(orderInvoiceIds)) {
            return;
        }
        Integer invoiceState = stateRequest.getInvoiceState();
        Iterable<EsOrderInvoice> orderInvoices = esOrderInvoiceRepository.findAllById(orderInvoiceIds);
        List<EsOrderInvoice> esOrderInvoices = Lists.newArrayList(orderInvoices);
        if (CollectionUtils.isNotEmpty(esOrderInvoices)) {
            List<EsOrderInvoice> invoiceList = this.reverseInvoiceState(esOrderInvoices, invoiceState);
            saveAll(invoiceList);
        }
    }


    /**
     * 扭转发票状态 ：已开票/待开票
     * @param esOrderInvoices
     * @param invoiceState
     * @return
     */
    private List<EsOrderInvoice> reverseInvoiceState(List<EsOrderInvoice> esOrderInvoices, Integer invoiceState) {
        return esOrderInvoices.stream().peek(orderInvoice -> {
            orderInvoice.setInvoiceState(invoiceState);
            if (Objects.equals(invoiceState, 1)) {
                orderInvoice.setInvoiceTime(LocalDateTime.now());
                return;
            }
            orderInvoice.setInvoiceTime(null);
        }).collect(Collectors.toList());
    }

    /**
     * 初始化订单开票数据
     *
     * @param request
     */
    public void init(EsOrderInvoiceInitRequest request) {
        boolean flg = true;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();

        OrderInvoiceFindAllRequest queryRequest = KsBeanUtil.convert(request, OrderInvoiceFindAllRequest.class);
        try {
            while (flg) {
                if(CollectionUtils.isNotEmpty(request.getIdList())){
                    pageNum = 0;
                    pageSize = request.getIdList().size();
                    flg = false;
                }
                queryRequest.putSort("createTime", SortType.DESC.toValue());
                queryRequest.setPageNum(pageNum);
                queryRequest.setPageSize(pageSize);
                List<OrderInvoiceVO> orderInvoiceVOList =
                        orderInvoiceQueryProvider.findAll(queryRequest).getContext().getValue().getContent();
                if (CollectionUtils.isEmpty(orderInvoiceVOList)) {
                    flg = false;
                    log.info("==========ES初始化订单开票数据，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsOrderInvoice> list = orderInvoiceVOList.stream().map(orderInvoiceVO -> {
                        EsOrderInvoice esOrderInvoice  = new EsOrderInvoice();
                        KsBeanUtil.copyPropertiesThird(orderInvoiceVO, esOrderInvoice);
                        //支付状态
                        if (Objects.nonNull(orderInvoiceVO.getPayOrder())) {
                            esOrderInvoice.setPayOrderStatus(orderInvoiceVO.getPayOrder().getPayOrderStatus().toValue());
                        }
                        //开票类型
                        if (Objects.nonNull(orderInvoiceVO.getInvoiceType())) {
                            esOrderInvoice.setInvoiceType(orderInvoiceVO.getInvoiceType().toValue());
                        }
                        //开票状态
                        if (Objects.nonNull(orderInvoiceVO.getInvoiceState())) {
                            esOrderInvoice.setInvoiceState(orderInvoiceVO.getInvoiceState().toValue());
                        }
                        //订单相关信息
                        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                                .tid(orderInvoiceVO.getOrderNo()).build()).getContext().getTradeVO();
                        if(Objects.nonNull(trade)){
                            esOrderInvoice.setOrderNo(trade.getId());
                            if(Objects.nonNull(trade.getTradePrice())){
                                esOrderInvoice.setOrderPrice(trade.getTradePrice().getTotalPrice());
                            }
                            if(Objects.nonNull(trade.getBuyer())){
                                //客户相关信息
                                esOrderInvoice.setCustomerId(trade.getBuyer().getId());
                                esOrderInvoice.setCustomerName(trade.getBuyer().getName());
                            }
                            //商家名称 兼容老数据
                            SupplierVO supplier;
                            if ( ( supplier = trade.getSupplier()) != null) {
                                esOrderInvoice.setSupplierName( supplier.getSupplierName());
                                //如何是o2o商家，缓存门店名称
                                if (supplier.getStoreType() == StoreType.O2O) {
                                    esOrderInvoice.setStoreName(supplier.getStoreName());
                                }
                            }
                            //流程状态（订单订单状态）
                            TradeStateVO tradeState = trade.getTradeState();
                            if(Objects.nonNull(tradeState) && Objects.nonNull(tradeState.getFlowState())){
                                esOrderInvoice.setFlowState(tradeState.getFlowState().toValue());
                            }
                        }
                        return esOrderInvoice;
                    }).collect(Collectors.toList());
                    saveAll(list);
                    log.info("==========ES初始化订单开票数据成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.error("==========ES初始化订单开票数据异常，异常pageNum:{}==============", pageNum, e);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040012, new Object[]{pageNum});
        }

    }

    private void save(EsOrderInvoice esOrderInvoice) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.ORDER_INVOICE, mapping);
        esOrderInvoiceRepository.save(esOrderInvoice);
    }


    private void saveAll(List<EsOrderInvoice> list) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.ORDER_INVOICE, mapping);
        esOrderInvoiceRepository.saveAll(list);
    }

    /**
     * 删除订单开票数据
     * @param request
     */
    public void delete(EsOrderInvoiceDeleteRequest request) {
        esOrderInvoiceRepository.deleteById(request.getOrderInvoiceId());
    }
}
