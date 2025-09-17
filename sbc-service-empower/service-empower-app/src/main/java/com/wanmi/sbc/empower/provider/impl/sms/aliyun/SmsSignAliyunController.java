package com.wanmi.sbc.empower.provider.impl.sms.aliyun;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sms.aliyun.SmsSignAliyunProvider;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunAddRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunDeleteRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunModifyRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunQueryRequest;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunAddResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunDeleteResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunModifyResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunQueryResponse;
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
public class SmsSignAliyunController implements SmsSignAliyunProvider {

    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Override
    public BaseResponse<SmsSignAliyunAddResponse> add(@Valid SmsSignAliyunAddRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.addSmsSign(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsSignAliyunAddResponse.class);
    }

    @Override
    public BaseResponse<SmsSignAliyunModifyResponse> modify(@Valid SmsSignAliyunModifyRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.modifySmsSign(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsSignAliyunModifyResponse.class);
    }

    @Override
    public BaseResponse<SmsSignAliyunDeleteResponse> delete(@Valid SmsSignAliyunDeleteRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.deleteSmsSign(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsSignAliyunDeleteResponse.class);
    }

    @Override
    public BaseResponse<SmsSignAliyunQueryResponse> query(@Valid SmsSignAliyunQueryRequest request) {
        SmsResponseAliyunVO smsResponseAliyunVO = aliyunSmsService.querySmsSign(request);
        return AliyunSmsService.toBaseResponse(smsResponseAliyunVO, SmsSignAliyunQueryResponse.class);
    }
}

