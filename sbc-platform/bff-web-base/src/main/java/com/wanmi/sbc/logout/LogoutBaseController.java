package com.wanmi.sbc.logout;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountDetailRequest;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsStatisticsResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerLogoutStatusModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelCustomerRequest;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.customer.service.CustomerAccountBaseService;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.message.StoreMessageBizService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.ValidateTradeAndAccountRequest;
import com.wanmi.sbc.order.api.response.trade.ValidateTradeAndAccountResponse;
import com.wanmi.sbc.setting.api.provider.cancellationreason.CancellationReasonQueryProvider;
import com.wanmi.sbc.setting.api.response.cancellationreason.CancellationReasonQueryResponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Tag(name = "LogoutBaseController", description = "注销 API")
@RestController
@Validated
@RequestMapping("/logout")
@Slf4j
public class LogoutBaseController {

    @Autowired
    private CustomerProvider customerProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerAccountBaseService customerAccountBaseService;

    @Autowired
    private CreditAccountQueryProvider creditAccountQueryProvider;

    @Autowired
    private CancellationReasonQueryProvider cancellationReasonQueryProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * 注销校验
     * @return
     */
    @Operation(summary = "注销校验")
    @GetMapping("/validateTradeAndAccount")
    public BaseResponse<ValidateTradeAndAccountResponse> validateTradeAndAccount(){
        ValidateTradeAndAccountResponse validateTradeAndAccountResponse = getValidateTradeAndAccountResponse();
        return BaseResponse.success(validateTradeAndAccountResponse);
    }

    private ValidateTradeAndAccountResponse getValidateTradeAndAccountResponse() {
        Operator operator = commonUtil.getOperator();
        String operatorId = operator.getUserId();
        // 订单校验
        ValidateTradeAndAccountRequest queryRequest = new ValidateTradeAndAccountRequest();
        queryRequest.setBuyerId(operatorId);
        Platform platform = operator.getPlatform();
        queryRequest.setPlatform(platform);
        ValidateTradeAndAccountResponse validateTradeAndAccountResponse =
                tradeQueryProvider.validateTradeAndAccount(queryRequest).getContext();
        // 余额校验
        CustomerFundsStatisticsResponse customerFundsStatisticsResponse =
                customerAccountBaseService.getCustomerFundsStatisticsResponseBaseResponse().getContext();
        if (customerFundsStatisticsResponse.getAccountBalanceTotal().compareTo(BigDecimal.ZERO)==0
                &&customerFundsStatisticsResponse.getBlockedBalanceTotal().compareTo(BigDecimal.ZERO)==0
                &&customerFundsStatisticsResponse.getWithdrawAmountTotal().compareTo(BigDecimal.ZERO)==0){
            validateTradeAndAccountResponse.setHasBalance(false);
        }else {
            validateTradeAndAccountResponse.setHasBalance(true);
        }
        // 授信校验
        CreditAccountDetailRequest request = CreditAccountDetailRequest.builder()
                .customerId(operatorId)
                .build();
        try{
            CreditAccountDetailResponse creditAccountDetail =
                    creditAccountQueryProvider.getCreditAccountDetail(request).getContext();
            if (Objects.nonNull(creditAccountDetail) && creditAccountDetail.getRepayAmount().compareTo(BigDecimal.ZERO)!=0){
                validateTradeAndAccountResponse.setHasCreditArrears(true);
            }else {
                validateTradeAndAccountResponse.setHasCreditArrears(false);
            }
        }catch (SbcRuntimeException e){
            if (StringUtils.equals(AccountErrorCodeEnum.K020013.getCode(), e.getErrorCode())){
                validateTradeAndAccountResponse.setHasCreditArrears(false);
            }
        }

        //付费会员校验
        List<PayingMemberLevelVO> payingMemberLevelVOList =
                payingMemberLevelQueryProvider.listByCustomerId(PayingMemberLevelCustomerRequest.builder()
                        .customerId(commonUtil.getOperatorId()).build()).getContext().getPayingMemberLevelVOList();
        if (CollectionUtils.isNotEmpty(payingMemberLevelVOList)) {
            validateTradeAndAccountResponse.setPayMemberActive(true);
        } else {
            validateTradeAndAccountResponse.setPayMemberActive(false);
        }
        return validateTradeAndAccountResponse;
    }

