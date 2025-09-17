package com.wanmi.sbc.order.returnorder.service;

import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PayState;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByCustomerIdsRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailListByCustomerIdsResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailBaseVO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.enums.ReturnType;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnItem;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.value.ReturnEventLog;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPoints;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPrice;
import com.wanmi.sbc.order.returnorder.repository.ReturnOrderRepository;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Company;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 供应商退单-自动退款业务处理
 *
 * @author: daiyitian
 * @createDate: 2019/5/21 18:17
 * @version: 1.0
 */
@Slf4j
@Service
public class ProviderReturnOrderService {

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ProviderTradeService providerTradeService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private ThirdPlatformReturnOrderService thirdPlatformReturnOrderService;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    /**
     * 根据供应商订单id自动退款
     *
     * @param providerOrderId
     * @param description
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void autoReturnByProviderOrderId(String providerOrderId, String description) {
        log.info("===========供应商订单id：{}，自动局部退款开始========", providerOrderId);
        ProviderTrade providerTrade = providerTradeService.findbyId(providerOrderId);
        if (Objects.isNull(providerTrade)) {
            log.warn("===========供应商订单id：{}，供应订单不存在========", providerOrderId);
            return;
        }
        if (PayState.NOT_PAID.equals(providerTrade.getTradeState().getPayState())) {
            log.warn("===========供应商订单id：{}，状态未支付========", providerOrderId);
            return;
        }

        List<ReturnOrder> orders = returnOrderRepository.findByPtid(providerTrade.getId());
        if(CollectionUtils.isNotEmpty(orders) && orders.stream().anyMatch(o -> !ReturnFlowState.VOID.equals(o.getReturnFlowState()))) {
            log.warn("===========供应商订单id：{}，存在售后流程========", providerOrderId);
            return;
        }

        Trade trade = tradeService.detail(providerTrade.getParentId());
        if (Objects.isNull(trade)) {
            log.warn("===========供应商订单id：{}，商家订单不存在========", providerOrderId);
            return;
        }
        Operator operator =
                Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                        .PLATFORM).build();
        //1、生成退单数据
        List<ReturnOrder> returnOrders = Collections.singletonList(generateReturn(trade, providerTrade, operator, description));
        log.info("===========生成退单数据,详细信息：{}========", returnOrders);
        //未发货，直接退款
        Boolean returnMoney = DeliverStatus.NOT_YET_SHIPPED.equals(providerTrade.getTradeState().getDeliverStatus());
        List<ReturnOrder> returnOrderList = this.addReturnOrder(Collections.singletonList(trade), returnOrders, returnMoney);
        log.info("===========供应商订单id：{}，自动退款结束========", providerOrderId);
    }

    /**
     *
     * @param tradeList
     * @param returnOrders
     * @param returnMoney
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ReturnOrder> addReturnOrder(List<Trade> tradeList, List<ReturnOrder> returnOrders, Boolean returnMoney){
        List<ReturnOrder> returnOrderList = returnOrderRepository.saveAll(returnOrders);

        //1、根据退单数据，获取出会员详情基础信息集合
        List<CustomerDetailBaseVO> customerDetailBaseVOList = listCustomerDetailBaseByCustomerIds(returnOrderList);
        log.info("===========会员详情基础信息集合,详细信息：{}========", customerDetailBaseVOList);

        //2、生成退款单数据集合
        List<RefundOrder> refundOrderList =
                getRefundOrderByReturnOrderListAndCustomerDetailBaseVOList(returnOrderList, customerDetailBaseVOList);

        refundOrderList = refundOrderService.batchAdd(refundOrderList);
        log.info("===========生成退款单数据集合,详细信息：{}========", refundOrderList);

        //3、自动退款-仅处理线上支付
        if(Boolean.TRUE.equals(returnMoney)) {
            List<Trade> trades = tradeList.stream()
                    .filter(t -> !Integer.valueOf(PayType.OFFLINE.toValue()).equals(NumberUtils.toInt(t.getPayInfo().getPayTypeId()))).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(trades)) {
                Operator operator =
                        Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                                .PLATFORM).build();
                refundOrderService.autoRefund(trades, returnOrderList, refundOrderList, operator, null);
            }
        }
        return returnOrderList;
    }

    private ReturnOrder generateReturn(Trade trade, ProviderTrade providerTrade, Operator operator, String description){
        List<TradeItem> tradeItems = providerTrade.getTradeItems();

        ReturnOrder returnOrder = new ReturnOrder();
        String rid = generatorService.generate("R");
        //退单号
        returnOrder.setId(rid);
        returnOrder.setPtid(providerTrade.getId());
        //订单编号
        returnOrder.setTid(providerTrade.getParentId());
        //购买人信息
        returnOrder.setBuyer(providerTrade.getBuyer());
        //商家信息
        returnOrder.setCompany(Company.builder().companyInfoId(trade.getSupplier().getSupplierId())
                .companyCode(trade.getSupplier().getSupplierCode()).supplierName(trade.getSupplier().getSupplierName())
                .storeId(trade.getSupplier().getStoreId()).storeName(trade.getSupplier().getStoreName()).storeType(trade.getSupplier().getStoreType())
                .companyType(trade.getSupplier().getIsSelf() ? BoolFlag.NO : BoolFlag.YES)
                .build());
        //封装供应商信息
        returnOrder.setProviderId(providerTrade.getSupplier().getStoreId().toString());
        returnOrder.setProviderCode(providerTrade.getSupplier().getSupplierCode());
        returnOrder.setProviderName(providerTrade.getSupplier().getSupplierName());
        returnOrder.setProviderCompanyInfoId(providerTrade.getSupplier().getSupplierId());
        //描述信息
        returnOrder.setDescription(description);
        //退货商品
        returnOrder.setReturnItems(returnOrderService.chgTradeTime(tradeItems));
        //退货赠品
        if(CollectionUtils.isNotEmpty(providerTrade.getGifts())) {
            returnOrder.setReturnGifts(returnOrderService.chgTradeTime(providerTrade.getGifts()));
        }
        if(CollectionUtils.isNotEmpty(providerTrade.getPreferential())) {
            returnOrder.setReturnPreferential(returnOrderService.chgTradeTime(providerTrade.getPreferential()));
        }
        TradePrice tradePrice = providerTrade.getTradePrice();
        ReturnPrice returnPrice = new ReturnPrice();

        returnPrice.setApplyPrice(tradePrice.getTotalPrice());
        returnPrice.setProviderTotalPrice(returnOrder.getReturnItems().stream().map(ReturnItem::getProviderPrice).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        //实退金额
        returnPrice.setActualReturnPrice(returnPrice.getApplyPrice());
        //商品总金额
        returnPrice.setTotalPrice(tradePrice.getTotalPrice());
        //申请金额可用
        returnPrice.setApplyStatus(Boolean.TRUE);
        //处理运费
        returnPrice.setFee(tradePrice.getDeliveryPrice());
        //退货总金额
        returnOrder.setReturnPrice(returnPrice);
        //收货人信息
        returnOrder.setConsignee(providerTrade.getConsignee());
        //退货单状态
        returnOrder.setReturnFlowState(ReturnFlowState.AUDIT);
        //记录日志
        returnOrder.appendReturnEventLog(
                new ReturnEventLog(operator, "订单自动退单", "订单自动退单", "订单自动退款", LocalDateTime.now())
        );
        // 支付方式
        returnOrder.setPayType(PayType.valueOf(providerTrade.getPayInfo().getPayTypeName()));
        //退单类型
        returnOrder.setReturnType(ReturnType.REFUND);
        //退单来源
        returnOrder.setPlatform(Platform.CUSTOMER);
        //退积分信息
        returnOrder.setReturnPoints(ReturnPoints.builder()
                .applyPoints(Objects.isNull(tradePrice.getPoints()) ? NumberUtils.LONG_ZERO :
                        tradePrice.getPoints())
                .actualPoints(Objects.isNull(tradePrice.getPoints()) ? NumberUtils.LONG_ZERO :
                        tradePrice.getPoints()).build());
        //创建时间
        returnOrder.setCreateTime(LocalDateTime.now());
        return returnOrder;
    }

    /**
     * 根据会员ID集合查询会员详情基础信息集合
     *
     * @param returnOrderList
     * @return
     */
    private List<CustomerDetailBaseVO> listCustomerDetailBaseByCustomerIds(List<ReturnOrder> returnOrderList) {
        List<String> customerIds =
                returnOrderList.stream().map(returnOrder -> returnOrder.getBuyer().getId()).collect(Collectors.toList());
        BaseResponse<CustomerDetailListByCustomerIdsResponse> baseResponse =
                customerDetailQueryProvider.listCustomerDetailBaseByCustomerIds(new CustomerDetailListByCustomerIdsRequest(customerIds));
        return baseResponse.getContext().getList();
    }

