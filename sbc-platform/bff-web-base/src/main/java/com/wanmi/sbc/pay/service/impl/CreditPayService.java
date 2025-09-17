package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditApplyQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditOrderProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAmountRestoreRequest;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.account.api.request.credit.CreditOrderRequest;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.vo.CreditApplyRecordVo;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.bean.enums.CreditPayState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.InternalPayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @type CreditPayService.java
 * @desc
 * @date 2022/11/22 16:40
 */
@PayPluginService(type = PayChannelType.CREDIT)
public class CreditPayService extends InternalPayService {

    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";

    @Autowired CreditAccountQueryProvider creditAccountQueryProvider;

    @Autowired CreditApplyQueryProvider creditApplyQueryProvider;

    @Autowired CreditAccountProvider creditAccountProvider;

    @Autowired CreditOrderProvider creditOrderProvider;

    @Override
    protected InternalPayBaseBean payMemberProcess(PayChannelRequest request) {
        //授信支付不支持付费会员
        throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
    }

    @Override
    protected void checkAccountPay(PayChannelRequest request, InternalPayBaseBean bean) {
        // 是否开通授信账户
        // 请求对象
        CreditAccountDetailRequest creditAccountDetailRequest =
                CreditAccountDetailRequest.builder().customerId(commonUtil.getOperatorId()).build();
        BaseResponse<CreditAccountDetailResponse> creditAccountDetailResponse =
                creditAccountQueryProvider.getCreditAccountByCustomerId(creditAccountDetailRequest);

        // 申请状态查询
        CreditApplyQueryRequest creditApplyQueryRequest =
                CreditApplyQueryRequest.builder().customerId(commonUtil.getOperatorId()).build();
        BaseResponse<CreditApplyRecordVo> baseResponse =
                creditApplyQueryProvider.queryApplyInfoByCustomerId(creditApplyQueryRequest);

        if (Objects.nonNull(creditAccountDetailResponse.getContext())
                && Objects.nonNull(baseResponse.getContext())) {
            CreditAccountDetailResponse credit = creditAccountDetailResponse.getContext();
            CreditApplyRecordVo recordVo = baseResponse.getContext();

            // 1、账户表无数据 提示去申请  null
            // 2、待审核 提示去申请   enable = 1，status = 0
            // 3、驳回 提示去申请    enable = 1，status = 1
            // 4、变更额度待审核 提示不可用   enable = 1，status = 3
            // 5、变更额度申请通过、驳回 正常  enable = 1，status = 1、2
            // 6、过期 提示不可用    enable = 1 ，endTime
            // 7、额度不够 提示额度不足 amount
            if (null == credit
                    || StringUtils.isBlank(credit.getId())
                    || null == recordVo
                    || null == recordVo.getAuditStatus()
                    || null == credit.getEndTime()) {
                // 授信账户不存在
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
            }

            // 初次申请中 提示去申请
            if (CreditAuditStatus.WAIT.equals(recordVo.getAuditStatus())) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
            }

            // 是否过了有效期且未还款完成（未判断是否还款完成 需要判断是否有必要）
            if (credit.getEndTime().isBefore(LocalDateTime.now())) {
                // 已过期
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            }

            // 驳回需要判断是初次申请驳回 还是 额度变更驳回
            if (CreditAuditStatus.REJECT.equals(recordVo.getAuditStatus())) {
                if (credit.getEnabled().equals(BoolFlag.NO)) {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
                }
            }

            // 变更申请待审核 提示不可用
            if (CreditAuditStatus.RESET_WAIT.equals(recordVo.getAuditStatus())) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            }

