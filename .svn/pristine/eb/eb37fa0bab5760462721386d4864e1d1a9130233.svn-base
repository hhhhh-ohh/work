package com.wanmi.sbc.customer.sms;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信服务
 * Created by aqlu on 15/12/4.
 */
@Component
public class SmsSendUtil {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 发送短信
     * @param smsTemplate 短信模板
     * @param phones      手机号码
     * @param params      手机参数
     */
    public void send(SmsTemplate smsTemplate, String[] phones, String... params) {
        Map<String, Object> paramsMap = new HashMap<>();
        Map<String, String> dto = new HashMap<>();
        //取第二参数
        if ((SmsTemplate.CUSTOMER_PASSWORD == smsTemplate
                || SmsTemplate.EMPLOYEE_PASSWORD == smsTemplate
                || SmsTemplate.ENTERPRISE_CUSTOMER_PASSWORD == smsTemplate) && params.length > 1) {
            dto.put("account", params[0]);
            dto.put("password", params[1]);
        }else {
            dto.put("code", params[0]);
        }
        paramsMap.put("templateParamDTO", dto);
        paramsMap.put("businessType", smsTemplate.name());
        paramsMap.put("phoneNumbers", StringUtils.join(phones,","));
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.SMS_SEND_CODE_MESSAGE_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(paramsMap));
        mqSendProvider.send(mqSendDTO);
    }
}
