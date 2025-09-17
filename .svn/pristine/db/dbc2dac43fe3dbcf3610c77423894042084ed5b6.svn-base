package com.wanmi.sbc.customer.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.customer.CustomerToDistributorModifyRequest;
import com.wanmi.sbc.customer.api.request.customer.LedgerBindInfoToEsRequest;
import com.wanmi.sbc.customer.api.request.customer.LedgerBindInfoToEsUpdateRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeHandoverRequest;
import com.wanmi.sbc.customer.api.request.mq.*;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MQ生产者
 * @author: Geek Wang
 * @createDate: 2019/2/25 13:57
 * @version: 1.0
 */
@Service
@Slf4j
public class ProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 修改会员账号，发送MQ消息，同时修改会员资金里的会员账号字段
     * @param customerId 会员ID
     * @param customerAccount 会员账号
     */
    public void modifyCustomerAccountWithCustomerFunds(String customerId,String customerAccount) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.MODIFY_CUSTOMER_ACCOUNT_FUNDS);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerFundsModifyCustomerAccountRequest(customerId,customerAccount)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 修改会员名称，发送MQ消息，同时修改会员资金里的会员名称字段
     * @param customerId 会员ID
     * @param customerName 会员名称
     */
    public void modifyCustomerNameWithCustomerFunds(String customerId,String customerName) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.MODIFY_ACCOUNT_FUNDS_CUSTOMER_NAME);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerFundsModifyCustomerNameRequest(customerId,customerName)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 修改会员名称、会员账号，发送MQ消息，同时修改会员资金里的会员名称、会员账号字段
     * @param customerId 会员ID
     * @param customerName 会员名称
     * @param customerAccount 会员账号
     */
    public void modifyCustomerNameAndAccountWithCustomerFunds(String customerId,String customerName,String customerAccount) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.MODIFY_ACCOUNT_FUNDS_CUSTOMER_NAME_AND_ACCOUNT);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerFundsModifyCustomerNameAndAccountRequest(customerId,customerName,customerAccount)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 初始化会员资金信息
     * @param customerId 会员ID
     * @param customerName 会员名称
     * @param customerAccount 会员账号
     */
    public void initCustomerFunds(String customerId,String customerName,String customerAccount){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ACCOUNT_FUNDS_INIT);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerFundsAddRequest(customerId,customerName,customerAccount)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 新增分销员，更新会员资金-是否分销员字段
     * @param customerId 会员ID
     * @param customerName 会员名称
     * @param customerAccount 会员账号
     */
    public void modifyIsDistributorWithCustomerFunds(String customerId,String customerName,String customerAccount) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ACCOUNT_FUNDS_MODIFY_IS_DISTRIBUTOR);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerFundsModifyIsDistributorRequest(customerId,customerName,customerAccount)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 新增团长，更新会员资金-是否团长字段
     * @param customerId 会员ID
     * @param customerName 会员名称
     * @param customerAccount 会员账号
     */
    public void modifyIsLeaderWithCustomerFunds(String customerId,String customerName,String customerAccount) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ACCOUNT_FUNDS_MODIFY_IS_LEADER);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerFundsModifyIsLeaderRequest(customerId,customerName,customerAccount)));
        mqSendProvider.send(mqSendDTO);
    }


    /**
     * 修改会员账号，发送MQ消息，同时修改会员提现管理里的会员账号字段
     * @param customerId 会员ID
     * @param customerAccount 会员账号
     * @author chenyufei
     */
    public void modifyCustomerAccountWithCustomerDrawCash(String customerId,String customerAccount) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ACCOUNT_DRAW_CASH_MODIFY_CUSTOMER_ACCOUNT);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerDrawCashModifyCustomerAccountRequest(customerId,customerAccount)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 修改会员名称，发送MQ消息，同时修改会员提现管理里的会员名称字段
     * @param customerId 会员ID
     * @param customerName 会员名称
     * @author chenyufei
     */
    public void modifyCustomerNameWithCustomerDrawCash(String customerId,String customerName) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ACCOUNT_DRAW_CASH_MODIFY_CUSTOMER_NAME);
        mqSendDTO.setData(JSONObject.toJSONString(new CustomerDrawCashModifyCustomerNameRequest(customerId,customerName)));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 邀新注册-发放奖励奖金
     * @param request
     */
    public void modifyInviteGrantAmountWithCustomerFunds(CustomerFundsGrantAmountRequest request) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ACCOUNT_FUNDS_INVITE_GRANT_AMOUNT);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 邀新注册-发放优惠券
     * @param request
     */
    public void addCouponGroupFromInviteNew(CouponGroupAddRequest request){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.MARKET_COUPON_INVITE_NEW_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 发送push、站内信、短信
     * @param request
     */
    public void sendMessage(MessageMQRequest request){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.SMS_SERVICE_MESSAGE_SEND);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 发送商家消息
     * @param request
     */
    public void sendStoreMessage(StoreMessageMQRequest request){
        log.info("商家消息发送开始，request:{}", request);
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.STORE_MESSAGE_SEND);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 业务员交接数据
     * @param employeeIds
     */
    public void modifyEmployeeData(List<String> employeeIds, String newEmployeeId){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ORDER_MODIFY_EMPLOYEE_DATA);
        mqSendDTO.setData(JSONObject.toJSONString(new EmployeeHandoverRequest(employeeIds, newEmployeeId)));
        mqSendProvider.send(mqSendDTO);
    }


    /**
     * 修改会员ES的是否分销员字段
     * @param customerId
     * @param isDistributor
     */
    public void updateCustomerToDistributor(String customerId,DefaultFlag isDistributor){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_MODIFY_CUSTOMER_IS_DISTRIBUTOR);
        mqSendDTO.setData(JSONObject.toJSONString(CustomerToDistributorModifyRequest.builder().customerId(customerId).isDistributor(isDistributor).build()));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 创建清分账户
     * @param businessId
     */
    public void saveLedgerReceiver(String businessId) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.LEDGER_ACCOUNT_ADD);
        mqSendDTO.setData(businessId);
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 批量新增分账绑定关系
     * @param accountId
     */
    public void batchAddLedgerReceiverRel(String accountId) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.LEDGER_RECEIVER_REL_BATCH_ADD);
        mqSendDTO.setData(accountId);
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * es保存分账绑定数据
     * @param rels
     */
    public void esAddLedgerBindInfo(List<LedgerReceiverRel> rels) {
        LedgerBindInfoToEsRequest request = LedgerBindInfoToEsRequest.builder()
                .relVOList(KsBeanUtil.convertList(rels, LedgerReceiverRelVO.class)).build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_LEDGER_BIND_INFO_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * es分账绑定数据更新
     * @param receiverId
     * @param name
     * @param account
     */
    public void esLedgerBindInfoUpdate(String receiverId, String name, String account) {
        LedgerBindInfoToEsUpdateRequest request = new LedgerBindInfoToEsUpdateRequest();
        request.setReceiverId(receiverId);
        request.setName(name);
        request.setAccount(account);

        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_LEDGER_BIND_INFO_UPDATE);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
