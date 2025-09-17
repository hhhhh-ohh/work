package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerCheckPayPasswordRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetPayOrderByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayCallBackOnlineBatchRequest;
import com.wanmi.sbc.order.api.request.trade.TradeUpdateRequest;
import com.wanmi.sbc.order.bean.dto.PayOrderDTO;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.dto.TradePayCallBackOnlineDTO;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.InternalPayBaseBean;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import com.wanmi.sbc.pay.service.PayInterface;
import com.wanmi.sbc.trade.PayServiceHelper;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @type InternalPayService.java
 * @desc 内部支付渠道处理类
 * @date 2022/11/17 11:10
 */
@Service
public abstract class InternalPayService implements PayInterface {
    @Autowired PaySettingQueryProvider paySettingQueryProvider;

    @Autowired PayServiceHelper payServiceHelper;

    @Autowired PayingMemberRecordTempQueryProvider payingMemberRecordTempQueryProvider;

    @Autowired CustomerQueryProvider customerQueryProvider;

    @Autowired CustomerSiteProvider customerSiteProvider;

    @Autowired PayTradeRecordProvider payTradeRecordProvider;

    @Autowired TradeProvider tradeProvider;

    @Autowired TradeQueryProvider tradeQueryProvider;

    @Autowired RedissonClient redissonClient;

    @Autowired CommonUtil commonUtil;

    @Override
    public BaseResponse getResult(PayChannelRequest request) {
        RLock rLock = redissonClient.getFairLock(commonUtil.getOperatorId());
        rLock.lock();
        try {
            //校验请求
            this.check(request);

            //是否是付费会员
            boolean isPayMember = this.isPayMember(request);
            InternalPayBaseBean bean = null;
            if (isPayMember) {
                //付费会员处理
                bean = this.payMemberProcess(request);
            } else {
                //正常订单处理拿到
                bean = this.tradeProcess(request);
            }

            //校验账户信息，包含余额之类
            this.checkAccountPay(request, bean);

            //更新账户数据
            this.updateAccount(request, bean);

            //插入账户明细
            this.addDetail(request,bean);

            //插入支付单
            this.payTradeRecord(request, bean);

            //处理订单相关的逻辑
            this.payCallBackOnlineProcess(bean);
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return this.resultProcess();
    }

    /**
     * 校验
     *
     * @param request
     */
    protected void check(PayChannelRequest request) {
        PayGatewayConfigResponse gatewayConfigResponse =
                paySettingQueryProvider
                        .getGatewayConfigByGateway(
                                GatewayConfigByGatewayRequest.builder()
                                        .gatewayEnum(getPayGatewayEnum(request.getPayChannelType()))
                                        // boss端才有支付
                                        .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                                        .build())
                        .getContext();
        if (gatewayConfigResponse.getPayGateway().getIsOpen().equals(IsOpen.NO)) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
        }
        this.checkCustomerPayPwd(request);
    }

    /**
     * 是否是付费会员支付
     *
     * @param request
     * @return
     */
    protected boolean isPayMember(PayChannelRequest request) {
        return payServiceHelper.isPayMember(request.getId());
    }

    /**
     * 付费会员支付处理
     *
     * @param request
     * @return
     */
    protected InternalPayBaseBean payMemberProcess(PayChannelRequest request) {
        String id = request.getId();
        // 根据付费记录id 查询记录
        PayingMemberRecordTempVO payingMemberRecordTempVO =
                payingMemberRecordTempQueryProvider
                        .getById(PayingMemberRecordTempByIdRequest.builder().recordId(id).build())
                        .getContext()
                        .getPayingMemberRecordTempVO();
        // 如果重复支付，判断状态，已成功状态则做异常提示
        if (payingMemberRecordTempVO.getPayState() == TradeStatus.SUCCEED.toValue()) {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
        }
        BigDecimal totalPrice = payingMemberRecordTempVO.getPayAmount();
        return InternalPayBaseBean.builder().id(id).totalPrice(totalPrice).build();
    }

    protected InternalPayBaseBean tradeProcess(PayChannelRequest request) {
        String id = payServiceHelper.getPayBusinessId(request.getId());
        List<TradeVO> tradeVOS = payServiceHelper.checkTrades(id);
        // 支付总金额
        BigDecimal totalPrice;
        Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
        if (creditRepayFlag) {
            totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(tradeVOS);
        } else {
            totalPrice = payServiceHelper.calcTotalPriceByYuan(tradeVOS);
        }
        return InternalPayBaseBean.builder()
                .id(id)
                .totalPrice(totalPrice)
                .tradeVOList(tradeVOS)
                .build();
    }