    /**
     * 根据退单数据、会员详情信息集合，生成对应的退款单数据集合
     *
     * @param returnOrderList
     * @param customerDetailBaseVOList
     * @return
     */
    private List<RefundOrder> getRefundOrderByReturnOrderListAndCustomerDetailBaseVOList(List<ReturnOrder> returnOrderList, List<CustomerDetailBaseVO> customerDetailBaseVOList) {
        List<RefundOrder> refundOrderList = new ArrayList<>(returnOrderList.size());
        Map<String, String> map =
                customerDetailBaseVOList.stream().collect(Collectors.toMap(CustomerDetailBaseVO::getCustomerId,
                        CustomerDetailBaseVO::getCustomerDetailId));
        for (ReturnOrder returnOrder : returnOrderList) {
            RefundOrder refundOrder = new RefundOrder();
            refundOrder.setReturnOrderCode(returnOrder.getId());
            refundOrder.setCustomerDetailId(map.get(returnOrder.getBuyer().getId()));
            refundOrder.setCreateTime(LocalDateTime.now());
            refundOrder.setRefundCode(generatorService.generateRid());
            refundOrder.setRefundStatus(RefundStatus.TODO);
            refundOrder.setReturnPrice(returnOrder.getReturnPrice().getApplyPrice());
            refundOrder.setReturnPoints(Objects.nonNull(returnOrder.getReturnPoints()) ?
                    returnOrder.getReturnPoints().getApplyPoints() : null);
            refundOrder.setGiftCardPrice(returnOrder.getReturnPrice().getGiftCardPrice());
            refundOrder.setDelFlag(DeleteFlag.NO);
            refundOrder.setPayType(returnOrder.getPayType());
            refundOrder.setSupplierId(returnOrder.getCompany().getCompanyInfoId());
            refundOrderList.add(refundOrder);
        }
        return refundOrderList;
    }
}
