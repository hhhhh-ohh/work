package com.wanmi.sbc.returnorder;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditOrderQueryRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.small.SmallProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByReturnOrderNoRequest;
import com.wanmi.sbc.order.api.request.returnorder.CanReturnItemNumByTidRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderAddRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByIdRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderListByTidRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderOfflineRefundForSupplierRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderOnlineModifyPriceRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderPageRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderQueryRefundPriceRequest;
import com.wanmi.sbc.order.api.request.small.ReturnSmallOrderRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByReturnOrderNoResponse;
import com.wanmi.sbc.order.api.response.returnorder.ReturnOrderAddResponse;
import com.wanmi.sbc.order.bean.dto.CompanyDTO;
import com.wanmi.sbc.order.bean.dto.RefundBillDTO;
import com.wanmi.sbc.order.bean.dto.ReturnCustomerAccountDTO;
import com.wanmi.sbc.order.bean.dto.ReturnOrderDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.returnorder.request.ReturnOfflineRefundRequest;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.refundcause.RefundCauseQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseQueryRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryOneResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 退货
 * Created by sunkun on 2017/11/23.
 */
@Tag(name = "StoreReturnOrderController", description = "退货服务API")
@RestController
@Validated
@RequestMapping("/return")
@Slf4j
public class StoreReturnOrderController {

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Resource
    private StoreQueryProvider storeQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Resource
    private PayOrderQueryProvider payOrderQueryProvider;

    @Resource
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;
    @Resource
    private CreditRepayQueryProvider repayQueryProvider;
    @Autowired
    private RefundCauseQueryProvider refundCauseQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private StoreMessageBizService storeMessageBizService;
    /***
     * 状态-还款中
     */
    private static final Integer REPAYING = 0;


    /**
     * 分页查询 from ES
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询 from ES")
    @EmployeeCheck
    @RequestMapping(method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_supplier_return_order_page_sign_word")
    public BaseResponse<MicroServicePage<ReturnOrderVO>> page(@RequestBody ReturnOrderPageRequest request) {
        request.setStoreType(commonUtil.getStoreType());
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.setReturnOrderType(ReturnOrderType.GENERAL_TRADE);
        MicroServicePage<ReturnOrderVO> page = returnOrderQueryProvider.page(request).getContext().getReturnOrderPage();

        List<String> customerIds = page.getContent().stream().map(v -> v.getBuyer().getId()).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        page.getContent().forEach(returnOrder -> {
            RefundOrderByReturnOrderNoResponse refundOrderByReturnCodeResponse = refundOrderQueryProvider.getByReturnOrderNo(new RefundOrderByReturnOrderNoRequest(returnOrder.getId())).getContext();
            if (Objects.nonNull(refundOrderByReturnCodeResponse)) {
                returnOrder.setRefundStatus(refundOrderByReturnCodeResponse.getRefundStatus());
            }
            //判断订单会员是否注销
            if (Objects.nonNull(returnOrder.getBuyer())
                    && map.containsKey(returnOrder.getBuyer().getId())) {
                returnOrder.setLogOutStatus(
                        map.get(returnOrder.getBuyer().getId())
                );
            }
        });
        return BaseResponse.success(page);
    }

    /**
     * 创建退单
     *
     * @param returnOrder
     * @return
     */
    @Operation(summary = "创建退单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse<ReturnOrderAddResponse> create(@RequestBody ReturnOrderDTO returnOrder) {
        //越权校验
        TradeVO trade = tradeQueryProvider
                .getById(TradeGetByIdRequest.builder().tid(returnOrder.getTid()).build())
                .getContext().getTradeVO();

        commonUtil.checkStoreId(trade.getSupplier().getStoreId());

        String id = returnOrder.getRefundCause().getId();
        RefundCauseQueryOneResponse context = refundCauseQueryProvider
                .findById(RefundCauseQueryRequest.builder().id(id).build()).getContext();
        returnOrder.setRefundCause(context);
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if (Objects.isNull(companyInfo)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Operator operator = commonUtil.getOperator();
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(commonUtil.getStoreId())
        ).getContext().getStoreVO();
        if (Objects.isNull(store)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        BaseResponse<CreditRepayPageResponse> repayRes = repayQueryProvider
                .findRepayOrderByOrderId(CreditOrderQueryRequest.builder().orderId(returnOrder.getTid()).build());
        if (Objects.nonNull(repayRes.getContext())) {
            if(REPAYING.equals(repayRes.getContext().getRepayStatus())){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050076);
            }
        }else{
            log.error("StoreReturnOrderController create called findRepayOrderByOrderId error,params:{},return:{}"
                    , returnOrder.getTid(), JSONObject.toJSONString(repayRes));
        }
        returnOrder.setCompany(CompanyDTO.builder().companyInfoId(companyInfo.getCompanyInfoId())
                .companyCode(companyInfo.getCompanyCode()).supplierName(companyInfo.getSupplierName())
                .storeId(commonUtil.getStoreId()).storeName(store.getStoreName()).companyType(store.getCompanyType()).build());
        //再次校验是否可退
        verifyIsReturnable(trade);
        returnOrder.setChannelType(trade.getChannelType());
        returnOrder.setDistributorId(trade.getDistributorId());
        returnOrder.setInviteeId(trade.getInviteeId());
        returnOrder.setShopName(trade.getShopName());
        returnOrder.setDistributorName(trade.getDistributorName());
        returnOrder.setDistributeItems(trade.getDistributeItems());
        returnOrder.setTerminalSource(TerminalSource.SUPPLIER);
        BaseResponse<ReturnOrderAddResponse> response = returnOrderProvider.add(
                ReturnOrderAddRequest.builder().returnOrder(returnOrder).operator(operator).build());
        operateLogMQUtil.convertAndSend(
                "订单", "代客退单", "退单号" + response.getContext().getReturnOrderId());
        return response;
    }


