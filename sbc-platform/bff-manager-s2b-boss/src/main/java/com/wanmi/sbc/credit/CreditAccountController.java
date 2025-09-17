package com.wanmi.sbc.credit;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.*;
import com.wanmi.sbc.account.api.response.credit.CreditRecoverPageResponse;
import com.wanmi.sbc.account.api.response.credit.CreditRepayDetailResponse;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountPageResponse;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.CreditTradePageRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdsRequest;
import com.wanmi.sbc.order.api.response.trade.CreditTradePageResponse;
import com.wanmi.sbc.order.api.response.trade.CreditTradeVOPageResponse;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdsResponse;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.BuyerVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeStateVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.service.PayServiceHelper;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/2/27 15:46
 * @description <p> 授信账户api </p>
 */
@RestController
@Validated
@Tag(name = "CreditAccountController", description = "S2B 平台端-授信账户API")
@RequestMapping("/credit")
public class CreditAccountController {

    @Autowired
    private CreditAccountQueryProvider creditAccountQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CreditRepayQueryProvider creditRepayQueryProvider;

    @Autowired
    private CreditAccountProvider creditAccountProvider;

    @Autowired
    private PayServiceHelper payServiceHelper;

    @Autowired
    private CustomerCacheService customerCacheService;


    /**
     * 授信账户详情
     *
     * @return
     */
    @GetMapping("/account-detail/{customerId}")
    @Operation(summary = "授信账户详情")
    @ReturnSensitiveWords(functionName = "f_boss_get_credit_account_detail_sign_word")
    public BaseResponse<CreditAccountDetailResponse> getCreditAccountDetail(@PathVariable("customerId") String customerId) {

        CreditAccountDetailRequest request = CreditAccountDetailRequest.builder()
                .customerId(customerId)
                .build();
        BaseResponse<CreditAccountDetailResponse> response = creditAccountQueryProvider.getCreditAccountDetail(request);
        response.getContext().setLogOutStatus(customerCacheService.getCustomerLogOutStatus(customerId));
        return response;
    }


    /**
     * 授信账户待还款订单
     *
     * @param request
     * @return
     */
    @PostMapping("/repay-list")
    @Operation(summary = "分页查询授信账户列表")
    @ReturnSensitiveWords(functionName = "f_boss_find_credit_repay_page_sign_word")
    public BaseResponse<MicroServicePage<CreditTradePageResponse>> findCreditRepayPage(@RequestBody CreditTradePageRequest request) {
        List<String> arrayList = Lists.newArrayList(PayState.PAID.getStateId(), PayState.PAID_EARNEST.getStateId());
        request.setPayOrderStatusList(arrayList);
        request.setHasRepaid(Boolean.FALSE);
        request.setNeedCreditRepayFlag(Boolean.TRUE);
        BaseResponse<CreditTradeVOPageResponse> creditRepayPage = tradeQueryProvider.findCreditRepayPage(request);
        MicroServicePage<TradeVO> newPage = creditRepayPage.getContext().getTradeVOList();
        List<TradeVO> tradeVOList = newPage.getContent();
        tradeVOList.forEach(tradeVO -> payServiceHelper.fillTradeBookingTimeOut(tradeVO));
        payServiceHelper.wrapperCreditTrade(tradeVOList);
        MicroServicePage<CreditTradePageResponse> response = this.getRepayPage(newPage,request);
        //获取会员注销状态
        LogOutStatus logOutStatus = customerCacheService.getCustomerLogOutStatus(request.getCustomerId());
        response.getContent().forEach(v->v.setLogOutStatus(logOutStatus));
        return BaseResponse.success(response);

    }


