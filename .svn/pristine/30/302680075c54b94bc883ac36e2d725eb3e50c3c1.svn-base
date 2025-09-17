package com.wanmi.sbc.customercredit;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.credit.CreditOrderQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditRepayDetailRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRepayDetailResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
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
import com.wanmi.sbc.trade.PayServiceHelper;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chenli
 * @ClassName CustomerCreditOrderBaseController
 * @Description 客户授信账户关联订单bff
 * @date 2021/3/3 13:59
 */
@Tag(description= "客户授信账户关联订单API", name = "CustomerCreditOrderBaseController")
@RestController
@Validated
@RequestMapping(value = "/credit/order")
public class CustomerCreditOrderBaseController {
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CreditOrderQueryProvider creditOrderQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CreditRepayQueryProvider creditRepayQueryProvider;

    @Autowired
    private PayServiceHelper payServiceHelper;

    /**
     * 授信订单使用记录
     *
     * @param request
     * @return
     */
    @PostMapping("/used-list")
    @Operation(summary = "授信订单使用记录")
    public BaseResponse<MicroServicePage<CreditTradePageResponse>> findCreditRepayPage(@RequestBody CreditTradePageRequest request) {
        String operatorId = commonUtil.getOperatorId();
        List<String> payStateList = Lists.newArrayList(PayState.PAID.getStateId(), PayState.PAID_EARNEST.getStateId());
        request.setPayOrderStatusList(payStateList);
        request.setCustomerId(operatorId);
        BaseResponse<CreditTradeVOPageResponse> creditUsedPage = tradeQueryProvider.findCreditUsedPage(request);
        MicroServicePage<TradeVO> newPage = creditUsedPage.getContext().getTradeVOList();

        if(null != newPage){
            List<TradeVO> tradeVOList = newPage.getContent();
            tradeVOList.forEach(tradeVO -> payServiceHelper.fillTradeBookingTimeOut(tradeVO));
            payServiceHelper.wrapperCreditTrade(tradeVOList);
            MicroServicePage<CreditTradePageResponse> response = this.getRepayPage(newPage, request);
            return BaseResponse.success(response);
        }else{
            return BaseResponse.success(new MicroServicePage());
        }

    }

    /**
     * 关联订单（已还款、还款中）
     *
     * @param request
     * @return
     */
    @PostMapping("/has-repaid-order")
    @Operation(summary = "关联订单（已还款、还款中）")
    public BaseResponse<Page<CreditRepayDetailResponse>> findRepayOrderPage(@RequestBody @Valid CreditRepayDetailRequest request) {
        BaseResponse<MicroServicePage<CreditRepayDetailResponse>> creditRepayPage = creditRepayQueryProvider.getCreditRepay(request);
        MicroServicePage<CreditRepayDetailResponse> response = creditRepayPage.getContext();
        List<CreditRepayDetailResponse> creditRepayList = creditRepayPage.getContext().getContent();
        List<CreditRepayDetailResponse> repayOrderList = this.getRepayOrderList(creditRepayList);
        Page<CreditRepayDetailResponse> newPage = new PageImpl<>(repayOrderList, request.getPageable(), response.getTotal());
        return BaseResponse.success(newPage);
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
        repayDetail.setOrderOriginPrice(tradeVO.getTradePrice().getTotalPrice());
        repayDetail.setOrderPrice(tradeVO.getCanRepayPrice());
        PayState payState = tradeVO.getTradeState().getPayState();
        FlowState flowState = tradeVO.getTradeState().getFlowState();
        repayDetail.setPayOrderStatus(payState.getStateId());
        repayDetail.setFlowState(flowState.getStateId());
        List<TradeItemVO> tradeItems = tradeVO.getTradeItems();
        List<String> urlList = tradeItems.stream().map(TradeItemVO::getPic).collect(Collectors.toList());
        repayDetail.setUrlList(urlList);
        repayDetail.setGoodsNum(tradeItems.size());
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
        //扭转预售商品支付尾款状态为已作废
        context.getTradeVO().forEach(tradeVO -> payServiceHelper.fillTradeBookingTimeOut(tradeVO));
        payServiceHelper.wrapperCreditTrade(context.getTradeVO());
        return context.getTradeVO().stream()
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
        Boolean buyCycleFlag = Boolean.FALSE;
        if (Objects.nonNull(trade.getOrderTag())) {
            buyCycleFlag = trade.getOrderTag().getBuyCycleFlag();
        }
            return CreditTradePageResponse.builder()
                    .flowState(flowState)
                    .orderPrice(totalPrice)
                    .orderOriginPrice(trade.getTradePrice().getTotalPrice())
                    .orderNo(trade.getId())
                    .goodsNum(tradeItems.size())
                    .customerName(buyer.getName())
                    .payTime(tradeState.getPayTime())
                    .storeId(storeId)
                    .customerAccount(buyer.getAccount())
                    .payOrderStatus(payState.getStateId())
                    .storeName(storeName)
                    .urlList(urlList)
                    .buyCycleFlag(buyCycleFlag)
                    .build();
    }

    /**
     * page转换
     *
     * @param newPage
     * @return
     */
    private MicroServicePage<CreditTradePageResponse> getRepayPage(MicroServicePage<TradeVO> newPage, CreditTradePageRequest request) {
        List<CreditTradePageResponse> repayList = Optional.ofNullable(newPage.getContent())
                .orElseGet(Collections::emptyList).stream()
                .map(this::copyProperties)
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(request.getPageNum(), request.getPageSize());
        return new MicroServicePage<>(repayList, pageRequest, newPage.getTotal());
    }

}
