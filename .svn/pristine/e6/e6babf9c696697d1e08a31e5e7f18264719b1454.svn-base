package com.wanmi.sbc.empower.provider.impl.sms.aliyun;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sms.aliyun.SmsTemplateAliyunProvider;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunAddRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunDeleteRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunModifyRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunQueryRequest;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunAddResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunDeleteResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunModifyResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunQueryResponse;
import com.wanmi.sbc.empower.bean.vo.SmsResponseAliyunVO;
import com.wanmi.sbc.empower.sms.service.aliyun.AliyunSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>短信基本接口实现</p>
 * @author dyt
 * @date 2019-12-03 15:36:05
 */
@Slf4j
@RestController
@Validated
public class SmsTemplateAliyunController implements SmsTemplateAliyunProvider {

    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Override
    public BaseResponse<SmsTemplateAliyunAddResponse> add(@Valid SmsTemplateAliyunAddRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.addSmsTemplate(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsTemplateAliyunAddResponse.class);
    }

    @Override
    public BaseResponse<SmsTemplateAliyunModifyResponse> modify(@Valid SmsTemplateAliyunModifyRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.modifySmsTemplate(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsTemplateAliyunModifyResponse.class);
    }

    @Override
    public BaseResponse<SmsTemplateAliyunDeleteResponse> delete(@Valid SmsTemplateAliyunDeleteRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.deleteSmsTemplate(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsTemplateAliyunDeleteResponse.class);
    }

    @Override
    public BaseResponse<SmsTemplateAliyunQueryResponse> query(@Valid SmsTemplateAliyunQueryRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.querySmsTemplate(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsTemplateAliyunQueryResponse.class);
    }

}

