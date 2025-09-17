package com.wanmi.sbc.order.returnorder.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.enums.node.OrderProcessType;
import com.wanmi.sbc.common.enums.node.ReturnOrderProcessType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByCustomerIdsRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailListByCustomerIdsResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailBaseVO;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelOrderProvider;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelRefundProvider;
import com.wanmi.sbc.empower.api.provider.channel.goods.ChannelGoodsSyncProvider;
import com.wanmi.sbc.empower.api.request.channel.base.*;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncByIdsRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderQuerySkuListResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelGoodsSyncQueryResponse;
import com.wanmi.sbc.empower.bean.dto.channel.base.goods.ChannelGoodsInfoDto;
import com.wanmi.sbc.empower.bean.vo.ChannelRefundReasonVO;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.order.api.constant.RefundReasonConstants;
import com.wanmi.sbc.order.api.request.distribution.ReturnOrderSendMQRequest;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderApplyRequest;
import com.wanmi.sbc.order.api.request.thirdplatformreturn.ThirdPlatformReturnOrderAutoApplySubRequest;
import com.wanmi.sbc.order.api.response.linkedmall.ThirdPlatformReturnReasonResponse;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.enums.ReturnType;
import com.wanmi.sbc.order.bean.vo.LinkedMallReasonVO;
import com.wanmi.sbc.order.common.OrderCommonService;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnItem;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.value.ReturnEventLog;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPoints;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPrice;
import com.wanmi.sbc.order.returnorder.mq.ReturnOrderProducerService;
import com.wanmi.sbc.order.returnorder.repository.ReturnOrderRepository;
import com.wanmi.sbc.order.thirdplatformtrade.model.entity.ThirdPlatformSuborderItem;
import com.wanmi.sbc.order.thirdplatformtrade.model.root.ThirdPlatformTrade;
import com.wanmi.sbc.order.thirdplatformtrade.repository.ThirdPlatformTradeRepository;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.Company;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.repository.ProviderTradeRepository;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 第三方订单-自动退款业务处理
 *
 * @author: Geek Wang
 * @createDate: 2019/5/21 18:17
 * @version: 1.0
 */
@Slf4j
@Service
public class ThirdPlatformReturnOrderService {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private ReturnOrderProducerService returnOrderProducerService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private OrderProducerService orderProducerService;

    @Autowired
    private OrderCommonService orderCommonService;

    @Autowired
    private ProviderTradeService providerTradeService;

    @Autowired
    private ThirdPlatformTradeRepository thirdPlatformTradeRepository;

    @Autowired
    private ChannelOrderProvider channelOrderProvider;

    @Autowired
    private ChannelRefundProvider channelRefundProvider;

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

    @Autowired
    private ProviderTradeRepository providerTradeRepository;

    @Autowired
    private ChannelGoodsSyncProvider channelGoodsSyncProvider;

    @Autowired
    private ReturnOrderService returnOrderService;

    /**
     * 根据订单号处理自动退款业务
     *
     * @param businessId
     */
    public void autoOrderRefundByBusinessId(String businessId) {
        this.autoOrderRefund(
                orderCommonService.findTradesByBusinessId(businessId).stream()
                        .filter(
                                t ->
                                        CollectionUtils.isNotEmpty(t.getThirdPlatformTypes())
                                                && (t.getThirdPlatformTypes()
                                                                .contains(
                                                                        ThirdPlatformType
                                                                                .LINKED_MALL)
                                                        || t.getThirdPlatformTypes()
                                                                .contains(ThirdPlatformType.VOP)))
                        .collect(Collectors.toList()));
    }

