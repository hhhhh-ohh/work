package com.wanmi.sbc.order.payorder.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.offline.OfflineQueryProvider;
import com.wanmi.sbc.account.api.request.offline.OfflineAccountGetByIdRequest;
import com.wanmi.sbc.account.api.request.offline.OfflineAccountListWithoutDeleteFlagByBankNoRequest;
import com.wanmi.sbc.account.api.response.offline.OfflineAccountGetByIdResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyListRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetByIdResponse;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetCustomerIdResponse;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemListResponse;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.order.api.request.trade.TradePayRecordObsoleteRequest;
import com.wanmi.sbc.order.bean.enums.OrderDeductionType;
import com.wanmi.sbc.order.customer.service.CustomerCommonService;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import com.wanmi.sbc.order.payorder.repository.PayOrderRepository;
import com.wanmi.sbc.order.payorder.request.PayOrderGenerateRequest;
import com.wanmi.sbc.order.payorder.request.PayOrderRequest;
import com.wanmi.sbc.order.payorder.response.PayOrderPageResponse;
import com.wanmi.sbc.order.payorder.response.PayOrderResponse;
import com.wanmi.sbc.order.receivables.model.root.Receivable;
import com.wanmi.sbc.order.receivables.repository.ReceivableRepository;
import com.wanmi.sbc.order.receivables.service.ReceivableService;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.trade.model.entity.value.Buyer;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.order.util.XssUtils;

import io.seata.spring.annotation.GlobalTransactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 支付单服务
 * Created by zhangjin on 2017/4/20.
 */
@Slf4j
@Service
@Transactional(readOnly = true, timeout = 10)
public class PayOrderService {

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private ReceivableService receivableService;

    @Autowired
    private OfflineQueryProvider offlineQueryProvider;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerCommonService customerCommonService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ReceivableRepository receivableRepository;

    @Autowired
    private TradeCacheService tradeCacheService;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    /**
     * 根据订单号生成支付单
     *
     * @param payOrderGenerateRequest payOrderGenerateRequest
     * @return Optional<payorder>
     */
    @Transactional
    public Optional<PayOrder> generatePayOrderByOrderCode(PayOrderGenerateRequest payOrderGenerateRequest) {
        PayOrder payOrder = new PayOrder();
        BaseResponse<CustomerDetailGetCustomerIdResponse> response = tradeCacheService.getCustomerDetailByCustomerId(payOrderGenerateRequest.getCustomerId());
        CustomerDetailVO customerDetail = response.getContext();
        payOrder.setCustomerDetailId(customerDetail.getCustomerDetailId());
        payOrder.setOrderCode(payOrderGenerateRequest.getOrderCode());
        payOrder.setUpdateTime(LocalDateTime.now());
        payOrder.setCreateTime(payOrderGenerateRequest.getOrderTime());
        payOrder.setDelFlag(DeleteFlag.NO);
        payOrder.setCompanyInfoId(payOrderGenerateRequest.getCompanyInfoId());
        payOrder.setPayOrderNo(generatorService.generateOid());
        if (payOrderGenerateRequest.getPayOrderPrice() == null) {
            payOrder.setPayOrderStatus(PayOrderStatus.PAYED);
        } else {
            payOrder.setPayOrderStatus(PayOrderStatus.NOTPAY);
        }
        payOrder.setPayOrderPrice(payOrderGenerateRequest.getPayOrderPrice());
        payOrder.setPayOrderPoints(payOrderGenerateRequest.getPayOrderPoints());
        payOrder.setPayType(payOrderGenerateRequest.getPayType());
        payOrder.setGiftCardPrice(payOrderGenerateRequest.getGiftCardPrice());
        payOrder.setGiftCardType(payOrderGenerateRequest.getGiftCardType());

        if (OrderType.POINTS_ORDER.equals(payOrderGenerateRequest.getOrderType())) {
            payOrderRepository.saveAndFlush(payOrder);
            // 积分订单生成收款单
            Receivable receivable = new Receivable();
            receivable.setPayOrderId(payOrder.getPayOrderId());
            receivable.setReceivableNo(generatorService.generateSid());
            receivable.setPayChannel("积分支付");
            receivable.setPayChannelId((Constants.DEFAULT_RECEIVABLE_ACCOUNT));
            receivable.setCreateTime(payOrderGenerateRequest.getOrderTime());
            receivable.setDelFlag(DeleteFlag.NO);
            receivableRepository.save(receivable);
            return Optional.of(payOrder);
        }
        return Optional.of(payOrderRepository.saveAndFlush(payOrder));
    }