    /**
     * 线下退款
     *
     * @param rid
     * @param request
     * @return
     */
    @Operation(summary = "线下退款")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/refund/{rid}/offline", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse refundOffline(@PathVariable String rid,
                                      @RequestBody @Valid ReturnOfflineRefundRequest request) {
        //客户账号
        ReturnCustomerAccountDTO customerAccount = null;
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                .build()).getContext();
        if (returnOrder.getReturnPrice().getTotalPrice().compareTo(request.getActualReturnPrice()) < 0) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050042, new Object[]{returnOrder.getReturnPrice().getTotalPrice()});
        }
        String tid = returnOrder.getTid();
        BigDecimal canReturnFee = returnOrderQueryProvider.getCanReturnFee(ReturnOrderListByTidRequest.builder().tid(tid)
                .providerId(returnOrder.getProviderId())
                .build()).getContext();
        BigDecimal fee = Objects.nonNull(request.getFee()) ? request.getFee() : BigDecimal.ZERO;
        if(fee.compareTo(canReturnFee) > Constants.ZERO){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050048, new Object[]{canReturnFee});
        }
        if (Objects.equals(request.getCustomerAccountId(), Constants.STR_0)) {
            customerAccount = new ReturnCustomerAccountDTO();
            customerAccount.setCustomerAccountName(request.getCustomerAccountName());
            customerAccount.setCustomerBankName(request.getCustomerBankName());
            customerAccount.setCustomerAccountNo(request.getCustomerAccountNo());
            customerAccount.setCustomerId(request.getCustomerId());
        }
        //退款流水
        RefundBillDTO refundBill = new RefundBillDTO();
        refundBill.setActualReturnPrice(request.getActualReturnPrice().add(fee));
        refundBill.setActualReturnPoints(request.getActualReturnPoints());
        refundBill.setRefundComment(request.getRefundComment());
        // 客户账号
        refundBill.setCustomerAccountId(request.getCustomerAccountId());
        refundBill.setCreateTime(StringUtils.isNotEmpty(request.getCreateTime()) ? DateUtil.parseDate(request.getCreateTime()) :
                LocalDateTime.now());
        BaseResponse response =
                returnOrderProvider.offlineRefundForSupplier(ReturnOrderOfflineRefundForSupplierRequest.builder().rid(rid)
                .fee(fee)
                .customerAccount(customerAccount).refundBill(refundBill).operator(commonUtil.getOperator()).build());
        // ============= 处理平台的消息发送：商家的待退款（线下）订单提醒 START =============
        storeMessageBizService.handleForTradeWaitRefund(response, rid);
        // ============= 处理平台的消息发送：商家的待退款（线下）订单提醒 START =============
        return response;
    }

    /**
     * 商家退款申请(修改退单价格新增流水)
     *
     * @param rid
     * @param request
     * @return
     */
    @Operation(summary = "商家退款申请(修改退单价格新增流水)")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/edit/price/{rid}", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse onlineEditPrice(@PathVariable String rid, @RequestBody @Valid ReturnOfflineRefundRequest request) {
        BigDecimal refundPrice = returnOrderQueryProvider.queryRefundPrice(ReturnOrderQueryRefundPriceRequest.builder()
                .rid(rid).build()).getContext().getRefundPrice();
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                .build()).getContext();
        //越权校验
        commonUtil.checkStoreId(returnOrder.getCompany().getStoreId());
        String tid = returnOrder.getTid();
        BigDecimal canReturnFee = returnOrderQueryProvider.getCanReturnFee(ReturnOrderListByTidRequest.builder().tid(tid)
                .providerId(returnOrder.getProviderId())
                .build()).getContext();