    /**
     * 根据订单集合处理自动退款业务
     *
     * @param tradeList
     */
    @Transactional
    public void autoOrderRefund(List<Trade> tradeList) {
        String businessId = tradeList.get(0).getId();
        if (tradeList.size() > 0) {
            businessId = tradeList.get(0).getParentId();
        }
        log.info("===========订单业务id：{}，自动退款开始========", businessId);
        Operator operator =
                Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                        .PLATFORM).build();
        //1、生成退单数据
        List<ReturnOrder> returnOrders = getReturnOrderByTradeList(tradeList, operator);
        log.info("===========生成退单数据,详细信息：{}========", returnOrders);
        List<ReturnOrder> returnOrderList = this.addReturnOrder(tradeList, returnOrders);
        //2、处理其他无关业务
        handleOther(tradeList, returnOrderList);
        log.info("===========订单业务id：{}，自动退款结束========", businessId);
    }

    @GlobalTransactional
    @Transactional
    public List<ReturnOrder> addReturnOrder(List<Trade> tradeList, List<ReturnOrder> returnOrders){
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
        List<Trade> trades = tradeList.stream()
                .filter(t -> !Integer.valueOf(PayType.OFFLINE.toValue()).equals(NumberUtils.toInt(t.getPayInfo().getPayTypeId()))).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(trades)) {
            Operator operator =
                    Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                            .PLATFORM).build();
            refundOrderService.autoRefund(trades, returnOrderList, refundOrderList, operator , null);
        }
        return returnOrderList;
    }


    /**
     * 根据订单数据生成对应退单数据
     *
     * @param tradeList
     * @return
     */
    private List<ReturnOrder> getReturnOrderByTradeList(List<Trade> tradeList, Operator operator) {
        List<ReturnOrder> returnOrderList = new ArrayList<>(tradeList.size());
        List<String> tidList = tradeList.stream().map(Trade::getId).collect(Collectors.toList());
        Map<String, List<ProviderTrade>> providerTradeMap = providerTradeService.findListByParentIdList(tidList).stream().collect(Collectors.groupingBy(ProviderTrade::getParentId));
        for (Trade trade : tradeList) {
            List<ProviderTrade> providerTrades = providerTradeMap.getOrDefault(trade.getId(), Collections.emptyList());
            if(CollectionUtils.isNotEmpty(providerTrades)) {
                for (ProviderTrade providerTrade : providerTrades) {
                    returnOrderList.add(generateReturn(trade, providerTrade, operator));
                }
            }else {
                returnOrderList.add(generateReturn(trade, null, operator));
            }
        }
        return returnOrderList;
    }

    private ReturnOrder generateReturn(Trade trade, ProviderTrade providerTrade, Operator operator){
        List<TradeItem> tradeItems = trade.getTradeItems();
        if(providerTrade != null){
            tradeItems = providerTrade.getTradeItems();
        }

        ReturnOrder returnOrder = new ReturnOrder();
        String rid = generatorService.generate("R");
        //退单号
        returnOrder.setId(rid);
        if(providerTrade!=null) {
            returnOrder.setPtid(providerTrade.getId());
        }
        //订单编号
        returnOrder.setTid(trade.getId());
        //购买人信息
        returnOrder.setBuyer(trade.getBuyer());
        //商家信息
        returnOrder.setCompany(Company.builder().companyInfoId(trade.getSupplier().getSupplierId())
                .companyCode(trade.getSupplier().getSupplierCode()).supplierName(trade.getSupplier().getSupplierName())
                .storeId(trade.getSupplier().getStoreId()).storeName(trade.getSupplier().getStoreName()).storeType(trade.getSupplier().getStoreType())
                .companyType(trade.getSupplier().getIsSelf() ? BoolFlag.NO : BoolFlag.YES)
                .build());
        //描述信息
        returnOrder.setDescription(RefundReasonConstants.Q_ORDER_SERVICE_THIRD_AUTO_REFUND_USER);
        //退货商品
        returnOrder.setReturnItems(tradeItems.stream().map(item -> ReturnItem.builder()
                .num(item.getNum().intValue())
                .skuId(item.getSkuId())
                .skuNo(item.getSkuNo())
                .pic(item.getPic())
                .skuName(item.getSkuName())
                .unit(item.getUnit())
                .price(item.getPrice())
                .supplyPrice(item.getSupplyPrice())
                .providerPrice(Objects.isNull(item.getSupplyPrice())?BigDecimal.ZERO:item.getSupplyPrice().multiply(BigDecimal.valueOf(item.getNum())))
                .splitPrice(item.getSplitPrice())
                .specDetails(item.getSpecDetails())
                .splitPoint(item.getPoints())
                .build()).collect(Collectors.toList()));
        TradePrice tradePrice = trade.getTradePrice();
        if(providerTrade!=null){
            tradePrice = providerTrade.getTradePrice();
        }
        ReturnPrice returnPrice = new ReturnPrice();

        returnPrice.setApplyPrice(tradePrice.getTotalPrice());
        returnPrice.setProviderTotalPrice(returnOrder.getReturnItems().stream().map(ReturnItem::getProviderPrice).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        //实退金额
        returnPrice.setActualReturnPrice(returnPrice.getApplyPrice());
        //商品总金额
        returnPrice.setTotalPrice(tradePrice.getTotalPrice());
        //申请金额可用
        returnPrice.setApplyStatus(Boolean.TRUE);
        //退货总金额
        returnOrder.setReturnPrice(returnPrice);
        //收货人信息
        returnOrder.setConsignee(trade.getConsignee());
        //退货单状态
        returnOrder.setReturnFlowState(ReturnFlowState.AUDIT);
        //记录日志
        returnOrder.appendReturnEventLog(
                new ReturnEventLog(operator, "订单自动退单", "订单自动退单", "订单自动退款", LocalDateTime.now())
        );

        // 支付方式
        returnOrder.setPayType(PayType.valueOf(trade.getPayInfo().getPayTypeName()));
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
     * 订单日志、发送MQ消息
     *
     * @param tradeList
     * @param returnOrderList
     */
    private void handleOther(List<Trade> tradeList, List<ReturnOrder> returnOrderList) {
        Map<String, List<ReturnOrder>> map = returnOrderList.stream().collect(Collectors.groupingBy(ReturnOrder::getTid));
        for (Trade trade : tradeList) {
            map.getOrDefault(trade.getId(), Collections.emptyList()).forEach(returnOrder -> {
                ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                        .addFlag(true)
                        .customerId(trade.getBuyer().getId())
                        .orderId(trade.getId())
                        .returnId(returnOrder.getId())
                        .build();
                returnOrderProducerService.returnOrderFlow(sendMQRequest);
            });

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("type", NodeType.RETURN_ORDER_PROGRESS_RATE.toValue());
            paramMap.put("node", ReturnOrderProcessType.REFUND_CHECK_PASS.toValue());
            paramMap.put("id", map.get(trade.getId()).get(0).getId());

            MessageMQRequest messageMQRequest = new MessageMQRequest();
            messageMQRequest.setNodeCode(OrderProcessType.THIRD_PAY_ERROR_AUTO_REFUND.getType());
            messageMQRequest.setNodeType(NodeType.RETURN_ORDER_PROGRESS_RATE.toValue());
            messageMQRequest.setParams(Lists.newArrayList(trade.getTradeItems().get(0).getSkuName()));
            messageMQRequest.setRouteParam(paramMap);
            messageMQRequest.setCustomerId(trade.getBuyer().getId());
            messageMQRequest.setPic(trade.getTradeItems().get(0).getPic());
            messageMQRequest.setMobile(trade.getBuyer().getAccount());
            orderProducerService.sendMessage(messageMQRequest);
        }

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

    public List<ReturnOrder> splitReturnOrder(ReturnOrder providerReturnOrder, ThirdPlatformType thirdPlatformType) {
        GoodsSource goodsSource = GoodsSource.LINKED_MALL;
        if(ThirdPlatformType.LINKED_MALL.equals(thirdPlatformType)){
            goodsSource = GoodsSource.LINKED_MALL;
        }
        if(ThirdPlatformType.VOP.equals(thirdPlatformType)){
            goodsSource = GoodsSource.VOP;
        }
        log.info("===========linkedMall退单拆分业务id：{}，开始========", providerReturnOrder.getId());
        //合并items
        List<ReturnItem> zipItems =
                this.zipLinkedMallItem(this.zipLinkedMallItem(providerReturnOrder.getReturnItems(),
                        providerReturnOrder.getReturnGifts(), thirdPlatformType),
                        providerReturnOrder.getReturnPreferential(), thirdPlatformType);
        if(CollectionUtils.isEmpty(zipItems)){
            log.info("===========linkedMall退单不含linkedMall数据：{}========", providerReturnOrder.getId());
            return Collections.emptyList();
        }
        List<ThirdPlatformTrade> tradeList = thirdPlatformTradeRepository.findListByParentId(providerReturnOrder.getPtid());
        tradeList = tradeList.stream()
                .filter(thirdPlatformTrade -> thirdPlatformType.equals(thirdPlatformTrade.getThirdPlatformType()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tradeList)) {
            log.info("===========linkedMall退单拆分业务id：{}，没有数据========", providerReturnOrder.getId());
            return Collections.emptyList();
        }
        //VOP暂不拆分退单
        if(ThirdPlatformType.VOP.equals(thirdPlatformType)){
            ThirdPlatformTrade trade = tradeList.get(0);
            providerReturnOrder.setThirdPlatformTradeId(trade.getId());
            providerReturnOrder.setThirdPlatformOrderId(CollectionUtils.isNotEmpty(trade.getThirdPlatformOrderIds()) ? trade.getThirdPlatformOrderIds().get(0): null);
            providerReturnOrder.setThirdPlatformType(thirdPlatformType);
            providerReturnOrder.setThirdSellerId(trade.getThirdSellerId());
            providerReturnOrder.setThirdSellerName(trade.getThirdSellerName());
            providerReturnOrder.setThirdPlatformPayErrorFlag(trade.getThirdPlatformPayErrorFlag());
            providerReturnOrder.setOutOrderId(CollectionUtils.isNotEmpty(trade.getOutOrderIds())?trade.getOutOrderIds().get(0):null);
            return Collections.singletonList(providerReturnOrder);
        }

        String userId = null;
        if (Objects.nonNull(providerReturnOrder.getBuyer()) && StringUtils.isNotBlank(providerReturnOrder.getBuyer().getId())) {
            userId = providerReturnOrder.getBuyer().getId();
        }
        if (userId == null) {
            userId = tradeList.get(0).getBuyer().getId();
        }

        //形成Map<skuId,linkedTrade>
        List<String> thirdIds = tradeList.stream().flatMap(t -> t.getThirdPlatformOrderIds().stream()).distinct().collect(Collectors.toList());
        ChannelOrderQuerySkuListRequest channelOrderQuerySkuListRequest = ChannelOrderQuerySkuListRequest.builder()
                .channelOrderIdList(thirdIds)
                .thirdPlatformType(thirdPlatformType)
                .build();
        channelOrderQuerySkuListRequest.setUserId(userId);
        ChannelOrderQuerySkuListResponse channelOrderQuerySkuListResponse = channelOrderProvider.batchQueryOrderSkuList(channelOrderQuerySkuListRequest)
                .getContext();
        Map<String, ChannelOrderQuerySkuListResponse.ChannelOrder> lmOrderMap = Collections.emptyMap();
        if(channelOrderQuerySkuListResponse != null && CollectionUtils.isNotEmpty(channelOrderQuerySkuListResponse.getChannelOrderList())){
            lmOrderMap = channelOrderQuerySkuListResponse.getChannelOrderList().stream()
                    .collect(Collectors.toMap(ChannelOrderQuerySkuListResponse.ChannelOrder::getChannelOrderId, Function.identity()));
        }
        //形成Map<skuId,ThirdPlatformTrade>
        Map<String, ThirdPlatformTrade> skuTradeMap = new HashMap<>();
        Map<String, TradeItem> skuItemMap = new HashMap<>();
        tradeList.forEach(trade -> {
            if (CollectionUtils.isNotEmpty(trade.getTradeItems())) {
                trade.getTradeItems().forEach(i -> {
                    skuTradeMap.put(i.getSkuId(), trade);
                    skuItemMap.put(i.getSkuId(), i);
                });

            }
            if (CollectionUtils.isNotEmpty(trade.getGifts())) {
                trade.getGifts().forEach(i -> {
                    skuTradeMap.put(i.getSkuId(), trade);
                    skuItemMap.put(i.getSkuId(), i);
                });
            }
            if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                trade.getPreferential().forEach(i -> {
                    skuTradeMap.put(i.getSkuId(), trade);
                    skuItemMap.put(i.getSkuId(), i);
                });
            }
        });

        //形成<skuId,returnItem> 商品
        Map<String, ReturnItem> returnItemMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(providerReturnOrder.getReturnItems())){
            returnItemMap.putAll(providerReturnOrder.getReturnItems().stream().collect(Collectors.toMap(ReturnItem::getSkuId, Function.identity())));
        }

        //形成<skuId,returnItem> 赠品
        Map<String, ReturnItem> giftItemMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(providerReturnOrder.getReturnGifts())){
            giftItemMap.putAll(providerReturnOrder.getReturnGifts().stream().collect(Collectors.toMap(ReturnItem::getSkuId, Function.identity())));
        }

        Map<Long, Map<String, ReturnItem>> preferentialMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(providerReturnOrder.getReturnPreferential())){
            preferentialMap.putAll(providerReturnOrder.getReturnPreferential().stream()
                    .collect(Collectors.groupingBy(ReturnItem::getMarketingId, Collectors.toMap(ReturnItem::getSkuId, Function.identity()))));
        }

        List<ReturnOrder> returnOrders = new ArrayList<>(2);
        for(ReturnItem item: zipItems){
            ThirdPlatformTrade trade = skuTradeMap.get(item.getSkuId());
            TradeItem tradeItem = skuItemMap.get(item.getSkuId());
            if (trade == null || tradeItem == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            ChannelOrderQuerySkuListResponse.ChannelOrder lmOrder = lmOrderMap.get(NumberUtils.toLong(trade.getThirdPlatformOrderIds().get(0)));
            ReturnOrder thirdReturnOrder = KsBeanUtil.convert(providerReturnOrder, ReturnOrder.class);
            //填装普通sku
            List<ReturnItem> returnItems = new ArrayList<>();
            ReturnItem returnItem = returnItemMap.get(item.getSkuId());
            if(returnItem != null){
                returnItem.setThirdPlatformSpuId(tradeItem.getThirdPlatformSpuId());
                returnItem.setThirdPlatformSkuId(tradeItem.getThirdPlatformSkuId());
                returnItem.setGoodsSource(goodsSource.toValue());
                returnItem.setThirdPlatformType(thirdPlatformType);
                //根据skuId和spuId 设置相应的subLmOrderid
                if (Objects.nonNull(lmOrder) && CollectionUtils.isNotEmpty(lmOrder.getChannelOrderSkuList())) {
                    lmOrder.getChannelOrderSkuList().stream().filter(s -> s.getChannelSpuId().equals(tradeItem.getThirdPlatformSpuId())
                            && s.getChannelSkuId().equals(tradeItem.getThirdPlatformSkuId())).findFirst()
                            .ifPresent(sub -> returnItem.setThirdPlatformSubOrderId(sub.getChannelSubOrderId()));
                }
                returnItems.add(returnItem);
            }
            thirdReturnOrder.setReturnItems(returnItems);

            //填装赠品
            List<ReturnItem> returnGifts = new ArrayList<>();
            ReturnItem returnGift = giftItemMap.get(item.getSkuId());
            if(returnGift != null){
                returnGift.setThirdPlatformSpuId(tradeItem.getThirdPlatformSpuId());
                returnGift.setThirdPlatformSkuId(tradeItem.getThirdPlatformSkuId());
                returnGift.setGoodsSource(goodsSource.toValue());
                returnGift.setThirdPlatformType(thirdPlatformType);
                //根据skuId和spuId 设置相应的subLmOrderid
                if (Objects.nonNull(lmOrder) && CollectionUtils.isNotEmpty(lmOrder.getChannelOrderSkuList())) {
                    lmOrder.getChannelOrderSkuList().stream().filter(s -> s.getChannelSpuId().equals(tradeItem.getThirdPlatformSpuId())
                            && s.getChannelSkuId().equals(tradeItem.getThirdPlatformSkuId())).findFirst()
                            .ifPresent(sub -> returnGift.setThirdPlatformSubOrderId(sub.getChannelSubOrderId()));
                }
                returnGifts.add(returnGift);
            }
            thirdReturnOrder.setReturnGifts(returnGifts);

            List<ReturnItem> returnPreferentialList = new ArrayList<>();
            ReturnItem returnPreferential =
                    preferentialMap.getOrDefault(item.getMarketingId(), new HashMap<>()).get(item.getSkuId());
            if(returnPreferential != null){
                returnPreferential.setThirdPlatformSpuId(tradeItem.getThirdPlatformSpuId());
                returnPreferential.setThirdPlatformSkuId(tradeItem.getThirdPlatformSkuId());
                returnPreferential.setGoodsSource(goodsSource.toValue());
                returnPreferential.setThirdPlatformType(thirdPlatformType);
                //根据skuId和spuId 设置相应的subLmOrderid
                if (Objects.nonNull(lmOrder) && CollectionUtils.isNotEmpty(lmOrder.getChannelOrderSkuList())) {
                    lmOrder.getChannelOrderSkuList().stream().filter(s -> s.getChannelSpuId().equals(tradeItem.getThirdPlatformSpuId())
                                    && s.getChannelSkuId().equals(tradeItem.getThirdPlatformSkuId())).findFirst()
                            .ifPresent(sub -> returnPreferential.setThirdPlatformSubOrderId(sub.getChannelSubOrderId()));
                }
                returnPreferentialList.add(returnPreferential);
            }
            thirdReturnOrder.setReturnPreferential(returnPreferentialList);
            thirdReturnOrder.setThirdPlatformTradeId(trade.getId());
            thirdReturnOrder.setThirdPlatformOrderId(trade.getThirdPlatformOrderIds().get(0));
            thirdReturnOrder.setThirdPlatformType(thirdPlatformType);
            thirdReturnOrder.setThirdSellerId(trade.getThirdSellerId());
            thirdReturnOrder.setThirdSellerName(trade.getThirdSellerName());
            thirdReturnOrder.setThirdPlatformPayErrorFlag(trade.getThirdPlatformPayErrorFlag());
            thirdReturnOrder.setOutOrderId(CollectionUtils.isNotEmpty(trade.getOutOrderIds())?trade.getOutOrderIds().get(0):null);
            returnOrders.add(thirdReturnOrder);
        }
        log.info("===========linkedMall退单拆分退单数量：{}，结束========", returnOrders.size());
        return returnOrders;
    }

    /**
     * 合并退单明细数据
     * @param tradeItems 普通商品
     * @param gifts 赠品
     * @return 新合并数据
     */
    private List<ReturnItem> zipLinkedMallItem(List<ReturnItem> tradeItems, List<ReturnItem> gifts, ThirdPlatformType thirdPlatformType) {
        List<ReturnItem> items = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tradeItems)) {
            items.addAll(tradeItems.stream()
                    .filter(i -> Objects.equals(thirdPlatformType, i.getThirdPlatformType()))
                    .map(i -> {
                        ReturnItem item = new ReturnItem();
                        item.setSkuId(i.getSkuId());
                        item.setThirdPlatformType(i.getThirdPlatformType());
                        item.setThirdPlatformSpuId(i.getThirdPlatformSpuId());
                        item.setThirdPlatformSkuId(i.getThirdPlatformSkuId());
                        item.setThirdPlatformSubOrderId(i.getThirdPlatformSubOrderId());
                        item.setNum(i.getNum());
                        item.setSupplyPrice(i.getSupplyPrice());
                        return item;
                    }).collect(Collectors.toList()));
        }

        if (CollectionUtils.isNotEmpty(gifts)) {
            List<ReturnItem> giftItems = gifts.stream()
                    .filter(i -> Objects.equals(thirdPlatformType, i.getThirdPlatformType()))
                    .map(i -> {
                        ReturnItem item = new ReturnItem();
                        item.setSkuId(i.getSkuId());
                        item.setThirdPlatformType(i.getThirdPlatformType());
                        item.setThirdPlatformSpuId(i.getThirdPlatformSpuId());
                        item.setThirdPlatformSkuId(i.getThirdPlatformSkuId());
                        item.setThirdPlatformSubOrderId(i.getThirdPlatformSubOrderId());
                        item.setNum(i.getNum());
                        item.setSupplyPrice(i.getSupplyPrice());
                        return item;
                    }).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(items)) {
                items = IteratorUtils.zip(items, giftItems,
                        (a, b) -> a.getSkuId().equals(b.getSkuId()),
                        (a, b) -> {
                            a.setNum(a.getNum() + b.getNum());
                            if (StringUtils.isBlank(a.getThirdPlatformSubOrderId())) {
                                a.setThirdPlatformSubOrderId(b.getThirdPlatformSubOrderId());
                            }
                            if (a.getProviderPrice() != null && b.getProviderPrice() != null) {
                                a.setProviderPrice(a.getProviderPrice().add(b.getProviderPrice()));
                            }
                        });
            } else {
                items.addAll(giftItems);
            }
        }
        return items;
    }

    /**
     * 查询原因列表
     * @param rid
     * @return
     */
    public ThirdPlatformReturnReasonResponse reasons(String rid) {
        ThirdPlatformReturnReasonResponse response = new ThirdPlatformReturnReasonResponse();
        ReturnOrder returnOrder = returnOrderRepository.findById(rid).orElse(null);
        ThirdPlatformType thirdPlatformType = returnOrder.getThirdPlatformType();
        if (Objects.isNull(returnOrder) || Objects.isNull(thirdPlatformType)) {
            return response;
        }
        ProviderTrade trade = providerTradeRepository.findFirstById(returnOrder.getPtid());
        if (Objects.isNull(trade)) {
            return response;
        }

        //如果普通商品id不存在，则取赠品id
        ReturnItem item = null;
        if(CollectionUtils.isNotEmpty(returnOrder.getReturnItems())){
            item = returnOrder.getReturnItems().get(0);
        }else if(CollectionUtils.isNotEmpty(returnOrder.getReturnGifts())){
            item = returnOrder.getReturnGifts().get(0);
        }
        if(item == null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ChannelRefundReasonRequest refundRequest = new ChannelRefundReasonRequest();
        refundRequest.setThirdPlatformType(thirdPlatformType);
        refundRequest.setSubChannelOrderId(item.getThirdPlatformSubOrderId());
        refundRequest.setUserId(trade.getBuyer().getId());
        if (ReturnType.REFUND == returnOrder.getReturnType()) {
            refundRequest.setRefund(Boolean.TRUE);
        } else {
            refundRequest.setReturnGood(Boolean.TRUE);
        }
        if (ThirdPlatformType.LINKED_MALL.equals(thirdPlatformType)) {
            // 设定商品收货状态
            refundRequest.setGoodsStatus(chgGoodsStatus(trade, returnOrder.getReturnType()));
        }
        List<ChannelRefundReasonVO> reasonList = channelRefundProvider.listRefundReason(refundRequest).getContext().getReasonList();
        if (CollectionUtils.isNotEmpty(reasonList)) {
            response.setReasonList(reasonList.stream().map(i -> {
                LinkedMallReasonVO reason = new LinkedMallReasonVO();
                reason.setReasonTextId(i.getReasonTextId());
                reason.setReasonTips(i.getReasonTips());
                reason.setProofRequired(i.getProofRequired());
                reason.setRefundDescRequired(i.getRefundDescRequired());
                return reason;
            }).collect(Collectors.toList()));
        }
        response.setDescription(returnOrder.getDescription());
        response.setImages(returnOrder.getImages());
        return response;
    }

    /**
     * 转换商品状态
     * @param trade 订单
     * @param returnType 退款类型
     * @return 1.未收到货 2.已收到货 3.已寄回 4.未发货 5.卖家确认收货  6.已发货
     */
    private int chgGoodsStatus(ProviderTrade trade, ReturnType returnType){
        int goodsStatus = 4;
        TradeState state = trade.getTradeState();
        if (com.wanmi.sbc.order.bean.enums.FlowState.COMPLETED == state.getFlowState()) {
            goodsStatus = 2; //买家确认收货
            if (CollectionUtils.isNotEmpty(trade.getTradeEventLogs())) {
                //当完成事件判断操作人id与买家id不一致时，就是卖家确认收货
                if(trade.getTradeEventLogs().stream().anyMatch(s -> com.wanmi.sbc.order.bean.enums.FlowState.COMPLETED.getDescription().equals(s.getEventType())
                        && s.getOperator() != null
                        && (!trade.getBuyer().getId().equals(s.getOperator().getUserId())))){
                    //卖家确认收货
                    goodsStatus = 5;
                }
            }
        } else if (DeliverStatus.SHIPPED == state.getDeliverStatus()) {
            goodsStatus = 6;
            if (ReturnType.REFUND.equals(returnType)) {
                goodsStatus = 1;
            }
        }
        return goodsStatus;
    }

    /**
     * linkedMall申请
     * @param request
     */
    @Transactional
    public void apply(ThirdPlatformReturnOrderApplyRequest request) {
        ReturnOrder returnOrder = returnOrderRepository.findById(request.getRid()).orElse(null);
        ThirdPlatformType thirdPlatformType = returnOrder.getThirdPlatformType();
        if (Objects.isNull(returnOrder) || Objects.isNull(thirdPlatformType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ProviderTrade trade = providerTradeRepository.findFirstById(returnOrder.getPtid());
        if (Objects.isNull(trade)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ReturnItem item = this.zipLinkedMallItem(returnOrder.getReturnItems(), returnOrder.getReturnGifts(), thirdPlatformType).get(0);
        ChannelRefundApplyRequest refundRequest = new ChannelRefundApplyRequest();
        refundRequest.setThirdPlatformType(thirdPlatformType);
        refundRequest.setSubChannelOrderId(item.getThirdPlatformSubOrderId());
        refundRequest.setUserId(trade.getBuyer().getId());
        if (ReturnType.REFUND == returnOrder.getReturnType()) {
            refundRequest.setRefund(Boolean.TRUE);
        } else {
            refundRequest.setReturnGood(Boolean.TRUE);
        }
        BigDecimal price = item.getProviderPrice();
        if(price == null) {
            if (Objects.nonNull(item.getSupplyPrice())) {
                price = item.getSupplyPrice().multiply(BigDecimal.valueOf(item.getNum()));
            } else if (StringUtils.isNotBlank(item.getThirdPlatformSpuId())) {
                //远程查第三方平台供货价
                ChannelGoodsSyncByIdsRequest goodsRequest = new ChannelGoodsSyncByIdsRequest();
                goodsRequest.setThirdPlatformType(thirdPlatformType);
                goodsRequest.setSpuIds(Collections.singletonList(NumberUtils.toLong(item.getThirdPlatformSpuId())));
                List<ChannelGoodsSyncQueryResponse> lmSpu = channelGoodsSyncProvider.syncGetChannelGoods(goodsRequest).getContext();
                if (CollectionUtils.isEmpty(lmSpu)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
                }
                ChannelGoodsInfoDto sku = lmSpu.get(0).getGoodsInfos().stream()
                        .filter(k -> k.getThirdPlatformSkuId().equals(item.getThirdPlatformSkuId())).findFirst()
                        .orElse(null);
                if (sku == null) {
                    price = BigDecimal.ZERO;
                } else {
                    price = sku.getSupplyPrice().multiply(BigDecimal.valueOf(item.getNum()));
                }
            } else {
                price = BigDecimal.ZERO;
            }
        }
        refundRequest.setApplyRefundFee(price);//单位：分
        refundRequest.setReasonTextId(request.getReasonTextId());
        refundRequest.setLeaveMessage(request.getLeaveMessage());
        refundRequest.setReasonTips(request.getReasonTips());
        //凭证图片
        if(CollectionUtils.isNotEmpty(request.getImages())){
            refundRequest.setImages(request.getImages());
        }
        //设定商品收货状态
        refundRequest.setGoodsStatus(chgGoodsStatus(trade, returnOrder.getReturnType()));
        channelRefundProvider.applyRefund(refundRequest);
        returnOrder.setThirdReasonId(request.getReasonTextId());
        returnOrder.setThirdReasonTips(request.getReasonTips());
        returnOrderRepository.save(returnOrder);
    }

    public void cancel(String rid) {
        ReturnOrder returnOrder = returnOrderRepository.findById(rid).orElse(null);
        // 只有当前退单已经向linkedmall平台申请了退单，该笔退单才会走linkedmall平台取消流程
        if (Objects.nonNull(returnOrder)) {
            ThirdPlatformType thirdPlatformType = returnOrder.getThirdPlatformType();
            if (Objects.nonNull(thirdPlatformType)
                    && Objects.nonNull(returnOrder.getThirdReasonId())) {
                String buyerId = returnOrder.getBuyer().getId();
                try {
                    returnOrder.getReturnItems().forEach(item -> {
                        ChannelRefundQueryStatusRequest queryRequest =
                                ChannelRefundQueryStatusRequest.builder()
                                        .thirdPlatformType(thirdPlatformType)
                                        .bizUid(buyerId)
                                        .subChannelOrderId(item.getThirdPlatformSubOrderId()).build();
                        ChannelRefundQueryStatusResponse lmRefundDetail =
                                channelRefundProvider.queryRefundStatus(queryRequest).getContext();
                        if (Objects.nonNull(lmRefundDetail) && Objects.nonNull(lmRefundDetail.getDisputeId())) {
                            ChannelRefundCancelRequest cancelRequest =
                                    ChannelRefundCancelRequest.builder()
                                            .thirdPlatformType(thirdPlatformType)
                                            .bizUid(buyerId)
                                            .subChannelOrderId(item.getThirdPlatformSubOrderId())
                                            .disputeId(lmRefundDetail.getDisputeId()).build();
                            channelRefundProvider.cancelRefund(cancelRequest);
                        }
                    });
                } catch (Exception e) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "退单取消失败，请重试");
                }
            }
        }
    }

    /**
     * 根据第三方子订单号自动申请退款
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoApplyBySubOrder(ThirdPlatformReturnOrderAutoApplySubRequest request) {
        String subTradeId = request.getThirdPlatformSubTradeId();
        log.info("===========第三方子订单id：{}，自动局部退款开始========", subTradeId);
        ThirdPlatformTrade thirdPlatformTrade = this.thirdPlatformTradeRepository.findFirstBySuborderList_SuborderId(subTradeId);
        if(Objects.isNull(thirdPlatformTrade)){
            log.warn("第三方子订单id:{}，查询为空", request.getThirdPlatformSubTradeId());
            return;
        }
        if (PayState.NOT_PAID.equals(thirdPlatformTrade.getTradeState().getPayState())) {
            log.warn("===========第三方子订单id：{}，状态未支付========", subTradeId);
            return;
        }

        List<ReturnOrder> orders = returnOrderRepository.findByPtid(thirdPlatformTrade.getParentId());
        if(CollectionUtils.isNotEmpty(orders) && orders.stream().anyMatch(o -> !ReturnFlowState.VOID.equals(o.getReturnFlowState()))) {
            log.warn("===========第三方子订单id：{}，存在售后流程========", subTradeId);
            return;
        }

        List<Trade> trade = orderCommonService.findTradesByBusinessId(thirdPlatformTrade.getTradeId());
        if (CollectionUtils.isEmpty(trade)) {
            log.warn("===========第三方子订单id：{}，商家订单不存在========", subTradeId);
            return;
        }
        Operator operator =
                Operator.builder().adminId("1").name("system").account("system").ip("127.0.0.1").platform(Platform
                        .PLATFORM).build();

        thirdPlatformTrade.setSupplier(trade.get(0).getSupplier());
        thirdPlatformTrade.setPayInfo(trade.get(0).getPayInfo());

        //1、生成退单数据
        ReturnOrder returnOrder = generateReturnBySub(thirdPlatformTrade, subTradeId, operator, request.getDescription());

        List<ReturnOrder> returnOrders = new ArrayList<>();
        returnOrderService.splitReturnTrade(returnOrder, trade.get(0), returnOrders);
        log.info("===========生成退单数据,详细信息：{}========", returnOrders);
        //未发货，直接退款
        List<ReturnOrder> returnOrderList = this.addReturnOrderBySub(trade, returnOrders);
        log.info("===========第三方子订单id：{}，自动退款结束========", subTradeId);

    }

    /**
     * 根据第三方子订单号新增退款单
     * @param tradeList 订单列表
     * @param returnOrders 退单信息
     * @return 退单保存后信息
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ReturnOrder> addReturnOrderBySub(List<Trade> tradeList, List<ReturnOrder> returnOrders){
        List<ReturnOrder> returnOrderList = returnOrderRepository.saveAll(returnOrders);

        //1、根据退单数据，获取出会员详情基础信息集合
        List<CustomerDetailBaseVO> customerDetailBaseVOList = listCustomerDetailBaseByCustomerIds(returnOrderList);
        log.info("===========会员详情基础信息集合,详细信息：{}========", customerDetailBaseVOList);

        //2、生成退款单数据集合
        List<RefundOrder> refundOrderList =
                getRefundOrderByReturnOrderListAndCustomerDetailBaseVOList(returnOrderList, customerDetailBaseVOList);

        refundOrderList = refundOrderService.batchAdd(refundOrderList);
        log.info("===========生成退款单数据集合,详细信息：{}========", refundOrderList);
        return returnOrderList;
    }

    /**
     * 根据子订单号生成退单实例
     * @param trade 订单
     * @param subOrderId 子订单号
     * @param operator 操作人
     * @param description 描述
     * @return
     */
    private ReturnOrder generateReturnBySub(ThirdPlatformTrade trade, String subOrderId, Operator operator, String description){
        List<TradeItem> tradeItems = trade.getTradeItems();
        List<TradeItem> tradeGifts = trade.getGifts();

        ReturnOrder returnOrder = new ReturnOrder();
        String rid = generatorService.generate("R");
        //退单号
        returnOrder.setId(rid);
        returnOrder.setPtid(trade.getParentId());
        //订单编号
        returnOrder.setTid(trade.getTradeId());
        //购买人信息
        returnOrder.setBuyer(trade.getBuyer());
        //商家信息
        returnOrder.setCompany(Company.builder().companyInfoId(trade.getSupplier().getSupplierId())
                .companyCode(trade.getSupplier().getSupplierCode()).supplierName(trade.getSupplier().getSupplierName())
                .storeId(trade.getSupplier().getStoreId()).storeName(trade.getSupplier().getStoreName()).storeType(trade.getSupplier().getStoreType())
                .companyType(trade.getSupplier().getIsSelf() ? BoolFlag.NO : BoolFlag.YES)
                .build());
        //描述信息
        returnOrder.setDescription(description);
        //退货商品
        returnOrder.setReturnItems(returnOrderService.chgTradeTime(tradeItems));
        //退货赠品
        if(CollectionUtils.isNotEmpty(tradeGifts)) {
            returnOrder.setReturnGifts(returnOrderService.chgTradeTime(tradeGifts));
        }
        if(CollectionUtils.isNotEmpty(trade.getPreferential())) {
            returnOrder.setReturnPreferential(returnOrderService.chgTradeTime(trade.getPreferential()));
        }
        TradePrice tradePrice = trade.getTradePrice();
        ReturnPrice returnPrice = new ReturnPrice();
        returnPrice.setApplyPrice(tradePrice.getTotalPrice());
        returnPrice.setProviderTotalPrice(returnOrder.getReturnItems().stream().map(ReturnItem::getProviderPrice).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        //退积分信息
        ReturnPoints points = new ReturnPoints();
        points.setApplyPoints(Objects.isNull(tradePrice.getPoints()) ? NumberUtils.LONG_ZERO : tradePrice.getPoints());

        //存在子订单号
        if(StringUtils.isNotBlank(subOrderId) && CollectionUtils.isNotEmpty(trade.getSuborderList())) {
            List<String> returnSkuIds = trade.getSuborderList().stream()
                    .filter(i -> subOrderId.equals(i.getSuborderId()))
                    .flatMap(i -> i.getItemList().stream())
                    .map(ThirdPlatformSuborderItem::getSkuId).collect(Collectors.toList());
            returnOrder.setReturnItems(returnOrder.getReturnItems().stream()
                    .filter(i -> returnSkuIds.contains(i.getThirdPlatformSkuId())).collect(Collectors.toList()));
            returnPrice.setProviderTotalPrice(returnOrder.getReturnItems().stream().map(ReturnItem::getProviderPrice).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
            returnPrice.setApplyPrice(returnOrder.getReturnItems().stream().map(ReturnItem::getSplitPrice).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));

            if (CollectionUtils.isNotEmpty(returnOrder.getReturnGifts())) {
                returnOrder.setReturnGifts(returnOrder.getReturnGifts().stream()
                        .filter(i -> returnSkuIds.contains(i.getThirdPlatformSkuId())).collect(Collectors.toList()));
            }
            //累积积分
            points.setApplyPoints(returnOrder.getReturnItems().stream().map(ReturnItem::getSplitPoint).filter(Objects::nonNull).reduce(Long::sum).orElse(0L));
        }

        //申请金额可用
        returnPrice.setApplyStatus(Boolean.TRUE);
        //退货总金额
        returnOrder.setReturnPrice(returnPrice);
        //退货积分
        returnOrder.setReturnPoints(points);
        //收货人信息
        returnOrder.setConsignee(trade.getConsignee());
        //退货单状态
        returnOrder.setReturnFlowState(ReturnFlowState.AUDIT);
        //记录日志
        returnOrder.appendReturnEventLog(
                new ReturnEventLog(operator, "系统自动申请退单", "系统自动申请退单", "系统自动申请退单", LocalDateTime.now())
        );
        // 支付方式
        returnOrder.setPayType(PayType.valueOf(trade.getPayInfo().getPayTypeName()));
        //退单类型
        returnOrder.setReturnType(ReturnType.REFUND);
        //退单来源
        returnOrder.setPlatform(Platform.CUSTOMER);

        //创建时间
        returnOrder.setCreateTime(LocalDateTime.now());
        return returnOrder;
    }
}
