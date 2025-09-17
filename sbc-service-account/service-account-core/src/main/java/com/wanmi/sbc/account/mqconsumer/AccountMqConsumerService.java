package com.wanmi.sbc.account.mqconsumer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.request.credit.CreditStateChangeEvent;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayCancelRequest;
import com.wanmi.sbc.account.api.request.funds.GrantAmountRequest;
import com.wanmi.sbc.account.credit.service.CreditOverviewService;
import com.wanmi.sbc.account.credit.service.CreditRepayQueryService;
import com.wanmi.sbc.account.customerdrawcash.service.CustomerDrawCashService;
import com.wanmi.sbc.account.funds.model.root.CustomerFunds;
import com.wanmi.sbc.account.funds.service.CustomerFundsService;
import com.wanmi.sbc.customer.api.request.mq.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author lvzhenwei
 * @className AccountMqConsumerService
 * @description TODO
 * @date 2021/8/13 3:20 下午
 **/
@Slf4j
@Service
public class AccountMqConsumerService {

    @Autowired
    private CustomerFundsService customerFundsService;

    @Autowired
    private CustomerDrawCashService customerDrawCashService;

    @Autowired
    private CreditRepayQueryService customerCreditRepayService;

    @Resource
    private CreditOverviewService creditOverviewService;

