package com.wanmi.sbc.util.sms;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.message.api.provider.smsbase.SmsBaseProvider;
import com.wanmi.sbc.message.api.request.smsbase.SmsSendRequest;
import com.wanmi.sbc.message.bean.dto.SmsTemplateParamDTO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务
 * Created by aqlu on 15/12/4.
 */
@Component
public class SmsSendUtil {

    @Autowired
    private SmsBaseProvider smsBaseProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 发送短信
     *
     * @param smsTemplate 短信模板
     * @param phones      手机号码
     * @param params      手机参数
     */
    public boolean send(SmsTemplate smsTemplate, String[] phones, String... params) {
        SmsTemplateParamDTO dto = new SmsTemplateParamDTO();
        //取第二参数
        if ((SmsTemplate.CUSTOMER_PASSWORD.equals(smsTemplate)
                || SmsTemplate.EMPLOYEE_PASSWORD.equals(smsTemplate)) && params.length > 1) {
            dto.setAccount(getParam(params, 0));
            dto.setPassword(getParam(params, 1));
        }else {
            dto.setCode(getParam(params, 0));
        }
        BaseResponse response = smsBaseProvider.send(SmsSendRequest.builder()
                .businessType(smsTemplate.name())
                .phoneNumbers(StringUtils.join(phones,","))
                .templateParamDTO(dto)
                .build());
        return CommonErrorCodeEnum.K000000.getCode().equals(response.getCode());
    }

    /**
     * 发送短信
     * @param smsTemplate 短信模板
     * @param phones      手机号码
     * @param params      手机参数
     */
    public void sendByMq(SmsTemplate smsTemplate, String[] phones, String... params) {
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

    private String getParam(String[] params, int index) {
        return params[index];
    }
}
