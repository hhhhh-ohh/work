package com.wanmi.sbc.empower.sms.service.aliyun;

import com.alibaba.fastjson2.JSON;
import com.aliyuncs.CommonRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sms.SmsSendRequest;
import com.wanmi.sbc.empower.bean.constant.SmsResponseCode;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;
import com.wanmi.sbc.empower.bean.vo.SmsResponseAliyunVO;
import com.wanmi.sbc.empower.sms.service.SmsSendBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component(SmsPlatformType.SMS_SEND_SERVICE_ALIYUN)
public class SmsSendAliyunService implements SmsSendBaseService {

    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Override
    public BaseResponse send(SmsSendRequest smsSendRequest) {
        CommonRequest request = new CommonRequest();
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", smsSendRequest.getPhoneNumbers());
        request.putQueryParameter("SignName", smsSendRequest.getSignName());
        request.putQueryParameter("TemplateCode", smsSendRequest.getTemplateCode());
        if (Objects.nonNull(smsSendRequest.getTemplateParamMap())) {
            request.putQueryParameter("TemplateParam", JSON.toJSONString(smsSendRequest.getTemplateParamMap()));
        }
        log.info("开始阿里云发送短信【{}】，mobile={}，templateCode={}，params={}", smsSendRequest.getSignName(),
                smsSendRequest.getPhoneNumbers(), smsSendRequest.getTemplateCode(),
                Objects.toString(JSON.toJSONString(smsSendRequest.getTemplateParamMap()), ""));
        SmsResponseAliyunVO aliyunSmsResponse = aliyunSmsService.doAction(request);
        if(!SmsResponseCode.SUCCESS.equals(aliyunSmsResponse.getCode())) {
            log.error("发送阿里云短信失败【{}】，mobile={}，templateCode={}，params={}，response={}",
                    smsSendRequest.getSignName(),
                    smsSendRequest.getPhoneNumbers(), smsSendRequest.getTemplateCode(),
                    Objects.toString(JSON.toJSONString(smsSendRequest.getTemplateParamMap()), ""),
                    JSON.toJSONString(aliyunSmsResponse));
            String errorCode = aliyunSmsResponse.getCode();
            if (StringUtils.isNotBlank(errorCode)){
                errorCode = errorCode.replace("K", "ALI");
            }
            return BaseResponse.info(errorCode, "阿里云发送短信失败:" + aliyunSmsResponse.getMessage());
        }else{
            log.info("阿里云发送短信成功【{}】，mobile={}，templateCode={}，params={}，response={}", smsSendRequest.getSignName(),
                    smsSendRequest.getPhoneNumbers(), smsSendRequest.getTemplateCode(),
                    Objects.toString(JSON.toJSONString(smsSendRequest.getTemplateParamMap()), ""),
                    JSON.toJSONString(aliyunSmsResponse));
        }
        return BaseResponse.success(aliyunSmsResponse);
    }
}
