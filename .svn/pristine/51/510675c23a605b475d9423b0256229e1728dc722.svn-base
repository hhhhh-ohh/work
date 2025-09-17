package com.wanmi.sbc.account.message;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.mq.MessageMqService;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description account，商家消息具体发送业务服务，统一管理
 * @author malianfeng
 * @date 2022/7/12 14:59
 */
@Slf4j
@Service
public class StoreMessageBizService {

    @Autowired private MessageMqService messageMqService;

    /**
     * 授信还款成功
     * @param creditAccount 授信账户信息
     */
    public void handleForCreditRepaymentSuccess(CustomerCreditAccount creditAccount) {
        try {
            // 封装发送请求
            StoreMessageMQRequest mqRequest = new StoreMessageMQRequest();
            mqRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
            mqRequest.setNodeCode(BossMessageNode.CREDIT_REPAYMENT_SUCCESS.getCode());
            mqRequest.setProduceTime(LocalDateTime.now());
            mqRequest.setContentParams(Lists.newArrayList(creditAccount.getCustomerAccount()));
            mqRequest.setRouteParams(MutableMap.of("account", creditAccount.getCustomerAccount()));
            messageMqService.sendStoreMessage(mqRequest);
        } catch (Exception e) {
            log.error("授信还款成功，消息处理失败，{}", creditAccount, e);
        }
    }
}