    /**
     * 分页查询授信账户列表
     *
     * @param request
     * @return
     */
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @PostMapping("/account-list")
    @Operation(summary = "分页查询授信账户列表")
    @ReturnSensitiveWords(functionName = "f_boss_find_credit_account_page_sign_word")
    public BaseResponse<MicroServicePage<CreditAccountPageResponse>> findCreditAccountPage(@RequestBody CreditAccountPageRequest request) {
        BaseResponse<MicroServicePage<CreditAccountPageResponse>> page = creditAccountQueryProvider.findCreditAccountPage(request);
        List<String> customerIds = page.getContext().getContent()
                .stream()
                .map(CreditAccountPageResponse::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        page.getContext().getContent().forEach(v->v.setLogOutStatus(map.get(v.getCustomerId())));
        return page;
    }

    /**
     * 分页查询已还款授信订单列表
     *
     * @param request
     * @return
     */
    @PostMapping("/has-repaid-list")
    @Operation(summary = "分页查询已还款授信订单列表")
    public BaseResponse<MicroServicePage<CreditRepayPageResponse>> findHasRepaidPage(@RequestBody @Valid CreditRepayPageRequest request) {
        return creditRepayQueryProvider.findCreditHasRepaidPage(request);
    }

    /**
     * 分页查询额度恢复记录
     *
     * @param request
     * @return
     */
    @PostMapping("/history-recover-list")
    @Operation(summary = "分页查询额度恢复记录")
    public BaseResponse<MicroServicePage<CreditRecoverPageResponse>> findCreditRecoverPage(@RequestBody @Valid CreditRecoverPageRequest request) {

        return creditAccountQueryProvider.findCreditRecoverPage(request);
    }

    /**
     * 分页查询已还款授信订单查看详情列表
     *
     * @param request
     * @return
     */
    @PostMapping("/has-repaid-detail")
    @Operation(summary = "分页查询已还款授信订单查看详情列表")
    public BaseResponse<Page<CreditRepayDetailResponse>> findRepayOrderPage(@RequestBody @Valid CreditRepayDetailRequest request) {
        BaseResponse<MicroServicePage<CreditRepayDetailResponse>> creditRepayPage = creditRepayQueryProvider.getCreditRepay(request);
        MicroServicePage<CreditRepayDetailResponse> detailResponse = creditRepayPage.getContext();
        List<CreditRepayDetailResponse> creditRepayDetailList = creditRepayPage.getContext().getContent();
        List<CreditRepayDetailResponse> repayOrderList = this.getRepayOrderList(creditRepayDetailList);
        Page<CreditRepayDetailResponse> newPage = new PageImpl<>(repayOrderList,
                request.getPageable(),
                detailResponse.getTotal());
        return BaseResponse.success(newPage);
    }

    @GetMapping("/testJob")
    public void test() {
        LocalDateTime now = LocalDateTime.now();
        CreditAccountPageRequest request = CreditAccountPageRequest.builder()
                .nowTime(now)
                .repayAmount(BigDecimal.ZERO)
                .build();
        //恢复额度
        creditAccountProvider.recoverCreditAmount(request);
    }

    /**
     * 获取订单信息，并设值到响应体中
     *
     * @param creditRepayDetailList
     * @return
     */
    private List<CreditRepayDetailResponse> getRepayOrderList(List<CreditRepayDetailResponse> creditRepayDetailList) {
        if (CollectionUtils.isEmpty(creditRepayDetailList)) {
            return Collections.emptyList();
        }
        Map<String, TradeVO> tradeMap = this.getTradeMap(creditRepayDetailList);
        return creditRepayDetailList.stream()
                .peek(repayDetail -> this.setRepayDetail(repayDetail, tradeMap))
                .collect(Collectors.toList());
    }

    /**
     * 授信订单信息设值
     *
     * @param repayDetail
     * @param tradeMap
     */
    private void setRepayDetail(CreditRepayDetailResponse repayDetail, Map<String, TradeVO> tradeMap) {
        TradeVO tradeVO = tradeMap.get(repayDetail.getOrderNo());
        BigDecimal totalPrice = tradeVO.getTradePrice().getTotalPrice();
        repayDetail.setOrderPrice(totalPrice);
        PayState payState = tradeVO.getTradeState().getPayState();
        FlowState flowState = tradeVO.getTradeState().getFlowState();
        repayDetail.setPayOrderStatus(payState.getStateId());
        List<TradeItemVO> tradeItems = tradeVO.getTradeItems();
        String skuName = tradeItems.stream().map(TradeItemVO::getSkuName)
                .collect(Collectors.joining(","));
        repayDetail.setSkuName(skuName);
        repayDetail.setFlowState(flowState.getStateId());
    }

    /**
     * 获取订单信息
     *
     * @param creditRepayDetailList
     * @return
     */
    private Map<String, TradeVO> getTradeMap(List<CreditRepayDetailResponse> creditRepayDetailList) {
        List<String> tidList = creditRepayDetailList.stream()
                .map(CreditRepayDetailResponse::getOrderNo)
                .collect(Collectors.toList());

        TradeGetByIdsRequest tidRequest = TradeGetByIdsRequest.builder()
                .tid(tidList)
                .build();
        BaseResponse<TradeGetByIdsResponse> tradeResponse = tradeQueryProvider.getByIds(tidRequest);
        TradeGetByIdsResponse context = tradeResponse.getContext();
        return Optional.ofNullable(context.getTradeVO())
                .orElseGet(Collections::emptyList).stream()
                .collect(Collectors.toMap(TradeVO::getId, Function.identity()));

    }

    /**
     * 订单属性设值
     *
     * @param trade
     * @return
     */
    private CreditTradePageResponse copyProperties(TradeVO trade) {
        // 购买人信息
        BuyerVO buyer = trade.getBuyer();
        // 下单信息
        TradeStateVO tradeState = trade.getTradeState();
        // 下单金额
        BigDecimal totalPrice = trade.getCanRepayPrice();
        //订单状态
        FlowState flowState = tradeState.getFlowState();

        List<TradeItemVO> tradeItems = trade.getTradeItems();
        //店铺名称
        String storeName = trade.getSupplier().getStoreName();
        Long storeId = trade.getSupplier().getStoreId();
        List<String> urlList = tradeItems.stream()
                .map(TradeItemVO::getPic)
                .collect(Collectors.toList());
        PayState payState = tradeState.getPayState();
        return CreditTradePageResponse.builder()
                .flowState(flowState)
                .orderPrice(totalPrice)
                .orderNo(trade.getId())
                .goodsNum(tradeItems.size())
                .customerName(buyer.getName())
                .payTime(tradeState.getPayTime())
                .storeId(storeId)
                .customerAccount(buyer.getAccount())
                .payOrderStatus(payState.getStateId())
                .storeName(storeName)
                .urlList(urlList)
                .build();
    }

    /**
     * page转换
     *
     * @param newPage
     * @return
     */
    private MicroServicePage<CreditTradePageResponse> getRepayPage(MicroServicePage<TradeVO> newPage,CreditTradePageRequest request) {
        List<CreditTradePageResponse> repayList = Optional.ofNullable(newPage.getContent())
                .orElseGet(Collections::emptyList).stream()
                .map(this::copyProperties)
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(request.getPageNum(), request.getPageSize());
        return new MicroServicePage<>(repayList, pageRequest, newPage.getTotal());
    }


}