    /**
     * 作废支付单
     *
     * @param payOrders payOrderIds
     */
    @Transactional
    @GlobalTransactional
    public void destoryPayOrder(List<PayOrder> payOrders, Operator operator) {
        //返回修改对应订单状态
        //只有支付完的订单那才改状态
        List<String> orderIds = payOrders.stream().filter(payOrder -> PayOrderStatus.PAYED.equals(payOrder
                .getPayOrderStatus()) || PayOrderStatus.TOCONFIRM.equals(payOrder
                .getPayOrderStatus())).map(PayOrder::getOrderCode).collect(Collectors.toList());

        List<String> payIds = payOrders.stream().map(PayOrder::getPayOrderId).collect(Collectors.toList());

        receivableService.deleteReceivables(payIds);
        if (!CollectionUtils.isEmpty(payIds)) {
            payOrderRepository.updatePayOrderStatus(payIds, PayOrderStatus.NOTPAY);
        }

        orderIds.forEach(orderId -> {
            TradePayRecordObsoleteRequest tradePayRecordObsoleteRequest =
                    TradePayRecordObsoleteRequest.builder().tid(orderId).operator(operator).build();
            tradeService.payRecordObsolete(tradePayRecordObsoleteRequest.getTid(),
                    tradePayRecordObsoleteRequest.getOperator());

        });
    }

