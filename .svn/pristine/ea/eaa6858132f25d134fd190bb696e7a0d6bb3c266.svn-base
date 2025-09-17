package com.wanmi.sbc.pay.service.impl;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsDetailAddRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsModifyRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.order.api.provider.paycallback.PayAndRefundCallBackProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.trade.CreditCallBackRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayCallBackOnlineBatchRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.InternalPayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @type BalancePayService.java
 * @desc
 * @date 2022/11/22 16:40
 */
@PayPluginService(type = PayChannelType.BALANCE)
public class BalancePayService extends InternalPayService {

    @Autowired CustomerFundsQueryProvider customerFundsQueryProvider;

    @Autowired CustomerFundsDetailProvider customerFundsDetailProvider;

    @Autowired CustomerFundsProvider customerFundsProvider;

    @Autowired PayAndRefundCallBackProvider payAndRefundCallBackProvider;

    @Override
    protected void checkAccountPay(PayChannelRequest request, InternalPayBaseBean bean) {
        // 处理用户余额, 校验余额是否可用
        CustomerFundsByCustomerIdResponse fundsByCustomerIdResponse =
                customerFundsQueryProvider
                        .getByCustomerId(
                                new CustomerFundsByCustomerIdRequest(commonUtil.getOperatorId()))
                        .getContext();
        if (fundsByCustomerIdResponse.getWithdrawAmount().compareTo(bean.getTotalPrice()) < 0) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020027);
        }
        // 设置账户id
        bean.setAccountId(fundsByCustomerIdResponse.getCustomerFundsId());
        bean.setAccountBalance(fundsByCustomerIdResponse.getAccountBalance());
    }

    @Override
    protected void updateAccount(PayChannelRequest request, InternalPayBaseBean bean) {
        CustomerFundsModifyRequest fundsModifyRequest = new CustomerFundsModifyRequest();
        fundsModifyRequest.setCustomerFundsId(bean.getAccountId());
        fundsModifyRequest.setExpenseAmount(bean.getTotalPrice());
        customerFundsProvider.balancePay(fundsModifyRequest);
    }

    @Override
    protected void addDetail(PayChannelRequest request, InternalPayBaseBean bean) {
        CustomerFundsDetailAddRequest customerFundsDetailAddRequest =
                new CustomerFundsDetailAddRequest();
        customerFundsDetailAddRequest.setCustomerId(commonUtil.getOperatorId());
        customerFundsDetailAddRequest.setBusinessId(bean.getId());
        customerFundsDetailAddRequest.setFundsType(FundsType.BALANCE_PAY);
        customerFundsDetailAddRequest.setReceiptPaymentAmount(bean.getTotalPrice());
        customerFundsDetailAddRequest.setFundsStatus(FundsStatus.YES);
        customerFundsDetailAddRequest.setAccountBalance(
                bean.getAccountBalance().subtract(bean.getTotalPrice()));
        customerFundsDetailAddRequest.setSubType(FundsSubType.BALANCE_PAY);
        customerFundsDetailAddRequest.setCreateTime(LocalDateTime.now());
        customerFundsDetailProvider.batchAdd(Lists.newArrayList(customerFundsDetailAddRequest));
    }

    @Override
    protected void payTradeRecord(
            PayChannelRequest request, InternalPayBaseBean bean) {
        // 新增交易记录

        if (payServiceHelper.isPayMember(request.getId())) {
            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
            payTradeRecordRequest.setBusinessId(bean.getId());
            payTradeRecordRequest.setApplyPrice(bean.getTotalPrice());
            payTradeRecordRequest.setPracticalPrice(payTradeRecordRequest.getApplyPrice());
            payTradeRecordRequest.setResult_code("SUCCESS");
            payTradeRecordRequest.setChannelItemId(request.getChannelItemId());
            payTradeRecordProvider.queryAndSave(payTradeRecordRequest);

        } else {

            List<PayTradeRecordRequest> payTradeRecordRequests =
                    bean.getTradeVOList().stream()
                            .map(
                                    tradeVO -> {
                                        PayTradeRecordRequest payTradeRecordRequest =
                                                new PayTradeRecordRequest();
                                        payTradeRecordRequest.setBusinessId(
                                                bean.getId()
                                                                .startsWith(
                                                                        GeneratorService
                                                                                ._PREFIX_TRADE_TAIL_ID)
                                                        ? bean.getId()
                                                        : tradeVO.getId());
                                        if (payServiceHelper.isCreditRepayFlag(request.getId())) {
                                            payTradeRecordRequest.setApplyPrice(
                                                    tradeVO.getCanRepayPrice());
                                        } else {
                                            payTradeRecordRequest.setApplyPrice(
                                                    payServiceHelper.calcTotalPriceByYuan(
                                                            Collections.singletonList(tradeVO)));
                                        }
                                        payTradeRecordRequest.setPracticalPrice(
                                                payTradeRecordRequest.getApplyPrice());
                                        payTradeRecordRequest.setResult_code("SUCCESS");
                                        payTradeRecordRequest.setChannelItemId(
                                                request.getChannelItemId());
                                        return payTradeRecordRequest;
                                    })
                            .collect(Collectors.toList());
            payTradeRecordProvider.batchSavePayTradeRecord(payTradeRecordRequests);
        }

    }

    @Override
    protected void tradeCallBackOnlineProcess(InternalPayBaseBean bean) {
        //授信还款
        if(payServiceHelper.isCreditRepayFlag(bean.getId())){
            List<String> ids = bean.getTradeVOList().stream().map(TradeVO::getId).collect(Collectors.toList());
            payAndRefundCallBackProvider.creditCallBack(
                    CreditCallBackRequest.builder()
                            .repayOrderCode(bean.getId())
                            .ids(ids)
                            .userId(commonUtil.getOperatorId())
                            .repayType(CreditRepayTypeEnum.BALANCE).build()
            );
        } else {
            super.tradeCallBackOnlineProcess(bean);
        }
    }
}
