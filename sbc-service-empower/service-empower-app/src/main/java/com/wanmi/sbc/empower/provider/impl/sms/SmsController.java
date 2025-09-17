package com.wanmi.sbc.empower.provider.impl.sms;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.sms.SmsProvider;
import com.wanmi.sbc.empower.api.request.sms.SmsSendRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingQueryRequest;
import com.wanmi.sbc.empower.bean.constant.SmsErrorCode;
import com.wanmi.sbc.empower.sms.model.root.SmsSetting;
import com.wanmi.sbc.empower.sms.service.SmsSendBaseService;
import com.wanmi.sbc.empower.sms.service.SmsSendServiceFactory;
import com.wanmi.sbc.empower.sms.service.SmsSettingService;
import com.wanmi.sbc.message.bean.enums.MessageErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>短信基本接口实现</p>
 * @author dyt
 * @date 2019-12-03 15:36:05
 */
@Slf4j
@RestController
@Validated
public class SmsController implements SmsProvider {

    @Autowired
    private SmsSettingService smsSettingService;
    @Autowired
    private SmsSendServiceFactory smsSendServiceFactory;

    @Override
    public BaseResponse send(@Valid SmsSendRequest request) {
        //手机号去重
        if(request.getPhoneNumbers().contains(",")){
            Set<String> phoneNumberSet = Stream.of(request.getPhoneNumbers().split(",")).collect(Collectors.toSet());
            request.setPhoneNumbers(StringUtils.join(phoneNumberSet, ','));
        }
        SmsSettingQueryRequest queryRequest =
                SmsSettingQueryRequest.builder().status(EnableStatus.ENABLE).delFlag(DeleteFlag.NO).build();
        if(request.getType() != null){
            queryRequest.setType(request.getType());
        }
        SmsSetting smsSetting = smsSettingService.getSmsSettingInfoByParam(queryRequest);
        if(smsSetting==null){
            log.error("无短信平台启用");
            throw new SbcRuntimeException(MessageErrorCodeEnum.K090001);
        }
        SmsSendBaseService smsSendBaseService = smsSendServiceFactory.create(smsSetting.getType());
        return smsSendBaseService.send(request);
    }
}

