package com.wanmi.sbc.message.handle.impl;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.enums.node.AccoutAssetsType;
import com.wanmi.sbc.common.enums.node.DistributionType;
import com.wanmi.sbc.common.enums.node.OrderProcessType;
import com.wanmi.sbc.common.enums.node.ReturnOrderProcessType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.message.SmsProxy;
import com.wanmi.sbc.message.bean.dto.SmsTemplateParamDTO;
import com.wanmi.sbc.message.handle.MessageDelivery;
import com.wanmi.sbc.message.smssenddetail.model.root.SmsSendDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("smsNoticeSendNodeHandler")
public class SmsNoticeSendHandler implements MessageDelivery {

    @Autowired
    private SmsProxy smsProxy;

    @Override
    public String messageHandlerName() {
        return "短信";
    }

    @Override
    public void handle(MessageMQRequest request) {
        log.info("通知类短信节点{}接收消息：{}", request.getNodeCode(), JSON.toJSONString(request));
        // 判断是否打开短信发送
        if (Constants.no.equals(request.getSendSms())) {
            return;
        }

        SmsSendDetail smsSend = new SmsSendDetail();
        smsSend.setBusinessType(request.getNodeCode());
        smsSend.setPhoneNumbers(request.getMobile());
        if(CollectionUtils.isNotEmpty(request.getParams())) {
            SmsTemplateParamDTO dto = new SmsTemplateParamDTO();
            //第一参数是number
            if(AccoutAssetsType.COUPON_RECEIPT.getType().equals(request.getNodeCode())
                    ||AccoutAssetsType.COUPON_EXPIRED.getType().equals(request.getNodeCode())
                    ||AccoutAssetsType.INTEGRAL_RECEIPT.getType().equals(request.getNodeCode())
                    ||AccoutAssetsType.INTEGRAL_EXPIRED.getType().equals(request.getNodeCode())
                    ||AccoutAssetsType.INTEGRAL_EXPIRED_AGAIN.getType().equals(request.getNodeCode())
                    ||AccoutAssetsType.GROWTH_VALUE_RECEIPT.getType().equals(request.getNodeCode())){
                dto.setNumber(request.getParams().get(0));
            }else if(AccoutAssetsType.BALANCE_CHANGE.getType().equals(request.getNodeCode())){
                //第一参数是money
                dto.setMoney(request.getParams().get(0));
            }else if(AccoutAssetsType.BALANCE_WITHDRAW_REJECT.getType().equals(request.getNodeCode())){
                //第一参数是remark
                dto.setRemark(StringUtil.trunc(request.getParams().get(0), 20, "..."));
            }else {
                dto.setName(StringUtil.trunc(request.getParams().get(0), 10, "..."));
            }

            if(request.getParams().size() > 1) {
                //第二参数是原因
                if (OrderProcessType.ORDER_CHECK_NOT_PASS.getType().equals(request.getNodeCode())
                        || ReturnOrderProcessType.AFTER_SALE_ORDER_CHECK_NOT_PASS.getType().equals(request.getNodeCode())
                        || ReturnOrderProcessType.RETURN_ORDER_GOODS_REJECT.getType().equals(request.getNodeCode())
                        || ReturnOrderProcessType.REFUND_CHECK_NOT_PASS.getType().equals(request.getNodeCode())) {
                    dto.setRemark(StringUtil.trunc(request.getParams().get(1), 20, "..."));
                } else if (OrderProcessType.GROUP_NUM_LIMIT.getType().equals(request.getNodeCode())
                        || DistributionType.FRIEND_REGISTER_SUCCESS_NO_REWARD.getType().equals(request.getNodeCode())
                        || DistributionType.FRIEND_REGISTER_SUCCESS_HAS_REWARD.getType().equals(request.getNodeCode())
                        || DistributionType.INVITE_CUSTOMER_REWARD_RECEIPT.getType().equals(request.getNodeCode())
                ) {
                    //第二参数是Number
                    dto.setNumber(request.getParams().get(1));
                } else if (AccoutAssetsType.BALANCE_CHANGE.getType().equals(request.getNodeCode())) {
                    //第二参数是Price
                    dto.setPrice(request.getParams().get(1));
                } else if (DistributionType.PROMOTE_ORDER_PAY_SUCCESS.getType().equals(request.getNodeCode())) {
                    //第二参数是Product
                    dto.setProduct(StringUtil.trunc(request.getParams().get(1), 10, "..."));
                }

                if(request.getParams().size() > Constants.TWO){
                    //第三参数是money
                    if(DistributionType.PROMOTE_ORDER_PAY_SUCCESS.getType().equals(request.getNodeCode())){
                        dto.setMoney(request.getParams().get(2));
                    }
                    if(DistributionType.FRIEND_REGISTER_SUCCESS_NO_REWARD.getType().equals(request.getNodeCode())
                            || DistributionType.FRIEND_REGISTER_SUCCESS_HAS_REWARD.getType().equals(request.getNodeCode())
                            || DistributionType.INVITE_CUSTOMER_REWARD_RECEIPT.getType().equals(request.getNodeCode())){
                        dto.setMoney(request.getParams().get(2));
                        if(request.getParams().size() > Constants.THREE){
                            dto.setPrice(request.getParams().get(3));
                        }
                    }
                }
            }
            smsSend.setTemplateParam(JSON.toJSONString(dto));
        }
        smsProxy.sendSms(smsSend);
    }

}
