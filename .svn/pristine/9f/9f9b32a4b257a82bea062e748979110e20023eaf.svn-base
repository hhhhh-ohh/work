package com.wanmi.sbc.order.refund.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsProvider;
import com.wanmi.sbc.account.api.provider.offline.OfflineQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAmountRestoreRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsAddAmountRequest;
import com.wanmi.sbc.account.api.request.offline.OfflineAccountGetByIdRequest;
import com.wanmi.sbc.account.api.response.offline.OfflineAccountGetByIdResponse;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.QueryPayType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.request.company.CompanyListRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.RefundRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemListResponse;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.request.refund.RefundOrderRefundRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderPageResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderVoFromEsResponse;
import com.wanmi.sbc.order.bean.enums.CreditPayState;
import com.wanmi.sbc.order.bean.enums.OrderDeductionType;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.RefundChannel;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;
import com.wanmi.sbc.order.bean.vo.RefundOrderResponse;
import com.wanmi.sbc.order.bean.vo.RefundOrderToEsVO;
import com.wanmi.sbc.order.customer.service.CustomerCommonService;
import com.wanmi.sbc.order.mqconsumer.OrderMqConsumerService;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.refund.model.root.RefundBill;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.repository.RefundBillRepository;
import com.wanmi.sbc.order.refund.repository.RefundOrderRepository;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPrice;
import com.wanmi.sbc.order.returnorder.repository.ReturnOrderRepository;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.swdh5.mq.OrderPushH5ProducerService;
import com.wanmi.sbc.order.swdh5.service.SmallService;
import com.wanmi.sbc.order.trade.model.entity.CreditPayInfo;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.util.XssUtils;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_LIST;

/**
 * 退款单服务
 * Created by zhangjin on 2017/4/21.
 */
@Slf4j
@Service
public class RefundOrderService {

    @Autowired
    private CustomerCommonService customerCommonService;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    @Autowired
    private RefundBillService refundBillService;

    @Autowired
    private OfflineQueryProvider offlineQueryProvider;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

    @Resource
    private CreditAccountProvider accountProvider;

    @Autowired
    private CustomerFundsProvider customerFundsProvider;

    @Autowired
    private PayProvider payProvider;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private RefundBillRepository refundBillRepository;

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    @Autowired
    private OrderMqConsumerService orderMqConsumerService;

    @Autowired
    private SmallService smallService;

    @Autowired
    private OrderPushH5ProducerService orderPushH5ProducerService;
    @Transactional
    public List<RefundOrder> batchAdd(List<RefundOrder> refundOrderList) {
        return refundOrderRepository.saveAll(refundOrderList);
    }

    /**
     * 根据退单生成退款单 //todo 操作人
     *
     * @param returnOrderCode returnOrderCode
     * @param customerId      customerId
     * @param price           price 应退金额
     * @param price           payType 应退金额
     * @return 退款单
     */
    public Optional<RefundOrder> generateRefundOrderByReturnOrderCode(String returnOrderCode, String customerId,
                                                                      BigDecimal price, PayType payType, Long returnPoints, boolean isEarnest) {
        ReturnOrder returnOrder = returnOrderRepository.findById(returnOrderCode).orElse(null);
        if (Objects.isNull(returnOrder)) {
            log.error("退单编号：{},查询不到退单信息", returnOrderCode);
            return Optional.empty();
        }

        Optional<RefundOrder> rdo = refundOrderRepository.findAllByReturnOrderCodeAndDelFlag(returnOrderCode,DeleteFlag.NO);

        if(Objects.isNull(returnPoints)){
            returnPoints = Nutils.nonNullActionRt(returnOrder, ro->ro.getReturnPoints().getApplyPoints(), null);
        }
        RefundOrder refundOrder = new RefundOrder();
        CustomerDetailVO customerDetail = customerCommonService.getCustomerDetailByCustomerId(customerId);
        if(rdo.isPresent()){
            refundOrder.setRefundId(rdo.get().getRefundId());
        }
        refundOrder.setReturnOrderCode(returnOrderCode);
        refundOrder.setCustomerDetailId(customerDetail.getCustomerDetailId());
        refundOrder.setCreateTime(LocalDateTime.now());
        refundOrder.setRefundCode(generatorService.generateRid());
        refundOrder.setRefundStatus(RefundStatus.TODO);
        refundOrder.setReturnPrice(price);
        refundOrder.setReturnPoints(returnPoints);
        refundOrder.setDelFlag(DeleteFlag.NO);
        refundOrder.setPayType(payType);
        refundOrder.setSupplierId(returnOrder.getCompany().getCompanyInfoId());
        if (isEarnest) {
            refundOrder.setGiftCardPrice(BigDecimal.ZERO);
        } else {
            refundOrder.setGiftCardPrice(returnOrder.getReturnPrice().getGiftCardPrice());
        }
        Optional<RefundOrder> optionalRefundOrder;
        try {
            optionalRefundOrder = Optional.of(refundOrderRepository.saveAndFlush(refundOrder));
        } catch (Exception e) {
            log.error("refundOrderRepository saveAndFlush error = {}",e.getMessage());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050012);
        }