            // 最终拦截
            if (credit.getEnabled().equals(BoolFlag.NO)) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            }

            // 授信额度判断
            if (null == credit.getUsableAmount()
                    || credit.getUsableAmount().compareTo(bean.getTotalPrice()) < 0) {
                // 授信额度不足不存在
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020026);
            }
            Map<String,Object> extendMap = new HashMap<>();
            extendMap.put(START_TIME,credit.getStartTime());
            extendMap.put(END_TIME,credit.getEndTime());
            bean.setAccountId(credit.getId());

            bean.setExtendMap(extendMap);
        } else {
            // 授信账户不存在
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
        }
    }

    @Override
    protected void updateAccount(PayChannelRequest request, InternalPayBaseBean bean) {
        // 授信额度变更
        CreditAmountRestoreRequest restoreRequest = new CreditAmountRestoreRequest();
        restoreRequest.setAmount(bean.getTotalPrice());
        restoreRequest.setCustomerId(commonUtil.getOperatorId());
        creditAccountProvider.updateCreditAmount(restoreRequest);
    }

    @Override
    protected void addDetail(PayChannelRequest request, InternalPayBaseBean bean) {
        // 授信额度使用记录
        List<CreditOrderRequest> creditOrderRequests =
                new ArrayList<>(bean.getTradeVOList().size());
        for (TradeVO tradeVO : bean.getTradeVOList()) {
            // 定金预售取定金/尾款，其他取订单总金额
            //                    BigDecimal price =
            // Objects.nonNull(tradeVO.getIsBookingSaleGoods()) &&
            //                            tradeVO.getBookingType() == BookingType.EARNEST_MONEY ?
            //                            totalPrice : tradeVO.getTradePrice().getTotalPrice();
            CreditOrderRequest creditOrderRequest = new CreditOrderRequest();
            creditOrderRequest.setCustomerId(commonUtil.getOperatorId());
            creditOrderRequest.setOrderId(tradeVO.getId());
            creditOrderRequest.setDelFlag(DeleteFlag.NO);
            creditOrderRequest.setCreatePerson(commonUtil.getOperatorId());
            creditOrderRequests.add(creditOrderRequest);

            // 设置授信支付信息
            CreditPayInfoVO creditPayInfoVO = new CreditPayInfoVO();
            creditPayInfoVO.setCreditAcccountId(bean.getAccountId());
            creditPayInfoVO.setEndTime((LocalDateTime) bean.getExtendMap().get(END_TIME));
            creditPayInfoVO.setStartTime((LocalDateTime) bean.getExtendMap().get(START_TIME));
            creditPayInfoVO.setHasRepaid(Boolean.FALSE);

            if (Objects.nonNull(tradeVO.getIsBookingSaleGoods())
                    && tradeVO.getBookingType() == BookingType.EARNEST_MONEY
                    && tradeVO.getTradeState().getPayState() == PayState.NOT_PAID) {
                creditPayInfoVO.setCreditPayState(CreditPayState.DEPOSIT);
            } else if (Objects.nonNull(tradeVO.getIsBookingSaleGoods())
                    && tradeVO.getIsBookingSaleGoods()
                    && tradeVO.getBookingType() == BookingType.EARNEST_MONEY
                    && tradeVO.getTradeState().getPayState() == PayState.PAID_EARNEST) {
                if (null != tradeVO.getCreditPayInfo()
                        && tradeVO.getCreditPayInfo()
                                .getCreditPayState()
                                .equals(CreditPayState.DEPOSIT)) {
                    creditPayInfoVO.setCreditPayState(CreditPayState.ALL);
                } else {
                    creditPayInfoVO.setCreditPayState(CreditPayState.BALANCE);
                }
            } else {
                // 订单总金额
                creditPayInfoVO.setCreditPayState(CreditPayState.PAID);
            }

            // 设置授信支付方式
            tradeVO.setPayWay(PayWay.CREDIT);
            tradeVO.setNeedCreditRepayFlag(Boolean.TRUE);
            tradeVO.setCreditPayInfo(creditPayInfoVO);
        }

        // 批量新增授信支付订单关联数据
        creditOrderProvider.addCreditOrder(creditOrderRequests);
    }

    @Override
    protected void payTradeRecord(PayChannelRequest request, InternalPayBaseBean bean) {

        // 新增交易记录
        List<PayTradeRecordRequest> payTradeRecordRequests = bean.getTradeVOList().stream()
                .map(tradeVO -> {
                    PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                    payTradeRecordRequest.setBusinessId(bean.getId().startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID) ?
                            bean.getId() : tradeVO.getId());
                    payTradeRecordRequest.setApplyPrice(payServiceHelper.
                            calcTotalPriceByYuan(Collections.singletonList(tradeVO)));
                    payTradeRecordRequest.setPracticalPrice(payTradeRecordRequest.getApplyPrice());
                    payTradeRecordRequest.setResult_code("SUCCESS");
                    //此处是授信支付渠道 前端传
                    payTradeRecordRequest.setChannelItemId(request.getChannelItemId());
                    return payTradeRecordRequest;
                }).collect(Collectors.toList());
        payTradeRecordProvider.batchSavePayTradeRecord(payTradeRecordRequests);
    }
}