    /**
     * pay模块无法引入tradeService，此处将OrderList传到controller，判断trade是否过了账期
     *
     * @param payOrderIds
     * @return
     */
    public List<PayOrder> findPayOrderByPayOrderIds(List<String> payOrderIds) {
        if (CollectionUtils.isEmpty(payOrderIds)) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020002);
        }
        return payOrderRepository.findAllById(payOrderIds);
    }

    /**
     * 修改收款单状态
     *
     * @param payOrderId payOrderId
     * @param payOrderId payOrderStatus
     */
    @Transactional
    public void updatePayOrder(String payOrderId, PayOrderStatus payOrderStatus) {
        if (Objects.isNull(payOrderId)) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020002);
        }
        payOrderRepository.updatePayOrderStatus(Lists.newArrayList(payOrderId), payOrderStatus);
    }

    /**
     * 根据订单编号查询支付单，支付单状态..
     *
     * @param orderCode orderCode
     * @return 支付单
     */
    public Optional<PayOrder> findPayOrderByOrderCode(String orderCode) {
        return payOrderRepository.findByOrderCodeAndDelFlag(orderCode, DeleteFlag.NO);
    }

    /**
     * 根据订单编号查询支付单，支付单状态..
     *
     * @param orderCode orderCode
     * @return 支付单
     */
    public PayOrder findPayOrderByCode(String orderCode) {
        return payOrderRepository.findByCodeAndDelFlag(orderCode);
    }

    /**
     * 根据订单编号批量查询支付单，支付单状态..
     *
     * @param orderCodes
     * @return 支付单
     */
    public List<PayOrder> findPayOrderByOrderCodes(List<String> orderCodes) {
        return payOrderRepository.findByOrderCodes(orderCodes);
    }

    public PayOrderResponse findPayOrder(String orderNo) {
        PayOrder payOrder = payOrderRepository.findByOrderCodeAndDelFlag(orderNo, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(AccountErrorCodeEnum.K020032));

        payOrder.setCustomerDetail(customerCommonService.getAnyCustomerDetailById(payOrder.getCustomerDetailId()));
        payOrder.setCompanyInfo(customerCommonService.getCompanyInfoById(payOrder.getCompanyInfoId()));
        return getPayOrderResponse(payOrder);
    }

    /**
     * 根据查询条件做收款单分页查询
     *
     * @param payOrderRequest payOrderRequest
     * @return Page<payorder>
     */
    public PayOrderPageResponse findPayOrders(PayOrderRequest payOrderRequest) {
        PayOrderPageResponse payOrderPageResponse = new PayOrderPageResponse();
        payOrderPageResponse.setPayOrderResponses(Collections.emptyList());
        payOrderPageResponse.setTotal(0L);
        payOrderPageResponse.setCurrentPage(payOrderRequest.getPageNum());
        payOrderPageResponse.setPageSize(payOrderRequest.getPageSize());

        //模糊匹配会员/商户名称，不符合条件直接返回
        if (!this.likeCustomerAndSupplierNameAndBankNo(payOrderRequest)) {
            return payOrderPageResponse;
        }

        //根据支付方式 查询支付渠道信息
        if(Objects.nonNull(payOrderRequest.getPayWay())){
            PayGatewayEnum payGatewayEnum = null;
            switch (payOrderRequest.getPayWay()){
                case ALIPAY:
                    payGatewayEnum = PayGatewayEnum.ALIPAY;
                    break;
                case WECHAT:
                    payGatewayEnum = PayGatewayEnum.WECHAT;
                    break;
                case UNIONPAY:
                    payGatewayEnum = PayGatewayEnum.UNIONPAY;
                    break;
                case UNIONPAY_B2B:
                    payGatewayEnum = PayGatewayEnum.UNIONB2B;
                    break;
                case CREDIT:
                    payGatewayEnum = PayGatewayEnum.CREDIT;
                    break;
                case BALANCE:
                    payGatewayEnum = PayGatewayEnum.BALANCE;
                    break;
                default:
                    payGatewayEnum = null;
                    break;
            }
            PayChannelItemListResponse channel = new PayChannelItemListResponse();
            if(Objects.nonNull(payGatewayEnum)){
                channel = paySettingQueryProvider.listChannelItemByGatewayName(
                        ChannelItemByGatewayRequest.builder()
                                .gatewayName(payGatewayEnum)
                                .build()).getContext();
            }


            if(CollectionUtils.isNotEmpty(channel.getPayChannelItemVOList())){
                List<Long> ids = channel.getPayChannelItemVOList()
                        .stream()
                        .map(PayChannelItemVO::getId)
                        .collect(Collectors.toList());

                if(CollectionUtils.isNotEmpty(ids)){
                    payOrderRequest.setPayChannelIds(ids);
                }
            }
        }

        Page<PayOrder> payOrders = payOrderRepository.findAll(findByRequest(payOrderRequest),
                payOrderRequest.getPageable());
        payOrderPageResponse.setPayOrderResponses(this.getPayOrderResponses(payOrders.getContent()));
        payOrderPageResponse.setTotal(payOrders.getTotalElements());
        return payOrderPageResponse;
    }

    /**
     * 根据查询条件做收款单查询
     *
     * @param payOrderRequest payOrderRequest
     * @return PayOrderPageResponse
     */
    public PayOrderPageResponse findPayOrdersWithNoPage(PayOrderRequest payOrderRequest) {
        //模糊匹配会员/商户名称，不符合条件直接返回
        if (!this.likeCustomerAndSupplierNameAndBankNo(payOrderRequest)) {
            return PayOrderPageResponse.builder().payOrderResponses(Collections.EMPTY_LIST).build();
        }

        List<PayOrder> payOrders = payOrderRepository.findAll(findByRequest(payOrderRequest));
        if (Objects.isNull(payOrders) || CollectionUtils.isEmpty(payOrders)) {
            return PayOrderPageResponse.builder().payOrderResponses(Collections.EMPTY_LIST).build();
        }
        return PayOrderPageResponse.builder().payOrderResponses(this.getPayOrderResponses(payOrders)).build();
    }

    /**
     * 获取多个支付单返回数据
     *
     * @param payOrders payOrder
     * @return PayOrderResponse
     */
    private List<PayOrderResponse> getPayOrderResponses(List<PayOrder> payOrders) {
        List<Long> companyIds = payOrders.stream()
                .map(PayOrder::getCompanyInfoId).collect(Collectors.toList());

        Map<Long, CompanyInfoVO> companyInfoVOMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(companyIds)) {
            companyInfoVOMap.putAll(customerCommonService.listCompanyInfoByCondition(
                    CompanyListRequest.builder().companyInfoIds(companyIds).build()
            ).stream().collect(Collectors.toMap(CompanyInfoVO::getCompanyInfoId, Function.identity())));
        }

        Map<String, Trade> tradeMap = new HashMap<>();
        List<String> orderCodes = payOrders.stream().map(PayOrder::getOrderCode).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(orderCodes)) {
            List<Trade> trades = tradeService
                    .queryAll(TradeQueryRequest.builder().ids(orderCodes.toArray(new String[orderCodes.size()])).orderType(OrderType.ALL_ORDER).build())
                    .stream()
                    .collect(Collectors.toList());

            //尾款订单号
            List<String> tailOrderNos = orderCodes.stream().filter(orderCode -> orderCode.startsWith("OT")).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(tailOrderNos)) {
                tailOrderNos.forEach(tailOrderNo -> {
                    Optional<Trade> optional = trades.stream().filter(trade -> tailOrderNo.equals(trade.getTailOrderNo())).findFirst();
                    //尾款订单号所代表的订单对象在trades找不到再去查库
                    if (!optional.isPresent()) {
                        List<Trade> tradeList = tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(tailOrderNo).build());
                        if (CollectionUtils.isNotEmpty(tradeList)) {
                            trades.addAll(tradeList);
                        }
                    }
                });
            }
            orderCodes.forEach(orderCode -> {
                List<Trade> list = trades.stream().filter(trade -> orderCode.equals(trade.getId()) || orderCode.equals(trade.getTailOrderNo())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(list)) {
                    tradeMap.put(orderCode, list.get(0));
                }
            });
            tradeMap.putAll(trades.stream().collect(Collectors.toMap(Trade::getId, Function.identity(), (k1, k2) -> k1)));
            if (CollectionUtils.isNotEmpty(trades)) {
                Trade trade = trades.get(0);
                if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && trade.getBookingType() == BookingType.EARNEST_MONEY
                        && StringUtils.isNotEmpty(trade.getTailOrderNo())) {
                    tradeMap.put(trade.getTailOrderNo(), trade);
                }
            }
        }

        return payOrders.stream().map(payOrder -> {
            if(!companyInfoVOMap.isEmpty()){
                payOrder.setCompanyInfo(companyInfoVOMap.get(payOrder.getCompanyInfoId()));
            }

            if(!tradeMap.isEmpty()){
                Trade trade = tradeMap.get(payOrder.getOrderCode());
                if (Objects.nonNull(trade)) {
                    Buyer buyer = trade.getBuyer();
                    CustomerDetailVO customerDetailVO = new CustomerDetailVO();
                    customerDetailVO.setCustomerId(buyer.getId());
                    customerDetailVO.setCustomerName(buyer.getName());
                    payOrder.setCustomerDetail(customerDetailVO);
                }
            }

            return this.getPayOrderResponse(payOrder);
        }).collect(Collectors.toList());
    }

    /**
     * 获取支付单返回数据
     *
     * @param payOrder payOrder
     * @return PayOrderResponse
     */
    private PayOrderResponse getPayOrderResponse(PayOrder payOrder) {
        PayOrderResponse payOrderResponse = new PayOrderResponse();
        BeanUtils.copyProperties(payOrder, payOrderResponse);
        payOrderResponse.setTotalPrice(payOrder.getPayOrderPrice());
        payOrderResponse.setPayOrderPoints(payOrder.getPayOrderPoints());
        payOrderResponse.setGiftCardPrice(payOrder.getGiftCardPrice());
        payOrderResponse.setGiftCardType(payOrder.getGiftCardType());
        if (Objects.nonNull(payOrder.getCustomerDetailId())) {
            try {
                CustomerDetailGetByIdResponse response = customerDetailQueryProvider.getCustomerDetailById(CustomerDetailByIdRequest.builder()
                        .customerDetailId(payOrder.getCustomerDetailId())
                        .build()).getContext();
                payOrderResponse.setCustomerId(response.getCustomerId());
                payOrderResponse.setCustomerName(response.getCustomerName());
            } catch (Exception e) {
                log.info("dirty data：customerDetailId: {}", payOrder.getCustomerDetailId());
                payOrderResponse.setCustomerId(null);
                payOrderResponse.setCustomerName("-");
            }
        }
        if (Objects.nonNull(payOrder.getCompanyInfo())) {
            payOrderResponse.setCompanyInfoId(payOrder.getCompanyInfo().getCompanyInfoId());
            payOrderResponse.setSupplierName(payOrder.getCompanyInfo().getSupplierName());
            if (payOrder.getCompanyInfo().getStoreType() == StoreType.O2O){
                StoreVO storeVO = payOrder.getCompanyInfo().getStoreVOList().get(0);
                payOrderResponse.setStoreName(storeVO.getStoreName());
                payOrderResponse.setSupplierName(storeVO.getStoreName());
            }
        }

        if (Objects.nonNull(payOrder.getReceivable())) {
            payOrderResponse.setReceivableNo(payOrder.getReceivable().getReceivableNo());
            payOrderResponse.setComment(payOrder.getReceivable().getComment());
            //收款时间
            payOrderResponse.setReceiveTime(payOrder.getReceivable().getCreateTime());
            payOrderResponse.setReceivableAccount(parseAccount(payOrder, false));
            payOrderResponse.setNormalReceivableAccount(parseAccount(payOrder, true));
            //支付渠道
            payOrderResponse.setPayChannel(payOrder.getReceivable().getPayChannel());
            payOrderResponse.setPayChannelId(payOrder.getReceivable().getPayChannelId());
            payOrderResponse.setEncloses(payOrder.getReceivable().getEncloses());
            //online todo
        }
        Trade trade = tradeService.detail(payOrder.getOrderCode());
        if (Objects.isNull(trade)) {
            // 尾款订单
            List<Trade> tradeList = tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(payOrder.getOrderCode()).build());
            trade = CollectionUtils.isNotEmpty(tradeList) ? tradeList.get(0) : new Trade();
        }

        payOrderResponse.setTradeState(trade.getTradeState());
        payOrderResponse.setTid(trade.getId());

        StringBuilder sb = new StringBuilder();

        //纯积分需要显示 不需要显示线上还是线下
        //线下、积分+线下需要显示
        //积分+线上 需要判断是否付款 未付款的不显示
        PayOrderStatus payState = payOrderResponse.getPayOrderStatus();
        Long points = payOrderResponse.getPayOrderPoints();
        PayType payType = payOrderResponse.getPayType();
        Boolean isZero = Objects.nonNull(payOrderResponse.getTotalPrice())
                && payOrderResponse.getTotalPrice().compareTo(BigDecimal.ZERO) < 1;
        Long payChannelId = payOrderResponse.getPayChannelId();

        //查询支付方式
        PayChannelItemResponse channelItemResponse = null;

        if(Objects.nonNull(payChannelId)){
            channelItemResponse = paySettingQueryProvider.getChannelItemById(
                    ChannelItemByIdRequest.builder()
                            .channelItemId(payChannelId)
                            .build()).getContext();
        }


