package com.wanmi.sbc.trade;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditApplyQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditOrderProvider;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAmountRestoreRequest;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.account.api.request.credit.CreditOrderRequest;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayByRepayCodeRequest;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.vo.CreditApplyRecordVo;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
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
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.channelItem.OpenedChannelItemRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayOpenedByStoreIdRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemListResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigListResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayResultByOrdercodeRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.trade.TradeDefaultPayBatchRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetPayOrderByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradePayCallBackOnlineBatchRequest;
import com.wanmi.sbc.order.api.request.trade.TradeUpdateRequest;
import com.wanmi.sbc.order.bean.dto.PayOrderDTO;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.dto.TradePayCallBackOnlineDTO;
import com.wanmi.sbc.order.bean.enums.CreditPayState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.trade.request.DefaultPayBatchRequest;
import com.wanmi.sbc.trade.request.PayCommonRequest;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 支付
 * Created by sunkun on 2017/8/31.
 */
@Tag(name =  "PayBaseController", description =  "支付 API")
@RestController
@Validated
@RequestMapping("/pay")
public class PayBaseController {

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private PaySettingProvider paySettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CreditAccountQueryProvider creditAccountQueryProvider;

    @Autowired
    private CreditAccountProvider creditAccountProvider;

    @Autowired
    private CreditApplyQueryProvider creditApplyQueryProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PayServiceHelper payServiceHelper;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CreditOrderProvider creditOrderProvider;

    @Autowired
    private CreditRepayQueryProvider creditRepayQueryProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired
    private PayingMemberRecordTempQueryProvider payingMemberRecordTempQueryProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;
    /**
     * 查询支付记录状态
     *
     * @param tid 订单id
     * @return
     */
    @Operation(summary = "查询支付记录状态")
    @Parameter(name = "tid", description = "订单id", required = true)
    @RequestMapping(value = "/record/status/{tid}", method = RequestMethod.GET)
    public BaseResponse<TradeStatus> getRecordStatus(@PathVariable String tid) {
        return BaseResponse.success(payTradeRecordQueryProvider.getPayResponseByOrdercode(new PayResultByOrdercodeRequest(tid))
                .getContext().getTradeStatus());
    }


    /**
     * 查询在线支付是否开启
     *
     * @param type [PC,H5,APP]
     * @return
     */
    @Operation(summary = "查询在线支付是否开启，type: PC,H5,APP")
    @Parameter(name = "type", description = "终端类型", required = true)
    @RequestMapping(value = "/gateway/isopen/{type}", method = RequestMethod.GET)
    public BaseResponse<Boolean> queryGatewayIsOpen(@PathVariable String type) {
        GatewayOpenedByStoreIdRequest request = new GatewayOpenedByStoreIdRequest();
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        List<PayGatewayConfigVO> payGatewayConfigList = paySettingQueryProvider.listOpenedGatewayConfig(request).getContext()
                .getGatewayConfigVOList();
        List<PayChannelItemVO> itemList = new ArrayList<>();
        payGatewayConfigList.forEach(config -> {
            List<PayChannelItemVO> payChannelItemList = paySettingQueryProvider.listOpenedChannelItemByGatewayName(new
                    OpenedChannelItemRequest(config.getPayGateway().getName(), TerminalType.valueOf(type))).getContext
                    ().getPayChannelItemVOList();
            if (CollectionUtils.isNotEmpty(payChannelItemList)) {
                itemList.addAll(payChannelItemList);
            }
        });
        return BaseResponse.success(!itemList.isEmpty());
    }