    protected void checkCustomerPayPwd(PayChannelRequest request) {
        // 校验密码是否可用
        CustomerGetByIdResponse customerGetByIdResponse =
                customerQueryProvider
                        .getCustomerById(new CustomerGetByIdRequest(commonUtil.getOperatorId()))
                        .getContext();
        if (StringUtils.isBlank(customerGetByIdResponse.getCustomerPayPassword())) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020023);
        }
        if (customerGetByIdResponse.getPayErrorTime() != null
                && customerGetByIdResponse.getPayErrorTime() == Constants.THREE) {
            Duration duration =
                    Duration.between(customerGetByIdResponse.getPayLockTime(), LocalDateTime.now());
            if (duration.toMinutes() < Constants.NUM_30) {
                // 支付密码输错三次，并且锁定时间还未超过30分钟，返回账户冻结错误信息
                throw new SbcRuntimeException(
                        CustomerErrorCodeEnum.K010032,
                        new Object[] {30 - duration.toMinutes()});
            }
        }

        // 校验密码是否正确
        CustomerCheckPayPasswordRequest customerCheckPayPasswordRequest =
                new CustomerCheckPayPasswordRequest();
        customerCheckPayPasswordRequest.setPayPassword(request.getPayPassword());
        customerCheckPayPasswordRequest.setCustomerId(commonUtil.getOperatorId());
        customerSiteProvider.checkCustomerPayPwd(customerCheckPayPasswordRequest);
    }

    protected abstract void checkAccountPay(PayChannelRequest request, InternalPayBaseBean bean);

    protected abstract void updateAccount(PayChannelRequest request, InternalPayBaseBean bean);

    protected abstract void addDetail(PayChannelRequest request, InternalPayBaseBean bean);


    protected abstract void payTradeRecord(PayChannelRequest request, InternalPayBaseBean bean);

    protected void payCallBackOnlineProcess(InternalPayBaseBean bean) {
        if (payServiceHelper.isPayMember(bean.getId())) {
           payMemberCallBackOnlineProcess(bean);
        } else {
            tradeCallBackOnlineProcess(bean);
        }

    }

    protected void payMemberCallBackOnlineProcess(
            InternalPayBaseBean bean) {
        Operator operator =
                Operator.builder()
                        .ip(HttpUtil.getIpAddr())
                        .adminId("-1")
                        .name("UNIONB2B")
                        .platform(Platform.THIRD)
                        .build();


        tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                .recordId(bean.getId())
                .operator(operator)
                .build());
    }

    protected void tradeCallBackOnlineProcess(
            InternalPayBaseBean bean) {

        // 支付成功，处理订单
        List<TradePayCallBackOnlineDTO> tradePayCallBackOnlineDTOS =
                bean.getTradeVOList().stream()
                        .map(
                                tradeVO -> {
                                    TradePayCallBackOnlineDTO tradePayCallBackOnlineDTO =
                                            new TradePayCallBackOnlineDTO();
                                    tradePayCallBackOnlineDTO.setTrade(
                                            KsBeanUtil.convert(tradeVO, TradeDTO.class));
                                    PayOrderVO payOrder =
                                            tradeQueryProvider
                                                    .getPayOrderById(
                                                            TradeGetPayOrderByIdRequest.builder()
                                                                    .payOrderId(
                                                                            bean.getId()
                                                                                            .startsWith(
                                                                                                    GeneratorService
                                                                                                            ._PREFIX_TRADE_TAIL_ID)
                                                                                    ? tradeVO
                                                                                            .getTailPayOrderId()
                                                                                    : tradeVO
                                                                                            .getPayOrderId())
                                                                    .build())
                                                    .getContext()
                                                    .getPayOrder();
                                    tradePayCallBackOnlineDTO.setPayOrderOld(
                                            KsBeanUtil.convert(payOrder, PayOrderDTO.class));
                                    return tradePayCallBackOnlineDTO;
                                })
                        .collect(Collectors.toList());
        // 若订单为代销订单，则更新为普通订单
        for (TradePayCallBackOnlineDTO callBackOnlineDTO : tradePayCallBackOnlineDTOS) {
            TradeDTO tradeDTO = callBackOnlineDTO.getTrade();
            if (payServiceHelper.checkAndWrapperTradeSellInfo(tradeDTO)) {
                tradeProvider.update(new TradeUpdateRequest(tradeDTO));
            }
        }
        Operator operator =
                Operator.builder()
                        .ip(HttpUtil.getIpAddr())
                        .adminId("-1")
                        .name("UNIONB2B")
                        .platform(Platform.THIRD)
                        .build();

        tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                .requestList(tradePayCallBackOnlineDTOS)
                .operator(operator)
                .build());
    }

    protected BaseResponse resultProcess() {
        return BaseResponse.SUCCESSFUL();
    }

    private PayGatewayEnum getPayGatewayEnum(PayChannelType payChannelType) {

        switch (payChannelType) {
            case WX_H5:
            case WX_APP:
            case WX_SCAN:
            case WX_VIDEO:
            case WX_OUT_H5:
            case WX_MINI_PROGRAM:
                return PayGatewayEnum.WECHAT;
            case ALI_H5:
            case ALI_APP:
                return PayGatewayEnum.ALIPAY;
            case UNION_CLOUD:
                return PayGatewayEnum.UNIONPAY;
            case UNION_B2B:
                return PayGatewayEnum.UNIONB2B;
            case LAKALA_WX:
            case LAKALA_ALI:
            case LAKALA_UNION:
                return PayGatewayEnum.LAKALA;
            case CREDIT:
                return PayGatewayEnum.CREDIT;
            case BALANCE:
                return PayGatewayEnum.BALANCE;
            default:
                return null;
        }
    }
}