//        refundPrice = refundPrice.subtract(canReturnFee);

        if (refundPrice.compareTo(request.getActualReturnPrice()) < 0) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050042, new Object[]{refundPrice});
        }
        if(Objects.nonNull(request.getFee()) && request.getFee().compareTo(canReturnFee) > Constants.ZERO){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050048, new Object[]{canReturnFee});
        }
        //客户账号
        ReturnCustomerAccountDTO customerAccount = null;

        if (null != request.getCustomerAccountId()
                && Objects.equals(request.getCustomerAccountId(), "0")) {
            customerAccount = new ReturnCustomerAccountDTO();
            customerAccount.setCustomerAccountName(request.getCustomerAccountName());
            customerAccount.setCustomerBankName(request.getCustomerBankName());
            customerAccount.setCustomerAccountNo(request.getCustomerAccountNo());
            customerAccount.setCustomerId(request.getCustomerId());
        }
        BigDecimal actualReturnPrice = request.getActualReturnPrice();
        // 实退金额  这里要加上运费 (自提订单没有运费)
        if (Objects.nonNull(request.getFee())) {
             actualReturnPrice = request.getActualReturnPrice().add(request.getFee());
        }
        BaseResponse response = returnOrderProvider.onlineModifyPrice(ReturnOrderOnlineModifyPriceRequest.builder()
                .returnOrder(KsBeanUtil.convert(returnOrder, ReturnOrderDTO.class))
                .refundComment(request.getRefundComment())
                .actualReturnPrice(actualReturnPrice)
                .actualReturnPoints(request.getActualReturnPoints())
                .customerAccountId(request.getCustomerAccountId())
                .customerAccount(customerAccount)
                .fee(Optional.ofNullable(request.getFee()).orElse(BigDecimal.ZERO))
                .operator(commonUtil.getOperator()).build());
        // ============= 处理平台的消息发送：商家的待退款（线上）订单提醒 START =============
        storeMessageBizService.handleForTradeWaitRefund(response, rid);
        // ============= 处理平台的消息发送：商家的待退款（线上）订单提醒 END =============
        return response;
    }

    /**
     * 是否可创建退单
     *
     * @return
     */
    @Operation(summary = "是否可创建退单")
    @Parameters({
            @Parameter(name = "tid", description = "退单Id", required = true)
    })
    @RequestMapping(value = "/returnable/{tid}", method = RequestMethod.GET)
    public BaseResponse isReturnable(@PathVariable String tid) {
        BaseResponse<FindPayOrderResponse> findPayOrderResponseBaseResponse = payOrderQueryProvider.findPayOrder(FindPayOrderRequest.builder().value(tid).build());
        FindPayOrderResponse payOrder = findPayOrderResponseBaseResponse.getContext();
        if (Objects.isNull(payOrder) || Objects.isNull(payOrder.getPayOrderStatus()) || payOrder.getPayOrderStatus() != PayOrderStatus.PAYED) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050015);
        }
        TradeVO trade = returnOrderQueryProvider.queryCanReturnItemNumByTid(CanReturnItemNumByTidRequest.builder()
                .tid(tid).build()).getContext();
        verifyIsReturnable(trade);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 校验是否可退
     *
     * @param trade
     */
    private void verifyIsReturnable(TradeVO trade ) {
        OrderTagVO orderTag = trade.getOrderTag();
        //周期购订单
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        if (buyCycleFlag) {
            TradeStateVO tradeState = trade.getTradeState();
            DeliverStatus deliverStatus = tradeState.getDeliverStatus();
            //已经全部发货的不能退
            if (Objects.equals(DeliverStatus.SHIPPED,deliverStatus)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
            Boolean isReturn = trade.getIsReturn();
            //周期购只能一次全退，有售后就不能再次申请
            if (isReturn) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
        }
        //验证是否是视频号订单
        if (Objects.nonNull(trade.getSellPlatformType())){
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070096, new Object[] {"视频号订单"});
        }
        // 验证是否砍价订单
        if (BooleanUtils.isTrue(trade.getBargain())) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070096, new Object[] {"砍价订单"});
        }
            FlowState flowState = trade.getTradeState().getFlowState();
            Boolean transitReturn = trade.getTransitReturn();
            //如果是虚拟订单，则校验订单状态，并且不校验在途退货
            if (Objects.nonNull(orderTag) && orderTag.getVirtualFlag()) {
                //如果订单已完成，则没有可退商品
                if(FlowState.COMPLETED.equals(flowState)) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050081);
                }
            } else {
                if (!buyCycleFlag && Boolean.FALSE.equals(transitReturn)) {
                    if (flowState == FlowState.DELIVERED || flowState == FlowState.DELIVERED_PART) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050082);
                    }
                }
            }
            TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
            if (Objects.nonNull(orderTag) && orderTag.getElectronicCouponFlag()) {
                request.setConfigType(ConfigType.ORDER_SETTING_VIRTUAL_APPLY_REFUND);
            } else {
                request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
            }
            TradeConfigGetByTypeResponse config = auditQueryProvider.getTradeConfigByType(request).getContext();

            boolean flag = config.getStatus() == 0;
            //申请退单状态数据库状态优先
            if (Objects.nonNull(trade.getTradeState().getRefundStatus())){
                flag = trade.getTradeState().getRefundStatus() == 0;
            }
            if (flag) {
                //已完成订单 下单时已完成订单申请退单开关 为关
                if (trade.getTradeState().getFlowState() == FlowState.COMPLETED) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
                }
//                else {
//                    if (Boolean.FALSE.equals(transitReturn)) {
//                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
//                    }
//                }
            }

            JSONObject content = JSON.parseObject(config.getContext());
            Integer day = content.getObject("day", Integer.class);
            if (trade.getTradeState().getFlowState() == FlowState.COMPLETED) {
                if (Objects.isNull(trade.getTradeState().getEndTime())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }

                if (trade.getTradeState().getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() < LocalDateTime.now().minusDays(day).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
                }
            }
            List<TradeItemVO> canReturnItems = trade.getTradeItems().parallelStream()
                    .filter(tradeItemVO -> tradeItemVO.getCanReturnNum() > 0)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(canReturnItems)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050081);
            }
    }


}