    /**
     * 查询银联企业支付配置
     *
     * @return
     */
    @Operation(summary = "查询银联企业支付配置")
    @RequestMapping(value = "/queryUnionB2bConfig", method = RequestMethod.GET)
    public BaseResponse<PayGatewayConfigResponse> queryUnionB2bConfig() {
        PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(
                new GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONB2B, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
        return BaseResponse.success(payGatewayConfigResponse);
    }

    /**
     * 0元订单批量支付（支付网关默认为银联）
     *
     * @param request 请求参数
     * @return {@link BaseResponse}
     */
    @Operation(summary = "0元订单批量支付（支付网关默认为银联）")
    @GlobalTransactional
    @RequestMapping(value = "/default", method = RequestMethod.POST)
    public BaseResponse defaultPayBatch(@RequestBody @Valid DefaultPayBatchRequest request) {
        tradeProvider.defaultPayBatch(new TradeDefaultPayBatchRequest(request.getTradeIds(), PayWay.UNIONPAY));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 授信支付校验
     *
     * @param price
     * @return
     */
    @Operation(summary = "授信支付校验")
    @RequestMapping(value = "/creditCheck/{price}", method = RequestMethod.POST)
    public BaseResponse creditCheck(@PathVariable BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) == 0 || price.compareTo(BigDecimal.ZERO) < 0) {
            //参数不正确
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //授信支付验证
        checkCreditPay(price);

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取支付网关配置
     *
     * @return
     */
    @Operation(summary = "获取授信配置")
    @RequestMapping(value = "/getCreditConfig", method = RequestMethod.GET)
    public BaseResponse<PayGatewayVO> getCreditConfig() {
        GatewayOpenedByStoreIdRequest request = new GatewayOpenedByStoreIdRequest();
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        PayGatewayConfigListResponse configVOList = paySettingQueryProvider.listOpenedGatewayConfig(GatewayOpenedByStoreIdRequest
                .builder().storeId(Constants.BOSS_DEFAULT_STORE_ID).build()).getContext();
        // 判断返回值是否为空
        if (CollectionUtils.isNotEmpty(configVOList.getGatewayConfigVOList())) {
            Optional<PayGatewayConfigVO> first = configVOList.getGatewayConfigVOList().stream().filter(c -> Objects.equals(c.getPayGateway().getName(), PayGatewayEnum.CREDIT)).findFirst();
            if(first.isPresent()){
                return BaseResponse.success(first.get().getPayGateway());
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 授信支付验证
     */
    private CreditAccountDetailResponse checkCreditPay(BigDecimal price) {
        //是否开通授信账户
        // 请求对象
        CreditAccountDetailRequest request = CreditAccountDetailRequest
                .builder().customerId(commonUtil.getOperatorId()).build();
        BaseResponse<CreditAccountDetailResponse> creditAccountDetailResponse
                = creditAccountQueryProvider.getCreditAccountByCustomerId(request);

        //申请状态查询
        CreditApplyQueryRequest creditApplyQueryRequest = CreditApplyQueryRequest
                .builder().customerId(commonUtil.getOperatorId()).build();
        BaseResponse<CreditApplyRecordVo> baseResponse = creditApplyQueryProvider
                .queryApplyInfoByCustomerId(creditApplyQueryRequest);

        if (Objects.nonNull(creditAccountDetailResponse.getContext())
                && Objects.nonNull(baseResponse.getContext())) {
            CreditAccountDetailResponse credit = creditAccountDetailResponse.getContext();
            CreditApplyRecordVo recordVo = baseResponse.getContext();

            //1、账户表无数据 提示去申请  null
            //2、待审核 提示去申请   enable = 1，status = 0
            //3、驳回 提示去申请    enable = 1，status = 1
            //4、变更额度待审核 提示不可用   enable = 1，status = 3
            //5、变更额度申请通过、驳回 正常  enable = 1，status = 1、2
            //6、过期 提示不可用    enable = 1 ，endTime
            //7、额度不够 提示额度不足 amount
            if (null == credit
                    || StringUtils.isBlank(credit.getId())
                    || null == recordVo
                    || null == recordVo.getAuditStatus()
                    || null == credit.getEndTime()
            ) {
                //授信账户不存在
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
            }

            //初次申请中 提示去申请
            if (CreditAuditStatus.WAIT.equals(recordVo.getAuditStatus())) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
            }

            //是否过了有效期且未还款完成（未判断是否还款完成 需要判断是否有必要）
            if (credit.getEndTime().isBefore(LocalDateTime.now())) {
                //已过期
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            }

            //驳回需要判断是初次申请驳回 还是 额度变更驳回
            if (CreditAuditStatus.REJECT.equals(recordVo.getAuditStatus())) {
                if (credit.getEnabled().equals(BoolFlag.NO)) {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
                }
            }

            //变更申请待审核 提示不可用
            if (CreditAuditStatus.RESET_WAIT.equals(recordVo.getAuditStatus())) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            }

            //最终拦截
            if (credit.getEnabled().equals(BoolFlag.NO)) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            }

            //授信额度判断
            if (null == credit.getUsableAmount()
                    || credit.getUsableAmount().compareTo(price) < 0
            ) {
                //授信额度不足不存在
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020026);
            }

            return credit;

        } else {
            //授信账户不存在
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020025);
        }
    }