    /**
     * @description 会员账户修改,触发账户模块-会员资金的会员账户修改
     * @author  lvzhenwei
     * @date 2021/8/13 3:33 下午
     * @param json
     * @return void
     **/
    public void modifyCustomerAccountFunds(String json) {
        try {
            CustomerFundsModifyCustomerAccountRequest request = JSONObject.parseObject(json, CustomerFundsModifyCustomerAccountRequest.class);
            int result = customerFundsService.updateCustomerAccountByCustomerId(request.getCustomerId(),request.getCustomerAccount());
            log.info("更新会员资金-会员账号字段，是否成功 ? {}",result == 0 ? "失败" : "成功");
        } catch (Exception e) {
            log.error("更新会员资金-会员账号字段，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 会员名称修改,触发账户模块-会员资金的会员名称修改
     * @author  lvzhenwei
     * @date 2021/8/13 3:45 下午
     * @param json
     * @return void
     **/
    public void modifyAccountFundsCustomerName(String json) {
        try {
            CustomerFundsModifyCustomerNameRequest request = JSONObject.parseObject(json, CustomerFundsModifyCustomerNameRequest.class);
            int result = customerFundsService.updateCustomerNameByCustomerId(request.getCustomerId(),request.getCustomerName());
            log.info("更新会员资金-会员名称字段，是否成功 ? {}",result == 0 ? "失败" : "成功");
        } catch (Exception e) {
            log.error("更新会员资金-会员名称字段,发生异常! param={}", json, e);
        }
    }

    /**
     * @description 会员名称、账号修改,触发账户模块-会员资金的会员名称、账号修改
     * @author  lvzhenwei
     * @date 2021/8/13 4:00 下午
     * @param json
     * @return void
     **/
    public void modifyFundsCustomerNameAndAccount(String json) {
        try {
            CustomerFundsModifyCustomerNameAndAccountRequest request = JSONObject.parseObject(json, CustomerFundsModifyCustomerNameAndAccountRequest.class);
            int result = customerFundsService.updateCustomerNameAndAccountByCustomerId(request.getCustomerId(),request.getCustomerName(),request.getCustomerAccount());
            log.info("更新会员资金-会员名称、会员账号字段，是否成功 ? {}",result == 0 ? "失败" : "成功");
        } catch (Exception e) {
            log.error("更新会员资金-会员名称、会员账号字段,发生异常! param={}", json, e);
        }
    }

    /**
     * @description 新增会员，初始化会员资金信息
     * @author  lvzhenwei
     * @date 2021/8/13 4:08 下午
     * @param json
     * @return void
     **/
    public void accountFundsInit(String json) {
        try {
            CustomerFundsAddRequest request = JSONObject.parseObject(json, CustomerFundsAddRequest.class);
            CustomerFunds customerFunds = customerFundsService.findByCustomerId(request.getCustomerId());
            if (Objects.isNull(customerFunds)){
                CustomerFunds result = customerFundsService.init(request.getCustomerId(),request.getCustomerName(),
                        request.getCustomerAccount(), BigDecimal.ZERO, NumberUtils.INTEGER_ZERO, NumberUtils.INTEGER_ZERO);
                log.info("新增会员，初始化会员资金信息，是否成功 ? {}", Objects.nonNull(result) ? "成功" : "失败");
            }else{
                log.info("用户ID:{},此账户会员资金信息，已存在！",request.getCustomerId());
            }
        } catch (Exception e) {
            log.error("新增会员，初始化会员资金信息, 发生异常！param={}", json, e);
        }
    }

    /**
     * @description 新增分销员，更新会员资金-是否分销员字段
     * @author  lvzhenwei
     * @date 2021/8/13 4:18 下午
     * @param json
     * @return void
     **/
    public void modifyAccountFundsIsDistributor(String json) {
        try {
            CustomerFundsModifyIsDistributorRequest request = JSONObject.parseObject(json, CustomerFundsModifyIsDistributorRequest.class);
            CustomerFunds customerFunds = customerFundsService.findByCustomerId(request.getCustomerId());
            if (Objects.isNull(customerFunds)){
                CustomerFunds addResult = customerFundsService.init(request.getCustomerId(),request.getCustomerName()
                        ,request.getCustomerAccount(),BigDecimal.ZERO, NumberUtils.INTEGER_ONE, NumberUtils.INTEGER_ZERO);
                log.info("新增分销员，更新会员资金-是否分销员字段，查询不到用户ID:{}，存在会员资金记录，执行插入一条会员资金记录，默认分销员,是否成功 ? {}",addResult.getCustomerId(), "成功");
            }else{
                int result = customerFundsService.updateIsDistributorByCustomerId(request.getCustomerId(),NumberUtils.INTEGER_ONE);
                log.info("新增分销员，更新会员资金-是否分销员字段,是否成功 ? {}",result == 0 ? "失败" : "成功");
            }
        } catch (Exception e) {
            log.error("新增分销员，更新会员资金-是否分销员字段, 发生异常！param={}", json, e);
        }
    }

    /**
     * @description 新增团长，更新会员资金-是否团长字段
     * @author  lvzhenwei
     * @date 2021/8/13 4:18 下午
     * @param json
     * @return void
     **/
    public void modifyAccountFundsIsLeader(String json) {
        try {
            CustomerFundsModifyIsDistributorRequest request = JSONObject.parseObject(json, CustomerFundsModifyIsDistributorRequest.class);
            CustomerFunds customerFunds = customerFundsService.findByCustomerId(request.getCustomerId());
            if (Objects.isNull(customerFunds)){
                CustomerFunds addResult = customerFundsService.init(
                        request.getCustomerId(),
                        request.getCustomerName(),
                        request.getCustomerAccount(),
                        BigDecimal.ZERO,
                        NumberUtils.INTEGER_ZERO,
                        NumberUtils.INTEGER_ONE
                );
                log.info("新增团长，更新会员资金-是否团长字段，查询不到用户ID:{}，存在会员资金记录，执行插入一条会员资金记录，默认团长,是否成功 ? {}",addResult.getCustomerId(), "成功");
            }else{
                int result = customerFundsService.updateIsLeaderByCustomerId(request.getCustomerId(),NumberUtils.INTEGER_ONE);
                log.info("新增团长，更新会员资金-是否团长字段,是否成功 ? {}",result == 0 ? "失败" : "成功");
            }
        } catch (Exception e) {
            log.error("新增团长，更新会员资金-是否团长字段, 发生异常！param={}", json, e);
        }
    }

    /**
     * @description 会员账户信息修改,触发账户模块-会员提现管理的会员名称修改
     * @author  lvzhenwei
     * @date 2021/8/13 4:34 下午
     * @param json
     * @return void
     **/
    public void modifyAccountDrawCashCustomerName(String json) {
        try {
            CustomerDrawCashModifyCustomerNameRequest request = JSONObject.parseObject(json, CustomerDrawCashModifyCustomerNameRequest.class);
            int result = customerDrawCashService.updateCustomerNameByCustomerId(request.getCustomerId(),request.getCustomerName());
            log.info("更新会员提现-会员名称字段，是否成功 ? {}",result == 0 ? "失败" : "成功");
        } catch (Exception e) {
            log.error("更新会员提现-会员名称字段,发生异常! param={}", json, e);
        }
    }

    /**
     * @description 会员账户信息修改,触发账户模块-会员提现管理的会员账户修改
     * @author  lvzhenwei
     * @date 2021/8/13 4:46 下午
     * @param json
     * @return void
     **/
    public void modifyDrawCashCustomerAccount(String json) {
        try {
            CustomerDrawCashModifyCustomerAccountRequest request = JSONObject.parseObject(json, CustomerDrawCashModifyCustomerAccountRequest.class);
            int result = customerDrawCashService.updateCustomerAccountByCustomerId(request.getCustomerId(),request.getCustomerAccount());
            log.info("更新会员提现-会员账号字段，是否成功 ? {}",result == 0 ? "失败" : "成功");
        } catch (Exception e) {
            log.error("更新会员提现-会员账号字段，发生异常! param={}", json, e);
        }
    }

    /**
     * @description 邀新注册-发放奖励奖金
     * @author  lvzhenwei
     * @date 2021/8/13 5:00 下午
     * @param json
     * @return void
     **/
    public void accountFundsInviteGrantAmount(String json) {
        try {
            GrantAmountRequest request = JSONObject.parseObject(json, GrantAmountRequest.class);
            customerFundsService.grantAmount(request);
        } catch (Exception e) {
            log.error("邀新注册-发放奖金, 发生异常！param={}", json, e);
        }
    }

    /**
     * @description 定时取消授信还款记录
     * @author  lvzhenwei
     * @date 2021/8/18 10:13 上午
     * @param json
     * @return void
     **/
    public void customerCreditRepayCancel(String json) {
        CustomerCreditRepayCancelRequest request = JSONObject.parseObject(json, CustomerCreditRepayCancelRequest.class);
        log.info("10分钟未还款，定时取消授信在线还款的还款单：{}", json);
        customerCreditRepayService.autoCancel(request);
        log.info("定时取消还款单成功！");
    }

    /**
     * @description 授信账户状态变更事件
     * @author  lvzhenwei
     * @date 2021/8/18 10:14 上午
     * @param eventJson
     * @return void
     **/
    public void creditStateChangeEvent(String eventJson) {
        if (StringUtils.isEmpty(eventJson)) {
            return;
        }
        log.info("授信额度变更开始消费消息：{}", eventJson);
        CreditStateChangeEvent event = JSON.parseObject(eventJson, CreditStateChangeEvent.class);
        if (Objects.isNull(event) || Objects.isNull(event.getCreditStateChangeType())) {
            return;
        }
        switch (event.getCreditStateChangeType()) {
            case AUDIT_PASS:
                creditOverviewService.auditPass(event);
                break;
            case AMOUNT_CHANGE_PASS:
                creditOverviewService.amountChangePass(event);
                break;
            case PAY:
                creditOverviewService.pay(event);
                break;
            case REFUND:
                creditOverviewService.refund(event);
                break;
            case REPAY:
                creditOverviewService.repay(event);
                break;
            case EXPIRED:
                creditOverviewService.expired(event);
                break;
            case RESTORE:
                creditOverviewService.restore(event);
                break;
            default:
                log.error("creditStateChangeEvent listener no support event:{}!", JSON.toJSONString(event));
        }
    }

}