    /**
     * 确认注销
     * @return
     */
    @Operation(summary = "确认注销")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse confirm(@Valid @RequestBody CustomerLogoutStatusModifyRequest logoutRequest){
        log.info("用户确认注销开始...");
        // 参数校验
        if (StringUtils.isBlank(logoutRequest.getCancellationReasonId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CancellationReasonQueryResponse cancellationReasonQueryResponse = cancellationReasonQueryProvider.findAll().getContext();
        cancellationReasonQueryResponse.getCancellationReasonVOList().forEach(cancellationReasonVO -> {
            if (logoutRequest.getCancellationReasonId().equals(cancellationReasonVO.getId())){
                logoutRequest.setCancellationReason(cancellationReasonVO.getReason());
            }
        });
        // 注销原因校验
        if (StringUtils.isBlank(logoutRequest.getCancellationReason()) ||
                logoutRequest.getCancellationReason().length()>Constants.NUM_50){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //校验是否可以注销
        ValidateTradeAndAccountResponse validateTradeAndAccountResponse = getValidateTradeAndAccountResponse();
        if (Objects.nonNull(validateTradeAndAccountResponse.getCount())&&validateTradeAndAccountResponse.getCount()!=0){
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070086);
        }
        if (Objects.nonNull(validateTradeAndAccountResponse.getPointCount())&&validateTradeAndAccountResponse.getPointCount()!=0){
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070091);
        }
        if (validateTradeAndAccountResponse.isHasBalance()){
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070087);
        }
        if (validateTradeAndAccountResponse.isHasCreditArrears()){
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070088);
        }
        log.info("用户确认注销满足条件...");
        // 更新用户注销状态为待注销
        String operatorId = commonUtil.getOperatorId();
        logoutRequest.setCustomerId(operatorId);
        logoutRequest.setLogOutStatus(LogOutStatus.LOGGING_OFF);
        customerProvider.modifyLogOutStatusAndReason(logoutRequest);
        log.info("用户确认注销更新为注销中,用户id:{}", operatorId);
        //同步ES
        esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                .idList(Collections.singletonList(operatorId)).build());

        // ============= 处理平台/商家的消息发送：客户注销提醒 START =============
        storeMessageBizService.handleForCustomerLogout(operatorId);
        // ============= 处理平台/商家的消息发送：客户注销提醒 END =============

        log.info("用户确认注销结束...");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询全部注销原因
     *
     * @return.
     */
    @Operation(summary = "查询全部注销原因")
    @GetMapping("/reason-list")
    public BaseResponse<CancellationReasonQueryResponse> getCancellationReasonList() {
        return cancellationReasonQueryProvider.findAll();
    }

    /**
     * 撤销注销
     * @return
     */
    @Operation(summary = "撤销注销")
    @GetMapping(value = "/cancel")
    public BaseResponse cancel(){
        String operatorId = commonUtil.getOperatorId();
        CustomerLogoutStatusModifyRequest logoutRequest = new CustomerLogoutStatusModifyRequest();
        if (StringUtils.isBlank(operatorId)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        log.info("用户撤销注销更新为正常状态,用户id:{}", operatorId);
        // 更新用户注销状态为正常
        logoutRequest.setCustomerId(operatorId);
        logoutRequest.setLogOutStatus(LogOutStatus.NORMAL);
        customerProvider.modifyLogOutStatusAndReason(logoutRequest);
        //同步ES
        esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                .idList(Collections.singletonList(operatorId)).build());
        log.info("用户撤销注销结束...");
        return BaseResponse.SUCCESSFUL();
    }

}