    /**
     * 授信支付
     *
     * @param payCommonRequest
     * @return
     */
    @Deprecated
    @Operation(summary = " 授信支付")
    @RequestMapping(value = "/creditPay", method = RequestMethod.POST)
    @GlobalTransactional
    @MultiSubmitWithToken
    public BaseResponse creditPay(@RequestBody @Valid PayCommonRequest payCommonRequest) {
        RLock rLock = redissonClient.getFairLock(commonUtil.getOperatorId());
        rLock.lock();
        try {
            PayGatewayConfigResponse gatewayConfigResponse =
                    paySettingQueryProvider.getGatewayConfigByGateway(
                            GatewayConfigByGatewayRequest.builder()
                            .gatewayEnum(PayGatewayEnum.CREDIT)
                            // boss端才有授信支付
                            .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                            .build()).getContext();
            if (gatewayConfigResponse.getPayGateway().getIsOpen().equals(IsOpen.NO)) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            }

            if(null == payCommonRequest.getTerminal()){
                //默认移动端
                payCommonRequest.setTerminal(TerminalType.H5);
            }

            //查询支付渠道
            PayChannelItemListResponse payChannelItemListResponse =
                    paySettingQueryProvider.listOpenedChannelItemByGatewayName(
                            OpenedChannelItemRequest.builder()
                            .gatewayName(PayGatewayEnum.CREDIT)
                            .terminalType(payCommonRequest.getTerminal())
                            .build()).getContext();

            List<PayChannelItemVO> payChannelItemVOS = payChannelItemListResponse.getPayChannelItemVOList();
            if(CollectionUtils.isNotEmpty(payChannelItemVOS)){
                payCommonRequest.setChannelItemId(payChannelItemVOS.get(0).getId());

                //支付方式关闭 则不可用
                IsOpen isOpen = payChannelItemVOS.get(0).getIsOpen();
                if(null != isOpen && isOpen.equals(IsOpen.NO)){
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
                }
            }
            boolean isPayMember = payServiceHelper.isPayMember(payCommonRequest.getTid());
            if (isPayMember) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
            } else {
                String tradeId = payServiceHelper.getPayBusinessId(payCommonRequest.getTid(), payCommonRequest.getParentTid(), null);
                List<TradeVO> tradeVOS = payServiceHelper.checkTrades(tradeId);
                BigDecimal totalPrice = payServiceHelper.calcTotalPriceByYuan(tradeVOS);

                //校验密码是否可用
                CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerById(new
                        CustomerGetByIdRequest(commonUtil.getOperatorId())).getContext();
                if (StringUtils.isBlank(customerGetByIdResponse.getCustomerPayPassword())) {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020023);
                }
                if (customerGetByIdResponse.getPayErrorTime() != null && customerGetByIdResponse.getPayErrorTime() == Constants.THREE) {
                    Duration duration = Duration.between(customerGetByIdResponse.getPayLockTime(), LocalDateTime.now());
                    if (duration.toMinutes() < Constants.NUM_30) {
                        //支付密码输错三次，并且锁定时间还未超过30分钟，返回账户冻结错误信息
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010032, new Object[]{30 - duration.toMinutes()});
                    }
                }

                //校验密码是否正确
                CustomerCheckPayPasswordRequest customerCheckPayPasswordRequest = new CustomerCheckPayPasswordRequest();
                customerCheckPayPasswordRequest.setPayPassword(payCommonRequest.getPayPassword());
                customerCheckPayPasswordRequest.setCustomerId(commonUtil.getOperatorId());
                customerSiteProvider.checkCustomerPayPwd(customerCheckPayPasswordRequest);

                //授信支付验证
                CreditAccountDetailResponse creditAccountDetailResponse = this.checkCreditPay(totalPrice);

                //授信额度变更
                CreditAmountRestoreRequest restoreRequest = new CreditAmountRestoreRequest();
                restoreRequest.setAmount(totalPrice);
                restoreRequest.setCustomerId(commonUtil.getOperatorId());
                creditAccountProvider.updateCreditAmount(restoreRequest);

                //授信额度使用记录
                List<CreditOrderRequest> creditOrderRequests = new ArrayList<>(tradeVOS.size());
                for (TradeVO tradeVO : tradeVOS) {
                    //定金预售取定金/尾款，其他取订单总金额
//                    BigDecimal price = Objects.nonNull(tradeVO.getIsBookingSaleGoods()) &&
//                            tradeVO.getBookingType() == BookingType.EARNEST_MONEY ?
//                            totalPrice : tradeVO.getTradePrice().getTotalPrice();
                    CreditOrderRequest creditOrderRequest =
                            new CreditOrderRequest();
                    creditOrderRequest.setCustomerId(commonUtil.getOperatorId());
                    creditOrderRequest.setOrderId(tradeVO.getId());
                    creditOrderRequest.setDelFlag(DeleteFlag.NO);
                    creditOrderRequest.setCreatePerson(commonUtil.getOperatorId());
                    creditOrderRequests.add(creditOrderRequest);

                    //设置授信支付信息
                    CreditPayInfoVO creditPayInfoVO = new CreditPayInfoVO();
                    creditPayInfoVO.setCreditAcccountId(creditAccountDetailResponse.getId());
                    creditPayInfoVO.setEndTime(creditAccountDetailResponse.getEndTime());
                    creditPayInfoVO.setStartTime(creditAccountDetailResponse.getStartTime());
                    creditPayInfoVO.setHasRepaid(Boolean.FALSE);

                    if (Objects.nonNull(tradeVO.getIsBookingSaleGoods())
                            && tradeVO.getBookingType() == BookingType.EARNEST_MONEY
                            && tradeVO.getTradeState().getPayState() == PayState.NOT_PAID) {
                        creditPayInfoVO.setCreditPayState(CreditPayState.DEPOSIT);
                    } else if (Objects.nonNull(tradeVO.getIsBookingSaleGoods())
                            && tradeVO.getIsBookingSaleGoods()
                            && tradeVO.getBookingType() == BookingType.EARNEST_MONEY
                            && tradeVO.getTradeState().getPayState() == PayState.PAID_EARNEST) {
                        if(null != tradeVO.getCreditPayInfo()
                                && tradeVO.getCreditPayInfo().getCreditPayState().equals(CreditPayState.DEPOSIT)){
                            creditPayInfoVO.setCreditPayState(CreditPayState.ALL);
                        }else{
                            creditPayInfoVO.setCreditPayState(CreditPayState.BALANCE);
                        }
                    } else {
                        //订单总金额
                        creditPayInfoVO.setCreditPayState(CreditPayState.PAID);
                    }

                    //设置授信支付方式
                    tradeVO.setPayWay(PayWay.CREDIT);
                    tradeVO.setNeedCreditRepayFlag(Boolean.TRUE);
                    tradeVO.setCreditPayInfo(creditPayInfoVO);
                }

                //批量新增授信支付订单关联数据
                creditOrderProvider.addCreditOrder(creditOrderRequests);

                // 新增交易记录
                List<PayTradeRecordRequest> payTradeRecordRequests = tradeVOS.stream()
                        .map(tradeVO -> {
                            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                            payTradeRecordRequest.setBusinessId(tradeId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID) ?
                                    tradeId : tradeVO.getId());
                            payTradeRecordRequest.setApplyPrice(payServiceHelper.
                                    calcTotalPriceByYuan(Collections.singletonList(tradeVO)));
                            payTradeRecordRequest.setPracticalPrice(payTradeRecordRequest.getApplyPrice());
                            payTradeRecordRequest.setResult_code("SUCCESS");
                            //此处是授信支付渠道 前端传
                            payTradeRecordRequest.setChannelItemId(payCommonRequest.getChannelItemId());
                            return payTradeRecordRequest;
                        }).collect(Collectors.toList());
                payTradeRecordProvider.batchSavePayTradeRecord(payTradeRecordRequests);
                // 支付成功，处理订单
                List<TradePayCallBackOnlineDTO> tradePayCallBackOnlineDTOS = tradeVOS.stream()
                        .map(tradeVO -> {
                            TradePayCallBackOnlineDTO tradePayCallBackOnlineDTO = new TradePayCallBackOnlineDTO();
                            tradePayCallBackOnlineDTO.setTrade(KsBeanUtil.convert(tradeVO, TradeDTO.class));
                            PayOrderVO payOrder = tradeQueryProvider.getPayOrderById(TradeGetPayOrderByIdRequest.builder()
                                    .payOrderId(tradeId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID) ?
                                            tradeVO.getTailPayOrderId() :
                                            tradeVO.getPayOrderId()).build()).getContext().getPayOrder();
                            tradePayCallBackOnlineDTO.setPayOrderOld(KsBeanUtil.convert(payOrder, PayOrderDTO.class));
                            return tradePayCallBackOnlineDTO;
                        }).collect(Collectors.toList());
                // 若订单为代销订单，则更新为普通订单
                for (TradePayCallBackOnlineDTO callBackOnlineDTO : tradePayCallBackOnlineDTOS) {
                    TradeDTO tradeDTO = callBackOnlineDTO.getTrade();
                    if (payServiceHelper.checkAndWrapperTradeSellInfo(tradeDTO)) {
                        tradeProvider.update(new TradeUpdateRequest(tradeDTO));
                    }
                }
                Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name("UNIONB2B")
                        .platform(Platform.THIRD).build();
                tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                        .requestList(tradePayCallBackOnlineDTOS)
                        .operator(operator)
                        .build());
                if (CollectionUtils.isEmpty(tradePayCallBackOnlineDTOS)) {
                    esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                            .customerId(tradeVOS.get(0).getBuyer().getId())
                            .build());
                }

            }

        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "授信还款支付前校验是否已支付成功")
    @Parameter(name = "repayCode", description = "还款单号", required = true)
    @RequestMapping(value = "/credit-repay/check/{repayCode}", method = RequestMethod.GET)
    public BaseResponse checkCreditRepayPayState(@PathVariable String repayCode) {
        CustomerCreditRepayVO customerCreditRepay = creditRepayQueryProvider.getCreditRepayByRepayCode(CustomerCreditRepayByRepayCodeRequest.builder()
                .repayCode(repayCode)
                .build()).getContext().getCustomerCreditRepayVO();
        String result = "0";
        if (Objects.nonNull(customerCreditRepay)) {
            if (Objects.equals(CreditRepayStatus.FINISH, customerCreditRepay.getRepayStatus())) {
                result = "1";
            } else if (Objects.equals(CreditRepayStatus.VOID, customerCreditRepay.getRepayStatus())) {
                result = "2";
            }
        }
        return BaseResponse.success(result);
    }
}