        return optionalRefundOrder;
    }

    /***
     * 根据退单生成尾款退款单
     * @param returnOrder   退货单对象
     * @param customerId    customerId
     * @param price         应退金额
     * @param payType       在线/线下支付
     * @param returnPoints  应退积分
     * @return              新增退款单对象
     */
    @Transactional
    public Optional<RefundOrder> generateTailRefundOrderByReturnOrderCode(ReturnOrder returnOrder, String customerId,
                                                                          BigDecimal price, PayType payType, Long returnPoints) {
        RefundOrder refundOrder = new RefundOrder();
        CustomerDetailVO customerDetail = customerCommonService.getCustomerDetailByCustomerId(customerId);
        refundOrder.setReturnOrderCode(returnOrder.getBusinessTailId());
        refundOrder.setCustomerDetailId(customerDetail.getCustomerDetailId());
        refundOrder.setCreateTime(LocalDateTime.now());
        refundOrder.setRefundCode(generatorService.generateRid());
        refundOrder.setRefundStatus(RefundStatus.TODO);
        refundOrder.setReturnPrice(price);
        refundOrder.setReturnPoints(returnPoints);
        refundOrder.setDelFlag(DeleteFlag.NO);
        refundOrder.setPayType(payType);
        refundOrder.setSupplierId(returnOrder.getCompany().getCompanyInfoId());
        refundOrder.setGiftCardPrice(returnOrder.getReturnPrice().getGiftCardPrice());
        return Optional.of(refundOrderRepository.saveAndFlush(refundOrder));
    }

    /**
     * 查询退款单
     *
     * @param refundOrderRequest refundOrderRequest
     * @return Page<RefundOrder>
     */
    public RefundOrderPageResponse findByRefundOrderRequest(RefundOrderRequest refundOrderRequest) {
        RefundOrderPageResponse refundOrderPageResponse = new RefundOrderPageResponse();
        refundOrderPageResponse.setData(Collections.emptyList());
        refundOrderPageResponse.setTotal(0L);

        //模糊匹配会员/商户名称，不符合条件直接返回
        if (!this.likeCustomerAndSupplierName(refundOrderRequest)) {
            return refundOrderPageResponse;
        }
        List<String> codes = new ArrayList<>();
        // 业务员过滤
        if (CollectionUtils.isNotEmpty(refundOrderRequest.getEmployeeIds())) {
            List<ReturnOrder> returnOrders =
                    returnOrderService.findByCondition(KsBeanUtil.convert(refundOrderRequest,
                            ReturnQueryRequest.class));
            if (CollectionUtils.isNotEmpty(returnOrders)) {
                codes.addAll(returnOrders.stream().map(ReturnOrder::getId).collect(Collectors.toList()));
                codes.addAll(
                        returnOrders.stream()
                                .filter(
                                        returnOrder ->
                                                StringUtils.isNotBlank(returnOrder.getBusinessTailId()))
                                .map(ReturnOrder::getBusinessTailId)
                                .collect(Collectors.toList()));
            } else {
                return refundOrderPageResponse;
            }
        }

        //批量查询
        if(StringUtils.isNotBlank(refundOrderRequest.getReturnOrderCode())){
            Optional<ReturnOrder> returnOrder = returnOrderRepository.findById(refundOrderRequest.getReturnOrderCode());
            if(returnOrder.isPresent()){
                List<String> returnCodes = new ArrayList<>();
                if (CollectionUtils.isEmpty(codes) || codes.contains(returnOrder.get().getId())) {
                    returnCodes.add(returnOrder.get().getId());
                }
                if (CollectionUtils.isEmpty(codes) || codes.contains(returnOrder.get().getBusinessTailId())) {
                    returnCodes.add(returnOrder.get().getBusinessTailId());
                }
                refundOrderRequest.setReturnOrderCode(null);
                codes.clear();
                codes.addAll(returnCodes);
            }
            if(CollectionUtils.isEmpty(codes)){
                return refundOrderPageResponse;
            }
        }
        refundOrderRequest.setReturnOrderCodes(codes);
        //根据支付方式 查询支付渠道信息
        if(Objects.nonNull(refundOrderRequest.getPayWay())){

            PayGatewayEnum payGatewayEnum = null;
            switch (refundOrderRequest.getPayWay()){
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
                    refundOrderRequest.setPayChannelIds(ids);
                }
            }
        }


        Page<RefundOrder> refundOrderPage = refundOrderRepository.findAll(findByRequest(refundOrderRequest),
                PageRequest.of(refundOrderRequest.getPageNum(), refundOrderRequest.getPageSize()));

        if (CollectionUtils.isEmpty(refundOrderPage.getContent())) {
            return refundOrderPageResponse;
        }
        List<RefundOrderResponse> refundOrderResponses = generateRefundOrderResponseNew(refundOrderPage.getContent());
        // 填充流水号
        fillTradeNo2RefundOrderList(refundOrderResponses);
        refundOrderPageResponse.setTotal(refundOrderPage.getTotalElements());
        refundOrderPageResponse.setData(refundOrderResponses);
        return refundOrderPageResponse;
    }

    /**
     * 查询不带分页的退款单
     *
     * @param refundOrderRequest refundOrderRequest
     * @return RefundOrderPageResponse
     */
    public RefundOrderPageResponse findByRefundOrderRequestWithNoPage(RefundOrderRequest refundOrderRequest) {
        RefundOrderPageResponse refundOrderPageResponse = new RefundOrderPageResponse();
        refundOrderPageResponse.setData(Collections.emptyList());
        refundOrderPageResponse.setTotal(0L);

        //模糊匹配会员/商户名称，不符合条件直接返回
        if (!this.likeCustomerAndSupplierName(refundOrderRequest)) {
            return refundOrderPageResponse;
        }

        List<RefundOrder> refundOrders = refundOrderRepository.findAll(findByRequest(refundOrderRequest));
        if (CollectionUtils.isEmpty(refundOrders)) {
            return refundOrderPageResponse;
        }
        List<RefundOrderResponse> refundOrderResponses = refundOrders.stream().map(this::generateRefundOrderResponse)
                .collect(Collectors.toList());
        refundOrderPageResponse.setData(refundOrderResponses);
        return refundOrderPageResponse;
    }

    /**
     * 根据条件查询，不分页
     *
     * @param refundOrderRequest refundOrderRequest
     * @return List
     */
    public List<RefundOrder> findAll(RefundOrderRequest refundOrderRequest) {
        //模糊匹配会员/商户名称，不符合条件直接返回
        if (!this.likeCustomerAndSupplierName(refundOrderRequest)) {
            return EMPTY_LIST;
        }
        return refundOrderRepository.findAll(findByRequest(refundOrderRequest));
    }

    public Optional<RefundOrder> findById(String refundId) {
        return refundOrderRepository.findByRefundIdAndDelFlag(refundId, DeleteFlag.NO);
    }

    /**
     * 根据退单编号查询退款单
     *
     * @param returnOrderCode 退单编号
     * @return 退款单信息
     */
    public RefundOrder findRefundOrderByReturnOrderNo(String returnOrderCode) {
        Optional<RefundOrder> refundOrderOptional =
                refundOrderRepository.findAllByReturnOrderCodeAndDelFlag(returnOrderCode, DeleteFlag.NO);
        if (refundOrderOptional.isPresent()) {
            return refundOrderOptional.get();
        } else {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
    }

    /**
     * 根据退单编号查询退款单
     *
     * @param returnOrderCode 退单编号
     * @return 退款单信息
     */
    public RefundOrderResponse findRefundOrderRespByReturnOrderNo(String returnOrderCode) {
        return generateRefundOrderResponse(findRefundOrderByReturnOrderNo(returnOrderCode));
    }

    /**
     * @return java.util.List<com.wanmi.sbc.order.bean.vo.RefundOrderResponse>
     * @Author yangzhen
     * @Description //  根据 RefundOrders 生成 RefundOrderResponse 对象 优化，去除循环查询
     * @Date 13:51 2020/11/28
     * @Param [refundOrder]
     **/
    private List<RefundOrderResponse> generateRefundOrderResponseNew(List<RefundOrder> refundOrder) {
        List<RefundOrderResponse> responseList = new ArrayList<>();

        List<ReturnOrder> returnOrders = org.apache.commons.collections4.IteratorUtils.toList(
                returnOrderRepository.findAllById(refundOrder.stream().map(RefundOrder::getReturnOrderCode)
                        .collect(Collectors.toList())).iterator());

        List<ReturnOrder> tailOrders = new ArrayList<>();
        List<String> tailNos = refundOrder.stream().filter(refund ->
                refund.getReturnOrderCode().startsWith(GeneratorService._PREFIX_RETURN_TRADE_TAIL_ID))
                .map(RefundOrder::getReturnOrderCode).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(tailNos)){
            tailOrders = org.apache.commons.collections4.IteratorUtils.toList(
                    returnOrderRepository.findALlByBusinessTailIdIn(tailNos).iterator());
        }

        Map<String, ReturnOrder> returnOrderMap
                = returnOrders.stream().collect(
                Collectors.toMap(ReturnOrder::getId, Function.identity(), (k1, k2) -> k1));

        Map<String, ReturnOrder> tailOrderMap
                = tailOrders.stream().collect(
                Collectors.toMap(ReturnOrder::getBusinessTailId, Function.identity(), (k1, k2) -> k1));

        List<CompanyInfoVO> companyInfoVOS = customerCommonService.listCompanyInfoByCondition(
                CompanyListRequest.builder().companyInfoIds(refundOrder.stream().map(RefundOrder::getSupplierId)
                        .collect(Collectors.toList())).build());

        Map<Long, CompanyInfoVO> companyInfoMap
                = companyInfoVOS.stream().collect(
                Collectors.toMap(CompanyInfoVO::getCompanyInfoId, Function.identity(), (k1, k2) -> k1));

        refundOrder.forEach(v -> {
            RefundOrderResponse refundOrderResponse = new RefundOrderResponse();
            BeanUtils.copyProperties(v, refundOrderResponse);
            ReturnOrder returnOrder = returnOrderMap.get(v.getReturnOrderCode());
            if(v.getReturnOrderCode().startsWith(GeneratorService._PREFIX_RETURN_TRADE_TAIL_ID)){
                returnOrder = tailOrderMap.get(v.getReturnOrderCode());
                refundOrderResponse.setBusinessTailId(v.getReturnOrderCode());
            }

            if (Objects.nonNull(returnOrder)) {
                refundOrderResponse.setReturnOrderCode(returnOrder.getId());
                refundOrderResponse.setCustomerId(returnOrder.getBuyer().getId());
                refundOrderResponse.setCustomerName(returnOrder.getBuyer().getName());

                if(Objects.nonNull(returnOrder.getCreditPayInfo())){
                    //授信支付对象
                    CreditPayInfoVO creditPayInfoVO = new CreditPayInfoVO();
                    BeanUtils.copyProperties(returnOrder.getCreditPayInfo(), creditPayInfoVO);
                    refundOrderResponse.setCreditPayInfo(creditPayInfoVO);
                }

                refundOrderResponse.setPayWay(returnOrder.getPayWay());
            }

            CompanyInfoVO companyInfo = companyInfoMap.get(v.getSupplierId());
            if (Objects.nonNull(companyInfo)) {
                refundOrderResponse.setSupplierName(companyInfo.getSupplierName());
                if (CollectionUtils.isNotEmpty(companyInfo.getStoreVOList())) {
                    StoreVO store = companyInfo.getStoreVOList().get(0);
                    refundOrderResponse.setStoreId(store.getStoreId());
                    if(store.getStoreType() == StoreType.O2O){
                        refundOrderResponse.setStoreName(store.getStoreName());
                    }
                }
            }

            if (Objects.nonNull(v.getRefundBill()) && DeleteFlag.NO.equals(v.getRefundBill().getDelFlag())) {
                //从退单冗余信息中获取用户账户信息(防止用户修改后,查询的不是当时退单的账户信息)
                ReturnOrder returnOrderEntity = returnOrderMap.get(v.getReturnOrderCode());
                if (returnOrderEntity != null && returnOrderEntity.getCustomerAccount() != null) {
                    log.info("客户账户信息customerAccount: {}", returnOrderEntity.getCustomerAccount());
                    refundOrderResponse.setCustomerAccountName(
                            returnOrderEntity.getCustomerAccount().getCustomerBankName() + "" +
                                    " " + (
                                    StringUtils.isNotBlank(returnOrderEntity.getCustomerAccount().getCustomerAccountNo()) ?
                                            getDexAccount(returnOrderEntity.getCustomerAccount().getCustomerAccountNo()) : ""
                            ));
                }

                refundOrderResponse.setActualReturnPrice(v.getRefundBill().getActualReturnPrice());
                refundOrderResponse.setActualReturnPoints(v.getRefundBill().getActualReturnPoints());
                refundOrderResponse.setReturnAccount(v.getRefundBill().getOfflineAccountId());
                refundOrderResponse.setOfflineAccountId(v.getRefundBill().getOfflineAccountId());
                refundOrderResponse.setComment(v.getRefundBill().getRefundComment());
                refundOrderResponse.setRefundBillCode(v.getRefundBill().getRefundBillCode());
                refundOrderResponse.setReturnAccountName(parseAccount(v));
                // 退款时间以boss端审核时间为准
                if (Objects.equals(RefundStatus.FINISH, v.getRefundStatus())) {
                    refundOrderResponse.setRefundBillTime(v.getRefundBill().getCreateTime());
                }
                refundOrderResponse.setPayChannel(v.getRefundBill().getPayChannel());
                refundOrderResponse.setPayChannelId(v.getRefundBill().getPayChannelId());

                //设置支付方式
                String payChannelValue = setPayChannelValue(refundOrderResponse);
                refundOrderResponse.setPayChannelValue(payChannelValue);
            }
            responseList.add(refundOrderResponse);

        });
        return responseList;
    }

    /**
     * 设置支付方式
     * @param refundOrderResponse
     * @return
     */
    public String setPayChannelValue(RefundOrderResponse refundOrderResponse){
        StringBuilder sb = new StringBuilder();

        //纯积分需要显示 不需要显示线上还是线下
        //线下、积分+线下需要显示
        //积分+线上 需要判断是否付款 未付款的不显示
        RefundStatus payState = refundOrderResponse.getRefundStatus();
        Long points = refundOrderResponse.getReturnPoints();
        PayType payType = refundOrderResponse.getPayType();
        // 零元订单要验证 应退和实退都是0
        Boolean isZero = Objects.nonNull(refundOrderResponse.getReturnPrice())
                && refundOrderResponse.getReturnPrice().compareTo(BigDecimal.ZERO) < 1
                && Objects.nonNull(refundOrderResponse.getActualReturnPrice())
                && refundOrderResponse.getActualReturnPrice().compareTo(BigDecimal.ZERO) < 1;
        Long payChannelId = refundOrderResponse.getPayChannelId();

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
//            if(!isZero || PayType.OFFLINE.equals(payType)){
//                sb.append("+");
//            }
//        }
//
//        if(PayType.OFFLINE.equals(payType)){
//            //线下支付
//            sb.append(PayType.OFFLINE.getDesc());
//        }else
        if(PayType.ONLINE.equals(payType)){
            //线上支付
            if(Objects.nonNull(channelItemResponse)
                    && StringUtils.isNotBlank(channelItemResponse.getChannel())
                    && !isZero){
                String channel = channelItemResponse.getChannel().toUpperCase(Locale.ROOT);
                sb.append(PayWay.valueOf(channel).getDesc());
            }
        }

        //0元非积分订单  展示 -
        if(isZero && (null == points || points == 0)){
            sb = new StringBuilder();
            sb.append("-");
        }

        //非线下订单未退款 展示-
        if(!payState.equals(RefundStatus.FINISH)){
            sb = new StringBuilder();
            sb.append("-");
        }

        return sb.toString();
    }

    /**
     * 根据 RefundOrder 生成 RefundOrderResponse 对象
     *
     * @param refundOrder refundOrder
     * @return new RefundOrderResponse()
     */
    private RefundOrderResponse generateRefundOrderResponse(RefundOrder refundOrder) {
        RefundOrderResponse refundOrderResponse = new RefundOrderResponse();
        BeanUtils.copyProperties(refundOrder, refundOrderResponse);

        if (StringUtils.isNotBlank(refundOrder.getReturnOrderCode())) {
            ReturnOrder returnOrder = returnOrderRepository.findById(refundOrder.getReturnOrderCode()).orElse(null);
            if (returnOrder != null && returnOrder.getBuyer() != null) {
                refundOrderResponse.setCustomerId(returnOrder.getBuyer().getId());
                refundOrderResponse.setCustomerName(returnOrder.getBuyer().getName());
            }
        }
        CompanyInfoVO companyInfo = null;
        if (Objects.nonNull(refundOrder.getSupplierId())) {
            companyInfo = customerCommonService.getCompanyInfoById(refundOrder.getSupplierId());
        }
        if (Objects.nonNull(companyInfo)) {
            refundOrderResponse.setSupplierName(companyInfo.getSupplierName());
            if (CollectionUtils.isNotEmpty(companyInfo.getStoreVOList())) {
                StoreVO store = companyInfo.getStoreVOList().get(0);
                refundOrderResponse.setStoreId(store.getStoreId());
            }
        }

        if (Objects.nonNull(refundOrder.getRefundBill()) && DeleteFlag.NO.equals(refundOrder.getRefundBill().getDelFlag())) {
            //从退单冗余信息中获取用户账户信息(防止用户修改后,查询的不是当时退单的账户信息)
            ReturnOrder returnOrder = returnOrderRepository.findById(refundOrder.getReturnOrderCode()).orElse(null);
            if (returnOrder != null && returnOrder.getCustomerAccount() != null) {
                log.info("客户账户信息customerAccount: {}", returnOrder.getCustomerAccount());
                refundOrderResponse.setCustomerAccountName(returnOrder.getCustomerAccount().getCustomerBankName() + "" +
                        " " + (
                        StringUtils.isNotBlank(returnOrder.getCustomerAccount().getCustomerAccountNo()) ?
                                getDexAccount(returnOrder.getCustomerAccount().getCustomerAccountNo()) : ""
                ));
            }

            refundOrderResponse.setActualReturnPrice(refundOrder.getRefundBill().getActualReturnPrice());
            refundOrderResponse.setActualReturnPoints(refundOrder.getRefundBill().getActualReturnPoints());
            refundOrderResponse.setReturnAccount(refundOrder.getRefundBill().getOfflineAccountId());
            refundOrderResponse.setOfflineAccountId(refundOrder.getRefundBill().getOfflineAccountId());
            refundOrderResponse.setComment(refundOrder.getRefundBill().getRefundComment());
            refundOrderResponse.setRefundBillCode(refundOrder.getRefundBill().getRefundBillCode());
            refundOrderResponse.setReturnAccountName(parseAccount(refundOrder));
            // 退款时间以boss端审核时间为准
            if (Objects.equals(RefundStatus.FINISH, refundOrder.getRefundStatus())) {
                refundOrderResponse.setRefundBillTime(refundOrder.getRefundBill().getCreateTime());
            }
            refundOrderResponse.setPayChannel(refundOrder.getRefundBill().getPayChannel());
            refundOrderResponse.setPayChannelId(refundOrder.getRefundBill().getPayChannelId());
            refundOrderResponse.setGiftCardPrice(refundOrder.getGiftCardPrice());
        }

        return refundOrderResponse;
    }

    /**
     * 作废退款单
     *
     * @param id id
     */
    @Transactional
    public void destory(String id) {
        refundBillService.deleteByRefundId(id);
        batchDestory(Lists.newArrayList(id));
    }

    /**
     * 批量确认
     *
     * @param ids ids
     */
    @Transactional
    public void batchConfirm(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<RefundOrder> refundOrders = refundOrderRepository.findAllById(ids);
        refundOrders.forEach(refundOrder -> {
            refundOrder.setRefundStatus(RefundStatus.FINISH);
        });
    }

    /**
     * 批量作废
     *
     * @param ids ids
     */
    @Transactional
    public void batchDestory(List<String> ids) {
        updateRefundConsumer.accept(ids, RefundStatus.TODO);
    }

    /**
     * 拒绝退款添加退款原因
     *
     * @param id id
     */
    @Transactional
    public void refuse(String id, String refuseReason) {
        RefundOrder refundOrder = refundOrderRepository.findById(id).orElse(null);
        if (Objects.isNull(refundOrder)) {
            return;
        }
        refundOrder.setRefuseReason(refuseReason);
        refundOrderRepository.saveAndFlush(refundOrder);
        updateRefundConsumer.accept(Lists.newArrayList(id), RefundStatus.REFUSE);
    }

    /**
     * 合计退款金额
     *
     * @return BigDecimal
     */
    public BigDecimal sumReturnPrice(RefundOrderRequest refundOrderRequest) {
        //模糊匹配会员/商户名称，不符合条件直接返回0
        if (!this.likeCustomerAndSupplierName(refundOrderRequest)) {
            return BigDecimal.ZERO;
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);

        Root<RefundOrder> root = query.from(RefundOrder.class);
        query.select(builder.sum(root.get("refundBill").get("actualReturnPrice")));
        query.where(buildWhere(refundOrderRequest, root, query, builder));

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * 修改退款单
     */
    private BiConsumer<List<String>, RefundStatus> updateRefundConsumer = (ids, refundStatus) -> {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        refundOrderRepository.updateRefundOrderStatus(refundStatus, ids);
    };

    /**
     * 替代关联查询-模糊商家名称、模糊会员名称，以并且关系的判断
     *
     * @param refundOrderRequest
     * @return true:有符合条件的数据,false:没有符合条件的数据
     */
    private boolean likeCustomerAndSupplierName(final RefundOrderRequest refundOrderRequest) {
        boolean supplierLike = true;
        //商家名称
        if (StringUtils.isNotBlank(refundOrderRequest.getSupplierName())) {
            CompanyListRequest request = CompanyListRequest.builder()
                    .supplierName(refundOrderRequest.getSupplierName())
                    .storeType(refundOrderRequest.getStoreType())
                    .build();
            refundOrderRequest.setCompanyInfoIds(customerCommonService.listCompanyInfoIdsByCondition(request));
            if (CollectionUtils.isEmpty(refundOrderRequest.getCompanyInfoIds())) {
                supplierLike = false;
            }
        }
        //门店名称
        boolean shopLike = true;
        if (StringUtils.isNotBlank(refundOrderRequest.getStoreName())) {
            CompanyListRequest request = CompanyListRequest.builder()
                    .storeName(refundOrderRequest.getStoreName())
                    .storeType(refundOrderRequest.getStoreType())
                    .build();
            refundOrderRequest.setCompanyInfoIds(customerCommonService.listCompanyInfoIdsByCondition(request));
            if (CollectionUtils.isEmpty(refundOrderRequest.getCompanyInfoIds())) {
                shopLike = false;
            }
        }
        //模糊会员名称
        boolean customerLike = true;
        if (StringUtils.isNotBlank(refundOrderRequest.getCustomerName())) {
            CustomerDetailListByConditionRequest request = CustomerDetailListByConditionRequest.builder().customerName
                    (refundOrderRequest.getCustomerName()).build();
            refundOrderRequest.setCustomerDetailIds(customerCommonService.listCustomerDetailIdsByCondition(request));
            if (CollectionUtils.isEmpty(refundOrderRequest.getCustomerDetailIds())) {
                customerLike = false;
            }
        }
        return supplierLike && customerLike && shopLike;
    }

    private Specification<RefundOrder> findByRequest(final RefundOrderRequest refundOrderRequest) {
        return (Root<RefundOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> buildWhere(refundOrderRequest,
                root, query, cb);
    }

    /**
     * 构造列表查询的where条件
     *
     * @param refundOrderRequest request
     * @param root               root
     * @param query              query
     * @param cb                 cb
     * @return Predicates
     */
    private Predicate buildWhere(RefundOrderRequest refundOrderRequest, Root<RefundOrder> root,
                                 CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<RefundOrder, RefundBill> refundOrderRefundBillJoin = root.join("refundBill", JoinType.LEFT);
        refundOrderRefundBillJoin.on(cb.equal(refundOrderRefundBillJoin.get("delFlag"), DeleteFlag.NO));

        if (!StringUtils.isEmpty(refundOrderRequest.getAccountId())) {
            predicates.add(cb.equal(refundOrderRefundBillJoin.get("offlineAccountId"),
                    refundOrderRequest.getAccountId()));
        }

        if (!StringUtils.isEmpty(refundOrderRequest.getReturnOrderCode()) && !StringUtils.isEmpty(refundOrderRequest.getReturnOrderCode().trim())) {
            predicates.add(cb.like(root.get("returnOrderCode"), buildLike(refundOrderRequest.getReturnOrderCode())));
        }

        if (CollectionUtils.isNotEmpty(refundOrderRequest.getCompanyInfoIds())) {
            predicates.add(root.get("supplierId").in(refundOrderRequest.getCompanyInfoIds()));
        }

        if (CollectionUtils.isNotEmpty(refundOrderRequest.getCustomerDetailIds())) {
            predicates.add(root.get("customerDetailId").in(refundOrderRequest.getCustomerDetailIds()));
        }

        if (!CollectionUtils.isEmpty(refundOrderRequest.getReturnOrderCodes())) {
            predicates.add(root.get("returnOrderCode").in(refundOrderRequest.getReturnOrderCodes().stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())));
        }

        if (!CollectionUtils.isEmpty(refundOrderRequest.getRefundIds())) {
            predicates.add(root.get("refundId").in(refundOrderRequest.getRefundIds().stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())));
        }

        if (!StringUtils.isEmpty(refundOrderRequest.getRefundBillCode()) && !StringUtils.isEmpty(refundOrderRequest.getRefundBillCode().trim())) {
            predicates.add(cb.like(refundOrderRefundBillJoin.get("refundBillCode"),
                    buildLike(refundOrderRequest.getRefundBillCode())));
        }

        //根据支付方式查询支付单
        if(Objects.nonNull(refundOrderRequest.getQueryPayType())){

            //积分支付 或者 积分+xx支付
            if(refundOrderRequest.getQueryPayType().name().startsWith(QueryPayType.POINT.name())){
                predicates.add(cb.greaterThan(root.get("returnPoints"), NumberUtils.INTEGER_ZERO));
            }

            if (CollectionUtils.isNotEmpty(refundOrderRequest.getPayChannelIds())) {
                //线上支付
                predicates.add(refundOrderRefundBillJoin.get("payChannelId").in(refundOrderRequest.getPayChannelIds()));
            }else{
                //线下支付
                if(refundOrderRequest.getQueryPayType().equals(QueryPayType.OFFLINE) ||
                        refundOrderRequest.getQueryPayType().equals(QueryPayType.POINT_OFFLINE)){
                    predicates.add(cb.equal(root.get("payType"), PayType.OFFLINE));
                }
            }
        }

        if (Objects.nonNull(refundOrderRequest.getPayChannelId())) {
            predicates.add(cb.equal(refundOrderRefundBillJoin.get("payChannelId"),
                    refundOrderRequest.getPayChannelId()));
        }

        if (CollectionUtils.isNotEmpty(refundOrderRequest.getPayChannelIds())) {
            //线上支付
            predicates.add(refundOrderRefundBillJoin.get("payChannelId").in(refundOrderRequest.getPayChannelIds()));
        }

        if (Objects.nonNull(refundOrderRequest.getPayType())) {
            predicates.add(cb.equal(root.get("payType"), PayType.fromValue(refundOrderRequest.getPayType())));
        }

        //积分支付 或者 积分+xx支付
        if(refundOrderRequest.getOrderDeductionType() == OrderDeductionType.POINT){
            predicates.add(cb.greaterThan(root.get("returnPoints"), NumberUtils.INTEGER_ZERO));
        }

        //积分支付 或者 积分+xx支付
        if(refundOrderRequest.getOrderDeductionType() == OrderDeductionType.CASH_GIFT_CARD){
            predicates.add(cb.greaterThan(root.get("giftCardPrice"), NumberUtils.INTEGER_ZERO));
        }

        //待商家退款，拒绝退款的订单平台不应该看到
        if (Objects.isNull(refundOrderRequest.getExcludeRefundStatus()) || refundOrderRequest.getExcludeRefundStatus()) {
            predicates.add(cb.notEqual(root.get("refundStatus"), RefundStatus.TODO));
            predicates.add(cb.notEqual(root.get("refundStatus"), RefundStatus.REFUSE));
        }

        if (Objects.nonNull(refundOrderRequest.getRefundStatus())) {
            predicates.add(cb.equal(root.get("refundStatus"), refundOrderRequest.getRefundStatus()));
        }

        //收款开始时间
        if (!StringUtils.isEmpty(refundOrderRequest.getBeginTime())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(cb.greaterThanOrEqualTo(refundOrderRefundBillJoin.get("createTime"),
                    LocalDateTime.of(LocalDate
                            .parse(refundOrderRequest.getBeginTime(), formatter), LocalTime.MIN)));
        }

        //收款
        if (!StringUtils.isEmpty(refundOrderRequest.getEndTime())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(cb.lessThan(refundOrderRefundBillJoin.get("createTime"),
                    LocalDateTime.of(LocalDate
                            .parse(refundOrderRequest.getEndTime(), formatter), LocalTime.MIN).plusDays(1)));
        }

        //删除条件
        predicates.add(cb.equal(root.get("delFlag"), DeleteFlag.NO));

        query.orderBy(cb.desc(root.get("createTime")));

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

    private static String buildLike(String field) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append('%').append(XssUtils.replaceLikeWildcard(field)).append('%').toString();
    }

    /**
     * 解析收款账号
     *
     * @param refundOrder refundOrder
     * @return string
     */
    private String parseAccount(RefundOrder refundOrder) {
        StringBuilder accountName = new StringBuilder();
        if (PayType.OFFLINE.equals(refundOrder.getPayType()) && Objects.nonNull(refundOrder.getRefundBill().getOfflineAccountId())) {
            OfflineAccountGetByIdResponse offlineAccount = offlineQueryProvider.getById(new OfflineAccountGetByIdRequest
                    (refundOrder
                            .getRefundBill()
                            .getOfflineAccountId())).getContext();

            if (offlineAccount.getAccountId() != null) {
                log.info("解析收款账号offlineAccount: {}", offlineAccount);
                Integer length = offlineAccount.getAccountName().length();
                accountName.append(offlineAccount.getBankName())
                        .append(' ').append(StringUtils.isNotEmpty(offlineAccount.getBankNo()) ?
                        getDexAccount(offlineAccount.getBankNo()) : "");
            }
        }
        return accountName.toString();
    }

    /**
     * 更新退款单的备注字段
     *
     * @param refundId      id
     * @param refundComment comment
     */
    void updateRefundOrderReason(String refundId, String refundComment) {
        refundOrderRepository.updateRefundOrderReason(refundId, refundComment);
    }

    /**
     * 根据退单编号查询退款单
     *
     * @param returnOrderCode 退单编号
     * @return 退款单信息
     */
    public RefundOrder getRefundOrderByReturnOrderNo(String returnOrderCode) {
        return refundOrderRepository.findAllByReturnOrderCodeAndDelFlag(returnOrderCode, DeleteFlag.NO).orElseGet(() -> null);
    }

    /**
     * @description 根据退单编号批量查询退款单
     * @author  daiyitian
     * @date 2021/4/19 18:03
     * @param returnOrderCode 退单编号
     * @return java.util.List<com.wanmi.sbc.order.refund.model.root.RefundOrder> 退款单
     **/
    public List<RefundOrder> getRefundOrderByReturnOrderNos(List<String> returnOrderCode) {
        return refundOrderRepository.findByReturnOrderCodeInAndDelFlag(returnOrderCode, DeleteFlag.NO);
    }

    /**
     * 返回掩码后的字符串
     *
     * @param bankNo
     * @return
     */
    public String getDexAccount(String bankNo) {
        String middle = "**********";
        if (bankNo.length() > Constants.FOUR) {
            if (bankNo.length() <= Constants.EIGHT) {
                return middle;
            } else {
                bankNo = bankNo.substring(0, 4) + middle + bankNo.substring(bankNo.length() - 4);
            }
        } else {
            return middle;
        }
        return bankNo;
    }

    /**
     * 自动退款
     *
     * @param tradeList
     * @param returnOrderList
     * @param refundOrderList
     * @param operator
     */
    @Transactional
    public List<Object> autoRefund(List<Trade> tradeList, List<ReturnOrder> returnOrderList,
                                   List<RefundOrder> refundOrderList, Operator operator,Long offlineAccountId) {
        List<Object> rsultObject = new ArrayList<>();
        RefundRequest refundRequest = new RefundRequest();
        Map<String, RefundOrder> refundOrderMap =
                refundOrderList.stream().collect(Collectors.toMap(RefundOrder::getReturnOrderCode,  Function.identity()));
        Map<String, Trade> tradeMap = tradeList.stream().collect(Collectors.toMap(Trade::getId,  Function.identity()));
        for (ReturnOrder returnOrder : returnOrderList) {
            RefundOrder refundOrder = refundOrderMap.get(returnOrder.getId());
            //拼团订单-非0元订单退商品总金额
            Trade trade = tradeMap.get(returnOrder.getTid());
            refundRequest.setRefundBusinessId(returnOrder.getId());
            returnOrder.setOfflineAccountId(offlineAccountId);
            // 拼接描述信息
            String body = trade.getTradeItems().get(0).getSkuName() + " " + (trade.getTradeItems().get(0).getSpecDetails
                    () == null ? "" : trade.getTradeItems().get(0).getSpecDetails());
            if (trade.getTradeItems().size() > 1) {
                body = body + " 等多件商品";
            }
            refundRequest.setDescription(body);
            refundRequest.setClientIp("127.0.0.1");
            refundRequest.setStoreId(trade.getSupplier().getStoreId());
            refundRequest.setTid(returnOrder.getTid());
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() == BookingType.EARNEST_MONEY && StringUtils.isNotEmpty(trade.getTailOrderNo())) {

//                BigDecimal needBackAmount = returnOrder.getReturnPrice().getApplyStatus() ? returnOrder.getReturnPrice()
//                        .getApplyPrice() : refundOrder.getReturnPrice();

                ReturnPrice returnPrice = returnOrder.getReturnPrice();
                // 应退定金
                BigDecimal earnestPrice = returnPrice.getEarnestPrice();
                // 应退尾款
                BigDecimal tailPrice = returnPrice.getTailPrice();

                if (earnestPrice.compareTo(BigDecimal.ZERO) == 0 && tailPrice.compareTo(BigDecimal.ZERO) == 0) {
                    refundRequest.setAmount(BigDecimal.ZERO);
                    refundRequest.setBusinessId(trade.getId());
                    refundBooking(refundRequest, returnOrder, refundOrder, operator, trade);
                    continue;
                }

                CreditPayInfo creditPayInfo = trade.getCreditPayInfo();
                //授信支付 线上退款 如果是已还款的 则不需要退
                if(null != creditPayInfo
                        && creditPayInfo.getHasRepaid()
                        && (creditPayInfo.getCreditPayState() == CreditPayState.DEPOSIT
                        || creditPayInfo.getCreditPayState() == CreditPayState.ALL)){
                    earnestPrice = BigDecimal.ZERO;
                }

                //授信支付 线上退款 如果是已还款的 则不需要退
                if(null != creditPayInfo
                        && creditPayInfo.getHasRepaid()
                        && (creditPayInfo.getCreditPayState() == CreditPayState.BALANCE
                        || creditPayInfo.getCreditPayState() == CreditPayState.ALL)){
                    tailPrice = BigDecimal.ZERO;
                }

                if (earnestPrice.compareTo(BigDecimal.ZERO) >= 0) {
                    refundRequest.setAmount(earnestPrice);
                    refundRequest.setBusinessId(trade.getPayInfo().isMergePay() ? trade.getParentId() : trade.getId());
                    refundRequest.setTid(trade.getPayInfo().isMergePay() ? trade.getParentId() : trade.getId());
                    refundBooking(refundRequest, returnOrder, refundOrder, operator, trade);
                }
                if (tailPrice.compareTo(BigDecimal.ZERO) >= 0) {
                    // 两笔交易时需两个单号处理
                    refundRequest.setRefundBusinessId(returnOrder.getBusinessTailId());
                    RefundOrder refund = this.findRefundOrderByReturnOrderNo(returnOrder.getBusinessTailId());
                    refund.setRefundChannel(RefundChannel.TAIL);
                    refundRequest.setAmount(tailPrice);
                    refundRequest.setBusinessId(trade.getTailOrderNo());
                    refundRequest.setTid(trade.getTailOrderNo());
                    refundBooking(refundRequest, returnOrder, refund, operator, trade);
                }
            } else {
                BigDecimal fee = BigDecimal.ZERO;
                if(Objects.nonNull(returnOrder.getReturnPrice().getFee())) {
                    fee = fee.add(returnOrder.getReturnPrice().getFee());
                }
                BigDecimal amount = returnOrder.getReturnPrice().getApplyStatus() ? returnOrder.getReturnPrice()
                        .getApplyPrice() : fee.add(refundOrder.getReturnPrice());
                refundRequest.setAmount(amount);
                refundRequest.setBusinessId(trade.getPayInfo().isMergePay() ? trade.getParentId() : trade.getId());
                refundRequest.setTid(trade.getId());
                try {
                    // 退款金额等于0 直接添加流水 修改退单状态
                    if (refundRequest.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                        returnOrderService.refundOnline(returnOrder, refundOrder, operator);
                    } else {
                        PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(refundRequest.getBusinessId());
                        BigDecimal totalPrice =payTradeRecord.getPracticalPrice();
                        refundRequest.setTotalPrice(totalPrice);
                        refundRequest.setPayNo(payTradeRecord.getPayNo());

                        // 通用退款
                        rsultObject.add(refundCommon(refundRequest, returnOrder, refundOrder, operator, trade));
                    }
                    //推送退单信息到h5,发送mq消息
                    orderPushH5ProducerService.sendRefundOrderToH5Message(returnOrder.getId());
                    //同步退款推送h5
                    //smallService.newReturnSmallOrder(Collections.singletonList(returnOrder));
                } catch (SbcRuntimeException e) {
                    // 已退款 更新退单状态
                    if (e.getErrorCode() != null && e.getErrorCode().equals(EmpowerErrorCodeEnum.K060032.getCode())) {
                        returnOrderService.refundOnline(returnOrder, refundOrder, operator);
                    } else if (e.getErrorCode() != null && (e.getErrorCode().equals(EmpowerErrorCodeEnum.K060011.getCode()) || e.getErrorCode()
                            .equals(EmpowerErrorCodeEnum.K060012.getCode()))) {
                        //K-100211、k-100212编码异常是支付宝、微信自定义异常。表示退款异常，详细信息见异常信息
                        RefundOrderRefundRequest refundOrderRefundRequest = new RefundOrderRefundRequest();
                        refundOrderRefundRequest.setRid(returnOrder.getId());
                        refundOrderRefundRequest.setFailedReason(e.getResult());
                        refundOrderRefundRequest.setOperator(operator);
                        returnOrderService.refundFailed(refundOrderRefundRequest);
                    }
                    log.error("refund error,", e);
                    //throw e;
                }
            }

        }
        return rsultObject;
    }

    @Transactional
    @GlobalTransactional
    public void refundBooking(RefundRequest refundRequest, ReturnOrder returnOrder, RefundOrder refundOrder, Operator operator, Trade trade) {
        Object object;
        try {
            // 退款金额等于0 直接添加流水 修改退单状态
            if (refundRequest.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                returnOrderService.refundOnline(returnOrder, refundOrder, operator);
            } else {
                PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(refundRequest.getBusinessId());
                BigDecimal totalPrice =payTradeRecord.getPracticalPrice();
                refundRequest.setTotalPrice(totalPrice);
                refundRequest.setPayNo(payTradeRecord.getPayNo());
                // 通用退款
                refundCommon(refundRequest, returnOrder, refundOrder, operator, trade);
            }
        } catch (SbcRuntimeException e) {
            // 已退款 更新退单状态
            if (e.getErrorCode() != null && e.getErrorCode().equals(EmpowerErrorCodeEnum.K060032.getCode())) {
                returnOrderService.refundOnline(returnOrder, refundOrder, operator);
            } else if (e.getErrorCode() != null && (e.getErrorCode().equals(EmpowerErrorCodeEnum.K060011.getCode()) || e.getErrorCode()
                    .equals(EmpowerErrorCodeEnum.K060012.getCode()))) {
                //K-100211、k-100212编码异常是支付宝、微信自定义异常。表示退款异常，详细信息见异常信息
                RefundOrderRefundRequest refundOrderRefundRequest = new RefundOrderRefundRequest();
                refundOrderRefundRequest.setRid(returnOrder.getId());
                refundOrderRefundRequest.setFailedReason(e.getResult());
                refundOrderRefundRequest.setOperator(operator);
                returnOrderService.refundFailed(refundOrderRefundRequest);
            }
            log.error("refund error,", e);
            //throw e;
        }
    }

    /**
     * @return java.util.List<com.wanmi.sbc.order.bean.vo.PayOrderToEsVO>
     * @Author yangzhen
     * @Description //查询退款单，初始化刷入es
     * @Date 18:37 2021/1/4
     * @Param [request]
     **/
    public List<RefundOrderToEsVO> initEsRefundOrderByPage(RefundOrderRequest request) {

        //分页查询退款单id
        List<String> refundIds = refundOrderRepository.listByPage(request.getPageRequest());

        //查询退款单信息
        List<RefundOrder> allById = refundOrderRepository.findAllById(refundIds);

        //根据商家id 查询商家信息
        Map<Long, CompanyInfoVO> companyInfoVOMap = new HashMap<>();
        companyInfoVOMap.putAll(customerCommonService.listCompanyInfoByCondition(
                CompanyListRequest.builder().companyInfoIds(allById.stream()
                        .map(RefundOrder::getSupplierId).collect(Collectors.toList())).build()
        ).stream().collect(Collectors.toMap(CompanyInfoVO::getCompanyInfoId,  Function.identity())));

        //查询退款流水信息
        List<RefundBill> refundBills = refundBillRepository.findByRefundIds(refundIds);

        //退款单map
        Map<String, RefundBill> refundBillsMap = refundBills.stream().collect(
                Collectors.toMap(RefundBill::getRefundId, Function.identity()));

        List<ReturnOrder> returnOrders = org.apache.commons.collections4.IteratorUtils.toList(
                returnOrderRepository.findAllById(allById.stream().map(RefundOrder::getReturnOrderCode)
                        .collect(Collectors.toList())).iterator());

        Map<String, ReturnOrder> returnOrderMap
                = returnOrders.stream().collect(Collectors.toMap(ReturnOrder::getId, Function.identity(), (k1, k2) -> k1));

        List<RefundOrderToEsVO> refundOrderToEsVOList = new ArrayList<>();
        allById.forEach(refundOrder -> {
            RefundOrderToEsVO refundOrderToEsVO = new RefundOrderToEsVO();

            refundOrderToEsVO.setRefundId(refundOrder.getRefundId());
            refundOrderToEsVO.setRefundStatus(refundOrder.getRefundStatus());
            refundOrderToEsVO.setReturnOrderCode(refundOrder.getReturnOrderCode());
            refundOrderToEsVO.setCreateTime(refundOrder.getCreateTime());

            //填充公司信息
            CompanyInfoVO companyInfoVO = companyInfoVOMap.get(refundOrder.getSupplierId());
            if (Objects.nonNull(companyInfoVO)) {
                refundOrderToEsVO.setCompanyInfoId(companyInfoVO.getCompanyInfoId());
                refundOrderToEsVO.setSupplierName(companyInfoVO.getSupplierName());
            }

            //退单用户信息
            ReturnOrder returnOrder = returnOrderMap.get(refundOrder.getReturnOrderCode());
            if (Objects.nonNull(returnOrder)) {
                refundOrderToEsVO.setCustomerId(returnOrder.getBuyer().getId());
                refundOrderToEsVO.setCustomerName(returnOrder.getBuyer().getName());
            }

            //填充流水信息
            RefundBill refundBill = refundBillsMap.get(refundOrder.getRefundId());
            if (Objects.nonNull(refundBill)) {
                refundOrderToEsVO.setRefundBillCode(refundBill.getRefundBillCode());
                refundOrderToEsVO.setOfflineAccountId(refundBill.getOfflineAccountId());
                refundOrderToEsVO.setRefundBillTime(refundBill.getCreateTime());
            }

            refundOrderToEsVO.setRefundBillDelFlag(DeleteFlag.NO);
            refundOrderToEsVO.setRefundOrderDelFlag(DeleteFlag.NO);
            refundOrderToEsVOList.add(refundOrderToEsVO);

        });
        return refundOrderToEsVOList;
    }

    /**
     * @return java.util.List<com.wanmi.sbc.order.bean.vo.RefundOrderResponse>
     * @Author yangzhen
     * @Description //根据退款单编号，组装退款单信息 返回前端展示
     * @Date 10:44 2021/1/5
     * @Param [request]
     **/
    public List<RefundOrderVoFromEsResponse> queryRefundOrderForEs(RefundOrderRequest request) {

        if (CollectionUtils.isEmpty(request.getRefundIds())) {
            return Collections.emptyList();
        }

        //退款单id
        List<String> refundIds = request.getRefundIds();

        //查询退款单信息
        List<RefundOrder> allById = refundOrderRepository.findByRefundIdInOrderByCreateTimeDesc(refundIds);

        //根据商家id 查询商家信息
        Map<Long, CompanyInfoVO> companyInfoVOMap = new HashMap<>();
        companyInfoVOMap.putAll(customerCommonService.listCompanyInfoByCondition(
                CompanyListRequest.builder().companyInfoIds(allById.stream()
                        .map(RefundOrder::getSupplierId).collect(Collectors.toList())).build()
        ).stream().collect(Collectors.toMap(CompanyInfoVO::getCompanyInfoId,  Function.identity())));

        //查询退款流水信息
        List<RefundBill> refundBills = refundBillRepository.findByRefundIds(refundIds);

        //退款单map
        Map<String, RefundBill> refundBillsMap = refundBills.stream().collect(
                Collectors.toMap(RefundBill::getRefundId, Function.identity()));

        List<ReturnOrder> returnOrders = org.apache.commons.collections4.IteratorUtils.toList(
                returnOrderRepository.findAllById(allById.stream().map(RefundOrder::getReturnOrderCode)
                        .collect(Collectors.toList())).iterator());

        Map<String, ReturnOrder> returnOrderMap
                = returnOrders.stream().collect(Collectors.toMap(ReturnOrder::getId, Function.identity(), (k1, k2) -> k1));

        List<RefundOrderVoFromEsResponse> refundOrderToEsVOList = new ArrayList<>();
        allById.forEach(v -> {
            RefundOrderVoFromEsResponse refundOrderResponse = new RefundOrderVoFromEsResponse();
            KsBeanUtil.copyProperties(v, refundOrderResponse);

            //填充流水信息
            RefundBill refundBill = refundBillsMap.get(v.getRefundId());
            if (Objects.nonNull(refundBill)) {
                KsBeanUtil.copyProperties(refundBill, refundOrderResponse);
                //创建时间  退款单的创建时间
                refundOrderResponse.setCreateTime(v.getCreateTime());
                //退款时间  退款流水的创建时间
                refundOrderResponse.setRefundBillTime(refundBill.getCreateTime());
            }

            ReturnOrder returnOrder = returnOrderMap.get(v.getReturnOrderCode());
            if (Objects.nonNull(returnOrder)) {
                refundOrderResponse.setCustomerId(returnOrder.getBuyer().getId());
                refundOrderResponse.setCustomerName(returnOrder.getBuyer().getName());
            }
            if (Objects.nonNull(v.getRefundBill()) && DeleteFlag.NO.equals(v.getRefundBill().getDelFlag())) {
                //从退单冗余信息中获取用户账户信息(防止用户修改后,查询的不是当时退单的账户信息)
                ReturnOrder returnOrderEntity = returnOrderMap.get(v.getReturnOrderCode());
                if (returnOrderEntity != null && returnOrderEntity.getCustomerAccount() != null) {
                    log.info("客户账户信息customerAccount: {}", returnOrderEntity.getCustomerAccount());
                    refundOrderResponse.setCustomerAccountName(
                            returnOrderEntity.getCustomerAccount().getCustomerBankName() + "" +
                                    " " + (
                                    StringUtils.isNotBlank(returnOrderEntity.getCustomerAccount().getCustomerAccountNo()) ?
                                            getDexAccount(returnOrderEntity.getCustomerAccount().getCustomerAccountNo()) : ""
                            ));
                }

                refundOrderResponse.setActualReturnPrice(v.getRefundBill().getActualReturnPrice());
                refundOrderResponse.setActualReturnPoints(v.getRefundBill().getActualReturnPoints());
                refundOrderResponse.setReturnAccount(v.getRefundBill().getOfflineAccountId());
                refundOrderResponse.setOfflineAccountId(v.getRefundBill().getOfflineAccountId());
                refundOrderResponse.setComment(v.getRefundBill().getRefundComment());
                refundOrderResponse.setRefundBillCode(v.getRefundBill().getRefundBillCode());
                refundOrderResponse.setReturnAccountName(parseAccount(v));
                // 退款时间以boss端审核时间为准
                if (Objects.equals(RefundStatus.FINISH, v.getRefundStatus())) {
                    refundOrderResponse.setRefundBillTime(v.getRefundBill().getCreateTime());
                }
                refundOrderResponse.setPayChannel(v.getRefundBill().getPayChannel());
                refundOrderResponse.setPayChannelId(v.getRefundBill().getPayChannelId());
            }

            //填充公司信息
            CompanyInfoVO companyInfoVO = companyInfoVOMap.get(v.getSupplierId());
            if (Objects.nonNull(companyInfoVO)) {
                refundOrderResponse.setSupplierName(companyInfoVO.getSupplierName());
            }
            refundOrderToEsVOList.add(refundOrderResponse);

        });
        return refundOrderToEsVOList;
    }

    /**
     * @return java.util.List<com.wanmi.sbc.order.bean.vo.PayOrderToEsVO>
     * @Author yangzhen
     * @Description //查询退款单冗余信息，商家、用户等，返回结果新增至es
     * @Date 18:37 2021/1/4
     * @Param [request]
     **/
    public List<RefundOrderToEsVO> addEsRefundOrder(List<RefundOrder> lists) {

        //查询退款流水信息
        List<RefundBill> refundBills = refundBillRepository.findByRefundIds(
                lists.stream().map(RefundOrder::getRefundId)
                        .collect(Collectors.toList()));

        //退款单map
        Map<String, RefundBill> refundBillsMap = refundBills.stream().collect(
                Collectors.toMap(RefundBill::getRefundId, Function.identity()));

        //根据商家id 查询商家信息
        Map<Long, CompanyInfoVO> companyInfoVOMap = new HashMap<>();
        companyInfoVOMap.putAll(customerCommonService.listCompanyInfoByCondition(
                CompanyListRequest.builder().companyInfoIds(lists.stream().map(RefundOrder::getSupplierId)
                        .collect(Collectors.toList())).build()
        ).stream().collect(Collectors.toMap(CompanyInfoVO::getCompanyInfoId, Function.identity())));

        List<ReturnOrder> returnOrders = org.apache.commons.collections4.IteratorUtils.toList(
                returnOrderRepository.findAllById(lists.stream().map(RefundOrder::getReturnOrderCode)
                        .collect(Collectors.toList())).iterator());

        Map<String, ReturnOrder> returnOrderMap
                = returnOrders.stream().collect(Collectors.toMap(ReturnOrder::getId, Function.identity(), (k1, k2) -> k1));

        List<RefundOrderToEsVO> refundOrderToEsVOList = new ArrayList<>();
        lists.forEach(refundOrder -> {
            RefundOrderToEsVO refundOrderToEsVO = new RefundOrderToEsVO();

            refundOrderToEsVO.setRefundId(refundOrder.getRefundId());
            refundOrderToEsVO.setRefundStatus(refundOrder.getRefundStatus());
            refundOrderToEsVO.setReturnOrderCode(refundOrder.getReturnOrderCode());
            refundOrderToEsVO.setCreateTime(refundOrder.getCreateTime());

            //填充公司信息
            CompanyInfoVO companyInfoVO = companyInfoVOMap.get(refundOrder.getSupplierId());
            if (Objects.nonNull(companyInfoVO)) {
                refundOrderToEsVO.setCompanyInfoId(companyInfoVO.getCompanyInfoId());
                refundOrderToEsVO.setSupplierName(companyInfoVO.getSupplierName());
            }

            //退单用户信息
            ReturnOrder returnOrder = returnOrderMap.get(refundOrder.getReturnOrderCode());
            if (Objects.nonNull(returnOrder)) {
                refundOrderToEsVO.setCustomerId(returnOrder.getBuyer().getId());
                refundOrderToEsVO.setCustomerName(returnOrder.getBuyer().getName());
            }

            //填充流水信息
            RefundBill refundBill = refundBillsMap.get(refundOrder.getRefundId());
            if (Objects.nonNull(refundBill)) {
                refundOrderToEsVO.setRefundBillCode(refundBill.getRefundBillCode());
                refundOrderToEsVO.setOfflineAccountId(refundBill.getOfflineAccountId());
                refundOrderToEsVO.setRefundBillTime(refundBill.getCreateTime());
            }

            refundOrderToEsVO.setRefundBillDelFlag(DeleteFlag.NO);
            refundOrderToEsVO.setRefundOrderDelFlag(DeleteFlag.NO);
            refundOrderToEsVOList.add(refundOrderToEsVO);

        });
        return refundOrderToEsVOList;
    }


    /***
     * 通用退款
     * @param refundRequest
     * @param returnOrder
     * @param refundOrder
     * @param operator
     * @param trade
     */
    @Transactional
    @GlobalTransactional
    public Object refundCommon(RefundRequest refundRequest, ReturnOrder returnOrder, RefundOrder refundOrder, Operator operator, Trade trade) {
        boolean sendFlag = true;
        // 调用网关退款，退款公用接口
        Object object = payProvider.commonRefund(refundRequest).getContext().getObject();
        // 支付宝退款没有回调方法，故支付宝的交易流水在此添加
        if (object != null) {
            Map result = (Map) object;
            // 获得支付类型，并给定默认值
            String payType = Optional.ofNullable(result.get("payType")).orElse("NONE").toString();
            if (Arrays.asList("ALIPAY", "BALANCE", "CREDIT").contains(payType)) {
                returnOrderService.refundOnline(returnOrder, refundOrder, operator);
                sendFlag = false;
            }

            // 余额退款不需要回调
            if ("BALANCE".equals(payType)) {
                customerFundsProvider.addAmount(CustomerFundsAddAmountRequest.builder()
                        .amount(refundRequest.getAmount())
                        .customerId(trade.getBuyer().getId())
                        .businessId(trade.getId())
                        .build());
            }

            // add by zhengyang for Task: 【ID1035468】 授信支付退款和余额支付同样处理
            // 根据支付渠道ID判断是哪条渠道支付的
            if ("CREDIT".equals(payType)) {
                accountProvider.restoreCreditAmount(CreditAmountRestoreRequest.builder()
                        .amount(refundRequest.getAmount())
                        .customerId(trade.getBuyer().getId())
                        .build());
            }
        }
        if (sendFlag){
            // 异步处理退款发送
            orderMqConsumerService.sendReturnMiniProgramMsg(returnOrder);
        }
        return object;
    }

    /***
     * 向退单返回对象中填充订单流水号
     * @param refundOrderList
     */
    private void fillTradeNo2RefundOrderList(List<RefundOrderResponse> refundOrderList) {
        // 0.准备支付单中的退单ID集合，过滤掉尾款订单
        List<String> refundIds = refundOrderList.stream()
                .map(r->StringUtils.isNotBlank(r.getBusinessTailId()) ? r.getBusinessTailId() : r.getReturnOrderCode())
                .collect(Collectors.toList());
        if (WmCollectionUtils.isNotEmpty(refundIds)) {
            // 1.查询结果Map，给支付单对应记录赋值
            Map<String, String> tradeNoMap = payTradeRecordService.queryTradeNoMapByBusinessIds(refundIds);
            if (WmCollectionUtils.isNotEmpty(tradeNoMap)) {
                refundOrderList.forEach(refundOrder -> {
                    String returnOrderCode = StringUtils.isNotBlank(refundOrder.getBusinessTailId()) ?
                            refundOrder.getBusinessTailId() : refundOrder.getReturnOrderCode();
                    if (tradeNoMap.containsKey(returnOrderCode)) {
                        refundOrder.setTradeNo(tradeNoMap.get(returnOrderCode));
                    }
                });
            }
        }
    }
}