//        if( null != points && points > 0){
//            sb.append("积分");
//            if(Objects.nonNull(payOrderResponse.getTotalPrice())
//                    && (!isZero || PayType.OFFLINE.equals(payType))){
//                sb.append("+");
//            }
//        }

//        if(PayType.OFFLINE.equals(payType)){
//            //线下支付
//            sb.append(PayType.OFFLINE.getDesc());
//        }else
        if(PayType.ONLINE.equals(payType)){
            //线上支付
            //如果实际付款金额为零，则不展示付款方式
            if(Objects.nonNull(channelItemResponse)
                    && StringUtils.isNotBlank(channelItemResponse.getChannel())
                    && !isZero){
                String channel = channelItemResponse.getChannel().toUpperCase(Locale.ROOT);
                sb.append(PayWay.valueOf(channel).getDesc());
            }
        }

        if(payState.equals(PayOrderStatus.NOTPAY) && !isZero && !PayType.OFFLINE.equals(payType)){
            sb = new StringBuilder();
            sb.append("-");
        }

        payOrderResponse.setPayChannelValue(sb.toString());

        return payOrderResponse;
    }

    /**
     * @param payOrder
     * @param normal   是否需要正常直接展示
     * @return
     */
    private String parseAccount(PayOrder payOrder, boolean normal) {
        StringBuilder accountName = new StringBuilder();
        if (PayType.OFFLINE.equals(payOrder.getPayType()) && Objects.nonNull(payOrder.getReceivable()
                .getOfflineAccountId())) {
            OfflineAccountGetByIdResponse response;
            try {
                response = offlineQueryProvider.getById(new OfflineAccountGetByIdRequest
                        (payOrder
                                .getReceivable()
                                .getOfflineAccountId())).getContext();
            } catch (Exception e) {
                log.error("parseAccount Exception", e);
                return "-";
            }
            if (response.getAccountId() != null) {
                //正常展示
                if (normal) {
                    accountName.append(response.getBankName()).append(response.getBankNo());
                } else {
                    accountName.append(response.getBankName()).append(' ')
                            .append(ReturnOrderService.getDexAccount(response.getBankNo()));
                }
            }
        }
        return accountName.toString();
    }

    /**
     * 通过订单编号列表查询支付单
     *
     * @param orderNos
     * @return
     */
    public List<PayOrder> findByOrderNos(List<String> orderNos, PayOrderStatus payOrderStatus) {
        return payOrderRepository.findByOrderNos(orderNos, payOrderStatus);
    }

    /**
     * 删除收款单
     *
     * @param payOrderId
     * @return rows
     */
    @Transactional
    public int deleteByPayOrderId(String payOrderId) {
        return payOrderRepository.deletePayOrderById(payOrderId);
    }

    /**
     * 合计收款金额
     *
     * @param payOrderRequest request
     * @return sum
     */
    public BigDecimal sumPayOrderPrice(PayOrderRequest payOrderRequest) {
        //模糊匹配会员/商户名称，不符合条件直接返回0
        if (!likeCustomerAndSupplierNameAndBankNo(payOrderRequest)) {
            return BigDecimal.ZERO;
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);

        Root<PayOrder> root = query.from(PayOrder.class);
        query.select(builder.sum(root.get("payOrderPrice")));
        query.where(buildWhere(payOrderRequest, root, query, builder));

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * 替代关联查询-模糊商家名称、模糊会员名称、银行账号，以并且关系的判断
     *
     * @param payOrderRequest
     * @return true:有符合条件的数据,false:没有符合条件的数据
     */
    private boolean likeCustomerAndSupplierNameAndBankNo(final PayOrderRequest payOrderRequest) {
        boolean supplierLike = true;
        //商家名称
        if (StringUtils.isNotBlank(payOrderRequest.getSupplierName()) && StringUtils.isNotBlank(payOrderRequest
                .getSupplierName().trim())) {
            CompanyListRequest request = CompanyListRequest.builder()
                    .supplierName(payOrderRequest.getSupplierName())
                    .storeType(payOrderRequest.getStoreType())
                    .build();
            payOrderRequest.setCompanyInfoIds(customerCommonService.listCompanyInfoIdsByCondition(request));

            if (CollectionUtils.isEmpty(payOrderRequest.getCompanyInfoIds())) {
                supplierLike = false;
            }
        }

        boolean shopLike = true;
        //门店名称
        if (StringUtils.isNotBlank(payOrderRequest.getStoreName()) && StringUtils.isNotBlank(payOrderRequest
                .getStoreName().trim())) {
            CompanyListRequest request = CompanyListRequest.builder()
                    .storeName(payOrderRequest.getStoreName())
                    .storeType(payOrderRequest.getStoreType())
                    .build();
            payOrderRequest.setCompanyInfoIds(customerCommonService.listCompanyInfoIdsByCondition(request));

            if (CollectionUtils.isEmpty(payOrderRequest.getCompanyInfoIds())) {
                shopLike = false;
            }
        }
        //模糊会员名称
        boolean customerLike = true;
        if (StringUtils.isNotBlank(payOrderRequest.getCustomerName()) && StringUtils.isNotBlank(payOrderRequest
                .getCustomerName().trim())) {
            List<CustomerDetailVO> customerDetailVOList = customerDetailQueryProvider.listCustomerDetailByCondition(
                    CustomerDetailListByConditionRequest.builder().customerName(payOrderRequest.getCustomerName()).build())
                    .getContext().getCustomerDetailVOList();
            if (CollectionUtils.isEmpty(customerDetailVOList)) {
                customerLike = false;
            } else {
                List<String> customerIds = customerDetailVOList.stream().map(CustomerDetailVO::getCustomerDetailId).collect(Collectors.toList());
                payOrderRequest.setCustomerDetailIds(customerIds);
                if (CollectionUtils.isEmpty(payOrderRequest.getCustomerDetailIds())) {
                    customerLike = false;
                }
            }
        }
        // 模糊银行账号
        boolean OfflineAccountLike = true;

        if (StringUtils.isNotBlank(payOrderRequest.getAccount())) {
            List<Long> offlineAccountIds = customerCommonService.listOfflineAccountIdsByBankNo(new
                    OfflineAccountListWithoutDeleteFlagByBankNoRequest(payOrderRequest.getAccount
                    ()));

            payOrderRequest.setAccountIds(offlineAccountIds);

            if (CollectionUtils.isEmpty(offlineAccountIds)) {
                OfflineAccountLike = false;
            }
        }

        return supplierLike && customerLike && OfflineAccountLike && shopLike;
    }

    /**
     * 构建动态查
     *
     * @param payOrderRequest
     * @return
     */
    private Specification<PayOrder> findByRequest(final PayOrderRequest payOrderRequest) {
        return (Root<PayOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> buildWhere(payOrderRequest, root,
                query, cb);
    }

    /**
     * 构建列表查询的where条件
     *
     * @param payOrderRequest request
     * @param root            root
     * @param query           query
     * @param cb              bc
     * @return predicates
     */
    private Predicate buildWhere(PayOrderRequest payOrderRequest, Root<PayOrder> root, CriteriaQuery<?> query,
            CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<PayOrder, Receivable> payOrderReceivableJoin = root.join("receivable", JoinType.LEFT);
        payOrderReceivableJoin.on(cb.equal(payOrderReceivableJoin.get("delFlag"), DeleteFlag.NO));

        if (!StringUtils.isEmpty(payOrderRequest.getOrderNo()) && !StringUtils.isEmpty(payOrderRequest.getOrderNo()
                .trim())) {
            predicates.add(cb.equal(root.get("orderCode"), payOrderRequest.getOrderNo()));
        }

        if (!StringUtils.isEmpty(payOrderRequest.getOrderCode()) && !StringUtils.isEmpty(payOrderRequest.getOrderCode
                ().trim())) {
            predicates.add(cb.like(root.get("orderCode"), buildLike(payOrderRequest.getOrderCode())));
        }

        if (CollectionUtils.isNotEmpty(payOrderRequest.getOrderNoList())) {
            predicates.add(root.get("orderCode").in(payOrderRequest.getOrderNoList()));
        }

        //收款单编号
        if (!StringUtils.isEmpty(payOrderRequest.getPayBillNo()) && !StringUtils.isEmpty(payOrderRequest.getPayBillNo
                ().trim())) {
            predicates.add(cb.like(payOrderReceivableJoin.get("receivableNo"), buildLike(payOrderRequest.getPayBillNo
                    ())));
        }
        //支付单状态
        if (Objects.nonNull(payOrderRequest.getPayOrderStatus())) {
            predicates.add(cb.equal(root.get("payOrderStatus"), payOrderRequest.getPayOrderStatus()));
        }

        if (Objects.nonNull(payOrderRequest.getCompanyInfoId())) {
            predicates.add(cb.equal(root.get("companyInfoId"), payOrderRequest.getCompanyInfoId()));
        }

        if (CollectionUtils.isNotEmpty(payOrderRequest.getCompanyInfoIds())) {
            predicates.add(root.get("companyInfoId").in(payOrderRequest.getCompanyInfoIds()));
        }

        if (CollectionUtils.isNotEmpty(payOrderRequest.getCustomerDetailIds())) {
            predicates.add(root.get("customerDetailId").in(payOrderRequest.getCustomerDetailIds()));
        }

        if (CollectionUtils.isNotEmpty(payOrderRequest.getEmployeeCustomerDetailIds())) {
            predicates.add(root.get("customerDetailId").in(payOrderRequest.getEmployeeCustomerDetailIds()));
        }

        if (Objects.nonNull(payOrderRequest.getPayChannelId())) {
            predicates.add(cb.equal(payOrderReceivableJoin.get("payChannelId"), payOrderRequest.getPayChannelId()));
        }

        //根据支付方式查询支付单
        if(Objects.nonNull(payOrderRequest.getQueryPayType())){

            //积分支付 或者 积分+xx支付
            if(payOrderRequest.getQueryPayType().name().startsWith(QueryPayType.POINT.name())){
                predicates.add(cb.greaterThan(root.get("payOrderPoints"), NumberUtils.INTEGER_ZERO));

                //线下支付无需判断是否付款
                if(!(payOrderRequest.getQueryPayType().equals(QueryPayType.OFFLINE) ||
                        payOrderRequest.getQueryPayType().equals(QueryPayType.POINT_OFFLINE))){
                    predicates.add(cb.equal(root.get("payOrderStatus"), PayOrderStatus.PAYED));
                }
            }

            if (CollectionUtils.isNotEmpty(payOrderRequest.getPayChannelIds())) {
                //线上支付
                predicates.add(payOrderReceivableJoin.get("payChannelId").in(payOrderRequest.getPayChannelIds()));
            }else{
                //线下支付
                if(payOrderRequest.getQueryPayType().equals(QueryPayType.OFFLINE) ||
                        payOrderRequest.getQueryPayType().equals(QueryPayType.POINT_OFFLINE)){
                    predicates.add(cb.equal(root.get("payType"), PayType.OFFLINE));
                }
            }
        }
        //支付方式
        if (Objects.nonNull(payOrderRequest.getPayType())) {
            predicates.add(cb.equal(root.get("payType"), PayType.fromValue(payOrderRequest.getPayType())));
        }
        //支付渠道
        if (CollectionUtils.isNotEmpty(payOrderRequest.getPayChannelIds())) {
            predicates.add(payOrderReceivableJoin.get("payChannelId").in(payOrderRequest.getPayChannelIds()));
        }
        //抵扣方式
        if(payOrderRequest.getOrderDeductionType() == OrderDeductionType.POINT){
            predicates.add(cb.greaterThan(root.get("payOrderPoints"), NumberUtils.INTEGER_ZERO));
        } else if (payOrderRequest.getOrderDeductionType() == OrderDeductionType.CASH_GIFT_CARD){
            predicates.add(cb.equal(root.get("giftCardType"), GiftCardType.CASH_CARD));
            predicates.add(cb.greaterThan(root.get("giftCardPrice"), NumberUtils.INTEGER_ZERO));
        } else if (payOrderRequest.getOrderDeductionType() == OrderDeductionType.PICKUP_GIFT_CARD){
            predicates.add(cb.greaterThan(root.get("giftCardPrice"), NumberUtils.INTEGER_ZERO));
            predicates.add(cb.equal(root.get("giftCardType"), GiftCardType.PICKUP_CARD));
        }

        if (!StringUtils.isEmpty(payOrderRequest.getAccount())) {
            predicates.add(payOrderReceivableJoin.get("offlineAccountId").in(payOrderRequest.getAccountIds()));
        }

        //收款开始时间
        if (!StringUtils.isEmpty(payOrderRequest.getStartTime())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(cb.greaterThanOrEqualTo(payOrderReceivableJoin.get("createTime"), LocalDateTime.of(LocalDate
                    .parse(payOrderRequest.getStartTime(), formatter), LocalTime.MIN)));
        }

        //收款
        if (!StringUtils.isEmpty(payOrderRequest.getEndTime())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(cb.lessThanOrEqualTo(payOrderReceivableJoin.get("createTime"),
                    LocalDateTime.of(LocalDate
                            .parse(payOrderRequest.getEndTime(), formatter), LocalTime.MIN)));
        }

        //线下账户查询
        if (!StringUtils.isEmpty(payOrderRequest.getAccountId())) {
            predicates.add(cb.equal(payOrderReceivableJoin.get("offlineAccountId"), payOrderRequest.getAccountId()));
        }

        if (!CollectionUtils.isEmpty(payOrderRequest.getPayOrderIds())) {
            predicates.add(root.get("payOrderId").in(payOrderRequest.getPayOrderIds()));
        }

        //删除条件
        predicates.add(cb.equal(root.get("delFlag"), DeleteFlag.NO));
        if (payOrderRequest.getSortByReceiveTime()) {
            query.orderBy(cb.desc(payOrderReceivableJoin.get("createTime")));
        } else {
            query.orderBy(cb.desc(root.get("createTime")));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

    private static String buildLike(String field) {
        return "%" + XssUtils.replaceLikeWildcard(field) + "%";
    }

    @Transactional
    public void updatePayOrderNo(String tid, String payOrderNo) {
        payOrderRepository.updatePayOrderNo(tid, payOrderNo);
    }

    /**
     * @description 根据ID查询支付单
     * @author  edz
     * @date: 2022/7/12 14:22
     * @param payOrderId
     * @return com.wanmi.sbc.order.payorder.model.root.PayOrder
     */
    public PayOrder findPayOrderByOrderId(String payOrderId){
         Optional<PayOrder> payOrderOptional = payOrderRepository.findById(payOrderId);
         if (payOrderOptional.isPresent()){
             return payOrderOptional.get();
         } else {
             log.error("查询财务支付单为空，支付单ID：{}", payOrderId);
             throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
         }
    }
}
