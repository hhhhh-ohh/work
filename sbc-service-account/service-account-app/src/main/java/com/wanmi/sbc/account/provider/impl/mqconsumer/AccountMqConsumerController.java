package com.wanmi.sbc.account.provider.impl.mqconsumer;

import com.wanmi.sbc.account.api.provider.mqconsumer.AccountMqConsumerProvider;
import com.wanmi.sbc.account.api.request.mqconsumer.AccountMqConsumerRequest;
import com.wanmi.sbc.account.mqconsumer.AccountMqConsumerService;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className AccountMqConsumerController
 * @description mq消费接口方法实现
 * @date 2021/8/13 3:31 下午
 **/
@RestController
public class AccountMqConsumerController implements AccountMqConsumerProvider {

    @Autowired
    private AccountMqConsumerService accountMqConsumerService;

    @Override
    public BaseResponse modifyCustomerAccountFunds(@RequestBody  @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.modifyCustomerAccountFunds(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyAccountFundsCustomerName(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.modifyAccountFundsCustomerName(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyFundsCustomerNameAndAccount(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.modifyFundsCustomerNameAndAccount(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse accountFundsInit(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.accountFundsInit(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyAccountFundsIsDistributor(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.modifyAccountFundsIsDistributor(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyAccountFundsIsLeader(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.modifyAccountFundsIsLeader(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyAccountDrawCashCustomerName(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.modifyAccountDrawCashCustomerName(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyDrawCashCustomerAccount(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.modifyDrawCashCustomerAccount(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse accountFundsInviteGrantAmount(@RequestBody @Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.accountFundsInviteGrantAmount(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse customerCreditRepayCancel(@Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.customerCreditRepayCancel(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse creditStateChangeEvent(@Valid AccountMqConsumerRequest request) {
        accountMqConsumerService.creditStateChangeEvent(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }
}
