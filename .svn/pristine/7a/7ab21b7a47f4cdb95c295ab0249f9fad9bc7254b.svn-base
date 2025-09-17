package com.wanmi.sbc.account.api.provider.mqconsumer;


import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectSwitchByCompanyInfoIdRequest;
import com.wanmi.sbc.account.api.request.mqconsumer.AccountMqConsumerRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description mq消费接口
 * @author  lvzhenwei
 * @date 2021/8/13 3:23 下午
 **/
@FeignClient(value = "${application.account.name}", contextId = "AccountMqConsumerProvider")
public interface AccountMqConsumerProvider {

    /**
     * @description
     * @author  lvzhenwei
     * @date 2021/8/13 3:30 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/modify-customer-account-funds")
    BaseResponse modifyCustomerAccountFunds(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 会员名称修改,触发账户模块-会员资金的会员名称修改
     * @author  lvzhenwei
     * @date 2021/8/13 3:47 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/modify-account-funds-customer-name")
    BaseResponse modifyAccountFundsCustomerName(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 会员名称、账号修改,触发账户模块-会员资金的会员名称、账号修改
     * @author  lvzhenwei
     * @date 2021/8/13 4:01 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/modify-funds-customer-name-and-account")
    BaseResponse modifyFundsCustomerNameAndAccount(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 新增会员，初始化会员资金信息
     * @author  lvzhenwei
     * @date 2021/8/13 4:09 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/account-funds-init")
    BaseResponse accountFundsInit(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 新增分销员，更新会员资金-是否分销员字段
     * @author  lvzhenwei
     * @date 2021/8/13 4:18 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/account-funds-modify-is-distributor")
    BaseResponse modifyAccountFundsIsDistributor(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 新增团长，更新会员资金-是否团长字段
     * @author  lvzhenwei
     * @date 2021/8/13 4:18 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/account-funds-modify-is-leader")
    BaseResponse modifyAccountFundsIsLeader(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 会员账户信息修改,触发账户模块-会员提现管理的会员名称修改
     * @author  lvzhenwei
     * @date 2021/8/13 4:34 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/draw-cash-modify-customer-name")
    BaseResponse modifyAccountDrawCashCustomerName(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 会员账户信息修改,触发账户模块-会员提现管理的会员账户修改
     * @author  lvzhenwei
     * @date 2021/8/13 4:47 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/modify-draw-cash-customer-account")
    BaseResponse modifyDrawCashCustomerAccount(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 邀新注册-发放奖励奖金
     * @author  lvzhenwei
     * @date 2021/8/13 5:01 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/account-funds-invite-grant-amount")
    BaseResponse accountFundsInviteGrantAmount(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 定时取消授信还款记录
     * @author  lvzhenwei
     * @date 2021/8/16 10:41 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/customer-credit-repay-cancel")
    BaseResponse customerCreditRepayCancel(@RequestBody @Valid AccountMqConsumerRequest request);

    /**
     * @description 授信账户状态变更事件
     * @author  lvzhenwei
     * @date 2021/8/18 10:15 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/account/${application.account.version}/mq/consumer/credit-state-change-event")
    BaseResponse creditStateChangeEvent(@RequestBody @Valid AccountMqConsumerRequest request);




}
